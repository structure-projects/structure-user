package cn.structured.oauth.user.api.dto.user;

import cn.structured.oauth.api.enums.VerificationCodeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 发送邮箱验证码
 *
 * @author chuck
 * @since JDK1.8
 */
@Data
@ApiModel(description = "发送邮箱验证码-DTO")
public class SendEmailCodeDTO {

    /**
     * 验证码类型
     */
    @NotNull
    @ApiModelProperty(value = "验证码类型")
    private VerificationCodeType codeType;

    /**
     * 邮箱地址
     */
    @NotBlank
    @ApiModelProperty(value = "邮箱地址")
    private String email;

}
