package crud.work;

import java.util.List;

import models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import models.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements CrudRepository<User, Long>{
    @Autowired
    @Qualifier("userContainer")
    private List<User> userContainer;

    @Qualifier("bookContainer")
    private List<Book> bookContainer;

    @Autowired
    public UserRepository(@Qualifier("userContainer") List<User> userContainer, @Qualifier("bookContainer") List<Book> bookContainer) {
        this.userContainer = userContainer;
        this.bookContainer = bookContainer;
    }

    @Override
    public void create(User user) {
        userContainer.add(user);
    }

    @Override
    public User read(Long id) {
        for (User user: userContainer) {
            if (user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    @Override
    public void update(User user) {
        for (int i = 0; i < userContainer.size(); i++) {
            if (userContainer.get(i).getId().equals(user.getId())) {
                userContainer.set(i, user);
                return;
            }
        }
    }


    @Override
    public void delete(Long id) {
        for (int i = 0; i < userContainer.size(); i++) {
            if (userContainer.get(i).getId().equals(id)) {
                User user = userContainer.get(i);
                List<Book> borrowedBooks = user.getBorrowedBooks();
                for (Book book : borrowedBooks) {
                    book.setAvailable(true);
                }
                userContainer.remove(i);
                return;
            }
        }
    }
}
