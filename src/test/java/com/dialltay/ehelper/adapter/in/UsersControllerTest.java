package com.dialltay.ehelper.adapter.in;

import com.dialltay.ehelper.adapter.in.web.UserController;
import com.dialltay.ehelper.application.domain.service.CreateUserService;
import com.dialltay.ehelper.application.port.in.GetUserSliceUseCase;
import com.dialltay.ehelper.config.AssetConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(UserController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateUserService createUserService;

    @MockitoBean
    private GetUserSliceUseCase getUserSliceUseCase;

    @Test
    void shouldCreateUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users"))
            .andExpect(result -> Assertions.assertEquals(200, result.getResponse().getStatus()));
    }

    @TestConfiguration
    static class configPlus {

        @Bean
        public AssetConfig getAssetConfig() {
            return new AssetConfig();
        }
    }
}
