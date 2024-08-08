package org.example.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {
    public char toAlphabetChar(int index) {
        return index < 0 || index > 25 ? '?' : (char) ('A' + index);
    }
}
