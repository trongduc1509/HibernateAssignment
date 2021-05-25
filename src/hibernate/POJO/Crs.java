package hibernate.POJO;

import java.sql.Date;
import java.util.Objects;

public class Crs {
    private int id;
    private Date start;
    private Date end;
    private int semester_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSemester_id() {
        return semester_id;
    }

    public void setSemester_id(int semester_id) {
        this.semester_id = semester_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crs crs = (Crs) o;
        return id == crs.id && Objects.equals(start, crs.start) && Objects.equals(end, crs.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end);
    }
}
