/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * See org.eclipse.jpt.jpa.ui/plugin.xml
 */
public class GenerateDDLHandler extends AbstractHandler
{
	public Object execute(ExecutionEvent event) throws ExecutionException {
		JpaProject jpaProject = null;
		ISelection selection = HandlerUtil.getCurrentSelectionChecked(event);
		
		if(selection instanceof IStructuredSelection) {
			Object selectedObject = ((IStructuredSelection) selection).getFirstElement();
			jpaProject = this.adaptSelection(selectedObject);
		} 
		if(jpaProject != null) {
			this.generateDDL(jpaProject, (IStructuredSelection)selection);
		}	
		return null;
	}
	
	protected void generateDDL(JpaProject project, IStructuredSelection selection) {
        this.getJpaPlatformUi(project).generateDDL(project, selection);
	}

	private JpaProject adaptSelection(Object selectedObject) {
		return PlatformTools.getAdapter(selectedObject, JpaProject.class);
	}

	private JpaPlatformUi getJpaPlatformUi(JpaProject project) {
        return (JpaPlatformUi) project.getJpaPlatform().getAdapter(JpaPlatformUi.class);
	}
}
