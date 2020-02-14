package org.octopus.api.ut;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.octopus.api.entity.BookEntity;
import org.octopus.api.mapper.BookMapper;
import org.octopus.api.model.BookDto;
import org.octopus.api.service.BookService;
import org.octopus.api.web.rest.BookController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerUT {
	@Autowired
	private MockMvc mvc;

	@Autowired
	protected WebApplicationContext wac;

	@MockBean
	protected BookService service;

	@MockBean
	protected BookMapper<BookEntity, BookDto> mapper;

	@Before
	public void setup() throws Exception {
		//this.mvc = standaloneSetup(this.apiController).build();
	}

	@Test
	public void test_hello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/hello")//
				.accept(MediaType.APPLICATION_JSON))//
				.andDo(print())//
				.andExpect(status().isOk())//
				.andExpect(MockMvcResultMatchers.content().string("Hello World"));
	}

	/*
	 * @Autowired protected BookController controller; // This is UT, so have to use
	 * MockBean dependency
	 * 
	 * @MockBean protected BookService service;
	 * 
	 * @Before public void setup() throws Exception { //this.mvc =
	 * standaloneSetup(this.controller).build(); controller.setService(service); }
	 * 
	 * @Test public void test_findAll() throws Exception {
	 * mvc.perform(MockMvcRequestBuilders.get("/api/book")//
	 * .accept(MediaType.APPLICATION_JSON))// .andDo(print())//
	 * .andExpect(status().isOk())// .andExpect(content().string("[]"));// Json
	 * Empty array }
	 * 
	 * @Test public void test_findAll_Page() throws Exception { List<BookEntity>
	 * books = Arrays.asList(BookEntity.builder().id(1L).name("Book1").build(),
	 * BookEntity.builder().id(2L).name("Book2").build(),
	 * BookEntity.builder().id(3L).name("Book3").build(),
	 * BookEntity.builder().id(4L).name("Book4").build(),
	 * BookEntity.builder().id(5L).name("Book5").build());
	 * 
	 * PageImpl<BookEntity> expectedPage = new PageImpl<BookEntity>(books);
	 * 
	 * Pageable pageable = PageRequest.of(0, 2);// anyhow return all
	 * 
	 * 
	 * when(service.findAll(org.mockito.ArgumentMatchers.isA(Pageable.class))).
	 * thenReturn(expectedPage);
	 * 
	 * //Page<BookEntity> actualPage = service.findAll(pageable);
	 * 
	 * String responseString =
	 * mvc.perform(MockMvcRequestBuilders.get("/api/book")).andDo(print())
	 * .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();;
	 * System.out.println(responseString); }
	 */
}
