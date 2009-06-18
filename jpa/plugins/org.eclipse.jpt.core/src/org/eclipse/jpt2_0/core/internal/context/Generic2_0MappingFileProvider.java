/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.internal.context;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.MappingFileProvider;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt2_0.core.internal.platform.Generic2_0JpaFactory;

public class Generic2_0MappingFileProvider
	implements MappingFileProvider
{
	// singleton
	private static final MappingFileProvider INSTANCE = new Generic2_0MappingFileProvider();

	/**
	 * Return the singleton.
	 */
	public static MappingFileProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private Generic2_0MappingFileProvider() {
		super();
	}

	public IContentType getContentType() {
		return JptCorePlugin.ORM2_0_XML_CONTENT_TYPE;
	}

	public MappingFile buildMappingFile(MappingFileRef parent, JpaXmlResource resource, JpaFactory factory) {
		return ((Generic2_0JpaFactory) factory).build2_0MappingFile(parent, resource);
	}

}
