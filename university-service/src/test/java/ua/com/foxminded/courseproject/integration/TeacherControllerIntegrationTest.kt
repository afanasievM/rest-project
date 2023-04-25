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
import ua.com.foxminded.courseproject.dto.TeacherDto;
import ua.com.foxminded.courseproject.service.TeacherServiceImpl;

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
class TeacherControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherServiceImpl teacherService;


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
    void getTeachers_shouldReturnAllTeachersAndOk_WhenRequestWithoutParameters() throws Exception {
        Integer expectedSize = 2;

        mockMvc.perform(get("/teachers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(expectedSize));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getTeachers_shouldReturnTeachersPageAndOk_WhenRequestWithCorrectParameters() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String expectedId = "e966f608-4621-11ed-b878-0242ac120002";
        params.put("size", Collections.singletonList(String.valueOf(sizeDefault)));
        params.put("page", Collections.singletonList(String.valueOf(pageDefault)));

        mockMvc.perform(get("/teachers").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber").value(pageDefault))
                .andExpect(jsonPath("$.pageable.pageSize").value(sizeDefault))
                .andExpect(jsonPath("$.content[0].id").value(expectedId));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getTeachers_shouldReturnPageWithAllTeachersAndOk_WhenPageSizeMoreTeachersNumber() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String pageNumber = "0";
        String size = "10";
        String expectedElements = "2";

        params.put("size", Collections.singletonList(size));
        params.put("page", Collections.singletonList(pageNumber));

        mockMvc.perform(get("/teachers").params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber").value(pageNumber))
                .andExpect(jsonPath("$.pageable.pageSize").value(size))
                .andExpect(jsonPath("$.totalElements").value(expectedElements));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void createTeacher_shouldReturnCreatedAndCreateTeacher_WhenTeacherNotExists() throws Exception {
        String firstName = "test1";
        String lastName = "test2";
        LocalDate birthDay = LocalDate.now().minusYears(18);
        String degree = "NTUU KPI";
        LocalDate firstDay = LocalDate.now();
        String rank = "phd";
        Integer salary = 1000;
        String title = "teacher";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("firstName", firstName);
        params.add("lastName", lastName);
        params.add("birthDay", birthDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        params.add("rank", rank);
        params.add("degree", degree);
        params.add("firstDay", firstDay.toString());
        params.add("salary", String.valueOf(salary));
        params.add("title", title);
        Integer pageNumber = 0;
        Integer size = 10;
        Pageable pageable = PageRequest.of(pageNumber,size);
        Integer expectedSize = 3;

        mockMvc.perform(post("/teachers").params(params))
                .andExpect(status().isCreated());
        assertEquals(expectedSize, teacherService.findAll(pageable).getContent().size());
    }


    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void updateTeacher_shouldReturnResetContentAndUpdateTeacher_WhenTeacherExists() throws Exception {
        TeacherDto existentTeacher = teacherService.findAll(pageableDefault).getContent().get(0);
        String id = existentTeacher.getId().toString();
        String changedFirstname = "CHANGED";
        String existedLastName = existentTeacher.getLastName();
        LocalDate existedBirthDay = existentTeacher.getBirthDay().minusYears(18);
        LocalDate existedFirstDay = existentTeacher.getFirstDay();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("firstName", changedFirstname);
        params.add("lastName", existedLastName);
        params.add("birthDay", existedBirthDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        params.add("firstDay", existedFirstDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


        mockMvc.perform(put("/teachers/{id}", id).params(params))
                .andExpect(status().isResetContent());
        assertEquals(changedFirstname, teacherService.findById(UUID.fromString(id)).getFirstName());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getTeacher_shouldReturnTeacherAndOk_WhenTeacherExists() throws Exception {
        TeacherDto existentTeacher = teacherService.findAll(pageableDefault).getContent().get(0);
        String id = existentTeacher.getId().toString();


        mockMvc.perform(get("/teachers/{id}", id))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void deleteTeacher_shouldReturnNoContentAndDelete_WhenTeacherExists() throws Exception {
        TeacherDto existentTeacher = teacherService.findAll(pageableDefault).getContent().get(0);
        String id = existentTeacher.getId().toString();
        Integer pageNumber = 0;
        Integer size = 10;
        Pageable pageable = PageRequest.of(pageNumber,size);
        Integer expectedSize = 1;

        mockMvc.perform(delete("/teachers/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertEquals(expectedSize, teacherService.findAll(pageable).getContent().size());

    }

}