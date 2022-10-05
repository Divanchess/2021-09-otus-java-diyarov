package ru.otus.config;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import reactor.util.annotation.NonNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class WebServiceConfiguration {
    private @Value("${server.port:#{8080}}") int SERVER_PORT;
    private @Value("${server.threadsCount:#{4}}") int THREAD_POOL_SIZE;

    @Bean
    public ReactiveWebServerFactory reactiveWebServerFactory() {
        var eventLoopGroup = new NioEventLoopGroup(THREAD_POOL_SIZE,
                new ThreadFactory() {
                    private final AtomicLong threadIdGenerator = new AtomicLong(0);
                    @Override
                    public Thread newThread(@NonNull Runnable task) {
                        var thread = new Thread(task);
                        thread.setName("server-thread-" + threadIdGenerator.incrementAndGet());
                        return thread;
                    }
                });
        var webServerFactory = new NettyReactiveWebServerFactory(SERVER_PORT);
        webServerFactory.addServerCustomizers(b -> b.runOn(eventLoopGroup));
        return webServerFactory;
    }

    @Bean
    public ReactorResourceFactory reactorResourceFactory() {
        var eventLoopGroup = new NioEventLoopGroup(THREAD_POOL_SIZE,
                new ThreadFactory() {
                    private final AtomicLong threadIdGenerator = new AtomicLong(0);
                    @Override
                    public Thread newThread(@NonNull Runnable task) {
                        var thread = new Thread(task);
                        thread.setName("client-thread-" + threadIdGenerator.incrementAndGet());
                        return thread;
                    }
                });

        var resourceFactory= new ReactorResourceFactory();
        resourceFactory.setLoopResources(b -> eventLoopGroup);
        resourceFactory.setUseGlobalResources(false);
        return resourceFactory;
    }
}
