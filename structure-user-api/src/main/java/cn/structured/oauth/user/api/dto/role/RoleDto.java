package cn.structured.oauth.user.api.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 创建角色
 *
 * @author cqliut
 * @version 2023.0706
 * @since 1.0.1
 */
@Data
@ApiModel(description = "创建角色")
public class RoleDto {

    @ApiModelProperty(value = "角色名称", example = "管理员")
    private String name;

    @ApiModelProperty(value = "角色编码", example = "ADMIN")
    private String code;

    @ApiModelProperty(value = "角色类型", example = "2", notes = "系统角色：1（系统角色不运行用户创建和修改）,用户角色：2")
    private String type;

    @ApiModelProperty(value = "数据权限", example = "2", notes = "数据权限(0-所有数据；1-部门及子部门数据；2-本部门数据；3-本人数据)")
    private Integer dataScope;

    @ApiModelProperty(value = "描述", example = "这个角色是一个管理员，用于管理用户角色和权限使用")
    private String remark;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @ApiModelProperty(value = "角色权限ID", example = "[1645717015337684992,1645717015337684993]")
    private List<String> authorities;
}
