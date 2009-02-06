/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.MappingFileProvider;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLink1_1JpaFactory;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;

public class EclipseLink1_1MappingFileProvider
	implements MappingFileProvider
{
	// singleton
	private static final MappingFileProvider INSTANCE = new EclipseLink1_1MappingFileProvider();

	/**
	 * Return the singleton.
	 */
	public static MappingFileProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLink1_1MappingFileProvider() {
		super();
	}
	public IContentType getContentType() {
		return JptEclipseLinkCorePlugin.ECLIPSELINK1_1_ORM_XML_CONTENT_TYPE;
	}

	public MappingFile buildMappingFile(MappingFileRef parent, JpaXmlResource resource, JpaFactory factory) {
		return ((EclipseLink1_1JpaFactory) factory).buildEclipseLink1_1MappingFile(parent, resource);
	}

}
