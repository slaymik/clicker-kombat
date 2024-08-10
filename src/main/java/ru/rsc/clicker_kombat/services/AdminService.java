package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.model.domain.Admin;
import ru.rsc.clicker_kombat.model.requests.AdminRequest;
import ru.rsc.clicker_kombat.model.responses.ActionResult;
import ru.rsc.clicker_kombat.repository.AdminRepository;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final JdbcUserDetailsManager manager;
    private final PasswordEncoder encoder;

    public List<String> getAllAdmins() {
        return adminRepository.findAll().stream().map(Admin::getUsername).toList();
    }

    public ActionResult createAdmin(AdminRequest request) {
        if (request.getUsername().isEmpty() || request.getPassword().isEmpty()) {
            return new ActionResult(false, "Переданы не все данные");
        } else {
            manager.createUser(User.builder()
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .passwordEncoder(encoder::encode)
                    .roles("ADMIN")
                    .build());
            return new ActionResult(true, "Админ успешно создан");
        }
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