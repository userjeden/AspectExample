package pl.spring.demo.dao.impl;
import pl.spring.demo.annotation.NullableId;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.to.BookTo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookDaoImpl implements BookDao {

    private final Set<BookTo> ALL_BOOKS = new HashSet<>();

    public BookDaoImpl() {
        addTestBooks();
    }

    @Override
    public List<BookTo> findAll() {
        return new ArrayList<>(ALL_BOOKS);
    }

    @Override
    public List<BookTo> findBookByTitle(String title) {
    	return ALL_BOOKS.stream().filter(u -> u.getTitle().equals(title)).collect(Collectors.toList());
    }

    @Override
    public List<BookTo> findBooksByAuthor(String author) {
        return null;
    }

    @Override
    @NullableId
    public BookTo save(BookTo book) {
    	
//    	  // MOVED TO ASPECT
//        if (book.getId() == null) {
//            book.setId(sequence.nextValue(ALL_BOOKS));
//        }
    	
        ALL_BOOKS.add(book);
        return book;
    }
  
    private void addTestBooks() {
        ALL_BOOKS.add(new BookTo(1L, "Romeo i Julia", "Wiliam Szekspir"));
        ALL_BOOKS.add(new BookTo(2L, "Opium w rosole", "Hanna Ożogowska"));
        ALL_BOOKS.add(new BookTo(3L, "Przygody Odyseusza", "Jan Parandowski"));
        ALL_BOOKS.add(new BookTo(4L, "Awantura w Niekłaju", "Edmund Niziurski"));
        ALL_BOOKS.add(new BookTo(5L, "Pan Samochodzik i Fantomas", "Zbigniew Nienacki"));
        ALL_BOOKS.add(new BookTo(6L, "Zemsta", "Aleksander Fredro"));
    }
    
}

