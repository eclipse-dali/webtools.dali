/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.JptCommonUiImages;
import org.eclipse.jpt.jaxb.eclipselink.ui.internal.plugin.JptJaxbEclipseLinkUiPlugin;

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
 * @see org.eclipse.jpt.jaxb.ui.JptJaxbUiImages
 * @see JptCommonUiImages
 */
// TODO add corresponding ImageDescriptorTest...
@SuppressWarnings("nls")
public final class JptJaxbEclipseLinkUiImages {
	
	// ********** directories **********
	
	private static final String OBJECTS_ = JptCommonUiImages.OBJECTS_;
	private static final String WIZARDS_ = JptCommonUiImages.WIZARDS_;
	
	
	// ********** oxm **********
	
	public static final ImageDescriptor OXM_FILE = buildImageDescriptor(OBJECTS_ + "oxm-file.gif");
	
	
	// ********** mappings **********
	
	public static final ImageDescriptor XML_INVERSE_REFERENCE = buildImageDescriptor(OBJECTS_ + "xml-inverse-reference.gif");
	public static final ImageDescriptor XML_JOIN_NODES = buildImageDescriptor(OBJECTS_ + "xml-join-nodes.gif");
	public static final ImageDescriptor XML_TRANSFORMATION = buildImageDescriptor(OBJECTS_ + "xml-transformation.gif");
	
	
	// ********** wizard banners **********
	
	public static final ImageDescriptor OXM_FILE_BANNER = buildImageDescriptor(WIZARDS_ + "oxm-file-banner.png");
	
	
	// ********** misc **********
	
	private static ImageDescriptor buildImageDescriptor(String path) {
		return JptJaxbEclipseLinkUiPlugin.instance().buildImageDescriptor(path);
	}
	
	private JptJaxbEclipseLinkUiImages() {
		throw new UnsupportedOperationException();
	}
}
