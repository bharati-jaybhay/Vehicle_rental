package com.demo.vehicle.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Please enter vehicle name")
    private String vehicleName;

    @NotBlank(message = "Please enter description")
    @Size(min = 10, max = 255, message = "Description must be between 10 and 255 characters")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Price is required")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @NotBlank(message = "Please enter image URL")
    private String imgUrl;

    @NotBlank(message = "Please enter vehicle status")
    public enum Status {
        AVAILABLE,
        BOOKED
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotBlank(message = "Please enter vehicle name") String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(@NotBlank(message = "Please enter vehicle name") String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public @NotBlank(message = "Please enter description") @Size(min = 10, max = 255, message = "Description must be between 10 and 255 characters") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Please enter description") @Size(min = 10, max = 255, message = "Description must be between 10 and 255 characters") String description) {
        this.description = description;
    }

    public @NotNull(message = "Please enter vehicle price") Double getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Please enter vehicle price") Double price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public @NotBlank(message = "Please enter image URL") String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(@NotBlank(message = "Please enter image URL") String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
