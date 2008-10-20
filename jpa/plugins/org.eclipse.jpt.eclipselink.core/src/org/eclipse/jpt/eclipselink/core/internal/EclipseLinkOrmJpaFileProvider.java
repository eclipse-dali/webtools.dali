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
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaFileProvider;
import org.eclipse.jpt.core.JpaProject;

/**
 * EclipseLink orm.xml
 */
public class EclipseLinkOrmJpaFileProvider
	implements JpaFileProvider
{

	// singleton
	private static final EclipseLinkOrmJpaFileProvider INSTANCE = new EclipseLinkOrmJpaFileProvider();

	/**
	 * Return the singleton.
	 */
	public static EclipseLinkOrmJpaFileProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private EclipseLinkOrmJpaFileProvider() {
		super();
	}

	public String getContentId() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE;
	}

	public JpaFile buildJpaFile(JpaProject jpaProject, IFile file, JpaFactory factory) {
		return ((EclipseLinkJpaFactory) factory).buildEclipseLinkOrmJpaFile(jpaProject, file);
	}

}
