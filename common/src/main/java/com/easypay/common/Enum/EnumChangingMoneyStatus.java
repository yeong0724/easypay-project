package com.easypay.common.Enum;

public enum EnumChangingMoneyStatus {
    REQUESTED(0, "요청"),
    SUCCEEDED(1, "성공"),
    FAILED(2, "실패"),
    CANCELED(3, "취소");

    private final int code;

    private final String description;


    EnumChangingMoneyStatus(int code, String description) {
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
