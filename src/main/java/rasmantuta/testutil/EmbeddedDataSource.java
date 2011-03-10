package rasmantuta.testutil;

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

import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The EmbeddedDataSource Rule makes a Spring EmbeddedDatabase available for test methods:
 *
 * <pre>
 * public class UserDaoTest{
 *     UserDao dao = null;
 *     &#064Rule
 *     public EmbeddedDataSource embeddedDataSource = new EmbeddedDataSource("testdata/DatabaseSchema.sql", "testdata/DefaultTestData.sql");
 *
 *     &#064Before
 *     public void setUp() {
 *         dao = new UserDao(embeddedDataSource.getDataSource());
 *     }
 *
 *     &#064Test
 *     public void testIsInRole() throws Exception {
 *         assertTrue(dao.isInRole("admin", "administrator"));
 *         assertTrue(dao.isInRole("admin", "everyone"));
 *     }
 *
 *     &#064Test
 *     &#064DataSet("testdata/findUsersInRoleBugTestData.sql")
 *     public void findUsersInRoleBug() throws Exception {
 *         assertFalse(dao.isInRole("guest", "admin"));
 *         assertTrue(dao.isInRole("guest", "everyone"));
 *     }
 * }
 * </pre>
 *
 * For the EmbeddedDataSource to be available in &#064Before annotated method, junit version 4.8 or later must be used.
 *
 * To use a custom Schema or DataSet, annotate the test method with &#064Schema or &#064DataSet.
 */

public class EmbeddedDataSource extends TestWatchman {
    private String schema;
    private String dataSet;
    private EmbeddedDatabase dataSource = null;
    

    public EmbeddedDataSource(String schema, String dataSet){
        this.schema = schema;
        this.dataSet = dataSet;
    }

    @Override
    public void starting(FrameworkMethod method) {
        // Get annotated Schema or DataSet if provided.
        DataSet providedDataSet = method.getAnnotation(DataSet.class);
        Schema providedSchema = method.getAnnotation(Schema.class);
        // Decide what Schema or DataSet to use
        dataSet = null == providedDataSet ? dataSet : providedDataSet.value();
        schema = null == providedSchema ? schema : providedSchema.value();

        dataSource = new EmbeddedDatabaseBuilder().addScript(schema).addScript(dataSet).build();
    }

    @Override
    public void finished(FrameworkMethod method) {
        dataSource.shutdown();
    }

    /**
     * Returns the schema configured with this instance of EmbeddedDataSource.
     *
     * Could be the scheme set in the constructor or by the @Schema annotation.
     * @return the Schema
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Returns the dataSet configured with this instance of EmbeddedDataSource.
     *
     * Could be the dataSet set in the constructor or by the @DataSet annotation.
     * @return the DataSet
     */
    public String getDataSet() {
        return dataSet;
    }

    /**
     * The EmbeddedDatabase configured with the Schema and DataSet provided in constructor or annotated.
     *
     * @return the EmbeddedDatabase
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Use this annotation on a test method to specify a different database schema for a single test.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface Schema {
        String value();
    }

    /**
     * Use this annotation on a test method to specify a different data set for a single test.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface DataSet {
        String value();
    }
}
