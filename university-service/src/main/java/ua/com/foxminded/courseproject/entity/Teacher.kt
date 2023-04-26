package ua.com.foxminded.courseproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "teachers")
public class Teacher extends Person {
    @Column(name = "degree")
    private String degree;
    @Column(name = "salary")
    private Integer salary;
    @Column(name = "first_date")
    private LocalDate firstDay;
    @Column(name = "rank")
    private String rank;
    @Column(name = "title")
    private String title;

    public Teacher() {
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public LocalDate getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(LocalDate firstDay) {
        this.firstDay = firstDay;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher teacher)) return false;
        if (!super.equals(o)) return false;

        if (getDegree() != null ? !getDegree().equals(teacher.getDegree()) : teacher.getDegree() != null) return false;
        if (getSalary() != null ? !getSalary().equals(teacher.getSalary()) : teacher.getSalary() != null) return false;
        if (getFirstDay() != null ? !getFirstDay().equals(teacher.getFirstDay()) : teacher.getFirstDay() != null)
            return false;
        if (getRank() != null ? !getRank().equals(teacher.getRank()) : teacher.getRank() != null) return false;
        return getTitle() != null ? getTitle().equals(teacher.getTitle()) : teacher.getTitle() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getDegree() != null ? getDegree().hashCode() : 0);
        result = 31 * result + (getSalary() != null ? getSalary().hashCode() : 0);
        result = 31 * result + (getFirstDay() != null ? getFirstDay().hashCode() : 0);
        result = 31 * result + (getRank() != null ? getRank().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "degree='" + degree + '\'' +
                ", salary=" + salary +
                ", firstDay=" + firstDay +
                ", rank='" + rank + '\'' +
                ", title='" + title + '\'' +
                ", id=" + super.getId() +
                ", firstName='" + super.getFirstName() + '\'' +
                ", lastName='" + super.getLastName() + '\'' +
                ", birthDay=" + super.getBirthDay() +
                '}';
    }
}
