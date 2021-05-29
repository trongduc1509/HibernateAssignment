package hibernate.POJO;

import java.util.Objects;

public class ClazzInfo {
    private int classInfoId;
    private String classId;
    private String studentId;

    public int getClassInfoId() {
        return classInfoId;
    }

    public void setClassInfoId(int classInfoId) {
        this.classInfoId = classInfoId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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
