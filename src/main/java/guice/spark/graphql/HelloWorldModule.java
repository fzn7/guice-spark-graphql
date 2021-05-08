package guice.spark.graphql;

import com.google.inject.AbstractModule;

public class HelloWorldModule extends AbstractModule {
    protected void configure() {
        bind(HelloWorldService.class).asEagerSingleton();
    }
}
