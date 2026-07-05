package com.cooldie.jesustotem.screens;

import com.cooldie.jesustotem.controllers.ControllerConfig;
import com.cooldie.jesustotem.controllers.ControllerJesus;
import java.util.function.LongFunction;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends Screen {
    private static final Component TITLE = Component.translatable("options.jesustotem.title");
    private static final Component FADE_IN = Component.translatable("options.jesustotem.fadeIn");
    private static final Component FADE_IN_TOOLTIP = Component.translatable("options.jesustotem.fadeIn.tooltip");
    private static final Component HOLD = Component.translatable("options.jesustotem.hold");
    private static final Component HOLD_TOOLTIP = Component.translatable("options.jesustotem.hold.tooltip");
    private static final Component FADE_OUT = Component.translatable("options.jesustotem.fadeOut");
    private static final Component FADE_OUT_TOOLTIP = Component.translatable("options.jesustotem.fadeOut.tooltip");
    private static final Component RESET_ALL = Component.translatable("options.jesustotem.resetAll");

    private final ConfigScreen.DurationSliderButton fadeInButton = new ConfigScreen.DurationSliderButton(
        0, 0,
        200, 20,
        FADE_IN,
        Tooltip.create(FADE_IN_TOOLTIP),
        value -> updateValue(0, value),
        ControllerJesus.fadeIn,
        ControllerJesus.FADE_IN
    );
    private final ConfigScreen.DurationSliderButton holdButton = new ConfigScreen.DurationSliderButton(
        0, 0,
        200, 20,
        HOLD,
        Tooltip.create(HOLD_TOOLTIP),
        value -> updateValue(1, value),
        ControllerJesus.hold,
        ControllerJesus.HOLD
    );
    private final ConfigScreen.DurationSliderButton fadeOutButton = new ConfigScreen.DurationSliderButton(
        0, 0,
        200, 20,
        FADE_OUT,
        Tooltip.create(FADE_OUT_TOOLTIP),
        value -> updateValue(2, value),
        ControllerJesus.fadeOut,
        ControllerJesus.FADE_OUT
    );

    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 61, 33);
    private final Screen parent;

    public ConfigScreen(Screen parent) {
        super(TITLE);
        this.parent = parent;
    }

    @Override
    protected void init() {
        LinearLayout contents = layout.addToContents(LinearLayout.vertical().spacing(4));
        contents.addChild(fadeInButton);
        contents.addChild(holdButton);
        contents.addChild(fadeOutButton);

        LinearLayout footer = layout.addToFooter(LinearLayout.horizontal().spacing(8));
        footer.addChild(Button.builder(RESET_ALL, button -> {
            fadeInButton.resetValue();
            holdButton.resetValue();
            fadeOutButton.resetValue();
        }).build());
        footer.addChild(Button.builder(CommonComponents.GUI_DONE, button -> onClose()).build());

        layout.addTitleHeader(title, font);
        layout.visitWidgets(eventListener -> addRenderableWidget(eventListener));

        repositionElements();
    }

    @Override
    protected void repositionElements() {
        layout.arrangeElements();
    }

    @Override
    public void removed() {
        ControllerJesus.fadeIn = fadeInButton.getValue();
        ControllerJesus.hold = holdButton.getValue();
        ControllerJesus.fadeOut = fadeOutButton.getValue();

        ControllerConfig.save();
    }

    @Override
    public void onClose() {
        minecraft.setScreen(parent);
    }

    private long updateValue(int buttonIndex, long value) {
        long fadeInValue = buttonIndex == 0 ? value : fadeInButton.getValue();
        long holdValue = buttonIndex == 1 ? value : holdButton.getValue();
        long fadeOutValue = buttonIndex == 2 ? value : fadeOutButton.getValue();

        if (fadeInValue + holdValue + fadeOutValue > ControllerJesus.COOLDOWN) {
            switch (buttonIndex) {
                case 0:
                    return ControllerJesus.COOLDOWN - (holdValue + fadeOutValue);
                case 1:
                    return ControllerJesus.COOLDOWN - (fadeInValue + fadeOutValue);
                case 2:
                    return ControllerJesus.COOLDOWN - (fadeInValue + holdValue);
            }
        }

        return value;
    }

    private static final class DurationSliderButton extends AbstractSliderButton {
        private final Component caption;
        private final LongFunction<Long> onValueUpdate;
        private final double defaultValue;

        DurationSliderButton(
            int x, int y,
            int width, int height,
            Component caption,
            Tooltip tooltip,
            LongFunction<Long> onValueUpdate,
            long initialValue,
            long defaultValue
        ) {
            super(x, y, width, height, CommonComponents.EMPTY, initialValue / (float) ControllerJesus.COOLDOWN);
            this.caption = caption;
            this.onValueUpdate = onValueUpdate;
            this.defaultValue = defaultValue / (float) ControllerJesus.COOLDOWN;
            updateMessage();
            setTooltip(tooltip);
        }

        @Override
        protected void updateMessage() {
            setMessage(value == 0f
                ? Component.translatable("options.generic_value", caption, CommonComponents.OPTION_OFF)
                : Component.translatable(
                    "options.jesustotem.percent_and_millisecond_value",
                    caption,
                    (int) (value * 100),
                    getValue()
                ));
        }

        @Override
        protected void applyValue() {
            value = onValueUpdate.apply(getValue()) / (float) ControllerJesus.COOLDOWN;
        }

        public long getValue() {
            return (long) (value * ControllerJesus.COOLDOWN);
        }

        public void resetValue() {
            if (value != defaultValue) {
                value = defaultValue;
                applyValue();
            }

            updateMessage();
        }
    }
}
