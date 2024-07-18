package cn.structured.oauth.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author chuck
 * @since 2024-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.NONE)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 加密后的密码
     */
    @TableField("password")
    private String password;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 简介
     */
    @TableField("intro")
    private String intro;

    /**
     * 性别,N 未知,M 男 ,F 女
     */
    @TableField("sex")
    private String sex;

    /**
     * 电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 是否未过期 0：过期 1：未过期
     */
    @TableField("is_unexpired")
    private Boolean unexpired;

    /**
     * 是否启用 1:  启用 0:未启用
     */
    @TableField(value = "is_enabled", fill = FieldFill.INSERT)
    private Boolean enabled;

    /**
     * 是否未锁定 0:  锁定 1:未锁定
     */
    @TableField("is_unlocked")
    private Boolean unlocked;

    /**
     * 是否删除 0：未删除 1：删除
     */
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    private Boolean deleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
