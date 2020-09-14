package space.vakar.bugtracker.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import space.vakar.bugtracker.bug.BugEntity;
import space.vakar.bugtracker.user.AppUserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "project")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public @Data class ProjectEntity implements Serializable {

  private static final long serialVersionUID = -8298958353983954771L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull
  @Size(max = 255)
  @NotBlank(message = "Title is mandatory")
  private String title;

  @NotNull
  @Size(max = 1023)
  @NotBlank(message = "Description is mandatory")
  private String description;

  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private AppUserEntity owner;

  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(orphanRemoval = true, mappedBy = "project")
  private List<BugEntity> bugs;

  public static final ProjectEntity EMPTY =
      ProjectEntity.builder()
          .id(0)
          .title("")
          .description("")
          .owner(AppUserEntity.EMPTY)
          .bugs(Collections.emptyList())
          .build();
}
