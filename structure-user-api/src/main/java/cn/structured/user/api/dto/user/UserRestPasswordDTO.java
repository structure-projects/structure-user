package cn.structured.user.api.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 重置密码
 *
 * @author chuck
 * @since JDK1.8
 */
@Data
@ApiModel(description = "重置密码-DTO")
public class UserRestPasswordDTO {

    /**
     * 通过手机号验证
     */
    @NotBlank
    @ApiModelProperty(value = "通过手机号验证")
    private String phone;

    /**
     * 验证码
     */
    @NotBlank
    @ApiModelProperty(value = "验证码")
    private String code;

    /**
     * 密码
     */
    @NotBlank
    @ApiModelProperty(value = "密码")
    private String password;

}
