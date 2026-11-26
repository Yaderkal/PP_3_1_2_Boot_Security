package web.Dto;

import java.util.Set;

public record UserRequest(
        String name,
        String middleName,
        String surName,
        String mail,
        String password,
        Set<Long> roleIds
) {}