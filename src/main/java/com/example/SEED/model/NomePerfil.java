package com.example.SEED.model;

public enum NomePerfil {
    ADM("ROLE_ADM"),
    RESPONSAVEL_SETOR("ROLE_RESPONSAVEL_SETOR"),
    RH("ROLE_RH");

    private final String role;

    NomePerfil(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    // Para melhor leitura
    public String getLabel() {
        return name().replace('_', ' ');
    }
}
