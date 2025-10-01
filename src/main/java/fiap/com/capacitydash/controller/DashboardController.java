package fiap.com.capacitydash.controller;

import fiap.com.capacitydash.model.Department;
import fiap.com.capacitydash.model.Motorcycle;
import fiap.com.capacitydash.service.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final QrCodeService qrCode;
    private final DepartamentService departamentService;
    private final AlertService alertService;
    private final ParkingSpaceService parkingSpaceService;
    private final MotorcycleService motorcycleService;

    @GetMapping
    public String  index(Model model){
        var departments = departamentService.findAllDepartments();
        var alerts = alertService.getAllAlerts();
        var motorcycles = motorcycleService.getAllMotorcycles();
        var parking = parkingSpaceService.findAll();
        Map<Long, Long> activeAlertsCount = departments.stream()
                .collect(Collectors.toMap(
                        Department::getId,
                        d -> d.getAlerts().stream()
                                .filter(a -> Objects.nonNull(a.getActive()) && a.getActive())
                                .count()
                ));

        model.addAttribute("departments", departments);
        model.addAttribute("alerts", alerts);
        model.addAttribute("parking", parking);
        model.addAttribute("motorcycles", motorcycles);
        model.addAttribute("activeAlertsCount", activeAlertsCount);
        return "dashboard";
    }

    @GetMapping("/details/{id}")
    public String departmentDetails(@PathVariable Long id, Model model){
        var department = departamentService.findDepartmentById(id);


        if (department == null) {
            return "redirect:/dashboard";
        }

        var parkingSpaces = parkingSpaceService.findAll();
        var alerts = alertService.getAllAlerts();


        model.addAttribute("department", department);
        model.addAttribute("allParkingSpaces", parkingSpaces);
        model.addAttribute("allAlerts", alerts);
        return "dashboard-details";
    }

}
