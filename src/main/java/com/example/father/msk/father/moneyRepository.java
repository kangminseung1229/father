package com.example.father.msk.father;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface moneyRepository extends JpaRepository<money, Long> {

    Optional<money> findByDatememo(LocalDate now);


    // 월별 합계
    @Query(value = "SELECT sum(totalPrice) from money where year = :year AND  month = :month ", nativeQuery = true)
    Long sumByMonth(int year, int month);

    // 납입금 월별 합계
    @Query(value = "SELECT sum(companyPrice) FROM money where year = :year AND month = :month ;", nativeQuery = true)
    Long sumCompanyPrice(int year, int month);

    // 소득 월별 합계
    @Query(value = "SELECT sum(myPrice) FROM money where year = :year AND month = :month ;", nativeQuery = true)
    Long sumMyPrice(int year, int month);

    // 해당 날짜 객체 반환
    Optional<money> findByDatememo(String datememo);


    Optional<List<money>> findByYearAndMonthOrderByDatememo(int year, int month);

}
