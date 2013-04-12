package annotation;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.collections15.MultiMap;
import org.apache.commons.collections15.multimap.MultiHashMap;

/**
 * A helper class to load jars in a directory and get the classes based on
 * annotations.
 *
 * @author Ankit Gupta - ankit.gupta2801@gmail.com
 *
 */
public class AnnotatedClassLookup {

    private MultiMap<Annotation, Class<?>> pluginMap;
    private File classDirectory;

    public AnnotatedClassLookup(File directory) {
        this.classDirectory = directory;
        pluginMap = new MultiHashMap<Annotation, Class<?>>();
        loadPlugins();
    }

    /**
     * Load plugins from the plugin directory.
     *
     */
    private void loadPlugins() {
        if (classDirectory.isDirectory()) {

            // get the list of files contained in the plugins directory.
            File[] containedFiles = classDirectory.listFiles();

            for (File containedFile : containedFiles) {
                // if the file is a Jar, look up the jar for implementations.
                if (!containedFile.isDirectory() && containedFile.getAbsolutePath().endsWith(
                        ".jar")) {
                    pluginMap = getPluginMap(containedFile);
                }
            }
        } else {
            System.err.println("Plugin Directory does not exist");
        }
    }

    /**
     * Gets a mapping from annotation type to the corresponding classes.
     *
     * @param file the file to look for getting the mapping
     * @return a multimap containing mappings from an annotation to the
     * corresponding classes.
     */
    private MultiMap<Annotation, Class<?>> getPluginMap(File file) {
        MultiMap<Annotation, Class<?>> pluginMap =
                new MultiHashMap<Annotation, Class<?>>();

        try {
            URLClassLoader jarLoader = loadJar(file.getAbsoluteFile());

            JarFile jarFile = new JarFile(file.getAbsoluteFile());
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String className = jarEntry.toString().replace("/", ".");
                if (className.endsWith(".class")) {
                    className = className.replace(".class", "");
                    try {
                        Class<?> loadedClass = jarLoader.loadClass(className);
                        Annotation[] annotations = loadedClass.getAnnotations();
                        for (Annotation annotation : annotations) {
                            pluginMap.put(annotation, loadedClass);
                        }

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (MalformedURLException e) {
            System.err.println(file + " plugin jar could not be loaded");
        } catch (IOException e) {
            System.err.println("Loading jar file " + file + " failed.");
        }

        return pluginMap;
    }

    /**
     * Loads the jar and return the class loader used for loading.
     *
     * @param jarToBeLoaded the jar file to be loaded.
     * @return the {@link URLClassLoader} used to load the jar.
     * @throws IOException if the jar file could not be read.
     */
    private URLClassLoader loadJar(File jarToBeLoaded) throws IOException {
        URL jarfileURL =
                new URL("jar:file://" + jarToBeLoaded.getAbsolutePath() + "!/");
        URLClassLoader urlClassLoader =
                new URLClassLoader(new URL[]{jarfileURL});
        return urlClassLoader;
    }

    public MultiMap<Annotation, Class<?>> getPluginMap() {
        return pluginMap;
    }
}
