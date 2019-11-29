package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.util;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by Alexander Molochko on 10/30/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
public class MathUtilsTest {
    @Test
    public void validDeviation_TwoValuesAproxEqualProvided_ShouldReturnZeroDeviation() throws Exception {
        // Arrange
        long firstValue = 10000;
        long secondValue = 10006;

        // Act
        long deviation = MathUtils.getValueDeviationInPercentage(firstValue, secondValue);

        // Assert
        Assert.assertTrue(deviation < 5);
    }

    @Test
    public void validDeviation_TwoValuesAproxEqualProvided_ShouldReturnPercentageDeviation() throws Exception {
        // Arrange
        long firstValue = 10000;
        long secondValue = 23333;

        // Act
        long deviation = MathUtils.getValueDeviationInPercentage(firstValue, secondValue);

        // Assert
        Assert.assertFalse(deviation < 5);
    }
}