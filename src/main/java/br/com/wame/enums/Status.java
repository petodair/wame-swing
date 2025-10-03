package br.com.wame.enums;

public enum Status {
    PENDING("Pendente"),
    PARTIAL("Parcial"),
    FINALIZED("Finalizado");

    private final String text;

    Status(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
