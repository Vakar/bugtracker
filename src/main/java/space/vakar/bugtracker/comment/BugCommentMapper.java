package space.vakar.bugtracker.comment;

public class BugCommentMapper {

  public static BugCommentDto from(BugCommentEntity entity) {
    return BugCommentDto.builder().content(entity.getContent()).build();
  }

  public static BugCommentEntity from(BugCommentDto dto) {
    return BugCommentEntity.builder().content(dto.getContent()).build();
  }

  private BugCommentMapper() {}
}
