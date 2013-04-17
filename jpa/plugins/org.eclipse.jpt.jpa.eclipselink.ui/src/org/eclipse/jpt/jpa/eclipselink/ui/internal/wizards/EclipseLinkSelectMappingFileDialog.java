/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.jpa.ui.internal.wizards.SelectMappingFileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Only EclipseLink mapping file will show up on EclipseLink platform with this dialog
 */
public class EclipseLinkSelectMappingFileDialog
	extends SelectMappingFileDialog
{
	public EclipseLinkSelectMappingFileDialog(Shell parent, IProject project) {
		super(parent, project);
	}

	public EclipseLinkSelectMappingFileDialog(Shell parent, IProject project, ILabelProvider labelProvider, ITreeContentProvider contentProvider) {
		super(parent, project, labelProvider, contentProvider);
	}

	@Override
	protected void openNewMappingFileWizard() {
		IPath path = EmbeddedEclipseLinkMappingFileWizard.createNewMappingFile(new StructuredSelection(super.project));
		this.updateDialog(path);
	}
}
