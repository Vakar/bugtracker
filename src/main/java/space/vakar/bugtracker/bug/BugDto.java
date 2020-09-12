package space.vakar.bugtracker.bug;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class BugDto {
  private int id;
  private String title;
  private String stepsToReproduce;
  private String expectedResults;
  private String actualResults;
  private FixStatus fixStatus;
}
