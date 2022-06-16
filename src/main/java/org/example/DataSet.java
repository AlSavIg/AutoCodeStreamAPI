package org.example;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class DataSet implements DataSetOptions{
    private final Map<Person, Course> clientsData;

    public DataSet(Map<Person, Course> clientsData) {
        this.clientsData = clientsData;
    }

    @Override
    public Double getMiddleClientsAge() {
        return clientsData.entrySet()
                .stream()
                .collect(Collectors.averagingInt(clientData -> clientData.getKey().getAge()));
    }

    @Override
    public List<String> getTitlesOfTheMostPopularCourses(long limit) {

        Map<String, Long> coursesPopularity = clientsData
                .entrySet()
                .stream()
                .collect(groupingBy(x -> x.getValue().getTitle(), counting()));

        List<Map.Entry<String, Long>> tmpList = new ArrayList<>(coursesPopularity.entrySet());
        tmpList.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));

        return tmpList
                .stream()
                .limit(limit)
                .map(Map.Entry::getKey)
                .toList();
    }

    private static String getString(Map.Entry<Person, Course> client) {
        Person person = client.getKey();
        Course course = client.getValue();
        return String.format("%s,%d,%s,%d\n",
                person.getName(),
                person.getAge(),
                course.getTitle(),
                course.getDuration());
    }

    @Override
    public Set<String> getAllClientsInfo() {
        return clientsData
                .entrySet()
                .stream()
                .map(DataSet::getString)
                .collect(toSet());
    }

    @Override
    public List<String> getAllClientsSortedByAge() {
        List<Map.Entry<Person, Course>> tmpList = new ArrayList<>(clientsData.entrySet());
        tmpList.sort(Comparator.comparingInt(o -> o.getKey().getAge()));

        return tmpList
                .stream()
                .map(DataSet::getString)
                .toList();
    }

    public static void main(String[] args) {
//        DataSet dataSet = new DataSet(clientsData);
//        System.out.println(dataSet.getTitlesOfTheMostPopularCourses(3));
    }
}
