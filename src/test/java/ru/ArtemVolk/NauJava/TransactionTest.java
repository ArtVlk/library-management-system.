package ru.ArtemVolk.NauJava;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ArtemVolk.NauJava.entity.Address;
import ru.ArtemVolk.NauJava.entity.User;
import ru.ArtemVolk.NauJava.crud.repository.AddressRepository;
import ru.ArtemVolk.NauJava.crud.repository.UserRepository;
import ru.ArtemVolk.NauJava.services.UserService;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class TransactionTest {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserService userService;

    @Autowired
    TransactionTest(UserRepository userRepository,
                       AddressRepository addressRepository, UserService userService) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.userService = userService;
    }

    @Test
    void testDeleteAddressAndUser() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        addressRepository.save(address);

        User user = new User();
        user.setName(UUID.randomUUID().toString());
        user.setAddress(address);
        userRepository.save(user);

        userService.deleteAddressAndUser(address);

        Assertions.assertFalse(userRepository.existsById(user.getId()));
        Assertions.assertFalse(addressRepository.existsById(address.getId()));
    }

    @Test
    @Transactional
    void testDeleteAddressAndUserInTxNegativeCase() {
        Address address = new Address();
        address.setStreet(UUID.randomUUID().toString());
        address.setCity(UUID.randomUUID().toString());
        addressRepository.save(address);

        User user = new User();
        user.setName(UUID.randomUUID().toString());
        user.setAddress(address);
        userRepository.save(user);

        Address nonExistentAddress = new Address();
        nonExistentAddress.setStreet(UUID.randomUUID().toString());
        nonExistentAddress.setCity(UUID.randomUUID().toString());


        Optional<Address> foundAddress = addressRepository.findById(address.getId());
        Assertions.assertTrue(foundAddress.isPresent());

        Optional<User> foundUser = userRepository.findById(user.getId());
        Assertions.assertTrue(foundUser.isPresent());
    }
}