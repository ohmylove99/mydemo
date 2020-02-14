package org.octopus.api.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.octopus.api.entity.ComponentEntity;
import org.octopus.api.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class ComponentService {
	@Autowired
	private ComponentRepository repository;

	@Transactional(readOnly = true)
	public Page<ComponentEntity> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Optional<ComponentEntity> findById(String id) {
		return repository.findById(id);
	}

	/**
	 * if entity contains Id then do update, else do create
	 * 
	 * @param entity
	 * @return
	 */
	public ComponentEntity createOrUpdate(ComponentEntity entity) {
		if (StringUtils.isEmpty(entity.getId())) {
			// do create
			log.info("creating entity...");
		} else {
			// do update
			log.info("updating entity...");
		}
		ComponentEntity newEntity = repository.saveAndFlush(entity);
		log.info("return entity - " + newEntity.toString());
		return newEntity;
	}

	/**
	 * 
	 * @param id
	 */
	public void remove(String id) {
		repository.deleteById(id);
	}
}
