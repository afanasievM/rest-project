package ua.com.foxminded.courseproject.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class DayScheduleDto {
    private UUID id;
    private List<LessonDto> lessons;

    @NotNull
    @Min(value = 1, message = "Weekday number should be greater than 0 and less than 8.")
    @Max(value = 7, message = "Weekday number should be greater than 0 and less than 8.")
    private Integer dayNumber;

    public DayScheduleDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<LessonDto> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonDto> lessonDtos) {
        this.lessons = lessonDtos;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayScheduleDto that)) return false;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getLessons() != null ? !getLessons().equals(that.getLessons()) : that.getLessons() != null) return false;
        return getDayNumber() != null ? getDayNumber().equals(that.getDayNumber()) : that.getDayNumber() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getLessons() != null ? getLessons().hashCode() : 0);
        result = 31 * result + (getDayNumber() != null ? getDayNumber().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DaySchedule{" +
                "id=" + id +
                ", lessons=" + lessons +
                ", dayNumber=" + dayNumber +
                '}';
    }
}
