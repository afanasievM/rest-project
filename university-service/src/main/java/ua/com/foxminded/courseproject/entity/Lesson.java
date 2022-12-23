package ua.com.foxminded.courseproject.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject")
    private Subject subject;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom")
    private ClassRoom classRoom;
    @Column(name = "number")
    private Integer number;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher")
    private Teacher teacher;
    @ManyToMany
    @JoinTable(
            name = "lessons_groups",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> groups;

    public Lesson() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson lesson)) return false;

        if (getId() != null ? !getId().equals(lesson.getId()) : lesson.getId() != null) return false;
        if (getSubject() != null ? !getSubject().equals(lesson.getSubject()) : lesson.getSubject() != null)
            return false;
        if (getClassRoom() != null ? !getClassRoom().equals(lesson.getClassRoom()) : lesson.getClassRoom() != null)
            return false;
        if (getNumber() != null ? !getNumber().equals(lesson.getNumber()) : lesson.getNumber() != null) return false;
        if (getStartTime() != null ? !getStartTime().equals(lesson.getStartTime()) : lesson.getStartTime() != null)
            return false;
        if (getEndTime() != null ? !getEndTime().equals(lesson.getEndTime()) : lesson.getEndTime() != null)
            return false;
        if (getTeacher() != null ? !getTeacher().equals(lesson.getTeacher()) : lesson.getTeacher() != null)
            return false;
        return getGroups() != null ? getGroups().equals(lesson.getGroups()) : lesson.getGroups() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getSubject() != null ? getSubject().hashCode() : 0);
        result = 31 * result + (getClassRoom() != null ? getClassRoom().hashCode() : 0);
        result = 31 * result + (getNumber() != null ? getNumber().hashCode() : 0);
        result = 31 * result + (getStartTime() != null ? getStartTime().hashCode() : 0);
        result = 31 * result + (getEndTime() != null ? getEndTime().hashCode() : 0);
        result = 31 * result + (getTeacher() != null ? getTeacher().hashCode() : 0);
        result = 31 * result + (getGroups() != null ? getGroups().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", subject=" + subject +
                ", classRoom=" + classRoom +
                ", number=" + number +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", teacher=" + teacher +
                ", groups=" + groups +
                '}';
    }
}
