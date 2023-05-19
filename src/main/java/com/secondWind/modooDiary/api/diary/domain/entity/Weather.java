package com.secondWind.modooDiary.api.diary.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Weather {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="status_id")
    private Long statusId;
    private String main;
    private String description;

    @OneToMany(mappedBy = "weather")
    private List<Diary> diaryList = new ArrayList<>();

    @Builder(builderMethodName = "of", builderClassName = "of")
    public Weather(Long statusId, String main, String description) {
        this.statusId = statusId;
        this.main = main;
        this.description = description;
    }
}
