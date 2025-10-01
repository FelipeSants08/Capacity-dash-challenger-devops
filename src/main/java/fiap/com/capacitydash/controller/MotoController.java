package fiap.com.capacitydash.controller;

import fiap.com.capacitydash.model.Motorcycle;
import fiap.com.capacitydash.repository.MotorcycleRepository;
import fiap.com.capacitydash.service.MotorcycleService;
import fiap.com.capacitydash.service.QrCodeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("dashboard")
@RequiredArgsConstructor
public class MotoController {


    private final MotorcycleService motorcycleService;
    private final QrCodeService qrCode;

    @GetMapping("/moto/{plate}")
    public String buscarMoto(@PathVariable String plate, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        Motorcycle motorcycle = motorcycleService.findByPlate(plate);

        if (motorcycle == null) {
            redirectAttributes.addFlashAttribute("error", "Motocicleta com a placa: " + plate + " não encontrada.");
            return "redirect:/dashboard";
        }
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String motoUrl = baseUrl + "/dashboard/moto/" + motorcycle.getPlate();
        model.addAttribute("motorcycle", motorcycle);
        String base64 = qrCode.generateQRCode(motoUrl);
        model.addAttribute("base64", base64);

        return "dashboard-moto";
    }

    @GetMapping("/moto")
    public String allMotorcycle(Model model){
        List<Motorcycle> motorcycles = motorcycleService.getAllMotorcycles();

        model.addAttribute("motorcycles", motorcycles);

        return "dashboard-moto-all";
    }

    @DeleteMapping("moto/{id}")
    public String deleteMoto(@PathVariable Long id, RedirectAttributes redirect ){
        motorcycleService.deleteById(id);
        redirect.addFlashAttribute("message", "Moto deletada com sucesso!");
        return "redirect:/dashboard/moto";
    }

}
