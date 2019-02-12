/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.JptCommonUiImages;
import org.eclipse.jpt.jaxb.ui.internal.plugin.JptJaxbUiPlugin;

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
public final class JptJaxbUiImages {

	// ********** directories **********

	private static final String IMAGES_ = JptCommonUiImages.IMAGES_;
	private static final String BUTTONS_ = JptCommonUiImages.BUTTONS_;
	private static final String OBJECTS_ = JptCommonUiImages.OBJECTS_;
	private static final String WIZARDS_ = JptCommonUiImages.WIZARDS_;


	// ********** general **********

	public static final ImageDescriptor JAXB_FACET = buildImageDescriptor(IMAGES_ + "jaxb-facet.gif");
	public static final ImageDescriptor JAXB_CONTENT = buildImageDescriptor(OBJECTS_ + "jaxb-content.gif");

	public static final ImageDescriptor DTD_FILE = buildImageDescriptor(OBJECTS_ + "dtd-file.gif");
	public static final ImageDescriptor XSD_FILE = buildImageDescriptor(OBJECTS_ + "xsd-file.gif");


	// ********** buttons **********

	public static final ImageDescriptor NEW_CLASS = buildImageDescriptor(BUTTONS_ + "new-class.gif");
	public static final ImageDescriptor NEW_JAXB_PROJECT = buildImageDescriptor(BUTTONS_ + "new-jaxb-project.gif");
	public static final ImageDescriptor NEW_XSD = buildImageDescriptor(BUTTONS_ + "new-xsd.gif");


	// ********** Java **********

	public static final ImageDescriptor JAXB_PACKAGE = buildImageDescriptor(OBJECTS_ + "jaxb-package.gif");
	public static final ImageDescriptor JAXB_CLASS = buildImageDescriptor(OBJECTS_ + "jaxb-class.gif");
	public static final ImageDescriptor JAXB_ENUM = buildImageDescriptor(OBJECTS_ + "jaxb-enum.gif");
	public static final ImageDescriptor JAXB_TRANSIENT_CLASS = buildImageDescriptor(OBJECTS_ + "jaxb-transient-class.gif");
	public static final ImageDescriptor JAXB_TRANSIENT_ENUM = buildImageDescriptor(OBJECTS_ + "jaxb-transient-enum.gif");
	public static final ImageDescriptor PERSISTENT_FIELD = buildImageDescriptor(OBJECTS_ + "persistent-field.gif");
	public static final ImageDescriptor PERSISTENT_PROPERTY = buildImageDescriptor(OBJECTS_ + "persistent-property.gif");
	public static final ImageDescriptor ENUM_CONSTANT = buildImageDescriptor(OBJECTS_ + "enum-constant.gif");


	// ********** mappings **********

	public static final ImageDescriptor JAXB_REGISTRY = buildImageDescriptor(OBJECTS_ + "jaxb-registry.gif");
	public static final ImageDescriptor XML_ANY_ATTRIBUTE = buildImageDescriptor(OBJECTS_ + "xml-any-attribute.gif");
	public static final ImageDescriptor XML_ANY_ELEMENT = buildImageDescriptor(OBJECTS_ + "xml-any-element.gif");
	public static final ImageDescriptor XML_ATTRIBUTE = buildImageDescriptor(OBJECTS_ + "xml-attribute.gif");
	public static final ImageDescriptor XML_ELEMENT = buildImageDescriptor(OBJECTS_ + "xml-element.gif");
	public static final ImageDescriptor XML_ELEMENT_REF = buildImageDescriptor(OBJECTS_ + "xml-element-ref.gif");
	public static final ImageDescriptor XML_ELEMENT_REFS = buildImageDescriptor(OBJECTS_ + "xml-element-refs.gif");
	public static final ImageDescriptor XML_ELEMENTS = buildImageDescriptor(OBJECTS_ + "xml-elements.gif");
	public static final ImageDescriptor XML_TRANSIENT = buildImageDescriptor(OBJECTS_ + "xml-transient.gif");
	public static final ImageDescriptor XML_VALUE = buildImageDescriptor(OBJECTS_ + "xml-value.gif");
	public static final ImageDescriptor NULL_ATTRIBUTE_MAPPING = buildImageDescriptor(OBJECTS_ + "null-attribute-mapping.gif");


	// ********** wizard banners **********

	public static final ImageDescriptor JAXB_PROJECT_BANNER = buildImageDescriptor(WIZARDS_ + "jaxb-project-banner.gif");
	public static final ImageDescriptor SCHEMA_GEN_BANNER = buildImageDescriptor(WIZARDS_ + "schema-gen-banner.gif");
	public static final ImageDescriptor CLASSES_GEN_BANNER = buildImageDescriptor(WIZARDS_ + "classes-gen-banner.gif");


	// ********** misc **********

	private static ImageDescriptor buildImageDescriptor(String path) {
		return JptJaxbUiPlugin.instance().buildImageDescriptor(path);
	}

	private JptJaxbUiImages() {
		throw new UnsupportedOperationException();
	}
}
