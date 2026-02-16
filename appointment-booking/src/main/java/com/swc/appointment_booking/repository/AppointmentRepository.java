package com.swc.appointment_booking.repository;

import com.swc.appointment_booking.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    List<Appointment> findByUserEmail (String email);

    List<Appointment> findByUserName (String name);
}
