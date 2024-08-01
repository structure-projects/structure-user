package cn.structured.user.controller.assembler;

import cn.structured.user.api.dto.user.UserDetailDTO;
import cn.structured.user.entity.User;
import cn.structured.security.entity.StructureAuthUser;

/**
 * 用户装配器
 *
 * @author cqliut
 * @version 2023.0711
 * @since 1.0.1
 */
public class UserAssembler {

    /**
     * 私有构造为防止构建实例
     */
    private UserAssembler() {

    }

    public static UserDetailDTO assembler(User user) {
        UserDetailDTO userDetailDto = new UserDetailDTO();
        userDetailDto.setId(user.getId());
        userDetailDto.setUsername(user.getUsername());
        userDetailDto.setNickname(user.getNickName());
        userDetailDto.setAvatar(user.getAvatar());
        userDetailDto.setEmail(user.getEmail());
        userDetailDto.setIntro(user.getIntro());
        userDetailDto.setPhone(user.getPhone());
        userDetailDto.setSex(user.getSex());
        userDetailDto.setCreateTime(user.getCreateTime());
        return userDetailDto;
    }

    public static StructureAuthUser assemblerAuthUser(User user) {
        StructureAuthUser authUser = new StructureAuthUser();
        authUser.setId(user.getId());
        authUser.setUsername(user.getUsername());
        authUser.setPassword(user.getPassword());
        authUser.setEnable(user.getEnabled());
        authUser.setUnlocked(user.getUnlocked());
        authUser.setUnexpired(user.getUnexpired());
        return authUser;
    }

}
