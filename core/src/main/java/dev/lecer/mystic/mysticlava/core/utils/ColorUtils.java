package dev.lecer.mystic.mysticlava.core.utils;

import dev.lecer.mystic.mysticlava.core.enums.Version;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Pattern;

@UtilityClass
public class ColorUtils {

    private final Pattern HEX_COLOR_REGEX = Pattern.compile("(?<!\\\\)(\\{|&|)#((?:[0-9a-fA-F]{3}){2})(}|)");

    @SuppressWarnings("deprecation")
    public BaseComponent[] colorizeComponent(String message) {
        return message == null ? null : Version.supportsHex() ? TextComponent.fromLegacyText(toHex(message)) : TextComponent.fromLegacyText(colorize(message));
    }

    public String colorize(String message) {
        return message == null ? null : Version.supportsHex() ? toHex(message) : ChatColor.translateAlternateColorCodes('&', message);
    }

    private String toHex(String message) {
        if (message == null) return null;

        try {
            if (message.isEmpty()) return message;

            var letters = message.toCharArray();
            var bound = letters.length - 1;

            for (var index = 0; index < bound; index++) {
                if (letters[index] == '&' && ChatColor.ALL_CODES.indexOf(letters[index + 1]) > -1) {
                    letters[index] = ChatColor.COLOR_CHAR;
                    letters[index + 1] = Character.toLowerCase(letters[index + 1]);
                }
            }

            var result = new String(letters);
            var match = HEX_COLOR_REGEX.matcher(result);

            while (match.find()) {
                var matched = match.group();
                var colorCode = match.group(2);
                var replacement = "";

                try {
                    replacement = ChatColor.of("#" + colorCode).toString();
                } catch (IllegalArgumentException ignored) {
                }

                result = result.replaceAll(Pattern.quote(matched), replacement);
            }

            return result;
        } catch (Exception exception) {
            return ChatColor.translateAlternateColorCodes('&', message);
        }
    }

    public String stripColors(String message) {
        return message == null ? null : ChatColor.stripColor(toHex(message));
    }
}
