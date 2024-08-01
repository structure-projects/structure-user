package cn.structured.user.api.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户信息DTO
 *
 * @author chuck
 * @since JDK1.8
 */
@Data
@ApiModel(description = "用户信息-DTO")
public class UserInfoDTO {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "角色")
    private List<String> roles;

    @ApiModelProperty(value = "权限")
    private List<String> perms;

}
