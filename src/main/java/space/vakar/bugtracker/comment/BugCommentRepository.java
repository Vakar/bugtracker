package space.vakar.bugtracker.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugCommentRepository extends JpaRepository<BugCommentEntity, Integer> {}
