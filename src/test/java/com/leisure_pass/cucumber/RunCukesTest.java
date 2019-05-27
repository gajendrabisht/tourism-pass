package com.leisure_pass.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "com.leisure_pass.cucumber.steps", plugin= "html:cucumber-report")
public class RunCukesTest {
}
