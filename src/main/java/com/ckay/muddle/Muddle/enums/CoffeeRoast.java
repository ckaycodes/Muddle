package com.ckay.muddle.Muddle.enums;


import lombok.Getter;

@Getter
public enum CoffeeRoast {
    JACOBS("Jacobs Wonderbar", "#3B2F2F"),
    ARABIC("Aromatic Arabic",  "#3B2F2F"),
    ETHER("Ether", "#3B2F2F"),
    ECSTATIC("Ecstatic", "#3B2F2F"),
    SILKEN("Silken Splendor", "#6D4C41"),
    TESORA("Tesora", "#6D4C41"),
    JULIES("Julies Ultimate", "#6D4C41"),
    PHILTERED("Philtered Soul", "#6D4C41"),
    AMBROSIA ("Ambrosia", "#C8B8B3"),
    DANCING("Dancing Water", "#C8B8B3"),
    MANHATTAN("New Manhattan", "#C8B8B3");

    private final String displayName;
    private final String hexColor;

    CoffeeRoast(String displayName, String hexColor) {
        this.displayName = displayName;
        this.hexColor = hexColor;
    }

}
