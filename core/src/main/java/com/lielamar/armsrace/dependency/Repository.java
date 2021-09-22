package com.lielamar.armsrace.dependency;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static com.lielamar.armsrace.bootstrap.Injector.Factory.sneakyThrow;

public final class Repository {

    public static final Repository MAVEN_CENTRAL = new Repository("https://repo1.maven.org/maven2/");

    private final URL url;

    public Repository(String url) {
        if (!url.endsWith("/")) url += '/';
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public URL getURL() {
        return url;
    }

    public URL getPom(@NotNull Dependency dependency) {
        try {
            return new URL(url + dependency.getMavenPath() + ".pom");
        } catch (MalformedURLException e) {
            throw sneakyThrow(e);
        }
    }

    public URL getJAR(@NotNull Dependency dependency) {
        try {
            if (dependency instanceof Dependency.URLDependency) {
                return ((Dependency.URLDependency) dependency).getUrl();
            }
            return new URL(url + dependency.getMavenPath() + ".jar");
        } catch (Throwable t) {
            throw sneakyThrow(t);
        }
    }

    private void download(@NotNull Dependency dependency, File file) {
        try {
            if (file.exists()) return;
            try (InputStream is = getJAR(dependency).openStream()) {
                Files.copy(is, file.toPath(), REPLACE_EXISTING);
            }
        } catch (Throwable throwable) {
            throw sneakyThrow(throwable);
        }
    }

    public static void download(URL url, File file) {
        try {
            if (file.exists()) return;
            try (InputStream is = url.openStream()) {
                Files.copy(is, file.toPath(), REPLACE_EXISTING);
            }
        } catch (Throwable t) {
            throw sneakyThrow(t);
        }
    }

    public void downloadFile(@NotNull Dependency dependency, @NotNull File directory) {
        try {
            for (Dependency transitive : dependency.getTransitiveDependencies(this)) {
                download(transitive, new File(directory, transitive.getName() + ".jar"));
            }
            download(dependency, new File(directory, dependency.getName() + ".jar"));
        } catch (Exception ignored) { // try another repository.
        }
    }

    public File downloadFile(Dependency dependency, File directory, DependencyData data, RelocationHandler handler) {
        try {
            for (Dependency transitive : dependency.getTransitiveDependencies(this)) {
                File input = new File(directory, transitive.getName() + ".jar");
                File output = new File(directory, transitive.getName() + "-remapped.jar");
                download(transitive, input);
                if (!data.getRelocations().isEmpty() && !output.exists()) {
                    output.createNewFile();
                    handler.remap(input, output, data.getRelocations());
                }
            }
            File input = new File(directory, dependency.getName() + ".jar");
            File output = new File(directory, dependency.getName() + "-remapped.jar");
            download(dependency, input);
            if (!data.getRelocations().isEmpty()) {
                if (!output.exists())
                    handler.remap(input, output, data.getRelocations());
                return output;
            }
            return input;
        } catch (Exception e) {
            return null; // try another repository.
        }
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Repository)) return false;
        final Repository other = (Repository) o;
        final Object this$url = this.url;
        final Object other$url = other.url;
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $url = this.url;
        result = result * PRIME + ($url == null ? 43 : $url.hashCode());
        return result;
    }

    public String toString() {return "Repository(url=" + this.url + ")";}
}