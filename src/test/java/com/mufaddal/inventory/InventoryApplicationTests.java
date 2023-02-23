package com.mufaddal.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InventoryApplicationTests {

	@Mock
	private ProductsRepository productsRepository;

	@Test
	void contextLoads() {
	}

	@Test 
	void getAndSetProductId() {
		Product cut = new Product();
		Long productId = 5L;
		cut.setId(productId);
		assertEquals(productId, cut.getId());
	}

	@Test
	void getAndSetName() {
		Product cut = new Product();
		String productName = "Wood chair";
		cut.setName(productName);
		assertEquals(productName, cut.getName());
	}

	@Test
	void getAndSetShortDescription() {
		Product cut = new Product();
		String productShortDescription = "This is a very amazing and mind blowing chair.";
		cut.setShortDescription(productShortDescription);
		assertEquals(productShortDescription, cut.getShortDescription());
	}

	@Test
	void getAndSetLongDescription() {
		Product cut = new Product();
		String productLongDescription = "Our wood chairs are crazy cool. They are made from the finest woods found in the jungles of Africa. Our crafters which are the best in the world, turns those fine trees into these beautiful and extravagant wood chairs.";
		cut.setLongDescription(productLongDescription);
		assertEquals(productLongDescription, cut.getLongDescription());
	}

	@Test
	void getAndSetImage() {
		Product cut = new Product();
		String productImage = "http://www.image-server.com/images/cool-images/wood-table.jpg";
		cut.setImage(productImage);
		assertEquals(productImage, cut.getImage());
	}

	@Test
	void getAndSetPrice() {
		Product cut = new Product();
		Long productPrice = 10000000L;
		cut.setPrice(productPrice);
		assertEquals(productPrice, cut.getPrice());
	}

	@Test
	void allArgsConstructorProduct(){
		Long myId = 1L;
		String myName = "mobile phone";
		String myShortDescription = "A good invention";
		String myLongDescription = "Very useful thing";
		String myImage = "mobilePhone.jpg";
		Long myPrice = 40000L;
		Product cut = new Product(myId, myName, myShortDescription, myLongDescription, myImage, myPrice);
		assertEquals(myId, cut.getId());
		assertEquals(myName, cut.getName());
		assertEquals(myShortDescription, cut.getShortDescription());
		assertEquals(myLongDescription, cut.getLongDescription());
		assertEquals(myImage, cut.getImage());
		assertEquals(myPrice, cut.getPrice());
	}

	@Test
	void builderProduct(){
		Long myId = 1L;
		String myName = "mobile phone";
		String myShortDescription = "A good invention";
		String myLongDescription = "Very useful thing";
		String myImage = "mobilePhone.jpg";
		Long myPrice = 40000L;
		Product cut = Product.builder()
							.id(myId)
							.name(myName)
							.shortDescription(myShortDescription)
							.longDescription(myLongDescription)
							.image(myImage)
							.price(myPrice)
							.build();
		assertEquals(myId, cut.getId());
		assertEquals(myName, cut.getName());
		assertEquals(myShortDescription, cut.getShortDescription());
		assertEquals(myLongDescription, cut.getLongDescription());
		assertEquals(myImage, cut.getImage());
		assertEquals(myPrice, cut.getPrice());
	}

	@Test
	void canGetAllProducts(){
		Product cut1 = Product.builder()
							.id(1L)
							.name("glass bottle")
							.shortDescription("glass bottle")
							.longDescription("glass bottle")
							.image("glass_bottle.png")
							.price(100L)
							.build();
		Product cut2 = Product.builder()
							.id(2L)
							.name("clay dish")
							.shortDescription("clay dish")
							.longDescription("clay dish")
							.image("clay_dish.png")
							.price(200L)
							.build();
		given(productsRepository.findAll()).willReturn(List.of(cut1, cut2));
		List<Product> productsList = productsRepository.findAll();
		assertNotNull(productsList);
		assertEquals(2, productsList.size());
	}

	@Test
	void canGetAProduct(){
		Long myId = 1L;
		String myName = "mobile phone";
		String myShortDescription = "A good invention";
		String myLongDescription = "Very useful thing";
		String myImage = "mobilePhone.jpg";
		Long myPrice = 40000L;
		Product cut = Product.builder()
							.id(myId)
							.name(myName)
							.shortDescription(myShortDescription)
							.longDescription(myLongDescription)
							.image(myImage)
							.price(myPrice)
							.build();
		given(productsRepository.findById(myId)).willReturn(Optional.of(cut));
		Product foundProduct = productsRepository.findById(cut.getId()).get();
		assertEquals(cut.getName(), foundProduct.getName());
		assertEquals(cut.getShortDescription(), foundProduct.getShortDescription());
		assertEquals(cut.getLongDescription(), foundProduct.getLongDescription());
		assertEquals(cut.getPrice(), foundProduct.getPrice());
	}

	@Test
	void canSaveProduct(){
		Long myId = 1L;
		String myName = "mobile phone";
		String myShortDescription = "A good invention";
		String myLongDescription = "Very useful thing";
		String myImage = "mobilePhone.jpg";
		Long myPrice = 40000L;
		Product cut = Product.builder()
							.id(myId)
							.name(myName)
							.shortDescription(myShortDescription)
							.longDescription(myLongDescription)
							.image(myImage)
							.price(myPrice)
							.build();
		given(productsRepository.save(cut)).willReturn(cut);
		Product savedProduct = productsRepository.save(cut);
		assertNotNull(savedProduct);
	}

}
