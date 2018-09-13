package com.ef.constants;

public enum DurationEnum {
    HOURLY("hourly"),
    DAILY("daily");

    private static final String EXCEPTION_MSG = "The duration value is invalid";
    private String name;

    private DurationEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static DurationEnum findByName(String name) {
        for(DurationEnum eachValue: DurationEnum.values()) {
            if(eachValue.getName().equals(name)) return eachValue;
        }

        throw new IllegalArgumentException(EXCEPTION_MSG);
    }
}
