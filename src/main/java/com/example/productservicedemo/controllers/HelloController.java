package com.example.productservicedemo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// THIS CLASS WILL BE SERVING REST (HTTP) APIs
// localhost:8080/hello
@RequestMapping("/hello")
// This controller will have different methods that will be serving the requests at /hello end point
public class HelloController {

    @GetMapping("/say/{name}")
    // GET request to /hello/say will be served by this method
    public String sayHello(@PathVariable("name") String name){
        return "Hello" + name;
    }



}
