package org.octopus.api.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Employee {
	private String id;
	private String name;
	private Date createdTime;
}
