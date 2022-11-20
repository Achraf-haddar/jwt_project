package com.project.backend.controller;

import com.project.backend.entity.Form;
import com.project.backend.entity.User;
import com.project.backend.service.FormService;
import com.project.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FormController {
    @Autowired
    private FormService formService;

    @PostMapping({"/addNewForm"})
    @PreAuthorize("hasRole('Admin')")
    public Form addNewForm(@RequestBody Form form) {
        return formService.addNewForm(form);
    }

    @GetMapping({"/getForms"})
    @PreAuthorize("hasAnyRole('UserEdit', 'UserDelete')")
    public List<Form> getForms() { return formService.getForms(); }

    @DeleteMapping({"/deleteForm/{Id}"})
    @PreAuthorize("hasRole('UserDelete')")
    public void deleteForm(@PathVariable(value="Id") Integer Id) { formService.deleteForm(Id); }

    @PutMapping({"/updateForm/{Id}"})
    @PreAuthorize("hasRole('UserEdit')")
    public Form updateForm(@PathVariable(value="Id") Integer Id, @RequestBody Form formDetails) { return formService.updateForm(Id, formDetails); }

}
