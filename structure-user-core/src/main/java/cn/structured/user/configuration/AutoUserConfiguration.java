package cn.structured.user.configuration;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 用户自动装配类
 *
 * @author chuck
 * @since JDK1.8
 */
@Import(SpringUtil.class)
@ComponentScan(basePackages = "cn.structured.oauth.user.**")
public class AutoUserConfiguration {
}
