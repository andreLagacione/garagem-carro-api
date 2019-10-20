package com.andrelagacione.garagemcarroapi.enums;

public enum TipoPessoa {
    VENDEDOR("vendedor"),
    CLIENTE("Cliente"),
    FUNCIONARIO("Funcionário");

    public final String label;

    private TipoPessoa(String label) {
        this.label = label;
    }
}
