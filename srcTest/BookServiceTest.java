import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class BookServiceTest {

	BookService service;
	Book lotr;
	Book harry;
	Book harry2;
	
	@Before public void setUp(){
		service = new BookService();
		lotr = new Book("1234", "Lord of The Rings", "Tolkien", 1959);
		harry = new Book("4321","Harry Potter #1", "Rowling", 2000);
		harry2 = new Book("4322","Harry Potter #2", "Rowling", 2001);
	}
	
	@Test public void retrieveAnInsertedBook(){
		
		service.insert(lotr);
		Book found = service.retrive("1234");
		
		assertEquals("1234",found.isbn);
		assertEquals("Lord of The Rings",found.name);
		assertEquals("Tolkien",found.author);
		assertEquals((Integer)1959,found.year);
	}
	
	@Test(expected=MandatoryFieldMissing.class)
	public void isbnIsMandatoryOnInsert(){
		lotr.isbn = null;
		service.insert(lotr);
	}
	
	@Test(expected=MandatoryFieldMissing.class)
	public void nameIsMandatoryOnInsert(){
		
		lotr.name = null;
		service.insert(lotr);
	}
	
	@Test public void retrieveFindsBooksBasedOnIsbn(){
		service.insert(lotr);
		service.insert(harry);
		
		assertEquals("Expected LOTR for ISBN 1234",lotr, service.retrive("1234"));
		assertEquals("Expected Harry for ISBN 4321",harry, service.retrive("4321"));
	}
	
	@Test public void retrieveNullForNonExistentIsbn(){
		service.insert(lotr);
		
		assertNull("Expected Null for ISBN 1111",service.retrive("1111"));
		
	}
	
//	@Test(expected=DuplicatedISBN.class)
//	public void rejectsBooksWithTheSameIsbn(){
//		service.insert(createBook());
//		service.insert(createBook());
//	}

	@Test public void findsNoBookForAnEmptyDatabase(){
		assertEquals(0, service.find().size());
	}
	
	@Test public void findsTheInsertedBook(){
		service.insert(lotr);
		List<Book> searchResult = service.find();
		assertEquals(1, searchResult.size());
		assertEquals(lotr, searchResult.get(0));
	}
	
	@Test
	public void findBookByName(){

		service.insert(harry);
		service.insert(lotr);
		List<Book> searchResult = service.find(lotr.name);
		assertEquals(1, searchResult.size());
		assertEquals(lotr.name, searchResult.get(0).name);
	}
	
	@Test
	public void findBookByPartOfName(){
		service.insert(harry);
		service.insert(harry2);
		service.insert(lotr);
		List<Book> searchResult = service.find("Pot");
		assertEquals(2, searchResult.size());
		assertEquals(harry.name, searchResult.get(0).name);
		assertEquals(harry2.name, searchResult.get(1).name);
	}
	
	@Test
	public void dontFindNonExistentBooks(){
		service.insert(harry);
		service.insert(lotr);
		List<Book> searchResult = service.find("asdf");
		assertEquals(0, searchResult.size());		
	}
	
}
