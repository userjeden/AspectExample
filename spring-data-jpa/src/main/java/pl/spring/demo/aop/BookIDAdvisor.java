package pl.spring.demo.aop;
import org.springframework.aop.MethodBeforeAdvice;
import pl.spring.demo.annotation.NullableId;
import pl.spring.demo.common.Sequence;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.to.BookTo;
import pl.spring.demo.to.IdAware;
import java.lang.reflect.Method;
import java.util.List;

public class BookIDAdvisor implements MethodBeforeAdvice {

	private Sequence sequence;
	
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        if (hasAnnotation(method, o, NullableId.class)) {
            fillBookID(objects[0], o);
        }
    }

    private void fillBookID(Object book, Object bookDao) { 
        if (book instanceof IdAware && ((IdAware) book).getId() == null) {
        	BookTo sourceBook = (BookTo) book;
        	BookDao bookDaoImpl = (BookDao) bookDao;
        	List<BookTo> allBooks = bookDaoImpl.findAll();
        	sourceBook.setId(sequence.nextValue(allBooks));
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean hasAnnotation (Method method, Object o, Class annotationClazz) throws NoSuchMethodException {
        boolean hasAnnotation = method.getAnnotation(annotationClazz) != null;
        if (!hasAnnotation && o != null) {
            hasAnnotation = o.getClass().getMethod(method.getName(), method.getParameterTypes()).getAnnotation(annotationClazz) != null;
        }
        return hasAnnotation;
    }
    
    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }
    
}
