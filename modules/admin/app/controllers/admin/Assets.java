/**
 * ---------------------------------
 * Copyright (c) 2016
 * RESOLVE Software Research Group
 * School of Computing
 * Clemson University
 * All rights reserved.
 * ---------------------------------
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */
package controllers.admin;

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