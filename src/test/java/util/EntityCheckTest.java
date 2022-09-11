package util;

import entity.User;
import exception.EntityExistsException;
import exception.EntityNotExistsException;
import exception.PasswordException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityCheckTest {

    @Test
    void ifAlreadyExists() {
        assertThrows(EntityExistsException.class, () -> EntityCheck.ifAlreadyExists(new User(13)));
    }

    @Test
    void ifNotExists() {
        assertThrows(EntityNotExistsException.class, () -> EntityCheck.ifNotExists(null));
    }

    @Test
    void ifPasswordsSame() {
        assertThrows(PasswordException.class, () -> EntityCheck.ifPasswordsSame(null, "firsT"));
        assertThrows(PasswordException.class, () -> EntityCheck.ifPasswordsSame("first", "firsT"));
        assertThrows(PasswordException.class, () -> EntityCheck.ifPasswordsSame("password", "pasSword"));
    }
}