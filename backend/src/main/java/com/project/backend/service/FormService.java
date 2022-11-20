package com.project.backend.service;

import com.project.backend.dao.FormDao;
import com.project.backend.dao.RoleDao;
import com.project.backend.dao.UserDao;
import com.project.backend.entity.Form;
import com.project.backend.entity.Role;
import com.project.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FormService {
    @Autowired
    private FormDao formDao;

    public Form addNewForm(Form form) {
        return formDao.save(form);
    }


    public List<Form> getForms() {
        return (List<Form>) formDao.findAll();
    }

    public void deleteForm(Integer Id) {
        Form form = formDao.findById(Id).get();
        formDao.delete(form);
    }

    public Form updateForm(Integer Id, Form formDetails) {
        Form form = formDao.findById(Id).get();
        form.setFirstField(formDetails.getFirstField());
        form.setSecondField(formDetails.getSecondField());
        form.setThirdField(formDetails.getThirdField());
        return formDao.save(form);
    }
}
