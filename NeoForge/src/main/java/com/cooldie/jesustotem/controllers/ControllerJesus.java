package com.cooldie.jesustotem.controllers;

import java.util.concurrent.atomic.AtomicReference;

public class ControllerJesus {
    public record Frame(float alpha, int index) {
        public static final Frame TRANSPARENT = new Frame(0f, 0);
    }

    private record State(long start, long pausedAt) {
        public static final State DEFAULT = new State(-1L, -1L);
    }

    public static final int COUNT = 3;
    public static final long COOLDOWN = 1680L;

    public static final long FADE_IN = 0L;
    public static final long HOLD = 0L;
    public static final long FADE_OUT = 840L;

    public static long fadeIn = FADE_IN;
    public static long hold = HOLD;
    public static long fadeOut = FADE_OUT;

    private AtomicReference<State> stateRef = new AtomicReference<>(State.DEFAULT);

    public void spawn(boolean paused) {
        long time = System.currentTimeMillis();
        stateRef.set(new State(time, paused ? time : -1L));
    }

    public void despawn() {
        stateRef.set(State.DEFAULT);
    }

    public void pause() {
        stateRef.updateAndGet(state -> {
            if (state.start() == -1L || state.pausedAt() != -1L) {
                return state;
            }

            long time = System.currentTimeMillis();
            return new State(state.start(), time);
        });
    }

    public void resume() {
        stateRef.updateAndGet(state -> {
            if (state.start() == -1L || state.pausedAt() == -1L) {
                return state;
            }

            long time = System.currentTimeMillis();
            long start = state.start() + (time - state.pausedAt());
            return new State(start, -1L);
        });
    }

    public Frame getFrame() {
        State state = stateRef.get();
        if (state.start() == -1L) {
            return Frame.TRANSPARENT;
        }

        long time = System.currentTimeMillis();
        long timeDelta = ((state.pausedAt() != -1L) ? state.pausedAt() : time) - state.start();
        long cooldownDelta = timeDelta % COOLDOWN;
        if (timeDelta >= COUNT * COOLDOWN) {
            despawn();
            return Frame.TRANSPARENT;
        }

        float alpha = getAlpha(cooldownDelta);
        return new Frame(alpha, (int) (timeDelta / COOLDOWN));
    }

    float getAlpha(long cooldownDelta) {
        if (cooldownDelta >= fadeIn + hold + fadeOut) {
            return 0f;
        } else if (cooldownDelta >= fadeIn + hold) {
            // Fade-out
            return (fadeIn + hold + fadeOut - cooldownDelta) / (float) fadeOut;
        } else if (cooldownDelta >= fadeIn) {
            // Hold
            return 1f;
        } else {
            // Fade-in
            return cooldownDelta / (float) fadeIn;
        }
    }
}
