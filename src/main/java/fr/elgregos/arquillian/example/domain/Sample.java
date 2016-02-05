package fr.elgregos.arquillian.example.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sample")
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Long getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public void setName(String name) {
	if (name == null || name.length() < 3) {
	    throw new IllegalArgumentException("Name can not be null and must have a size of at least 3 characters");
	}
	this.name = name;
    }

}