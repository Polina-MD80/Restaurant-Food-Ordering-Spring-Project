package softuni.restaurant.web.customers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class ContactsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void contactPageOpens() throws Exception {
        mockMvc
                .perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(view().name("contacts"));
    }
}