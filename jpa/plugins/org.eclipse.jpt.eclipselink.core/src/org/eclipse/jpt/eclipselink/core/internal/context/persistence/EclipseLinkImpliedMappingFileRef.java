/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.GenericMappingFileRef;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;

/**
 * Currently, this class is only used for implied eclipselink mapping files
 */	
public class EclipseLinkImpliedMappingFileRef extends GenericMappingFileRef
{
	public EclipseLinkImpliedMappingFileRef(PersistenceUnit parent) {
		super(parent, null);
	}
	
	
	@Override
	protected void initializeFileName() {
		fileName = JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH;
	}
	
	@Override
	protected void updateFileName() {
		setFileName_(JptEclipseLinkCorePlugin.DEFAULT_ECLIPSELINK_ORM_XML_FILE_PATH);
	}
}
