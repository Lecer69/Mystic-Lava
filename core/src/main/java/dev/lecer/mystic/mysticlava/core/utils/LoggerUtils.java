package dev.lecer.mystic.mysticlava.core.utils;

import dev.lecer.mystic.mysticlava.api.MysticLavaAPI;
import dev.lecer.mystic.mysticlava.core.MysticLava;
import lombok.experimental.UtilityClass;

import java.util.logging.Level;
import java.util.logging.Logger;

@UtilityClass
public class LoggerUtils {

    private final Logger LOGGER = MysticLava.getInstance().getLogger();
    private final String PREFIX = "[%s]".formatted(MysticLavaAPI.getPluginName());

    public void info(String message) {
        LOGGER.info(PREFIX + message);
    }

    public void warning(String message) {
        warning(message, null);
    }

    public void warning(String message, Throwable throwable) {
        LOGGER.log(Level.WARNING, PREFIX + message, throwable);
    }

    public void error(String message) {
        error(message, null);
    }

    public void error(String message, Throwable throwable) {
        LOGGER.log(Level.SEVERE, PREFIX + message, throwable);
    }
}