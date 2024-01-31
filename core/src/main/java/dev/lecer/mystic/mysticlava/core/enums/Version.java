package dev.lecer.mystic.mysticlava.core.enums;

import dev.lecer.mystic.mysticlava.core.utils.LoggerUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Version {
    v1_8_R1,
    v1_8_R2,
    v1_8_R3,
    v1_9_R1,
    v1_9_R2,
    v1_10_R1,
    v1_11_R1,
    v1_12_R1,
    v1_13_R1,
    v1_13_R2,
    v1_14_R1,
    v1_15_R1,
    v1_16_R1,
    v1_16_R2,
    v1_16_R3,
    v1_17_R1,
    v1_18_R1,
    v1_18_R2,
    v1_19_R1,
    v1_19_R2,
    v1_19_R3,
    v1_20_R1,
    v1_20_R2,
    v1_20_R3;

    private static Version CURRENT;
    private static String STRING_VERSION;

    public static void bootstrap() {
        CURRENT = Version.fromString(getStringVersion());

        if (getCurrentVersion() != null)
            LoggerUtils.info("Server is running on %s version.".formatted(getCurrentVersion().name()));
    }

    private static String getStringVersion() {
        if (STRING_VERSION == null)
            STRING_VERSION = getBukkitPackageName().split("\\.")[3];
        
        return STRING_VERSION;
    }

    private static String getBukkitPackageName() {
        return Bukkit.getServer()
                .getClass()
                .getPackage()
                .getName();
    }
    
    @Nullable
    private static Version fromString(String version) {
        return version == null ? null : Arrays.stream(Version.values())
                .filter(value -> value.name().equalsIgnoreCase(version))
                .findFirst()
                .orElse(null);
    }

    public static Version getCurrentVersion() {
        return CURRENT;
    }

    public static boolean is(@NotNull Version version) {
        return Version.getCurrentVersion() == version;
    }

    public static boolean after(@NotNull Version version) {
        return Version.getCurrentVersion().ordinal() > version.ordinal();
    }

    public static boolean afterOrEqual(@NotNull Version version) {
        return Version.getCurrentVersion().ordinal() >= version.ordinal();
    }

    public static boolean before(@NotNull Version version) {
        return Version.getCurrentVersion().ordinal() < version.ordinal();
    }

    public static boolean beforeOrEqual(@NotNull Version version) {
        return Version.getCurrentVersion().ordinal() <= version.ordinal();
    }

    public static boolean supportsHex() {
        return afterOrEqual(Version.v1_16_R1);
    }
}