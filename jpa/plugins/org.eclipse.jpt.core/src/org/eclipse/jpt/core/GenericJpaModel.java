/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import java.util.Vector;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.internal.AsynchronousJpaProjectUpdater;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.internal.SimpleJpaProjectConfig;
import org.eclipse.jpt.core.internal.facet.JpaFacetInstallDataModelProperties;
import org.eclipse.jpt.core.internal.operations.JpaFileCreationDataModelProperties;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.core.internal.operations.PersistenceFileCreationDataModelProvider;
import org.eclipse.jpt.utility.internal.AsynchronousCommandExecutor;
import org.eclipse.jpt.utility.internal.BitTools;
import org.eclipse.jpt.utility.internal.SimpleCommandExecutor;
import org.eclipse.jpt.utility.internal.StatefulCommandExecutor;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.SynchronizedBoolean;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;
import org.eclipse.wst.common.project.facet.core.events.IProjectFacetActionEvent;

/**
 * The JPA model maintains a list of all JPA projects in the workspace.
 * It keeps the list (and the state of the JPA projects themselves)
 * synchronized with the workspace by listening for various
 * changes:<ul>
 * <li>Resource
 * <li>Java
 * <li>Faceted Project
 * </ul>
 * We use an Eclipse {@link ILock lock} to synchronize access to the JPA
 * projects when dealing with these events. In an effort to reduce deadlocks,
 * the simple Resource and Java change events are dispatched to a background
 * thread, allowing us to handle the events outside of the workspace lock held
 * during resource and Java change notifications.
 * <p>
 * Events that trigger either the adding or removing of a JPA project (e.g.
 * {@link IFacetedProjectEvent.Type#POST_INSTALL}) are handled "synchronously"
 * by allowing the background thread to handle any outstanding events before
 * updating the list of JPA projects and returning execution to the event
 * source.
 * <p>
 * Various things that cause us to add or remove a JPA project:<ul>
 * <li>Startup of the Dali plug-in will trigger all the pre-existing JPA
 *     projects to be added
 * 
 * <li>Project created and facet installed<p>
 *     {@link IFacetedProjectEvent.Type#POST_INSTALL}
 * <li>Project facet uninstalled<p>
 *     {@link IFacetedProjectEvent.Type#PRE_UNINSTALL}
 * 
 * <li>Project opened<p>
 *     {@link IFacetedProjectEvent.Type#PROJECT_MODIFIED}
 * <li>Project closed<p>
 *     {@link IFacetedProjectEvent.Type#PROJECT_MODIFIED}
 * 
 * <li>Pre-existing project imported from directory or archive (created and opened)<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 *     -> {@link IResource#PROJECT}
 *     -> {@link IResourceDelta#ADDED}
 *     -> {@link IResourceDelta#OPEN}
 * <li>Project renamed<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 *     -> {@link IResource#PROJECT}
 *     -> {@link IResourceDelta#ADDED}
 *     -> {@link IResourceDelta#MOVED_FROM}
 * <li>Project deleted<p>
 *     {@link IResourceChangeEvent#PRE_DELETE}
 * 
 * <li>Project facet installed by editing the facets settings file directly<p>
 *     {@link IFacetedProjectEvent.Type#PROJECT_MODIFIED}
 * <li>Project facet uninstalled by editing the facets settings file directly<p>
 *     {@link IFacetedProjectEvent.Type#PROJECT_MODIFIED}
 * </ul>
 */
class GenericJpaModel
	extends AbstractModel
	implements JpaModel
{
	/**
	 * All the JPA projects in the workspace.
	 */
	private final Vector<JpaProject> jpaProjects = new Vector<JpaProject>();

	/**
	 * Synchronize access to the JPA projects.
	 */
	/* private */ final ILock lock = this.getJobManager().newLock();

	/**
	 * Determine how Resource and Java change events are
	 * handled (i.e. synchronously or asynchronously).
	 */
	private volatile StatefulCommandExecutor eventHandler = new AsynchronousCommandExecutor(JptCoreMessages.DALI_EVENT_HANDLER_THREAD_NAME);

	/**
	 * Listen for<ul>
	 * <li>changes to projects and files
	 * <li>deleted projects
	 * <li>clean builds
	 * </ul>
	 */
	private final IResourceChangeListener resourceChangeListener = new ResourceChangeListener();

	/**
	 * The types of resource change events that interest
	 * {@link #resourceChangeListener}.
	 */
	private static final int RESOURCE_CHANGE_EVENT_TYPES =
			IResourceChangeEvent.PRE_DELETE |
			IResourceChangeEvent.POST_CHANGE |
			IResourceChangeEvent.POST_BUILD;

	/**
	 * Listen for the JPA facet being added to or removed from a "faceted" project.
	 */
	private final IFacetedProjectListener facetedProjectListener = new FacetedProjectListener();

	/**
	 * Listen for Java changes (unless the Dali UI is active).
	 * @see #javaElementChangeListenerIsActive
	 */
	private final JavaElementChangeListener javaElementChangeListener = new JavaElementChangeListener();


	// ********** constructor **********

	/**
	 * internal - called by the Dali plug-in
	 */
	GenericJpaModel() {
		super();
	}


	// ********** plug-in controlled life-cycle **********

	/**
	 * internal - called by the Dali plug-in
	 */
	void start() {
		try {
			this.lock.acquire();
			this.start_();
		} finally {
			this.lock.release();
		}
	}

	private void start_() {
		debug("*** JPA model START ***"); //$NON-NLS-1$
		try {
			this.buildJpaProjects();
			this.eventHandler.start();
			this.getWorkspace().addResourceChangeListener(this.resourceChangeListener, RESOURCE_CHANGE_EVENT_TYPES);
			FacetedProjectFramework.addListener(this.facetedProjectListener, IFacetedProjectEvent.Type.values());
			JavaCore.addElementChangedListener(this.javaElementChangeListener);
		} catch (RuntimeException ex) {
			JptCorePlugin.log(ex);
			this.stop_();
		}
	}

	/**
	 * side-effect: 'jpaProjects' populated
	 */
	private void buildJpaProjects() {
		try {
			this.buildJpaProjects_();
		} catch (CoreException ex) {
			// if we have a problem, leave the currently built JPA projects in
			// place and keep executing (should be OK...)
			JptCorePlugin.log(ex);
		}
	}

	private void buildJpaProjects_() throws CoreException {
		this.getWorkspace().getRoot().accept(new ResourceProxyVisitor(), IResource.NONE);
	}

	/**
	 * internal - called by the Dali plug-in
	 */
	void stop() throws Exception {
		try {
			this.lock.acquire();
			this.stop_();
		} finally {
			this.lock.release();
		}
	}

	private void stop_() {
		debug("*** JPA model STOP ***"); //$NON-NLS-1$
		JavaCore.removeElementChangedListener(this.javaElementChangeListener);
		FacetedProjectFramework.removeListener(this.facetedProjectListener);
		this.getWorkspace().removeResourceChangeListener(this.resourceChangeListener);
		this.eventHandler.stop();
		this.clearJpaProjects();
	}

	private void clearJpaProjects() {
		// clone to prevent concurrent modification exceptions
		for (JpaProject jpaProject : this.getJpaProjects_()) {
			this.removeJpaProject(jpaProject);
		}
	}


	// ********** JpaModel implementation **********

	public Iterable<JpaProject> getJpaProjects() {
		try {
			this.lock.acquire();
			return this.getJpaProjects_();
		} finally {
			this.lock.release();
		}
	}

	private Iterable<JpaProject> getJpaProjects_() {
		return new LiveCloneIterable<JpaProject>(this.jpaProjects);
	}

	public int getJpaProjectsSize() {
		return this.jpaProjects.size();
	}

	public JpaProject getJpaProject(IProject project) {
		try {
			this.lock.acquire();
			return this.getJpaProject_(project);
		} finally {
			this.lock.release();
		}
	}

	private JpaProject getJpaProject_(IProject project) {
		for (JpaProject jpaProject : this.jpaProjects) {
			if (jpaProject.getProject().equals(project)) {
				return jpaProject;
			}
		}
		return null;
	}

	public JpaFile getJpaFile(IFile file) {
		JpaProject jpaProject = this.getJpaProject(file.getProject());
		return (jpaProject == null) ? null : jpaProject.getJpaFile(file);
	}

	public void rebuildJpaProject(IProject project) {
		try {
			this.lock.acquire();
			this.rebuildJpaProject_(project);
		} finally {
			this.lock.release();
		}
	}

	/**
	 * assumption: the JPA project holder exists
	 */
	private void rebuildJpaProject_(IProject project) {
		this.removeJpaProject(this.getJpaProject_(project));
		this.addJpaProject(project);
	}

	public boolean javaElementChangeListenerIsActive() {
		return this.javaElementChangeListener.isActive();
	}

	public void setJavaElementChangeListenerIsActive(boolean javaElementChangeListenerIsActive) {
		this.javaElementChangeListener.setActive(javaElementChangeListenerIsActive);
	}

	public IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	public IJobManager getJobManager() {
		return Job.getJobManager();
	}


	// ********** adding/removing JPA projects **********

	/* private */ void addJpaProject(IProject project) {
		this.addJpaProject(this.buildJpaProject(project));
	}

	private void addJpaProject(JpaProject jpaProject) {
		// figure out exactly when JPA projects are added
		dumpStackTrace("add: ", jpaProject); //$NON-NLS-1$
		// the JPA project will be null if we have any problems building it...
		// (e.g. if we have problems getting the JPA platform)
		if (jpaProject != null) {
			this.addItemToCollection(jpaProject, this.jpaProjects, JPA_PROJECTS_COLLECTION);
		}
	}

	/**
	 * return null if we have any problems...
	 */
	private JpaProject buildJpaProject(IProject project) {
		return this.buildJpaProject(this.buildJpaProjectConfig(project));
	}

	/**
	 * return null if we have any problems...
	 */
	private JpaProject buildJpaProject(JpaProject.Config config) {
		JpaPlatform jpaPlatform = config.getJpaPlatform();
		if (jpaPlatform == null) {
			return null;
		}
		JpaProject jpaProject = this.buildJpaProject(jpaPlatform, config);
		if (jpaProject == null) {
			return null;
		}
		jpaProject.setUpdater(new AsynchronousJpaProjectUpdater(jpaProject));
		return jpaProject;
	}

	/**
	 * return null if we have any problems...
	 */
	private JpaProject buildJpaProject(JpaPlatform jpaPlatform, JpaProject.Config config) {
		try {
			return jpaPlatform.getJpaFactory().buildJpaProject(config);
		} catch (Exception ex) {
			JptCorePlugin.log(ex);
			return null;
		}
	}

	private JpaProject.Config buildJpaProjectConfig(IProject project) {
		SimpleJpaProjectConfig config = new SimpleJpaProjectConfig();
		config.setProject(project);
		config.setJpaPlatform(JptCorePlugin.getJpaPlatform(project));
		config.setConnectionProfileName(JptCorePlugin.getConnectionProfileName(project));
		config.setUserOverrideDefaultCatalog(JptCorePlugin.getUserOverrideDefaultCatalog(project));
		config.setUserOverrideDefaultSchema(JptCorePlugin.getUserOverrideDefaultSchema(project));
		config.setDiscoverAnnotatedClasses(JptCorePlugin.discoverAnnotatedClasses(project));
		config.setMetamodelSourceFolderName(JptCorePlugin.getMetamodelSourceFolderName(project));
		return config;
	}

	private void removeJpaProject(JpaProject jpaProject) {
		// figure out exactly when JPA projects are removed
		dumpStackTrace("remove: ", jpaProject); //$NON-NLS-1$
		this.removeItemFromCollection(jpaProject, this.jpaProjects, JPA_PROJECTS_COLLECTION);
		jpaProject.dispose();
	}


	// ********** Project POST_CHANGE **********

	/**
	 * Forward the specified resource delta to all our JPA projects;
	 * they will each determine whether the event is significant.
	 */
	/* private */ void projectChanged(IResourceDelta delta) {
		this.eventHandler.execute(this.buildProjectChangedCommand(delta));
	}

	private Command buildProjectChangedCommand(final IResourceDelta delta) {
		return new Command() {
			@Override
			void execute_() {
				GenericJpaModel.this.projectChanged_(delta);
			}
		};
	}

	/* private */ void projectChanged_(IResourceDelta delta) {
		for (JpaProject jpaProject : this.jpaProjects) {
			jpaProject.projectChanged(delta);
		}
	}


	// ********** Project POST_BUILD (CLEAN_BUILD) **********

	/* private */ void projectPostCleanBuild(IProject project) {
		this.executeAfterEventsHandled(this.buildProjectPostCleanBuildCommand(project));
	}

	private Command buildProjectPostCleanBuildCommand(final IProject project) {
		return new Command() {
			@Override
			void execute_() {
				GenericJpaModel.this.projectPostCleanBuild_(project);
			}
		};
	}

	/* private */ void projectPostCleanBuild_(IProject project) {
		JpaProject jpaProject = this.getJpaProject_(project);
		if (jpaProject != null) {
			this.removeJpaProject(jpaProject);
			this.addJpaProject(project);
		}
	}


	// ********** Project PRE_DELETE **********

	/**
	 * A project is being deleted. Remove its corresponding
	 * JPA project if appropriate.
	 */
	/* private */ void projectPreDelete(IProject project) {
		this.executeAfterEventsHandled(this.buildProjectPreDeleteCommand(project));
	}

	private Command buildProjectPreDeleteCommand(final IProject project) {
		return new Command() {
			@Override
			void execute_() {
				GenericJpaModel.this.projectPreDelete_(project);
			}
		};
	}

	/* private */ void projectPreDelete_(IProject project) {
		JpaProject jpaProject = this.getJpaProject(project);
		if (jpaProject != null) {
			this.removeJpaProject(jpaProject);
		}
	}


	// ********** Resource and/or Facet events **********

	/**
	 * Check whether the JPA facet has been added or removed.
	 */
	/* private */ void checkForJpaFacetTransition(IProject project) {
		this.executeAfterEventsHandled(this.buildCheckForJpaFacetTransitionCommand(project));
	}

	private Command buildCheckForJpaFacetTransitionCommand(final IProject project) {
		return new Command() {
			@Override
			void execute_() {
				GenericJpaModel.this.checkForJpaFacetTransition_(project);
			}
		};
	}

	/* private */ void checkForJpaFacetTransition_(IProject project) {
		JpaProject jpaProject = this.getJpaProject_(project);

		if (JptCorePlugin.projectHasJpaFacet(project)) {
			if (jpaProject == null) {  // JPA facet added
				this.addJpaProject(project);
			}
		} else {
			if (jpaProject != null) {  // JPA facet removed
				this.removeJpaProject(jpaProject);
			}
		}
	}


	// ********** FacetedProject POST_INSTALL **********

	/* private */ void jpaFacetedProjectPostInstall(IProjectFacetActionEvent event) {
		this.executeAfterEventsHandled(this.buildJpaFacetedProjectPostInstallCommand(event));
	}

	private Command buildJpaFacetedProjectPostInstallCommand(final IProjectFacetActionEvent event) {
		return new Command() {
			@Override
			void execute_() {
				GenericJpaModel.this.jpaFacetedProjectPostInstall_(event);
			}
		};
	}

	/* private */ void jpaFacetedProjectPostInstall_(IProjectFacetActionEvent event) {
		IProject project = event.getProject().getProject();
		IDataModel dataModel = (IDataModel) event.getActionConfig();

		// assume(?) this is the first event to indicate we need to add the JPA project to the JPA model
		this.addJpaProject(project);

		boolean buildOrmXml = dataModel.getBooleanProperty(JpaFacetInstallDataModelProperties.CREATE_ORM_XML);
		this.createProjectXml(project, buildOrmXml);
	}

	private void createProjectXml(IProject project, boolean buildOrmXml) {
		this.createPersistenceXml(project);

		if (buildOrmXml) {
			this.createOrmXml(project);
		}
	}

	private void createPersistenceXml(IProject project) {
		IDataModel config = DataModelFactory.createDataModel(new PersistenceFileCreationDataModelProvider());
		config.setProperty(JpaFileCreationDataModelProperties.PROJECT_NAME, project.getName());
		// default values for all other properties should suffice
		try {
			config.getDefaultOperation().execute(null, null);
		} catch (ExecutionException ex) {
			JptCorePlugin.log(ex);
		}
	}

	private void createOrmXml(IProject project) {
		IDataModel config = DataModelFactory.createDataModel(new OrmFileCreationDataModelProvider());
		config.setProperty(JpaFileCreationDataModelProperties.PROJECT_NAME, project.getName());
		// default values for all other properties should suffice
		try {
			config.getDefaultOperation().execute(null, null);
		} catch (ExecutionException ex) {
			JptCorePlugin.log(ex);
		}
	}


	// ********** FacetedProject PRE_UNINSTALL **********

	/* private */ void jpaFacetedProjectPreUninstall(IProjectFacetActionEvent event) {
		IProject project = event.getProject().getProject();
		this.executeAfterEventsHandled(this.buildJpaFacetedProjectPreUninstallCommand(project));
	}

	private Command buildJpaFacetedProjectPreUninstallCommand(final IProject project) {
		return new Command() {
			@Override
			void execute_() {
				GenericJpaModel.this.jpaFacetedProjectPreUninstall_(project);
			}
		};
	}

	/* private */ void jpaFacetedProjectPreUninstall_(IProject project) {
		// assume(?) this is the first event to indicate we need to remove the JPA project to the JPA model
		this.removeJpaProject(this.getJpaProject_(project));
	}


	// ********** Java element changed **********

	/**
	 * Forward the Java element changed event to all the JPA projects
	 * because the event could affect multiple projects.
	 */
	/* private */ void javaElementChanged(ElementChangedEvent event) {
		this.eventHandler.execute(this.buildJavaElementChangedCommand(event));
	}

	private Command buildJavaElementChangedCommand(final ElementChangedEvent event) {
		return new Command() {
			@Override
			void execute_() {
				GenericJpaModel.this.javaElementChanged_(event);
			}
		};
	}

	/* private */ void javaElementChanged_(ElementChangedEvent event) {
		for (JpaProject jpaProject : this.jpaProjects) {
			jpaProject.javaElementChanged(event);
		}
	}


	// ********** miscellaneous **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.jpaProjects);
	}


	// ********** event handler **********

	/**
	 * If the event handler is executing asynchronously:<br>
	 * Allow all the commands currently on the command executor's queue to execute.
	 * Once they have executed, suspend the command executor and process the
	 * specified command (on <em>this</em> thread, <em>not</em> the command
	 * executor thread). Once the specified command is finished, allow the
	 * command executor to resume processing its command queue.
	 * <p>
	 * If the event handler is executing synchronously:<br>
	 * All the events have already been handled synchronously, so we simply
	 * execute the specified command [sorta] directly.
	 */
	private void executeAfterEventsHandled(Command command) {
		SynchronizedBoolean flag = new SynchronizedBoolean(false);
		this.eventHandler.execute(new PauseCommand(Thread.currentThread(), flag));
		try {
			flag.waitUntilTrue();
		} catch (InterruptedException ex) {
			// ignore - not sure why this thread would be interrupted
		}
		try {
			command.execute();
		} finally {
			flag.setFalse();
		}
	}

	/**
	 * If this "pause" command is executing (asynchronously) on a different
	 * thread than the JPA model:<ul>
	 * <li>it will set the flag to <code>true</code>, allowing the JPA model to
	 * resume executing on its own thread
	 * <li>then it will suspend its command executor until the JPA model sets
	 * the flag back to <code>false</code>.
	 * </ul>
	 * If this "pause" command is executing (synchronously) on the same thread
	 * as the JPA model, it will simply set the flag to <code>true</code> and
	 * return.
	 */
	private static class PauseCommand
		implements org.eclipse.jpt.utility.Command
	{
		private final Thread producerThread;
		private final SynchronizedBoolean flag;

		PauseCommand(Thread producerThread, SynchronizedBoolean flag) {
			super();
			this.producerThread = producerThread;
			this.flag = flag;
		}

		public void execute() {
			this.flag.setTrue();
			if (Thread.currentThread() != this.producerThread) {
				try {
					this.flag.waitUntilFalse();
				} catch (InterruptedException ex) {
					// ignore - the command executor will check for interruptions
				}
			}
		}
	}

	/**
	 * This method is called (via reflection) when the test plug-in is loaded.
	 * @see JptCoreTestsPlugin#start(BundleContext)
	 */
	public void handleEventsSynchronously() {
		try {
			this.lock.acquire();
			this.handleEventsSynchronously_();
		} finally {
			this.lock.release();
		}
	}

	private void handleEventsSynchronously_() {
		this.eventHandler.stop();
		this.eventHandler = new SimpleCommandExecutor();
		this.eventHandler.start();
	}


	// ********** resource proxy visitor **********

	/**
	 * Visit the workspace resource tree, adding a JPA project to the
	 * JPA model for each open Eclipse project that has a JPA facet.
	 */
	private class ResourceProxyVisitor implements IResourceProxyVisitor {
		ResourceProxyVisitor() {
			super();
		}

		public boolean visit(IResourceProxy resourceProxy) {
			switch (resourceProxy.getType()) {
				case IResource.ROOT :
					return true;  // all projects are in the "root"
				case IResource.PROJECT :
					this.checkProject(resourceProxy);
					return false;  // no nested projects
				case IResource.FOLDER :
					return false;  // ignore
				case IResource.FILE :
					return false;  // ignore
				default :
					return false;
			}
		}

		private void checkProject(IResourceProxy resourceProxy) {
			if (resourceProxy.isAccessible()) {  // the project exists and is open
				IProject project = (IProject) resourceProxy.requestResource();
				if (JptCorePlugin.projectHasJpaFacet(project)) {
					GenericJpaModel.this.addJpaProject(project);
				}
			}
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}

	}


	// ********** command **********

	/**
	 * Abstract class that holds the JPA model lock while
	 * executing.
	 */
	private abstract class Command
		implements org.eclipse.jpt.utility.Command
	{
		Command() {
			super();
		}

		public final void execute() {
			try {
				GenericJpaModel.this.lock.acquire();
				this.execute_();
			} finally {
				GenericJpaModel.this.lock.release();
			}
		}

		abstract void execute_();

	}


	// ********** resource change listener **********

	private class ResourceChangeListener implements IResourceChangeListener {

		ResourceChangeListener() {
			super();
		}

		/**
		 * Check for:<ul>
		 * <li>project close/delete
		 * <li>file add/remove
		 * <li>project open/rename
		 * </ul>
		 */
		public void resourceChanged(IResourceChangeEvent event) {
			switch (event.getType()) {
				case IResourceChangeEvent.POST_CHANGE :
					this.resourcePostChange(event);
					break;

				// workspace or project events
				case IResourceChangeEvent.PRE_REFRESH :
					break;  // ignore
				case IResourceChangeEvent.PRE_BUILD :
					break;  // ignore
				case IResourceChangeEvent.POST_BUILD :
					this.resourcePostBuild(event);
					break;

				// project-only events
				case IResourceChangeEvent.PRE_CLOSE :  
					break;  // ignore
				case IResourceChangeEvent.PRE_DELETE :
					this.resourcePreDelete(event);
					break;
				default :
					break;
			}
		}

		/**
		 * A resource has changed somehow.
		 * Check for files being added or removed.
		 * (The JPA project only handles added and removed files here
		 * File changes are handled via the Java Element Changed event.)
		 * Also check for opened projects.
		 */
		private void resourcePostChange(IResourceChangeEvent event) {
			debug("Resource POST_CHANGE"); //$NON-NLS-1$
			this.resourceChanged(event.getDelta());
		}

		private void resourceChanged(IResourceDelta delta) {
			IResource resource = delta.getResource();
			switch (resource.getType()) {
				case IResource.ROOT :
					this.resourceChangedChildren(delta);
					break;
				case IResource.PROJECT :
					this.projectChanged((IProject) resource, delta);
					break;
				case IResource.FOLDER :
					break;  // ignore
				case IResource.FILE :
					break;  // ignore
				default :
					break;
			}
		}

		private void resourceChangedChildren(IResourceDelta delta) {
			for (IResourceDelta child : delta.getAffectedChildren()) {
				this.resourceChanged(child);  // recurse
			}
		}

		private void projectChanged(IProject project, IResourceDelta delta) {
			GenericJpaModel.this.projectChanged(delta);
			this.checkForOpenedProject(project, delta);
		}


		/**
		 * Crawl the specified delta, looking for projects being opened.
		 * Projects being deleted are handled in {@link IResourceChangeEvent#PRE_DELETE}.
		 * Projects being closed are handled in {@link IFacetedProjectEvent.Type#PROJECT_MODIFIED}.
		 */
		private void checkForOpenedProject(IProject project, IResourceDelta delta) {
			switch (delta.getKind()) {
				case IResourceDelta.ADDED :  // all but project import and rename handled with the facet POST_INSTALL event
					this.checkDeltaFlagsForOpenedProject(project, delta);
					this.checkDeltaFlagsForRenamedProject(project, delta);
					break;
				case IResourceDelta.REMOVED :  // already handled with the PRE_DELETE event
					break;
				case IResourceDelta.CHANGED : 
					this.checkDeltaFlagsForOpenedProject(project, delta);
					break;
				case IResourceDelta.ADDED_PHANTOM :
					break;  // ignore
				case IResourceDelta.REMOVED_PHANTOM :
					break;  // ignore
				default :
					break;
			}
		}

		/**
		 * We don't get any events from the Facets Framework when a pre-existing
		 * project is imported, so we need to check for the newly imported project here.
		 * <p>
		 * This event also occurs when a project is simply opened. Project opening
		 * also triggers a {@link IFacetedProjectEvent.Type#PROJECT_MODIFIED} event
		 * and that is where we add the JPA project, not here.
		 */
		private void checkDeltaFlagsForOpenedProject(IProject project, IResourceDelta delta) {
			if (BitTools.flagIsSet(delta.getFlags(), IResourceDelta.OPEN) && project.isOpen()) {
				debug("\tProject CHANGED - OPEN: ", project.getName()); //$NON-NLS-1$
				GenericJpaModel.this.checkForJpaFacetTransition(project);
			}
		}

		/**
		 * We don't get any events from the Facets Framework when a project is renamed,
		 * so we need to check for the renamed projects here.
		 */
		private void checkDeltaFlagsForRenamedProject(IProject project, IResourceDelta delta) {
			if (BitTools.flagIsSet(delta.getFlags(), IResourceDelta.MOVED_FROM) && project.isOpen()) {
				debug("\tProject ADDED - MOVED_FROM: ", delta.getMovedFromPath()); //$NON-NLS-1$
				GenericJpaModel.this.checkForJpaFacetTransition(project);
			}
		}

		/**
		 * A post build event has occurred.
		 * Check for whether the build was a "clean" build and trigger project update.
		 */
		private void resourcePostBuild(IResourceChangeEvent event) {
			debug("Resource POST_BUILD: ", event.getResource()); //$NON-NLS-1$
			if (event.getBuildKind() == IncrementalProjectBuilder.CLEAN_BUILD) {
				this.resourcePostCleanBuild(event.getDelta());
			}
		}

		private void resourcePostCleanBuild(IResourceDelta delta) {
			IResource resource = delta.getResource();
			switch (resource.getType()) {
				case IResource.ROOT :
					this.resourcePostCleanBuildChildren(delta);
					break;
				case IResource.PROJECT :
					this.projectPostCleanBuild((IProject) resource);
					break;
				case IResource.FOLDER :
					break;  // ignore
				case IResource.FILE :
					break;  // ignore
				default :
					break;
			}
		}

		private void resourcePostCleanBuildChildren(IResourceDelta delta) {
			for (IResourceDelta child : delta.getAffectedChildren()) {
				this.resourcePostCleanBuild(child);  // recurse
			}
		}

		private void projectPostCleanBuild(IProject project) {
			debug("\tProject CLEAN: ", project.getName()); //$NON-NLS-1$
			GenericJpaModel.this.projectPostCleanBuild(project);
		}

		/**
		 * A project is being deleted. Remove its corresponding
		 * JPA project if appropriate.
		 */
		private void resourcePreDelete(IResourceChangeEvent event) {
			IProject project = (IProject) event.getResource();
			debug("Resource (Project) PRE_DELETE: ", project); //$NON-NLS-1$
			GenericJpaModel.this.projectPreDelete(project);
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}

	}


	// ********** faceted project listener **********

	/**
	 * Forward the Faceted project change event back to the JPA model.
	 */
	private class FacetedProjectListener implements IFacetedProjectListener {

		FacetedProjectListener() {
			super();
		}

		/**
		 * Check for:<ul>
		 * <li>install of JPA facet
		 * <li>un-install of JPA facet
		 * <li>any other appearance or disappearance of the JPA facet
		 * </ul>
		 */
		public void handleEvent(IFacetedProjectEvent event) {
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
			debug("Facet POST_INSTALL: ", event.getProjectFacet()); //$NON-NLS-1$
			if (event.getProjectFacet().getId().equals(JptCorePlugin.FACET_ID)) {
				GenericJpaModel.this.jpaFacetedProjectPostInstall(event);
			}
		}

		private void facetedProjectPreUninstall(IProjectFacetActionEvent event) {
			debug("Facet PRE_UNINSTALL: ", event.getProjectFacet()); //$NON-NLS-1$
			if (event.getProjectFacet().getId().equals(JptCorePlugin.FACET_ID)) {
				GenericJpaModel.this.jpaFacetedProjectPreUninstall(event);
			}
		}

		/**
		 * This event is triggered for any change to a faceted project.
		 * We use the event to watch for the following:<ul>
		 * <li>an open project is closed
		 * <li>a closed project is opened
		 * <li>one of a project's (facet) metadata files is edited directly
		 * </ul>
		 */
		private void facetedProjectModified(IProject project) {
			debug("Facet PROJECT_MODIFIED: ", project.getName()); //$NON-NLS-1$
			GenericJpaModel.this.checkForJpaFacetTransition(project);
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}

	}


	// ********** Java element change listener **********

	/**
	 * Forward the Java element change event back to the JPA model.
	 */
	private class JavaElementChangeListener implements IElementChangedListener {
		/**
		 * A flag to activate/deactivate the listener
		 * so we can ignore Java events whenever Dali is manipulating the Java
		 * source code via the Dali model. We do this because the 0.5 sec delay
		 * between the Java source being changed and the corresponding event
		 * being fired causes us no end of pain.
		 */
		private volatile boolean active = true;

		JavaElementChangeListener() {
			super();
		}

		public void elementChanged(ElementChangedEvent event) {
			if (this.active) {
				GenericJpaModel.this.javaElementChanged(event);
			}
		}

		void setActive(boolean active) {
			this.active = active;
		}

		boolean isActive() {
			return this.active;
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}

	}


	// ********** DEBUG **********

	// @see JpaModelTests#testDEBUG()
	private static final boolean DEBUG = false;

	/**
	 * trigger #toString() call and string concatenation only if DEBUG is true
	 */
	/* private */ static void debug(String message, Object object) {
		if (DEBUG) {
			debug_(message + object);
		}
	}

	/* private */ static void debug(String message) {
		if (DEBUG) {
			debug_(message);
		}
	}

	private static void debug_(String message) {
		System.out.println(Thread.currentThread().getName() + ": " + message); //$NON-NLS-1$
	}

	/* private */ static void dumpStackTrace() {
		dumpStackTrace(null);
	}

	/* private */ static void dumpStackTrace(String message, Object object) {
		if (DEBUG) {
			dumpStackTrace_(message + object);
		}
	}

	/* private */ static void dumpStackTrace(String message) {
		if (DEBUG) {
			dumpStackTrace_(message);
		}
	}

	private static void dumpStackTrace_(String message) {
		// lock System.out so the stack elements are printed out contiguously
		synchronized (System.out) {
			if (message != null) {
				debug_(message);
			}
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			// skip the first 3 elements - those are this method and 2 methods in Thread
			for (int i = 3; i < stackTrace.length; i++) {
				StackTraceElement element = stackTrace[i];
				if (element.getMethodName().equals("invoke0")) { //$NON-NLS-1$
					break;  // skip all elements outside of the JUnit test
				}
				System.out.println("\t" + element); //$NON-NLS-1$
			}
		}
	}

}
