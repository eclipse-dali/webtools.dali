/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.resource.orm;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;

/**
 * eclipselink-orm.xml v. 2.0 implementation of {@link JpaResourceModelProvider}
 */
public class EclipseLink2_0OrmResourceModelProvider
	implements JpaResourceModelProvider
{
	// singleton
	private static final JpaResourceModelProvider INSTANCE = 
			new EclipseLink2_0OrmResourceModelProvider();
	
	
	/**
	 * Return the singleton
	 */
	public static JpaResourceModelProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLink2_0OrmResourceModelProvider() {
		super();
	}
	
	
	public IContentType getContentType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK2_0_ORM_XML_CONTENT_TYPE;
	}
	
	public JpaXmlResource buildResourceModel(JpaProject jpaProject, IFile file) {
		return EclipseLink2_0OrmXmlResourceProvider.getXmlResourceProvider(file).getXmlResource();
	}
}
