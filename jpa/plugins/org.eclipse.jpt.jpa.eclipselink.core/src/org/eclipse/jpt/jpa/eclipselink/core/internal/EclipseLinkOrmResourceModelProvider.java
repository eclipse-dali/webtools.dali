/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaResourceModelProvider;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.orm.EclipseLinkOrmXmlResourceProvider;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;

/**
 * EclipseLink orm.xml
 */
public class EclipseLinkOrmResourceModelProvider
	implements JpaResourceModelProvider
{
	// singleton
	private static final JpaResourceModelProvider INSTANCE = new EclipseLinkOrmResourceModelProvider();
	
	
	/**
	 * Return the singleton
	 */
	public static JpaResourceModelProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkOrmResourceModelProvider() {
		super();
	}
	
	
	public IContentType getContentType() {
		return XmlEntityMappings.CONTENT_TYPE;
	}
	
	public JptXmlResource buildResourceModel(JpaProject jpaProject, IFile file) {
		return EclipseLinkOrmXmlResourceProvider.getXmlResourceProvider(file).getXmlResource();
	}
}
