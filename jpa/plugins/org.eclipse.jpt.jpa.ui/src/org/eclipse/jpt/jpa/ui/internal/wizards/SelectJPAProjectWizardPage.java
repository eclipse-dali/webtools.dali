/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java.JpaJavaGlobalMetadataConversionWizard;
import org.eclipse.jpt.jpa.ui.internal.wizards.gen.PromptJPAProjectWizardPage;
import org.eclipse.swt.widgets.TableItem;

public class SelectJPAProjectWizardPage extends PromptJPAProjectWizardPage {

	public SelectJPAProjectWizardPage( final String helpContextId ) {
		super(SELECT_PROJECT_PAGE_NAME);
	}
	
	@Override
	protected void handleJpaProjectSelection() {
		if (projTable.getSelectionIndex() != -1) {
			TableItem item =  projTable.getItem(projTable.getSelectionIndex());
			String projName = item.getText(0);
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
			JpaProject jpaProject = (JpaProject)project.getAdapter(JpaProject.class);
			((JpaJavaGlobalMetadataConversionWizard)getWizard()).setJpaProject(jpaProject);
			validate();
		}
	}
}
