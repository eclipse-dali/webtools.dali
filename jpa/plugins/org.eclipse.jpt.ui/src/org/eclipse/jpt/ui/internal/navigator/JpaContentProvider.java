/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.navigator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.IBaseJpaContent;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;
import org.eclipse.wst.common.project.facet.core.events.IProjectFacetActionEvent;

public class JpaContentProvider
	implements ITreeContentProvider
{
	private IFacetedProjectListener facetListener;
	
	private StructuredViewer viewer;
	
	
	// TODO - create delegate content provider that depends on platform UI
	public JpaContentProvider() {
		super();
		facetListener = new FacetListener();
		FacetedProjectFramework.addListener(
				facetListener, 
				IFacetedProjectEvent.Type.POST_INSTALL,
				IFacetedProjectEvent.Type.POST_UNINSTALL,
				IFacetedProjectEvent.Type.PROJECT_MODIFIED);
	}
	
	/**
	 * @see IContentProvider#dispose()
	 */
	public void dispose() {
		FacetedProjectFramework.removeListener(facetListener); 
	}
	
	/**
	 * @see IContentProvider#inputChanged(Viewer, Object, Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// should always be a StructuredViewer
		this.viewer = (StructuredViewer) viewer;
	}
	
	/**
	 * @see ITreeContentProvider#getParent(Object)
	 */
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see ITreeContentProvider#hasChildren(Object)
	 */
	public boolean hasChildren(Object element) {
		return true;
	}
	
	/** 
	 * @see ITreeContentProvider#getChildren(Object)
	 */
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IAdaptable) {
			IProject project = (IProject) ((IAdaptable) parentElement).getAdapter(IProject.class);
			
			if (project != null) {
				IJpaProject jpaProject = JptCorePlugin.jpaProject(project);
				
				if (jpaProject != null) {
					return new Object[] {jpaProject.contextModel()};
				}
			}
		}
		
		if (parentElement instanceof IBaseJpaContent) {
			IBaseJpaContent baseJpaContent = (IBaseJpaContent) parentElement;
			if (baseJpaContent.getPersistenceXml() != null) {
				return new Object[] {baseJpaContent.getPersistenceXml()};
			}
		}
		
		return new Object[0];
	}
	
	/**
	 * @see IStructuredContentProvider#getElements(Object)
	 */
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}
	
	
	private class FacetListener
		implements IFacetedProjectListener
	{
		public void handleEvent(IFacetedProjectEvent event) {
			if (event.getType() == IFacetedProjectEvent.Type.PROJECT_MODIFIED) {
				refreshViewer(event.getProject().getProject());
			}
			else if (event.getType() == IFacetedProjectEvent.Type.POST_INSTALL
					|| event.getType() == IFacetedProjectEvent.Type.POST_UNINSTALL) {
				IProjectFacetActionEvent ipaEvent = (IProjectFacetActionEvent) event;
				if (ipaEvent.getProjectFacet().equals(
						ProjectFacetsManager.getProjectFacet(JptCorePlugin.FACET_ID))) {
					refreshViewer(ipaEvent.getProject().getProject());
				}
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
								viewer.refresh(project);
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
