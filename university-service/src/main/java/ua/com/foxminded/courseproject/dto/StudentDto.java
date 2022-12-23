package ua.com.foxminded.courseproject.dto;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class StudentDto extends PersonDto {
    private GroupDto group;

    @NotNull
    @Min(value = 1, message = "Student course number be greater than or equals 1 and less or equals than 6.")
    @Max(value = 6, message = "Student course number be greater than or equals 1 and less or equals than 6.")
    private Integer course;
    private Boolean isCaptain;

    public StudentDto() {
    }

    public GroupDto getGroup() {
        return group;
    }

    public void setGroup(GroupDto group) {
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
        if (!(o instanceof StudentDto that)) return false;
        if (!super.equals(o)) return false;

        if (getGroup() != null ? !getGroup().equals(that.getGroup()) : that.getGroup() != null) return false;
        if (getCourse() != null ? !getCourse().equals(that.getCourse()) : that.getCourse() != null) return false;
        return Objects.equals(isCaptain, that.isCaptain);
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
        return "StudentDto{" +
                "group=" + group +
                ", course=" + course +
                ", isCaptain=" + isCaptain +
                ", id=" + super.getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                '}';
    }
}
