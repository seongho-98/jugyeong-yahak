package com.ssafy.http.apis.members.entities;

import com.ssafy.http.apis.roles.entities.RoleEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "members")
@ToString
public class MemberEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long governmentId;

  @Column(nullable = true)
  private Long classId;

  @ManyToOne
  //@JoinColumn(name = "roles", nullable = false)
  @JoinColumn(name = "role", nullable = false)
  private RoleEntity role;

  @Column(nullable = false)
  private String statusCode;

  @Column(nullable = false)
  private String uuid;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String phone;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String faceImageUrl;

  @Column(nullable = false)
  private String firstResponder;

  @Column(nullable = true)
  private Long tabletNo;

  @Column(nullable = false)
  private Integer gender;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Builder
  public MemberEntity(Long id, Long governmentId, Long classId, RoleEntity role, String statusCode,
      String uuid, String password, String name, String phone, String address, String faceImageUrl,
      String firstResponder, Long tabletNo, LocalDateTime createdAt, LocalDateTime updatedAt,
      Integer gender) {
    this.id = id;
    this.gender = gender;
    this.governmentId = governmentId;
    this.classId = classId;
    this.role = role;
    this.statusCode = statusCode;
    this.uuid = uuid;
    this.password = password;
    this.name = name;
    this.phone = phone;
    this.address = address;
    this.faceImageUrl = faceImageUrl;
    this.firstResponder = firstResponder;
    this.tabletNo = tabletNo;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public void setClassId(Long classId) {
    this.classId = classId;
  }

  @PrePersist
  public void createTimeStamps() {
    updatedAt = LocalDateTime.now();
    createdAt = LocalDateTime.now();
  }

  @PreUpdate
  public void updateTimeStamps() {
    updatedAt = LocalDateTime.now();
  }

  public void encodePassword(PasswordEncoder passwordEncoder) {
    password = passwordEncoder.encode(password);
  }
}
