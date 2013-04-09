/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.ui.internal.wizards.makepersistent;

import org.eclipse.osgi.util.NLS;


public class JptJpaUiMakePersistentMessages  {

	private static final String BUNDLE_NAME = "jpt_jpa_ui_make_persistent"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaUiMakePersistentMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String ADD;
	public static String REMOVE;
	public static String BROWSE;
	public static String CASCADE;
	public static String CHOOSE_TYPE;
	
	public static String CLASS_MAPPING_PAGE_TITLE;
	public static String CLASS_MAPPING_PAGE_DESC;
	public static String CLASS_MAPPING_PAGE_TYPE_TABLE_COLUMN;
	public static String CLASS_MAPPING_PAGE_MAPPING_TABLE_COLUMN;
	public static String CLASS_MAPPING_PAGE_PRIMARY_KEY_PROPERTY_COLUMN;
	
	public static String SELECT_TABLE_DLG_DESC;
	public static String SELECT_TABLE_DLG_TITLE;

	public static String PROPS_MAPPING_PAGE_TITLE;
	public static String PROPS_MAPPING_PAGE_DESC;
	public static String PROPS_MAPPING_PAGE_LABEL;
	public static String PROPS_MAPPING_PAGE_JAVA_CLASS_LABEL;
	public static String PROPS_MAPPING_PAGE_PROPERTY_NAME_LABEL;
	public static String PROPS_MAPPING_PAGE_PROPERTY_TYPE_LABEL;
	public static String PROPS_MAPPING_PAGE_DATABASE_COLUMN;
	public static String PROPS_MAPPING_PAGE_COLUMN_TYPE;
	public static String PROPS_MAPPING_PAGE_COLUMN_TYPE_NA;
	public static String PROPS_MAPPING_PAGE_EDIT;
	public static String PROPS_MAPPING_PAGE_REMOVE;
	public static String PROPS_MAPPING_PAGE_UNSPECIFIED;
	public static String PROPS_MAPPING_PAGE_MAPPEDBY;
	public static String PROPS_MAPPING_PAGE_JOIN_TABLE;
	public static String PROPS_MAPPING_PAGE_JOIN_AND;

	public static String PROPERTY_NAME_LABEL;
	public static String PROPERTY_TYPE_LABEL;	
	public static String ID_ANNOTATION_DLG_TITLE;
	public static String ID_ANNOTATION_GROUP_DESC;
	public static String GENERATION_STRATEGY;
	public static String COL_ANNOTATION_GROUP_DESC;
	public static String COLUMN_NAME;
	public static String REF_COLUMN_NAME;
	public static String UNIQUE;
	public static String NULLABLE;
	public static String LENGTH;
	public static String PRECISION;
	public static String SCALE;
	public static String INSERTABLE;
	public static String UPDATABLE;
	public static String TEMPORAL;
	public static String FETCH_TYPE;
	public static String SELECT_COLUMN_DLG_TITLE;
	public static String SELECT_COLUMN_DLG_DESC;
	public static String BASIC_ANNOTATION_DLG_TITLE;
	public static String BASIC_ANNOTATION_GROUP_DESC;
	public static String SELECT_CASCADE_DLG_TITLE;
	public static String TOMANY_ANNOTATION_DLG_TITLE;
	public static String TARGET_ENTITY_GROUP_DESC;
	public static String TARGET_ENTITY_GROUP_LABEL;
	public static String ORDER_BY_TITLE;
	public static String ORDER_BY_LABEL;
	public static String ORDER_BY_DESC;
	public static String MAPPED_BY_DESC;
	public static String PK_JOIN_COLUMN_DESC;
	public static String JOIN_TABLE_DESC;
	public static String MANY_TO_MANY_PROP_DESC;
	public static String ONE_TO_MANY_PROP_DESC;
	public static String MANY_TO_ONE_PROP_DESC;
	public static String ONE_TO_ONE_PROP_DESC;	
	public static String JOIN_PROPS;
	public static String REF_TABLE_NOT_SPECIFIED;
	public static String ADD_JOIN_COLUMN;
	public static String JOIN_COLUMN_TABLE_DESC;
	public static String NO_JOIN_COLUMN_LABEL;
	public static String ADD_JOIN_COLUMN_DLG_TITLE;
	public static String ASSOCIATION_WIZARD_TITLE;
	public static String ASSOCIATION_WIZARD_ERROR;
	public static String ASSOCIATION_WIZARD_NO_TARGET_ENTITY;	
	public static String CARDINALITY_PAGE_TITLE;
	public static String CARDINALITY_PAGE_DESC;
	public static String MAPPING_PAGE_TITLE;
	public static String MAPPING_PAGE_DESC;
	public static String JOIN_PROPS_PAGE_TITLE;
	public static String JOIN_PROPS_PAGE_DESC;
	public static String CHOOSE_PROPERTY_TITLE;
	public static String CHOOSE_PROPERTY_DESC;
	public static String PK_JOIN_COLUMNS_LABEL;
	public static String JOIN_COLUMNS_LABEL;
	public static String EDIT_JOIN_COLUMNS;
	public static String EDIT_JOIN_COLUMNS_DESC;
	public static String SELECT_ORDERBY_DIALOG_PROPERTY;
	public static String SELECT_ORDERBY_DIALOG_ORDER;

	private JptJpaUiMakePersistentMessages() {
		throw new UnsupportedOperationException();
	}

}
