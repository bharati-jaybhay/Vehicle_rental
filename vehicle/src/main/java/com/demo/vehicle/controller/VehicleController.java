package com.demo.vehicle.controller;

import com.demo.vehicle.model.Vehicle;
import com.demo.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.regex.Pattern;

@Controller
@AllArgsConstructor
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;


    @GetMapping("/admin-login")
    public String showLoginPage() {
        return "admin-login";
    }

    // Show add vehicle form
    @GetMapping("/vehicles/add")
    public String showAddVehicleForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        model.addAttribute("btn_name", "Save");
        return "form";
    }

    // Handle form submission
    @PostMapping("/vehicles/add")
    public String saveVehicle(@RequestParam(name = "id") String idStr,
                              @Valid @ModelAttribute Vehicle vehicle,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "form";
        }

        String regex = "^\\d+$";
        if (Pattern.matches(regex, idStr)) {
            int id = Integer.parseInt(idStr);
            vehicle.setId(id);

            vehicleService.saveVehicle(vehicle);

            if (id != 0) {
                redirectAttributes.addFlashAttribute("successMessage", "Vehicle updated successfully.");
                return "redirect:/vehicles/view/" + id;
            } else {
                redirectAttributes.addFlashAttribute("successMessage", "Vehicle added successfully.");
                return "redirect:/vehicles/add";
            }

        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "ID must be numeric.");
            return "redirect:/vehicles";
        }
    }


    @GetMapping("/showAll")
    public String ShowAllVehicles(Model model) {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("allVehicles", vehicles);
        return "index";
    }

    @GetMapping("/vehicles/view/{id}")
    public String showVehicleById(@PathVariable(name = "id") String idStr, Model model, RedirectAttributes redirectAttributes) {
        String regex = "^\\d+$";
        if (Pattern.matches(regex, idStr)) {
            int id = Integer.parseInt(idStr);
            Vehicle vehicle = vehicleService.getVehicleById(id);

            if (vehicle != null) {
                model.addAttribute("vehicle", vehicle);
                return "show-vehicle"; // your Thymeleaf page
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Your vehicle not found in our record!");
                return "redirect:/vehicles";
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Id cannot be string!");
            return "redirect:/vehicles";
        }
    }

    // Delete Vehicle by ID
    @GetMapping("/vehicles/delete")
    public String deleteVehicleById(@RequestParam(name = "id") String idStr, RedirectAttributes redirectAttributes) {
        String regex = "^\\d+$";
        if (Pattern.matches(regex, idStr)) {
            int id = Integer.parseInt(idStr);
            Vehicle vehicle = vehicleService.getVehicleById(id);

            if (vehicle != null) {
                vehicleService.deleteVehicleById(id);
                redirectAttributes.addFlashAttribute("errorMessage", "Vehicle Deleted Successfully!");
                return "redirect:/index";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Your vehicle not found in our record!");
                return "redirect:/vehicles";
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Id cannot be string!");
            return "redirect:/vehicles";
        }
    }

    // Show Edit Vehicle Form
    @GetMapping("/vehicles/edit")
    public String showUpdateVehicleForm(@RequestParam(name = "id") String idStr, RedirectAttributes redirectAttributes, Model model) {
        String regex = "^\\d+$";
        if (Pattern.matches(regex, idStr)) {
            int id = Integer.parseInt(idStr);
            Vehicle vehicle = vehicleService.getVehicleById(id);

            if (vehicle != null) {
                model.addAttribute("vehicle", vehicle);
                model.addAttribute("btn_name", "Update");
                return "form"; // form to update vehicle
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Your vehicle not found in our record!");
                return "redirect:/vehicles";
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Id cannot be string!");
            return "redirect:/vehicles";
        }
    }

}
