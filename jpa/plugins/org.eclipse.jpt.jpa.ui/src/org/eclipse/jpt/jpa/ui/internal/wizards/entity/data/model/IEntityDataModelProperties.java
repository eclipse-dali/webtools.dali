/***********************************************************************
 * Copyright (c) 2008 by SAP AG, Walldorf. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SAP AG - initial API and implementation
 *     Dimiter Dimitrov, d.dimitrov@sap.com - initial API and implementation     
 ***********************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.entity.data.model;

import org.eclipse.jst.j2ee.application.internal.operations.IAnnotationsDataModel;
import org.eclipse.jst.j2ee.internal.common.operations.INewJavaClassDataModelProperties;

public interface IEntityDataModelProperties extends INewJavaClassDataModelProperties, IAnnotationsDataModel {
	
	public static final String ENTITY = "IEntityDataModelProperties.ENTITY"; //$NON-NLS-1$
	public static final String MAPPED_AS_SUPERCLASS = "IEntityDataModelProperties.MAPPED_AS_SUPERCLASS"; //$NON-NLS-1$
	public static final String INHERITANCE = "IEntityDataModelProperties.INHERITANCE"; //$NON-NLS-1$
	public static final String INHERITANCE_STRATEGY = "IEntityDataModelProperties.INHERITANCE_STRATEGY"; //$NON-NLS-1$
	public static final String XML_SUPPORT = "IEntityDataModelProperties.XML_SUPPORT"; //$NON-NLS-1$XML_SUPPORT	
	public static final String XML_NAME = "IEntityDataModelProperties.XML_NAME"; //$NON-NLS-1$XML_SUPPORT
	public static final String ENTITY_NAME = "IEntityDataModelProperties.ENTITY_NAME"; //$NON-NLS-1$
	public static final String TABLE_NAME_DEFAULT = "IEntityDataModelProperties.TABLE_NAME_DEFAULT"; //$NON-NLS-1$
	public static final String TABLE_NAME = "IEntityDataModelProperties.TABLE_NAME"; //$NON-NLS-1$
	public static final String ENTITY_FIELDS = "IEntityDataModelProperties.ENTITY_FIELDS"; //$NON-NLS-1$	
	public static final String PK_FIELDS = "IEntityDataModelProperties.PK_FIELDS"; //$NON-NLS-1$
	public static final String FIELD_ACCESS_TYPE = "IEntityDataModelProperties.FIELD_ACCESS_TYPE"; //$NON-NLS-1$
	public static final String PROPERTY_ACCESS_TYPE = "IEntityDataModelProperties.PROPERTY_ACCESS_TYPE"; //$NON-NLS-1$
	public static final String EMPTY_STRING = ""; //$NON-NLS-1$	
}
