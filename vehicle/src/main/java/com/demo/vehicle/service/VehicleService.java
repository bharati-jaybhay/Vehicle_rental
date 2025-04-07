package com.demo.vehicle.service;

import com.demo.vehicle.model.Vehicle;

import java.util.List;

public interface VehicleService {

    Vehicle saveVehicle(Vehicle vehicle);


    List<Vehicle> getAllVehicles();

    Vehicle getVehicleById(int id);

    void deleteVehicleById(int id);
}
