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
import org.eclipse.jpt.jpa.core.JpaProject;

/**
 * Standard adapter for retrieving a {@link JpaProject JPA project} model
 * with change notification when the JPA project is created or destroyed:
 * <pre>
 * IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("Foo Project");
 * JpaProjectModel jpaProjectModel = (JpaProjectModel) project.getAdapter(JpaProjectModel.class);
 * JpaProject jpaProject = jpaProjectModel.getValue();
 * </pre>
 * @see org.eclipse.jpt.jpa.ui.internal.ProjectAdapterFactory
 */
public interface JpaProjectModel
	extends PropertyValueModel<JpaProject>
{
	/**
	 * Return the project corresponding to the JPA project model.
	 */
	IProject getProject();
}
