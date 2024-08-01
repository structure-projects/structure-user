package cn.structured.user.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色VO
 *
 * @author cqliut
 * @version 2023.0707
 * @since 1.0.1
 */
@Data
@ApiModel(description = "角色VO")
public class RoleVO {

    @ApiModelProperty(value = "角色ID", example = "1645717015337684992")
    private Long id;

    @ApiModelProperty(value = "角色名称", example = "管理员")
    private String name;

    @ApiModelProperty(value = "角色编码", example = "ADMIN")
    private String code;

    @ApiModelProperty(value = "数据权限", example = "2", notes = "数据权限(0-所有数据；1-部门及子部门数据；2-本部门数据；3-本人数据)")
    private Integer dataScope;

    @ApiModelProperty(value = "描述", example = "这个角色是一个管理员，用于管理用户角色和权限使用")
    private String remark;

    @ApiModelProperty(value = "启用/停用", example = "true", notes = "启用 true,停用 false")
    private Boolean enabled;

    @ApiModelProperty(value = "操作人", example = "张三")
    private String operator;

    @ApiModelProperty(value = "操作时间", example = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime operatorTime;

    @ApiModelProperty(value = "角色权限Code", example = "[1645717015337684992,1645717015337684993]")
    private List<String> authorities;

}
