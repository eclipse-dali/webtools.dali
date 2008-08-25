/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 *
 *
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
@SuppressWarnings("nls")
public class JptUiPlugin extends AbstractUIPlugin
{
	private static JptUiPlugin INSTANCE;

	/**
	 * The plug-in identifier of JPA UI support
	 * (value <code>"org.eclipse.jpt.ui"</code>).
	 */
	public final static String PLUGIN_ID = "org.eclipse.jpt.ui";  //$NON-NLS-1$

	/**
	 * Returns the singleton Plugin
	 */
	public static JptUiPlugin getPlugin() {
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


	// **************** Image API **********************************************

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
	public static Image getImage(String key) {
		ImageDescriptor desc = getImageDescriptor(key);
		return (desc == null) ? null : desc.createImage();
	}


	// **************** Construction *******************************************

	public JptUiPlugin() {
		super();
		INSTANCE = this;
	}


	/**
	 * Return the JPA platform UI corresponding to the given JPA platform
	 */
	//TODO rename this to getJpaPlatformUi
	public JpaPlatformUi jpaPlatformUi(JpaPlatform jpaPlatform) {
		return JpaPlatformUiRegistry.instance().getJpaPlatformUi(jpaPlatform.getId());
	}
}
