package space.vakar.bugtracker.user;

public class AppUserMapper {

  public static AppUserDto from(AppUserEntity entity) {
    return AppUserDto.builder().id(entity.getId()).name(entity.getName()).build();
  }

  public static AppUserEntity from(AppUserDto dto) {
    return AppUserEntity.builder().id(dto.getId()).name(dto.getName()).build();
  }

  private AppUserMapper() {}
}
