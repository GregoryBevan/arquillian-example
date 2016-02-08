package fr.elgregos.arquillian.example.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sample")
public class Sample implements Serializable {

	public static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public void setName(final String name) {
		if (name == null || name.length() < 3) {
			throw new IllegalArgumentException("Name can not be null and must have a size of at least 3 characters");
		}
		this.name = name;
	}

}