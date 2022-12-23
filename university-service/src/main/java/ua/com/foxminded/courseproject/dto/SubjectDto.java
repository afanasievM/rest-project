package ua.com.foxminded.courseproject.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class SubjectDto {
    private UUID id;

    @NotNull
    @Size(min = 1,max = 36, message = "Name of subject length should be between 1 and 36.")
    private String name;

    public SubjectDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubjectDto subjectDto)) return false;

        if (getId() != null ? !getId().equals(subjectDto.getId()) : subjectDto.getId() != null) return false;
        return getName() != null ? getName().equals(subjectDto.getName()) : subjectDto.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
