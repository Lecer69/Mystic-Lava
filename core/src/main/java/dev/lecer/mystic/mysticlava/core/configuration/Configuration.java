package dev.lecer.mystic.mysticlava.core.configuration;

import dev.lecer.mystic.mysticlava.core.MysticLava;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@ToString
public class Configuration {

    private final Map<String, Object> configMap;

    private final String fileName;
    private final String savePath;
    private final String localPath;

    public Configuration(String fileName, String savePath, String localPath) {
        this.fileName = fileName;
        this.savePath = savePath;
        this.localPath = localPath;
        this.configMap = new HashMap<>();

        this.bootstrap();
    }

    private void bootstrap() {
        final File file = new File(this.getSavePath());

        if (!file.exists()) this.save();
        this.load();
    }

    @SneakyThrows
    private void load() {
        final Yaml yaml = new Yaml();
        final InputStream inputStream = Files.newInputStream(Paths.get(this.getSavePath()));

        final Map<String, Object> load = yaml.load(inputStream);
        load.forEach((key, value) -> {
            if (value instanceof Map) {
                this.loadMap((LinkedHashMap<String, Object>) value, key);
                return;
            }

            this.configMap.put(key, value);
        });

        inputStream.close();
    }

    @SneakyThrows
    private void save() {
        @Cleanup final InputStream inputStream = MysticLava.class.getResourceAsStream(this.localPath);
        final File file = new File(this.getSavePath());

        final Path directory = Paths.get(this.getDir(this.getSavePath()));
        Files.createDirectories(directory);

        final URI uri = file.toURI();
        final Path path = Paths.get(uri);

        Files.write(path, new ArrayList<>(this.getLines(inputStream)), StandardCharsets.UTF_8);
    }

    @NotNull
    private String getDir(String path) {
        final String[] strings = path.split("/");
        final List<String> list = new ArrayList<>(Arrays.asList(strings));
        list.remove(list.size() - 1);

        return String.join("/", list);
    }

    public Object getObject(String path) {
        if (path == null) return null;

        final AtomicReference<Object> configValue = new AtomicReference<>();
        this.configMap.forEach((key, value) -> {

            if (!key.equalsIgnoreCase(path)) return;
            if (Objects.isNull(value)) return;

            configValue.set(value);
        });

        return configValue.get();
    }

    public <T> T get(String path, Class<T> type) {
        final Object object = this.getObject(path);
        return type.isInstance(object) ? type.cast(object) : null;
    }

    public <T> List<T> getList(String path, Class<T> type) {
        final Object object = this.getObject(path);
        return object instanceof List ? (List<T>) object : null;
    }

    private void loadMap(LinkedHashMap<String, Object> map, Object parent) {
        map.forEach((key, value) -> {
            final String formatted = String.format("%s.%s", parent, key);
            if (value instanceof Map) {
                this.loadMap((LinkedHashMap<String, Object>) value, formatted);
                return;
            }

            this.configMap.put(formatted, value);
        });
    }

    private String getSavePath() {
        final String path = this.savePath.endsWith("/") ? this.savePath : this.savePath + "/";
        return String.format("%s%s.yml", path, this.fileName);
    }

    private Collection<String> getLines(InputStream inputStream) {
        final ArrayList<String> arrays = new ArrayList<>();

        final Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) arrays.add(scanner.nextLine());
        scanner.close();

        return arrays;
    }
}
