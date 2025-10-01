package fiap.com.capacitydash.service;

import fiap.com.capacitydash.model.Department;
import fiap.com.capacitydash.repository.DepartamentRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentService {


    private final DepartamentRepository departamentRepository;


    public Department create(Department department){
        return departamentRepository.save(department);
    }

    public List<Department> findAllDepartments(){
        return departamentRepository.findAll();
    }


    public Department findDepartmentById(Long id){
        return departamentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("ID não encontrado!")
        );
    }

}
