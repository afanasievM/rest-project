package ua.com.foxminded.courseproject.dto;

import org.springframework.format.annotation.DateTimeFormat;
import ua.com.foxminded.courseproject.validation.Age;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

public class PersonDto {

    private UUID id;

    @NotNull
    @Size(min = 1, max = 36, message = "Firstname should be between 1 and 36.")
    protected String firstName;

    @NotNull
    @Size(min = 1, max = 36, message = "Lastname should be between 1 and 36.")
    protected String lastName;

    @NotNull
    @Age(min = 16)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected LocalDate birthDay;

    public PersonDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonDto personDto)) return false;

        if (getId() != null ? !getId().equals(personDto.getId()) : personDto.getId() != null) return false;
        if (getFirstName() != null ? !getFirstName().equals(personDto.getFirstName()) : personDto.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(personDto.getLastName()) : personDto.getLastName() != null)
            return false;
        return getBirthDay() != null ? getBirthDay().equals(personDto.getBirthDay()) : personDto.getBirthDay() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getBirthDay() != null ? getBirthDay().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PersonDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDay=" + birthDay +
                '}';
    }
}
