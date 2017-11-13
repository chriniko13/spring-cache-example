package spring.cache.example.service;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.stereotype.Service;
import spring.cache.example.domain.Foo;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class FooSearchService {

    private final CompositeCacheManager compositeCacheManager;

    private List<Foo> foosDB;

    public FooSearchService(CompositeCacheManager compositeCacheManager) {
        this.compositeCacheManager = compositeCacheManager;
    }

    @PostConstruct
    public void setup() {

        //init db...
        foosDB = new LinkedList<>();
        foosDB.add(new Foo("chri"));
        foosDB.add(new Foo("niko"));

        //populate cache...
        Cache foosCache = compositeCacheManager.getCache("foos");
        IntStream.rangeClosed(1, 10).boxed().forEach(idx -> {
            foosCache.put(idx.toString(), new Foo(idx.toString()));
        });

    }

    @Cacheable(value = {"foos"})
    public Foo get(String idx) {

        System.out.println("FooSearchService#get(String) --- method called!");

        return foosDB.stream().filter(foo -> foo.getValue().equals(idx)).findAny().orElse(null);
    }

}
