package cn.structured.oauth.user.controller.web;

import cn.structure.common.constant.SymbolConstant;
import cn.structure.common.entity.ResResultVO;
import cn.structure.common.exception.CommonException;
import cn.structure.common.utils.ResultUtilSimpleImpl;
import cn.structured.oauth.api.enums.PlatformCodeEnum;
import cn.structured.oauth.api.enums.VerificationCodeType;
import cn.structured.oauth.user.api.dto.user.*;
import cn.structured.oauth.user.entity.User;
import cn.structured.oauth.user.service.IUserService;
import cn.structured.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用户控制器
 *
 * @author chuck
 * @since JDK1.8
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @ApiOperation(value = "删除用户", notes = "管理接口")
    @DeleteMapping(value = "/{userId}")
    public ResResultVO<Void> remove(@ApiParam(value = "用户ID", example = "1645717015337684992") @PathVariable Long userId) {
        userService.removeById(userId);
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

    @ApiOperation(value = "锁定", notes = "管理接口")
    @PutMapping(value = "/lock/{userId}")
    public ResResultVO<Void> lock(@ApiParam(value = "用户ID", example = "1645717015337684992")
                                  @PathVariable("userId") Long userId) {
        userService.lock(userId);
        return ResultUtilSimpleImpl.success(null);
    }

    @ApiOperation(value = "解锁", notes = "管理接口")
    @PutMapping(value = "/unlock/{userId}")
    public ResResultVO<Void> unlock(@ApiParam(value = "用户ID", example = "1645717015337684992")
                                    @PathVariable("userId") Long userId) {
        userService.unlock(userId);
        return ResultUtilSimpleImpl.success(null);
    }

    @ApiOperation(value = "重置密码", notes = "管理接口")
    @PutMapping(value = "/resetPassword")
    public ResResultVO<Void> resetPassword(@RequestBody @Validated RestPasswordDto resetPassword) {
        userService.resetPassword(resetPassword.getUserId(), resetPassword.getPassword());
        return ResultUtilSimpleImpl.success(null);
    }

    @ApiOperation(value = "查询当前登录用户详情", notes = "全局有权限即可")
    @GetMapping(value = "/current")
    public ResResultVO<UserInfoDto> current() {
        return ResultUtilSimpleImpl.success(userService.currentUserInfo());
    }


    @ApiOperation(value = "通过用户ID查询用户详情")
    @GetMapping(value = "/get/{userId}")
    public ResResultVO<UserDetailDto> get(@ApiParam(value = "用户ID", example = "1645717015337684992")
                                          @PathVariable("userId") Long userId) {
        return ResultUtilSimpleImpl.success(userService.getUserDetailByUserId(userId));
    }


    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public ResResultVO<Long> register(@RequestBody @Validated RegisterUserDto registerUserDto) {
        Long userId = userService.registerUser(registerUserDto);
        return ResultUtilSimpleImpl.success(userId);
    }

    @ApiOperation(value = "变更当前用户信息", notes = "个人接口")
    @PutMapping(value = "/changeCurrent")
    public ResResultVO<Void> change(@RequestBody @Validated ChangeCurrentUserDto changeUserDto) {
        Long userId = SecurityUtils.getUserId();
        User oauthUser = new User();
        oauthUser.setId(userId);
        oauthUser.setNickName(changeUserDto.getNickname());
        oauthUser.setAvatar(changeUserDto.getAvatar());
        oauthUser.setSex(changeUserDto.getSex());
        oauthUser.setEmail(changeUserDto.getEmail());
        oauthUser.setPhone(changeUserDto.getPhone());
        oauthUser.setIntro(changeUserDto.getIntro());
        userService.updateById(oauthUser);
        return ResultUtilSimpleImpl.success(null);
    }

    @ApiOperation(value = "绑定平台用户ID", notes = "个人权限接口需要知道用户的ID")
    @PostMapping(value = "bindingPlatformUser")
    public ResResultVO<Void> bindingPlatformUser(@RequestBody @Validated BindingPlatformUserIdDto bindingPlatformUserIdDto) {
        userService.bindingPlatformUser(bindingPlatformUserIdDto);
        return ResultUtilSimpleImpl.success(null);
    }

    @ApiOperation(value = "修改密码", notes = "个人操作，管理员需要知道用户的密码不建议调用")
    @PostMapping(value = "/changePassword")
    public ResResultVO<Void> changePassword(@RequestBody ChangePasswordDto changePassword) {
        // token中获取用户ID
        Long userId = SecurityUtils.getUserId();
        userId = (null != userId) ? userId : changePassword.getUserId();
        userService.changePassword(userId, changePassword.getOldPassword(), changePassword.getNewPassword());
        return ResultUtilSimpleImpl.success(null);
    }

    @ApiOperation(value = "用户重置密码")
    @PutMapping(value = "/userResetPassword")
    public ResResultVO<Void> userResetPassword(@RequestBody @Validated UserRestPasswordDto resetPassword) {
        String code = resetPassword.getCode();
        String redisKey = PlatformCodeEnum.PHONE.getCode() + SymbolConstant.COLON + VerificationCodeType.RESET_PASSWORD.getCode() + SymbolConstant.COLON + resetPassword.getPhone();
        String cacheCode = redisTemplate.boundValueOps(redisKey).get();
        if (code.equals(cacheCode)) {
            //todo 验证失败
            throw new CommonException("", "");
        }
        //验证code
        userService.resetPassword(resetPassword.getId(), resetPassword.getPassword());
        return ResultUtilSimpleImpl.success(null);
    }


    @ApiOperation(value = "查询用户详情", notes = "全局有权限即可")
    @GetMapping(value = "/currentUserDetail")
    public ResResultVO<UserDetailDto> currentUserDetail() {
        Long userId = SecurityUtils.getUserId();
        return ResultUtilSimpleImpl.success(userService.getUserDetailByUserId(userId));
    }
}
