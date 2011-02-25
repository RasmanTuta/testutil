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

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import javax.sql.DataSource;

public class UserDao extends SimpleJdbcDaoSupport {
    public UserDao(DataSource dataSource){
        setDataSource(dataSource);
    }

    public boolean isInRole(String userName, String roleName){
        boolean retval;
        try {
            getJdbcTemplate().queryForInt(
                    "select ROLE_ID from ROLES a INNER JOIN USER_ROLES b ON a.ROLE_ID=b.ROLE_ID_FK INNER JOIN USERS c ON b.USER_ID_FK=c.USER_ID" +
                            " WHERE a.ROLE_NAME=? AND c.USER_NAME=?", roleName, userName
            );
            retval = true;
        } catch (DataAccessException e) {
            retval = false;
        }

        return retval;
    }
}
