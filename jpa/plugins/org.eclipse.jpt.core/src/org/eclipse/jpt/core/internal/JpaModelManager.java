/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.internal.prefs.JpaPreferenceConstants;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;

public class JpaModelManager
{
	final JpaModel model;
	
	private final IResourceChangeListener resourceChangeListener;
	
	private final IFacetedProjectListener facetedProjectListener;
	
	private final IElementChangedListener elementChangeListener;
	
	private final IPropertyChangeListener preferencesListener;
	

	private static JpaModelManager INSTANCE;
	
	/**
	 * Returns the singleton JpaModelManager
	 */
	public final static JpaModelManager instance() {
		if (INSTANCE == null) {
			INSTANCE = new JpaModelManager();
		}
		return INSTANCE;
	}
	
	
	private JpaModelManager() {
		super();
		model = JpaCoreFactory.eINSTANCE.createJpaModel();
		resourceChangeListener = new ResourceChangeListener();
		facetedProjectListener = new FacetedProjectListener();
		elementChangeListener = new ElementChangeListener();
		preferencesListener = new PreferencesListener();
	}
	
	void start() {
		try {
			this.buildWorkspace();
			ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener);
			FacetedProjectFramework.addListener(facetedProjectListener, IFacetedProjectEvent.Type.values());
			JavaCore.addElementChangedListener(elementChangeListener);
			JptCorePlugin.getPlugin().getPluginPreferences().addPropertyChangeListener(preferencesListener);
		}
		catch (RuntimeException ex) {
			JptCorePlugin.log(ex);
			this.stop();
		}
	}
	
	void stop() {
		JptCorePlugin.getPlugin().getPluginPreferences().removePropertyChangeListener(preferencesListener);
		JavaCore.removeElementChangedListener(elementChangeListener);
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);
		model.dispose();
	}
	
	private void buildWorkspace() {
		Job workspaceBuildJob = new WorkspaceBuildJob();
		workspaceBuildJob.schedule(5000L);  //temporary delay for bundle init problem
	}
	
	/**
	 * Return the workspace-wide IJpaModel
	 * 
	 * This IJpaProject may not be fully filled (it may not have all the correct
	 * projects added) if retrieved soon after it is created (e.g. workspace opening, 
	 * project opening, facet installation ...)  To ensure it is fully filled in 
	 * those cases, you may instead use getFilledJpaModel().
	 * @see getFilledJpaModel()
	 */
	public IJpaModel getJpaModel() {
		return model;
	}
	
	/**
	 * Return the workspace-wide IJpaModel
	 * 
	 * This IJpaModel will be fully filled (it will have all the correct projects added).
	 * @see getJpaProject(IProject)
	 */
	public IJpaModel getFilledJpaModel() 
			throws CoreException {
		model.fill();
		return model;
	}
	
	/** 
	 * Returns the IJpaProject corresponding to the given IProject.
	 * Returns <code>null</code> if unable to associate the given project
	 * with an IJpaProject.
	 * 
	 * This IJpaProject may not be fully filled (it may not have all the correct
	 * files added) if retrieved soon after it is created (e.g. workspace opening, 
	 * project opening, facet installation ...)  To ensure it is fully filled in 
	 * those cases, you may instead use getFilledJpaProject(IProject).
	 * @see getFilledJpaProject(IProject)
	 */
	public synchronized IJpaProject getJpaProject(IProject project) {
		if (project == null) {
			return null;
		}
		
		return this.model.getJpaProject(project);
	}
	
	/**
	 * Returns the IJpaProject corresponding to the given IProject.
	 * Returns <code>null</code> if unable to associate the given project
	 * with an IJpaProject.
	 * 
	 * This IJpaProject will be fully filled (it will have all the correct files added).
	 * @see getJpaProject(IProject)
	 */
	public synchronized IJpaProject getFilledJpaProject(IProject project) 
			throws CoreException {
		JpaProject jpaProject = (JpaProject) getJpaProject(project);
		
		if (jpaProject != null) {
			jpaProject.fill();
		}
		
		return jpaProject;
	}
	
	/**
	 * INTERNAL ONLY
	 * 
	 * Fills the IJpaProject associated with the IProject, if it exists
	 */
	public synchronized void fillJpaProject(IProject project) 
			throws CoreException {
		JpaProject jpaProject = (JpaProject) getJpaProject(project);
		
		if (jpaProject != null) {
			jpaProject.fill();
		}
	}
	
	/**
	 * INTERNAL ONLY
	 * Create an IJpaProject without files filled in
	 */
	public synchronized IJpaProject createJpaProject(IProject project) 
			throws CoreException {
		if (FacetedProjectFramework.hasProjectFacet(project, JptCorePlugin.FACET_ID)) {
			JpaProject jpaProject = JpaCoreFactory.eINSTANCE.createJpaProject(project);
			model.getProjects().add(jpaProject);
			return jpaProject;
		}
		return null;
	}
	
	/**
	 * INTERNAL ONLY
	 * Create an IJpaProject with files filled in
	 */
	public synchronized IJpaProject createFilledJpaProject(IProject project) 
			throws CoreException {
		JpaProject jpaProject = (JpaProject) createJpaProject(project);
		
		if (jpaProject != null) {
			jpaProject.fill();
		}
		
		return jpaProject;
	}
	
	/**
	 * INTERNAL ONLY
	 * Dispose the IJpaProject
	 */
	public void disposeJpaProject(IJpaProject jpaProject) {
		((JpaProject) jpaProject).dispose();
	}
	
	/**
	 * Returns the IJpaFile corresponding to the given IFile.
	 * Returns <code>null</code> if unable to associate the given file
	 * with an IJpaFile.
	 */
	public synchronized IJpaFile getJpaFile(IFile file) {
		if (file == null) {
			return null;
		}
		
		IProject project = file.getProject();
		JpaProject jpaProject = (JpaProject) this.getJpaProject(project);
		if (jpaProject == null) {
			return null;
		}
		
		return jpaProject.getJpaFile(file);
	}
	
	
	
	private class WorkspaceBuildJob extends Job
	{
		WorkspaceBuildJob() {
			// TODO - Internationalize (? It *is* a system job ...)
			super("Initializing JPA Model ...");
			setSystem(true);
			setPriority(SHORT);
		}
		
		@Override
		protected IStatus run(IProgressMonitor monitor) {
			final IWorkspace workspace = ResourcesPlugin.getWorkspace();
			
			try {
				workspace.run(
					new IWorkspaceRunnable() {
						public void run(IProgressMonitor progress) throws CoreException {
							model.fill();
						}
					},
					monitor);
			}
			catch (CoreException ce) {
				return ce.getStatus();
			}
			return Status.OK_STATUS;
		}
		
	}
	
	
	private static class ResourceChangeListener 
		implements IResourceChangeListener
	{
		ThreadLocal<ResourceChangeProcessor> resourceChangeProcessors = new ThreadLocal<ResourceChangeProcessor>();
		
		ResourceChangeListener() {
			super();
		}
		
		public void resourceChanged(IResourceChangeEvent event) {
			getResourceChangeProcessor().resourceChanged(event);
		}
		
		public ResourceChangeProcessor getResourceChangeProcessor() {
			ResourceChangeProcessor processor = this.resourceChangeProcessors.get();
			if (processor == null) { 
				processor = new ResourceChangeProcessor();
				this.resourceChangeProcessors.set(processor);
			}
			return processor;
		}
	}
	
	
	private static class ResourceChangeProcessor
	{
		private JpaModel model;
		
		ResourceChangeProcessor() {
			model = JpaModelManager.instance().model;
		}
		
		public void resourceChanged(IResourceChangeEvent event) {
			if (event.getSource() instanceof IWorkspace) {
				IResource resource = event.getResource();
				IResourceDelta delta = event.getDelta();
				
				switch (event.getType()){
					case IResourceChangeEvent.PRE_DELETE :
					case IResourceChangeEvent.PRE_CLOSE :
						try {
							if ((resource.getType() == IResource.PROJECT)
									&& (FacetedProjectFramework.hasProjectFacet(
											(IProject) resource, JptCorePlugin.FACET_ID))) {
								projectBeingDeleted((IProject) resource);
							}
						} 
						catch (CoreException e) {
							// project doesn't exist or is not open: ignore
						}
						return;
						
					case IResourceChangeEvent.POST_CHANGE :
						if (isApplicable(delta)) { // avoid changing due to SYNC or MARKER deltas
							checkForProjectsBeingAddedOrRemoved(delta);
							checkForFilesBeingAddedOrRemoved(delta);
						}
						return;
				}
			}
		}
		
		/**
		 * Process the given delta and look for files being added, removed, or changed
		 */
		private void checkForFilesBeingAddedOrRemoved(IResourceDelta delta) {
			IResource resource = delta.getResource();
			boolean processChildren = false;
	
			switch (resource.getType()) {
				case IResource.ROOT :
					processChildren = true;
					break;
				
				case IResource.PROJECT :
					IProject project = (IProject) resource;
					
					try {
						if (FacetedProjectFramework.hasProjectFacet(project, JptCorePlugin.FACET_ID)) {
							JpaProject jpaProject = (JpaProject) model.getJpaProject(project);
							if (jpaProject != null) {
								// sometimes we receive events before the project
								// has been fully initialized
								jpaProject.synchInternalResources(delta);
							}
						}
					}
					catch (CoreException ex) {
						// we can't do anything anyway
					}
					break;
			}
			if (processChildren) {
				IResourceDelta[] children = delta.getAffectedChildren();
				for (int i = 0; i < children.length; i++) {
					checkForFilesBeingAddedOrRemoved(children[i]);
				}
			}
			
		}
		
		/**
		 * Process the given delta and look for projects being added, opened, or closed.
		 * Note that projects being deleted are checked in deletingProject(IProject).
		 */
		private void checkForProjectsBeingAddedOrRemoved(IResourceDelta delta) {
			IResource resource = delta.getResource();
			boolean processChildren = false;
	
			switch (resource.getType()) {
				case IResource.ROOT :
					processChildren = true;
					break;
				
				case IResource.PROJECT :
					// NB: No need to check project's facet as if the project is not a jpa project:
					//     - if the project is added or changed this is a noop for projectsBeingDeleted
					//     - if the project is closed, it has already lost its jpa facet
					IProject project = (IProject) resource;
					
					// could be problems here ...
					JpaProject jpaProject = (JpaProject) model.getJpaProject(project);
					switch (delta.getKind()) {
						case IResourceDelta.REMOVED :
							// we should have already handled this in the PRE_DELETE event
							break;
						
						case IResourceDelta.ADDED :
							// if project is renamed (for instance, we should act as though it's been opened)
							// fall through
						
						case IResourceDelta.CHANGED : 
							if ((delta.getFlags() & IResourceDelta.OPEN) != 0) {
								if (project.isOpen()) {
									// project has been opened, but don't create it if it's already there 
									// (which can happen on project creation)
									if (jpaProject == null) {
										try {
											JpaModelManager.instance().createFilledJpaProject(project);
										}
										catch (CoreException ce) {
											JptCorePlugin.log(ce);
										}
									}
								}
							}		
							break;
					}
					break;
			}
			if (processChildren) {
				IResourceDelta[] children = delta.getAffectedChildren();
				for (int i = 0; i < children.length; i++) {
					checkForProjectsBeingAddedOrRemoved(children[i]);
				}
			}
		}
		
		/**
		 * The platform project is being deleted.  Remove jpa info.
		 */
		private void projectBeingDeleted(IProject project) {
			// could be problems here ...
			JpaProject jpaProject = (JpaProject) model.getJpaProject(project);
			jpaProject.dispose();
		}
		
		/**
		 * Returns whether a given delta contains some information relevant to 
		 * the JPA model,
		 * in particular it will not consider SYNC or MARKER only deltas.
		 */
		private boolean isApplicable(IResourceDelta rootDelta) {
			if (rootDelta != null) {
				// use local exception to quickly escape from delta traversal
				class FoundRelevantDeltaException extends RuntimeException {
					private static final long serialVersionUID = 7137113252936111022L; // backward compatible
					// only the class name is used (to differentiate from other RuntimeExceptions)
				}
				try {
					rootDelta.accept(
						new IResourceDeltaVisitor() {
							public boolean visit(IResourceDelta delta) {
								switch (delta.getKind()) {
									case IResourceDelta.ADDED :
									case IResourceDelta.REMOVED :
										throw new FoundRelevantDeltaException();
									case IResourceDelta.CHANGED :
										// if any flag is set but SYNC or MARKER, this delta should be considered
										if (delta.getAffectedChildren().length == 0 // only check leaf delta nodes
												&& (delta.getFlags() & ~(IResourceDelta.SYNC | IResourceDelta.MARKERS)) != 0) {
											throw new FoundRelevantDeltaException();
										}
								}
								return true;
							}
						}
					);
				} 
				catch(FoundRelevantDeltaException e) {
					return true;
				} 
				catch(CoreException e) { // ignore delta if not able to traverse
				}
			}
			return false;
		}
	}
	
	
	// **************** faceted project listener ******************************
	
	private static class FacetedProjectListener 
		implements IFacetedProjectListener
	{
		ThreadLocal<FacetedProjectChangeProcessor> processors = 
				new ThreadLocal<FacetedProjectChangeProcessor>();
		
		FacetedProjectListener() {
			super();
		}
		
		public void handleEvent(IFacetedProjectEvent event) {
			getProcessor().handleEvent(event);
		}
		
		public FacetedProjectChangeProcessor getProcessor() {
			FacetedProjectChangeProcessor processor = processors.get();
			if (processor == null) { 
				processor = new FacetedProjectChangeProcessor();
				processors.set(processor);
			}
			return processor;
		}
	}
	
	
	private static class FacetedProjectChangeProcessor
	{
		private JpaModel model;
		
		FacetedProjectChangeProcessor() {
			model = JpaModelManager.instance().model;
		}
		
		protected void handleEvent(IFacetedProjectEvent event) {
			IProject project = event.getProject().getProject();
			JpaProject jpaProject = (JpaProject) model.getJpaProject(project);
			boolean jpaFacetExists = false;
			try {
				jpaFacetExists = FacetedProjectFramework.hasProjectFacet(project, JptCorePlugin.FACET_ID);
			}
			catch (CoreException ce) {
				// nothing to do, assume facet doesn't exist
				JptCorePlugin.log(ce);
			}
			
			if (jpaFacetExists && jpaProject == null) {
				try {
					JpaModelManager.instance().createFilledJpaProject(project);
				}
				catch (CoreException ce) {
				// nothing to do, nothing we *can* do
				JptCorePlugin.log(ce);
				}
			}
			else if (jpaProject != null && ! jpaFacetExists) {
				jpaProject.dispose();
			}
		}
	}
	
	
	// **************** element change listener *******************************

	private static class ElementChangeListener 
		implements IElementChangedListener
	{
		ThreadLocal<ElementChangeProcessor> elementChangeProcessor = new ThreadLocal<ElementChangeProcessor>();
		
		ElementChangeListener() {
			super();
		}
		
		public void elementChanged(ElementChangedEvent event) {
			this.getElementChangeProcessor().elementChanged(event);
		}
		
		public ElementChangeProcessor getElementChangeProcessor() {
			ElementChangeProcessor processor = this.elementChangeProcessor.get();
			if (processor == null) {
				processor = new ElementChangeProcessor();
				this.elementChangeProcessor.set(processor);
			}
			return processor;
		}

		private static class ElementChangeProcessor {
			ElementChangeProcessor() {
				super();
			}
			public void elementChanged(ElementChangedEvent event) {
				JpaModelManager.instance().model.handleEvent(event);
			}
		}
	}
	
	
	// ********** preferences listener **********	

	private static class PreferencesListener
		implements IPropertyChangeListener
	{
		PreferencesListener() {
			super();
		}
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty() == JpaPreferenceConstants.PREF_DEFAULT_JPA_LIB) {
				try {
					JavaCore.setClasspathVariable("DEFAULT_JPA_LIB", new Path((String) event.getNewValue()), null);
				}
				catch (JavaModelException ex) {
					JptCorePlugin.log(ex);
				}
			}
		}
	}

}
