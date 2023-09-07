package ua.com.foxminded.courseproject.validation

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ua.com.foxminded.courseproject.dto.ClassRoomDto
import ua.com.foxminded.courseproject.dto.LessonDto
import ua.com.foxminded.courseproject.dto.SubjectDto
import validation.ValidationTestFixture.Companion.validator

class LessonDtoTest {
    private lateinit var lessonDto: LessonDto

    @BeforeEach
    fun setUp() {
        lessonDto = LessonDto()
        lessonDto.subject = SubjectDto()
        lessonDto.classRoom = ClassRoomDto()
        lessonDto.number = 3
    }

    @Test
    fun subjectValidation_shouldThrowException_whenNameIsNull() {
        lessonDto.subject = null
        val expectedMessage = "Lesson subject should be not null."
        val expectedSize = 1

        val constraintViolations = validator.validate(lessonDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun validation_shouldNotThrowException_whenSubjectClassroomNumberAreNotNullAndValid() {
        val expectedSize = 0
        val constraintViolations = validator.validate(lessonDto)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun classRoomValidation_shouldThrowException_whenNameIsNull() {
        lessonDto.classRoom = null
        val expectedMessage = "Lesson classroom should be not null."
        val expectedSize = 1

        val constraintViolations = validator.validate(lessonDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun numberValidation_shouldThrowException_whenNumberIsNull() {
        lessonDto.number = null
        val expectedMessage = "must not be null"
        val expectedSize = 1

        val constraintViolations = validator.validate(lessonDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun numberValidation_shouldThrowException_whenNumberLessZero() {
        lessonDto.number = -1
        val expectedMessage = "Lesson number should be greater than 0 and less than 5."
        val expectedSize = 1

        val constraintViolations = validator.validate(lessonDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun numberValidation_shouldNotThrowException_whenNumberGreaterFive() {
        lessonDto.number = 6
        val expectedMessage = "Lesson number should be greater than 0 and less than 5."
        val expectedSize = 1

        val constraintViolations = validator.validate(lessonDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun numberValidation_shouldNotThrowException_whenNumberEqualsZero() {
        lessonDto.number = 0
        val expectedSize = 0

        val constraintViolations = validator.validate(lessonDto)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun numberValidation_shouldNotThrowException_whenNumberEqualsFive() {
        lessonDto.number = 5
        val expectedSize = 0

        val constraintViolations = validator.validate(lessonDto)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun numberValidation_shouldNotThrowException_whenNumberBetweenZeroAndFive() {
        lessonDto.number = 3
        val expectedSize = 0

        val constraintViolations = validator.validate(lessonDto)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }
}
