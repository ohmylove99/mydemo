package org.octopus.api.mapper;

import java.util.List;

import org.mapstruct.Mapper;

/**
 * 
 * @author joshualeng
 *
 * @param <Entity>
 * @param <DTO>
 */
@Mapper
public interface BookMapper<Entity, DTO> {
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public DTO toDTO(Entity entity);

	/**
	 * 
	 * @param entities
	 * @return
	 */
	public List<DTO> toDTOs(List<Entity> entities);

	/**
	 * 
	 * @param dto
	 * @return
	 */
	public Entity toEntity(DTO dto);
}