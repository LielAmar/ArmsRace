package com.lielamar.armsrace.bootstrap;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * A simple interface for adding URLs to {@link URLClassLoader}s, without being
 * limited by Java 9+'s strict module reflection.
 */
public interface Injector {

    void insertURL(URL url) throws Throwable;

    final class Factory {

        public static Injector create(URLClassLoader classLoader) {
            try {
                Class.forName("java.lang.Module"); // Java 9
                return UnsafeInjectable.create(classLoader);
            } catch (Throwable throwable) {
                try {
                    Method mAddURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                    mAddURL.setAccessible(true);
                    MethodHandle addURL = MethodHandles.lookup().unreflect(mAddURL).bindTo(classLoader);
                    return addURL::invoke;
                } catch (Throwable throwable1) {
                    throw sneakyThrow(throwable1);
                }
            }
        }

        public static RuntimeException sneakyThrow(Throwable t) {
            if (t == null) throw new NullPointerException("t");
            return sneakyThrow0(t);
        }

        private static <T extends Throwable> T sneakyThrow0(Throwable t) throws T {
            throw (T) t;
        }
    }

}