package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.model.domain.Admin;
import ru.rsc.clicker_kombat.model.requests.AdminRequest;
import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.repository.AdminRepository;

import java.util.List;
import java.util.Optional;

import static ru.rsc.clicker_kombat.consts.EntityResponseConstsAndFactory.getEntityResponseSuccess;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public EntityResponse createAdmin(AdminRequest request) {
        Admin admin = new Admin(request.getUsername(), request.getPassword());
        return getEntityResponseSuccess(admin);
    }

    public ActionResult deleteAdmin(String username) {
        Optional<Admin> admin = adminRepository.findAdminByUsername(username);
        if (admin.isPresent()) {
            adminRepository.delete(admin.get());
            return new ActionResult(true, "Админ с ником %s был удален".formatted(username));
        } else
            return new ActionResult(false, "Админ с ником %s не был найден".formatted(username));
    }
}