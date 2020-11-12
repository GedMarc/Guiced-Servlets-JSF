package com.guicedee.guicedservlets.jsf.implementations;

import com.guicedee.guicedinjection.interfaces.IGuiceScanModuleInclusions;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

public class GuicedJSFModuleInclusions implements IGuiceScanModuleInclusions<GuicedJSFModuleInclusions> {
    @Override
    public @NotNull Set<String> includeModules() {
        Set<String> moduleScanningAllowed = new HashSet<>();
        moduleScanningAllowed.add("com.guicedee.guicedservlets.jsf");
        return moduleScanningAllowed;
    }
}
