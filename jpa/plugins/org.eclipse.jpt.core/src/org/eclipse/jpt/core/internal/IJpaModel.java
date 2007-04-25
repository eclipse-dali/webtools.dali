/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.Iterator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IJpa Model</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIJpaModel()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IJpaModel extends EObject
{
	/**
	 * Returns the IJpaProject corresponding to the given IProject.
	 * Returns <code>null</code> if unable to associate the given IProject
	 * with an IJpaProject.
	 */
	IJpaProject getJpaProject(IProject project) throws CoreException;

	/**
	 * Returns a (non-modifiable) Iterator on all the IJpaProjects in the model.
	 */
	Iterator<IJpaProject> jpaProjects();
}