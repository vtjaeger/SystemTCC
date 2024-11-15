package com.tcc.models;

public enum UserRole {
    ADMIN("admin"),
    ALUNO("aluno"),
    COORDENADOR("coordeador"),
    PROFESSOR("professor");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
