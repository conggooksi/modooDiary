package com.secondWind.modooDiary.api.member.auth.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.secondWind.modooDiary.api.diary.service.DiaryServiceImpl;
import com.secondWind.modooDiary.api.member.repository.MemberRepository;
import com.secondWind.modooDiary.api.quiz.repository.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private DiaryServiceImpl diaryService;

    @Mock
    private MemberRepository memberRepository;
    @MockBean
    private QuizRepository quizRepository;

    private final static String BASE_URL = "/api/auth";

    @Test
    @Transactional
    @DisplayName("로그인")
    public void login() throws Exception {
        // when
        String url = "/login";
        String loginId = "kimgeonkuk@gmail.com";
        String password = "1234";
        String originalInput = loginId + ":" + password;
        String encodedIdAndPw = Base64.getEncoder().encodeToString(originalInput.getBytes());

        MvcResult mvcResult = mvc.perform(post(BASE_URL + url)
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedIdAndPw))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // then
        JsonElement data = JsonParser.parseString(mvcResult.getResponse().getContentAsString()).getAsJsonObject().get("data");
        log.info(data.toString());
//    parser.parse(mvcResult.getResponse().getContentAsString()).getAsJsonObject().get("data");

//    assertThat(parser.parse(mvcResult.getResponse().getContentAsString()).getAsJsonObject().get("data").getAsLong())
//            .isInstanceOf(Long.class);
    }

}