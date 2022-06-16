package org.example;

public class Course {
    private final String courseTitle;
    private final int courseDuration;

    public Course(String courseTitle, int courseDuration) {
        this.courseTitle = courseTitle;
        this.courseDuration = courseDuration;
    }

    public String getTitle() {
        return courseTitle;
    }

    public int getDuration() {
        return courseDuration;
    }
}
