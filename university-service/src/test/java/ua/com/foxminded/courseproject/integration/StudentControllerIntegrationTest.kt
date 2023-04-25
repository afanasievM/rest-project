package ua.com.foxminded.courseproject.integration;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.com.foxminded.courseproject.dto.StudentDto;
import ua.com.foxminded.courseproject.service.StudentServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = "classpath:initial_data.sql")
@Transactional
class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentServiceImpl studentService;

    private Pageable pageableDefault;

    private Integer pageDefault;

    private Integer sizeDefault;


    @BeforeEach
    void setUp() {
        this.pageDefault = 0;
        this.sizeDefault = 1;
        this.pageableDefault = PageRequest.of(pageDefault, sizeDefault);
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getStudents_shouldReturnAllStudentsAndOk_WhenRequestWithoutParameters() throws Exception {
        Integer expectedSize = 2;

        mockMvc.perform(get("/students"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(expectedSize));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getStudents_shouldReturnStudentsPageAndOk_WhenRequestWithCorrectParameters() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String expectedId = "f92afb9e-462a-11ed-b878-0242ac120002";
        params.put("size", Collections.singletonList(String.valueOf(sizeDefault)));
        params.put("page", Collections.singletonList(String.valueOf(pageDefault)));

        mockMvc.perform(get("/students").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber").value(pageDefault))
                .andExpect(jsonPath("$.pageable.pageSize").value(sizeDefault))
                .andExpect(jsonPath("$.content[0].id").value(expectedId));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getStudents_shouldReturnPageWithAllStudentsAndOk_WhenPageSizeMoreStudentsNumber() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String pageNumber = "0";
        String size = "10";
        String expectedElements = "2";

        params.put("size", Collections.singletonList(size));
        params.put("page", Collections.singletonList(pageNumber));

        mockMvc.perform(get("/students").params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber").value(pageNumber))
                .andExpect(jsonPath("$.pageable.pageSize").value(size))
                .andExpect(jsonPath("$.totalElements").value(expectedElements));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void createStudent_shouldReturnCreatedAndCreateStudent_WhenStudentNotExists() throws Exception {
        String firstName = "firstName";
        String lastName = "LastName";
        LocalDate birthDay = LocalDate.now().minusYears(30);
        String courseNumber = "1";
        Boolean captain = false;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("firstName", firstName);
        params.add("lastName", lastName);
        params.add("birthDay", String.valueOf(birthDay));
        params.add("course", courseNumber);
        params.add("captain", String.valueOf(captain));
        Integer pageNumber = 0;
        Integer size = 10;
        Pageable pageable = PageRequest.of(pageNumber,size);
        Integer expectedSize = 3;

        mockMvc.perform(post("/students").queryParams(params))
                .andExpect(status().isCreated());
        assertEquals(expectedSize, studentService.findAll(pageable).getContent().size());
    }


    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void updateStudent_shouldReturnResetContentAndUpdateStudent_WhenStudentExists() throws Exception {
        StudentDto existentStudent = studentService.findAll(pageableDefault).getContent().get(0);
        String id = existentStudent.getId().toString();
        String changedFirstname = "CHANGED";
        String existedLastName = existentStudent.getLastName();
        String courseNumber = "1";
        LocalDate existedBirthDay = existentStudent.getBirthDay().minusYears(18);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("firstName", changedFirstname);
        params.add("lastName", existedLastName);
        params.add("birthDay", existedBirthDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        params.add("course", courseNumber);

        mockMvc.perform(put("/students/{id}", id).params(params))
                .andExpect(status().isResetContent());

        assertEquals(changedFirstname, studentService.findById(UUID.fromString(id)).getFirstName());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getStudent_shouldReturnStudentAndOk_WhenStudentExists() throws Exception {
        StudentDto existentStudent = studentService.findAll(pageableDefault).getContent().get(0);
        String id = existentStudent.getId().toString();

        mockMvc.perform(get("/students/{id}", id))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void deleteStudent_shouldReturnNoContentAndDelete_WhenStudentExists() throws Exception {
        StudentDto existentStudent = studentService.findAll(pageableDefault).getContent().get(0);
        String id = existentStudent.getId().toString();
        Integer pageNumber = 0;
        Integer size = 10;
        Pageable pageable = PageRequest.of(pageNumber,size);
        Integer expectedSize = 1;

        mockMvc.perform(delete("/students/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertEquals(expectedSize, studentService.findAll(pageable).getContent().size());

    }

}