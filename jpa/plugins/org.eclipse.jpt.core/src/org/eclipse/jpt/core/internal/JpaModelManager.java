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
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IOpenable;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaModel;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.prefs.JpaPreferenceConstants;
import org.eclipse.jpt.utility.internal.BitTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;
import org.eclipse.wst.common.project.facet.core.events.IProjectFacetActionEvent;

/**
 * "Internal" global stuff.
 * Provide access via a singleton.
 * Hold and manage the JPA model (which holds all the JPA projects)
 * and the various global listeners. We attempt to determine whether events
 * are relevant before forwarding them to the JPA model.
 * 
 * Various things that cause us to add or remove a JPA project:
 * - Startup of the Dali plug-in will trigger all the JPA projects to be added
 * 
 * - Project created and facet installed
 *     facet POST_INSTALL
 * - Project facet uninstalled
 *     facet PRE_UNINSTALL
 * 
 * - Project opened
 *     facet PROJECT_MODIFIED
 * - Project closed
 *     facet PROJECT_MODIFIED
 * 
 * - Pre-existing project imported (created and opened)
 *     resource POST_CHANGE -> PROJECT -> CHANGED -> OPEN
 * - Project deleted
 *     resource PRE_DELETE
 * 
 * - Project facet installed by editing the facets settings file directly
 *     facet PROJECT_MODIFIED
 * - Project facet uninstalled by editing the facets settings file directly
 *     facet PROJECT_MODIFIED
 */
public class JpaModelManager {

	/**
	 * The JPA model - null until the plug-in is started.
	 */
	private GenericJpaModel jpaModel;

	/**
	 * Listen for changes to projects and files.
	 */
	private final IResourceChangeListener resourceChangeListener;

	/**
	 * Listen for the JPA facet being added or removed from a project.
	 */
	private final IFacetedProjectListener facetedProjectListener;

	/**
	 * Listen for Java changes and forward them to the JPA model,
	 * which will forward them to the JPA projects.
	 */
	private final IElementChangedListener javaElementChangeListener;

	/**
	 * Listen for changes to the "Default JPA Lib" preference
	 * so we can update the corresponding classpath variable.
	 */
	private final IPropertyChangeListener preferencesListener;


	// ********** singleton **********

	private static final JpaModelManager INSTANCE = new JpaModelManager();

	/**
	 * Return the singleton JPA model manager.
	 */
	public static JpaModelManager instance() {
		return INSTANCE;
	}


	// ********** constructor **********

	/**
	 * Private - ensure single instance.
	 */
	private JpaModelManager() {
		super();
		this.resourceChangeListener = new ResourceChangeListener();
		this.facetedProjectListener = new FacetedProjectListener();
		this.javaElementChangeListener = new JavaElementChangeListener();
		this.preferencesListener = new PreferencesListener();
	}


	// ********** plug-in controlled life-cycle **********

	/**
	 * internal - called by JptCorePlugin
	 */
	public synchronized void start() throws Exception {
		debug("*** START JPA model manager ***");
		try {
			this.jpaModel = new GenericJpaModel();
			ResourcesPlugin.getWorkspace().addResourceChangeListener(this.resourceChangeListener);
			FacetedProjectFramework.addListener(this.facetedProjectListener, IFacetedProjectEvent.Type.values());
			JavaCore.addElementChangedListener(this.javaElementChangeListener);
			JptCorePlugin.instance().getPluginPreferences().addPropertyChangeListener(this.preferencesListener);
		} catch (RuntimeException ex) {
			this.log(ex);
			this.stop();
		}
	}

	/**
	 * internal - called by JptCorePlugin
	 */
	public synchronized void stop() throws Exception {
		debug("*** STOP JPA model manager ***");
		JptCorePlugin.instance().getPluginPreferences().removePropertyChangeListener(this.preferencesListener);
		JavaCore.removeElementChangedListener(this.javaElementChangeListener);
		FacetedProjectFramework.removeListener(this.facetedProjectListener);
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this.resourceChangeListener);
		this.jpaModel.dispose();
		this.jpaModel = null;
	}


	// ********** API **********

	/**
	 * Return the workspace-wide JPA model.
	 */
	public JpaModel getJpaModel() {
		return this.jpaModel;
	}

	/** 
	 * Return the JPA project corresponding to the specified Eclipse project,
	 * or null if unable to associate the specified project with a
	 * JPA project.
	 */
	public JpaProject getJpaProject(IProject project) throws CoreException {
		return this.jpaModel.getJpaProject(project);
	}

	/**
	 * Return the JPA file corresponding to the specified Eclipse file,
	 * or null if unable to associate the specified file with a JPA file.
	 */
	public JpaFile getJpaFile(IFile file) throws CoreException {
		return this.jpaModel.getJpaFile(file);
	}

	/**
	 * The JPA settings associated with the specified Eclipse project
	 * have changed in such a way as to require the associated
	 * JPA project to be completely rebuilt
	 * (e.g. when the user changes a project's JPA platform).
	 */
	public void rebuildJpaProject(IProject project) {
		this.jpaModel.rebuildJpaProject(project);
	}

	/**
	 * Log the specified status.
	 */
	public void log(IStatus status) {
		JptCorePlugin.log(status);
    }

	/**
	 * Log the specified message.
	 */
	public void log(String msg) {
		JptCorePlugin.log(msg);
    }

	/**
	 * Log the specified exception or error.
	 */
	public void log(Throwable throwable) {
		JptCorePlugin.log(throwable);
	}


	// ********** resource changed **********

	/**
	 * Check for:
	 *   - project close/delete
	 *   - file add/remove
	 */
	/* private */ void resourceChanged(IResourceChangeEvent event) {
		if (! (event.getSource() instanceof IWorkspace)) {
			return;  // this probably shouldn't happen...
		}
		switch (event.getType()){
			case IResourceChangeEvent.PRE_DELETE :  // project-only event
				this.resourcePreDelete(event);
				break;
			case IResourceChangeEvent.POST_CHANGE :
				this.resourcePostChange(event);
				break;
			case IResourceChangeEvent.PRE_CLOSE :  // project-only event
			case IResourceChangeEvent.PRE_BUILD :
			case IResourceChangeEvent.POST_BUILD :
			default :
				break;
		}
	}

	/**
	 * A project is being deleted. Remove its corresponding
	 * JPA project if appropriate.
	 */
	private void resourcePreDelete(IResourceChangeEvent event) {
		debug("Resource (Project) PRE_DELETE: " + event.getResource());
		this.jpaModel.projectPreDelete((IProject) event.getResource());
	}

	/**
	 * A resource has changed somehow.
	 * Check for files being added or removed.
	 * (The JPA project only handles added and removed files here, ignoring
	 * changed files.)
	 */
	private void resourcePostChange(IResourceChangeEvent event) {
		debug("Resource POST_CHANGE");
		this.synchronizeFiles(event.getDelta());
		this.checkForOpenedProjects(event.getDelta());
	}

	private void synchronizeFiles(IResourceDelta delta) {
		IResource resource = delta.getResource();
		switch (resource.getType()) {
			case IResource.ROOT :
				this.synchronizeFiles(delta.getAffectedChildren());  // recurse
				break;
			case IResource.PROJECT :
				this.synchronizeFiles((IProject) resource, delta);
				break;
			case IResource.FILE :
			case IResource.FOLDER :
			default :
				break;
		}
	}

	private void synchronizeFiles(IResourceDelta[] deltas) {
		for (int i = 0; i < deltas.length; i++) {
			this.synchronizeFiles(deltas[i]);  // recurse
		}
	}

	/**
	 * Checked exceptions bite.
	 */
	private void synchronizeFiles(IProject project, IResourceDelta delta) {
		try {
			this.jpaModel.synchronizeFiles(project, delta);
		} catch (CoreException ex) {
			this.log(ex);  // problem traversing the project's resources - not much we can do
		}
	}

	/**
	 * Crawl the specified delta, looking for projects being opened.
	 * Projects being deleted are handled in IResourceChangeEvent.PRE_DELETE.
	 * Projects being closed are handled in IFacetedProjectEvent.Type.PROJECT_MODIFIED.
	 */
	private void checkForOpenedProjects(IResourceDelta delta) {
		IResource resource = delta.getResource();
		switch (resource.getType()) {
			case IResource.ROOT :
				this.checkForOpenedProjects(delta.getAffectedChildren());  // recurse
				break;
			case IResource.PROJECT :
				this.checkForOpenedProject((IProject) resource, delta);
				break;
			case IResource.FILE :
			case IResource.FOLDER :
			default :
				break;
		}
	}

	private void checkForOpenedProjects(IResourceDelta[] deltas) {
		for (int i = 0; i < deltas.length; i++) {
			this.checkForOpenedProjects(deltas[i]);  // recurse
		}
	}

	private void checkForOpenedProject(IProject project, IResourceDelta delta) {
		switch (delta.getKind()) {
			case IResourceDelta.CHANGED : 
				this.checkDeltaFlagsForOpenedProject(project, delta);
				break;
			case IResourceDelta.REMOVED :  // already handled with the PRE_DELETE event
			case IResourceDelta.ADDED :  // all but project rename handled with the facet POST_INSTALL event
				this.checkDeltaFlagsForRenamedProject(project, delta);
				break;
			case IResourceDelta.ADDED_PHANTOM :  // ignore
			case IResourceDelta.REMOVED_PHANTOM :  // ignore
			default :
				break;
		}
	}

	/**
	 * We don't get any events from the Facets Framework when a pre-existing
	 * project is imported, so we need to check for the newly imported project here.
	 * 
	 * This event also occurs when a project is simply opened. Project opening
	 * also triggers a Facet PROJECT_MODIFIED event and that is where we add
	 * the JPA project, not here
	 */
	private void checkDeltaFlagsForOpenedProject(IProject project, IResourceDelta delta) {
		if (BitTools.flagIsSet(delta.getFlags(), IResourceDelta.OPEN) && project.isOpen()) {
			debug("\tProject CHANGED - OPEN: " + project.getName());
			this.jpaModel.checkForTransition(project);
		}
	}
	
	/**
	 * We don't get any events from the Facets Framework when a project is renamed,
	 * so we need to check for the renamed projects here.
	 */
	private void checkDeltaFlagsForRenamedProject(IProject project, IResourceDelta delta) {
		if (BitTools.flagIsSet(delta.getFlags(), IResourceDelta.MOVED_FROM) && project.isOpen()) {
			debug("\tProject ADDED - MOVED_FROM: " + delta.getMovedFromPath());
			this.jpaModel.checkForTransition(project);
		}
	}


	// ********** faceted project changed **********

	/**
	 * Check for:
	 *   - install of JPA facet
	 *   - un-install of JPA facet
	 *   - any other appearance or disappearance of the JPA facet
	 */
	/* private */ void facetedProjectChanged(IFacetedProjectEvent event) {
		switch (event.getType()) {
			case POST_INSTALL :
				this.facetedProjectPostInstall((IProjectFacetActionEvent) event);
				break;
			case PRE_UNINSTALL :
				this.facetedProjectPreUninstall((IProjectFacetActionEvent) event);
				break;
			case PROJECT_MODIFIED :
				this.facetedProjectModified(event.getProject().getProject());
				break;
			default :
				break;
		}
	}

	private void facetedProjectPostInstall(IProjectFacetActionEvent event) {
		debug("Facet POST_INSTALL: " + event.getProjectFacet());
		if (event.getProjectFacet().getId().equals(JptCorePlugin.FACET_ID)) {
			this.jpaModel.jpaFacetedProjectPostInstall(event);
		}
	}

	private void facetedProjectPreUninstall(IProjectFacetActionEvent event) {
		debug("Facet PRE_UNINSTALL: " + event.getProjectFacet());
		if (event.getProjectFacet().getId().equals(JptCorePlugin.FACET_ID)) {
			this.jpaModel.jpaFacetedProjectPreUninstall(event);
		}
	}

	/**
	 * This event is triggered for any change to a faceted project.
	 * We use the event to watch for the following:
	 *   - an open project is closed
	 *   - a closed project is opened
	 *   - one of a project's (facet) metadata files is edited directly
	 */
	private void facetedProjectModified(IProject project) {
		debug("Facet PROJECT_MODIFIED: " + project.getName());
		this.jpaModel.checkForTransition(project);
	}


	// ********** Java element changed **********

	/**
	 * Forward the event to the JPA model.
	 */
	/* private */ void javaElementChanged(ElementChangedEvent event) {
		if (this.eventIndicatesProjectAddedButNotOpen(event)) {
			return;
		}
		this.jpaModel.javaElementChanged(event);
	}

	//209275 - This particular event only causes problems in a clean workspace the first time a JPA project
	//is created through the JPA wizard. The second time a JPA project is created, this event occurs, but 
	//it occurs as the wizard is closing so it does not cause a deadlock.
	private boolean eventIndicatesProjectAddedButNotOpen(ElementChangedEvent event) {
		IJavaElementDelta delta = event.getDelta();
		if (delta.getKind() == IJavaElementDelta.CHANGED) {
			if (delta.getElement().getElementType() == IJavaElement.JAVA_MODEL) {
				IJavaElementDelta[] children = delta.getAffectedChildren();
				if (children.length == 1) {
					IJavaElementDelta childDelta = children[0];
					if (childDelta.getKind() == IJavaElementDelta.ADDED) {
						IJavaElement childElement = childDelta.getElement();
						if (childElement.getElementType() == IJavaElement.JAVA_PROJECT) {
							if (childDelta.getAffectedChildren().length == 0) {
								if (!((IOpenable) childElement).isOpen()) {
									return true;
								}
							}
						}
					}
				}
			}
		}	
		return false;
	}

	// ********** preference changed **********

	/**
	 * When the "Default JPA Lib" preference changes,
	 * update the appropriate JDT Core classpath variable.
	 */
	/* private */ void preferenceChanged(PropertyChangeEvent event) {
		if (event.getProperty() == JpaPreferenceConstants.PREF_DEFAULT_JPA_LIB) {
			try {
				JavaCore.setClasspathVariable("DEFAULT_JPA_LIB", new Path((String) event.getNewValue()), null);
			} catch (JavaModelException ex) {
				this.log(ex);  // not sure what would cause this...
			}
		}
	}


	// ********** resource change listener **********

	/**
	 * Forward the Resource change event back to the JPA model manager.
	 */
	private class ResourceChangeListener implements IResourceChangeListener {
		ResourceChangeListener() {
			super();
		}
		public void resourceChanged(IResourceChangeEvent event) {
			JpaModelManager.this.resourceChanged(event);
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}


	// ********** faceted project listener **********

	/**
	 * Forward the Faceted project change event back to the JPA model manager.
	 */
	private class FacetedProjectListener implements IFacetedProjectListener {
		FacetedProjectListener() {
			super();
		}
		public void handleEvent(IFacetedProjectEvent event) {
			JpaModelManager.this.facetedProjectChanged(event);
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}


	// ********** Java element change listener **********

	/**
	 * Forward the Java element change event back to the JPA model manager.
	 */
	private class JavaElementChangeListener implements IElementChangedListener {
		JavaElementChangeListener() {
			super();
		}
		public void elementChanged(ElementChangedEvent event) {
			JpaModelManager.this.javaElementChanged(event);
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}


	// ********** preferences listener **********

	/**
	 * Forward the Preferences change event back to the JPA model manager.
	 */
	private class PreferencesListener implements IPropertyChangeListener {
		PreferencesListener() {
			super();
		}
		public void propertyChange(PropertyChangeEvent event) {
			JpaModelManager.this.preferenceChanged(event);
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}


	// ********** debug **********

	// @see JpaModelTests#testDEBUG()
	private static final boolean DEBUG = false;

	private static void debug(String message) {
		if (DEBUG) {
			System.out.println(Thread.currentThread().getName() + ": " + message);
		}
	}

}
