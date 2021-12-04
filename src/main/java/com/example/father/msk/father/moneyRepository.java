package com.example.father.msk.father;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface moneyRepository extends JpaRepository<money, Long> {

    Optional<money> findByDatememo(LocalDate now);

    List<money> findBymonthOrderByDatememo(int month);

    // 월별 합계
    @Query(value = "SELECT sum(totalPrice) from money where month = :month ", nativeQuery = true)
    Long sumByMonth(int month);

}
