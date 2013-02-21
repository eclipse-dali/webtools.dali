/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.JptCommonUiImages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;

/**
 * Actually, just the image descriptors....
 * <p>
 * Code should use these constants to acquire (and release) the images provided
 * by Dali. The images should be managed by a
 * {@link org.eclipse.jface.resource.ResourceManager "local" resource manager}
 * supplied by the {@link org.eclipse.jpt.jpa.ui.JpaWorkbench JpaWorkbench}.
 * <p>
 * Also, the {@link org.eclipse.ui.IWorkbench#getSharedImages() Eclipse workbench}
 * supplies more general purpose
 * {@link org.eclipse.ui.ISharedImages images}.
 * 
 * @see org.eclipse.jpt.jpa.ui.JpaWorkbench#buildLocalResourceManager()
 * @see org.eclipse.jpt.jpa.ui.JpaWorkbench#getResourceManager(org.eclipse.swt.widgets.Control)
 * @see JptCommonUiImages
 */
@SuppressWarnings("nls")
public final class JptJpaUiImages {

	// ********** directories **********

	private static final String IMAGES_ = JptCommonUiImages.IMAGES_;
	private static final String OBJECTS_ = JptCommonUiImages.OBJECTS_;
	private static final String VIEWS_ = JptCommonUiImages.VIEWS_;
	private static final String WIZARDS_ = JptCommonUiImages.WIZARDS_;


	// ********** general **********

	public static final ImageDescriptor JPA_FACET = buildImageDescriptor(IMAGES_ + "jpa-facet.gif");
	public static final ImageDescriptor JPA_CONTENT = buildImageDescriptor(OBJECTS_ + "jpa-content.gif");
	public static final ImageDescriptor JPA_FILE = buildImageDescriptor(OBJECTS_ + "jpa-file.gif");
	public static final ImageDescriptor JAR_FILE = buildImageDescriptor(OBJECTS_ + "jpa-jar-file.gif");
	public static final ImageDescriptor ENUM = buildImageDescriptor(OBJECTS_ + "enum.gif");


	// ********** views **********

	public static final ImageDescriptor JPA_PERSPECTIVE = buildImageDescriptor(VIEWS_ + "jpa-perspective.gif");
	public static final ImageDescriptor JPA_STRUCTURE_VIEW = buildImageDescriptor(VIEWS_ + "jpa-structure.gif");
	public static final ImageDescriptor JPA_DETAILS_VIEW = buildImageDescriptor(VIEWS_ + "jpa-details.gif");


	// ********** wizard banners **********

	public static final ImageDescriptor JPA_PROJECT_BANNER = buildImageDescriptor(WIZARDS_ + "jpa-project-banner.gif");
	public static final ImageDescriptor ENTITY_BANNER = buildImageDescriptor(WIZARDS_ + "entity-banner.gif");
	public static final ImageDescriptor JPA_FILE_BANNER = buildImageDescriptor(WIZARDS_ + "jpa-file-banner.gif");


	// ********** persistence.xml **********

	public static final ImageDescriptor PERSISTENCE = buildImageDescriptor(OBJECTS_ + "persistence.gif");
	public static final ImageDescriptor PERSISTENCE_UNIT = buildImageDescriptor(OBJECTS_ + "persistence-unit.gif");
	public static final ImageDescriptor MAPPING_FILE_REF = buildImageDescriptor(OBJECTS_ + "jpa-file.gif");
	public static final ImageDescriptor CLASS_REF = buildImageDescriptor(OBJECTS_ + "java-class.gif");
	public static final ImageDescriptor JAR_FILE_REF = buildImageDescriptor(OBJECTS_ + "jpa-jar-file.gif");


	// ********** orm.xml **********

	public static final ImageDescriptor ENTITY_MAPPINGS = buildImageDescriptor(OBJECTS_ + "entity-mappings.gif");


	// ********** orm.xml/Java **********

	public static final ImageDescriptor ENTITY = buildImageDescriptor(OBJECTS_ + "entity.gif");
	public static final ImageDescriptor EMBEDDABLE = buildImageDescriptor(OBJECTS_ + "embeddable.gif");
	public static final ImageDescriptor MAPPED_SUPERCLASS = buildImageDescriptor(OBJECTS_ + "mapped-superclass.gif");
	public static final ImageDescriptor NULL_TYPE_MAPPING = buildImageDescriptor(OBJECTS_ + "java-class.gif");
	public static final ImageDescriptor ID = buildImageDescriptor(OBJECTS_ + "id.gif");
	public static final ImageDescriptor EMBEDDED_ID = buildImageDescriptor(OBJECTS_ + "embedded-id.gif");
	public static final ImageDescriptor BASIC = buildImageDescriptor(OBJECTS_ + "basic.gif");
	public static final ImageDescriptor VERSION = buildImageDescriptor(OBJECTS_ + "version.gif");
	public static final ImageDescriptor MANY_TO_ONE = buildImageDescriptor(OBJECTS_ + "many-to-one.gif");
	public static final ImageDescriptor ONE_TO_MANY = buildImageDescriptor(OBJECTS_ + "one-to-many.gif");
	public static final ImageDescriptor ONE_TO_ONE = buildImageDescriptor(OBJECTS_ + "one-to-one.gif");
	public static final ImageDescriptor MANY_TO_MANY = buildImageDescriptor(OBJECTS_ + "many-to-many.gif");
	public static final ImageDescriptor ELEMENT_COLLECTION = buildImageDescriptor(OBJECTS_ + "element-collection.gif");
	public static final ImageDescriptor EMBEDDED = buildImageDescriptor(OBJECTS_ + "embedded.gif");
	public static final ImageDescriptor TRANSIENT = buildImageDescriptor(OBJECTS_ + "transient.gif");
	public static final ImageDescriptor NULL_ATTRIBUTE_MAPPING = buildImageDescriptor(OBJECTS_ + "null-attribute-mapping.gif");


	// ********** JPQL content assist **********

	public static final ImageDescriptor JPQL_FUNCTION = buildImageDescriptor(OBJECTS_ + "jpql-function.gif");
	public static final ImageDescriptor JPQL_IDENTIFIER = buildImageDescriptor(OBJECTS_ + "jpql-identifier.gif");
	public static final ImageDescriptor JPQL_VARIABLE = buildImageDescriptor(OBJECTS_ + "jpql-variable.gif");


	// ********** entity generation database **********

	public static final ImageDescriptor TABLE = buildImageDescriptor(OBJECTS_ + "table.gif");
	public static final ImageDescriptor COLUMN = buildImageDescriptor(OBJECTS_ + "column.gif");


	// ********** entity generation database **********

	public static final ImageDescriptor ENTITY_GEN_TABLE = buildImageDescriptor(OBJECTS_ + "entity-gen-table.gif");
	public static final ImageDescriptor ENTITY_GEN_TABLE_OBJECT = buildImageDescriptor(OBJECTS_ + "entity-gen-table-object.gif");
	public static final ImageDescriptor ENTITY_GEN_COLUMN = buildImageDescriptor(OBJECTS_ + "entity-gen-column.gif");
	public static final ImageDescriptor ENTITY_GEN_KEY_COLUMN = buildImageDescriptor(OBJECTS_ + "entity-gen-column-key.gif");


	// ********** misc **********

	private static ImageDescriptor buildImageDescriptor(String path) {
		return JptJpaUiPlugin.instance().buildImageDescriptor(path);
	}

	private JptJpaUiImages() {
		throw new UnsupportedOperationException();
	}
}
