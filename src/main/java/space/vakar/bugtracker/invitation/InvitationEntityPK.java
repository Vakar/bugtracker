package space.vakar.bugtracker.invitation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class InvitationEntityPK implements Serializable {
  private static final long serialVersionUID = 1942181300842087010L;
  private int fromUser;
  private int toUser;
  private int project;
}
