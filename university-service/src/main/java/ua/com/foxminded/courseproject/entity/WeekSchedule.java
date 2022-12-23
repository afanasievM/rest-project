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
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "weeks")
public class WeekSchedule {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToMany
    @JoinTable(
            name = "weeks_days",
            joinColumns = @JoinColumn(name = "week_id"),
            inverseJoinColumns = @JoinColumn(name = "day_id"))
    private List<DaySchedule> daysSchedule;
    @Column(name = "odd")
    private Boolean isOdd;

    public WeekSchedule() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<DaySchedule> getDaysSchedule() {
        return daysSchedule;
    }

    public void setDaysSchedule(List<DaySchedule> daysSchedule) {
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
        if (!(o instanceof WeekSchedule that)) return false;

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
