package com.lielamar.armsrace.dependency;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

/**
 * Handles class runtime relocation of packages in downloaded dependencies
 */
public class RelocationHandler {

    public static final Dependency RELOCATOR = new Dependency(
            "me.lucko",
            "jar-relocator",
            "1.4"
    );

    private static final String JAR_RELOCATOR_CLASS = "me.lucko.jarrelocator.JarRelocator";
    private static final String JAR_RELOCATOR_RUN_METHOD = "run";

    private final Constructor<?> jarRelocatorConstructor;
    private final Method jarRelocatorRunMethod;

    public RelocationHandler(URL[] urls) {
        try {
            IsolatedClassLoader classLoader = new IsolatedClassLoader(urls);
            Class<?> jarRelocatorClass = classLoader.loadClass(JAR_RELOCATOR_CLASS);
            jarRelocatorConstructor = jarRelocatorClass.getDeclaredConstructor(File.class, File.class, Map.class);
            jarRelocatorConstructor.setAccessible(true);

            jarRelocatorRunMethod = jarRelocatorClass.getDeclaredMethod(JAR_RELOCATOR_RUN_METHOD);
            jarRelocatorRunMethod.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void remap(File input, File output, Map<String, String> relocations) throws Exception {
        output.createNewFile();
        // create and invoke a new relocator
        Object relocator = jarRelocatorConstructor.newInstance(input, output, relocations);
        jarRelocatorRunMethod.invoke(relocator);
    }

    private static class IsolatedClassLoader extends URLClassLoader {

        static {
            ClassLoader.registerAsParallelCapable();
        }

        public IsolatedClassLoader(URL[] urls) {
            /*
             * ClassLoader#getSystemClassLoader returns the AppClassLoader
             *
             * Calling #getParent on this returns the ExtClassLoader (Java 8) or
             * the PlatformClassLoader (Java 9). Since we want this classloader to
             * be isolated from the Minecraft server (the app), we set the parent
             * to be the platform class loader.
             */
            super(urls, ClassLoader.getSystemClassLoader().getParent());
        }

    }

}