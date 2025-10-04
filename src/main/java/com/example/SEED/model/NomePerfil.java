package com.example.SEED.model;

public enum NomePerfil {
    //No Spring Security, o sistema espera que as roles venham prefixadas com "ROLE_"
    ADM("ROLE_ADM"),
    RESPONSAVEL_SETOR("ROLE_RESPONSAVEL_SETOR"),
    RH("ROLE_RH");

    private final String role;

    NomePerfil(String role) {
        this.role = role;
    }
    /*
    Todo enum pode ter um construtor. Quando criamos NomePerfil(String role),
    cada constante do enum chama esse construtor passando seu próprio valor (ex: "ROLE_ADM").
    Dentro do construtor, usamos this.role = role para guardar esse valor no atributo role.
    EX: ADM = new NomePerfil("ROLE_ADM"), o ROLE_ADM é guardado no atributo role.
    A principal usabilidade disso é para conseguir usar o gerRole() no getAuthorityName()
    */

    public String getRole() {
        return role;
    }

    // Para melhor leitura
    public String getLabel() {
        return name().replace('_', ' ');
    }
}
