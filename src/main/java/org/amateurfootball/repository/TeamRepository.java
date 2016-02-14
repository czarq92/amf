package org.amateurfootball.repository;

import org.amateurfootball.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long>{
	public Team findTeamByName(String name);
}
