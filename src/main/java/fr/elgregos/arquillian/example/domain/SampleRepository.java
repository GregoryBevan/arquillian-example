package fr.elgregos.arquillian.example.domain;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SampleRepository extends AbstractRepository {

}