package com.secondWind.modooDiary.api.diary.repository;

import com.secondWind.modooDiary.api.diary.domain.entity.Diary;
import com.secondWind.modooDiary.api.diary.domain.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Weather findByStatusId(Double weatherStatusId);
}
