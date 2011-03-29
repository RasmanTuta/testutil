package rasmantuta.testutil;

//   2011 Kristian Berg (RasmanTuta)
//
//   The License is; there is no License!

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class PopulatedTemporaryFolderTest {
    @Rule
    public PopulatedTemporaryFolder tempFolder = new PopulatedTemporaryFolder(new String[]{PTFDATA_DEFAULT_TEST_FILE_TXT});
    private static final String PTFDATA_DEFAULT_TEST_FILE_TXT = "/PTFData/defaultTestFile.txt";
    private static final String PTFDATA_NON_DEFAULT_TEST_FILE_TXT = "/PTFData/nonDefaultTestFile.txt";

    @Before
    public void setUp(){
        assertRootExists();
    }

    @Test
    public void testTempFolder() throws Exception{
        assertRootExists();
    }

    private void assertRootExists() {
        File root = tempFolder.getRoot();
        assertTrue(root.exists());
    }

    @Test
    public void testDefaultTestFile() throws Exception{
        final File file = new File(tempFolder.getRoot(), PTFDATA_DEFAULT_TEST_FILE_TXT);
        assertTrue(file.exists());
        assertFileContains(file, PTFDATA_DEFAULT_TEST_FILE_TXT);
        assertFalse(new File(tempFolder.getRoot(), PTFDATA_NON_DEFAULT_TEST_FILE_TXT).exists());
    }

    @Test
    @PopulatedTemporaryFolder.OverrideResources(value = {PTFDATA_NON_DEFAULT_TEST_FILE_TXT})
    public void testOverrideResources() throws Exception{
        final File file = new File(tempFolder.getRoot(), PTFDATA_NON_DEFAULT_TEST_FILE_TXT);
        assertTrue(file.exists());
        assertFileContains(file, PTFDATA_NON_DEFAULT_TEST_FILE_TXT);
        assertFalse(new File(tempFolder.getRoot(), PTFDATA_DEFAULT_TEST_FILE_TXT).exists());
    }

    @Test
    @PopulatedTemporaryFolder.AdditionalResources(value = {PTFDATA_NON_DEFAULT_TEST_FILE_TXT})
    public void testNonDefaultTestFile() throws Exception{
        final File file = new File(tempFolder.getRoot(), PTFDATA_DEFAULT_TEST_FILE_TXT);
        assertTrue(file.exists());
        assertFileContains(file, PTFDATA_DEFAULT_TEST_FILE_TXT);
        final File file2 = new File(tempFolder.getRoot(), PTFDATA_NON_DEFAULT_TEST_FILE_TXT);
        assertTrue(file2.exists());
        assertFileContains(file2, PTFDATA_NON_DEFAULT_TEST_FILE_TXT);
     }

    public void assertFileContains(File file, String expectedContent) throws Exception{
        String actualContent = FileUtils.readFileToString(file).trim();

        assertEquals(expectedContent, actualContent);
    }

 }
