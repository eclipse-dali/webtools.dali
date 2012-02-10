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

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;

/**
 * Standard adapter for retrieving a {@link JpaRootContextNode JPA root
 * context node} model with change notification when the JPA root context
 * node is created or destroyed:
 * <pre>
 * IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("Foo Project");
 * JpaRootContextNodeModel jpaRootContextNodeModel = (JpaJpaRootContextNodeModel) project.getAdapter(JpaJpaRootContextNodeModel.class);
 * JpaRootContextNode jpaRootContextNode = jpaRootContextNodeModel.getValue();
 * </pre>
 * @see org.eclipse.jpt.jpa.ui.internal.ProjectAdapterFactory
 */
public interface JpaRootContextNodeModel
	extends PropertyValueModel<JpaRootContextNode>
{
	/**
	 * Return the project corresponding to the JPA root context model.
	 */
	IProject getProject();
}
