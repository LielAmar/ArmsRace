package com.lielamar.armsrace.dependency;

import lombok.NonNull;
import com.lielamar.armsrace.bootstrap.Injector;

import java.io.File;
import java.util.*;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Collections.unmodifiableMap;

public class DependencyData {

    private static final String RELOCATION_PREFIX = "armsrace.libs.";

    private final Collection<Dependency> dependencies;
    private final Collection<Repository> repositories;
    private final Map<String, String> relocations;

    public DependencyData(Collection<Dependency> dependencies, Collection<Repository> repositories, Map<String, String> relocations) {
        this.dependencies = unmodifiableCollection(dependencies);
        this.repositories = unmodifiableCollection(repositories);
        this.relocations = unmodifiableMap(relocations);
    }

    public void load(File directory, RelocationHandler relocationHandler, Injector classLoader) throws Throwable {
        directory.mkdirs();
        for (Dependency dependency : getDependencies()) {
            boolean downloaded = false;
            for (Repository repository : getRepositories()) {
                File f = repository.downloadFile(dependency, directory, this, relocationHandler);
                if (f != null) {
                    classLoader.insertURL(f.toURI().toURL());
                    downloaded = true;
                    break;
                }
            }
            if (!downloaded) {
                StringJoiner repositories = new StringJoiner("\n- ", "\n- ", "").setEmptyValue("[None]");
                getRepositories().forEach(r -> repositories.add(r.getURL().toString()));
                String name = dependency.getGroupId() + ":" + dependency.getArtifactId() + ":" + dependency.getVersion();
                throw new IllegalArgumentException("Could not download dependency " + name + " from the specified repositories: " + repositories);
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Set<Dependency> dependencies = new LinkedHashSet<>();
        private final Set<Repository> repositories = new LinkedHashSet<>();
        private final Map<String, String> relocations = new LinkedHashMap<>();

        public Builder repository(@NonNull String repository) {
            repositories.add(new Repository(repository));
            return this;
        }

        public Builder dependency(@NonNull Dependency dependency) {
            dependencies.add(dependency);
            return this;
        }

        public Builder dependencyFromURL(@NonNull String url, @NonNull String artifactId, @NonNull String version) {
            dependencies.add(new Dependency.URLDependency(url, artifactId, version));
            return this;
        }

        public Builder dependency(@NonNull String groupId, @NonNull String artifactId, @NonNull String version) {
            return dependency(new Dependency(groupId, artifactId, version));
        }

        public Builder rawDependency(@NonNull String groupId, @NonNull String artifactId, @NonNull String version) {
            return dependency(new Dependency.RawDependency(groupId, artifactId, version));
        }

        public Builder dependency(@NonNull String gradleFormat) {
            String[] parts = gradleFormat.split(":");
            return dependency(parts[0], parts[1], parts[2]);
        }

        public Builder relocate(@NonNull String pattern, @NonNull String relocatedPattern) {
            relocations.put(pattern.replace('#', '.').replace("|", ""), RELOCATION_PREFIX + relocatedPattern
                    .replace("|", ""));
            return this;
        }

        public DependencyData build() {
            repositories.add(Repository.MAVEN_CENTRAL);
            return new DependencyData(dependencies, repositories, relocations);
        }

    }

    public Collection<Dependency> getDependencies() {return this.dependencies;}

    public Collection<Repository> getRepositories() {return this.repositories;}

    public Map<String, String> getRelocations() {return this.relocations;}
}