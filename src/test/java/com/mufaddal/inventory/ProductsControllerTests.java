package com.mufaddal.inventory;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class ProductsControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductsRepository productsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void postANewProduct() throws Exception {
        Product product = Product.builder()
                .id(2L)
                .name("clay dish")
                .shortDescription("clay dish")
                .longDescription("clay dish")
                .image("clay_dish.png")
                .price(200L)
                .build();
        given(productsRepository.save(any(Product.class))).willAnswer((invocation) -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.shortDescription", is(product.getShortDescription())))
                .andExpect(jsonPath("$.longDescription", is(product.getLongDescription())))
                .andExpect(jsonPath("$.image", is(product.getImage())))
                .andExpect(jsonPath("$.price", is(product.getPrice().intValue())));
    }

    @Test
    public void getAllTheProducts() throws Exception {
        Product product1 = Product.builder()
                .id(2L)
                .name("clay dish")
                .shortDescription("clay dish")
                .longDescription("clay dish")
                .image("clay_dish.png")
                .price(200L)
                .build();
        Product product2 = Product.builder()
                .id(2L)
                .name("headphones")
                .shortDescription("cool headphones")
                .longDescription("very nice headphones")
                .image("headphones.png")
                .price(500L)
                .build();
        List<Product> products = List.of(product1, product2);
        given(productsRepository.findAll()).willReturn(products);

        ResultActions response = mockMvc.perform(get("/api/products/all"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(products.size())));
    }

    @Test
    public void getProductById() throws Exception {
        Product product = Product.builder()
                .id(2L)
                .name("headphones")
                .shortDescription("cool headphones")
                .longDescription("very nice headphones")
                .image("headphones.png")
                .price(500L)
                .build();
        given(productsRepository.findById(2L)).willReturn(Optional.of(product));

        ResultActions response = mockMvc.perform(get("/api/products/{id}", product.getId()));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.longDescription", is(product.getLongDescription())))
                .andExpect(jsonPath("$.image", is(product.getImage())));
    }

    @Test
    public void cantGetProductByIdIfTheThingDoesntExist() throws Exception{
        Long productId = 1L;
        given(productsRepository.findById(productId)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/api/products/{id}", productId));

        response.andExpect(status().isNotFound());
    }
}
