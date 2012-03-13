/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jpa.core.JpaProject;

/**
 * Standard adapter for retrieving a model of a workspace's
 * {@link JpaProject JPA project}s
 * with change notification when the model changes:
 * <pre>
 * IWorkspace workspace = ResourcesPlugin.getWorkspace();
 * JpaProjectsModel jpaProjectsModel = (JpaProjectsModel) workspace.getAdapter(JpaProjectsModel.class);
 * </pre>
 * @see org.eclipse.jpt.jpa.ui.internal.WorkspaceAdapterFactory
 */
public interface JpaProjectsModel
	extends CollectionValueModel<JpaProject>
{
	/**
	 * Return the workspace corresponding to the JPA projects model.
	 */
	IWorkspace getWorkspace();
}
