/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.MappingFileProvider;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.resource.orm.OrmXmlResource;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmXmlResource;

public class EclipseLinkMappingFileProvider
	implements MappingFileProvider
{
	// singleton
	private static final MappingFileProvider INSTANCE = new EclipseLinkMappingFileProvider();

	/**
	 * Return the singleton.
	 */
	public static MappingFileProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkMappingFileProvider() {
		super();
	}

	public String getResourceType() {
		return EclipseLinkOrmXmlResource.TYPE;
	}

	public MappingFile buildMappingFile(MappingFileRef parent, OrmXmlResource resource, JpaFactory factory) {
		return ((EclipseLinkJpaFactory) factory).buildEclipseLinkMappingFile(parent, (EclipseLinkOrmXmlResource) resource);
	}

}
