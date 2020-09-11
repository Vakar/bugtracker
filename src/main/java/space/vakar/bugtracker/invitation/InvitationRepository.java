package space.vakar.bugtracker.invitation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<InvitationEntity, InvitationEntityPK> {}
