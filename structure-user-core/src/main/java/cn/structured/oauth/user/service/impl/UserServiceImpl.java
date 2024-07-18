package cn.structured.oauth.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.structure.common.exception.CommonException;
import cn.structured.mybatis.plus.starter.base.BaseServiceImpl;
import cn.structured.oauth.api.enums.PlatformCodeEnum;
import cn.structured.oauth.user.api.dto.OptionDto;
import cn.structured.oauth.user.api.dto.user.*;
import cn.structured.oauth.user.controller.assembler.UserAssembler;
import cn.structured.oauth.user.entity.*;
import cn.structured.oauth.user.mapper.*;
import cn.structured.oauth.user.service.IUserService;
import cn.structured.security.util.SecurityUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户账户ServiceImpl
 *
 * @author chuck
 * @since 1.0.1
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserBindMapper userBindMapper;
    @Resource
    private UserRoleMappingMapper userRoleMappingMapper;
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleAuthorityMappingMapper roleAuthorityMappingMapper;

    @Override
    public User loadUserByUserName(String username) {
        //查询用户信息
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, username)
                .select(User::getId, User::getUsername, User::getPassword, User::getEnabled, User::getUnexpired, User::getUnlocked)
        );
        //验证用户
        checkUser(user);
        return user;
    }

    @Override
    public User loadUserByPlatformUserIdAndPlatformCode(String platformUserId, String platformCode) {
        //查询用户绑定ID
        UserBind oauthUserBind = userBindMapper.selectOne(Wrappers.<UserBind>lambdaQuery()
                .eq(UserBind::getPlatformCode, platformCode)
                .eq(UserBind::getPlatformUserId, platformUserId)
                .select(UserBind::getPlatformUserId, UserBind::getUserId));
        if (null == oauthUserBind) {
            throw new CommonException("", "用户不存在！");
        }
        //查询用户信息
        User user = userMapper.selectById(oauthUserBind.getUserId());
        //验证用户
        checkUser(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long registerUser(RegisterUserDto registerUserDto) {
        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        String password = registerUserDto.getPassword();
        //判断密码不为空则将密码加密后写入到数据库中
        if (StrUtil.isNotBlank(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        user.setUnexpired(Boolean.TRUE);
        user.setEnabled(Boolean.TRUE);
        user.setUnlocked(Boolean.TRUE);
        user.setDeleted(Boolean.FALSE);
        String type = registerUserDto.getType();
        userMapper.insert(user);
        if (StrUtil.isNotBlank(type)) {
            //验证code是否正确
            UserBind oauthUserBind = new UserBind();
            oauthUserBind.setUserId(user.getId());
            oauthUserBind.setPlatformCode(registerUserDto.getType());
            //手机号注册
            if (PlatformCodeEnum.PHONE.getCode().equals(type)) {
                oauthUserBind.setPlatformUserId(registerUserDto.getPhone());
                userBindMapper.insert(oauthUserBind);
            }
            //邮箱注册
            if (PlatformCodeEnum.EMAIL.getCode().equals(type)) {
                oauthUserBind.setPlatformUserId(registerUserDto.getEmail());
                userBindMapper.insert(oauthUserBind);
            }
        }
        return user.getId();
    }

    /**
     * 验证用户信息
     *
     * @param user 用户信息
     */
    private void checkUser(User user) {
        if (null == user) {
            throw new CommonException("", "用户不存在！");
        }
        if (!user.getUnexpired()) {
            throw new CommonException("", "用户已过期！");
        }
        if (!user.getUnlocked()) {
            throw new CommonException("", "用户已锁定！");
        }
        if (!user.getEnabled()) {
            throw new CommonException("", "用户未启用！");
        }
    }


    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        //查询用户信息
        User user = this.getById(userId);
        String password = user.getPassword();
        //验证用户输入密码和数据库中的密码是否匹配
        boolean matches = passwordEncoder.matches(oldPassword, password);

        //验证密码是否正确
        if (!matches) {
            //todo 密码验证失败后提示错误信息！
            throw new CommonException();
        }
        //调用重置密码方法重置密码
        resetPassword(userId, newPassword);
    }

    @Override
    public void resetPassword(Long userId, String newPassword) {
        String password = passwordEncoder.encode(newPassword);
        User user = new User();
        user.setId(userId);
        user.setPassword(password);
        userMapper.updateById(user);
    }

    @Override
    public void enable(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setEnabled(true);
        userMapper.updateById(user);
    }

    @Override
    public void disable(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setEnabled(false);
        userMapper.updateById(user);
    }

    @Override
    public void lock(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setUnlocked(false);
        userMapper.updateById(user);
    }

    @Override
    public void unlock(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setUnlocked(true);
        userMapper.updateById(user);
    }

    @Override
    public void bindingPlatformUser(BindingPlatformUserIdDto bindingPlatformUserIdDto) {
        UserBind oauthUserBind = new UserBind();
        oauthUserBind.setUserId(bindingPlatformUserIdDto.getUserId());
        oauthUserBind.setPlatformUserId(bindingPlatformUserIdDto.getPlatformUserId());
        oauthUserBind.setPlatformCode(bindingPlatformUserIdDto.getPlatformTypeCode());
        userBindMapper.insert(oauthUserBind);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long registerPlatformUser(RegisterPlatformUserDto registerPlatformUserDto) {
        User user = new User();
        user.setNickName(registerPlatformUserDto.getNickname());
        user.setUsername(registerPlatformUserDto.getPlatformUserId());
        user.setAvatar(registerPlatformUserDto.getAvatar());
        user.setUnexpired(Boolean.TRUE);
        user.setEnabled(Boolean.TRUE);
        user.setUnlocked(Boolean.TRUE);
        user.setDeleted(Boolean.FALSE);
        String type = registerPlatformUserDto.getType();
        userMapper.insert(user);
        if (StrUtil.isNotBlank(type)) {
            UserBind oauthUserBind = new UserBind();
            oauthUserBind.setUserId(user.getId());
            oauthUserBind.setPlatformCode(registerPlatformUserDto.getType());
            oauthUserBind.setPlatformUserId(registerPlatformUserDto.getPlatformUserId());
            userBindMapper.insert(oauthUserBind);
        }
        return user.getId();
    }

    @Override
    public UserInfoDto currentUserInfo() {
        //获取当前用户ID
        Long userId = SecurityUtils.getUserId();
        //查询当前用户信息
        User user = baseMapper.selectById(userId);
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(user.getId());
        userInfoDto.setUsername(user.getUsername());
        userInfoDto.setNickname(user.getNickName());
        userInfoDto.setAvatar(user.getAvatar());
        //查询当前用户所具备的角色
        Set<Long> roleIds = userRoleMappingMapper
                .selectList(Wrappers.<UserRoleMapping>lambdaQuery()
                        .eq(UserRoleMapping::getUserId, userId)
                        .select(UserRoleMapping::getId, UserRoleMapping::getRoleId))
                .stream()
                .map(UserRoleMapping::getRoleId)
                .collect(Collectors.toSet());
        //这是目前没有角色表示一个普通用户
        if (!roleIds.isEmpty()) {
            //去数据库中把角色标识符查询出来
            List<Role> roles = roleMapper.selectList(Wrappers.<Role>lambdaQuery()
                    .in(Role::getId, roleIds)
                    .eq(Role::getEnabled, true)
                    .select(Role::getId, Role::getCode));
            roleIds = roles.stream().map(Role::getId).collect(Collectors.toSet());
            if (roles.isEmpty()) {
                userInfoDto.setRoles(Lists.newArrayList());
                userInfoDto.setPerms(Lists.newArrayList());
            } else {
                userInfoDto.setRoles(roles.stream().map(Role::getCode).collect(Collectors.toList()));
                List<RoleAuthorityMapping> roleAuthorityMappings = roleAuthorityMappingMapper.selectList(Wrappers.<RoleAuthorityMapping>lambdaQuery()
                        .in(RoleAuthorityMapping::getRoleId, roleIds)
                        .select(RoleAuthorityMapping::getAuthorityCode));
                userInfoDto.setPerms(roleAuthorityMappings.stream()
                        .map(RoleAuthorityMapping::getAuthorityCode)
                        .collect(Collectors.toList()));

            }
        }
        return userInfoDto;
    }

    @Override
    public List<String> getUserAuthorities(Long userId) {
        Set<Long> roleIds = userRoleMappingMapper
                .selectList(Wrappers.<UserRoleMapping>lambdaQuery()
                        .eq(UserRoleMapping::getUserId, userId)
                        .select(UserRoleMapping::getId, UserRoleMapping::getRoleId))
                .stream()
                .map(UserRoleMapping::getRoleId)
                .collect(Collectors.toSet());
        if (CollUtil.isEmpty(roleIds)) {
            return Lists.newArrayList();
        }

        filterRole(roleIds);
        //如果没有角色则直接返回
        if (roleIds.isEmpty()) {
            return Lists.newArrayList();
        }

        //去数据库中把角色标识符查询出来
        List<RoleAuthorityMapping> roleAuthorityMappings = roleAuthorityMappingMapper.selectList(Wrappers.<RoleAuthorityMapping>lambdaQuery()
                .in(RoleAuthorityMapping::getRoleId, roleIds)
                .select(RoleAuthorityMapping::getAuthorityCode));
        return roleAuthorityMappings.stream()
                .map(RoleAuthorityMapping::getAuthorityCode)
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> getUserRole(Long userId) {
        Set<Long> roleIds = userRoleMappingMapper
                .selectList(Wrappers.<UserRoleMapping>lambdaQuery()
                        .eq(UserRoleMapping::getUserId, userId)
                        .select(UserRoleMapping::getId, UserRoleMapping::getRoleId))
                .stream()
                .map(UserRoleMapping::getRoleId)
                .collect(Collectors.toSet());

        filterRole(roleIds);
        if (roleIds.isEmpty()) {
            return Lists.newArrayList();
        }

        return roleMapper.selectList(Wrappers.<Role>lambdaQuery()
                .in(Role::getId, roleIds)
                .select(Role::getId, Role::getCode, Role::getName));
    }

    @Override
    public void assigningRole(List<Long> roleIds, Long userId) {
        //删除原来的角色
        userRoleMappingMapper.delete(Wrappers.<UserRoleMapping>lambdaQuery().eq(UserRoleMapping::getUserId, userId));
        //插入新的角色
        List<UserRoleMapping> userRoleMappings = roleIds.stream().map(
                roleId -> {
                    UserRoleMapping userRoleMapping = new UserRoleMapping();
                    userRoleMapping.setRoleId(roleId);
                    userRoleMapping.setUserId(userId);
                    return userRoleMapping;
                }
        ).collect(Collectors.toList());
        userRoleMappingMapper.insertList(userRoleMappings);
    }

    @Override
    public UserDetailDto getUserDetailByUserId(Long userId) {
        User user = this.getById(userId);
        List<Role> userRole = this.getUserRole(userId);
        List<OptionDto> roleList = userRole.stream().map(role -> {
                            OptionDto option = new OptionDto();
                            option.setId(role.getId());
                            option.setValue(role.getCode());
                            option.setLabel(role.getName());
                            return option;
                        }
                )
                .collect(Collectors.toList());
        UserDetailDto userDetail = UserAssembler.assembler(user);
        userDetail.setRole(roleList);
        return userDetail;
    }

    /**
     * 过滤禁用的角色
     *
     * @param roleIds 角色ID
     */
    private void filterRole(Set<Long> roleIds) {
        if (roleIds.isEmpty()) {
            return;
        }
        //过滤禁用的角色
        roleIds = roleMapper.selectList(Wrappers.<Role>lambdaQuery()
                        .in(Role::getId, roleIds)
                        .eq(Role::getEnabled, true)
                        .select(Role::getId, Role::getCode))
                .stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
    }

}
