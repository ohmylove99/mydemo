package org.octopus.api.web.rest;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.octopus.api.entity.BookEntity;
import org.octopus.api.exception.BookNotFoundException;
import org.octopus.api.exception.BookUnSupportedFieldPatchException;
import org.octopus.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookController {
	@Autowired
	private BookRepository repository;

	// Find
	@GetMapping("/book")
	List<BookEntity> findAll() {
		return repository.findAll();
	}

	// Save
	@PostMapping("/book")
	// return 201 instead of 200
	@ResponseStatus(HttpStatus.CREATED)
	BookEntity newBookEntity(@Valid @RequestBody BookEntity newBookEntity) {
		return repository.save(newBookEntity);
	}

	// Find
	@GetMapping("/book/{id}")
	BookEntity findOne(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
	}

	// Save or update
	@PutMapping("/book/{id}")
	BookEntity saveOrUpdate(@RequestBody BookEntity newBookEntity, @PathVariable Long id) {

		return repository.findById(id).map(x -> {
			x.setName(newBookEntity.getName());
			x.setPrice(newBookEntity.getPrice());
			return repository.save(x);
		}).orElseGet(() -> {
			newBookEntity.setId(id);
			return repository.save(newBookEntity);
		});
	}

	// update name only
	@PatchMapping("/book/{id}")
	BookEntity patch(@RequestBody Map<String, String> update, @PathVariable Long id) {

		return repository.findById(id).map(x -> {

			String name = update.get("name");
			if (!StringUtils.isEmpty(name)) {
				x.setName(name);
				// better create a custom method to update a value = :newValue where id = :id
				return repository.save(x);
			} else {
				throw new BookUnSupportedFieldPatchException(update.keySet());
			}

		}).orElseGet(() -> {
			throw new BookNotFoundException(id);
		});

	}

	@DeleteMapping("/book/{id}")
	void deleteBook(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
