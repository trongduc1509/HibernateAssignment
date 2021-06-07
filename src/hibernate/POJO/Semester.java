package hibernate.POJO;

import java.sql.Date;
import java.util.Objects;

public class Semester {
    private int id;
    private String name;
    private Integer year;
    private Date start;
    private Date end;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Semester semester = (Semester) o;
        return id == semester.id && Objects.equals(name, semester.name) && Objects.equals(year, semester.year) && Objects.equals(start, semester.start) && Objects.equals(end, semester.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, start, end);
    }

    @Override
    public String toString() {
        return getName() + " - " + getYear();
    }
}
