package net.portic.library.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfigurationServiceTest {

    @Mock
    private Resource propertiesFileResource;
    @InjectMocks
    private ConfigurationService configurationService;

    @Test
    void testInitialize() throws IOException {
        when(propertiesFileResource.getInputStream()).thenReturn(getClass().getClassLoader().getResourceAsStream("message.properties"));

        configurationService.initialize();

        Properties properties = (Properties) ReflectionTestUtils.getField(configurationService, "properties");
        assertThat(properties).isNotNull();
        assertThat(properties).hasSize(13);
    }

    @Test
    void testInitializeWithIOException() throws IOException {
        when(propertiesFileResource.getInputStream()).thenThrow(new IOException());

        configurationService.initialize();

        Properties properties = (Properties) ReflectionTestUtils.getField(configurationService, "properties");
        assertThat(properties).isNotNull().isEmpty();
    }

    @Test
    void testGetPropertyValue() {
        Properties properties = new Properties();
        properties.setProperty("MSG001.prepare", "ICS2Preparer");
        ReflectionTestUtils.setField(configurationService, "properties", properties);

        String value = configurationService.getPropertyValue("MSG001.prepare");

        assertThat(value).isEqualTo("ICS2Preparer");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testGetPropertyValueWithNullKey(String key) {
        Properties properties = new Properties();
        properties.setProperty("MSG001.prepare", "ICS2Preparer");
        ReflectionTestUtils.setField(configurationService, "properties", properties);

        String value = configurationService.getPropertyValue(key);

        assertThat(value).isNull();
    }
}