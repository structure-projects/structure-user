package cn.structured.user.controller.web;

import cn.hutool.core.util.StrUtil;
import cn.structure.common.entity.ResResultVO;
import cn.structure.common.utils.ResultUtilSimpleImpl;
import cn.structured.mybatis.plus.starter.vo.ResPage;
import cn.structured.oauth.user.api.dto.OptionDTO;
import cn.structured.oauth.user.api.dto.role.BindingAuthorityDTO;
import cn.structured.oauth.user.api.dto.role.RoleDTO;
import cn.structured.oauth.user.api.vo.RoleVO;
import cn.structured.user.controller.assembler.RoleAssembler;
import cn.structured.user.entity.Role;
import cn.structured.user.service.IRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色控制器
 *
 * @author chuck
 * @since JDK1.8
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping(value = "/api/roles")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @ApiOperation(value = "新增角色")
    @PostMapping(value = "/")
    public ResResultVO<Long> add(@RequestBody @Validated RoleDTO roleDto) {
        Role role = RoleAssembler.assembler(roleDto);
        roleService.save(role);
        return ResultUtilSimpleImpl.success(role.getId());
    }

    @ApiOperation(value = "修改角色")
    @PutMapping(value = "/{roleId}")
    public ResResultVO<Void> update(@RequestBody @Validated RoleDTO roleDto,
                                    @ApiParam(value = "角色ID", example = "1645717015337684992")
                                    @PathVariable Long roleId) {
        Role role = RoleAssembler.assembler(roleDto);
        role.setId(roleId);
        roleService.updateById(role);
        return ResultUtilSimpleImpl.success(null);
    }

    @ApiOperation(value = "角色列表")
    @GetMapping(value = "/{page}/{pageSize}/page")
    public ResResultVO<ResPage<RoleVO>> page(@ApiParam(value = "页码", required = true, example = "1")
                                             @PathVariable("page") Long page,
                                             @ApiParam(value = "页大小", required = true, example = "20")
                                             @PathVariable("pageSize") Long pageSize,
                                             @ApiParam(value = "关键字", example = "ttt")
                                             @RequestParam(value = "keywords", required = false, defaultValue = "") String keywords) {

        LambdaQueryWrapper<Role> queryWrapper = Wrappers.<Role>lambdaQuery()
                .like(StrUtil.isNotBlank(keywords), Role::getName, StringPool.PERCENT + keywords + StringPool.PERCENT)
                .or()
                .like(StrUtil.isNotBlank(keywords), Role::getCode, StringPool.PERCENT + keywords + StringPool.PERCENT);

        Page<Role> pageParam = new Page<>(page, pageSize);
        pageParam.setOptimizeCountSql(false);

        IPage<Role> pageResult = roleService.page(pageParam, queryWrapper);
        return ResultUtilSimpleImpl.success(ResPage.convert(pageResult, RoleAssembler::assembler));
    }

    @ApiOperation(value = "绑定权限")
    @PostMapping(value = "/bindAuthorities")
    public ResResultVO<Void> update(@RequestBody @Validated BindingAuthorityDTO bindingAuthorityDto) {
        roleService.saveRoleMenu(bindingAuthorityDto);
        return ResultUtilSimpleImpl.success(null);
    }

    @ApiOperation(value = "角色详情")
    @GetMapping(value = "/{roleId}")
    public ResResultVO<RoleVO> get(@ApiParam(value = "角色ID", example = "1645717015337684992")
                                   @PathVariable("roleId") Long roleId) {
        Role role = roleService.getById(roleId);
        RoleVO resultRole = RoleAssembler.assembler(role);
        List<String> authorities = roleService.getAuthorities(roleId);
        resultRole.setAuthorities(authorities);
        return ResultUtilSimpleImpl.success(resultRole);
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping(value = "/{ids}")
    public ResResultVO<Void> remove(@ApiParam(value = "角色ID", example = "1645717015337684992")
                                    @PathVariable("ids") List<Long> ids) {
        roleService.removeByIds(ids);
        return ResultUtilSimpleImpl.success(null);
    }

    @ApiOperation(value = "启用")
    @PutMapping(value = "/enable/{roleId}")
    public ResResultVO<Void> enable(@ApiParam(value = "角色ID", example = "1645717015337684992")
                                    @PathVariable("roleId") Long roleId) {
        roleService.enable(roleId);
        return ResultUtilSimpleImpl.success(null);
    }

    @ApiOperation(value = "停用")
    @PutMapping(value = "/disable/{roleId}")
    public ResResultVO<Void> disable(@ApiParam(value = "角色ID", example = "1645717015337684992")
                                     @PathVariable("roleId") Long roleId) {
        roleService.disable(roleId);
        return ResultUtilSimpleImpl.success(null);
    }

    @ApiOperation(value = "角色列表")
    @GetMapping(value = "/options")
    public ResResultVO<List<OptionDTO>> option(@ApiParam(value = "关键字", example = "name")
                                               @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.<Role>lambdaQuery()
                .like(StrUtil.isNotBlank(keyword), Role::getName, "%" + keyword + "%")
                .like(StrUtil.isNotBlank(keyword), Role::getCode, "%" + keyword + "%");

        List<Role> pageResult = roleService.list(queryWrapper);
        return ResultUtilSimpleImpl.success(pageResult
                .stream()
                .map(RoleAssembler::assemblerOption)
                .collect(Collectors.toList()));
    }

}
