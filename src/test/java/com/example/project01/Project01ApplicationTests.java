package com.example.project01;

import com.example.project01.entity.Product;
import com.example.project01.repository.ProductRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class Project01ApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository repository;

    private   HttpHeaders httpHeaders = new HttpHeaders();

    @Before
    public  void init(){
       httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE);
    }
    @After
    public  void delete(){
        repository.deleteAll();
    }

    private Product createProduct(String name,int price){
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        return  product;
    }


    @Test
    public  void  testCreateProduct() throws Exception {

        HttpHeaders httpHeaders = new HttpHeaders();
       httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        JSONObject request = new JSONObject().put("name","HarryPorter");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/products",httpHeaders,request.toString());

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").hasJsonPath())
                .andExpect(jsonPath("$.name").hasJsonPath())
                .andExpect(jsonPath("$.price").hasJsonPath())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE));
    }


    public  void testGetProduct() throws Exception {

        Product product = createProduct("Econmics",450);
        repository.insert(product);



        mockMvc.perform(get("/products/" + product.getId()).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));

    }

    @Test
    public void testReplaceProduct() throws Exception {
        Product product = createProduct("ABC",100);
        repository.insert(product);

        JSONObject request = new JSONObject().put("name","Mac").put("price",550);

        mockMvc.perform(put("/products/" + product.getId()).headers(httpHeaders).content(request.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getId()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));
    }
    @Test
    public  void testDeleteProduct() throws Exception {
        Product product = createProduct("ABC",330);
        repository.insert(product);

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/"+ product.getId()).headers(httpHeaders))
                .andExpect(status().isNoContent());

        repository.findById(product.getId()).orElseThrow(RuntimeException ::new);
    }
    @Test
    public  void testSearchProductsSortByPriceAsc2() throws Exception{
        Product p1 = createProduct("A",350);
        Product p2 = createProduct("B",350);
        Product p3 = createProduct("C",350);
        Product p4 = createProduct("D",350);
        Product p5= createProduct("E",350);
        repository.insert(Arrays.asList(p1,p2,p3,p4,p5));

       MvcResult result =  mockMvc.perform(get("/products/").headers(httpHeaders).param("keyword","Manage")
                .param("orderBy","price").param("sortRule","asc")).andReturn();

        MockHttpServletResponse mockHttpResponse = result.getResponse();
        String responseJSONStr = mockHttpResponse.getContentAsString();
        JSONArray productJSONArray = new JSONArray(responseJSONStr);
        List<String> productIds = new ArrayList<>();
        for(int i = 0 ; i < productJSONArray.length(); i++){
            JSONObject productJson = productJSONArray.getJSONObject(i);
            productIds.add(productJson.getString("id"));
        }

        Assert.assertEquals(productIds.get(0),p1.getId());
        Assert.assertEquals(productIds.get(1),p2.getId());
        Assert.assertEquals(productIds.get(2),p3.getId());
        Assert.assertEquals(HttpStatus.OK.value(),mockHttpResponse.getStatus());
        Assert.assertEquals(MediaType.APPLICATION_JSON_VALUE,mockHttpResponse.getHeaders(httpHeaders.CONTENT_TYPE));
    }

    @Test
    public  void testSearchProductsSortByPriceAsc() throws Exception{
        Product p1 = createProduct("A",350);
        Product p2 = createProduct("B",350);
        Product p3 = createProduct("C",350);
        Product p4 = createProduct("D",350);
        Product p5= createProduct("E",350);
        repository.insert(Arrays.asList(p1,p2,p3,p4,p5));

        mockMvc.perform(get("/products/").headers(httpHeaders).param("keyword","Manage")
                        .param("orderBy","price").param("sortRule","asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(4)))
                .andExpect(jsonPath("$[0].id").value(p1.getId()))
                .andExpect(jsonPath("$[1].id").value(p2.getId()))
                .andExpect(jsonPath("$[2].id").value(p3.getId()));
    }


}
