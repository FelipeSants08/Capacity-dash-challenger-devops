package fiap.com.capacitydash.controller;

import fiap.com.capacitydash.model.Motorcycle;
import fiap.com.capacitydash.service.MotorcycleService;
import fiap.com.capacitydash.service.QrCodeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/moto/form")
public class FormMotoController {

    final MotorcycleService motorcycleService;
    final QrCodeService qrCode;

    @GetMapping
    public String showForm(Motorcycle motorcycle){
        return "form";
    }
    @PostMapping
    public String createFormMoto(@Valid Motorcycle motorcycle, BindingResult result, RedirectAttributes redirect, HttpServletRequest request){


        if(result.hasErrors()) {
            return "form";
        }

        if (motorcycleService.existsByPlate(motorcycle.getPlate())) {
            result.rejectValue("plate", "error.motorcycle", "Esta placa já está cadastrada.");
            return "form";
        }

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        Motorcycle motorcycleSave = motorcycleService.save(motorcycle);
        String motoUrl = baseUrl + "/dashboard/moto/" + motorcycleSave.getPlate();
        String qrCodeBase64 = qrCode.generateQRCode(motoUrl);
        redirect.addFlashAttribute("qrCodeBase64", qrCodeBase64);
        redirect.addFlashAttribute("message", "Motocicleta cadastrada com sucesso!");

        return "redirect:/dashboard";
    }

}
