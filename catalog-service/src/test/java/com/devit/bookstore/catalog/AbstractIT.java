package com.devit.bookstore.catalog;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(TestContainersConfiguration.class)
@AutoConfigureRestTestClient
public abstract class AbstractIT {

    @Autowired
    protected RestTestClient restClient;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {

        // configure the RestTestClient to use the random port
        restClient = restClient.mutate().baseUrl("http://localhost:" + port).build();
    }
}
