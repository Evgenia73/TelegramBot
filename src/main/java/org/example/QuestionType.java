package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum QuestionType {
    JS,
    HISTORY,
    MATH,
    MUSIC;


    @Override
    public String toString() {
        return switch (this) {
            case JS -> "JS";
            case HISTORY -> "История";
            case MATH -> "Математика";
            case MUSIC -> "Музыка";
        };
    }

    public static QuestionType valueFromString(String type) {
        return switch (type) {
            case "JS" -> JS;
            case "История" -> HISTORY;
            case "Математика" -> MATH;
            case "Музыка" -> MUSIC;
            default -> throw new ClassCastException();
        };
    }
}
