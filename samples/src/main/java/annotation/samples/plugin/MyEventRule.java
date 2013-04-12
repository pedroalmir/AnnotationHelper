package annotation.samples.plugin;

import annotation.plugin.PluginRule;

/**
 * A rule to check if there the given class is annotated with MyEvent
 * annotation.
 *
 * @author Ankit Gupta - ankit.gupta2801@gmail.com
 */
public class MyEventRule implements PluginRule {

    @Override
    public boolean verify(Class<?> clazz) {
        if (clazz.getAnnotation(MyEvent.class) != null) {
            return true;
        }
        return false;
    }
}
