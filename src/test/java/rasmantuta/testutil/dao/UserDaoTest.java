package rasmantuta.testutil.dao;

//   Copyright 2011 Kristian Berg (RasmanTuta)
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import rasmantuta.testutil.DataSet;
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
    @DataSet("testdata/findUsersInRoleBugTestData.sql")
    public void findUsersInRoleBug() throws Exception {
        assertFalse(dao.isInRole("guest", "admin"));
        assertTrue(dao.isInRole("guest", "everyone"));
    }

}
