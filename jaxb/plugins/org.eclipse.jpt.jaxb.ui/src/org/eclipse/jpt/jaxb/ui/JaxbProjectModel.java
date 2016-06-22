/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui;

import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.JaxbProject;

/**
 * Standard adapter for retrieving a {@link JaxbProject JAXB project} model
 * with change notification when the JAXB project is created or destroyed:
 * <pre>
 * IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("Foo Project");
 * JaxbProjectModel jaxbProjectModel = (JaxbProjectModel) project.getAdapter(JaxbProjectModel.class);
 * JaxbProject jaxbProject = jaxbProjectModel.getValue();
 * </pre>
 * @see org.eclipse.jpt.jaxb.ui.internal.ProjectAdapterFactory
 */
public interface JaxbProjectModel
	extends PropertyValueModel<JaxbProject>
{
	// simply an interface we can use in an adapter definition
}
