package com.lhjundi.domain.entities.book;

public enum BookGender {
    ACTION ("Adventure novels"),
    DRAMA ("Drama novels"),
    HISTORY("History books"),
    HORROR("Horror novels"),
    SCIENCE("Science books"),
    TECHNICAL("Technical literature");

    private final String label;

    BookGender(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
