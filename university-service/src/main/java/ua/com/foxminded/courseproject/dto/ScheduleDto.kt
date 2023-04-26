package ua.com.foxminded.courseproject.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ScheduleDto {
    private UUID id;
    private List<WeekScheduleDto> weeks;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public ScheduleDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<WeekScheduleDto> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<WeekScheduleDto> weeks) {
        this.weeks = weeks;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScheduleDto that)) return false;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getWeeks() != null ? !getWeeks().equals(that.getWeeks()) : that.getWeeks() != null) return false;
        if (getStartDate() != null ? !getStartDate().equals(that.getStartDate()) : that.getStartDate() != null)
            return false;
        return getEndDate() != null ? getEndDate().equals(that.getEndDate()) : that.getEndDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getWeeks() != null ? getWeeks().hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getEndDate() != null ? getEndDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ScheduleDto{" +
                "id=" + id +
                ", weeks=" + weeks +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
