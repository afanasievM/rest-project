package ua.com.foxminded.courseproject.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class TeacherDto extends PersonDto {
    private String degree;
    private Integer salary;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate firstDay;
    private String rank;
    private String title;

    public TeacherDto() {
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
        if (!(o instanceof TeacherDto that)) return false;
        if (!super.equals(o)) return false;

        if (getDegree() != null ? !getDegree().equals(that.getDegree()) : that.getDegree() != null) return false;
        if (getSalary() != null ? !getSalary().equals(that.getSalary()) : that.getSalary() != null) return false;
        if (getFirstDay() != null ? !getFirstDay().equals(that.getFirstDay()) : that.getFirstDay() != null)
            return false;
        if (getRank() != null ? !getRank().equals(that.getRank()) : that.getRank() != null) return false;
        return getTitle() != null ? getTitle().equals(that.getTitle()) : that.getTitle() == null;
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
        return "TeacherDto{" +
                "degree='" + degree + '\'' +
                ", salary=" + salary +
                ", firstDay=" + firstDay +
                ", rank='" + rank + '\'' +
                ", title='" + title + '\'' +
                ", id=" + super.getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                '}';
    }
}
