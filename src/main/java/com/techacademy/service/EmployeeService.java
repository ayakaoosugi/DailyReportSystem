package com.techacademy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.Authentication;
import com.techacademy.entity.Employee;
import com.techacademy.repository.EmployeeRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository ;

    public EmployeeService(EmployeeRepository repository) {
        this.employeeRepository = repository;
    }

    /** 全件を検索して返す */
    public List<Employee> getEmployeeList() {
        // リポジトリのfindAllメソッドを呼び出す
        return employeeRepository.findAll();
    }
        
    /** employeeの登録を行なう */
    @Transactional
    public Employee saveEmployee(Employee employee) {
    	employee.setCreated_at(LocalDateTime.now());
    	employee.setUpdated_at(LocalDateTime.now());
    	employee.setDelete_flag(0);
    	Authentication authentication=employee.getAuthentication();
    	authentication.setEmployee(employee);
    	employee.setAuthentication(authentication);
    	
        return employeeRepository.save(employee);
    }
   
    /**Employeeを1件検索して返す */
	public Employee getEmployee(Integer id) {
		// TODO 自動生成されたメソッド・スタブ
		return employeeRepository.findById(id).get() ;
	}

	/** Userの削除を行なう */
	@Transactional
	public void deleteEmployee(Set<Integer> idck) {
		for (Integer id : idck) {
			employeeRepository.deleteById(id);
		}
	}
}
