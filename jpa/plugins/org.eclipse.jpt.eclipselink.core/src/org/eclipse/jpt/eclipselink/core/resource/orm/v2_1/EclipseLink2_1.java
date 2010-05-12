/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm.v2_1;

import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLink2_0;

@SuppressWarnings("nls")
public interface EclipseLink2_1
	extends EclipseLink2_0
{
	String SCHEMA_NAMESPACE = EclipseLink.SCHEMA_NAMESPACE;
	String SCHEMA_LOCATION = "http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_2_1.xsd";
	String SCHEMA_VERSION	= "2.1";
	
	
	// EclipseLink 2.1 specific nodes
	
	String ATTRIBUTE = "attribute";
	String ATTRIBUTE_TYPE = "attribute-type";
	String BATCH_FETCH = "batch-fetch";
		String BATCH_FETCH__SIZE = "size";
		String BATCH_FETCH__TYPE = "type";
	String CACHE_KEY_TYPE = "cache-key-type";
	String CLASS_EXTRACTOR = "class-extractor";
	String FETCH_GROUP = "fetch-group";
		String FETCH_GROUP__LOAD = "load";
	String PARENT_CLASS = "parent-class";
	String RETURN_INSERT = "return-insert";
	String RETURN_ONLY = "return-only";
	String RETURN_UPDATE = "return-update";
}
