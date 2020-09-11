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
  private int fromUser;
  private int toUser;
  private int project;
}
