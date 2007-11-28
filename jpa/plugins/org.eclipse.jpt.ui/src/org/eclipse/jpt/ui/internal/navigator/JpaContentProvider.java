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

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.ui.internal.IJpaPlatformUi;
import org.eclipse.jpt.ui.internal.JptUiPlugin;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonContentProvider;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;
import org.eclipse.wst.common.project.facet.core.events.IProjectFacetActionEvent;

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
public class JpaContentProvider
	implements ICommonContentProvider
{
	private IFacetedProjectListener facetListener;
	
	private StructuredViewer viewer;
	
	/**
	 * Exactly *one* of these is created for each view that utilizes it.  Therefore, 
	 * as we delegate to the platform UI for each project, we should maintain the 
	 * same multiplicity.  That is, if there is a delegate for each platform UI, we 
	 * should maintain *one* delegate for each view.
	 * 
	 * Key: platform id,  Value: delegate content provider
	 */
	private Map<String, ICommonContentProvider> delegateContentProviders;
	
	
	public JpaContentProvider() {
		super();
		delegateContentProviders = new HashMap<String, ICommonContentProvider>();
		facetListener = new FacetListener();
		FacetedProjectFramework.addListener(
				facetListener, 
				IFacetedProjectEvent.Type.POST_INSTALL,
				IFacetedProjectEvent.Type.POST_UNINSTALL,
				IFacetedProjectEvent.Type.PROJECT_MODIFIED);
	}
	
	
	// **************** IContentProvider implementation ************************
	
	public void dispose() {
		FacetedProjectFramework.removeListener(facetListener);
		for (ICommonContentProvider delegate : delegateContentProviders.values()) {
			delegate.dispose();
		}
		delegateContentProviders.clear();
	}
	
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// should always be a StructuredViewer
		this.viewer = (StructuredViewer) viewer;
		for (ICommonContentProvider delegate : delegateContentProviders.values()) {
			delegate.inputChanged(viewer, oldInput, newInput);
		}
	}
	
	
	// **************** IStructuredContentProvider implementation **************
	
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}
	
	
	// **************** ITreeContentProvider implementation ********************
	
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean hasChildren(Object element) {
		return true;
	}
	
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IAdaptable) {
			IProject project = (IProject) ((IAdaptable) parentElement).getAdapter(IProject.class);
			
			if (project != null) {
				IJpaProject jpaProject = JptCorePlugin.jpaProject(project);
				if (jpaProject != null) {
					IJpaPlatformUi platformUi = JptUiPlugin.getPlugin().jpaPlatformUi(jpaProject.jpaPlatform());
					
					if (platformUi != null) {
						return new Object[] {jpaProject.contextModel()};
					}
				}	
			}
			
			IJpaContextNode contextNode = (IJpaContextNode) ((IAdaptable) parentElement).getAdapter(IJpaContextNode.class);
			
			if (contextNode != null) {
				ICommonContentProvider delegate = getDelegate(contextNode);
				
				if (delegate != null) {
					return delegate.getChildren(parentElement);
				}
			}
		}
		
		return new Object[0];
	}
	
	
	// **************** IMementoAware implementation ***************************
	
	public void saveState(IMemento memento) {
		for (ICommonContentProvider delegate : delegateContentProviders.values()) {
			delegate.saveState(memento);
		}
	}
	
	public void restoreState(IMemento memento) {
		for (ICommonContentProvider delegate : delegateContentProviders.values()) {
			delegate.restoreState(memento);
		}
	}
	
	
	// **************** ICommonContentProvider implementation ******************
	
	public void init(ICommonContentExtensionSite config) {
		for (ICommonContentProvider delegate : delegateContentProviders.values()) {
			delegate.init(config);
		}
	}
	
	
	// *************** internal ************************************************
	
	private ICommonContentProvider getDelegate(IJpaContextNode contextNode) {
		IJpaPlatform platform = contextNode.jpaProject().jpaPlatform();
		IJpaPlatformUi platformUi = JptUiPlugin.getPlugin().jpaPlatformUi(platform);
		
		ICommonContentProvider delegate = delegateContentProviders.get(platform.getId());
		
		if (delegate == null && platform != null && ! delegateContentProviders.containsKey(platform.getId())) {
			delegate = platformUi.buildNavigatorContentProvider();
			delegateContentProviders.put(platform.getId(), delegate);
		}
		
		return delegate;
	}
	
	
	// **************** member classes *****************************************
	
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
