package cn.structured.user.controller.open;

import cn.structure.common.entity.ResResultVO;
import cn.structure.common.utils.ResultUtilSimpleImpl;
import cn.structured.oauth.user.api.dto.user.AssigningRoleDTO;
import cn.structured.oauth.user.api.dto.user.RegisterPlatformUserDTO;
import cn.structured.oauth.user.api.dto.user.RestPasswordDTO;
import cn.structured.oauth.user.api.dto.user.UserDetailDTO;
import cn.structured.user.controller.assembler.UserAssembler;
import cn.structured.user.entity.Role;
import cn.structured.user.entity.User;
import cn.structured.user.service.IUserService;
import cn.structured.security.entity.StructureAuthUser;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 开放api
 *
 * @author chuck
 * @since JDK1.8
 */
@RestController
@RequestMapping(value = "/open-api/user")
public class OpenApiUserController {

    @Resource
    private IUserService userService;

    @ApiOperation(value = "通过用户ID查询用户详情", notes = "全局有权限即可")
    @GetMapping(value = "/get/{userId}")
    public ResResultVO<UserDetailDTO> get(@ApiParam(value = "用户ID", example = "1645717015337684992")
                                          @PathVariable("userId") Long userId) {
        User user = userService.getById(userId);
        return ResultUtilSimpleImpl.success(UserAssembler.assembler(user));
    }

    @ApiOperation(value = "通过用户ID查询用户详情", notes = "全局有权限即可")
    @GetMapping(value = "/get/list")
    public ResResultVO<Map<Long, UserDetailDTO>> getListByIds(Set<Long> userIds) {
        Map<Long, UserDetailDTO> userMap = userService.list(Wrappers.<User>lambdaQuery()
                        .in(User::getId, userIds)
                        .select(User::getId, User::getNickName, User::getAvatar))
                .stream()
                .collect(Collectors.toMap(User::getId, UserAssembler::assembler));
        return ResultUtilSimpleImpl.success(userMap);
    }

    @ApiOperation(value = "注册平台用户")
    @PostMapping(value = "/register")
    public ResResultVO<Long> register(@RequestBody RegisterPlatformUserDTO registerPlatformUser) {
        Long userId = userService.registerPlatformUser(registerPlatformUser);
        return ResultUtilSimpleImpl.success(userId);
    }

    @ApiOperation(value = "通过用户名获取用户信息")
    @GetMapping(value = "/getUserByUsername")
    public ResResultVO<StructureAuthUser> getUserByUsername(@ApiParam(value = "用户名", example = "admin")
                                                            @RequestParam("username") String username) {
        User user = userService.loadUserByUserName(username);
        return ResultUtilSimpleImpl.success(UserAssembler.assemblerAuthUser(user));
    }

    @ApiOperation(value = "通过平台用户ID和CODE获取用户信息")
    @GetMapping(value = "/getUserByPlatformUserIdAndCode")
    public ResResultVO<StructureAuthUser> getUserByPlatformUserIdAndCode(@ApiParam(value = "平台ID", example = "18888888888")
                                                                         @RequestParam("platformUserId") String platformUserId,
                                                                         @ApiParam(value = "平台编码", example = "phone")
                                                                         @RequestParam("platformCode") String platformCode) {
        User user = userService.loadUserByPlatformUserIdAndPlatformCode(platformUserId, platformCode);
        return ResultUtilSimpleImpl.success(UserAssembler.assemblerAuthUser(user));
    }

    @ApiOperation(value = "获取用户权限")
    @GetMapping(value = "/getUserAuthorities/{userId}")
    public ResResultVO<List<String>> getUserAuthorities(@ApiParam(value = "用户ID", example = "18888888888")
                                                        @PathVariable("userId") Long userId) {
        return ResultUtilSimpleImpl.success(userService.getUserAuthorities(userId));
    }

    @ApiOperation(value = "查询用户角色")
    @GetMapping(value = "/getUserRole/{userId}")
    public ResResultVO<List<String>> getUserRole(@ApiParam(value = "用户ID", example = "18888888888")
                                                 @PathVariable("userId") Long userId) {
        return ResultUtilSimpleImpl.success(userService.getUserRole(userId).stream().map(Role::getCode).collect(Collectors.toList()));
    }

    @ApiOperation(value = "查询用户角色ids")
    @GetMapping(value = "/getUserRoleIds/{userId}")
    public ResResultVO<List<Long>> getUserRoleIds(@ApiParam(value = "用户ID", example = "18888888888")
                                                  @PathVariable("userId") Long userId) {
        return ResultUtilSimpleImpl.success(userService.getUserRole(userId).stream().map(Role::getId).collect(Collectors.toList()));
    }

    @ApiOperation(value = "重置密码")
    @PutMapping(value = "/resetPassword")
    public ResResultVO<Void> resetPassword(@RequestBody RestPasswordDTO restPasswordDto) {
        userService.resetPassword(restPasswordDto.getUserId(), restPasswordDto.getPassword());
        return ResultUtilSimpleImpl.success(null);
    }

    @ApiOperation(value = "启用", notes = "管理接口")
    @PutMapping(value = "/enable/{userId}")
    public ResResultVO<Void> enable(@ApiParam(value = "用户ID", example = "1645717015337684992")
                                    @PathVariable("userId") Long userId) {
        userService.enable(userId);
        return ResultUtilSimpleImpl.success(null);
    }

    @ApiOperation(value = "停用", notes = "管理接口")
    @PutMapping(value = "/disable/{userId}")
    public ResResultVO<Void> disable(@ApiParam(value = "用户ID", example = "1645717015337684992")
                                     @PathVariable("userId") Long userId) {
        userService.disable(userId);
        return ResultUtilSimpleImpl.success(null);
    }


    @ApiOperation(value = "分配角色")
    @PutMapping(value = "/assigningRole}")
    public ResResultVO<Void> assigningRole(@RequestBody @Validated AssigningRoleDTO assigningRoleDto) {
        userService.assigningRole(assigningRoleDto.getRoleIds(), assigningRoleDto.getUserId());
        return ResultUtilSimpleImpl.success(null);
    }


    @ApiOperation(value = "删除用户", notes = "管理接口")
    @DeleteMapping(value = "/{ids}")
    public ResResultVO<Void> remove(@ApiParam(value = "用户ID", example = "1645717015337684992")
                                    @PathVariable Set<Long> ids) {
        userService.removeByIds(ids);
        return ResultUtilSimpleImpl.success(null);
    }


}
