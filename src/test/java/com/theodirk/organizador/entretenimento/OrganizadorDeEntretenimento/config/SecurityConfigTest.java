package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

        @Autowired
        private MockMvc mockMvc;


    @Test
        public void whenPublicEndpoint_thenNoAuthenticationRequired() throws Exception {
            mockMvc.perform(get("/swagger-ui.html"))
                    .andExpect(MockMvcResultMatchers.status().isFound());
        }

        @Test
        public void whenProtectedEndpoint_thenAuthenticationRequired() throws Exception {
            mockMvc.perform(get("/api/roles/grupos"))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized());
        }

        @Test
        @WithMockUser
        public void whenAuthenticated_thenCanAccessProtectedEndpoint() throws Exception {

            mockMvc.perform(get("/api/entretenimento"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
}

