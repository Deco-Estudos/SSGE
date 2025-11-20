package com.example.SEED.Perfil;

public enum NomePerfil {
    //No Spring Security, o sistema espera que as roles venham prefixadas com "ROLE_"
    ADM("ROLE_ADM"),
    RESPONSAVEL_SETOR("ROLE_RESPONSAVEL_SETOR"),
    RH("ROLE_RH"),
    DIRETOR("ROLE_DIRETOR");

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
