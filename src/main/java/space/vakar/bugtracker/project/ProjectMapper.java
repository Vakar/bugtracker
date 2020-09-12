package space.vakar.bugtracker.project;

public class ProjectMapper {

  public static ProjectDto from(ProjectEntity entity) {
    return ProjectDto.builder()
        .id(entity.getId())
        .title(entity.getTitle())
        .description(entity.getDescription())
        .build();
  }

  public static ProjectEntity from(ProjectDto dto) {
    return ProjectEntity.builder()
        .id(dto.getId())
        .title(dto.getTitle())
        .description(dto.getDescription())
        .build();
  }

  public static ProjectEntity merge(ProjectEntity entity, ProjectDto dto) {
    entity.setTitle(dto.getTitle());
    entity.setDescription(dto.getDescription());
    return entity;
  }

  private ProjectMapper() {}
}
