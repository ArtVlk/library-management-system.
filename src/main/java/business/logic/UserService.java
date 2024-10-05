package business.logic;

import models.User;

public interface UserService {
    void createUser(Long id, String name, String email);
    void findById(Long id);
    void deleteById(Long id);
    void updateUser(Long id, String name, String email);
    void borrowBook(Long userId, Long bookId);
    void returnBook(Long userId, Long bookId);
}
