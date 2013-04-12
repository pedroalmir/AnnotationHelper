package annotation.samples;

import annotation.AnnotatedClassLookup;
import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Set;
import org.apache.commons.collections15.MultiMap;

/**
 *
 * @author Ankit Gupta - ankit.gupta2801@gmail.com
 */
public class AnnotationBasedLoader {

    public static void main(String[] args) {
        AnnotatedClassLookup lookup = new AnnotatedClassLookup(new File("lib"));
        MultiMap<Annotation, Class<?>> pluginMap = lookup.getPluginMap();
        Set<Annotation> keySet = pluginMap.keySet();

        if (keySet != null) {
            for (Annotation anno : keySet) {
                System.out.println("Classes annotated with annotation " + anno);
                Collection<Class<?>> classes = pluginMap.get(anno);
                for (Class c : classes) {
                    if (!c.isInterface()) {
                        System.err.println(c);
                    }
                }
            }
        }
    }
}
