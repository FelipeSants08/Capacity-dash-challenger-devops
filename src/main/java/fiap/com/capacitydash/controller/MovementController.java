package fiap.com.capacitydash.controller;

import fiap.com.capacitydash.model.MovementType;
import fiap.com.capacitydash.service.MovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
@RequestMapping("/movement")
public class MovementController {
    private final MovementService movementService;

    @GetMapping("/form")
    public String showMovementForm() {
        return "movement-form";
    }

    @PostMapping("/register")
    public String registerMovement(
            @RequestParam("motorcyclePlate") String motorcyclePlate,
            @RequestParam(value = "parkingCode",required = false) String parkingCode,
            @RequestParam MovementType movementType,
            RedirectAttributes redirectAttributes) {

        try {
            if (movementType == MovementType.ENTRADA) {
                movementService.entrada(motorcyclePlate, parkingCode);
            } else {
                movementService.exit(motorcyclePlate);
            }
            redirectAttributes.addFlashAttribute("message", "Movimento registrado com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/dashboard";
    }
}