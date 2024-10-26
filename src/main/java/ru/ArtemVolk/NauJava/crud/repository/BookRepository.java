package ru.ArtemVolk.NauJava.crud.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ArtemVolk.NauJava.entity.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
