package com.lielamar.armsrace.bootstrap;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import static com.lielamar.armsrace.bootstrap.Injector.Factory.sneakyThrow;

public final class UnsafeInjectable implements Injector {

    private final ArrayDeque<URL> unopenedURLs;
    private final ArrayList<URL> pathURLs;

    public UnsafeInjectable(final ArrayDeque<URL> unopenedURLs, final ArrayList<URL> pathURLs) {
        this.unopenedURLs = unopenedURLs;
        this.pathURLs = pathURLs;
    }

    public static Injector create(final URLClassLoader classLoader) {
        try {
            final Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            final Unsafe unsafe = (Unsafe) field.get(null);
            final Object ucp = fetchField(unsafe, URLClassLoader.class, classLoader, "ucp");
            final ArrayDeque<URL> unopenedURLs = (ArrayDeque<URL>) fetchField(unsafe, ucp, "unopenedUrls");
            final ArrayList<URL> pathURLs = (ArrayList<URL>) fetchField(unsafe, ucp, "path");
            return new UnsafeInjectable(unopenedURLs, pathURLs);
        } catch (Throwable t) {
            throw sneakyThrow(t);
        }
    }

    private static Object fetchField(final Unsafe unsafe, final Object object, final String name) throws NoSuchFieldException {
        return fetchField(unsafe, object.getClass(), object, name);
    }

    private static Object fetchField(final Unsafe unsafe, final Class<?> clazz, final Object object, final String name) throws NoSuchFieldException {
        final Field field = clazz.getDeclaredField(name);
        final long offset = unsafe.objectFieldOffset(field);
        return unsafe.getObject(object, offset);
    }

    @Override public void insertURL(URL url) {
        unopenedURLs.addLast(url);
        pathURLs.add(url);
    }
}