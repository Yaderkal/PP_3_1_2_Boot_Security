package web.Dto;

import web.model.Role;

public record RoleDto(Long id, String name) {

    public static RoleDto from(Role role) {

        return new RoleDto(role.getId(), role.getName());
    }
}
