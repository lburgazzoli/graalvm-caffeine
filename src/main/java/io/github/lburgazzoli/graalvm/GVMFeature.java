package io.github.lburgazzoli.graalvm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import com.github.benmanes.caffeine.cache.Cache;
import org.apache.xbean.finder.ClassFinder;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeReflection;

public class GVMFeature implements Feature {

    private static void allowInstantiate(Class cl) {
        RuntimeReflection.register(cl);
        for (Constructor<?> c : cl.getConstructors()) {
            RuntimeReflection.register(c);
        }
    }

    private static void allowMethods(Class cl) {
        for (Method method : cl.getMethods()) {
            RuntimeReflection.register(method);
        }
    }

    private static void allowAll(Class cl) {
        allowInstantiate(cl);
        allowMethods(cl);
    }

    @Override
    public void beforeAnalysis(BeforeAnalysisAccess access) {
        try {
            ClassFinder finder = new ClassFinder(GVMFeature.class.getClassLoader());
            finder.findImplementations(Cache.class).forEach(GVMFeature::allowAll);

        } catch (Throwable t) {
            throw new RuntimeException("Unable to analyse classes", t);
        }
    }
}
