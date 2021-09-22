package com.lielamar.armsrace.dependency;

import lombok.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a runtime-downloaded dependency
 */
public class Dependency {

    private static final String MAVEN_FORMAT = "%s/%s/%s/%s-%s";

    private final String groupId, artifactId, version;

    private final String mavenPath, name;

    public Dependency(String groupId, String artifactId, String version) {
        groupId = groupId.replace('#', '.').replace("|", "");
        artifactId = artifactId.replace('#', '.').replace("|", "");
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        mavenPath = String.format(MAVEN_FORMAT, groupId.replace('.', '/'), artifactId, version, artifactId, version);
        name = artifactId + "-" + version;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public String getMavenPath() {
        return mavenPath;
    }

    public String getName() {
        return name;
    }

    public List<Dependency> getTransitiveDependencies(Repository repository) {
        List<Dependency> dependencies = new ArrayList<>();
        try (InputStream stream = repository.getPom(this).openStream()) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(stream);
            NodeList list = doc.getDocumentElement().getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeName().equals("dependencies")) {
                    NodeList deps = node.getChildNodes();
                    for (int j = 0; j < deps.getLength(); j++) {
                        Node n = deps.item(j);
                        if (!(n instanceof Element)) continue;
                        Element dependency = (Element) n;
                        String groupId = dependency.getElementsByTagName("groupId").item(0).getTextContent();
                        String artifactId = dependency.getElementsByTagName("artifactId").item(0).getTextContent();
                        String version = "";
                        String scope = "";
                        try {
                            version = dependency.getElementsByTagName("version").item(0).getTextContent();
                            scope = dependency.getElementsByTagName("scope").item(0).getTextContent();
                        } catch (NullPointerException ignored) {
                        }
                        if (groupId.equals("${project.groupId}")) groupId = this.groupId;
                        if (version.equals("${project.version}")) version = this.version;
                        if (scope.equals("compile") && !version.isEmpty()) {
                            dependencies.add(new Dependency(groupId, artifactId, version));
                        }
                    }
                    break;
                }
            }
        } catch (Throwable e) {
            if (!(e instanceof FileNotFoundException)) e.printStackTrace();
        }
        return dependencies;
    }

    @Getter
    public static class URLDependency extends Dependency {

        private final URL url;

        public URLDependency(String url, String artifactId, String version) {
            super(artifactId, artifactId, version);
            try {
                this.url = new URL(url);
            } catch (MalformedURLException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override public List<Dependency> getTransitiveDependencies(Repository repository) {
            return Collections.emptyList();
        }

        public URL getUrl() {return this.url;}
    }

    public static class RawDependency extends Dependency {

        public RawDependency(String groupId, String artifactId, String version) {
            super(groupId, artifactId, version);
        }

        @Override public List<Dependency> getTransitiveDependencies(Repository repository) {
            return Collections.emptyList();
        }
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Dependency)) return false;
        final Dependency other = (Dependency) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$groupId = this.getGroupId();
        final Object other$groupId = other.getGroupId();
        if (this$groupId == null ? other$groupId != null : !this$groupId.equals(other$groupId)) return false;
        final Object this$artifactId = this.getArtifactId();
        final Object other$artifactId = other.getArtifactId();
        if (this$artifactId == null ? other$artifactId != null : !this$artifactId.equals(other$artifactId))
            return false;
        final Object this$version = this.getVersion();
        final Object other$version = other.getVersion();
        if (this$version == null ? other$version != null : !this$version.equals(other$version)) return false;
        final Object this$mavenPath = this.getMavenPath();
        final Object other$mavenPath = other.getMavenPath();
        if (this$mavenPath == null ? other$mavenPath != null : !this$mavenPath.equals(other$mavenPath)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {return other instanceof Dependency;}

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $groupId = this.getGroupId();
        result = result * PRIME + ($groupId == null ? 43 : $groupId.hashCode());
        final Object $artifactId = this.getArtifactId();
        result = result * PRIME + ($artifactId == null ? 43 : $artifactId.hashCode());
        final Object $version = this.getVersion();
        result = result * PRIME + ($version == null ? 43 : $version.hashCode());
        final Object $mavenPath = this.getMavenPath();
        result = result * PRIME + ($mavenPath == null ? 43 : $mavenPath.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {return "Dependency(groupId=" + this.getGroupId() + ", artifactId=" + this.getArtifactId() + ", version=" + this.getVersion() + ", mavenPath=" + this.getMavenPath() + ", name=" + this.getName() + ")";}
}