/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.navigator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.ui.internal.jface.NavigatorContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProviderFactory;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProviderFactory;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.ui.JaxbWorkbench;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUi;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * This extension of navigator content provider delegates to the platform UI
 * (see the org.eclipse.jpt.jaxb.ui.jaxbPlatformUis extension point) for navigator content.
 * 
 * If there is a platform UI for the given project, this content provider will
 * provide a root "JAXB Content" node (child of the project), otherwise there
 * will be no content.  For children of the "JAXB Content" node (or for any other
 * sub-node), this provider will delegate to the content provider returned by the 
 * platform UI implementation.
 */
public class JaxbNavigatorContentProvider
	extends NavigatorContentProvider
{
	private final JaxbProjectManager jaxbProjectManager;
	private final CollectionChangeListener jaxbProjectListener;
	
	private StructuredViewer viewer;
	
	
	public JaxbNavigatorContentProvider() {
		super();
		this.jaxbProjectListener = this.buildJaxbProjectListener();
		this.jaxbProjectManager = this.getJaxbProjectManager();
		if (this.jaxbProjectManager != null) {
			this.jaxbProjectManager.addCollectionChangeListener(JaxbProjectManager.JAXB_PROJECTS_COLLECTION, this.jaxbProjectListener);
		}
	}
	
	protected CollectionChangeListener buildJaxbProjectListener() {
		return new JaxbProjectListener();
	}

	@Override
	protected ItemTreeContentProviderFactory buildItemContentProviderFactory() {
		return new JaxbNavigatorTreeItemContentProviderFactory();
	}

	@Override
	protected ItemExtendedLabelProviderFactory buildItemLabelProviderFactory() {
		return new JaxbNavigatorItemLabelProviderFactory();
	}

	@Override
	protected ResourceManager buildResourceManager() {
		return new LocalResourceManager(this.getParentResourceManager());
	}

	protected ResourceManager getParentResourceManager() {
		return JFaceResources.getResources();
	}

	@Override
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		super.inputChanged(v, oldInput, newInput);
		this.viewer = (StructuredViewer) v;
	}
	
	@Override
	protected boolean hasChildren_(Object element) {
		if (element instanceof IAdaptable) {
			IProject project = (IProject) ((IAdaptable) element).getAdapter(IProject.class);
			
			if (project != null) {
				JaxbProject jaxbProject = this.getJaxbProject(project);
				if (jaxbProject != null) {
					JaxbPlatformUi platformUi = (JaxbPlatformUi) jaxbProject.getPlatform().getAdapter(JaxbPlatformUi.class);
					return platformUi != null;
				}	
			}
		}
		return false;
	}

	protected JaxbProject getJaxbProject(IProject project) {
		return (this.jaxbProjectManager == null) ? null : this.jaxbProjectManager.getJaxbProject(project);
	}

	@Override
	protected Object[] getChildren_(Object parentElement) {
		if (parentElement instanceof IAdaptable) {
			IProject project = (IProject) ((IAdaptable) parentElement).getAdapter(IProject.class);
			
			if (project != null) {
				JaxbProject jaxbProject = this.getJaxbProject(project);
				if (jaxbProject != null) {
					JaxbPlatformUi platformUi =  (JaxbPlatformUi) jaxbProject.getPlatform().getAdapter(JaxbPlatformUi.class);
					if (platformUi != null) {
						return new Object[] {jaxbProject.getContextRoot()};
					}
				}	
			}
		}
		return null;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if (this.jaxbProjectManager != null) {
			this.jaxbProjectManager.removeCollectionChangeListener(JaxbProjectManager.JAXB_PROJECTS_COLLECTION, this.jaxbProjectListener);
		}
	}
	
	private JaxbProjectManager getJaxbProjectManager() {
		JaxbWorkspace jaxbWorkspace = this.getJaxbWorkspace();
		return (jaxbWorkspace == null) ? null : jaxbWorkspace.getJaxbProjectManager();
	}

	private JaxbWorkspace getJaxbWorkspace() {
		JaxbWorkbench jaxbWorkbench = this.getJaxbWorkbench();
		return (jaxbWorkbench == null) ? null : jaxbWorkbench.getJaxbWorkspace();
	}

	private JaxbWorkbench getJaxbWorkbench() {
		return PlatformTools.getAdapter(this.getWorkbench(), JaxbWorkbench.class);
	}

	private IWorkbench getWorkbench() {
		return PlatformUI.getWorkbench();
	}

	
	// **************** member classes *****************************************
	
	/* CU private */ class JaxbProjectListener
		implements CollectionChangeListener
	{
		public void collectionChanged(CollectionChangeEvent event) {
			this.refreshViewer(null);
		}
		public void collectionCleared(CollectionClearEvent event) {
			this.refreshViewer(null);
		}
		public void itemsAdded(CollectionAddEvent event) {
			for (Object item : event.getItems()) {
				this.refreshViewer(((JaxbProject) item).getProject());
			}
		}
		public void itemsRemoved(CollectionRemoveEvent event) {
			for (Object item : event.getItems()) {
				this.refreshViewer(((JaxbProject) item).getProject());
			}
		}
		
		private void refreshViewer(final IProject project) {
			if (viewer != null 
					&& viewer.getControl() != null 
					&& !viewer.getControl().isDisposed()) {
				// Using job here so that project model update (which also uses
				//  a job) will complete first
				Job refreshJob = new Job("Refresh viewer") {
					@Override
					protected IStatus run(IProgressMonitor monitor) {
						// Using runnable here so that refresh will go on correct thread
						viewer.getControl().getDisplay().asyncExec(new Runnable() {
							public void run() {
								if (project != null) {
									viewer.refresh(project);
								}
								else {
									viewer.refresh();
								}
							}
						});
						return Status.OK_STATUS;
					}
				};
				refreshJob.setRule(project);
				refreshJob.schedule();
			}
		}
	}
}
