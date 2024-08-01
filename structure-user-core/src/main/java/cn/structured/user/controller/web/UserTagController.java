package cn.structured.user.controller.web;

import cn.structure.common.entity.ResResultVO;
import cn.structure.common.utils.ResultUtilSimpleImpl;
import cn.structured.oauth.user.api.dto.TagDTO;
import cn.structured.oauth.user.api.dto.role.RoleDTO;
import cn.structured.user.entity.UserTag;
import cn.structured.user.service.IUserTagService;
import cn.structured.security.util.SecurityUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chuck
 * @version 2024/07/14 上午12:46
 * @since 1.8
 */
@Api(tags = "标签管理")
@RestController
@RequestMapping(value = "/api/user-tags")
public class UserTagController {

    @Resource
    private IUserTagService userTagService;

    @ApiOperation(value = "新增用户标签")
    @PostMapping(value = "/")
    public ResResultVO<Long> add(@RequestBody @Validated TagDTO tagDto) {
        Long userId = SecurityUtils.getUserId();
        UserTag userTag = new UserTag();
        userTag.setUserId(userId);
        userTag.setTag(tagDto.getTag());
        userTagService.save(userTag);
        return ResultUtilSimpleImpl.success(userTag.getId());
    }

    @ApiOperation(value = "删除用户标签")
    @DeleteMapping(value = "/{tagId}")
    public ResResultVO<Void> userDelete(@RequestBody @Validated RoleDTO roleDto,
                                        @ApiParam(value = "标签ID", example = "1645717015337684992")
                                        @PathVariable Long tagId) {
        Long userId = SecurityUtils.getUserId();
        userTagService.remove(Wrappers.<UserTag>lambdaQuery()
                .eq(UserTag::getUserId, userId)
                .eq(UserTag::getId, tagId));
        return ResultUtilSimpleImpl.success(null);
    }

    @ApiOperation(value = "获取用户标签")
    @GetMapping(value = "/get")
    public ResResultVO<List<TagDTO>> getUserTag() {
        Long userId = SecurityUtils.getUserId();
        List<UserTag> list = userTagService.list(Wrappers.<UserTag>lambdaQuery()
                .eq(UserTag::getUserId, userId));
        return ResultUtilSimpleImpl.success(list.stream().map(userTag -> {
            TagDTO tagDto = new TagDTO();
            tagDto.setId(userTag.getId());
            tagDto.setTag(userTag.getTag());
            return tagDto;
        }).collect(Collectors.toList()));
    }
}
