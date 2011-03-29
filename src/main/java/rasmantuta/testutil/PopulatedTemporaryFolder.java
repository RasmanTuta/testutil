package rasmantuta.testutil;

//   2011 Kristian Berg (RasmanTuta)
//
//   The License is; there is no License!

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TemporaryFolder;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;


public class PopulatedTemporaryFolder implements MethodRule {

    public TemporaryFolder tempFolder = new CallbackTemporaryFolder();
    private final String[] resources;
    private FrameworkMethod method = null;

    protected void before() throws IOException {
        final String[] actualResources = createActualResources();

        for (String resource : actualResources) {
            final InputStream stream = getClass().getResourceAsStream(resource);
            final File file = newFileWithFolders(resource);
            FileUtils.copyInputStreamToFile(stream, file);
        }
    }

    private String[] createActualResources() {
        // Get annotated Resources
        AdditionalResources additionalResources = method.getAnnotation(AdditionalResources.class);
        OverrideResources overrideResources = method.getAnnotation(OverrideResources.class);
        Assert.isTrue((null == additionalResources || null == overrideResources), "Both @AdditionalResources and @OverrideResources can not be used on same method.");
        String[] res ;
        if(null!=additionalResources){
            res = (String[]) ArrayUtils.addAll(resources, additionalResources.value());
        }else if(null!=overrideResources){
            res = overrideResources.value();
        }else{
            res = resources;
        }
        return res;
    }


    public File newFile(String fileName) throws IOException {
        return tempFolder.newFile(fileName);
    }

    public File newFolder(String folderName) {
        return tempFolder.newFolder(folderName);
    }

    public File getRoot() {
        return tempFolder.getRoot();
    }

    public void delete() {
        tempFolder.delete();
    }

    public void create() throws IOException {
        tempFolder.create();
    }

    public void mkdirs(String fileName) {
        final String[] strings = fileName.split("/");
        String dir = "";
        for (String string : Arrays.copyOf(strings, strings.length-1)) {
            if (!"".equals(string)) {
                dir += "/" + string;
                if(!dir.equals(fileName))
                    tempFolder.newFolder(dir);
            }
        }
    }

    public File newFileWithFolders(String fileName) throws IOException {
        mkdirs(fileName);
        return newFile(fileName);
    }

    public File newFolderWithParents(String fileName) throws IOException {
        mkdirs(fileName);
        return newFolder(fileName);
    }

    public PopulatedTemporaryFolder() {
        resources = new String[0];
    }

    public PopulatedTemporaryFolder(String[] resources) {
        Assert.notNull(resources, "resources can not be null.");
        Assert.noNullElements(resources, "resources can not contain null elements.");
        this.resources = resources;
    }

    @Override
    public final Statement apply(final Statement base, FrameworkMethod method, Object target) {
        this.method = method;
        return tempFolder.apply(base, method, target);
    }

    /**
     * Use this annotation on a test method to specify an additional set of resources for a single test.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface AdditionalResources {
        String[] value();
    }

    /**
     * Use this annotation on a test method to specify a different set of resources for a single test.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface OverrideResources {
        String[] value();
    }

    private class CallbackTemporaryFolder extends TemporaryFolder {
        @Override
        protected void before() throws Throwable {
            super.before();
            PopulatedTemporaryFolder.this.before();
        }
    }
}
