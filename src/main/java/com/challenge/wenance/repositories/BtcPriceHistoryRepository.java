package com.challenge.wenance.repositories;

import com.challenge.wenance.models.entities.BtcPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface BtcPriceHistoryRepository extends JpaRepository<BtcPriceHistory, Long> {

    List<BtcPriceHistory> findByCreated(Timestamp at);

    @Query(value = "SELECT avg(price) FROM BtcPriceHistory WHERE created >= :from and created <= :to")
    Optional<Double> getPriceAverageBetweenTimeStamps(@Param("from") Timestamp from, @Param ("to")Timestamp to);

    @Query(value="select MAX(price) FROM BtcPriceHistory")
    Double getMaxBTCPrice();



}
