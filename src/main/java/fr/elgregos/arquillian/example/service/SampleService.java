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

    public Sample create(String name) {
	Sample s = new Sample();
	s.setName(name);
	repository.persist(s);
	return s;
    }

    public void delete(Long id) {
	Sample entity = repository.get(id, Sample.class);
	repository.delete(entity);
    }

    public Sample get(Long id) {
	return repository.get(id, Sample.class);
    }

}