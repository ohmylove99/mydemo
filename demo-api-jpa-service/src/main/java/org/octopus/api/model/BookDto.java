package org.octopus.api.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
	private Long id;

	@Size(min = 3, max = 20)
	@NotEmpty(message = "Please provide a name")
	private String name;

	@NotNull(message = "Please provide a price")
	private BigDecimal price;

	private Date publishDate;
}
