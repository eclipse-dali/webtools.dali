/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.internal.context.IContextModel;
import org.eclipse.jpt.core.internal.platform.BaseJpaFactory;

/**
 * Use IJpaFactory to create any core (e.g. IJpaProject), resource 
 * (e.g. PersistenceResourceModel), or context (e.g. IAttributeMapping) model objects.  
 * @see BaseJpaFactory
 */
public interface IJpaFactory 
{
	/**
	 * Construct an IJpaProject for the specified config, to be
	 * added to the specified JPA project. Return null if unable to create
	 * the JPA file (e.g. the content type is unrecognized).
	 */
	IJpaProject createJpaProject(IJpaProject.Config config) throws CoreException;
	
	IJpaDataSource createJpaDataSource(IJpaProject jpaProject, String connectionProfileName);
	
	/**
	 * Build a resource model to be associated with the given JPA file.
	 * Return null if no resource model is to be associated.
	 */
	IResourceModel buildResourceModel(IFile file);
	
	/**
	 * Construct a JPA file for the specified file, to be
	 * added to the specified JPA project. Return null if unable to create
	 * the JPA file (e.g. the content type is unrecognized).
	 */
	IJpaFile createJpaFile(IJpaProject jpaProject, IFile file, IResourceModel resourceModel);
	
	/**
	 * Build a (updated) context model to be associated with the given JPA project.
	 * The context model will be built once, but updated many times.
	 * @see update(IJpaProject, IContextModel, IProgressMonitor)
	 */
	IContextModel buildContextModel(IJpaProject jpaProject);
}
