package com.secondWind.modooDiary.api.diary.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@EntityScan({"com.secondWind.modooDiary.api.diary", "com.secondWind.modooDiary.api.member",
        "com.secondWind.modooDiary.api.quiz"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DiaryControllerTest {

    @Autowired
    private MockMvc mvc;

//    @MockBean
//    private MemberRepository memberRepository;
//
//    @MockBean
//    private QuizRepository quizRepository;

    @Test
    void getDiaries() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/api/diaries")
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andReturn();


    }
}