package pl.spring.demo.service;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.to.BookTo;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "CommonServiceTest-context.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookServiceImplTest {

	
    @Autowired
    private BookService bookService;
    
    @Autowired
    private CacheManager cacheManager;

    
    public void cleanCacheContent() {
        cacheManager.getCache("booksCache").clear();
    }

    
    @Test
    public void testShouldFindAllBooks() {
    	
        // when
    	cleanCacheContent();
        List<BookTo> allBooks = bookService.findAllBooks();
        
        // then
        assertNotNull(allBooks);
        assertEquals(6, allBooks.size());
    }

    
    @Test
    public void testShouldFindAllBooksByTitle() {
    	
        // given
        final String title = "Opium w rosole";
        
        // when
        cleanCacheContent();
        List<BookTo> booksByTitle = bookService.findBooksByTitle(title);
        
        // then
        assertNotNull(booksByTitle);
        assertEquals(1, booksByTitle.size());
    }
    
    
    @Test(expected = BookNotNullIdException.class)
    public void testShouldThrowBookNotNullIdException() {
    	
    	// given
    	final BookTo bookToSave = new BookTo();
    	bookToSave.setId(22L);
    	
    	// when
    	cleanCacheContent();
    	bookService.saveBook(bookToSave);
    	
    	// then
    	fail("test should throw BookNotNullIdException");
    }
    
    
    @Test
    public void testShouldSaveBookWithProperId() {
    	
        // given
        final BookTo bookToSave = new BookTo();
        bookToSave.setTitle("MyBook");
        
        // when
        BookTo bookSaved = bookService.saveBook(bookToSave);
        System.out.println("SAVED id: " + bookSaved.getId() + " title: " + bookSaved.getTitle());
        cleanCacheContent();
        
        // then
        List<BookTo> allBooks = bookService.findAllBooks();
        List<BookTo> booksByTitle = bookService.findBooksByTitle("MyBook");
        
        assertEquals("MyBook", booksByTitle.get(0).getTitle());
        assertTrue(booksByTitle.get(0).getId().equals(7L));
        
        System.out.println("*** in BookStore ***");
        for(BookTo b : allBooks){
        	System.out.println("BOOK id: " + b.getId() + " title: " + b.getTitle());
        }
    }
    
    
}


