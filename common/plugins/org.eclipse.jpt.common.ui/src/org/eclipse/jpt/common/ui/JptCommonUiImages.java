/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.internal.plugin.JptCommonUiPlugin;
import org.eclipse.swt.SWT;

/**
 * Actually, just the image descriptors....
 * <p>
 * Code should use these constants to acquire (and release) the images provided
 * by Dali. The images should be managed by a
 * {@link org.eclipse.jface.resource.ResourceManager "local" resource manager}.
 * <p>
 * Also, the {@link org.eclipse.ui.IWorkbench Eclipse workbench} supplies more
 * general purpose {@link org.eclipse.ui.ISharedImages images}.
 * 
 * @see org.eclipse.jface.resource.ResourceManager#createImage(ImageDescriptor)
 * @see org.eclipse.jface.resource.ResourceManager#createImageWithDefault(ImageDescriptor)
 * @see org.eclipse.jface.resource.ResourceManager#destroyImage(ImageDescriptor)
 * @see org.eclipse.core.runtime.FileLocator#find(org.osgi.framework.Bundle, org.eclipse.core.runtime.IPath, java.util.Map)
 * @see org.eclipse.ui.IWorkbench#getSharedImages()
 * @see org.eclipse.ui.ISharedImages
 */
@SuppressWarnings("nls")
public final class JptCommonUiImages {

	// ********** Dali standard directories **********

	/**
	 * @see org.eclipse.core.runtime.FileLocator#find(org.osgi.framework.Bundle, org.eclipse.core.runtime.IPath, java.util.Map)
	 */
	public static final String LOCALE = "$nl$";
	public static final String LOCALE_ = LOCALE + '/';

	public static final String IMAGES = LOCALE_ + "images";
	public static final String IMAGES_ = IMAGES + '/';

	public static final String BUTTONS = IMAGES_ + "buttons";
	public static final String BUTTONS_ = BUTTONS + '/';

	public static final String OBJECTS = IMAGES_ + "objects";
	public static final String OBJECTS_ = OBJECTS + '/';

	public static final String OVERLAYS = IMAGES_ + "overlays";
	public static final String OVERLAYS_ = OVERLAYS + '/';

	public static final String VIEWS = IMAGES_ + "views";
	public static final String VIEWS_ = VIEWS + '/';

	public static final String WIZARDS = IMAGES_ + "wizards";
	public static final String WIZARDS_ = WIZARDS + '/';


	// ********** buttons **********

	public static final ImageDescriptor DOT_BUTTON = buildImageDescriptor(BUTTONS_ + "dot.gif");
	public static final ImageDescriptor ADD_BUTTON = buildImageDescriptor(BUTTONS_ + "add.png");
	public static final ImageDescriptor EDIT_BUTTON = buildImageDescriptor(BUTTONS_ + "edit.png");
	public static final ImageDescriptor DELETE_BUTTON = buildImageDescriptor(BUTTONS_ + "delete.png");
	public static final ImageDescriptor MOVE_UP_BUTTON = buildImageDescriptor(BUTTONS_ + "move-up.png");
	public static final ImageDescriptor MOVE_DOWN_BUTTON = buildImageDescriptor(BUTTONS_ + "move-down.png");
	public static final ImageDescriptor EXPAND_ALL_BUTTON = buildImageDescriptor(BUTTONS_ + "expand-all.png");
	public static final ImageDescriptor COLLAPSE_ALL_BUTTON = buildImageDescriptor(BUTTONS_ + "collapse-all.png");
	public static final ImageDescriptor RESTORE_DEFAULTS_BUTTON = buildImageDescriptor(BUTTONS_ + "restore-defaults.png");
	public static final ImageDescriptor BROWSE_BUTTON = buildImageDescriptor(BUTTONS_ + "browse.png");
	public static final ImageDescriptor MINI_BROWSE_BUTTON = buildImageDescriptor(BUTTONS_ + "browse-mini.png");
	public static final ImageDescriptor SELECT_ALL_BUTTON = buildImageDescriptor(BUTTONS_ + "select-all.png");
	public static final ImageDescriptor DESELECT_ALL_BUTTON = buildImageDescriptor(BUTTONS_ + "deselect-all.png");
	public static final ImageDescriptor REFRESH_BUTTON = buildImageDescriptor(BUTTONS_ + "refresh.gif");
	public static final ImageDescriptor ADD_CONNECTION_BUTTON = buildImageDescriptor(BUTTONS_ + "add-connection.gif");
	public static final ImageDescriptor RECONNECT_BUTTON = buildImageDescriptor(BUTTONS_ + "reconnect.png");


	// ********** objects **********

	public static final ImageDescriptor FILE = buildImageDescriptor(OBJECTS_ + "file.png");
	public static final ImageDescriptor FOLDER = buildImageDescriptor(OBJECTS_ + "folder.png");
	public static final ImageDescriptor PACKAGE = buildImageDescriptor(OBJECTS_ + "package.png");
	public static final ImageDescriptor WARNING = buildImageDescriptor(OBJECTS_ + "warning.gif");


	// ********** overlays **********

	public static final ImageDescriptor ERROR_OVERLAY = buildImageDescriptor(OVERLAYS_ + "error.gif");
	public static final ImageDescriptor WARNING_OVERLAY = buildImageDescriptor(OVERLAYS_ + "warning.png");


	// ********** misc **********

	/**
	 * If the specified flag is <code>true</code>, return an image descriptor
	 * corresponding to the specified descriptor "grayed" out; otherwise return
	 * the specified descriptor unchanged.
	 * @see SWT#IMAGE_GRAY
	 */
	public static ImageDescriptor gray(ImageDescriptor descriptor, boolean gray) {
		return gray ? gray(descriptor) : descriptor;
	}

	/**
	 * Return an image descriptor corresponding to the specified image
	 * descriptor "grayed" out.
	 * @see org.eclipse.swt.graphics.Image#Image(org.eclipse.swt.graphics.Device, org.eclipse.swt.graphics.Image, int)
	 * @see SWT#IMAGE_GRAY
	 */
	public static ImageDescriptor gray(ImageDescriptor descriptor) {
		return ImageDescriptor.createWithFlags(descriptor, SWT.IMAGE_GRAY);
	}

	private static ImageDescriptor buildImageDescriptor(String path) {
		return JptCommonUiPlugin.instance().buildImageDescriptor(path);
	}

	private JptCommonUiImages() {
		throw new UnsupportedOperationException();
	}
}
