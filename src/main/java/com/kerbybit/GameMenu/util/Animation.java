package com.kerbybit.GameMenu.util;

public class Animation {
    public static float interp(float from, float to, float speed) {
        if (Math.floor(Math.abs(to-from)) != 0) {
            return from + (to-from)/speed;
        } else {
            return to;
        }
    }
}
