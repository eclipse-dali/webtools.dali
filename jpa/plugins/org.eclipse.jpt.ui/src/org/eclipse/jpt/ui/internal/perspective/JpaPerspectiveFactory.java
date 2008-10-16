/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/        
package org.eclipse.jpt.ui.internal.perspective;

import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.progress.IProgressConstants;

public class JpaPerspectiveFactory implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();

		//Package area
		IFolderLayout folder = layout.createFolder(
				"left", IPageLayout.LEFT, (float) 0.25, editorArea); //$NON-NLS-1$
		folder.addView(ProjectExplorer.VIEW_ID);
		folder.addPlaceholder(JavaUI.ID_TYPE_HIERARCHY);
		folder.addPlaceholder(IPageLayout.ID_RES_NAV);

		//Database Explorer area
		layout.addView("org.eclipse.datatools.connectivity.DataSourceExplorerNavigator", //$NON-NLS-1$
			IPageLayout.BOTTOM, (float) 0.60, ProjectExplorer.VIEW_ID);

		//Problems/Console area
		IFolderLayout outputFolder = layout.createFolder(
				"bottom", IPageLayout.BOTTOM, (float) 0.60, editorArea); //$NON-NLS-1$
		outputFolder.addView(IPageLayout.ID_PROBLEM_VIEW);
		outputFolder.addPlaceholder(IPageLayout.ID_BOOKMARKS);
		outputFolder.addPlaceholder(IProgressConstants.PROGRESS_VIEW_ID);

		//JPA Details (Split with Problems/Console area)
		layout.addView("org.eclipse.jpt.ui.jpaDetailsView", //$NON-NLS-1$
				IPageLayout.RIGHT, (float) .60, "bottom"); //$NON-NLS-1$
		
		//JPA Structure area
		IFolderLayout outlineFolder = layout.createFolder(
				"right", IPageLayout.RIGHT, (float) 0.75, editorArea); //$NON-NLS-1$
		outlineFolder.addView("org.eclipse.jpt.ui.jpaStructureView"); //$NON-NLS-1$
		outlineFolder.addView(IPageLayout.ID_OUTLINE);
	}
}
