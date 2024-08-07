package com.example.productservicedemo.services;

import com.example.productservicedemo.dtos.FakeStoreProductsDto;
import com.example.productservicedemo.models.Category;
import com.example.productservicedemo.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//@Primary
@Service("fakeProductService")
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate;
    private RedisTemplate redisTemplate;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate,
                                   RedisTemplate redisTemplate){
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Product getSingleproduct(Long id){

        Product p = (Product) redisTemplate.opsForHash().get("PRODUCTS", "PRODUCT_" + id);

        if(p != null){
            return p;
        }


        FakeStoreProductsDto productsDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductsDto.class
        );

        Product p1 = convertFakeStoreDtoToProduct(productsDto);
        redisTemplate.opsForHash().put("PRODUCTS", "PRODUCT_"+ id, p1);

        //convert fakestoredto to product to return product
        return p1;
    }

    public Product convertFakeStoreDtoToProduct(FakeStoreProductsDto productsDto){
        Product product = new Product();
        product.setTitle(productsDto.getTitle());
        product.setPrice(productsDto.getPrice());
        product.setCategory(new Category());
        product.setDescription(productsDto.getDescription());
        product.setImage(productsDto.getImage());
        product.getCategory().setName(productsDto.getCategory());
        return product;
    }

    public FakeStoreProductsDto convertProductToFakeStoreDto(Product product){
        FakeStoreProductsDto productsDto = new FakeStoreProductsDto();
        productsDto.setTitle(product.getTitle());
        productsDto.setPrice(product.getPrice());
        productsDto.setDescription(product.getDescription());
        productsDto.setImage(product.getImage());
        productsDto.setCategory(product.getCategory().getName());
        return productsDto;
    }

    public Product createProduct(Product product){
       FakeStoreProductsDto productDto = convertProductToFakeStoreDto(product);
       productDto.setDescription("new product added");
       productDto.setTitle("new product");
       productDto.setPrice(12.5);
       productDto.setCategory("electronic");
       FakeStoreProductsDto responseDto =  restTemplate.postForObject("https://fakestoreapi.com/products",
               productDto,
                FakeStoreProductsDto.class);
       if(responseDto != null){
           System.out.println("Product created successfully");
           System.out.println("Product id: " + responseDto.getId());
           return convertFakeStoreDtoToProduct(responseDto);
       }
       System.out.println("Product creation is failed");
       return null;
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber,
                                        int pageSize,
                                        String sortBy,
                                        String sortOrder) {
        FakeStoreProductsDto[] productsDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductsDto[].class);

        assert productsDto != null;
        if(productsDto.length != 0){
            List<Product> products = new ArrayList<>();
            for(FakeStoreProductsDto productDto : productsDto){
                products.add(convertFakeStoreDtoToProduct(productDto));
            }
            return new PageImpl<>(products);
            //return products;
        }else{
            System.out.println("No products found");
        }
        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product){
//        FakeStoreProductsDto productsDto = restTemplate.patchForObject(
//                "https://fakestoreapi.com/products/" + id,
//                product,
//                FakeStoreProductsDto.class
//        );
//
//        /*productsDto.setTitle("Test product");
//        productsDto.setPrice(13.5);
//        productsDto.setDescription("lorem ipsum set");
//        productsDto.setCategory("electronic");*/

        return null;
    }


    public Product replaceProduct(Long id, Product product){
        FakeStoreProductsDto fakeStoreProductsDto = new FakeStoreProductsDto();
        fakeStoreProductsDto.setTitle(product.getTitle());
        fakeStoreProductsDto.setPrice(product.getPrice());
        fakeStoreProductsDto.setDescription(product.getDescription());
        fakeStoreProductsDto.setImage(product.getImage());
        fakeStoreProductsDto.setCategory(product.getCategory().getName());


        RequestCallback requestCallback =  restTemplate.httpEntityCallback(fakeStoreProductsDto, FakeStoreProductsDto.class);
        HttpMessageConverterExtractor<FakeStoreProductsDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductsDto.class, restTemplate.getMessageConverters());

        FakeStoreProductsDto responseDto =  restTemplate.execute(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.PUT,
                requestCallback,
                responseExtractor);

        return convertFakeStoreDtoToProduct(responseDto);
    }


    @Override
    public boolean deleteProduct(Long id) {
        FakeStoreProductsDto responseDto = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.DELETE,
                null,
                FakeStoreProductsDto.class).getBody();

        if(responseDto != null) {
            System.out.println("Product deleted successfully");
            //return convertFakeStoreDtoToProduct(responseDto);
            return true;
        }

        System.out.println("Product deletion failed");
        return false;
    }

    @Override
    public List<Product> getProductsInCategory(String category){
        FakeStoreProductsDto[] productsDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/category/" + category,
                FakeStoreProductsDto[].class);

        assert productsDto != null;
        if(productsDto.length != 0){
            List<Product> products = new ArrayList<>();
            for(FakeStoreProductsDto productDto : productsDto){
                products.add(convertFakeStoreDtoToProduct(productDto));
            }
            return products;
        }else{
            System.out.println("No products found");
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        return null;
    }
}
