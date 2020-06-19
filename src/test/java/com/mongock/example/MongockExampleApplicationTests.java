package com.mongock.example;

import com.mongock.example.domain.Product;
import com.mongock.example.repository.ProductRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = {MongockExampleApplication.class}, webEnvironment = RANDOM_PORT)
@ContextConfiguration(initializers = MongoDbContainerInitializer.Initializer.class)
class MongockExampleApplicationTests {

	private MediaType contentType =
			new MediaType(APPLICATION_JSON.getType(), APPLICATION_JSON.getSubtype());

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ProductRepository productRepository;

	@Test
	public void should_retrieve_handler_history() throws Exception {
		Product product = productRepository.findByName("Nike SB");
		String result = mockMvc
				.perform(get("/product"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(3)))
				.andReturn().getResponse().getContentAsString();
		Assertions.assertTrue(result.contains(product.getName()));
		Assertions.assertTrue(result.contains(product.getId().toHexString()));
		Assertions.assertTrue(result.contains(product.getBrand()));
	}
}
