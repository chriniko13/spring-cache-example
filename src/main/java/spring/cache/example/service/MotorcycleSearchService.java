package spring.cache.example.service;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import spring.cache.example.domain.Motorcycle;

import java.util.*;
import java.util.stream.Collectors;

public class MotorcycleSearchService {

    private Map<String, List<Motorcycle>> motorcyclesByBrandDb;

    public void init() throws Exception {
        initializeDB();
    }

    @Cacheable(value = "motorcycles", key = "#a0")
    public Optional<List<Motorcycle>> find(String brand) {

        System.out.println("MotorcycleSearchService#find(String) --- method called!");

        return Optional.ofNullable(motorcyclesByBrandDb.get(brand));

    }

    @Cacheable(value = "suzukis", key = "#a0.concat(#a1)", condition = "#a0.equalsIgnoreCase('suzuki')")
    public Optional<Motorcycle> find(String brand, String model) {

        System.out.println("MotorcycleSearchService#find(String,String) --- method called!");

        return Optional
                .ofNullable(motorcyclesByBrandDb.get(brand))
                .flatMap(motorcycles -> motorcycles.stream().filter(motorcycle -> motorcycle.getModel().equalsIgnoreCase(model)).findAny());

    }

    @CacheEvict(value = "motorcycles")
    public void delete(String brand) {

        System.out.println("MotorcycleSearchService#delete(String) --- method called!");

        motorcyclesByBrandDb.remove(brand);
    }

    @CachePut(value = "motorcycles", key = "#a0.get(0).brand")
    public Optional<List<Motorcycle>> add(List<Motorcycle> motorcycles) {

        System.out.println("MotorcycleSearchService#add(List<Motorcycle>) --- method called!");

        if (motorcycles == null || motorcycles.isEmpty()) {
            throw new IllegalArgumentException("please add some motorcycles.");
        }

        Set<String> brands = motorcycles.stream().map(Motorcycle::getBrand).collect(Collectors.toSet());
        if (brands.size() != 1) {
            throw new IllegalArgumentException("all motorcycles must have the same brand name.");
        }

        motorcyclesByBrandDb.put(motorcycles.get(0).getBrand(), motorcycles);

        return Optional.of(motorcycles);
    }

    @CacheEvict(value = {"motorcycles"}, allEntries = true)
    public void clearMotorcyclesCache() {
        System.out.println("MotorcycleSearchService#clearMotorcyclesCache --- method called!");
    }

    private void initializeDB() {
        motorcyclesByBrandDb = new LinkedHashMap<>();

        motorcyclesByBrandDb.put("Suzuki",
                Arrays.asList(
                        new Motorcycle("Suzuki", "GSXR-600"),
                        new Motorcycle("Suzuki", "GSXR-1000"),
                        new Motorcycle("Suzuki", "Vstrom-1000")));

        motorcyclesByBrandDb.put("Yamaha",
                Arrays.asList(
                        new Motorcycle("Yamaha", "R6"),
                        new Motorcycle("Yamaha", "R1"),
                        new Motorcycle("Yamaha", "MT-09")));

        motorcyclesByBrandDb.put("Kawasaki",
                Arrays.asList(
                        new Motorcycle("Kawasaki", "ZX10R"),
                        new Motorcycle("Kawasaki", "ZX6R"),
                        new Motorcycle("Kawasaki", "Z1000")));

        motorcyclesByBrandDb.put("Honda",
                Arrays.asList(
                        new Motorcycle("Honda", "CBR1000RR"),
                        new Motorcycle("Honda", "CB600RR"),
                        new Motorcycle("Honda", "CB1000R")));
    }
}
