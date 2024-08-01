package cn.structured.user.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import cn.structure.common.constant.SymbolConstant;
import cn.structure.common.exception.CommonException;
import cn.structured.oauth.api.enums.PlatformCodeEnum;
import cn.structured.oauth.api.enums.VerificationCodeType;
import cn.structured.oauth.user.api.dto.user.SendEmailCodeDTO;
import cn.structured.oauth.user.api.dto.user.SendSmsCodeDTO;
import cn.structured.oauth.user.api.enums.UserExceptionEnum;
import cn.structured.user.configuration.SmsConfig;
import cn.structured.user.configuration.UserProperties;
import cn.structured.user.service.IAuthCodeService;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 认证码管理
 *
 * @author chuck
 * @since JDK1.8
 */
@Slf4j
@Service
public class AuthCodeServiceImpl implements IAuthCodeService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private SmsConfig smsConfig;

    @Resource
    private UserProperties userProperties;

    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.profiles.active}")
    private String profile;

    @Override
    public void sendSmsCode(SendSmsCodeDTO sendSmsCodeDto) {
        String phone = sendSmsCodeDto.getPhone();
        //生成验证码
        String verificationCode = generateVerificationCode();
        if ("dev".equals(profile)) {
            //将验证码存储到redis中
            String redisKey = PlatformCodeEnum.PHONE.getCode() + SymbolConstant.COLON + sendSmsCodeDto.getCodeType().getCode() + SymbolConstant.COLON + phone;
            redisTemplate.boundValueOps(redisKey).set("123456", 5, TimeUnit.MINUTES);
            return;
        }
        IAcsClient acsClient = SpringUtil.getBean(IAcsClient.class);
        if (null == acsClient) {
            //抛出异常
            throw new CommonException(UserExceptionEnum.SMS_SERVER_ERROR.getCode(), UserExceptionEnum.SMS_SERVER_ERROR.getMessage());
        }
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(smsConfig.getSignName());
        //必填:短信模板-可在短信控制台中找到

        request.setTemplateCode(getSmsTemplateCode(sendSmsCodeDto.getCodeType()));
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\"" + verificationCode + "\"}");
        //阿里返回值
        try {
            //发送短信验证
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            //发送验证码成功
            if ("OK".equals(sendSmsResponse.getCode())) {
                //将验证码存储到redis中
                String redisKey = PlatformCodeEnum.PHONE.getCode() + SymbolConstant.COLON + sendSmsCodeDto.getCodeType().getCode() + SymbolConstant.COLON + phone;
                redisTemplate.boundValueOps(redisKey).set(verificationCode, 5, TimeUnit.MINUTES);
            }
        } catch (ClientException e) {
            log.error("sms service send error -> {}", e.getMessage());
            //短信服务发送异常错误信息
            throw new CommonException(UserExceptionEnum.SMS_SERVER_SEND_ERROR.getCode(), UserExceptionEnum.SMS_SERVER_SEND_ERROR.getMessage());
        }
    }

    @Override
    public void sendEmailCode(SendEmailCodeDTO sendEmailCodeDto) {
        //验证码类型
        VerificationCodeType codeType = sendEmailCodeDto.getCodeType();
        String email = sendEmailCodeDto.getEmail();
        //生成验证码
        String verificationCode = generateVerificationCode();
        //发送邮箱验证
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("yunjida-mail@qq.com");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject(codeType.getName());
            mimeMessageHelper.setText(String.format(codeType.getText(), verificationCode));
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("send mail fail -> {}", e.getMessage());
            throw new CommonException(UserExceptionEnum.EMAIL_SEND_ERROR.getCode(), UserExceptionEnum.EMAIL_SEND_ERROR.getMessage());
        }
        String redisKey = PlatformCodeEnum.EMAIL.getCode() + SymbolConstant.COLON + sendEmailCodeDto.getCodeType().getCode() + SymbolConstant.COLON + email;
        redisTemplate.boundValueOps(redisKey).set(verificationCode, 5, TimeUnit.MINUTES);
    }

    /**
     * 获取模版类型
     *
     * @param codeType 验证码编码类型
     * @return {@link String}
     */
    private String getSmsTemplateCode(VerificationCodeType codeType) {
        switch (codeType) {
            case LOGIN:
                return userProperties.getSmsLoginTemplateCode();
            case REGISTER:
                return userProperties.getSmsRegisterTemplateCode();
            case RESET_PASSWORD:
                return userProperties.getSmsResetPasswordTemplateCode();
            default:
                throw new CommonException();
        }
    }

    /**
     * 生成验证码
     *
     * @return 返回验证码
     */
    private String generateVerificationCode() {
        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            verificationCode.append(random.nextInt(10));
        }
        return verificationCode.toString();
    }

}
