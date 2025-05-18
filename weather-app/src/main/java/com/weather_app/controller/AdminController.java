package com.weather_app.controller;


// import com.weather_app.payload.LoginPayload;
import com.weather_app.model.Admin;
import com.weather_app.payload.LoginPayload;
import com.weather_app.service.AdminService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
public class AdminController extends BasicControllerOperations<AdminService, Admin> {
    public AdminController(AdminService service) {
        super(service);
    }

    @PostMapping("/login")
    public Admin login(@RequestBody @Validated LoginPayload login) {
        return this.service.login(login.getEmail(), login.getPassword());
    }
}
