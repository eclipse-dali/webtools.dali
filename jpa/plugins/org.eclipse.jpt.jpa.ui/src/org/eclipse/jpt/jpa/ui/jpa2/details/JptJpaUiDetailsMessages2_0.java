/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.jpa2.details;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JPA 2.0 UI details view.
 */
public class JptJpaUiDetailsMessages2_0 {

	private static final String BUNDLE_NAME = "jpt_jpa_ui_details2_0"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaUiDetailsMessages2_0.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String CASCADE_PANE_DETACH;
	
	public static String COLLECTION_TABLE_COMPOSITE_TITLE;
	public static String COLLECTION_TABLE_COMPOSITE_JOIN_COLUMN;
	public static String COLLECTION_TABLE_COMPOSITE_NAME;
	public static String COLLECTION_TABLE_COMPOSITE_SCHEMA;
	public static String COLLECTION_TABLE_COMPOSITE_CATALOG;
	public static String COLLECTION_TABLE_COMPOSITE_OVERRIDE_DEFAULT_JOIN_COLUMNS;
	
	public static String DERIVED_IDENTITY_TITLE;
	public static String DERIVED_IDENTITY_NULL_DERIVED_IDENTITY;
	public static String DERIVED_IDENTITY_ID_DERIVED_IDENTITY;
	public static String DERIVED_IDENTITY_MAPS_ID_DERIVED_IDENTITY;
	public static String DERIVED_IDENTITY_MAPS_ID_UNSPECIFIED_VALUE;
	
	public static String ELEMENT_COLLECTION_MAPPING_LABEL;
	public static String ELEMENT_COLLECTION_MAPPING_LINK_LABEL;
	
	public static String ELEMENT_COLLECTION_SECTION_TITLE;
	public static String ABSTRACT_ELEMENT_COLLECTION_MAPPING_COMPOSITE_VALUE_SECTION_TITLE;
	
	public static String ENTITY_CACHEABLE_LABEL;
	public static String ENTITY_CACHEABLE_WITH_DEFAULT_LABEL;
	
	public static String EMBEDDED_ID_MAPPING_MAPPED_BY_RELATIONSHIP_PANE_LABEL;
	
	public static String ID_MAPPING_MAPPED_BY_RELATIONSHIP_PANE_LABEL;
	
	public static String ORDERING_COMPOSITE_ORDER_COLUMN;
	
	public static String ORPHAN_REMOVAL_COMPOSITE_ORPHAN_REMOVAL_LABEL;
	public static String ORPHAN_REMOVAL_COMPOSITE_ORPHAN_REMOVAL_LABEL_DEFAULT;
	
	public static String LOCK_MODE_COMPOSITE_LOCK_MODE_LABEL;

	public static String LOCK_MODE_COMPOSITE_READ;
	public static String LOCK_MODE_COMPOSITE_WRITE;
	public static String LOCK_MODE_COMPOSITE_OPTIMISTIC;
	public static String LOCK_MODE_COMPOSITE_OPTIMISTIC_FORCE_INCREMENT;
	public static String LOCK_MODE_COMPOSITE_PESSIMISTIC_READ;
	public static String LOCK_MODE_COMPOSITE_PESSIMISTIC_WRITE;
	public static String LOCK_MODE_COMPOSITE_PESSIMISTIC_FORCE_INCREMENT;
	public static String LOCK_MODE_COMPOSITE_NONE;

	public static String TARGET_CLASS_COMPOSITE_LABEL;

	private JptJpaUiDetailsMessages2_0() {
		throw new UnsupportedOperationException();
	}
}
