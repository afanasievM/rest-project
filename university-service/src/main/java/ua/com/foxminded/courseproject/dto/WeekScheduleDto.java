package ua.com.foxminded.courseproject.dto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class WeekScheduleDto {
    private UUID id;
    private List<DayScheduleDto> daysSchedule;

    @NotNull
    private Boolean isOdd;

    public WeekScheduleDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<DayScheduleDto> getDaysSchedule() {
        return daysSchedule;
    }

    public void setDaysSchedule(List<DayScheduleDto> daysSchedule) {
        this.daysSchedule = daysSchedule;
    }

    public Boolean getIsOdd() {
        return isOdd;
    }

    public void setIsOdd(Boolean isOdd) {
        this.isOdd = isOdd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeekScheduleDto that)) return false;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getDaysSchedule() != null ? !getDaysSchedule().equals(that.getDaysSchedule()) : that.getDaysSchedule() != null)
            return false;
        return getIsOdd() != null ? getIsOdd().equals(that.getIsOdd()) : that.getIsOdd() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getDaysSchedule() != null ? getDaysSchedule().hashCode() : 0);
        result = 31 * result + (getIsOdd() != null ? getIsOdd().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WeekSchedule{" +
                "id=" + id +
                ", daysSchedule=" + daysSchedule +
                ", odd=" + isOdd +
                '}';
    }
}
