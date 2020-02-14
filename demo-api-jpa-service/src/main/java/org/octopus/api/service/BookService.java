package org.octopus.api.service;

import java.util.List;
import java.util.Optional;

import org.octopus.api.entity.BookEntity;
import org.octopus.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class BookService {
	@Autowired
	private BookRepository repository;

	public BookEntity create(BookEntity entity) {
		return repository.save(entity);
	}

	public BookEntity update(BookEntity entity) {
		return repository.save(entity);
	}

	public void remove(Long id) {
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Optional<BookEntity> get(Long id) {
		return repository.findById(id);
	}

	@Transactional(readOnly = true)
	public List<BookEntity> findByName(String name) {
		return repository.findByName(name);
	}

	public Page<BookEntity> findAll(Pageable pageable) {
		Page<BookEntity> books = repository.findAll(pageable);
		return books;
	}

	public List<BookEntity> findAll() {
		List<BookEntity> books = repository.findAll();
		return books;
	}

	protected BookRepository getRepository() {
		return repository;
	}
}
