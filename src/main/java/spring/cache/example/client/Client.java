package spring.cache.example.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.cache.example.domain.Foo;
import spring.cache.example.domain.Motorcycle;
import spring.cache.example.service.FooSearchService;
import spring.cache.example.service.MotorcycleSearchService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Client {

    public static void main(String[] args) {

        final ApplicationContext context = new ClassPathXmlApplicationContext("appconfig-root.xml");
        final MotorcycleSearchService motorcycleSearchService = context.getBean("motorcycleSearchService", MotorcycleSearchService.class);
        final FooSearchService fooSearchService = context.getBean("fooSearchService", FooSearchService.class);

        firstExample(motorcycleSearchService);

        secondExample(motorcycleSearchService);

        thirdExample(motorcycleSearchService);

        fourthExample(motorcycleSearchService);

        fifthExample(motorcycleSearchService);

        sixthExample(fooSearchService);

    }

    private static void firstExample(MotorcycleSearchService motorcycleSearchService) {
        //1st example...
        System.out.println("------- 1st example ---------");


        Optional<List<Motorcycle>> searchResultFindByBrand = motorcycleSearchService.find("Suzuki");
        searchResultFindByBrand.ifPresent(System.out::println);

        System.out.println("\n");

        searchResultFindByBrand = motorcycleSearchService.find("Suzuki");
        searchResultFindByBrand.ifPresent(System.out::println);

        System.out.println("\n");
    }

    private static void secondExample(MotorcycleSearchService motorcycleSearchService) {
        //2nd example...
        System.out.println("------- 2nd example ---------");

        Optional<Motorcycle> searchResultFindByBrandAndModel = motorcycleSearchService.find("Kawasaki", "zx10r");
        searchResultFindByBrandAndModel.ifPresent(System.out::println);

        System.out.println("\n");

        searchResultFindByBrandAndModel = motorcycleSearchService.find("Kawasaki", "zx10r");
        searchResultFindByBrandAndModel.ifPresent(System.out::println);

        System.out.println("\n");
    }

    private static void thirdExample(MotorcycleSearchService motorcycleSearchService) {
        //3rd example...
        System.out.println("------- 3rd example ---------");

        Optional<Motorcycle> searchResultFindByBrandAndModel2 = motorcycleSearchService.find("Suzuki", "gsxr-1000");
        searchResultFindByBrandAndModel2.ifPresent(System.out::println);

        System.out.println("\n");

        searchResultFindByBrandAndModel2 = motorcycleSearchService.find("Suzuki", "gsxr-1000");
        searchResultFindByBrandAndModel2.ifPresent(System.out::println);

        System.out.println("\n");
    }

    private static void fourthExample(MotorcycleSearchService motorcycleSearchService) {
        //4th example... (cache evict example)
        System.out.println("------- 4th example ---------");

        Optional<List<Motorcycle>> honda = motorcycleSearchService.find("Honda");
        honda.ifPresent(System.out::println);

        System.out.println("\n");


        honda = motorcycleSearchService.find("Honda");
        honda.ifPresent(System.out::println);

        motorcycleSearchService.clearMotorcyclesCache();

        System.out.println("\n");


        honda = motorcycleSearchService.find("Honda");
        honda.ifPresent(System.out::println);


        System.out.println("\n");
    }

    private static void fifthExample(MotorcycleSearchService motorcycleSearchService) {
        //5th example...
        System.out.println("------- 5th example ---------");

        Optional<List<Motorcycle>> kawasaki = motorcycleSearchService.find("Kawasaki");
        kawasaki.ifPresent(System.out::println);

        System.out.println("\n");

        kawasaki = motorcycleSearchService.find("Kawasaki");
        kawasaki.ifPresent(System.out::println);

        System.out.println("\n");

        motorcycleSearchService.delete("Kawasaki");

        System.out.println("\n");

        kawasaki = motorcycleSearchService.find("Kawasaki");
        System.out.println("search result = " + kawasaki.orElse(Collections.emptyList()));

        System.out.println("\n");

        motorcycleSearchService.add(Arrays.asList(
                new Motorcycle("Kawasaki", "ZX10R"),
                new Motorcycle("Kawasaki", "ZX6R"),
                new Motorcycle("Kawasaki", "Z1000")));

        System.out.println("\n");

        kawasaki = motorcycleSearchService.find("Kawasaki");
        System.out.println("search result = " + kawasaki.orElse(Collections.emptyList()));

        System.out.println("\n");

        kawasaki = motorcycleSearchService.find("Kawasaki");
        System.out.println("search result = " + kawasaki.orElse(Collections.emptyList()));

        System.out.println("\n");
    }

    private static void sixthExample(FooSearchService fooSearchService) {
        //6th example...
        System.out.println("------- 6th example ---------");

        Foo foo = fooSearchService.get("1");
        System.out.println("result = " + foo);

        System.out.println("\n");

        foo = fooSearchService.get("niko");
        System.out.println("result = " + foo);

        System.out.println("\n");

        foo = fooSearchService.get("niko");
        System.out.println("result = " + foo);

        System.out.println("\n");
    }
}
