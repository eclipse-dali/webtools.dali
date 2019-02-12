/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.JptCommonUiImages;
import org.eclipse.jpt.dbws.eclipselink.ui.internal.plugin.JptDbwsEclipseLinkUiPlugin;

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
 * @see JptCommonUiImages
 */
// TODO add corresponding ImageDescriptorTest...
@SuppressWarnings("nls")
public final class JptDbwsEclipseLinkUiImages {

	// ********** directories **********

	private static final String BUTTONS_ = JptCommonUiImages.BUTTONS_;
	private static final String OBJECTS_ = JptCommonUiImages.OBJECTS_;
	private static final String WIZARDS_ = JptCommonUiImages.WIZARDS_;


	// ********** buttons **********

	public static final ImageDescriptor NEW_WEB_SERVICES_CLIENT = buildImageDescriptor(BUTTONS_ + "new-web-services-client.gif");


	// ********** objects **********

	public static final ImageDescriptor DTD_FILE = buildImageDescriptor(OBJECTS_ + "dtd-file.gif");
	public static final ImageDescriptor XSD_FILE = buildImageDescriptor(OBJECTS_ + "xsd-file.gif");


	// ********** wizard banners **********

	public static final ImageDescriptor NEW_WEB_SERVICES_CLIENT_BANNER = buildImageDescriptor(WIZARDS_ + "new-web-services-client.gif");


	// ********** misc **********

	private static ImageDescriptor buildImageDescriptor(String path) {
		return JptDbwsEclipseLinkUiPlugin.instance().buildImageDescriptor(path);
	}

	private JptDbwsEclipseLinkUiImages() {
		throw new UnsupportedOperationException();
	}
}
