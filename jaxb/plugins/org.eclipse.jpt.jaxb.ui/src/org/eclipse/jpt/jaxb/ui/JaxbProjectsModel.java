/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui;

import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;

/**
 * Standard adapter for retrieving a model of a workspace's
 * {@link JaxbProject JAXB project}s
 * with change notification when the model changes:
 * <pre>
 * IWorkspace workspace = ResourcesPlugin.getWorkspace();
 * JaxbProjectsModel jaxbProjectsModel = (JaxbProjectsModel) workspace.getAdapter(JaxbProjectsModel.class);
 * </pre>
 * @see org.eclipse.jpt.jaxb.ui.internal.WorkspaceAdapterFactory
 */
public interface JaxbProjectsModel
	extends CollectionValueModel<JaxbProject>
{
	/**
	 * Return the JAXB workspace corresponding to the JAXB projects model.
	 */
	JaxbWorkspace getJaxbWorkspace();
}
