package annotation.plugin;

/**
 * An interface to check if a class is a valid plugin according to certain rules.
 * @author Ankit Gupta - ankit.gupta2801@gmail.com
 */
public interface PluginRule {
    
    public boolean verify(Class<?> clazz);
    
}
