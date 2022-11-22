package com.example.project01;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.swing.tree.TreeNode;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class ObjectMapperTest {
    private ObjectMapper mapper  = new ObjectMapper();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  static class  Book{
        private  String id;
        private  String name;
        private  int price;
        @JsonIgnore
        private  String isbn;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date createdTime;
        @JsonUnwrapped
        private Publisher publisher;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public Date getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(Date createdTime) {
            this.createdTime = createdTime;
        }

        @JsonUnwrapped
        public Publisher getPublisher() {
            return publisher;
        }

        public void setPublisher(Publisher publisher) {
            this.publisher = publisher;
        }


    }

    private  static  class  Publisher{
        private  String companyName;
        private  String address;
        @JsonProperty("telephone")
        private  String tel;

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }
    //Object as a book  to JSON Object
    @Test
    public void  testSerializeBookToJSON() throws  Exception{
        Book book = new Book();
        book.setId("B0001");
        book.setName("Computer Science");
        book.setPrice(200);
        book.setIsbn("123-1233");
        book.setCreatedTime(new Date());


        String bookJSONStr = mapper.writeValueAsString(book);
        JSONObject object = new JSONObject(bookJSONStr);

        Assert.assertEquals(book.getId(),object.getString("id"));
        Assert.assertEquals(book.getName(),object.getString("name"));
        Assert.assertEquals(book.getPrice(),object.getString("price"));
        Assert.assertEquals(book.getIsbn(),object.getString("isbn"));
        Assert.assertEquals(book.getCreatedTime().getTime(),object.getLong("createTime"));
    }
    // JSON Object to Object as a Publisher
    @Test
    public  void testDeserializeJSONToPublisher() throws  Exception{
        JSONObject publisherJSON = new JSONObject()
                .put("companyName","Taipei Caompany")
                .put("address","Taipei")
                .put("tel","02 27880324");
        String  publisherJSONStr =  publisherJSON.toString();
        Publisher publisher = mapper.readValue(publisherJSONStr,Publisher.class);

        Assert.assertEquals(publisherJSON.getString("companyName"),publisher.getCompanyName());
        Assert.assertEquals(publisherJSON.getString("address"),publisher.getAddress());
        Assert.assertEquals(publisherJSON.getString("tel"),publisher.getTel());

    }





}

