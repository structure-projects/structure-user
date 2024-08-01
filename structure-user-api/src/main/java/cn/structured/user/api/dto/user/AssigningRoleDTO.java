package cn.structured.user.api.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 分配角色DTO
 *
 * @author chuck
 * @version 2024/07/20 上午12:44
 * @since 1.8
 */
@Data
@ApiModel(description = "分配角色-DTO")
public class AssigningRoleDTO {

    @NotNull
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @NotNull
    @ApiModelProperty(value = "角色ID")
    private List<Long> roleIds;

}
