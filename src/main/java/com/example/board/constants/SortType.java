package com.example.board.constants;

import lombok.Getter;

@Getter
public enum SortType {
    CREATED_AT("createdAt"),
    LATEST("latest"),
    MOST_REPLIES("replies");

    private final String value;

    SortType(String value) {
        this.value = value;
    }

    public static SortType fromString(String sortType) {
        for (SortType type : values()) {
            if (type.value.equalsIgnoreCase(sortType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid sort type: " + sortType);
    }
}
