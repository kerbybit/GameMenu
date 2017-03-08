package com.kerbybit.GameMenu;

public class Animation {
    public static float interp(float from, float to, float speed, float step) {
        if (Math.floor(Math.abs(to-from)/step) != 0) {
            return from + (to-from)/speed;
        } else {
            return to;
        }
    }
}
