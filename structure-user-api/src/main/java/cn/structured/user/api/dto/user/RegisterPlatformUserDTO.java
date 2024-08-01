package cn.structured.user.api.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 注册平台用户
 *
 * @author chuck
 * @since JDK1.8
 */
@Data
@ApiModel(description = "注册平台用户-DTO")
public class RegisterPlatformUserDTO {

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 平台用户ID
     */
    @NotBlank
    @ApiModelProperty(value = "平台用户ID")
    private String platformUserId;

    /**
     * 平台类型
     */
    @NotBlank
    @ApiModelProperty(value = "平台类型")
    private String type;


}
