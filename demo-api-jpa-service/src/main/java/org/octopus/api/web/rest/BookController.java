package org.octopus.api.web.rest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.octopus.api.entity.BookEntity;
import org.octopus.api.exception.EntityNotFoundException;
import org.octopus.api.exception.EntityUnSupportedFieldPatchException;
import org.octopus.api.mapper.BookMapper;
import org.octopus.api.model.BookDto;
import org.octopus.api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/book")
@Getter
@Setter
public class BookController {
	@Autowired
	private BookService service;
	@Autowired
	private BookMapper<BookEntity, BookDto> mapper;

	// Find
	@GetMapping()
	public ResponseEntity<List<BookDto>> getBooks(final Pageable pageable) {
		// PageRequest pageReq = PageRequest.of(page, size,
		// Sort.Direction.fromString(sortDir), sort);
		List<BookEntity> books = service.findAll(pageable).getContent();
		return ResponseEntity.ok(mapper.toDTOs(books));
	}

	@GetMapping("/page")
	public Page<BookDto> readPageable(final Pageable pageable) {
		Page<BookEntity> entities = service.findAll(pageable);
		return entities.map(entity -> mapper.toDTO(entity));
	}

	// Save
	@PostMapping()
	// return 201 instead of 200
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<BookDto> create(@Valid @RequestBody BookDto newBook) {
		BookEntity entity = service.create(mapper.toEntity(newBook));

		return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(entity));
	}

	// Find
	@GetMapping("/book/{id}")
	public ResponseEntity<BookDto> findOne(@PathVariable Long id) {
		Optional<BookEntity> product = service.get(id);
		if (product.isPresent()) {
			return ResponseEntity.ok(mapper.toDTO(product.get()));
		} else {
			throw new EntityNotFoundException(BookEntity.class.getSimpleName(), id.toString());
		}
	}

	// Save or update
	@PutMapping("/book/{id}")
	public ResponseEntity<BookDto> createOrUpdate(@RequestBody BookDto newBook, @PathVariable Long id) {
		BookEntity book = mapper.toEntity(newBook);
		Optional<BookEntity> findBook = service.get(id);
		if (findBook.isPresent()) {
			book.setId(id);
			BookEntity bookUpdated = service.update(book);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.toDTO(bookUpdated));
		} else {
			BookEntity entity = service.create(mapper.toEntity(newBook));
			return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(entity));
		}
	}

	// update name only, you can extends via reflection
	@PatchMapping("/book/{id}")
	BookEntity patch(@RequestBody Map<String, String> update, @PathVariable Long id) {

		return service.get(id).map(x -> {

			String name = update.get("name");
			if (!StringUtils.isEmpty(name)) {
				x.setName(name);
				// better create a custom method to update a value = :newValue where id = :id
				return service.update(x);
			} else {
				throw new EntityUnSupportedFieldPatchException(update.keySet());
			}

		}).orElseGet(() -> {
			throw new EntityNotFoundException(BookEntity.class.getSimpleName(), id.toString());
		});

	}

	@DeleteMapping("/book/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable Long id) {
		Optional<BookEntity> entity = service.get(id);
		if (entity.isPresent()) {
			service.remove(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} else {
			throw new EntityNotFoundException(BookEntity.class.getSimpleName(), id.toString());
		}
	}
}
