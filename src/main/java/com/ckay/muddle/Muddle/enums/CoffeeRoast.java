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
    AMBROSIA ("Ambrosia", "#D7CCC8"),
    DANCING("Dancing Water", "#D7CCC8"),
    MANHATTAN("New Manhattan", "#D7CCC8");

    private final String displayName;
    private final String hexColor;

    CoffeeRoast(String displayName, String hexColor) {
        this.displayName = displayName;
        this.hexColor = hexColor;
    }

}
