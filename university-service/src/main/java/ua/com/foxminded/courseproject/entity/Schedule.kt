package ua.com.foxminded.courseproject.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToMany
    @JoinTable(
            name = "schedule_weeks",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "week_id"))
    private List<WeekSchedule> weeks;
    @Column(name = "start_time")
    private Timestamp startDate;
    @Column(name = "end_time")
    private Timestamp endDate;

    public Schedule() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<WeekSchedule> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<WeekSchedule> weeks) {
        this.weeks = weeks;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule schedule)) return false;

        if (getId() != null ? !getId().equals(schedule.getId()) : schedule.getId() != null) return false;
        if (getWeeks() != null ? !getWeeks().equals(schedule.getWeeks()) : schedule.getWeeks() != null) return false;
        if (getStartDate() != null ? !getStartDate().equals(schedule.getStartDate()) : schedule.getStartDate() != null)
            return false;
        return getEndDate() != null ? getEndDate().equals(schedule.getEndDate()) : schedule.getEndDate() == null;
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
        return "Schedule{" +
                "id=" + id +
                ", weeks=" + weeks +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
