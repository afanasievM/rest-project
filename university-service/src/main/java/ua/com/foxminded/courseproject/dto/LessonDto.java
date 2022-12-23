package ua.com.foxminded.courseproject.dto;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class LessonDto {
    private UUID id;

    @NotNull(message = "Lesson subject should be not null.")
    private SubjectDto subject;

    @NotNull(message = "Lesson classroom should be not null.")
    private ClassRoomDto classRoom;

    @NotNull
    @Min(value = 0, message = "Lesson number should be greater than 0 and less than 5.")
    @Max(value = 5, message = "Lesson number should be greater than 0 and less than 5.")
    private Integer number;

    private LocalTime startTime;
    private LocalTime endTime;
    private TeacherDto teacher;
    private List<GroupDto> groups;

    public LessonDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }

    public ClassRoomDto getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoomDto classRoom) {
        this.classRoom = classRoom;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public TeacherDto getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDto teacher) {
        this.teacher = teacher;
    }

    public List<GroupDto> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupDto> group) {
        this.groups = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LessonDto lessonDto)) return false;

        if (getId() != null ? !getId().equals(lessonDto.getId()) : lessonDto.getId() != null) return false;
        if (getSubject() != null ? !getSubject().equals(lessonDto.getSubject()) : lessonDto.getSubject() != null)
            return false;
        if (getClassRoom() != null ? !getClassRoom().equals(lessonDto.getClassRoom()) : lessonDto.getClassRoom() != null)
            return false;
        if (getNumber() != null ? !getNumber().equals(lessonDto.getNumber()) : lessonDto.getNumber() != null)
            return false;
        if (getStartTime() != null ? !getStartTime().equals(lessonDto.getStartTime()) : lessonDto.getStartTime() != null)
            return false;
        if (getEndTime() != null ? !getEndTime().equals(lessonDto.getEndTime()) : lessonDto.getEndTime() != null)
            return false;
        if (getTeacher() != null ? !getTeacher().equals(lessonDto.getTeacher()) : lessonDto.getTeacher() != null)
            return false;
        return getGroups() != null ? getGroups().equals(lessonDto.getGroups()) : lessonDto.getGroups() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getSubject() != null ? getSubject().hashCode() : 0);
        result = 31 * result + (getClassRoom() != null ? getClassRoom().hashCode() : 0);
        result = 31 * result + (getNumber() != null ? getNumber().hashCode() : 0);
        result = 31 * result + (getStartTime() != null ? getStartTime().hashCode() : 0);
        result = 31 * result + (getEndTime() != null ? getEndTime().hashCode() : 0);
        result = 31 * result + (getTeacher() != null ? getTeacher().hashCode() : 0);
        result = 31 * result + (getGroups() != null ? getGroups().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LessonDto{" +
                "id=" + id +
                ", subjectDto=" + subject +
                ", classRoomDto=" + classRoom +
                ", number=" + number +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", teacherDto=" + teacher +
                ", groupDtos=" + groups +
                '}';
    }
}
