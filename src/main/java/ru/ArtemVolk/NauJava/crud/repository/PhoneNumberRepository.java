package ru.ArtemVolk.NauJava.crud.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ArtemVolk.NauJava.entity.PhoneNumber;

public interface PhoneNumberRepository extends CrudRepository<PhoneNumber, Long> {
}
