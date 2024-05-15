package com.lhjundi.domain.entities.book;

public enum BookStatus {
    AVAILABLE ("Available for loan"),
    CHECKED_OUT ("Borrowed");

    private final String label;

    BookStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
