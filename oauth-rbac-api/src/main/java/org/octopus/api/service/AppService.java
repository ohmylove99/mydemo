package org.octopus.api.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.octopus.api.entity.AppEntity;
import org.octopus.api.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class AppService extends BaseService<AppEntity, String> {
	@Autowired
	protected AppRepository repository;

	/**
	 * 
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<AppEntity> findAll(Pageable pageable) {
		return findAll(repository, pageable);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public Optional<AppEntity> findById(String id) {
		return findById(repository, id);
	}

	/**
	 * 
	 * @param id
	 */
	public void remove(String id) {
		repository.deleteById(id);
	}

	/**
	 * if entity contains Id then do update, else do create
	 * 
	 * @param entity
	 * @return
	 */
	public AppEntity createOrUpdate(AppEntity entity) {
		if (StringUtils.isEmpty(entity.getId())) {
			// do create
			log.info("creating entity...");
		} else {
			// do update
			log.info("updating entity...");
		}
		return createOrUpdate(repository, entity);
	}
}
