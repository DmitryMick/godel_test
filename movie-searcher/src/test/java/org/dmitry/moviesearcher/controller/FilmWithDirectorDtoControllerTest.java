package org.dmitry.moviesearcher.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dmitry.moviesearcher.dto.FilmDirectorRespDto;
import org.dmitry.moviesearcher.dto.LastNameDatesRequestDto;
import org.dmitry.moviesearcher.service.FilmWithDirectorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
class FilmWithDirectorDtoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmWithDirectorService service;

    @Autowired
    private FilmWithDirectorDtoController controller;

    @Test
    void controllerIsNotNull_thanOK() {
        Assertions.assertNotNull(controller);
    }

    @Test
    void ifResponseContentTypeIsJSon_ThenOK() throws Exception {
        String type = mockMvc.perform(MockMvcRequestBuilders.get("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(new LastNameDatesRequestDto())))
                .andReturn()
                .getResponse()
                .getContentType();

        assertEquals(MediaType.APPLICATION_JSON_VALUE, type);
    }

    @Test
    void whenWrongLastNameFormatInRequest_thanBadRequestContainsErrorJSon() throws Exception {
        String intName = "111";
        LastNameDatesRequestDto requestDto = new LastNameDatesRequestDto(intName, null, null);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(requestDto)))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);

        assertTrue(content.contains("\"status\":\"BAD_REQUEST\""));
        assertTrue(content.contains("\"fieldName\":\"lastName\""));
        assertTrue(content.contains("\"rejectedValue\":\"111\""));

        String messageError = "\"messageError\":\"The director's last name must be in latin letters with \\\"-\\\" and \\\"'\\\" as delimiters.\"";
        assertTrue(content.contains(messageError));
    }

    @Test
    void whenCorrectRequestData_thanResponseNotNullWithStatusOk() throws Exception {
        String intName = "Burton";
        LastNameDatesRequestDto requestDto = new LastNameDatesRequestDto(intName, null, null);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(requestDto)))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        FilmDirectorRespDto[] responseDtoList = mapFromJson(content, FilmDirectorRespDto[].class);
        assertNotNull(responseDtoList);
    }

    private <T> T mapFromJson(String json, Class<T> clazz) throws JsonParseException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}