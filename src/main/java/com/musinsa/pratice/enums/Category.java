package com.musinsa.pratice.enums;

public enum Category {
    TOP("상의"),
    OUTER("아우터"),
    BOTTOM("바지"),
    SNEAKERS("스니커즈"),
    BAG("가방"),
    HAT("모자"),
    SOCKS("양말"),
    ACCESSORIES("악세서리");

    public final String label;

    Category(String label) {
        this.label = label;
    }

    public static Boolean isNotExist(String name) {
        try {
            Category.valueOf(name.toUpperCase());
        } catch (Exception e) {
            return true;
        }

        return false;
    }

}
