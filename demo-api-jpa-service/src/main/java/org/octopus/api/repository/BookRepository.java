package org.octopus.api.repository;

import java.util.List;

import org.octopus.api.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
	List<BookEntity> findByName(String name);
}