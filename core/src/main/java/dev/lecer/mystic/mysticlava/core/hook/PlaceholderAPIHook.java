package dev.lecer.mystic.mysticlava.core.hook;

import dev.lecer.mystic.mysticlava.api.MysticLavaAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIHook extends PlaceholderExpansion {

    @NotNull
    @Override
    public String getIdentifier() {
        return MysticLavaAPI.getPluginName().toLowerCase();
    }

    @NotNull
    @Override
    public String getAuthor() {
        return MysticLavaAPI.getAuthors();
    }

    @NotNull
    @Override
    public String getVersion() {
        return MysticLavaAPI.getVersion();
    }
}
