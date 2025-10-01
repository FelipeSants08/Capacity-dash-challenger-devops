package fiap.com.capacitydash.service;

import fiap.com.capacitydash.model.Alert;
import fiap.com.capacitydash.model.Department;
import fiap.com.capacitydash.model.LevelAlert;
import fiap.com.capacitydash.model.ParkingSpace;
import fiap.com.capacitydash.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
    private final DepartamentService departamentService;


    public List<Alert> getAllAlertsActive(){
        return alertRepository.findAll().stream().filter(a -> a.getActive() == true).toList();
    }

    public List<Alert> getAllAlerts(){
        return alertRepository.findAll();
    }

    public void gerarAlertCritico(Department department){
        desativaAlert(department);
        Alert alert = new Alert();
        alert.setLevelAlert(LevelAlert.CRITICAL);
        alert.setDepartment(department);
        alert.setActive(true);
        alert.setDateTimeAlert(LocalDateTime.now());
        alert.setMessage("Departamento Critico, 95% de ocupação");
        alertRepository.save(alert);
    }


    public void gerarAlertCuidado(Department department){
        desativaAlert(department);
        Alert alert = new Alert();
        alert.setLevelAlert(LevelAlert.WARNING);
        alert.setDepartment(department);
        alert.setActive(true);
        alert.setDateTimeAlert(LocalDateTime.now());
        alert.setMessage("Cuidado, Departamento com 80% da lotação");
        alertRepository.save(alert);
    }

    public void gerarAlertInformacao(Department department){
        desativaAlert(department);
        Alert alert = new Alert();
        alert.setLevelAlert(LevelAlert.INFO);
        alert.setDepartment(department);
        alert.setActive(true);
        alert.setDateTimeAlert(LocalDateTime.now());
        alert.setMessage("Info, departamento com 50% da capacidade");
        alertRepository.save(alert);
    }


    public void gerarAlertaAutomatico(Long idDepartament){
        Department department = departamentService.findDepartmentById(idDepartament);
        double percentual = gerarPorcentagem(department);
        if (percentual >= 0.95){
            gerarAlertCritico(department);
        } else if (percentual >= 0.80){
            gerarAlertCuidado(department);
        } else if (percentual > 0.50) {
            gerarAlertInformacao(department);
        } else {
            desativaAlert(department);
        }
    }

    public double gerarPorcentagem(Department department){
        int totalDeVagas = department.getParkingSpaces().size();
        int totalDeVagasOcupadas = department.getParkingSpaces().stream().filter(ParkingSpace::getOccupied).toList().size();
        return (double) totalDeVagasOcupadas /  totalDeVagas;
    }

    public void desativaAlert(Department department){
        department.getAlerts().forEach(alert -> alert.setActive(false));
        alertRepository.saveAll(department.getAlerts());
    }

}
