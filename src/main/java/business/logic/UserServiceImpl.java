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
            System.out.println("Пользователь с таким идентификатором уже существует.");
            return;
        }

        if (!isValidEmail(email)) {
            System.out.println("Некорректный формат почты.");
            return;
        }

        User newUser = new User();
        newUser.setId(id);
        newUser.setName(name);
        newUser.setEmail(email);
        userRepository.create(newUser);
        System.out.println("Пользователь успешно создан...");
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
    public void findById(Long id) {
        User user = userRepository.read(id);
        if (user != null) {
            System.out.println("Данные пользователя:");
            System.out.println("ID: " + user.getId());
            System.out.println("Name: " + user.getName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Взятые книги : " + user.getBorrowedBooksList());
        } else {
            System.out.println("Пользователь с ID " + id + " не найден.");
        }
    }

    @Override
    public void deleteById(Long id) {
        userRepository.delete(id);
        System.out.println("Пользователь успешно удален...");
    }

    @Override
    public void updateUser(Long id, String name, String email) {
        User user = userRepository.read(id);
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            userRepository.update(user);
        }
        System.out.println("Пользователь успешно обновлен...");
    }

    @Override
    public void borrowBook(Long userId, Long bookId) {
        User user = userRepository.read(userId);
        if (user == null) {
            System.out.println("Пользователь с ID " + userId + " не найден.");
            return;
        }

        Book book = bookRepository.read(bookId);
        if (book == null) {
            System.out.println("Книга с ID " + bookId + " не найдена.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Книга с ID " + bookId + " уже взята в аренду.");
            return;
        }

        user.getBorrowedBooks().add(book);
        book.setAvailable(false);
        System.out.println("Книга успешно взята в аренду...");
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
        System.out.println("Книга успешно возвращена.");
    }
}
