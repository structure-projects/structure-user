package cn.structured.user.api.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 注册用户DTO
 *
 * @author chuck
 * @since JDK1.8
 */
@Data
@ApiModel(description = "注册用户-DTO")
public class RegisterUserDto {

    /**
     * 用户名
     */
    @NotBlank
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @NotBlank
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号",notes = "当type = phone时不能为空",example = "18888888888")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱",notes = "当type = email时不能为空",example = "18888888888@qq.com")
    private String email;

    /**
     * 类型
     */
    @NotBlank
    @ApiModelProperty(value = "类型")
    private String type;

    /**
     * 验证码
     */
    @NotBlank
    @ApiModelProperty(value = "验证码")
    private String code;

}
