package ru.generator.loanoffers.enums;

import java.util.Random;

public class RandomAnswerGenerator<T extends Enum<T>>{
    private static final Random random = new Random();
    private final T[] values;

    public RandomAnswerGenerator(Class<T> e) {
        values = e.getEnumConstants();
    }

    public T randomEnum() {
        return values[random.nextInt(values.length)];
    }
}
