/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * See org.eclipse.jpt.jpa.ui/plugin.xml
 */
public class GenerateEntitiesHandler extends AbstractHandler
{
	public Object execute(ExecutionEvent event) throws ExecutionException {
		JpaProject jpaProject = null;
		ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
		
		if (selection instanceof IStructuredSelection) {
			Object selectedObject = ((IStructuredSelection) selection).getFirstElement();
			jpaProject = this.jpaProjectFromSelection(selectedObject);
		} 
		if (jpaProject != null) {
			this.generateEntities(jpaProject, (IStructuredSelection) selection);
		}	
		return null;
	}
	
	protected void generateEntities(JpaProject project, IStructuredSelection selection) {
		JpaPlatformUi ui = this.getJpaPlatformUi(project);
		if (ui != null) {
			ui.generateEntities(project, selection);
		}
	}

	private JpaProject jpaProjectFromSelection(Object selectedObject) {
		IProject project = PlatformTools.getAdapter(selectedObject, IProject.class);
		return project == null ? null : PlatformTools.getAdapter(project, JpaProject.class);
	}

	private JpaPlatformUi getJpaPlatformUi(JpaProject project) {
        return (JpaPlatformUi) project.getJpaPlatform().getAdapter(JpaPlatformUi.class);
	}
}
