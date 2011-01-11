/*******************************************************************************
 *  Copyright (c) 2007, 2011 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.navigator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.platform.JaxbPlatformUi;
import org.eclipse.jpt.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonContentProvider;

/**
 * This extension of navigator content provider delegates to the platform UI
 * (see the org.eclipse.jpt.ui.jpaPlatform extension point) for navigator content.
 * 
 * If there is a platform UI for the given project, this content provider will
 * provide a root "JPA Content" node (child of the project), otherwise there
 * will be no content.  For children of the "JPA Content" node (or for any other
 * sub-node), this provider will delegate to the content provider returned by the 
 * platform UI implementation.
 */
public class JaxbNavigatorContentProvider
		implements ICommonContentProvider {
	
	private JaxbNavigatorContentAndLabelProvider delegate;
	
	private final CollectionChangeListener jaxbProjectListener;
	
	private StructuredViewer viewer;
	
	
	public JaxbNavigatorContentProvider() {
		super();
		this.jaxbProjectListener = this.buildJaxbProjectListener();
		JptJaxbCorePlugin.getProjectManager().addCollectionChangeListener(JaxbProjectManager.JAXB_PROJECTS_COLLECTION, this.jaxbProjectListener);
	}
	
	protected CollectionChangeListener buildJaxbProjectListener() {
		return new JaxbProjectListener();
	}

	public JaxbNavigatorContentAndLabelProvider getDelegate() {
		return this.delegate;
	}
	
	
	// **************** IContentProvider implementation ************************
	
	public void dispose() {
		JptJaxbCorePlugin.getProjectManager().removeCollectionChangeListener(JaxbProjectManager.JAXB_PROJECTS_COLLECTION, this.jaxbProjectListener);
		if (this.delegate != null) {
			this.delegate.dispose();
		}
	}
	
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (this.delegate != null) {
			this.delegate.inputChanged(viewer, oldInput, newInput);
		}
		this.viewer = (StructuredViewer) viewer;
	}
	
	
	// **************** IStructuredContentProvider implementation **************
	
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}
	
	
	// **************** ITreeContentProvider implementation ********************
	
	public Object getParent(Object element) {
		if (this.delegate != null) {
			return this.delegate.getParent(element);
		}
		
		return null;
	}
	
	public boolean hasChildren(Object element) {
		if (element instanceof IAdaptable) {
			IProject project = (IProject) ((IAdaptable) element).getAdapter(IProject.class);
			
			if (project != null) {
				JaxbProject jaxbProject = JptJaxbCorePlugin.getJaxbProject(project);
				if (jaxbProject != null) {
					JaxbPlatformDescription desc = jaxbProject.getPlatform().getDescription();
					JaxbPlatformUi platformUi = 
							JptJaxbUiPlugin.getJaxbPlatformUiManager().getJaxbPlatformUi(desc);
					
					return platformUi != null;
				}	
			}
		}
		
		if (this.delegate != null) {
			return this.delegate.hasChildren(element);
		}
		
		return false;
	}
	
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IAdaptable) {
			IProject project = (IProject) ((IAdaptable) parentElement).getAdapter(IProject.class);
			
			if (project != null) {
				JaxbProject jaxbProject = JptJaxbCorePlugin.getJaxbProject(project);
				if (jaxbProject != null) {
					JaxbPlatformDescription desc = jaxbProject.getPlatform().getDescription();
					JaxbPlatformUi platformUi = 
							JptJaxbUiPlugin.getJaxbPlatformUiManager().getJaxbPlatformUi(desc);
					
					if (platformUi != null) {
						return new Object[] {jaxbProject.getContextRoot()};
					}
				}	
			}
		}
		
		if (this.delegate != null) {
			return this.delegate.getChildren(parentElement);
		}
			
		return new Object[0];
	}
	
	
	// **************** IMementoAware implementation ***************************
	
	public void saveState(IMemento memento) {
		// no op
	}
	
	public void restoreState(IMemento memento) {
		// no op
	}
	
	
	// **************** ICommonContentProvider implementation ******************
	
	public void init(ICommonContentExtensionSite config) {
		if (this.delegate == null) {
			JaxbNavigatorLabelProvider labelProvider = (JaxbNavigatorLabelProvider) config.getExtension().getLabelProvider();
			if (labelProvider != null && labelProvider.getDelegate() != null) {
				this.delegate = labelProvider.getDelegate();
			}
			else {
				this.delegate = new JaxbNavigatorContentAndLabelProvider();
			}
		}
	}
	
	
	// **************** member classes *****************************************
	
	private class JaxbProjectListener
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
				if (project != null) {
					refreshJob.setRule(project);
				}
				refreshJob.schedule();
			}
		}
	}
}
