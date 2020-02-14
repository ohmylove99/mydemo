package org.octopus.api.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.octopus.api.Application;
import org.octopus.api.model.Department;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApiCongtrollerIT {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void hello() {
		assertEquals("Hello World", this.restTemplate.getForObject("/api/hello", String.class));
	}

	@Test
	public void helloweekdays() {
		assertTrue(this.restTemplate.getForEntity("http://localhost:" + port + "/api/hello/weekdays", String[].class)
				.getBody().length == 5);
	}

	@Test
	public void helloemployee() {
		assertTrue(this.restTemplate.getForEntity("http://localhost:" + port + "/api/hello/employee", HashMap.class)
				.getBody().size() == 2);
	}

	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;

	// Please note the different @Before vs. BeforeEach
	// As Before have to static
	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void getDepartment() throws Exception {
		// Test Way1
		assertTrue(this.restTemplate.getForEntity("http://localhost:" + port + "/api/hello/department", HashMap.class)
				.getBody().size() == 2);

		ResponseEntity<Department> responseEntity = restTemplate
				.getForEntity("http://localhost:" + port + "/api/hello/department", Department.class);
		Department object = responseEntity.getBody();
		MediaType contentType = responseEntity.getHeaders().getContentType();
		HttpStatus statusCode = responseEntity.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
		assertEquals("Technology", object.getName());
		assertEquals(MediaType.APPLICATION_JSON, contentType);

		// Test Way2
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/api/hello/department",
				HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals("{name:\"Technology\"}", response.getBody(), false);

		// Test Way3 - Using mockMvc with Json String
		mockMvc.perform(get("http://localhost:" + port + "/api/hello/department")//
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))//
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.name", is("Technology")));

	}

	@Test
	public void greet() throws Exception {
		mockMvc.perform(get("http://localhost:" + port + "/api/greet")//
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))//
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.message", is("Hello World!!!")));

	}

	@Test
	public void greetWithPathVariable() throws Exception {
		mockMvc.perform(get("http://localhost:" + port + "/api/greetWithPathVariable/jason")//
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))//
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.message", is("Hello World jason!!!")));

	}

	@Test
	public void greetWithQueryVariable() throws Exception {
		mockMvc.perform(get("http://localhost:" + port + "/api/greetWithQueryVariable?name=jason")//
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))//
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.message", is("Hello World jason!!!")));

	}

	@Test
	public void greetWithPostAndFormData() throws Exception {
		mockMvc.perform(post("http://localhost:" + port + "/api/greetWithPostAndFormData").param("id", "1")
				.param("name", "John Doe")//
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))//
				.andDo(print())//
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))//
				.andExpect(status().isOk())//
				.andExpect(jsonPath("$.message", is("Hello World John Doe!!!")));
	}
}
