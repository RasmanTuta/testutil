package rasmantuta.testutil.dao;

//   2011 Kristian Berg (RasmanTuta)
//
//   The License is; there is no License!

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import rasmantuta.testutil.EmbeddedDataSource;

public class UserDaoTest{
    UserDao dao = null;
    @Rule
    public EmbeddedDataSource embeddedDataSource = new EmbeddedDataSource("testdata/DatabaseSchema.sql", "testdata/DefaultTestData.sql");

    @Before
    public void setUp() {
        dao = new UserDao(embeddedDataSource.getDataSource());
    }

    @Test
    public void testIsInRole() throws Exception {
        assertTrue(dao.isInRole("admin", "administrator"));
        assertTrue(dao.isInRole("admin", "everyone"));
    }


    @Test
    @EmbeddedDataSource.DataSet("testdata/findUsersInRoleBugTestData.sql")
    public void findUsersInRoleBug() throws Exception {
        assertFalse(dao.isInRole("guest", "admin"));
        assertTrue(dao.isInRole("guest", "everyone"));
    }

}
