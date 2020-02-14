package org.octopus.api.ut;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.octopus.api.web.rest.ApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebMvcTest(ApiController.class)
public class ApiControllerUT {
	@Autowired
	private MockMvc mvc;
	@Autowired
	protected WebApplicationContext wac;
	/*@Autowired
	ApiController apiController;

	@Before
	public void setup() throws Exception {
		this.mvc = standaloneSetup(this.apiController).build();
	}*/

	@Test
	public void test_hello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/hello")//
				.accept(MediaType.APPLICATION_JSON))//
				.andDo(print())//
				.andExpect(status().isOk())//
				.andExpect(MockMvcResultMatchers.content().string("Hello World"));
	}

}
