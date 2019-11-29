package ua.com.crosp.solutions.hearthealthmonitor.infrastructure.implementation.mapper;

import junit.framework.Assert;

import org.junit.Test;

import ua.com.crosp.solutions.hearthealthmonitor.base.domain.valueobject.FullName;

/**
 * Created by Alexander Molochko on 10/30/17.
 * Project HeartHealthMonitor
 * Copyright (C) 2017 CROSP Solutions
 */
public class JsonMapperTest {
    @Test
    public void jsonObjectString_ObjectProvided_ShouldReturnJsonString() throws Exception {
        // Arrange
        FullName fullName = new FullName();
        fullName.setFirstName("Andrew");
        fullName.setLastName("Potapenko");
        JsonMapper jsonMapper = new JsonMapper();

        // Act
        String jsonString = jsonMapper.convertToJson(fullName);

        // Assert
        Assert.assertEquals("{\"mFirstName\":\"Andrew\",\"mLastName\":\"Potapenko\"}", jsonString);
    }

    @Test
    public void object_JsonStringProvided_ShouldReturnParsedObject() throws Exception {
        // Arrange
        String jsonObjectString = "{\"mFirstName\":\"Andrew\",\"mLastName\":\"Potapenko\"}";
        FullName fullName = new FullName();
        fullName.setFirstName("Andrew");
        fullName.setLastName("Potapenko");
        JsonMapper jsonMapper = new JsonMapper();

        // Act
        FullName parsedFullName = jsonMapper.convertFromJson(jsonObjectString, FullName.class);

        // Assert
        Assert.assertEquals(fullName.getFirstName(), parsedFullName.getFirstName());
        Assert.assertEquals(fullName.getLastName(), parsedFullName.getLastName());
    }

}