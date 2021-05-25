package hibernate.POJO;

import java.util.Objects;

public class Clazz {
    private String id;
    private Integer total;
    private Integer male;
    private Integer female;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getMale() {
        return male;
    }

    public void setMale(Integer male) {
        this.male = male;
    }

    public Integer getFemale() {
        return female;
    }

    public void setFemale(Integer female) {
        this.female = female;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clazz clazz = (Clazz) o;
        return Objects.equals(id, clazz.id) && Objects.equals(total, clazz.total) && Objects.equals(male, clazz.male) && Objects.equals(female, clazz.female);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, total, male, female);
    }
}
