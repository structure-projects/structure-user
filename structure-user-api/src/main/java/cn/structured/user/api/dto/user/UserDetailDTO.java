package cn.structured.user.api.dto.user;

import cn.structured.user.api.dto.OptionDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户详情DTO
 *
 * @author chuck
 * @since JDK1.8
 */
@Data
@ApiModel(description = "用户详情-DTO")
public class UserDetailDTO {

    @ApiModelProperty(value = "用户ID")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "简介")
    private String intro;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "角色名称")
    private List<OptionDTO> role;

    @ApiModelProperty(value = "注册时间")
    private LocalDateTime createTime;

}
