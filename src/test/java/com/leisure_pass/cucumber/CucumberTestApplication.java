package com.leisure_pass.cucumber;

import com.leisure_pass.Application;
import com.leisure_pass.repository.CustomerRepository;
import com.leisure_pass.repository.PassRepository;
import org.apache.http.HttpClientConnection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
@RunWith(SpringRunner.class)
@ContextConfiguration
public class CucumberTestApplication {

    @Autowired
    public CustomerRepository customerRepository;

    @Autowired
    public PassRepository passRepository;

    @LocalServerPort
    private int port = 8080;

    public TestRestTemplate restTemplate = new TestRestTemplate();
//    public RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    public String baseUrl() {
        return String.format("http://localhost:%s", port);
    }

}
