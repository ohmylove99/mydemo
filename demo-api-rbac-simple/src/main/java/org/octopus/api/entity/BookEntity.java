package org.octopus.api.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "demo_api_rbac_simple_book") 
public class BookEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    @Size(min = 3, max = 20)
    private String name;
    
    private BigDecimal price;
    
    private Date publishDate;
}
