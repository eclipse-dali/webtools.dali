/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaFileProvider;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.internal.XmlJpaFile;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.internal.resource.orm.EclipseLinkOrmResourceModelProvider;

/**
 * EclipseLink orm.xml
 */
public class EclipseLinkOrmJpaFileProvider
	implements JpaFileProvider
{
	public static final String RESOURCE_TYPE = "EclipseLink ORM"; //$NON-NLS-1$

	// singleton
	private static final EclipseLinkOrmJpaFileProvider INSTANCE = new EclipseLinkOrmJpaFileProvider();

	/**
	 * Return the singleton.
	 */
	public static EclipseLinkOrmJpaFileProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkOrmJpaFileProvider() {
		super();
	}

	public IContentType getContentType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE;
	}

	public JpaFile buildJpaFile(JpaProject jpaProject, IFile file, JpaFactory factory) {
		return new XmlJpaFile(jpaProject, file, RESOURCE_TYPE, this.buildEclipseLinkOrmResource(file));
	}

	protected JpaXmlResource buildEclipseLinkOrmResource(IFile file) {
		return EclipseLinkOrmResourceModelProvider.getModelProvider(file).getResource();
	}

}
