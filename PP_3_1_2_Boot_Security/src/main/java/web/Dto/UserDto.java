package web.Dto;

import web.model.User;

import java.util.Set;
import java.util.stream.Collectors;

// UserDto
public record UserDto(Long id, String name, String middleName, String surName,
                      String mail, Set<RoleDto> roles) {
    public static UserDto from(User user) {
        Set<RoleDto> roles = user.getRoles().stream()
                .map(RoleDto::from)
                .collect(Collectors.toSet());
        return new UserDto(user.getId(), user.getName(), user.getMiddleName(),
                user.getSurName(), user.getMail(), roles);
    }
}