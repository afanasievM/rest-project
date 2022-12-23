package ua.com.foxminded.courseproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "students")
public class Student extends Person {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
    @Column(name = "course")
    private Integer course;
    @Column(name = "is_captain")
    private Boolean isCaptain;

    public Student() {
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Boolean getCaptain() {
        return isCaptain;
    }

    public void setCaptain(Boolean captain) {
        isCaptain = captain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        if (!super.equals(o)) return false;

        if (getGroup() != null ? !getGroup().equals(student.getGroup()) : student.getGroup() != null) return false;
        if (getCourse() != null ? !getCourse().equals(student.getCourse()) : student.getCourse() != null) return false;
        return Objects.equals(isCaptain, student.isCaptain);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getGroup() != null ? getGroup().hashCode() : 0);
        result = 31 * result + (getCourse() != null ? getCourse().hashCode() : 0);
        result = 31 * result + (isCaptain != null ? isCaptain.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "group=" + group +
                ", course=" + course +
                ", isCaptain=" + isCaptain +
                ", id=" + super.getId() +
                ", firstName='" + super.getFirstName() + '\'' +
                ", lastName='" + super.getLastName() + '\'' +
                ", birthDay=" + super.getBirthDay() +
                '}';
    }
}
