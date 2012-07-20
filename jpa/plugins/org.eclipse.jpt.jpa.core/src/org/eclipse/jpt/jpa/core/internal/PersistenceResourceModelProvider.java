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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaResourceModelProvider;
import org.eclipse.jpt.jpa.core.internal.resource.persistence.PersistenceXmlResourceProvider;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.jpa.core.resource.xml.JpaXmlResource;

/**
 * persistence.xml
 */
public class PersistenceResourceModelProvider
	implements JpaResourceModelProvider
{
	// singleton
	private static final JpaResourceModelProvider INSTANCE = new PersistenceResourceModelProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaResourceModelProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private PersistenceResourceModelProvider() {
		super();
	}

	public IContentType getContentType() {
		return XmlPersistence.CONTENT_TYPE;
	}

	public JpaXmlResource buildResourceModel(JpaProject jpaProject, IFile file) {
		if (! file.isSynchronized(IResource.DEPTH_ZERO)) {
			return null;
		}
		return PersistenceXmlResourceProvider.getXmlResourceProvider(file).getXmlResource();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
