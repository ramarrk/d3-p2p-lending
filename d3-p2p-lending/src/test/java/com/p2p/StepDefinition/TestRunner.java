package com.p2p.StepDefinition;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import io.cucumber.junit.platform.engine.Constants;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/funding.feature") // Kunci hanya ke fitur pendanaanmu
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.p2p.StepDefinition")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty")
public class TestRunner {
    // Biarkan kelas ini kosong
}