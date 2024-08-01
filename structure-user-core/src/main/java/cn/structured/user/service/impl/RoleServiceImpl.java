package cn.structured.user.service.impl;

import cn.structured.mybatis.plus.starter.base.BaseServiceImpl;
import cn.structured.user.api.dto.role.BindingAuthorityDTO;
import cn.structured.user.entity.Role;
import cn.structured.user.entity.RoleAuthorityMapping;
import cn.structured.user.mapper.RoleAuthorityMappingMapper;
import cn.structured.user.mapper.RoleMapper;
import cn.structured.user.service.IRoleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色管理
 *
 * @author chuck
 * @since JDK1.8
 */
@Slf4j
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleAuthorityMappingMapper authorityMappingMapper;

    @Override
    public void enable(Long roleId) {
        Role role = new Role();
        role.setId(roleId);
        role.setEnabled(true);
        baseMapper.updateById(role);
    }

    @Override
    public void disable(Long roleId) {
        Role role = new Role();
        role.setId(roleId);
        role.setEnabled(false);
        baseMapper.updateById(role);
    }

    @Override
    public List<String> getAuthorities(Long roleId) {
        return authorityMappingMapper.selectList(Wrappers.<RoleAuthorityMapping>lambdaQuery()
                        .eq(RoleAuthorityMapping::getRoleId, roleId)
                        .select(RoleAuthorityMapping::getId, RoleAuthorityMapping::getAuthorityCode))
                .stream()
                .map(RoleAuthorityMapping::getAuthorityCode)
                .collect(Collectors.toList());
    }

    @Override
    public void saveRoleMenu(BindingAuthorityDTO bindingAuthorityDto) {
        authorityMappingMapper.delete(Wrappers.<RoleAuthorityMapping>lambdaQuery().eq(RoleAuthorityMapping::getRoleId, bindingAuthorityDto.getId()));
        //构建角色权限
        List<RoleAuthorityMapping> roleMenus = bindingAuthorityDto.getAuthorities().stream()
                .map(authorityId -> {
                    RoleAuthorityMapping oauthRoleAuthorityMapping = new RoleAuthorityMapping();
                    oauthRoleAuthorityMapping.setRoleId(bindingAuthorityDto.getId());
                    oauthRoleAuthorityMapping.setAuthorityCode(authorityId);
                    return oauthRoleAuthorityMapping;
                }).collect(Collectors.toList());
        //判断权限不为空
        if (!roleMenus.isEmpty()) {
            //插入权限
            authorityMappingMapper.insertList(roleMenus);
        }
    }

}
