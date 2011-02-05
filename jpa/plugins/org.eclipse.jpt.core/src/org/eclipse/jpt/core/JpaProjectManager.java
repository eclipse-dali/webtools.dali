/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.utility.model.Model;

/**
 * The JPA project manager holds all the JPA projects in the workspace.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface JpaProjectManager
	extends Model
{
	/**
	 * Return the JPA model's JPA projects.
	 */
	Iterable<JpaProject> getJpaProjects();
		public static final String JPA_PROJECTS_COLLECTION = "jpaProjects"; //$NON-NLS-1$

	/**
	 * Return the size of the JPA model's list of JPA projects.
	 */
	int getJpaProjectsSize();

	/**
	 * Return the JPA project corresponding to the specified Eclipse project.
	 * Return <code>null</code> if unable to associate the specified Eclipse
	 * project with a JPA project.
	 */
	JpaProject getJpaProject(IProject project);

	/**
	 * Return the JPA file corresponding to the specified Eclipse file,
	 * or <code>null</code> if unable to associate the specified file with a
	 * JPA file.
	 */
	JpaFile getJpaFile(IFile file);

	/**
	 * The JPA settings associated with the specified Eclipse project
	 * have changed in such a way as to require the associated
	 * JPA project to be completely rebuilt
	 * (e.g. when the user changes a project's JPA platform).
	 */
	void rebuildJpaProject(IProject project);

	/**
	 * Return whether the model's Java change listener is active.
	 */
	boolean javaElementChangeListenerIsActive();

	/**
	 * Set whether the model's Java change listener is active.
	 */
	void setJavaElementChangeListenerIsActive(boolean javaElementChangeListenerIsActive);

}
