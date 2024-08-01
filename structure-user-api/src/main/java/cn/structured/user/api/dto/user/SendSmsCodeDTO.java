package cn.structured.user.api.dto.user;

import cn.structured.oauth.api.enums.VerificationCodeType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 发送短信验证码
 *
 * @author chuck
 * @since JDK1.8
 */
@Data
@ApiModel(description = "发送短信验证码-DTO")
public class SendSmsCodeDTO {

    /**
     * 验证码类型
     */
    @NotNull
    @ApiModelProperty(value = "验证码类型")
    private VerificationCodeType codeType;

    /**
     * 手机号
     */
    @NotBlank
    @ApiModelProperty(value = "手机号")
    private String phone;

}
