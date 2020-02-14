package org.octopus.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Greeting {
	private int id;
    private String message;
}
