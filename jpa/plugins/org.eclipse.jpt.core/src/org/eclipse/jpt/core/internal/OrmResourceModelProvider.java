/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaResourceModel;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.JptCorePlugin;

/**
 * orm.xml
 */
public class OrmResourceModelProvider
	implements JpaResourceModelProvider
{
	// singleton
	private static final JpaResourceModelProvider INSTANCE = new OrmResourceModelProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaResourceModelProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private OrmResourceModelProvider() {
		super();
	}

	public IContentType getContentType() {
		return JptCorePlugin.ORM_XML_CONTENT_TYPE;
	}

	public JpaResourceModel buildResourceModel(JpaProject jpaProject, IFile file) {
		return org.eclipse.jpt.core.internal.resource.orm.OrmXmlResourceProvider.getXmlResourceProvider(file).getXmlResource();
	}

}
