package io.github.lburgazzoli.graalvm.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class Application {
    private static final Cache<String, String> CACHE = Caffeine.newBuilder()
        //.executor(task -> ForkJoinPool.commonPool().execute(task))
        .build();

    public static void main(String[] args) {
        CACHE.put("key", "test");

        System.out.println("" + CACHE.getIfPresent("key"));
    }
}
