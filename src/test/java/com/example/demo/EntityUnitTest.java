package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    private Doctor d1;

    private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    @BeforeAll
    void setUp() {
        
        d1 = new Doctor("John", "Doe", 35, "john.doe@example.com");
        p1 = new Patient("Jane", "Doe", 25, "jane.doe@example.com");
        r1 = new Room("Dermatology");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startsAt1 = LocalDateTime.parse("10:00 01/01/2023", formatter);
        LocalDateTime finishesAt1 = LocalDateTime.parse("11:00 01/01/2023", formatter);

        LocalDateTime startsAt2 = LocalDateTime.parse("14:00 01/01/2023", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("15:00 01/01/2023", formatter);

        LocalDateTime startsAt3 = LocalDateTime.parse("16:00 01/01/2023", formatter);
        LocalDateTime finishesAt3 = LocalDateTime.parse("17:00 01/01/2023", formatter);

        a1 = new Appointment(p1, d1, r1, startsAt1, finishesAt1);
        a2 = new Appointment(p1, d1, r1, startsAt2, finishesAt2);
        a3 = new Appointment(p1, d1, r1, startsAt3, finishesAt3);
    }

    @Test
    void testDoctorEntity() {
        
        Doctor savedDoctor = entityManager.persistAndFlush(d1);
        Doctor retrievedDoctor = entityManager.find(Doctor.class, savedDoctor.getId());

        assertThat(retrievedDoctor).isEqualTo(d1);
    }

    @Test
    void testPatientEntity() {
        Patient savedPatient = entityManager.persistAndFlush(p1);
        Patient retrievedPatient = entityManager.find(Patient.class, savedPatient.getId());

        assertThat(retrievedPatient).isEqualTo(p1);
    }

    @Test
    void testRoomEntity() {
        // Aquí puedes escribir pruebas específicas para la entidad Room
        Room savedRoom = entityManager.persistAndFlush(r1);
        Room retrievedRoom = entityManager.find(Room.class, savedRoom.getRoomName());

        assertThat(retrievedRoom).isEqualTo(r1);
    }

    @Test
    void testAppointmentEntity() {
        // Aquí puedes escribir pruebas específicas para la entidad Appointment
        Appointment savedAppointment = entityManager.persistAndFlush(a1);
        Appointment retrievedAppointment = entityManager.find(Appointment.class, savedAppointment.getId());

        assertThat(retrievedAppointment).isEqualTo(a1);
    }
}

