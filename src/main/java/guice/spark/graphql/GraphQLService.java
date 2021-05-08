package guice.spark.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.Collections;

import static spark.Spark.get;
import static spark.Spark.post;

public class GraphQLService {
    private final GraphQL graphQL;
    private final ObjectMapper mapper;

    @Inject
    public GraphQLService(GraphQL graphQL, ObjectMapper mapper) {
        this.graphQL = graphQL;
        this.mapper = mapper;
    }

    public void initialize() {
        post("/graphql", (request, response) -> {
            GraphQLRequestBody body = mapper.readValue(request.body(), GraphQLRequestBody.class);

            String query = body.getQuery();
            if (query == null) {
                query = "";
            }

            ExecutionResult executionResult = graphQL.execute(
                    ExecutionInput.newExecutionInput()
                            .query(query)
                            .operationName(body.getOperationName())
                            .variables(body.getVariables())
                            .build()
            );

            response.type("application/json");
            return mapper.writeValueAsString(executionResult.toSpecification());
        });

        get("/playground", (req, res) -> new VelocityTemplateEngine().render(
                new ModelAndView(Collections.emptyMap(), "playground.html"))
        );
    }
}