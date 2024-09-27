package net.portic.library.domain.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
@Service
public class ConfigurationService {

    @Value("${properties.filepath}")
    private Resource propertiesFileResource;

    private final Properties properties = new Properties();

    @PostConstruct
    public void initialize() {
        try (InputStream inputStream = propertiesFileResource.getInputStream()) {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Error loading properties file", e);
        }
    }

    public String getPropertyValue(String key) {
        if (key == null || key.isEmpty()) {
            return null;
        } else {
            return properties.getProperty(key);
        }
    }
}
