/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.general;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.common.core.internal.utility.ProjectTools;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.internal.persistence.ArchiveFileSelectionDialog;
import org.eclipse.jpt.jpa.ui.internal.persistence.ArchiveFileSelectionDialog.DeploymentPathCalculator;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceUnitJarFilesComposite;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkPersistenceUnitJarFilesComposite
	extends PersistenceUnitJarFilesComposite
{
	public EclipseLinkPersistenceUnitJarFilesComposite(
			Pane<? extends PersistenceUnit> parent,
			Composite parentComposite) {
		super(parent, parentComposite);
	}
	
	
	@Override
	protected DeploymentPathCalculator buildJarFileDeploymentPathCalculator() {
		return new EclipseLinkDeploymentPathCalculator();
	}
	
	
	static class EclipseLinkDeploymentPathCalculator
		extends ArchiveFileSelectionDialog.ModuleDeploymentPathCalculator
	{
		@Override
		public String calculateDeploymentPath(IFile file) {
			String path = super.calculateDeploymentPath(file);
			if (ProjectTools.hasWebFacet(file.getProject())) {
				path = "../" + path; //$NON-NLS-1$
			}
			return path;
		}
	}
}
