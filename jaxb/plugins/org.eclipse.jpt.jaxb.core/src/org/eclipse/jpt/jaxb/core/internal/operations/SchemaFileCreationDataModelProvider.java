/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0, which accompanies this distribution
* and is available at https://www.eclipse.org/legal/epl-2.0/.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.operations;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.core.internal.operations.AbstractJptFileCreationDataModelProvider;

/**
 *  SchemaFileCreationDataModelProvider
 */
public class SchemaFileCreationDataModelProvider extends AbstractJptFileCreationDataModelProvider
{

	@Override
	protected String getDefaultFileName() {
		if(this.getProject() == null) {
			return null;
		}
		return this.getProject().getName();
	}

	/**
	 * Return a best guess source location for the for the specified project
	 */
	@Override
	protected IContainer getDefaultContainer() {
		IContainer defaultContainer = super.getDefaultContainer();
		if(defaultContainer != null) {
			IProject project = (IProject) this.model.getProperty(PROJECT);
			return defaultContainer.getFolder(project.getLocation());
		}
		return null;
	}
}
