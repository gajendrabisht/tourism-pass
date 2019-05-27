package com.leisure_pass.cucumber.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leisure_pass.cucumber.CucumberTestApplication;
import com.leisure_pass.cucumber.CukeWorld;
import com.leisure_pass.domain.Status;
import com.leisure_pass.entity.Customer;
import com.leisure_pass.entity.Pass;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import org.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;

public class CukeStepDefs extends CucumberTestApplication {

    private CukeWorld cukeWorld;

    public CukeStepDefs() {
        this.cukeWorld = new CukeWorld();
    }

    @Given("^a customer exists as")
    public void a_customer_exists_as(DataTable table) {
        Customer customer = new ObjectMapper().convertValue(table.asMaps().get(0), Customer.class);
        customerRepository.saveAndFlush(customer);
    }

    @When("^customer '(.*)' adds a pass")
    public void customer_adds_a_pass(String customerId, DataTable table) {
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(table.asMaps().get(0), getHttpHeaders());
        String url = String.format("%s/customers/%s/passes", baseUrl(), customerId);
        ResponseEntity<String> response = restTemplate.exchange(url, POST, httpEntity, String.class);
        cukeWorld.setResponseStatus(response.getStatusCodeValue());
        cukeWorld.setResponseBody(response.getBody());
    }

    @When("^customer '(.*)' (.*)s pass '(.*)'")
    public void customer_cancels_a_pass(String customerId, String action, String passId) {
        String url = String.format("%s/customers/%s/passes/%s/%s", baseUrl(), customerId, passId, action);
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, getHttpHeaders());
        ResponseEntity<Void> response = restTemplate.exchange(url, PATCH, requestEntity, Void.class);
        cukeWorld.setResponseStatus(response.getStatusCodeValue());
    }

    @When("^vendor '(.*)' checks validity of pass '(.*)'")
    public void vendor_checks_validity_of_pass(String vendorId, String passId) {
        String url = String.format("%s/passes/%s/isValid", baseUrl(), passId);
        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.add("vendorId", vendorId);
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, GET, requestEntity, String.class);
        cukeWorld.setResponseStatus(response.getStatusCodeValue());
        cukeWorld.setResponseBody(response.getBody());
    }

    @Then("^I should get response status (\\d+)$")
    public void i_should_get_response_status(int responseStatusCode) {
        assertThat(cukeWorld.getResponseStatus(), is(responseStatusCode));
    }

    @Then("^I should get error message '(.*)'$")
    public void i_should_get_errorMessage(String errorMessage) {
        assertThat(cukeWorld.getResponseBody(), is(errorMessage));
    }

    @Then("^I should get newly created pass as$")
    public void i_should_get_response_body_as(DataTable table) throws JSONException, IOException {
        Map<String, String> expected = table.asMaps().get(0);
        Pass actualPass = new ObjectMapper().readValue(cukeWorld.getResponseBody(), Pass.class);
        assertThat(actualPass.getCity(), is(expected.get("city")));
        assertThat(actualPass.getLength(), is(Integer.valueOf(expected.get("length"))));
        assertThat(actualPass.getStatus(), is(Status.valueOf(expected.get("status"))));
    }

    @Then("^pass '(.*)' status should be '(.*)'")
    public void i_should_get_response_body_as(String passId, String status) {
        assertThat(passRepository.findById(UUID.fromString(passId)).get().getStatus(), is(Status.valueOf(status)));
    }

    @Then("^validity returned is '(.*)'")
    public void i_should_get_response_body_as(String expectedIsValid) {
        assertThat(Boolean.valueOf(cukeWorld.getResponseBody()), is(Boolean.valueOf(expectedIsValid)));
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Before
    public void before() {
        cleanupDB();
    }

    @After
    public void after() {
        cleanupDB();
    }

    private void cleanupDB() {
        customerRepository.deleteAll();
        passRepository.deleteAll();
    }

}
