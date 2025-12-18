package com.dialltay.ehelper.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class AssetConfig {

    private final Map<String, String> manifest;
    private final static String MANIFEST_PATH = "manifest.json";
    private final static Logger LOGGER = LoggerFactory.getLogger(AssetConfig.class);

    public AssetConfig() {
        this.manifest = loadManifest();
    }

    private Map<String, String> loadManifest() {
        try {
            ClassPathResource resource = new ClassPathResource(MANIFEST_PATH);
            ObjectMapper mapper = new ObjectMapper();
            try (InputStream is = resource.getInputStream()) {
                return mapper.readValue(is, new TypeReference<>() {});
            }
        } catch (IOException e) {
            LOGGER.error("⚠️  {} not found or invalid!", MANIFEST_PATH, e);
            throw new RuntimeException("Failed to load asset manifest", e);
        }

    }

    public String getAssetPath(String assetName) {
        return manifest.getOrDefault(assetName, "/"+assetName);
    }

    public Map<String, String> getManifest() {
        return manifest;
    }
}
