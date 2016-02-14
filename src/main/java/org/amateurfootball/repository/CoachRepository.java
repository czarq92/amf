package org.amateurfootball.repository;

import org.amateurfootball.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<Coach, Long>{
	public Coach findCoachByLogin(String login);
}
