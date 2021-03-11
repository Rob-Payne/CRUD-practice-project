package com.example.CRUDpracticeproject.controller;

import com.example.CRUDpracticeproject.dao.EmployeeRepository;
import com.example.CRUDpracticeproject.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    @GetMapping("/employees")
    public Iterable<Employee> listEmployees() {
        return this.employeeRepository.findAll();
    }

    @GetMapping("/employees/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable Long id){
        return this.employeeRepository.findById(id);
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee newEmployee){
        return this.employeeRepository.save(newEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id){
        this.employeeRepository.deleteById(id);
    }

    @PatchMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee newRecord){

        //Find Record
        Employee oldRecord = employeeRepository.findById(id).get();

        //Update record
        if(newRecord.getName() != null) oldRecord.setName(newRecord.getName());

        if(newRecord.getStartDate() != null) oldRecord.setStartDate(newRecord.getStartDate());

        //Save record
        return this.employeeRepository.save(oldRecord);
    }

}
