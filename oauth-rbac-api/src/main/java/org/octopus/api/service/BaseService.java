package org.octopus.api.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseService<T, PK> {

	public Page<T> findAll(JpaRepository<T, PK> repository, Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Optional<T> findById(JpaRepository<T, PK> repository, PK id) {
		return repository.findById(id);
	}

	public void remove(JpaRepository<T, PK> repository, PK id) {
		repository.deleteById(id);
	}

	public T createOrUpdate(JpaRepository<T, PK> repository, T entity) {
		log.info("creating or updating entity - " + entity.toString());
		T newEntity = repository.saveAndFlush(entity);
		log.info("return entity - " + newEntity.toString());
		return newEntity;
	}
}
