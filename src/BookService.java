import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class BookService {

	ArrayList<Book> books = new ArrayList<Book>();
	
	public void insert(Book book) {
		validate(book);
		books.add(book);
	}

	private void validate(Book book) {
		if (book.isbn == null || book.name == null) 
			throw new MandatoryFieldMissing();
	}

	public Book retrive(String isbn) {
		for(Book b: books){
			if(b.isbn == isbn) return b;
		}
			
		return null;
	}

	public List<Book> find() {
		return books;
	}

	public List<Book> find(String name) {
		List<Book> founds = new ArrayList<Book>();
		
		for (Book book : books) {
			if (book.name.indexOf(name) != -1) {
				founds.add(book);
			}
		}
		return founds;
	}

}
