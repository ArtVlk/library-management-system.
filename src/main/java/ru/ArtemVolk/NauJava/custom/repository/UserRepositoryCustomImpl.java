package ru.ArtemVolk.NauJava.custom.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.ArtemVolk.NauJava.entity.*;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private final EntityManager entityManager;

    @Autowired
    public UserRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findByNameAndGender(String name, String gender) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Predicate namePredicate = criteriaBuilder.equal(userRoot.get("name"), name);
        Predicate genderPredicate = criteriaBuilder.equal(userRoot.get("gender"), gender);
        Predicate nameAndGenderPredicate = criteriaBuilder.and(namePredicate, genderPredicate);
        criteriaQuery.select(userRoot).where(nameAndGenderPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<User> findByPhoneNumber(String phoneNumber) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        Predicate phoneNumberPredicate = criteriaBuilder.equal(userRoot.get("phoneNumber").get("number"), phoneNumber);
        criteriaQuery.select(userRoot).where(phoneNumberPredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
