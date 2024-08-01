package cn.structured.user.service;

import cn.structured.user.api.dto.user.SendEmailCodeDTO;
import cn.structured.user.api.dto.user.SendSmsCodeDTO;

/**
 * Code服务类
 *
 * @author chuck
 * @since JDK1.8
 */
public interface IAuthCodeService {

    /**
     * 发送短信验证码
     *
     * @param sendSmsCodeDto 发送短信DTO
     */
    void sendSmsCode(SendSmsCodeDTO sendSmsCodeDto);

    /**
     * 发送邮箱验证码
     *
     * @param sendEmailCodeDto 邮箱DTO
     */
    void sendEmailCode(SendEmailCodeDTO sendEmailCodeDto);

}
