package controllers.common;

//  Play API Imports
import play.api.mvc.*;

/**
 * TODO: Write a description of this module
 */
public class Assets {
	
	/**
     * <p>This renders the main interface page for the WebIDE.</p>
     *
     * @return The result of rendering the page
     */
	public static Action<AnyContent> at(String path, String file) {
		return controllers.Assets.at(path, file, false);
	}
	
}