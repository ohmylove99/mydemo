package org.octopus.api.ut;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.octopus.api.repository.BookRepository;
import org.octopus.api.web.rest.BookController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerUT {
	@Autowired
	private MockMvc mvc;
	@Autowired
	protected WebApplicationContext wac;
	@Autowired
	protected BookController controller;
	// This is UT, so have to use MockBean dependency
	@MockBean
	protected BookRepository repository;

	@Before
	public void setup() throws Exception {
		this.mvc = standaloneSetup(this.controller).build();
	}

	@Test
	public void test_findAll() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/book")//
				.accept(MediaType.APPLICATION_JSON))//
				.andDo(print())//
				.andExpect(status().isOk())//
				.andExpect(content().string("[]"));// Json Empty array
	}
}
