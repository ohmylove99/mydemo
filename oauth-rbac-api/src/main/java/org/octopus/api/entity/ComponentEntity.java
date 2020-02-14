package org.octopus.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity(name = "Component")
@Table(name = "oauth_rbac_component")
public class ComponentEntity extends IdentityEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6659559102920865337L;

	@Column(length = 128)
	@Size(min = 3, max = 20)
	@NotEmpty(message = "Please provide a name")
	private String name;

	@Column(length = 256)
	private String description;

	@Column(length = 256)
	private String contact;
}
