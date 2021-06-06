package hibernate.POJO;

import java.sql.Date;

public class CrsView {
    private int id;
    private Date start;
    private Date end;
    private String semName;
    private Integer semYear;

    public CrsView() {
    }

    public CrsView(int id, Date start, Date end, String semName, Integer semYear) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.semName = semName;
        this.semYear = semYear;
    }

    public int getId() {
        return id;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getSemName() {
        return semName;
    }

    public Integer getSemYear() {
        return semYear;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setSemName(String semName) {
        this.semName = semName;
    }

    public void setSemYear(Integer semYear) {
        this.semYear = semYear;
    }
}
