package ru.ArtemVolk.NauJava;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import ru.ArtemVolk.NauJava.custom.repository.UserRepositoryCustom;
import ru.ArtemVolk.NauJava.entity.Address;
import ru.ArtemVolk.NauJava.entity.PhoneNumber;
import ru.ArtemVolk.NauJava.entity.User;
import ru.ArtemVolk.NauJava.crud.repository.AddressRepository;
import ru.ArtemVolk.NauJava.crud.repository.PhoneNumberRepository;
import ru.ArtemVolk.NauJava.crud.repository.UserRepository;
import ru.ArtemVolk.NauJava.services.UserService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserRepositoryTest {
    private final UserRepository userRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final UserRepositoryCustom userRepositoryCustom;
    private final PhoneNumberRepository phoneNumberRepositoryCustom;

    @Autowired
    UserRepositoryTest(UserRepository userRepository, PhoneNumberRepository phoneNumberRepository,
                       AddressRepository addressRepository, UserService userService,
                       UserRepositoryCustom userRepositoryCustomCriteriaApi, PhoneNumberRepository phoneNumberRepositoryCriteriaApi) {
        this.userRepository = userRepository;
        this.phoneNumberRepository = phoneNumberRepository;
        this.addressRepository = addressRepository;
        this.userService = userService;
        this.userRepositoryCustom = userRepositoryCustomCriteriaApi;
        this.phoneNumberRepositoryCustom = phoneNumberRepositoryCriteriaApi;
    }

    @Test
    @Transactional
    @Rollback
    void testFindUserByNameAndGender() {
        String userName = UUID.randomUUID().toString();
        String userGender = "Male";

        User user = new User();
        user.setName(userName);
        user.setGender(userGender);
        userRepository.save(user);

        List<User> foundUsers = userRepository.findByNameAndGender(userName, userGender);

        assertEquals(1, foundUsers.size());
        User foundUser = foundUsers.getFirst();
        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(userName, foundUser.getName());
        assertEquals(userGender, foundUser.getGender());
    }


    @Test
    @Transactional
    @Rollback
    void testFindUserByPhoneNumber() {
        String phoneNumber = UUID.randomUUID().toString();

        PhoneNumber phoneNumberEntity = new PhoneNumber();
        phoneNumberEntity.setNumber(phoneNumber);
        phoneNumberRepository.save(phoneNumberEntity);

        User user = new User();
        user.setName(UUID.randomUUID().toString());
        user.setPhoneNumber(phoneNumberEntity);
        userRepository.save(user);

        List<User> foundUsers = userRepository.findByPhoneNumber(phoneNumber);

        assertEquals(1, foundUsers.size());
        User foundUser = foundUsers.getFirst();
        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(phoneNumber, foundUser.getPhoneNumber().getNumber());
    }


    @Test
    @Transactional
    @Rollback
    void testFindUserByAddress() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        addressRepository.save(address);

        User user = new User();
        user.setName(UUID.randomUUID().toString());
        user.setAddress(address);
        userRepository.save(user);

        User foundUser = userRepository.findByAddress(address);

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(address.getId(), foundUser.getAddress().getId());
    }

    @Test
    @Transactional
    @Rollback
    void testCriteriaApiFindByNameAndGender() {
        String userName = UUID.randomUUID().toString();
        String userGender = "Male";

        User user = new User();
        user.setName(userName);
        user.setGender(userGender);
        userRepository.save(user);

        List<User> foundUsers = userRepositoryCustom.findByNameAndGender(userName, userGender);

        assertEquals(1, foundUsers.size());
        User foundUser = foundUsers.getFirst();
        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(userName, foundUser.getName());
        assertEquals(userGender, foundUser.getGender());
    }

    @Test
    @Transactional
    @Rollback
    void testCriteriaApiFindUserByPhoneNumber() {
        String phoneNumber = UUID.randomUUID().toString();

        PhoneNumber phoneNumberEntity = new PhoneNumber();
        phoneNumberEntity.setNumber(phoneNumber);
        phoneNumberRepository.save(phoneNumberEntity);

        User user = new User();
        user.setName(UUID.randomUUID().toString());
        user.setPhoneNumber(phoneNumberEntity);
        userRepository.save(user);

        List<User> foundUsers = userRepositoryCustom.findByPhoneNumber(phoneNumber);

        assertEquals(1, foundUsers.size());
        User foundUser = foundUsers.getFirst();
        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(phoneNumber, foundUser.getPhoneNumber().getNumber());

    }

    @Test
    @Transactional
    @Rollback
    public void testFindByName() {
        String userName = UUID.randomUUID().toString();

        User user = new User();
        user.setName(userName);
        userRepository.save(user);

        User foundUser = userRepository.findByName(userName);

        assertNotNull(foundUser);
        assertEquals(userName, foundUser.getName());
    }

    @Test
    public void testAddUser() {
        String userName = UUID.randomUUID().toString();
        User user = new User();
        user.setName(userName);

        userRepository.addUser(user);

        User foundUser = userRepository.findByName(userName);

        assertNotNull(foundUser);
        assertEquals(userName, foundUser.getName());
    }
}