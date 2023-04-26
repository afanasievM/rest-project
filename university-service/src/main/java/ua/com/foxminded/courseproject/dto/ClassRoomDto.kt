package ua.com.foxminded.courseproject.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

public class ClassRoomDto {
    private UUID id;

    @NotNull
    @PositiveOrZero(message = "Classroom number must be positive or equal 0.")
    private Integer number;

    public ClassRoomDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassRoomDto classRoomDto)) return false;

        if (getId() != null ? !getId().equals(classRoomDto.getId()) : classRoomDto.getId() != null) return false;
        return getNumber() != null ? getNumber().equals(classRoomDto.getNumber()) : classRoomDto.getNumber() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getNumber() != null ? getNumber().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ClassRoom{" +
                "id=" + id +
                ", number=" + number +
                '}';
    }
}
