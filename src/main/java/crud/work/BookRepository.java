package crud.work;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import models.Book;

@Component
public class BookRepository implements CrudRepository<Book, Long> {
    @Autowired
    @Qualifier("bookContainer")
    private List<Book> bookContainer;

    @Autowired
    public BookRepository(List<Book> bookContainer) {
        this.bookContainer = bookContainer;
    }

    @Override
    public void create(Book book) {
        bookContainer.add(book);
    }

    @Override
    public Book read(Long id) {
        for (Book book : bookContainer) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public void update(Book book) {
        for (int i = 0; i < bookContainer.size(); i++) {
            if (bookContainer.get(i).getId().equals(book.getId())) {
                bookContainer.set(i, book);
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        for (int i = 0; i < bookContainer.size(); i++) {
            if (bookContainer.get(i).getId().equals(id)) {
                bookContainer.remove(i);
                return;
            }
        }
    }
}