package guice.spark.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import graphql.GraphQL;

public class GraphQLModule extends AbstractModule {
    protected void configure() {
        bind(GraphQLService.class).asEagerSingleton();
        bind(GraphQL.class).toProvider(GraphQlProvider.class).asEagerSingleton();
        bind(ObjectMapper.class).asEagerSingleton();
    }
}
