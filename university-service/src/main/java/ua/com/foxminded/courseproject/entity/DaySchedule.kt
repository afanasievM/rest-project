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
@Table(name = "day_schedule")
public class DaySchedule {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToMany
    @JoinTable(
            name = "days_lessons",
            joinColumns = @JoinColumn(name = "day_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id"))
    private List<Lesson> lessons;
    @Column(name = "day_number")
    private Integer dayNumber;

    public DaySchedule() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
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
        if (!(o instanceof DaySchedule that)) return false;

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
