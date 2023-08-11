package com.ssafy.http.apis.studentlibraries.entities;

import com.ssafy.http.apis.themes.entities.ThemeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "homework_histories")
public class HomeworkHistoriesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "member_id", nullable = false)
  private Long memberId;

  @ManyToOne
  @JoinColumn(name = "theme_id", nullable = false)
  private ThemeEntity themeEntity;

  @Column(name = "status_code", nullable = false)
  private String status;

  @Column(name = "class_id", nullable = false)
  private Long classId;

  @Builder
  public HomeworkHistoriesEntity(Long id, Long memberId, ThemeEntity themeEntity, String status,
      Long classId) {
    this.id = id;
    this.memberId = memberId;
    this.themeEntity = themeEntity;
    this.status = status;
    this.classId = classId;
  }
}