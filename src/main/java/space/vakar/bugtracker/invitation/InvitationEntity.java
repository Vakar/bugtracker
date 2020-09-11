package space.vakar.bugtracker.invitation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.vakar.bugtracker.project.ProjectEntity;
import space.vakar.bugtracker.user.AppUserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "invitation")
@IdClass(InvitationEntityPK.class)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class InvitationEntity implements Serializable {

  private static final long serialVersionUID = -8556658652604622045L;

  @Id
  @ManyToOne(optional = false)
  private AppUserEntity fromUser;

  @Id
  @ManyToOne(optional = false)
  private AppUserEntity toUser;

  @Id
  @ManyToOne(optional = false)
  private ProjectEntity project;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Acceptance acceptance;
}
