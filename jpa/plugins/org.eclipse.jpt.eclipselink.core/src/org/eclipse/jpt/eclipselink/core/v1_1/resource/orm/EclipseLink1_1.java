/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.v1_1.resource.orm;

import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLink;

@SuppressWarnings("nls")
public interface EclipseLink1_1
	extends EclipseLink
{
	String SCHEMA_NAMESPACE = EclipseLink.SCHEMA_NAMESPACE;
	String SCHEMA_LOCATION = "http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_1_1.xsd";
	String SCHEMA_VERSION	= "1.1";
	
	// EclipseLink 1.1 specific nodes
	
	String PRIMARY_KEY = "primary-key";  //$NON-NLS-1$
	String PRIMARY_KEY__VALIDATION = "validation";  //$NON-NLS-1$
	String PRIMARY_KEY__COLUMN = "column";  //$NON-NLS-1$
}
