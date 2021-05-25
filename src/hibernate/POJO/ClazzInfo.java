package hibernate.POJO;

import java.util.Objects;

public class ClazzInfo {
    private int classInfoId;
    private String class_id;
    private String student_id;

    public int getClassInfoId() {
        return classInfoId;
    }

    public void setClassInfoId(int classInfoId) {
        this.classInfoId = classInfoId;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClazzInfo clazzInfo = (ClazzInfo) o;
        return classInfoId == clazzInfo.classInfoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(classInfoId);
    }
}
