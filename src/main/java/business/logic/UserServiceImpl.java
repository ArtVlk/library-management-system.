package business.logic;

import models.Book;
import models.User;
import crud.work.BookRepository;
import crud.work.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void createUser(Long id, String name, String email) {
        if (userRepository.read(id) != null) {
            throw new RuntimeException("Пользователь с таким идентификатором уже существует.");
        }

        if (!isValidEmail(email)) {
            throw new RuntimeException("Некорректный формат почты.");
        }

        User newUser = new User();
        newUser.setId(id);
        newUser.setName(name);
        newUser.setEmail(email);
        userRepository.create(newUser);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.read(id);
        if (user == null) {
            throw new RuntimeException("Пользователь с ID " + id + " не найден.");
        }
        return user;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.delete(id);
    }

    @Override
    public void updateUser(Long id, String name, String email) {
        User user = userRepository.read(id);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            userRepository.update(user);
        }
    }

    @Override
    public void borrowBook(Long userId, Long bookId) {
        User user = userRepository.read(userId);
        if (user == null) {
            throw new RuntimeException("Пользователь с ID " + userId + " не найден.");
        }

        Book book = bookRepository.read(bookId);
        if (book == null) {
            throw new RuntimeException("Книга с ID " + bookId + " не найдена.");
        }

        if (!book.isAvailable()) {
            throw new RuntimeException("Книга с ID " + bookId + " уже взята в аренду.");
        }

        user.getBorrowedBooks().add(book);
        book.setAvailable(false);
    }

    @Override
    public void returnBook(Long userId, Long bookId) {
        User user = userRepository.read(userId);
        Book book = bookRepository.read(bookId);
        if (user != null && book != null && !book.isAvailable()) {
            user.returnBook(book);
            book.setAvailable(true);
            bookRepository.update(book);
        } else {
            throw new RuntimeException("User is none or book is already available");
        }
    }
}
