package ru.ifmo.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogTableDTO {
    private double argument;
    private double targetValue;
    private double lnValue;
}
