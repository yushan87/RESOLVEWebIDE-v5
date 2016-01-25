package controllers.analytics;

//  Play API Imports
import controllers.Assets.Asset;
import javax.inject.Inject;
import play.api.mvc.Action;
import play.api.mvc.AnyContent;

/**
 * TODO: Write a description of this module
 */
public class Assets {

	@Inject
	private controllers.Assets myAssets;
	
	/**
     * <p>This renders the main interface page for the WebIDE.</p>
     *
     * @return The result of rendering the page
     */
	public Action<AnyContent> versioned(String path, Asset file) {
		return myAssets.versioned(path, file);
	}
	
}