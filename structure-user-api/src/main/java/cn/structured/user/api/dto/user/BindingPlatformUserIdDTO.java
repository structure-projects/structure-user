package cn.structured.user.api.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 绑定用户DTO
 *
 * @author chuck
 * @since JDK1.8
 */
@Data
@ApiModel(description = "绑定用户-DTO")
public class BindingPlatformUserIdDTO {

    /**
     * 用户ID
     */
    @NotNull
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /**
     * 平台用户ID
     */
    @NotBlank
    @ApiModelProperty(value = "平台用户ID")
    private String platformUserId;

    /**
     * 平台编码
     */
    @NotBlank
    @ApiModelProperty(value = "平台编码")
    private String platformTypeCode;

}
