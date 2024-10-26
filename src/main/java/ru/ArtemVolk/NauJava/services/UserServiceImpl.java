package ru.ArtemVolk.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.ArtemVolk.NauJava.entity.Address;
import ru.ArtemVolk.NauJava.entity.User;
import ru.ArtemVolk.NauJava.crud.repository.AddressRepository;
import ru.ArtemVolk.NauJava.crud.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public UserServiceImpl(AddressRepository addressRepository, UserRepository userRepository,
                           PlatformTransactionManager transactionManager) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.transactionManager = transactionManager;
    }

    @Transactional
    @Override
    public void deleteAddressAndUser(Address address) {
        TransactionStatus status = transactionManager.getTransaction(new
                DefaultTransactionDefinition());
        try
        {
            User user = userRepository.findByAddress(address);
            userRepository.delete(user);
            addressRepository.delete(address);
            transactionManager.commit(status);
        }
        catch (DataAccessException ex)
        {
            transactionManager.rollback(status);
            throw ex;
        }
    }
}