package jsp.springboot;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booked")
public class BookController {
	@Autowired
	private BookRepository bookRepository;
	
	@PostMapping
	public ResponseStructure<Book> saveBook(@RequestBody Book book) {
		ResponseStructure<Book> response = new ResponseStructure<Book>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Book record inserted");
		response.setData(bookRepository.save(book));
		return response;
	}
	
	@PostMapping("/All")
	public ResponseStructure<List<Book>> saveBooks(@RequestBody List<Book> books) {
		ResponseStructure<List<Book>> responseStructure = new ResponseStructure<List<Book>>();
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage("Books record is inserted");
		responseStructure.setData(bookRepository.saveAll(books));
		return responseStructure;
	}
	
	@GetMapping
	public ResponseStructure<List<Book>> getAllBooks(){
		ResponseStructure<List<Book>> responseStructure = new ResponseStructure<List<Book>>();
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage("Books record is inserted");
		responseStructure.setData(bookRepository.findAll());
		return responseStructure;
	}
	
	@GetMapping("/{id}")
	public ResponseStructure<Book> getBookById(@PathVariable Integer id) {
		ResponseStructure<Book> responseStructure = new ResponseStructure<Book>();
		Optional<Book> opt = bookRepository.findById(id);
		if(opt.isPresent()) {
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Books record is Fetching");
			responseStructure.setData(opt.get());
			return responseStructure;
		}else {
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Id is not found");
			return responseStructure;
		}
	}
	
	@PutMapping
	public ResponseStructure<Book> updateBook(@RequestBody Book book) {
		ResponseStructure<Book> responseStructure = new ResponseStructure<Book>();
		if(book.getId()!=null) {
			Optional<Book> opt = bookRepository.findById(book.getId());
			if(opt.isPresent()) {
				responseStructure.setStatusCode(HttpStatus.CREATED.value());
				responseStructure.setMessage("Books record is updated");
				responseStructure.setData(bookRepository.save(opt.get()));
				return responseStructure;
				
			}else {
				responseStructure.setStatusCode(HttpStatus.OK.value());
				responseStructure.setMessage("Id is invalid");
				return responseStructure;
			}
		}
		else {
			responseStructure.setStatusCode(HttpStatus.CREATED.value());
			responseStructure.setMessage("Id is not available");
			return responseStructure;
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseStructure<String> deleteBook(@PathVariable Integer id) {
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();
		Optional<Book> opt = bookRepository.findById(id);
		if(opt.isPresent()) {
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Books record is deleted");
			responseStructure.setData("Success");
			bookRepository.delete(opt.get());
			return responseStructure;
		}else {
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Id is not found");
			responseStructure.setData("Success");
			
			return responseStructure;
		}
	}
}
