package cn.structured.user.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标签DTO
 *
 * @author chuck
 * @version 2024/07/14 上午12:53
 * @since 1.8
 */
@Data
@ApiModel(description = "标签DTO")
public class TagDTO {

    @ApiModelProperty(value = "标签ID")
    private Long id;
    @ApiModelProperty(value = "标签名")
    private String tag;
}
