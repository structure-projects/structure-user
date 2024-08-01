package cn.structured.oauth.user.api.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 重置密码
 *
 * @author chuck
 * @since JDK1.8
 */
@Data
@ApiModel(description = "重置密码-DTO")
public class RestPasswordDTO {

    /**
     * 用户ID
     */
    @NotNull
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /**
     * 密码
     */
    @NotBlank
    @ApiModelProperty(value = "密码")
    private String password;

}
