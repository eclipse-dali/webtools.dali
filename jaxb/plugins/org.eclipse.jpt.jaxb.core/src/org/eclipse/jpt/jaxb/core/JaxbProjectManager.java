/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.utility.model.Model;


/**
 * The JAXB project manager holds all the JAXB projects in the workspace.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface JaxbProjectManager
		extends Model {
	
	/**
	 * Return the JAXB model's JAXB projects.
	 */
	Iterable<JaxbProject> getJaxbProjects();
		public static final String JAXB_PROJECTS_COLLECTION = "jaxbProjects"; //$NON-NLS-1$

	/**
	 * Return the size of the JAXB model's list of JAXB projects.
	 */
	int getJaxbProjectsSize();

	/**
	 * Return the JAXB project corresponding to the specified Eclipse project.
	 * Return null if unable to associate the specified Eclipse project
	 * with a JAXB project.
	 */
	JaxbProject getJaxbProject(IProject project);

	/**
	 * Return the JAXB file corresponding to the specified Eclipse file,
	 * or null if unable to associate the specified file with a JAXB file.
	 */
	JaxbFile getJaxbFile(IFile file);

	/**
	 * The JAXB settings associated with the specified Eclipse project
	 * have changed in such a way as to require the associated
	 * JPA project to be completely rebuilt
	 * (e.g. when the user changes a project's JAXB platform).
	 */
	void rebuildJaxbProject(IProject project);

	/**
	 * Return whether the model's Java change listener is active.
	 */
	boolean javaElementChangeListenerIsActive();

	/**
	 * Set whether the model's Java change listener is active.
	 */
	void setJavaElementChangeListenerIsActive(boolean javaElementChangeListenerIsActive);
	
}
