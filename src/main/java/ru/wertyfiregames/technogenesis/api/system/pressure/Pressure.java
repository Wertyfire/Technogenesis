package ru.wertyfiregames.technogenesis.api.system.pressure;

import net.minecraft.network.chat.Component;

public class Pressure {
    public static final String UNIT_NAME_STRING = "PU";

    public static final int INTS_IN_ONE_UNIT = 1000;
    public static final double UNITS_IN_ONE_INT = 0.001f;

    public static double intsToUnits(int ints) {
        return UNITS_IN_ONE_INT * ints;
    }

    public static int unitsToInts(double units) {
        return (int) Math.round(INTS_IN_ONE_UNIT * units);
    }

    public static Component getPressureForDisplay(int pressure) {
        return getPressureForDisplay(intsToUnits(pressure));
    }

    public static Component getPressureForDisplay(double pressure) {
        String stringValue = String.format("%.2f", pressure);

        String toOutput = stringValue + " " + UNIT_NAME_STRING;

        return Component.translatable("technogenesis.system.pressure.display", toOutput);
    }
}