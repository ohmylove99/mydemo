package org.octopus.api.it;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.octopus.api.entity.BookEntity;
import org.octopus.api.repository.BookRepository;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
@ActiveProfiles("test")
public class BookControllerIT {
	private static final ObjectMapper om = new ObjectMapper();

	@Autowired
	private TestRestTemplate restTemplate;
	private RestTemplate patchRestTemplate;

	@MockBean
	private BookRepository mockRepository;

	@Before
	public void init() {
		BookEntity book = new BookEntity();
		book.setId(1L);
		book.setName("Book Name");
		book.setPrice(new BigDecimal("9.99"));
		when(mockRepository.findById(1L)).thenReturn(Optional.of(book));

		this.patchRestTemplate = restTemplate.getRestTemplate();
		HttpClient httpClient = HttpClientBuilder.create().build();
		this.patchRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
	}

	@Test
	public void find_bookId_OK() throws JSONException {

		String expected = "{id:1,name:\"Book Name\", price:9.99}";

		ResponseEntity<String> response = restTemplate.getForEntity("/api/book/1", String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

		JSONAssert.assertEquals(expected, response.getBody(), false);

		verify(mockRepository, times(1)).findById(1L);

	}

	@Test
	public void find_allBook_OK() throws Exception {

		BookEntity book1 = new BookEntity();
		book1.setId(1L);
		book1.setName("Book A");
		book1.setPrice(new BigDecimal("1.99"));

		BookEntity book2 = new BookEntity();
		book2.setId(1L);
		book2.setName("Book B");
		book2.setPrice(new BigDecimal("2.99"));

		List<BookEntity> books = Arrays.asList(book1, book2);

		when(mockRepository.findAll()).thenReturn(books);

		String expected = om.writeValueAsString(books);

		ResponseEntity<String> response = restTemplate.getForEntity("/api/book", String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);

		verify(mockRepository, times(1)).findAll();
	}

	@Test
	public void find_bookIdNotFound_404() throws Exception {

		String expected = "{status:404,error:\"Not Found\",message:\"Book id not found : 5\",path:\"/api/book/5\"}";

		ResponseEntity<String> response = restTemplate.getForEntity("/api/book/5", String.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);

	}

	@Test
	public void save_book_OK() throws Exception {

		BookEntity newBook = new BookEntity();
		newBook.setId(1L);
		newBook.setName("Spring Boot");
		newBook.setPrice(new BigDecimal("2.99"));
		when(mockRepository.save(any(BookEntity.class))).thenReturn(newBook);

		String expected = om.writeValueAsString(newBook);

		ResponseEntity<String> response = restTemplate.postForEntity("/api/book", newBook, String.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);

		verify(mockRepository, times(1)).save(any(BookEntity.class));

	}

	@Test
	public void update_book_OK() throws Exception {

		BookEntity updateBook = new BookEntity();
		updateBook.setId(1L);
		updateBook.setName("Book ABC");
		updateBook.setPrice(new BigDecimal("19.99"));
		when(mockRepository.save(any(BookEntity.class))).thenReturn(updateBook);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(om.writeValueAsString(updateBook), headers);

		ResponseEntity<String> response = patchRestTemplate.exchange("/api/book/1", HttpMethod.PUT, entity,
				String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		JSONAssert.assertEquals(om.writeValueAsString(updateBook), response.getBody(), false);

		verify(mockRepository, times(1)).findById(1L);
		verify(mockRepository, times(1)).save(any(BookEntity.class));

	}

	@Test
	public void patch_bookName_OK() {

		when(mockRepository.save(any(BookEntity.class))).thenReturn(new BookEntity());
		String patchInJson = "{\"name\":\"super name\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(patchInJson, headers);

		ResponseEntity<String> response = patchRestTemplate.exchange("/api/book/1", HttpMethod.PUT, entity,
				String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		verify(mockRepository, times(1)).findById(1L);
		verify(mockRepository, times(1)).save(any(BookEntity.class));

	}

	@Test
	public void patch_bookPrice_405() throws JSONException {

		String expected = "{status:405,error:\"Method Not Allowed\",message:\"Field [price] update is not allow.\"}";

		String patchInJson = "{\"price\":\"99.99\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(patchInJson, headers);

		ResponseEntity<String> response = restTemplate.exchange("/api/book/1", HttpMethod.PATCH, entity, String.class);

		assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
		JSONAssert.assertEquals(expected, response.getBody(), false);

		verify(mockRepository, times(1)).findById(1L);
		verify(mockRepository, times(0)).save(any(BookEntity.class));
	}

	@Test
	public void delete_book_OK() {

		doNothing().when(mockRepository).deleteById(1L);

		HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
		ResponseEntity<String> response = restTemplate.exchange("/api/book/1", HttpMethod.DELETE, entity, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		verify(mockRepository, times(1)).deleteById(1L);
	}

	@Test
	public void save_emptyName_emptyPrice_400() throws JSONException {

		String bookInJson = "{\"id1\":\"ABC\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(bookInJson, headers);

		// send json with POST
		ResponseEntity<String> response = restTemplate.postForEntity("/api/book", entity, String.class);
		// printJSON(response);

		String expectedJson = "{\"status\":400,\"errors\":[\"Please provide a name\",\"Please provide a price\"]}";
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		JSONAssert.assertEquals(expectedJson, response.getBody(), false);

		verify(mockRepository, times(0)).save(any(BookEntity.class));

	}

	@Test
	public void create_emptyName_emptyPrice__with_id_400() throws JSONException {

		String bookInJson = "{\"id\":\"ABC\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(bookInJson, headers);

		// send json with POST
		ResponseEntity<String> response = restTemplate.postForEntity("/api/book", entity, String.class);
		// printJSON(response);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		JSONAssert.assertEquals(null, response.getBody(), false);

		verify(mockRepository, times(0)).save(any(BookEntity.class));

	}

	/*
	 * { "timestamp":"2019-08-05T09:44:13.217+0000", "status":400,
	 * "errors":["size must be between 3 and 20"] }
	 */
	@Test
	public void save_invalidName_400() throws JSONException {

		String bookInJson = "{\"name\":\"A\",\"price\":\"9.99\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(bookInJson, headers);

		// Try exchange
		ResponseEntity<String> response = restTemplate.exchange("/api/book", HttpMethod.POST, entity, String.class);

		String expectedJson = "{\"status\":400,\"errors\":[\"size must be between 3 and 20\"]}";
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		JSONAssert.assertEquals(expectedJson, response.getBody(), false);

		verify(mockRepository, times(0)).save(any(BookEntity.class));

	}
}
