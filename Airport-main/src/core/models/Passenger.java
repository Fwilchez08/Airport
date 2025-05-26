/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

/*import core.models.Flight;
import core.utils.Sujeto;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;*/
import core.utils.Sujeto;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author edangulo
 */
public class Passenger extends Sujeto {

     private final String id;
    private final LocalDate birthDate;
    

    private String firstname;
    private String lastname;
    private int countryPhoneCode;
    private long phone;
    private String country;
    
    
    private final List<Flight> flights = new CopyOnWriteArrayList<>();

    
    public Passenger(String id, String firstname, String lastname, LocalDate birthDate, int countryPhoneCode, long phone, String country1) {

        
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.countryPhoneCode = countryPhoneCode;
        this.phone = phone;
        this.country = country;
    }
    

    public Passenger(Passenger passenger) {
        this(passenger.id, passenger.firstname, passenger.lastname, passenger.birthDate,
             passenger.countryPhoneCode, passenger.phone, passenger.country);
        
        
        passenger.flights.forEach(flight -> 
            this.flights.add(flight != null ? flight.clonar() : null)
        );
    }
    
    
   /* private void validateParameters(String id, String firstname, String lastname, 
                                  LocalDate birthDate, int countryPhoneCode, 
                                  long phone, String country) {
        
        if (firstname == null || firstname.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastname == null || lastname.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid birth date");
        }
        if (countryPhoneCode <= 0) {
            throw new IllegalArgumentException("Country code must be positive");
        }
        if (phone <= 0) {
            throw new IllegalArgumentException("Phone number must be positive");
        }
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
    }*/
    
    public String getId() {
        return id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        if (firstname == null || firstname.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        if (lastname == null || lastname.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        this.lastname = lastname;
    }

    public int getCountryPhoneCode() {
        return countryPhoneCode;
    }

    public void setCountryPhoneCode(int countryPhoneCode) {
        if (countryPhoneCode <= 0) {
            throw new IllegalArgumentException("Country code must be positive");
        }
        this.countryPhoneCode = countryPhoneCode;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        if (phone <= 0) {
            throw new IllegalArgumentException("Phone number must be positive");
        }
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

   /* public void setCountry(String country) {
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
        this.country = country;
    }*/
    
    
    public List<Flight> getFlights() {
        return Collections.unmodifiableList(flights);
    }

    public void addFlight(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Flight cannot be null");
        }
        this.flights.add(flight);
    }
    
   
    public String getFullname() {
        return firstname + " " + lastname;
    }
    
    public String generateFullPhone() {
        return "+" + countryPhoneCode + " " + phone;
    }
    
    public int calculateAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    
    public int getNumFlights() {
        return flights.size();
    }
    
    public boolean hasInternationalFlights() {
        return flights.stream()
            .anyMatch(flight -> !flight.getArrivalLocation().getAirportCountry().equals(this.country));
    }
    
   
    public Passenger clonar() {
        return new Passenger(this);
    }
}
