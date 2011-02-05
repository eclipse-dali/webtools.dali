/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.internal.JptCommonCoreMessages;
import org.eclipse.jpt.common.core.internal.utility.CallbackJobSynchronizer;
import org.eclipse.jpt.common.core.internal.utility.JobCommand;
import org.eclipse.jpt.common.core.internal.utility.JobSynchronizer;
import org.eclipse.jpt.common.utility.Command;
import org.eclipse.jpt.common.utility.internal.AsynchronousCommandExecutor;
import org.eclipse.jpt.common.utility.internal.SimpleCommandExecutor;
import org.eclipse.jpt.common.utility.internal.StatefulCommandExecutor;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.SynchronizedBoolean;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.synchronizers.CallbackSynchronizer;
import org.eclipse.jpt.common.utility.synchronizers.Synchronizer;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.internal.SimpleJpaProjectConfig;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;
import org.eclipse.wst.common.project.facet.core.events.IProjectFacetActionEvent;

/**
 * The JPA project manager maintains a list of all JPA projects in the workspace.
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
 * {@link IResourceChangeEvent#POST_CHANGE}) are handled "synchronously"
 * by allowing the background thread to handle any outstanding events before
 * updating the list of JPA projects and returning execution to the event
 * source.
 * <p>
 * Various things that cause us to add or remove a JPA project:<ul>
 * <li>The {@link JptCorePlugin} will "lazily" instantiate and {@link #start() start}
 *     a JPA project manager as appropriate. This will trigger the manager
 *     to find and add all pre-existing JPA projects.
 * 
 * <li>Project created and facet installed<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 * <li>Project facet uninstalled<p>
 *     {@link IFacetedProjectEvent.Type#PRE_UNINSTALL}
 * 
 * <li>Project opened<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 *     -> {@link IResource#FILE}
 *     -> {@link IResourceDelta#ADDED} facet settings file
 *     (<code>/.settings/org.eclipse.wst.common.project.facet.core.xml</code>)
 * <li>Project closed<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 *     -> {@link IResource#FILE}
 *     -> {@link IResourceDelta#REMOVED} facet settings file
 * 
 * <li>Pre-existing project imported from directory or archive (created and opened)<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 *     -> {@link IResource#FILE}
 *     -> {@link IResourceDelta#ADDED} facet settings file
 * <li>Project renamed<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 *     -> {@link IResource#FILE}
 *     -> {@link IResourceDelta#REMOVED} facet settings file of old project
 *     -> {@link IResourceDelta#ADDED} facet settings file of new project
 * <li>Project deleted<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 *     -> {@link IResource#FILE}
 *     -> {@link IResourceDelta#REMOVED} facet settings file
 * 
 * <li>Project facet installed by editing the facets settings file directly<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 *     -> {@link IResource#FILE}
 *     -> {@link IResourceDelta#CHANGED} facet settings file
 * <li>Project facet uninstalled by editing the facets settings file directly<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 *     -> {@link IResource#FILE}
 *     -> {@link IResourceDelta#CHANGED} facet settings file
 * </ul>
 */
// TODO remove faceted project listener and rely solely on resource change events
// for the faceted project settings file
class GenericJpaProjectManager
	extends AbstractModel
	implements JpaProjectManager
{
	/**
	 * All the JPA projects in the workspace.
	 */
	private final Vector<JpaProject> jpaProjects = new Vector<JpaProject>();

	/**
	 * Synchronize access to the JPA projects.
	 */
	/* CU private */ final ILock lock = this.getJobManager().newLock();

	/**
	 * Determine how Resource and Java change events are
	 * handled (i.e. synchronously or asynchronously).
	 */
	private volatile StatefulCommandExecutor eventHandler = new AsynchronousCommandExecutor(JptCommonCoreMessages.DALI_EVENT_HANDLER_THREAD_NAME);

	/**
	 * Listen for<ul>
	 * <li>changes to projects and files
	 * <li>clean builds
	 * </ul>
	 */
	private final IResourceChangeListener resourceChangeListener = new ResourceChangeListener();

	/**
	 * The types of resource change events that interest
	 * {@link #resourceChangeListener}.
	 */
	private static final int RESOURCE_CHANGE_EVENT_TYPES =
			IResourceChangeEvent.POST_CHANGE |
			IResourceChangeEvent.POST_BUILD;

	/**
	 * Listen for changes to this file to determine when the JPA facet is
	 * added to or removed from a "faceted" project.
	 */
	private static final String FACETED_PROJECT_FRAMEWORK_SETTINGS_FILE_NAME = FacetedProjectFramework.PLUGIN_ID + ".xml"; //$NON-NLS-1$

	/**
	 * Listen for the JPA facet being added to or removed from a "faceted" project.
	 */
	private final IFacetedProjectListener facetedProjectListener = new FacetedProjectListener();

	/**
	 * The types of faceted project events that interest
	 * {@link #facetedProjectListener}.
	 */
	private static final IFacetedProjectEvent.Type[] FACETED_PROJECT_EVENT_TYPES = new IFacetedProjectEvent.Type[] {
			IFacetedProjectEvent.Type.PRE_UNINSTALL
		};

	/**
	 * Listen for Java changes (unless the Dali UI is active).
	 * @see #javaElementChangeListenerIsActive()
	 */
	private final JavaElementChangeListener javaElementChangeListener = new JavaElementChangeListener();

	/**
	 * The types of resource change events that interest
	 * {@link #javaElementChangeListener}.
	 */
	private static final int JAVA_CHANGE_EVENT_TYPES =
			ElementChangedEvent.POST_CHANGE |
			ElementChangedEvent.POST_RECONCILE;


	// ********** constructor **********

	/**
	 * Internal: called by {@link JptCorePlugin Dali plug-in}.
	 */
	GenericJpaProjectManager() {
		super();
	}


	// ********** plug-in controlled life-cycle **********

	/**
	 * Internal: called by {@link JptCorePlugin Dali plug-in}.
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
		debug("*** JPA project manager START ***"); //$NON-NLS-1$
		try {
			this.buildJpaProjects();
			this.eventHandler.start();
			this.getWorkspace().addResourceChangeListener(this.resourceChangeListener, RESOURCE_CHANGE_EVENT_TYPES);
			FacetedProjectFramework.addListener(this.facetedProjectListener, FACETED_PROJECT_EVENT_TYPES);
			JavaCore.addElementChangedListener(this.javaElementChangeListener, JAVA_CHANGE_EVENT_TYPES);
		} catch (RuntimeException ex) {
			JptCorePlugin.log(ex);
			this.stop_();
		}
	}

	/**
	 * Side-effect: {@link #jpaProjects} populated.
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
	 * Internal: called by {@link JptCorePlugin Dali plug-in}.
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
		debug("*** JPA project manager STOP ***"); //$NON-NLS-1$
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


	// ********** JpaProjectManager implementation **********

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

	/* CU private */ void addJpaProject(IProject project) {
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
		jpaProject.setContextModelSynchronizer(this.buildJobContextModelSynchronizer(jpaProject));
		jpaProject.setUpdateSynchronizer(this.buildJobUpdateSynchronizer(jpaProject));
		return jpaProject;
	}

	/**
	 * return null if we have any problems...
	 */
	private JpaProject buildJpaProject(JpaPlatform jpaPlatform, JpaProject.Config config) {
		try {
			return jpaPlatform.getJpaFactory().buildJpaProject(config);
		} catch (RuntimeException ex) {
			JptCorePlugin.log(ex);
			return null;
		}
	}

	private JpaProject.Config buildJpaProjectConfig(IProject project) {
		SimpleJpaProjectConfig config = new SimpleJpaProjectConfig();
		config.setProject(project);
		config.setJpaPlatform(JptCorePlugin.getJpaPlatformManager().buildJpaPlatformImplementation(project));
		config.setConnectionProfileName(JptCorePlugin.getConnectionProfileName(project));
		config.setUserOverrideDefaultCatalog(JptCorePlugin.getUserOverrideDefaultCatalog(project));
		config.setUserOverrideDefaultSchema(JptCorePlugin.getUserOverrideDefaultSchema(project));
		config.setDiscoverAnnotatedClasses(JptCorePlugin.discoverAnnotatedClasses(project));
		config.setMetamodelSourceFolderName(JptCorePlugin.getMetamodelSourceFolderName(project));
		return config;
	}

	private Synchronizer buildJobContextModelSynchronizer(JpaProject jpaProject) {
		return new JobSynchronizer(
				this.buildContextModelJobName(jpaProject),
				this.buildContextModelJobCommand(jpaProject),
				jpaProject.getProject()
			);
	}

	private String buildContextModelJobName(JpaProject jpaProject) {
		return NLS.bind(JptCoreMessages.CONTEXT_MODEL_SYNC_JOB_NAME, jpaProject.getName());
	}

	private JobCommand buildContextModelJobCommand(final JpaProject jpaProject) {
		return new JobCommand() {
			public IStatus execute(IProgressMonitor monitor) {
				return jpaProject.synchronizeContextModel(monitor);
			}
		};
	}

	private CallbackSynchronizer buildJobUpdateSynchronizer(JpaProject jpaProject) {
		return new CallbackJobSynchronizer(
				this.buildUpdateJobName(jpaProject),
				this.buildUpdateJobCommand(jpaProject),
				jpaProject.getProject()
			);
	}

	private String buildUpdateJobName(JpaProject jpaProject) {
		return NLS.bind(JptCoreMessages.UPDATE_JOB_NAME, jpaProject.getName());
	}

	private JobCommand buildUpdateJobCommand(final JpaProject jpaProject) {
		return new JobCommand() {
			public IStatus execute(IProgressMonitor monitor) {
				return jpaProject.update(monitor);
			}
		};
	}

	/* CU private */ void removeJpaProject(JpaProject jpaProject) {
		// figure out exactly when JPA projects are removed
		dumpStackTrace("remove: ", jpaProject); //$NON-NLS-1$
		this.removeItemFromCollection(jpaProject, this.jpaProjects, JPA_PROJECTS_COLLECTION);
		jpaProject.dispose();
	}


	// ********** Project POST_CHANGE **********

	/* CU private */ void projectChanged(IResourceDelta delta) {
		this.eventHandler.execute(this.buildProjectChangedCommand(delta));
	}

	private Command buildProjectChangedCommand(final IResourceDelta delta) {
		return new EventHandlerCommand("Project POST_CHANGE Command") { //$NON-NLS-1$
			@Override
			void execute_() {
				GenericJpaProjectManager.this.projectChanged_(delta);
			}
		};
	}

	/**
	 * Forward the specified resource delta to all our JPA projects;
	 * they will each determine whether the event is significant.
	 */
	/* CU private */ void projectChanged_(IResourceDelta delta) {
		for (JpaProject jpaProject : this.jpaProjects) {
			jpaProject.projectChanged(delta);
		}
	}


	// ********** Project POST_BUILD (CLEAN_BUILD) **********

	/* CU private */ void projectPostCleanBuild(IProject project) {
		this.executeAfterEventsHandled(this.buildProjectPostCleanBuildCommand(project));
	}

	private Command buildProjectPostCleanBuildCommand(final IProject project) {
		return new EventHandlerCommand("Project POST_BUILD (CLEAN_BUILD) Command") { //$NON-NLS-1$
			@Override
			void execute_() {
				GenericJpaProjectManager.this.projectPostCleanBuild_(project);
			}
		};
	}

	/* CU private */ void projectPostCleanBuild_(IProject project) {
		JpaProject jpaProject = this.getJpaProject_(project);
		if (jpaProject != null) {
			this.removeJpaProject(jpaProject);
			this.addJpaProject(project);
		}
	}


	// ********** File POST_CHANGE **********

	/**
	 * The Faceted Project settings file has changed in some fashion, check
	 * whether the JPA facet has been added to or removed from the specified
	 * project.
	 */
	/* CU private */ void checkForJpaFacetTransition(IProject project) {
		JpaProject jpaProject = this.getJpaProject_(project);

		if (JpaFacet.isInstalled(project)) {
			if (jpaProject == null) {  // JPA facet added
				this.executeAfterEventsHandled(this.buildAddJpaProjectCommand(project));
			}
		} else {
			if (jpaProject != null) {  // JPA facet removed
				this.executeAfterEventsHandled(this.buildRemoveJpaProjectCommand(jpaProject));
			}
		}
	}

	private Command buildAddJpaProjectCommand(final IProject project) {
		return new EventHandlerCommand("Add JPA Project Command") { //$NON-NLS-1$
			@Override
			void execute_() {
				GenericJpaProjectManager.this.addJpaProject(project);
			}
		};
	}

	private Command buildRemoveJpaProjectCommand(final JpaProject jpaProject) {
		return new EventHandlerCommand("Remove JPA Project Command") { //$NON-NLS-1$
			@Override
			void execute_() {
				GenericJpaProjectManager.this.removeJpaProject(jpaProject);
			}
		};
	}


	// ********** FacetedProject PRE_UNINSTALL **********

	/* CU private */ void jpaFacetedProjectPreUninstall(IProjectFacetActionEvent event) {
		IProject project = event.getProject().getProject();
		this.executeAfterEventsHandled(this.buildJpaFacetedProjectPreUninstallCommand(project));
	}

	private Command buildJpaFacetedProjectPreUninstallCommand(final IProject project) {
		return new EventHandlerCommand("Faceted Project PRE_UNINSTALL Command") { //$NON-NLS-1$
			@Override
			void execute_() {
				GenericJpaProjectManager.this.jpaFacetedProjectPreUninstall_(project);
			}
		};
	}

	/* CU private */ void jpaFacetedProjectPreUninstall_(IProject project) {
		// assume(?) this is the first event to indicate we need to remove the JPA project from the JPA project manager
		this.removeJpaProject(this.getJpaProject_(project));
	}


	// ********** Java element changed **********

	/* CU private */ void javaElementChanged(ElementChangedEvent event) {
		this.eventHandler.execute(this.buildJavaElementChangedCommand(event));
	}

	private Command buildJavaElementChangedCommand(final ElementChangedEvent event) {
		return new EventHandlerCommand("Java element changed Command") { //$NON-NLS-1$
			@Override
			void execute_() {
				GenericJpaProjectManager.this.javaElementChanged_(event);
			}
		};
	}

	/**
	 * Forward the Java element changed event to all the JPA projects
	 * because the event could affect multiple projects.
	 */
	/* CU private */ void javaElementChanged_(ElementChangedEvent event) {
		for (JpaProject jpaProject : this.jpaProjects) {
			jpaProject.javaElementChanged(event);
		}
	}


	// ********** misc **********

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
		this.eventHandler.execute(new PauseCommand(flag));
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
	 * thread than the JPA project manager:<ol>
	 * <li>it will set the flag to <code>true</code>, allowing the JPA project
	 * manager to resume executing on its own thread
	 * <li>then it will suspend its command executor until the JPA project
	 * manager sets the flag back to <code>false</code>.
	 * </ol>
	 * If this "pause" command is executing (synchronously) on the same thread
	 * as the JPA project manager, it will simply set the flag to
	 * <code>true</code> and return.
	 */
	private static class PauseCommand
		implements Command
	{
		private final Thread producerThread;
		private final SynchronizedBoolean flag;

		PauseCommand(SynchronizedBoolean flag) {
			this(Thread.currentThread(), flag);
		}

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
	 * See JptCoreTestsPlugin#start(BundleContext)
	 */
	@SuppressWarnings("unused")
	private void handleEventsSynchronously() {
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
	 * JPA project manager for each open Eclipse project that has a JPA facet.
	 */
	private class ResourceProxyVisitor
		implements IResourceProxyVisitor
	{
		ResourceProxyVisitor() {
			super();
		}

		public boolean visit(IResourceProxy resourceProxy) {
			switch (resourceProxy.getType()) {
				case IResource.ROOT :
					return true;  // all projects are in the "root"
				case IResource.PROJECT :
					this.processProject(resourceProxy);
					return false;  // no nested projects
				case IResource.FOLDER :
					return false;  // ignore
				case IResource.FILE :
					return false;  // ignore
				default :
					return false;
			}
		}

		private void processProject(IResourceProxy resourceProxy) {
			if (resourceProxy.isAccessible()) {  // the project exists and is open
				IProject project = (IProject) resourceProxy.requestResource();
				if (JpaFacet.isInstalled(project)) {
					GenericJpaProjectManager.this.addJpaProject(project);
				}
			}
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}

	}


	// ********** event handler command **********

	/**
	 * Command that holds the JPA project manager lock while
	 * executing.
	 */
	private abstract class EventHandlerCommand
		implements Command
	{
		private final String name;

		EventHandlerCommand(String name) {
			super();
			this.name = name;
		}

		public final void execute() {
			try {
				GenericJpaProjectManager.this.lock.acquire();
				this.execute_();
			} catch (RuntimeException ex) {
				JptCorePlugin.log(ex);
			} finally {
				GenericJpaProjectManager.this.lock.release();
			}
		}

		abstract void execute_();

		@Override
		public String toString() {
			return this.name;
		}
	}


	// ********** resource change listener **********

	private class ResourceChangeListener
		implements IResourceChangeListener
	{
		ResourceChangeListener() {
			super();
		}

		/**
		 * PRE_UNINSTALL is the only facet event we use for 
		 * removing JPA projects. These are the cases where we listen for resource events.
		 * <p>
		 * Check for:<ul>
		 * <li>facet settings file added/removed/changed
		 * (<code>/.settings/org.eclipse.wst.common.project.facet.core.xml</code>)
		 * <li>file add/remove - forwarded to the individual JPA projects
		 * <li>project clean
		 * </ul>
		 */
		public void resourceChanged(IResourceChangeEvent event) {
			switch (event.getType()) {
				case IResourceChangeEvent.POST_CHANGE :
					this.processPostChangeEvent(event);
					break;

				// workspace or project events
				case IResourceChangeEvent.PRE_REFRESH :
					break;  // ignore
				case IResourceChangeEvent.PRE_BUILD :
					break;  // ignore
				case IResourceChangeEvent.POST_BUILD :
					this.processPostBuildEvent(event);
					break;

				// project-only events
				case IResourceChangeEvent.PRE_CLOSE :  
					break;  // ignore
				case IResourceChangeEvent.PRE_DELETE :
					break;  // ignore
				default :
					break;
			}
		}

		private void processPostChangeEvent(IResourceChangeEvent event) {
			debug("Resource POST_CHANGE"); //$NON-NLS-1$
			this.processPostChangeDelta(event.getDelta());
		}

		private void processPostChangeDelta(IResourceDelta delta) {
			IResource resource = delta.getResource();
			switch (resource.getType()) {
				case IResource.ROOT :
					this.processPostChangeRootDelta(delta);
					break;
				case IResource.PROJECT :
					this.processPostChangeProjectDelta(delta);
					break;
				case IResource.FOLDER :
					this.processPostChangeFolderDelta((IFolder) resource, delta);
					break;
				case IResource.FILE :
					this.processPostChangeFileDelta((IFile) resource, delta);
					break;
				default :
					break;
			}
		}

		// ***** POST_CHANGE ROOT
		private void processPostChangeRootDelta(IResourceDelta delta) {
			this.processPostChangeDeltaChildren(delta);
		}

		// ***** POST_CHANGE PROJECT
		/**
		 * Process the project first for the Opening project case.
		 * The JPA project will not be built until the children are processed
		 * and we see that the facet metadata file is added.
		 * Otherwise the JPA project would be built and then we would process
		 * the ADDED deltas for all the files in the project.
		 */
		private void processPostChangeProjectDelta(IResourceDelta delta) {
			GenericJpaProjectManager.this.projectChanged(delta);
			this.processPostChangeDeltaChildren(delta);
		}

		// ***** POST_CHANGE FOLDER
		private void processPostChangeFolderDelta(IFolder folder, IResourceDelta delta) {
			if (folder.getName().equals(".settings")) { //$NON-NLS-1$
				this.processPostChangeDeltaChildren(delta);
			}
		}

		// ***** POST_CHANGE FILE
		private void processPostChangeFileDelta(IFile file, IResourceDelta delta) {
			if (file.getName().equals(FACETED_PROJECT_FRAMEWORK_SETTINGS_FILE_NAME)) {
				this.checkForFacetFileChanges(file, delta);
			}
		}
		
		private void checkForFacetFileChanges(IFile file, IResourceDelta delta) {
			switch (delta.getKind()) {
				case IResourceDelta.ADDED :
				case IResourceDelta.REMOVED :
				case IResourceDelta.CHANGED : 
					GenericJpaProjectManager.this.checkForJpaFacetTransition(file.getProject());
					break;
				case IResourceDelta.ADDED_PHANTOM :
					break;  // ignore
				case IResourceDelta.REMOVED_PHANTOM :
					break;  // ignore
				default :
					break;
			}
		}

		private void processPostChangeDeltaChildren(IResourceDelta delta) {
			for (IResourceDelta child : delta.getAffectedChildren()) {
				this.processPostChangeDelta(child);  // recurse
			}
		}

		/**
		 * A post build event has occurred.
		 * Check for whether the build was a "clean" build and trigger project update.
		 */
		// ***** POST_BUILD
		private void processPostBuildEvent(IResourceChangeEvent event) {
			debug("Resource POST_BUILD: ", event.getResource()); //$NON-NLS-1$
			if (event.getBuildKind() == IncrementalProjectBuilder.CLEAN_BUILD) {
				this.processPostCleanBuildDelta(event.getDelta());
			}
		}

		private void processPostCleanBuildDelta(IResourceDelta delta) {
			IResource resource = delta.getResource();
			switch (resource.getType()) {
				case IResource.ROOT :
					this.processPostCleanBuildDeltaChildren(delta);
					break;
				case IResource.PROJECT :
					this.processProjectPostCleanBuild((IProject) resource);
					break;
				case IResource.FOLDER :
					break;  // ignore
				case IResource.FILE :
					break;  // ignore
				default :
					break;
			}
		}

		private void processPostCleanBuildDeltaChildren(IResourceDelta delta) {
			for (IResourceDelta child : delta.getAffectedChildren()) {
				this.processPostCleanBuildDelta(child);  // recurse
			}
		}

		private void processProjectPostCleanBuild(IProject project) {
			debug("\tProject CLEAN: ", project.getName()); //$NON-NLS-1$
			GenericJpaProjectManager.this.projectPostCleanBuild(project);
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}

	}


	// ********** faceted project listener **********

	/**
	 * Forward the Faceted project change event back to the JPA project manager.
	 */
	private class FacetedProjectListener
		implements IFacetedProjectListener
	{
		FacetedProjectListener() {
			super();
		}

		/**
		 * Check for:<ul>
		 * <li>un-install of JPA facet
		 * </ul>
		 */
		public void handleEvent(IFacetedProjectEvent event) {
			switch (event.getType()) {
				case PRE_UNINSTALL :
					this.processPreUninstallEvent((IProjectFacetActionEvent) event);
					break;
				default :
					break;
			}
		}

		private void processPreUninstallEvent(IProjectFacetActionEvent event) {
			debug("Facet PRE_UNINSTALL: ", event.getProjectFacet()); //$NON-NLS-1$
			if (event.getProjectFacet().equals(JpaFacet.FACET)) {
				GenericJpaProjectManager.this.jpaFacetedProjectPreUninstall(event);
			}
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}

	}


	// ********** Java element change listener **********

	/**
	 * Forward the Java element change event back to the JPA project manager.
	 */
	private class JavaElementChangeListener
		implements IElementChangedListener
	{
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
				GenericJpaProjectManager.this.javaElementChanged(event);
			}
			// TODO save unhandled events and process when listener is reactivated?
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

	// @see JpaProjectManagerTests#testDEBUG()
	private static final boolean DEBUG = false;

	/**
	 * trigger #toString() call and string concatenation only if DEBUG is true
	 */
	/* CU private */ static void debug(String message, Object object) {
		if (DEBUG) {
			debug_(message + object);
		}
	}

	/* CU private */ static void debug(String message) {
		if (DEBUG) {
			debug_(message);
		}
	}

	private static void debug_(String message) {
		System.out.println(Thread.currentThread().getName() + ": " + message); //$NON-NLS-1$
	}

	/* CU private */ static void dumpStackTrace() {
		dumpStackTrace(null);
	}

	/* CU private */ static void dumpStackTrace(String message, Object object) {
		if (DEBUG) {
			dumpStackTrace_(message + object);
		}
	}

	/* CU private */ static void dumpStackTrace(String message) {
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
