package com.suraj.repository;

import com.suraj.modal.Project;
import com.suraj.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

//    List<Project> findByOwner(User user);

//    @Query("Select p From Project p join p.team t where t=:user")
//    List<Project> findProjectByTeam(@Param("user") User user);

    List<Project> findByNameContainingAndTeamContains(String partialName, User user);

    List<Project> findByTeamContainingOrOwner(User user, User owner);
}
