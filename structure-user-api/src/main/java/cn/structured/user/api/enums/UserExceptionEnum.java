package cn.structured.user.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chuck
 * @version 2024/08/01 下午8:53
 * @since 1.8
 */
@Getter
@AllArgsConstructor
public enum UserExceptionEnum {
    SMS_SERVER_ERROR("U001","短信服务异常"),
    SMS_SERVER_SEND_ERROR("U002","发送短信异常"),
    NOT_USER_ERROR("U003","用户不存在"),
    USER_EXPIRE_ERROR("U004","用户已过期"),
    USER_LOCK_ERROR("U005","用户已锁定"),
    USER_NOT_ENABLE("U006","用户未启用"),
    USER_PASSWORD_ERROR("U007","密码验证失败！"),
    EMAIL_SEND_ERROR("U008","短信发送失败！"),
    CODE_ERROR("U009","验证失败！"),
    ;
    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误描述
     */
    private final String message;
}
