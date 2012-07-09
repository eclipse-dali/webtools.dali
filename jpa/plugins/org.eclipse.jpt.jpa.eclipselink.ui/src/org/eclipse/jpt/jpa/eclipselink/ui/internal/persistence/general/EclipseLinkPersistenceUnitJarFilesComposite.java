/*******************************************************************************
 *  Copyright (c) 2009, 2012  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.general;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.internal.persistence.ArchiveFileSelectionDialog;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceUnitJarFilesComposite;
import org.eclipse.jpt.jpa.ui.internal.persistence.ArchiveFileSelectionDialog.DeploymentPathCalculator;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkPersistenceUnitJarFilesComposite
	extends PersistenceUnitJarFilesComposite
{
	public EclipseLinkPersistenceUnitJarFilesComposite(
			Pane<? extends PersistenceUnit> parentPane,
			Composite parent) {

		super(parentPane, parent);
	}
	
	
	@Override
	protected DeploymentPathCalculator buildJarFileDeploymentPathCalculator() {
		return new EclipseLinkDeploymentPathCalculator();
	}
	
	
	private static class EclipseLinkDeploymentPathCalculator
		extends ArchiveFileSelectionDialog.ModuleDeploymentPathCalculator
	{
		@Override
		public String calculateDeploymentPath(IFile file) {
			String baseDeploymentPath = super.calculateDeploymentPath(file);
			if (JptJpaCorePlugin.projectHasWebFacet(file.getProject())) {
				return "../" + baseDeploymentPath;
			}
			return baseDeploymentPath;
		}
	}
}
