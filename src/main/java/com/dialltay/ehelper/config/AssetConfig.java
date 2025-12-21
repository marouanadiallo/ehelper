package com.dialltay.ehelper.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Getter
@Component
public class AssetConfig {

    private final Map<String, String> manifest;
    private final static String MANIFEST_PATH = "manifest.json";
    private final static Map<String, String> DEFAULT_MANIFEST = Map.of(
            "main.css", "/main.bundle.css",
            "main.js", "/js/main.bundle.js",
            "users.js", "/js/users.bundle.js",
            "create-user", "/js/create-user.bundle.js"
    );

    private final static Logger LOGGER = LoggerFactory.getLogger(AssetConfig.class);

    public AssetConfig() {
        this.manifest = loadManifest();
    }

    private Map<String, String> loadManifest() {
        ClassPathResource resource = new ClassPathResource(AssetConfig.MANIFEST_PATH);
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = resource.getInputStream()) {
            return mapper.readValue(is, new TypeReference<>() {});
        } catch (FileNotFoundException e) {
            LOGGER.warn("⚠️  {} not found ! Using default manifest.", AssetConfig.MANIFEST_PATH);
            return AssetConfig.DEFAULT_MANIFEST;
        }
        catch (Exception e) {
            LOGGER.warn("⚠️  {} could not be loaded ! Using default manifest.", AssetConfig.MANIFEST_PATH);
            return AssetConfig.DEFAULT_MANIFEST;
        }
    }

    public String getAssetPath(String assetName) {
        return manifest.getOrDefault(assetName, "/"+assetName);
    }

}
