package cn.structured.user.api.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 更改密码DTO
 *
 * @author chuck
 * @since JDK1.8
 */
@Data
@ApiModel(description = "更改密码-DTO")
public class ChangePasswordDTO {

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /**
     * 旧的密码
     */
    @NotBlank
    @ApiModelProperty(value = "旧的密码")
    private String oldPassword;

    /**
     * 新的密码
     */
    @NotBlank
    @ApiModelProperty(value = "新的密码")
    private String newPassword;


}
