/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.JpaProject;

/**
 * Not much different from the abstract JPA project.
 */
public class GenericJpaProject
	extends AbstractJpaProject
{

	// ********** constructor/initialization **********

	public GenericJpaProject(JpaProject.Config config) throws CoreException {
		super(config);
	}

}
