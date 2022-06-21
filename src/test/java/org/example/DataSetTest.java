package org.example;

import au.com.bytecode.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.nio.file.Files.readAllLines;
import static org.junit.jupiter.api.Assertions.*;

class DataSetTest{
    DataSetTest() throws Exception {}

    Map<Person, Course> clientsData = fillClientsDataFromCSV();

    private static Map<Person, Course> fillClientsDataFromCSV() throws Exception
    {
        Map<Person, Course> clientsData = new HashMap<>();
        CSVReader reader = new CSVReader(new FileReader(
                Objects.requireNonNull(DataSetTest.class.getResource("clientsData.csv")).getPath()),
                ',', '"', 0);
        List<String[]> allRows = reader.readAll();
        for(String[] row : allRows){
            clientsData.put(new Person(row[0], Integer.parseInt(row[1])),
                            new Course(row[3], Integer.parseInt(row[2])));
        }
        return clientsData;
    }

    @Test
    void getMiddleClientAge() {
        DataSet dataSet = new DataSet(clientsData);

        assertTrue(Math.abs(dataSet.getMiddleClientsAge() - 39.4545) < 0.0001);
    }

    @Test
    void getTitlesOfTheMostPopularCourses() {
        DataSet dataSet = new DataSet(clientsData);
        assertEquals(
                List.of(
                        "Инженер по тестированию",
                        "Режиссёр монтажа",
                        "Бизнес-аналитик"),
                dataSet.getTitlesOfTheMostPopularCourses(3)
        );
    }

    @Test
    void getAllClients() {
        DataSet dataSet = new DataSet(clientsData);
        assertEquals(
                Set.of("Thomas Wood,18,12,Создание электронной музыки\n",
                        "Michael Young,19,16,Дизайнер интерьеров\n",
                        "Josie Reed,20,10,SMM-специалист 2022\n",
                        "Megan Cooper,22,24,Инженер по тестированию\n",
                        "Ethan Morris,23,24,Разработчик игр на Unity с нуля до Middle\n",
                        "Harry Morgan,27,12,Менеджер проектов\n",
                        "Barbara Nelson,28,36,Копирайтер с нуля до PRO\n",
                        "Dan Black,31,12,Бизнес-аналитик\n",
                        "Cindy Clark,33,16,Режиссёр монтажа\n",
                        "Charles Barnett,34,24,Разработчик на Unreal Engine 4 с нуля до Middle\n",
                        "Alexander Wright,35,12,Таргетолог с нуля до PRO\n",
                        "Emily Perry,39,24,Инженер по тестированию\n",
                        "George Jenkins,44,16,Инженер по тестированию\n",
                        "Lucy Brooks,45,24,Коммерческий иллюстратор\n",
                        "Sara Murphy,48,10,Инженер по тестированию\n",
                        "William Johnson,49,16,Python-разработчик\n",
                        "David Scott,53,10,Продакт-менеджер\n",
                        "John Smith,54,24,Python-разработчик\n",
                        "Helen Larsson,58,12,Режиссёр монтажа\n",
                        "Matthew Wilson,60,24,Бизнес-аналитик\n",
                        "Lily Brown,63,10,Графический дизайнер\n",
                        "Jack Lewis,65,10,Режиссёр монтажа\n"),
                dataSet.getAllClientsInfo()
                );
    }

    @Test
    void getAllClientsSortedByAge() {
        DataSet dataSet = new DataSet(clientsData);
        assertEquals(
                List.of("Thomas Wood,18,12,Создание электронной музыки\n",
                        "Michael Young,19,16,Дизайнер интерьеров\n",
                        "Josie Reed,20,10,SMM-специалист 2022\n",
                        "Megan Cooper,22,24,Инженер по тестированию\n",
                        "Ethan Morris,23,24,Разработчик игр на Unity с нуля до Middle\n",
                        "Harry Morgan,27,12,Менеджер проектов\n",
                        "Barbara Nelson,28,36,Копирайтер с нуля до PRO\n",
                        "Dan Black,31,12,Бизнес-аналитик\n",
                        "Cindy Clark,33,16,Режиссёр монтажа\n",
                        "Charles Barnett,34,24,Разработчик на Unreal Engine 4 с нуля до Middle\n",
                        "Alexander Wright,35,12,Таргетолог с нуля до PRO\n",
                        "Emily Perry,39,24,Инженер по тестированию\n",
                        "George Jenkins,44,16,Инженер по тестированию\n",
                        "Lucy Brooks,45,24,Коммерческий иллюстратор\n",
                        "Sara Murphy,48,10,Инженер по тестированию\n",
                        "William Johnson,49,16,Python-разработчик\n",
                        "David Scott,53,10,Продакт-менеджер\n",
                        "John Smith,54,24,Python-разработчик\n",
                        "Helen Larsson,58,12,Режиссёр монтажа\n",
                        "Matthew Wilson,60,24,Бизнес-аналитик\n",
                        "Lily Brown,63,10,Графический дизайнер\n",
                        "Jack Lewis,65,10,Режиссёр монтажа\n"),
                dataSet.getAllClientsSortedByAge()
        );
    }

    @Test
    public void testNoCyclesAndIfStatements() throws IOException {

        final Path sources = Paths.get("src/main/java");
        Files.walk(sources)
                .filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".java"))
                .forEach(this::assertSourceHasNoSCyclesAndIfStatements);
    }

    private void assertSourceHasNoSCyclesAndIfStatements(final Path sourcePath) {
        final String source = readPathToString(sourcePath);

        assertFalse(matchesRegex(source, "\\Wif\\W"));
        assertFalse(matchesRegex(source, "\\Wfor\\W"));
        assertFalse(matchesRegex(source, "\\Wwhile\\W"));
    }

    private boolean matchesRegex(String source, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        return (matcher.find());
    }


    private String readPathToString(final Path path) {
        try {
            return String.join("\n", readAllLines(path, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}