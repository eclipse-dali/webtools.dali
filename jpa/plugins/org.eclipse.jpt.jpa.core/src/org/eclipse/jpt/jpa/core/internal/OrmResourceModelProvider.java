/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.resource.orm.OrmXmlResourceProvider;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;

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
		return XmlEntityMappings.CONTENT_TYPE;
	}

	public JptXmlResource buildResourceModel(JpaProject jpaProject, IFile file) {
		return OrmXmlResourceProvider.getXmlResourceProvider(file).getXmlResource();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
