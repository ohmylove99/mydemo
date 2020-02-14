package org.octopus.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

@Entity(name = "App")
@Table(name = "oauth_rbac_app")
public class AppEntity extends IdentityEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 611628435787516376L;

	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO, generator = "APP_SEQ")
	//@SequenceGenerator(sequenceName = "app_seq", allocationSize = 1, name = "APP_SEQ")
	//private Long id;

	@Column(length = 6)
	private String appId;

	@Column(length = 10)
	private String shortName;

	@Column(length = 128)
	private String name;

	@Column(length = 256)
	private String contact;
}
