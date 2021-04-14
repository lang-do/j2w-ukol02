package cz.czechitas.java2webapps.ukol2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class GeneratorController {

    private final List<String> seznamCitaty;

    private final Random nahodneCislo;

    private static final int pocetObrazku = 8;

    public GeneratorController() throws IOException {
        nahodneCislo = new Random();
        seznamCitaty = readAllLines("citaty.txt");
    }

    private static List<String> readAllLines(String resource)throws IOException {
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
        try(InputStream inputStream=classLoader.getResourceAsStream(resource);
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
            return reader
                    .lines()
                    .collect(Collectors.toList()); //příklad volání: readAllLines("citaty.txt")
        }
    }

    @GetMapping("/")
    public ModelAndView generatorCitatObrazek(){

        ModelAndView result = new ModelAndView("generatorCitatObrazek");

        int indexCitatu = nahodneCislo.nextInt(seznamCitaty.size());
        result.addObject("citat", seznamCitaty.get(indexCitatu));

        int indexObrazku = nahodneCislo.nextInt(pocetObrazku);
        result.addObject("obrazek", String.format("/images/obr-%d.jpg", indexObrazku));
        return result;
    }

}
