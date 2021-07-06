package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homePage;

    private final List<WorkPeriod> periods = new ArrayList<>();

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.homePage = new Link(name,url);
        addPeriod(startDate,endDate,title,description);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Organization that = (Organization) o;
//
//        if (!homePage.equals(that.homePage)) return false;
//        if (!startDate.equals(that.startDate)) return false;
//        if (!endDate.equals(that.endDate)) return false;
//        if (!title.equals(that.title)) return false;
//        return description != null ? description.equals(that.description) : that.description == null;
//    }

    public void addPeriod (LocalDate startDate, LocalDate endDate, String title, String description) {
        periods.add(new WorkPeriod(startDate,endDate,title,description));
    }

//    @Override
//    public int hashCode() {
//        int result = homePage.hashCode();
//        result = 31 * result + startDate.hashCode();
//        result = 31 * result + endDate.hashCode();
//        result = 31 * result + title.hashCode();
//        result = 31 * result + (description != null ? description.hashCode() : 0);
//        return result;
//    }
}
