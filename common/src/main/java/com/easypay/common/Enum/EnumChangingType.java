package com.easypay.common.Enum;

public enum EnumChangingType {
    INCREASING(0, "증액"),
    DECREASING(1, "감액");

    private final int code;

    private final String description;

    EnumChangingType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return name();
    }
}
