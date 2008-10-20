/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaFileProvider;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;

/**
 * persistence.xml
 */
public class PersistenceJpaFileProvider
	implements JpaFileProvider
{

	// singleton
	private static final PersistenceJpaFileProvider INSTANCE = new PersistenceJpaFileProvider();

	/**
	 * Return the singleton.
	 */
	public static PersistenceJpaFileProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private PersistenceJpaFileProvider() {
		super();
	}

	public String getContentId() {
		return JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE;
	}

	public JpaFile buildJpaFile(JpaProject jpaProject, IFile file, JpaFactory factory) {
		return factory.buildPersistenceJpaFile(jpaProject, file);
	}

}
