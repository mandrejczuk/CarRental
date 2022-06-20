package com.example.projekt.repositories;

import com.example.projekt.models.Reservation;
import com.example.projekt.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

    Boolean existsByValue(String value);
    Token findByValue(String value);
    Token findByReservation(Reservation reservation);
    Boolean existsByReservation(Reservation reservation);
}
