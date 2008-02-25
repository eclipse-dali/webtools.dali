/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.internal.platform.base.BaseJpaFactory;

/**
 * Use JpaFactory to create any core (e.g. JpaProject), resource 
 * (e.g. PersistenceResource), or context (e.g. AttributeMapping) model objects.  
 * @see BaseJpaFactory
 */
public interface JpaFactory 
{
	/**
	 * Construct a JpaProject for the specified config, to be
	 * added to the specified JPA project. Return null if unable to create
	 * the JPA file (e.g. the content type is unrecognized).
	 */
	JpaProject buildJpaProject(JpaProject.Config config) throws CoreException;
	
	JpaDataSource buildJpaDataSource(JpaProject jpaProject, String connectionProfileName);
	
	/**
	 * Construct a JPA file for the specified file and with the specified resource
	 * model, to be added to the specified JPA project.
	 * This should be non-null iff (if and only if) {@link #hasRelevantContent(IFile)}
	 * returns true.
	 */
	JpaFile buildJpaFile(JpaProject jpaProject, IFile file, ResourceModel resourceModel);
	
	/**
	 * Return true if a resource model will be provided for the given file
	 */
	boolean hasRelevantContent(IFile file);
	
	/**
	 * Build a resource model to be associated with the given file.
	 * This should be non-null iff (if and only if) {@link #hasRelevantContent(IFile)}
	 * returns true. 
	 */
	ResourceModel buildResourceModel(JpaProject jpaProject, IFile file);
	
	/**
	 * Build a (updated) context model to be associated with the given JPA project.
	 * The context model will be built once, but updated many times.
	 * @see JpaProject.update(ProgressMonitor)
	 */
	ContextModel buildContextModel(JpaProject jpaProject);
}
