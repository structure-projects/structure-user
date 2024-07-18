package cn.structured.oauth.user.service.impl;

import cn.structured.mybatis.plus.starter.base.BaseServiceImpl;
import cn.structured.oauth.user.entity.UserTag;
import cn.structured.oauth.user.mapper.UserTagMapper;
import cn.structured.oauth.user.service.IUserTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author chuck
 * @version 2024/07/14 上午12:49
 * @since 1.8
 */
@Slf4j
@Service
public class UserTagServiceImpl extends BaseServiceImpl<UserTagMapper, UserTag> implements IUserTagService {
}
