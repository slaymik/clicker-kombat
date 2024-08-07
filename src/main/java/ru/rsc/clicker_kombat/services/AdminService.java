package ru.rsc.clicker_kombat.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rsc.clicker_kombat.model.domain.Admin;
import ru.rsc.clicker_kombat.model.requests.AdminRequest;
import ru.rsc.clicker_kombat.model.responses.EntityResponse;
import ru.rsc.clicker_kombat.repository.AdminRepository;

import java.util.List;

import static ru.rsc.clicker_kombat.consts.EntityResponseStatuses.SUCCESS;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public EntityResponse createAdmin(AdminRequest request){
        Admin admin = new Admin(request.getUsername(), request.getPassword());
        return EntityResponse.builder()
                .status(SUCCESS)
                .entity(admin)
                .build();
    }
}