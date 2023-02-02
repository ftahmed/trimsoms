package me.ftahmed.bootify.model;


public enum Role {

    USER("User"),
    ADMIN("Admin");

    private final String label;

    private Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    // @Override
    // public String toString() {
    //     return this.label;
    // }
}