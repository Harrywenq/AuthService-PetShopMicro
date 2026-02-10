package com.huytpq.auth_service.feign;

import com.huytpq.auth_service.dto.input.LoginInput;
import com.huytpq.auth_service.dto.output.UserOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserClient {

    @PostMapping("/api/users/internal/validate-login")
    UserOutput validateLogin(@RequestBody LoginInput input);
}
