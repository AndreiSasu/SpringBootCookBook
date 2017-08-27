package org.test.bookpub.mainapp;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.test.bookpub.mainapp.entity.Author;
import org.test.bookpub.mainapp.entity.Book;
import org.test.bookpub.mainapp.entity.Publisher;
import org.test.bookpub.mainapp.repository.AuthorRepository;
import org.test.bookpub.mainapp.repository.BookRepository;
import org.test.bookpub.mainapp.repository.PublisherRepository;


@Order(Ordered.LOWEST_PRECEDENCE - 15) // must execute AFTER DbCountRunner otherwise it will report 0 
public class StartupRunner implements CommandLineRunner {
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private DataSource ds;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private PublisherRepository publisherRepository;

	@Override
	public void run(String... args) throws Exception {
		logger.info("Hello " + ds.toString());
		logger.info("Number of books: " + bookRepository.count());
		
		Author author = new Author("Alex", "Antonov");
		author = authorRepository.save(author);
		Publisher publisher = new Publisher("Packt");
		publisher = publisherRepository.save(publisher);
		Book book = new Book("978-1-78528-415-1", "Spring Boot Recipes",
		author, publisher);
		bookRepository.save(book);

	}

	@Scheduled(initialDelay = 1000, fixedRate = 10000)
	public void run() {
		logger.info("Number of books: " + bookRepository.count());
	}

}
