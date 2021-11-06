package com.example.evermos.demo.utils;

public class Helper {
    public String toRoman(Integer num) {
        final String[] ROMAN_LETTERS = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM",  "M" };
        final int[] ROMAN_NUMBERS    = {  1,    4,   5,    9,  10,   40,  50,   90, 100,  400, 500,  900, 1000 };
        StringBuilder result = new StringBuilder();
        for (int h = ROMAN_NUMBERS.length - 1; h >= 0; h--) {
            result.append(ROMAN_LETTERS[h].repeat(num / ROMAN_NUMBERS[h]));
            num = num % ROMAN_NUMBERS[h];
        }
        return result.toString();
    }
}
