package site.jordanthomas.BookStore;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookStoreApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getAllBooks() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/books")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	void getSingleBook() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/books/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void returnsNotFoundForInvalidSingleBook() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/books/5")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();
	}

	@Test
	public void addsNewBook() throws Exception {
		String newBook = "{\"title\":\"Dreamcast\",\"author\":\"Sega\",\"price\":199.99}";
		mockMvc.perform(MockMvcRequestBuilders.post("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newBook)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
	}

	@Test
	public void replaceBook() throws Exception {
		String newBook = "{\"title\":\"Dreamcast\",\"author\":\"Sega\",\"price\":199.99}";
		mockMvc.perform(MockMvcRequestBuilders.put("/books/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(newBook)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
	}

	@Test
	public void deleteBook() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/books/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andReturn();
	}
}
