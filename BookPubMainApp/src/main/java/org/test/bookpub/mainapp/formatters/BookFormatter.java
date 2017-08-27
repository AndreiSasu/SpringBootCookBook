package org.test.bookpub.mainapp.formatters;

import java.util.Locale;

import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;
import org.test.bookpub.mainapp.entity.Book;
import org.test.bookpub.mainapp.repository.BookRepository;

/**
 * Let’s suppose that for our application, we would like to have a Formatter
 * that would take an ISBN number of a book in a String form and convert it to a
 * Book entity object.
 * 
 * This way, we can define the controller request methods with a Book argument
 * when the request URL signature only contains an ISBN number or a database ID.
 * 
 * Must be registered in the config, see WebConfiguration
 * 
 * Difference between a Formatter and a PropertyEditor is that the Formatter is global
 * 
 * 
 * @author andrei
 *
 */
public class BookFormatter implements Formatter<Book> {
	private BookRepository repository;

	public BookFormatter(BookRepository repository) {
		this.repository = repository;
	}

	@Override
	public Book parse(String bookIdentifier, Locale locale) throws ParseException {
		Book book = repository.findBookByIsbn(bookIdentifier);
		return book != null ? book : repository.findOne(Long.valueOf(bookIdentifier));
	}

	@Override
	public String print(Book book, Locale locale) {
		return book.getIsbn();
	}
}
