package ui.database;

import static org.junit.jupiter.api.Assertions.assertEquals;


import ui.database.DatabaseTalkerUi;
import org.junit.jupiter.api.Test;


public class CSVDatabaseTalkerTest {
    DatabaseTalkerUi databaseTalker = new CSVDatabaseTalker("src/main/resources/ui/testUsers.csv");

    @Test
    public void userExistsTest(){
        assertEquals(databaseTalker.userExists("user1"), true);
        assertEquals(databaseTalker.userExists("user2"), true);
        assertEquals(databaseTalker.userExists("NOTAUSER"), false);
    }



    @Test
    public void checkPasswordTest(){
        assertEquals(databaseTalker.checkPassword("user1", "password1"), true);
        assertEquals(databaseTalker.checkPassword("user1", "password2"), false);
        assertEquals(databaseTalker.checkPassword("user2", "password1"), false);
        assertEquals(databaseTalker.checkPassword("user2", "password2"), true);
        assertEquals(databaseTalker.checkPassword("NOTAUSER", "password1"), false);
    }


    @Test
    public void insertUserTest(){
        assertEquals(false, databaseTalker.userExists("user3"));
        assertEquals(true, databaseTalker.insertUser("user3", "password3"));
        assertEquals(true, databaseTalker.userExists("user3"));
        assertEquals(false, databaseTalker.insertUser("user3", "password3"));
        databaseTalker.deleteUser("user3");
    }
}
