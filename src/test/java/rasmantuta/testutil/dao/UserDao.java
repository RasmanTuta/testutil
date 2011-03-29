package rasmantuta.testutil.dao;

//   2011 Kristian Berg (RasmanTuta)
//
//   The License is; there is no License!

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
