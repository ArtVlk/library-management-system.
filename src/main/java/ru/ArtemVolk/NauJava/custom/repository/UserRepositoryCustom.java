package ru.ArtemVolk.NauJava.custom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ArtemVolk.NauJava.entity.User;

public interface UserRepositoryCustom {
    List<User> findByNameAndGender(String name, String gender);
    List<User> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

}
