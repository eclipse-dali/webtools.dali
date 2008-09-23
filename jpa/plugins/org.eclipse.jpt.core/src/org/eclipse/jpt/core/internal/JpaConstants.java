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
package org.eclipse.jpt.core.internal;

public interface JpaConstants
{
	String XML_NS = "xmlns";  //$NON-NLS-1$
	String XML_NS_XSI = "xmlns:xsi";  //$NON-NLS-1$
	String XSI_SCHEMA_LOCATION = "xsi:schemaLocation";  //$NON-NLS-1$
	String XSI_NS_URL = "http://www.w3.org/2001/XMLSchema-instance";  //$NON-NLS-1$
	
	String PERSISTENCE_NS_URL = "http://java.sun.com/xml/ns/persistence";  //$NON-NLS-1$
	String PERSISTENCE_SCHEMA_LOC_1_0 = "http://java.sun.com/xml/ns/persistence/persistence_1_0.xsd";  //$NON-NLS-1$
	
	String ORM_NS_URL = "http://java.sun.com/xml/ns/persistence/orm";  //$NON-NLS-1$
	String ORM_SCHEMA_LOC_1_0 = "http://java.sun.com/xml/ns/persistence/orm_1_0.xsd";  //$NON-NLS-1$
	
	String VERSION_1_0_TEXT		= "1.0"; //$NON-NLS-1$
}
