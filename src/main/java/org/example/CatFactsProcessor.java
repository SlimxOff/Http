package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CatFactsProcessor {
    public static void main(String[] args) {
        // Создание HTTP-клиента
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // Создание GET-запроса
        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");

        try {
            // Выполнение запроса
            CloseableHttpResponse response = httpClient.execute(request);

            // Извлечение фактов из JSON-ответа
            ObjectMapper objectMapper = new ObjectMapper();
            CatFact[] catFactsArray = objectMapper.readValue(response.getEntity().getContent(), CatFact[].class);
            List<CatFact> catFacts = Arrays.asList(catFactsArray);

            // Фильтрация фактов
            List<CatFact> filteredFacts = catFacts.stream()
                    .filter(fact -> fact.getUpvotes() != null)
                    .collect(Collectors.toList());

            // Вывод отфильтрованных фактов
            filteredFacts.forEach(System.out::println);

            // Закрытие соединения
            response.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
