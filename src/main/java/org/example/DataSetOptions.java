package org.example;

import java.util.List;
import java.util.Set;

public interface DataSetOptions {
    Double getMiddleClientsAge();
    List<String> getTitlesOfTheMostPopularCourses(long limit);
    Set<String> getAllClientsInfo();
    List<String> getAllClientsSortedByAge();
}
