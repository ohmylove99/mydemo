package org.octopus.api.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.octopus.api.entity.BookEntity;
import org.octopus.api.repository.BookRepository;
import org.octopus.api.util.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceUT {

	@Mock
	private BookRepository repository;

	@InjectMocks
	private BookService service;

	@Mock
	private Pageable pageableMock;

	@Mock
	private Utils utils;

	@Mock
	private Page<BookEntity> bookPage;

	@Test
	public void whenFindAll_thenReturnBookList() {
		// given
		BookEntity book = BookEntity.builder().name("Book1").price(new BigDecimal("1")).build();

		List<BookEntity> expectedBooks = Arrays.asList(book);

		doReturn(expectedBooks).when(repository).findAll();

		// when
		List<BookEntity> actualBooks = service.findAll();

		// then
		assertEquals(actualBooks, expectedBooks);
	}

	@Test
	public void whenFindAllPage_thenReturnBookList() {
		// given
		List<BookEntity> books = Arrays.asList(BookEntity.builder().id(1L).name("Book1").build(),
				BookEntity.builder().id(2L).name("Book2").build(), BookEntity.builder().id(3L).name("Book3").build(),
				BookEntity.builder().id(4L).name("Book4").build(), BookEntity.builder().id(5L).name("Book5").build());

		PageImpl<BookEntity> expectedPage = new PageImpl<BookEntity>(books);
		// when
		when(repository.findAll(org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(expectedPage);

		Pageable pageable = PageRequest.of(0, 2);// anyhow return all
		Page<BookEntity> actualPage = service.findAll(pageable);
		// then
		assertEquals(5, actualPage.getContent().size());
		assertEquals(expectedPage, actualPage);
	}

}
