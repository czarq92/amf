package org.amateurfootball.repository;

import org.amateurfootball.model.MatchStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchStatisticsRepository extends JpaRepository<MatchStatistics,Long> {
}
