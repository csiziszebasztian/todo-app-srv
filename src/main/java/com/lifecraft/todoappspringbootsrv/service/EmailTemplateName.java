package com.lifecraft.todoappspringbootsrv.service;

public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account"),
    CONFIRM_EMAIL("confirm_email");

    private final String name;

    private EmailTemplateName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
