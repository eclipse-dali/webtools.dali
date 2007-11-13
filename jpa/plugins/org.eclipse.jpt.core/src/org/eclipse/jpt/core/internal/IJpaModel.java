/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.utility.internal.model.Model;

/**
 * The JPA model holds all the JPA projects.
 */
public interface IJpaModel extends Model {

	/**
	 * Return the JPA project corresponding to the specified Eclipse project.
	 * Return null if unable to associate the specified Eclipse project
	 * with a JPA project.
	 */
	IJpaProject jpaProject(IProject project) throws CoreException;

	/**
	 * Return whether the JPA model contains a JPA project corresponding
	 * to the specified Eclipse project.
	 */
	boolean containsJpaProject(IProject project);

	/**
	 * Return the JPA model's JPA projects.
	 */
	Iterator<IJpaProject> jpaProjects() throws CoreException;
		public static final String JPA_PROJECTS_COLLECTION = "jpaProjects";

	/**
	 * Return the size of the JPA model's list of JPA projects.
	 */
	int jpaProjectsSize();
	
	/**
	 * Return the JPA file corresponding to the specified Eclipse file,
	 * or null if unable to associate the specified file with a JPA file.
	 */
	IJpaFile jpaFile(IFile file) throws CoreException;

}
