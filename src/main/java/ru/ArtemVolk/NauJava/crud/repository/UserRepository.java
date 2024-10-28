package ru.ArtemVolk.NauJava.crud.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.ArtemVolk.NauJava.entity.Address;
import ru.ArtemVolk.NauJava.entity.User;


import java.util.List;

@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByNameAndGender(String name, String gender);
    @Query("SELECT c FROM User c WHERE c.phoneNumber.number = :phoneNumber")
    List<User> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
    User findByAddress(Address address);

    //task6
    User findByName(String name);
    default void addUser(User user) {
        save(user);
    }

    //for deatils
    User findByUsername(String username);
}
