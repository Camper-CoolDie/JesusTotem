package com.cooldie.jesustotem.controllers;

public class ControllerJesus {
    public static final int COUNT = 3;
    public static final long COOLDOWN = 1680L;

    public static final long FADE_IN = 0L;
    public static final long HOLD = 0L;
    public static final long FADE_OUT = 840L;

    public static long fadeIn = FADE_IN;
    public static long hold = HOLD;
    public static long fadeOut = FADE_OUT;

    private long start = -1L;

    public void spawn() {
        start = System.currentTimeMillis();
    }

    public void despawn() {
        start = -1L;
    }

    public float getAlpha(long time) {
        if (start == -1L) {
            return 0f;
        }

        long timeDelta = time - start;
        long cooldownDelta = timeDelta % COOLDOWN;
        if (timeDelta >= COUNT * COOLDOWN) {
            despawn();
            return 0f;
        }

        if (cooldownDelta >= fadeIn + hold + fadeOut) {
            return 0f;
        } else if (cooldownDelta >= fadeIn + hold) {
            // fade-out
            return (fadeIn + hold + fadeOut - cooldownDelta) / (float) fadeOut;
        } else if (cooldownDelta >= fadeIn) {
            // hold
            return 1f;
        } else {
            // fade-in
            return cooldownDelta / (float) fadeIn;
        }
    }

    public int getIndex(long time) {
        if (start == -1L) {
            return 0;
        }

        long timeDelta = time - start;
        return (int) (timeDelta / COOLDOWN);
    }
}
