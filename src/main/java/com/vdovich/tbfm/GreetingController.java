package com.vdovich.tbfm;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.atomic.AtomicLong;

@RequestMapping
public class GreetingController {
    private static final String TEMPLATE = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping
    public Greeting greeting(@RequestParam(value = "name",defaultValue = "World")String name){
        return new Greeting(counter.incrementAndGet(),String.format(TEMPLATE,name));
    }
}
