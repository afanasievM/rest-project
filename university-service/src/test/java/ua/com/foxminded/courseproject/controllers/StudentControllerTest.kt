package ua.com.foxminded.courseproject.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.com.foxminded.courseproject.dto.GroupDto;
import ua.com.foxminded.courseproject.dto.StudentDto;
import ua.com.foxminded.courseproject.service.StudentServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentServiceImpl studentService;

    private Pageable pageableDefault;

    private Integer pageDefault;

    private Integer sizeDefault;


    @BeforeEach
    void setUp() {
        this.pageDefault = 0;
        this.sizeDefault = 5;
        this.pageableDefault = PageRequest.of(pageDefault, sizeDefault);
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getStudents_shouldReturnAllStudentsAndOk_WhenRequestWithoutParameters() throws Exception {
        Page<StudentDto> expected = setStudents(pageableDefault);

        when(studentService.findAll(pageableDefault)).thenReturn(expected);

        mockMvc.perform(get("/students"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(expected.getTotalElements()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getStudents_shouldReturnStudentsPageAndOk_WhenRequestWithCorrectParameters() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Page<StudentDto> expected = setStudents(pageableDefault);
        params.put("size", Collections.singletonList(String.valueOf(sizeDefault)));
        params.put("page", Collections.singletonList(String.valueOf(pageDefault)));

        when(studentService.findAll(pageableDefault)).thenReturn(expected);

        mockMvc.perform(get("/students").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber").value(pageDefault))
                .andExpect(jsonPath("$.pageable.pageSize").value(sizeDefault))
                .andExpect(jsonPath("$.content[0].id").value(expected.getContent().get(0).getId().toString()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getStudents_shouldReturnPageWithAllStudentsAndOk_WhenPageSizeMoreStudentsNumber() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String pageNumber = "0";
        String size = "10";
        Pageable pageable = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(size));
        Page<StudentDto> expected = setStudents(pageable);
        params.put("size", Collections.singletonList(size));
        params.put("page", Collections.singletonList(pageNumber));

        when(studentService.findAll(pageable)).thenReturn(expected);

        mockMvc.perform(get("/students").params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber").value(pageNumber))
                .andExpect(jsonPath("$.pageable.pageSize").value(size))
                .andExpect(jsonPath("$.totalElements").value(expected.getTotalElements()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void createStudent_shouldReturnCreated_WhenStudentNotExists() throws Exception {
        String firstName = "test1";
        String lastName = setStudents(pageableDefault).getContent().get(0).getLastName();
        LocalDate birthDay = setStudents(pageableDefault).getContent().get(0).getBirthDay().minusYears(18);
        String courseNumber = "1";
        boolean captain = false;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("firstName", firstName);
        params.add("lastName", lastName);
        params.add("birthDay", birthDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        params.add("captain", String.valueOf(captain));
        params.add("course", courseNumber);
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName(firstName);
        studentDto.setLastName(lastName);
        studentDto.setBirthDay(birthDay);
        studentDto.setCourse(Integer.valueOf(courseNumber));
        studentDto.setCaptain(captain);

        when(studentService.personExists(studentDto)).thenReturn(false);

        mockMvc.perform(post("/students").queryParams(params))
                .andExpect(status().isCreated());
    }


    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void updateStudent_shouldReturnResetContent_WhenStudentExists() throws Exception {
        StudentDto existentStudent = setStudents(pageableDefault).getContent().get(0);
        String id = existentStudent.getId().toString();
        String existedFirstName = existentStudent.getFirstName();
        String existedLastName = existentStudent.getLastName();
        String courseNumber = "1";
        LocalDate existedBirthDay = existentStudent.getBirthDay().minusYears(18);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("firstName", existedFirstName);
        params.add("lastName", existedLastName);
        params.add("birthDay", existedBirthDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        params.add("course", courseNumber);

        when(studentService.findById(UUID.fromString(id))).thenReturn(existentStudent);

        mockMvc.perform(put("/students/{id}", id).queryParams(params))
                .andExpect(status().isResetContent());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getStudent_shouldReturnStudentAndOk_WhenStudentExists() throws Exception {
        StudentDto existentStudent = setStudents(pageableDefault).getContent().get(0);
        String id = existentStudent.getId().toString();

        when(studentService.findById(UUID.fromString(id))).thenReturn(existentStudent);

        mockMvc.perform(get("/students/{id}", id))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void deleteStudent_shouldReturnNoContent() throws Exception {
        String id = UUID.randomUUID().toString();

        mockMvc.perform(delete("/students/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private Page<StudentDto> setStudents(Pageable pageable) {
        List<StudentDto> studentDtos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            StudentDto student = new StudentDto();
            student.setId(UUID.randomUUID());
            student.setFirstName("firstName" + i);
            student.setLastName("lastName" + i);
            student.setCourse(i);
            student.setBirthDay(LocalDate.now());
            student.setCaptain(false);
            GroupDto group = new GroupDto();
            group.setId(UUID.randomUUID());
            group.setName("test");
            student.setGroup(group);
            studentDtos.add(student);
        }
        return new PageImpl<>(studentDtos, pageable, studentDtos.size());
    }
}