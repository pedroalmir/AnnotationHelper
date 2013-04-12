package annotation.samples.plugin;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Collection;

import org.apache.commons.collections15.MultiMap;

import annotation.AnnotatedClassLookup;
import annotation.plugin.PluginLoader;
import annotation.plugin.PluginRule;

/**
 *
 * @author Ankit Gupta - ankit.gupta2801@gmail.com
 */
public class Main {
    
    public static void main(String[] args){
    	AnnotatedClassLookup acl = new AnnotatedClassLookup(new File("data/plugins"));
        MultiMap<Annotation, Class<?>> classMap = acl.getPluginMap();
        
        PluginLoader pluginLoader = new PluginLoader(new MyEventRule());
        pluginLoader.scanClasses(classMap.values());
        
        MultiMap<PluginRule, Class<?>> pluginMap = pluginLoader.getPluginMap();
        
        for(PluginRule rule : pluginMap.keySet()){
            System.out.println("\n\nClasses for rule " + rule.getClass().toString());
            Collection<Class<?>> classesForPlugin = pluginMap.get(rule);
            for(Class<?> c : classesForPlugin){
                System.out.println(c);
            }
        }
        
    }
    
}
