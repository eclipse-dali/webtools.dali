/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.facet;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class JpaFacetUninstallDelegate 
	implements IDelegate 
{
	public void execute(IProject project, IProjectFacetVersion fv, Object config, IProgressMonitor monitor) throws CoreException {
		JpaPreferences.removePreferences(project);
		removeBuilder(project, STATIC_WEAVING_BUILDER_ID);
	}

	//TODO hack to fix bug 380735 in RC2. We need to move this code into the eclipselink plug-in
	private static String STATIC_WEAVING_BUILDER_ID = "org.eclipse.jpt.jpa.eclipselink.core.builder"; //$NON-NLS-1$

	private static void removeBuilder(IProject project, String builderId) throws CoreException {
		IProjectDescription description = project.getDescription();
		ICommand[] commands = description.getBuildSpec();
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(builderId)) {
				ICommand[] newCommands = new ICommand[commands.length - 1];
				System.arraycopy(commands, 0, newCommands, 0, i);
				System.arraycopy(commands, i + 1, newCommands, i, commands.length - i - 1);
				description.setBuildSpec(newCommands);
				project.setDescription(description, null);
				return;
			}
		}
	}
}
