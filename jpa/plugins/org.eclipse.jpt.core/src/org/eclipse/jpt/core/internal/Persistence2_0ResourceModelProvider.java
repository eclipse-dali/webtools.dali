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
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.resource.persistence.Persistence2_0XmlResourceProvider;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;

/**
 * persistence.xml
 */
public class Persistence2_0ResourceModelProvider
	implements JpaResourceModelProvider
{
	// singleton
	private static final JpaResourceModelProvider INSTANCE = new Persistence2_0ResourceModelProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaResourceModelProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private Persistence2_0ResourceModelProvider() {
		super();
	}

	public IContentType getContentType() {
		return JptCorePlugin.PERSISTENCE2_0_XML_CONTENT_TYPE;
	}

	public JpaXmlResource buildResourceModel(JpaProject jpaProject, IFile file) {
		return Persistence2_0XmlResourceProvider.getXmlResourceProvider(file).getXmlResource();
	}

}
