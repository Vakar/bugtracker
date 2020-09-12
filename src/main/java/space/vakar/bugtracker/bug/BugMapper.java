package space.vakar.bugtracker.bug;

public class BugMapper {

  public static BugDto from(BugEntity entity) {
    return BugDto.builder()
        .id(entity.getId())
        .title(entity.getTitle())
        .stepsToReproduce(entity.getStepsToReproduce())
        .expectedResults(entity.getExpectedResults())
        .actualResults(entity.getActualResults())
        .fixStatus(entity.getFixStatus())
        .build();
  }

  public static BugEntity from(BugDto dto) {
    return BugEntity.builder()
        .id(dto.getId())
        .title(dto.getTitle())
        .stepsToReproduce(dto.getStepsToReproduce())
        .expectedResults(dto.getExpectedResults())
        .actualResults(dto.getActualResults())
        .fixStatus(dto.getFixStatus())
        .build();
  }

  public static BugEntity merge(BugEntity entity, BugDto dto) {
    entity.setTitle(dto.getTitle());
    entity.setStepsToReproduce(dto.getStepsToReproduce());
    entity.setExpectedResults(dto.getExpectedResults());
    entity.setActualResults(dto.getActualResults());
    entity.setFixStatus(dto.getFixStatus());
    return entity;
  }

  private BugMapper() {}
}
