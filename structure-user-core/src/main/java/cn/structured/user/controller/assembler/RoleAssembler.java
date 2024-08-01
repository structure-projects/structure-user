package cn.structured.user.controller.assembler;

import cn.structured.oauth.user.api.dto.OptionDTO;
import cn.structured.oauth.user.api.dto.role.RoleDTO;
import cn.structured.oauth.user.api.vo.RoleVO;
import cn.structured.user.entity.Role;

/**
 * 角色装配器
 *
 * @author cqliut
 * @version 2023.0713
 * @since 1.0.1
 */
public class RoleAssembler {

    private RoleAssembler() {
    }

    /**
     * 角色DTO装配成角色
     *
     * @param roleDto 创建角色
     * @return 角色实体对象
     */
    public static Role assembler(RoleDTO roleDto) {
        Role role = new Role();
        role.setName(roleDto.getName());
        role.setDataScope(roleDto.getDataScope());
        role.setCode(roleDto.getCode());
        role.setEnabled(roleDto.getEnabled());
        role.setAuthorities(roleDto.getAuthorities());
        return role;
    }

    /**
     * 角色实体装配为角色VO
     *
     * @param role 角色实体
     * @return 返回角色VO对象
     */
    public static RoleVO assembler(Role role) {
        RoleVO roleVo = new RoleVO();
        roleVo.setId(role.getId());
        roleVo.setDataScope(role.getDataScope());
        roleVo.setName(role.getName());
        roleVo.setCode(role.getCode());
        roleVo.setEnabled(role.getEnabled());
        roleVo.setOperator(role.getOperator());
        roleVo.setOperatorTime(role.getUpdateTime());
        return roleVo;
    }

    /**
     * 角色装配成下拉选择VO对象
     *
     * @param role 角色
     * @return {@link OptionDTO}
     */
    public static OptionDTO assemblerOption(Role role) {
        OptionDTO option = new OptionDTO();
        option.setId(role.getId());
        option.setLabel(role.getName());
        option.setValue(role.getCode());
        return option;
    }

}
