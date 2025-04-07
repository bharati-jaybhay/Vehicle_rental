package com.demo.vehicle.service;

import com.demo.vehicle.model.Vehicle;
import com.demo.vehicle.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService{

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle getVehicleById(int id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found!!"));
    }

    @Override
    public void deleteVehicleById(int id) {
        vehicleRepository.deleteById(id);
    }
}
