package com.secondWind.modooDiary.api.diary.domain.entity;

import com.secondWind.modooDiary.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted = false")
public class Drawing extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drawing_id")
    private Long id;
    private String displayUrl;
    private String deleteUrl;
    private int isDeleted;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public Drawing(Long id, String displayUrl, String deleteUrl, int isDeleted, Diary diary) {
        this.id = id;
        this.displayUrl = displayUrl;
        this.deleteUrl = deleteUrl;
        this.isDeleted = isDeleted;
    }

    @Builder(builderMethodName = "getDrawingBuilder", builderClassName = "getDrawingBuilder")
    public Drawing(String displayUrl, String deleteUrl) {
        this.displayUrl = displayUrl;
        this.deleteUrl = deleteUrl;
    }

    public void deleteDrawing() {
        this.isDeleted = 1;
    }
}