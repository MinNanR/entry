import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.minnan.entry.EntryApplication;
import site.minnan.entry.application.service.UserService;
import site.minnan.entry.userinterface.dto.user.AddUserDTO;

@SpringBootTest(classes = EntryApplication.class)
public class TestAddUser {

    @Autowired
    private UserService userService;

    @Test
    public void addUser() {
        AddUserDTO dto = new AddUserDTO();
        dto.setUsername("min");
        dto.setRealName("民难");
        dto.setRole("ADMIN");
        userService.addUser(dto);
    }
}
