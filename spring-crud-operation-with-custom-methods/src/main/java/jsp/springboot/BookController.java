package jsp.springboot;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/books")
public class BookController {
	@Autowired
	private BookRepository bookRepository;

	@PostMapping
	public ResponseEntity<ResponseStructure<Book>> saveBook(@RequestBody Book book) {
		ResponseStructure<Book> response = new ResponseStructure<Book>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Book record inserted");
		response.setData(bookRepository.save(book));
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/All")
	public ResponseEntity<ResponseStructure<List<Book>>> saveBooks(@RequestBody List<Book> books) {
		ResponseStructure<List<Book>> responseStructure = new ResponseStructure<List<Book>>();
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage("Books record is inserted");
		responseStructure.setData(bookRepository.saveAll(books));
		return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<ResponseStructure<List<Book>>> getAllBooks() {
		ResponseStructure<List<Book>> responseStructure = new ResponseStructure<List<Book>>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("Books record is fetched");
		responseStructure.setData(bookRepository.findAll());
		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Book>> getBookById(@PathVariable Integer id) {
		ResponseStructure<Book> responseStructure = new ResponseStructure<Book>();
		Optional<Book> opt = bookRepository.findById(id);
		if (opt.isPresent()) {
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Books record is Fetching");
			responseStructure.setData(opt.get());
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else
			throw new NoRecordAvailableException("Record is not available For this Id");
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<Book>> updateBook(@RequestBody Book book) {
		ResponseStructure<Book> responseStructure = new ResponseStructure<Book>();
		if (book.getId() != null) {
			Optional<Book> opt = bookRepository.findById(book.getId());
			if (opt.isPresent()) {
				responseStructure.setStatusCode(HttpStatus.CREATED.value());
				responseStructure.setMessage("Books record is updated");
				responseStructure.setData(bookRepository.save(book));
				return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);

			} else {
				throw new IdNotFoundException("Id not available");
			}
		} else {
			throw new IdNotFoundException("Id not available");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteBook(@PathVariable Integer id) {
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();
		Optional<Book> opt = bookRepository.findById(id);
		if (opt.isPresent()) {
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Books record is deleted");
			responseStructure.setData("Success");
			bookRepository.delete(opt.get());
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else {
			throw new IdNotFoundException("Id not available");
		}
	}

	// custom methods
	@GetMapping("/author/{author}")
	public ResponseEntity<ResponseStructure<List<Book>>> getBookByAuthor(@PathVariable String author) {
		ResponseStructure<List<Book>> responseStructure = new ResponseStructure<>();
		List<Book> book = bookRepository.findByAuthor(author);
		if (!book.isEmpty()) {
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Books record is Fetching");
			responseStructure.setData(book);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else
			throw new NoRecordAvailableException("Record is not available For this Id");
	}

	@GetMapping("/authors/{author}/{genre}")
	public ResponseEntity<ResponseStructure<List<Book>>> getBookByAuthorAndGenre(@PathVariable String author,@PathVariable String genre) {
		ResponseStructure<List<Book>> responseStructure = new ResponseStructure<>();
		List<Book> book = bookRepository.findByAuthorAndGenre(author, genre);
		if (!book.isEmpty()) {
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Books record is Fetching");
			responseStructure.setData(book);
			return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		} else
			throw new NoRecordAvailableException("Record is not available For this Id");
	}
}
