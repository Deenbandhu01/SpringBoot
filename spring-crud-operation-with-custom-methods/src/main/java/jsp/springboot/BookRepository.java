package jsp.springboot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer>{
	
	List<Book> findByAuthor(String author);
	
	List<Book> findByAuthorAndGenre(String author,String genre);

	List<Book> findByPriceGreaterThan(double price);
	
	List<Book> findByPriceLessThan(double price);
	
	List<Book> findByPriceBetween(double startRange, double endRange);

}
