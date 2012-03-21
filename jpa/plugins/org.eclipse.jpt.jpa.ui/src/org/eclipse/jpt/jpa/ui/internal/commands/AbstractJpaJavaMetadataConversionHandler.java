/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractJpaJavaMetadataConversionHandler extends AbstractHandler {

	public AbstractJpaJavaMetadataConversionHandler() {
		super();
	}


	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selections = (IStructuredSelection)
				HandlerUtil.getCurrentSelectionChecked(event);
		
		Object selection = selections.iterator().next();
		JpaProject jpaProject = getJpaProjectFromSelection(selection);
		if (jpaProject != null) {
			converterJavaGlobalMetadata(jpaProject);
		}
		return null;
	}


	abstract protected void converterJavaGlobalMetadata(JpaProject jpaProject);

	protected JpaPlatformUi getJpaPlatformUi(JpaProject project) {
		String coreJpaPlatformId = project.getJpaPlatform().getId();
		return JpaPlatformUiRegistry.instance().getJpaPlatformUi(coreJpaPlatformId);
	}

	protected JpaProject getJpaProjectFromSelection(Object selection) {
		IProject project = getIProjectFromSelection(selection);
		if (project != null) {
			return (JpaProject) project.getAdapter(JpaProject.class);
		}
		return null;
	}

	protected IProject getIProjectFromSelection(Object selection) {
		if (selection instanceof IProject) {
			return (IProject) selection;
		}
		if (selection instanceof IJavaProject) {
			return ((IJavaProject) selection).getProject();
		}
		return null;
	}
}
