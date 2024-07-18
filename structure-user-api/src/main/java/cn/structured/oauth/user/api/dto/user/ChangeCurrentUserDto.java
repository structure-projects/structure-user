package cn.structured.oauth.user.api.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 变更用户DTO
 *
 * @author chuck
 * @since JDK1.8
 */
@Data
public class ChangeCurrentUserDto {

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "简介")
    private String intro;

}
