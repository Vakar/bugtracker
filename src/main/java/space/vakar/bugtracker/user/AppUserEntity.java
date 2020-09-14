package space.vakar.bugtracker.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import space.vakar.bugtracker.invitation.InvitationEntity;
import space.vakar.bugtracker.project.ProjectEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "app_user")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public @Data class AppUserEntity implements Serializable {

  private static final long serialVersionUID = -4792778241440574324L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull
  @Size(max = 255)
  @NotBlank(message = "User name is mandatory")
  private String name;

  @JsonIgnore
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "owner", orphanRemoval = true)
  private List<ProjectEntity> projects;

  @JsonIgnore
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "toUser", orphanRemoval = true)
  private List<InvitationEntity> invitationsToUser;

  @JsonIgnore
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "fromUser", orphanRemoval = true)
  private List<InvitationEntity> invitationsFromUser;

  public static final AppUserEntity EMPTY =
      AppUserEntity.builder()
          .id(0)
          .name("")
          .projects(Collections.emptyList())
          .invitationsToUser(Collections.emptyList())
          .invitationsFromUser(Collections.emptyList())
          .build();
}
