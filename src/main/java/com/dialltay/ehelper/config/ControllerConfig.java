package com.dialltay.ehelper.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;


@ControllerAdvice
public class ControllerConfig {

    private final AssetConfig assetConfig;
    public ControllerConfig(final AssetConfig assetConfig) {
        this.assetConfig = assetConfig;
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute("asset")
    public Map<String, String> getAssets() {
        return this.assetConfig.getManifest();
    }
}
