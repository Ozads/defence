package com.ozads.futsalnepal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozads.futsalnepal.model.Login;
import com.ozads.futsalnepal.model.Place;

public interface PlaceRepository extends JpaRepository<Place,Long> {

}
