package dev.lecer.mystic.mysticlava.api;

import dev.lecer.mystic.mysticlava.core.MysticLava;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MysticLavaAPI {

    public String getPluginName() {
        return instance().getDescription().getName();
    }

    public String getVersion() {
        return instance().getDescription().getVersion();
    }

    public String getAuthors() {
        return String.join(", ", instance().getDescription().getAuthors());
    }

    public String getDescription() {
        return instance().getDescription().getDescription();
    }

    public String getWebsite() {
        return instance().getDescription().getWebsite();
    }

    private MysticLava instance() {
        return MysticLava.getInstance();
    }
}
