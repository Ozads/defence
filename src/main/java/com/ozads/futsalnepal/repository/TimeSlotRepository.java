package com.ozads.futsalnepal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozads.futsalnepal.model.Court;
import com.ozads.futsalnepal.model.Place;
import com.ozads.futsalnepal.model.TimeSlot;

public interface TimeSlotRepository extends JpaRepository<TimeSlot,Long> {

	List<TimeSlot> findTimeSlotByCourtId(Long id);

	TimeSlot findTimeSlotById(Long timeSlotId);

	

}
