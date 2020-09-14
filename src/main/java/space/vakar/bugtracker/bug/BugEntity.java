package space.vakar.bugtracker.bug;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import space.vakar.bugtracker.comment.BugCommentEntity;
import space.vakar.bugtracker.project.ProjectEntity;
import space.vakar.bugtracker.user.AppUserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "bug")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public @Data class BugEntity implements Serializable {

  private static final long serialVersionUID = 376691134879290387L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull
  @Size(max = 255)
  @NotBlank(message = "Title is mandatory")
  private String title;

  @NotNull
  @Size(max = 1023)
  @NotBlank(message = "Steps to reproduce is mandatory")
  private String stepsToReproduce;

  @NotNull
  @Size(max = 1023)
  @NotBlank(message = "Expected results is mandatory")
  private String expectedResults;

  @NotNull
  @Size(max = 1023)
  @NotBlank(message = "Actual results is mandatory")
  private String actualResults;

  @NotNull
  @Enumerated(EnumType.STRING)
  private FixStatus fixStatus;

  @JsonIgnore
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "creator_id")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private AppUserEntity creator;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fixer_id")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private AppUserEntity fixer;

  @JsonIgnore
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private ProjectEntity project;

  @JsonIgnore
  @OneToMany(orphanRemoval = true, mappedBy = "bug")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<BugCommentEntity> comments;

  public static final BugEntity EMPTY =
      BugEntity.builder()
          .id(0)
          .title("")
          .stepsToReproduce("")
          .expectedResults("")
          .actualResults("")
          .fixStatus(FixStatus.NEW)
          .creator(AppUserEntity.EMPTY)
          .fixer(AppUserEntity.EMPTY)
          .project(ProjectEntity.EMPTY)
          .comments(Collections.emptyList())
          .build();
}
