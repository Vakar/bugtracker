package space.vakar.bugtracker.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class ProjectDto {
  private int id;
  private String title;
  private String description;
}
