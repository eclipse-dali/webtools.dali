/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2013 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;

public class JPAEditorPreferenceInitializer extends
		AbstractPreferenceInitializer {
	
	public static final String PROPERTY_DIAGRAM_FOLDER 				= "org.eclipse.jpt.jpadiagrameditor.ui.diagramfolder";						//$NON-NLS-1$
	public static final String PROPERTY_ENTITY_PACKAGE 				= "org.eclipse.jpt.jpadiagrameditor.ui.entity.defaultPackage";				//$NON-NLS-1$
	public static final String PROPERTY_TABLE_NAME_PREFIX 			= "org.eclipse.jpt.jpadiagrameditor.ui.entity.defaultTableNamePrefix";		//$NON-NLS-1$
	public static final String PROPERTY_DIRECT_EDIT_CLASS_NAME 		= "org.eclipse.jpt.jpadiagrameditor.ui.entity.defaultDirectEditClassName";	//$NON-NLS-1$
	public static final String PROPERTY_ENTITY_ACCESS_TYPE 			= "org.eclipse.jpt.jpadiagrameditor.ui.entity.defaultEntityAccessType";	//$NON-NLS-1$	
	public static final String PROPERTY_DEFAULT_COLLECTION_TYPE 	= "org.eclipse.jpt.jpadiagrameditor.ui.entity.defaultCollectionType";		//$NON-NLS-1$
	public static final String PROPERTY_ONE_TO_MANY_OLD_STYLE	  	= "org.eclipse.jpt.jpadiagrameditor.ui.entity.defaultOneToManyOldStyle";			//$NON-NLS-1$
	
	public static final String PROPERTY_DEFAULT_SUPPORT_ORM_XML	  	= "org.eclipse.jpt.jpadiagrameditor.ui.supportOrmXml";			//$NON-NLS-1$
	public static final String PROPERTY_DEFAULT_ORM_XML_FILE_NAME	= "org.eclipse.jpt.jpadiagrameditor.ui.ormXmlFileName";			//$NON-NLS-1$

	
	
	public static final String PROPERTY_VAL_DIAGRAM_FOLDER		 	= "diagrams";			//$NON-NLS-1$	
	public static final String PROPERTY_VAL_ENTITY_PACKAGE 			= "org.persistence";	//$NON-NLS-1$
	public static final String PROPERTY_VAL_PREFIX 					= "";					//$NON-NLS-1$
	public static final String PROPERTY_VAL_ACCESS_FIELD_BASED 		= "field";				//$NON-NLS-1$
	public static final String PROPERTY_VAL_ACCESS_PROPERTY_BASED 	= "property";			//$NON-NLS-1$
	
	public static final String PROPERTY_VAL_COLLECTION_TYPE 	= "collection";			//$NON-NLS-1$
	public static final String PROPERTY_VAL_LIST_TYPE 			= "list";			//$NON-NLS-1$
	public static final String PROPERTY_VAL_SET_TYPE 			= "set";			//$NON-NLS-1$
	public static final String PROPERTY_VAL_MAP_TYPE 			= "map";			//$NON-NLS-1$

	
	
	private IPreferenceStore store;

	public JPAEditorPreferenceInitializer() {
		store = JPADiagramEditorPlugin.getDefault()
		.getPreferenceStore();
	}
	
	public JPAEditorPreferenceInitializer(IPreferenceStore store) {
		this.store = store;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		store.setDefault(PROPERTY_DIAGRAM_FOLDER, PROPERTY_VAL_DIAGRAM_FOLDER);		
		store.setDefault(PROPERTY_ENTITY_PACKAGE, PROPERTY_VAL_ENTITY_PACKAGE);
		store.setDefault(PROPERTY_TABLE_NAME_PREFIX, PROPERTY_VAL_PREFIX);
		store.setDefault(PROPERTY_DIRECT_EDIT_CLASS_NAME, true);
		store.setDefault(PROPERTY_ENTITY_ACCESS_TYPE, PROPERTY_VAL_ACCESS_FIELD_BASED);
		store.setDefault(PROPERTY_DEFAULT_COLLECTION_TYPE, PROPERTY_VAL_COLLECTION_TYPE);
		store.setDefault(PROPERTY_ONE_TO_MANY_OLD_STYLE, false);
		store.setDefault(PROPERTY_DEFAULT_SUPPORT_ORM_XML, false);
		store.setDefault(PROPERTY_DEFAULT_ORM_XML_FILE_NAME, XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
	}

}
