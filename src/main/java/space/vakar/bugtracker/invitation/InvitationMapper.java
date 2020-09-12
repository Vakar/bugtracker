package space.vakar.bugtracker.invitation;

public class InvitationMapper {

  public static InvitationDto from(InvitationEntity entity) {
    return InvitationDto.builder().acceptance(entity.getAcceptance()).build();
  }

  public static InvitationEntity from(InvitationDto dto) {
    return InvitationEntity.builder().acceptance(dto.getAcceptance()).build();
  }

  private InvitationMapper() {}
}
