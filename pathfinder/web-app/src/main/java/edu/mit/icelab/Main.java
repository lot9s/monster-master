package edu.mit.icelab;


/* Java includes */
import java.util.HashMap;
import java.util.Map;

/* FreeMarker includes */
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Version;

/* Spark includes */
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

/* ICE Lab includes */
import edu.mit.icelab.CreatureClassifier;


/** 
 * This class defines the behavior for a web app that classifies creatures from the Pathfinder RPG
 * by CR. 
 */
public class Main {
	/** The driver for this web server. */
	public static void main(String[] args) {
		// create an object for classifying monsters from the Pathfinder RPG
		CreatureClassifier classifier = new CreatureClassifier();
		
		// --- set up HTML template engine ---
		FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
		Configuration freeMarkerConfiguration = new Configuration(new Version(2,3,0));
		freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(Main.class, "/"));
		freeMarkerEngine.setConfiguration(freeMarkerConfiguration);
		
		
		// --- set up web server's routes and behavior ---
		// serve static files
		String USER_HOME =  System.getProperty("user.home");
		System.setProperty("mm.res", USER_HOME + "/Desktop/monster-master/public");
		staticFiles.externalLocation(System.getProperty("mm.res"));;
		
		// handles requests from users to classify Pathfinder creatures
		get("/classify/*", (req, res) -> {
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("cr", classifier.label(req.splat()[0]));
			return freeMarkerEngine.render(new ModelAndView(attributes, "/resources/classify.ftl"));
		});
	}
}
