/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.perspective;

import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jpt.jpa.ui.internal.views.JpaDetailsView;
import org.eclipse.jpt.jpa.ui.internal.views.JpaStructureView;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.progress.IProgressConstants;

public class JpaPerspectiveFactory
	implements IPerspectiveFactory
{
	/**
	 * Perspective ID specified in <code>plugin.xml</code>.
	 */
	public static final String ID = "org.eclipse.jpt.ui.jpaPerspective"; //$NON-NLS-1$

	private static final String DATABASE_EXPLORER_VIEW_ID = "org.eclipse.datatools.connectivity.DataSourceExplorerNavigator"; //$NON-NLS-1$

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();

		// Project Explorer
		IFolderLayout folder = layout.createFolder("left", IPageLayout.LEFT, (float) 0.25, editorArea); //$NON-NLS-1$
		folder.addView(ProjectExplorer.VIEW_ID);
		folder.addPlaceholder(JavaUI.ID_TYPE_HIERARCHY);
//		folder.addPlaceholder(IPageLayout.ID_RES_NAV);
		folder.addPlaceholder(IPageLayout.ID_PROJECT_EXPLORER);

		// Data Source Explorer
		layout.addView(DATABASE_EXPLORER_VIEW_ID, IPageLayout.BOTTOM, (float) 0.60, ProjectExplorer.VIEW_ID);

		// Problems
		IFolderLayout outputFolder = layout.createFolder("bottom", IPageLayout.BOTTOM, (float) 0.60, editorArea); //$NON-NLS-1$
		outputFolder.addView(IPageLayout.ID_PROBLEM_VIEW);
		outputFolder.addPlaceholder(IPageLayout.ID_BOOKMARKS);
		outputFolder.addPlaceholder(IProgressConstants.PROGRESS_VIEW_ID);

		// JPA Details (split with Problems)
		layout.addView(JpaDetailsView.ID, IPageLayout.RIGHT, (float) 0.60, "bottom"); //$NON-NLS-1$
		
		// JPA Structure
		IFolderLayout outlineFolder = layout.createFolder("right", IPageLayout.RIGHT, (float) 0.75, editorArea); //$NON-NLS-1$
		outlineFolder.addView(JpaStructureView.ID);
		outlineFolder.addView(IPageLayout.ID_OUTLINE);
	}
}
