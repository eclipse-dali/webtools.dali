/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.operations;


import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.core.internal.operations.AbstractJptFileCreationOperation;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public abstract class AbstractJpaFileCreationOperation
		extends AbstractJptFileCreationOperation {
	
	protected AbstractJpaFileCreationOperation(IDataModel dataModel) {
		super(dataModel);
	}
	
	
	protected JpaProject getJpaProject() throws ExecutionException {
		IProject project = getProject();
		JpaProject jpaProject = this.getJpaProject(project);
		if (jpaProject == null) {
			throw new ExecutionException("Project does not have JPA facet"); //$NON-NLS-1$
		}
		return jpaProject;
	}
	
	protected JpaProject getJpaProject(IProject project) throws ExecutionException {
		try {
			return this.getJpaProject_(project);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			throw new ExecutionException(null, ex);
		}
	}
	
	protected JpaProject getJpaProject_(IProject project) throws InterruptedException {
		JpaProject.Reference ref = this.getJpaProjectReference(project);
		return (ref == null) ? null : ref.getValue();
	}
	
	protected JpaProject.Reference getJpaProjectReference(IProject project) {
		return (JpaProject.Reference) project.getAdapter(JpaProject.Reference.class);
	}
}
