package org.octopus.api.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.octopus.api.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryUT {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private BookRepository repository;

	@Test
	public void testFindByName() {
		BookEntity book = new BookEntity();
		book.setName("C++");
		book.setPrice(new BigDecimal(3L));
		entityManager.persist(book);

		List<BookEntity> books = repository.findByName("C++");
		assertEquals(1, books.size());

		assertThat(books).extracting(BookEntity::getName).containsOnly("C++");
	}
}
