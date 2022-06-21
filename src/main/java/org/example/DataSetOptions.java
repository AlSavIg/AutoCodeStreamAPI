package org.example;

import java.util.List;
import java.util.Set;

public interface DataSetOptions {
    Double getMiddleClientsAge();
    List<String> getTitlesOfTheMostPopularCourses(long limit);
    // Get !three! of course titles which contains more people than other
    Set<String> getAllClientsInfo();
    // Format: [client's] "name,age,courseTitle,courseDuration\n" [courseDuration] = month
    List<String> getAllClientsSortedByAge();
    // The same format (decreasing)
}
