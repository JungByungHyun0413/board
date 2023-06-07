package org.koreait.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
public class BoardApiSaveTest {

    @Autowired
    private MockMvc mockMvc;

    private String getParams(String subject, String content){
        String params = String.format("{\"subject\":\"%s\", \"content\":\"%s\"}", subject, content);
        return params;
    }

    @Test
    @DisplayName("게시글 작성 성공시 응답 코드 201")
    void saveSuccessTest() throws Exception {
        // {"subject" : "테스트 제목", "content" : "테스트 내용"}
        String params = getParams("테스트 제목", "테스트 내용");
        mockMvc.perform(post("/api/board/write")
                .contentType("application/json")
                .content(params))
                .andDo(print())
                .andExpect(status().isCreated());

    }
    @Test
    @DisplayName("필수항목(제목, 내용) 누락시 발생 문구 테스트")
    void requiredSubjectTest() throws Exception {
        String params = getParams("", "테스트 내용");
        String body = mockMvc.perform(post("/api/board/write")
                .contentType("application/json")
                .content(params))
                .andReturn()
                .getResponse()
                .getContentAsString(Charset.forName("UTF-8")); // 응답 Body 데이터
        System.out.println(body);
    }
}
