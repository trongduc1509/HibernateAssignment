package hibernate.POJO;

import javafx.scene.control.CheckBox;

public class CourseView {
    private int courseId;
    private String subjectId;
    private String subjectName;
    private Integer credits;
    private String teacher;
    private String room;
    private String day;
    private String session;
    private Integer semesterId;
    private Integer maxSlot;
    private Integer registedSlot;
    private CheckBox select;

    public CourseView() {
    }

    public CourseView(int courseId, String subjectId, String subjectName, Integer credits, String teacher, String room, String day, String session, Integer semesterId, Integer maxSlot, Integer registedSlot) {
        this.courseId = courseId;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credits = credits;
        this.teacher = teacher;
        this.room = room;
        this.day = day;
        this.session = session;
        this.semesterId = semesterId;
        this.maxSlot = maxSlot;
        this.registedSlot = registedSlot;
        this.select = new CheckBox();
    }

    public int getCourseId() {
        return courseId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Integer getCredits() {
        return credits;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getRoom() {
        return room;
    }

    public String getDay() {
        return day;
    }

    public String getSession() {
        return session;
    }

    public Integer getSemesterId() {
        return semesterId;
    }

    public Integer getMaxSlot() {
        return maxSlot;
    }

    public CheckBox getSelect() {
        return select;
    }

    public Integer getRegistedSlot() {
        return registedSlot;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setSemesterId(Integer semesterId) {
        this.semesterId = semesterId;
    }

    public void setMaxSlot(Integer maxSlot) {
        this.maxSlot = maxSlot;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    public void setRegistedSlot(Integer registedSlot) {
        this.registedSlot = registedSlot;
    }
}
