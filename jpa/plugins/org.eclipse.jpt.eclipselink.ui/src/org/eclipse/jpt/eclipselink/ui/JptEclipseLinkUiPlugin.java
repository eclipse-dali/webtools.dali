/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * The activator class controls the plug-in life cycle
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
@SuppressWarnings("nls")
public class JptEclipseLinkUiPlugin extends AbstractUIPlugin
{

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.jpt.eclipselink.ui";

	
	// ********** singleton **********
	private static JptEclipseLinkUiPlugin INSTANCE;

	/**
	 * Returns the singleton Plugin
	 */
	public static JptEclipseLinkUiPlugin instance() {
		return INSTANCE;
	}

	public static void log(IStatus status) {
        INSTANCE.getLog().log(status);
    }

	public static void log(String msg) {
        log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, msg, null));
    }

	public static void log(Throwable throwable) {
		log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, throwable.getLocalizedMessage(), throwable));
	}

	// ********** Image API **********
	/**
	 * This gets a .gif from the icons folder.
	 */
	public static ImageDescriptor getImageDescriptor(String key) {
		if (! key.startsWith("icons/")) {
			key = "icons/" + key;
		}
		if (! key.endsWith(".gif")) {
			key = key + ".gif";
		}
		return imageDescriptorFromPlugin(PLUGIN_ID, key);
	}

	/**
	 * This returns an image for a .gif from the icons folder
	 */
	//TODO we are using the ImageRegistry here and storing all our icons for the life of the plugin, 
	//which means until the workspace is closed.  This is better than before where we constantly 
	//created new images. Bug 306437 is about cleaning this up and using Local Resource Managers 
	//on our views so that closing the JPA perspective would mean our icons are disposed.
	public static Image getImage(String key) {
		ImageRegistry imageRegistry = instance().getImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null) {
			imageRegistry.put(key, getImageDescriptor(key));
			image = imageRegistry.get(key);
		}
		return image;
	}


	
	// ********** constructors **********
	public JptEclipseLinkUiPlugin() {
		super();
		if (INSTANCE != null) {
			throw new IllegalStateException();
		}
		INSTANCE = this;
	}
}
