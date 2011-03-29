package rasmantuta.testutil;

//   2011 Kristian Berg (RasmanTuta)
//
//   The License is; there is no License!

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EmbeddedDataSourceTest {
    private static final String DATABASE_SCHEMA_SQL = "testdata/DatabaseSchema.sql";
    private static final String DEFAULT_TEST_DATA_SQL = "testdata/DefaultTestData.sql";
    private static final String SIMPLE_TEST_DATA_SQL = "testdata/SimpleTestData.sql";
    private static final String CUSTOM_DATABASE_SCHEMA_SQL = "testdata/CustomDatabaseSchema.sql";

    @Rule
    public EmbeddedDataSource dataSource = new EmbeddedDataSource(DATABASE_SCHEMA_SQL, DEFAULT_TEST_DATA_SQL);

    @Test
    @EmbeddedDataSource.DataSet(SIMPLE_TEST_DATA_SQL)
    public void simpleTest() {
        assertEquals(SIMPLE_TEST_DATA_SQL, dataSource.getDataSet());
        assertEquals(DATABASE_SCHEMA_SQL, dataSource.getSchema());
    }

    @Test
    public void defaultTest() {
        assertEquals(DEFAULT_TEST_DATA_SQL, dataSource.getDataSet());
        assertEquals(DATABASE_SCHEMA_SQL, dataSource.getSchema());
    }

    @Test
    public void schemaTest() {
        assertEquals(DATABASE_SCHEMA_SQL, dataSource.getSchema());
        assertEquals(DEFAULT_TEST_DATA_SQL, dataSource.getDataSet());
    }

    @Test
    @EmbeddedDataSource.Schema(CUSTOM_DATABASE_SCHEMA_SQL)
    public void customSchemaTest() {
        assertEquals(DEFAULT_TEST_DATA_SQL, dataSource.getDataSet());
        assertEquals(CUSTOM_DATABASE_SCHEMA_SQL, dataSource.getSchema());
    }

}
