package space.vakar.bugtracker.comment;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import space.vakar.bugtracker.bug.BugEntity;
import space.vakar.bugtracker.user.AppUserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "bug_comment")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public @Data class BugCommentEntity implements Serializable {

  private static final long serialVersionUID = 3795851772488391522L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull
  @Size(max = 1023)
  @NotBlank(message = "Comment content is mandatory")
  private String content;

  @ToString.Exclude @EqualsAndHashCode.Exclude @CreationTimestamp private Timestamp timeOfCreation;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(optional = false)
  @JoinColumn(name = "creator_id")
  private AppUserEntity creator;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(optional = false)
  private BugEntity bug;

  public static final BugCommentEntity EMPTY =
      BugCommentEntity.builder()
          .id(0)
          .content("")
          .creator(AppUserEntity.EMPTY)
          .bug(BugEntity.EMPTY)
          .build();
}
