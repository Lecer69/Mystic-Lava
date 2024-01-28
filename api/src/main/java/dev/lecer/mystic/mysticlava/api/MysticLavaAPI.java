package dev.lecer.mystic.mysticlava.api;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MysticLavaAPI {

    public String getPluginName() {
        return MysticLavaPlugin.getInstance().getDescription().getName();
    }

    public String getVersion() {
        return MysticLavaPlugin.getInstance().getDescription().getVersion();
    }

    public String getAuthors() {
        return String.join(", ", MysticLavaPlugin.getInstance().getDescription().getAuthors());
    }

    public String getDescription() {
        return MysticLavaPlugin.getInstance().getDescription().getDescription();
    }

    public String getWebsite() {
        return MysticLavaPlugin.getInstance().getDescription().getWebsite();
    }
}
