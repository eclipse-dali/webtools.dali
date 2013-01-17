/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractJavaMetadataConversionHandler
	extends AbstractHandler
{
	public AbstractJavaMetadataConversionHandler() {
		super();
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		this.execute_(event);
		return null;
	}

	protected void execute_(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelectionChecked(event);
		JpaProject jpaProject = this.convertSelectionToJpaProject(selection.getFirstElement());
		if (jpaProject != null) {
			JpaPlatformUi ui = this.getJpaPlatformUi(jpaProject);
			if (ui != null) {
				this.convertJavaMetadata(ui, jpaProject);
			}
		}
	}

	protected abstract void convertJavaMetadata(JpaPlatformUi ui, JpaProject jpaProject);

	protected JpaProject convertSelectionToJpaProject(Object sel) {
		try {
			return (sel == null) ? null : this.convertSelectionToJpaProject_(sel);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			return null;
		}
	}

	protected JpaProject convertSelectionToJpaProject_(Object sel) throws InterruptedException {
		// this will handle JpaProject and IProject (when the JpaProject is already present)
		JpaProject jpaProject = PlatformTools.getAdapter(sel, JpaProject.class);
		if (jpaProject != null) {
			return jpaProject;
		}
		IProject project = this.convertSelectionToProject(sel);
		if (project == null) {
			return null;
		}
		JpaProject.Reference ref = (JpaProject.Reference) project.getAdapter(JpaProject.Reference.class);
		// this will wait for the JpaProject if it is under construction
		return (ref == null) ? null : ref.getValue();
	}

	protected IProject convertSelectionToProject(Object sel) {
		// this will handle JpaProject and IProject
		IResource resource = PlatformTools.getAdapter(sel, IResource.class);
		if (resource != null) {
			return resource.getProject();
		}

		IJavaProject javaProject = this.convertSelectionToJavaProject(sel);
		return (javaProject == null) ? null : javaProject.getProject();
	}

	protected IJavaProject convertSelectionToJavaProject(Object sel) {
		IJavaElement javaElement = PlatformTools.getAdapter(sel, IJavaElement.class);
		return (javaElement == null) ? null : javaElement.getJavaProject();
	}

	private JpaPlatformUi getJpaPlatformUi(JpaProject jpaProject) {
		return (JpaPlatformUi) jpaProject.getJpaPlatform().getAdapter(JpaPlatformUi.class);
	}
}
