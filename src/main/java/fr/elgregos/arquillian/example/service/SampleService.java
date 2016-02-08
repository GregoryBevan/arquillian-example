package fr.elgregos.arquillian.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.elgregos.arquillian.example.domain.Sample;
import fr.elgregos.arquillian.example.domain.SampleRepository;

@Service
@Transactional
public class SampleService {

	@Autowired
	private SampleRepository repository;

	public Sample create(final String name) {
		final Sample s = new Sample();
		s.setName(name);
		this.repository.persist(s);
		return s;
	}

	public void delete(final Long id) {
		final Sample entity = this.repository.get(id, Sample.class);
		this.repository.delete(entity);
	}

	public Sample get(final Long id) {
		return this.repository.get(id, Sample.class);
	}

}