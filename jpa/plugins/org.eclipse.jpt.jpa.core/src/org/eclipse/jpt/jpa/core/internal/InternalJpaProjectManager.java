/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import java.util.HashSet;
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
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.internal.JptCommonCoreMessages;
import org.eclipse.jpt.common.core.internal.utility.command.CommandJobCommandAdapter;
import org.eclipse.jpt.common.core.internal.utility.command.SimpleJobCommandExecutor;
import org.eclipse.jpt.common.core.internal.utility.command.SingleUseQueueingExtendedJobCommandExecutor;
import org.eclipse.jpt.common.core.utility.command.ExtendedJobCommandExecutor;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.utility.BooleanReference;
import org.eclipse.jpt.common.utility.ExceptionHandler;
import org.eclipse.jpt.common.utility.ModifiableObjectReference;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.ExtendedCommandExecutor;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.command.ThreadLocalExtendedCommandExecutor;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SnapshotCloneIterable;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.jpa.core.JpaFacet;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * The JPA project manager maintains a list of all the JPA projects in the
 * workspace. It keeps the list (and the state of the JPA projects themselves)
 * synchronized with the workspace by listening for Resource and Java change
 * events.
 * <p>
 * We use Eclipse {@link ISchedulingRule scheduling rules} to synchronize
 * access to the JPA projects when dealing with these events. In an effort to
 * reduce deadlocks, the Resource and Java change events are dispatched to
 * background jobs, allowing us to handle the events outside of the workspace
 * lock held during Resource and Java change notifications. The
 * {@link ISchedulingRule scheduling rules} are also used to synchronize the
 * event handling with the various other asynchronous Dali activities:<ul>
 * <li>{@link org.eclipse.jpt.jpa.core.internal.validation.JpaValidator Validation}
 * <li>JPA Project context model {@link JpaProject#synchronizeContextModel()
 *     <em>synchronization</em>} [with the resource model]
 * <li>JPA Project {@link org.eclipse.jpt.jpa.core.internal.AbstractJpaProject#update()
 *     <em>update</em>}
 * </ul>
 * Any method that returns a value (e.g. {@link #waitToGetJpaProjects()}) is
 * "synchronized" with the background jobs. This allows any outstanding events
 * to be handled <em>before</em> the value is returned.
 * <p>
 * Various things that cause us to add or remove a JPA project:<ul>
 * <li>The {@link JptJpaCorePlugin} will "lazily" instantiate and
 *     {@link #start() start} a JPA project manager as appropriate.
 *     This will trigger the manager to find and add all pre-existing
 *     JPA projects.
 * 
 * <li>Project created and facet installed<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 * <li>Project facet uninstalled<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 * 
 * <li>Project opened<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}<p>
 *     -> {@link IResource#FILE}<p>
 *     -> {@link IResourceDelta#ADDED}<p>
 *     facet settings file<p>
 *     (<code>.settings/org.eclipse.wst.common.project.facet.core.xml</code>)
 * <li>Project closed<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}<p>
 *     -> {@link IResource#FILE}<p>
 *     -> {@link IResourceDelta#REMOVED}
 *     <p>facet settings file
 * 
 * <li>Pre-existing project imported from directory or archive (created and opened)<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}<p>
 *     -> {@link IResource#FILE}<p>
 *     -> {@link IResourceDelta#ADDED}<p>
 *     facet settings file
 * <li>Project renamed<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}<p>
 *     -> {@link IResource#FILE}<p>
 *     -> {@link IResourceDelta#REMOVED}<p>
 *     facet settings file of old project<p>
 *     -> {@link IResourceDelta#ADDED}<p>
 *     facet settings file of new project
 * <li>Project deleted<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}<p>
 *     -> {@link IResource#FILE}<p>
 *     -> {@link IResourceDelta#REMOVED}<p>
 *     facet settings file
 * 
 * <li>Project facet installed by editing the facets settings file directly<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}<p>
 *     -> {@link IResource#FILE}<p>
 *     -> {@link IResourceDelta#CHANGED}<p>
 *     facet settings file
 * <li>Project facet uninstalled by editing the facets settings file directly<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}<p>
 *     -> {@link IResource#FILE}<p>
 *     -> {@link IResourceDelta#CHANGED}<p>
 *     facet settings file
 * </ul>
 */
public class InternalJpaProjectManager
	extends AbstractModel
	implements JpaProjectManager, JpaProject.Manager
{
	/**
	 * The workspace the JPA project manager corresponds to.
	 */
	private final IWorkspace workspace;

	/**
	 * Determine how commands (Resource and Java change events etc.) are
	 * handled (i.e. synchronously or asynchronously).
	 * The default command executor executes commands asynchronously via
	 * Eclipse {@link Job jobs}.
	 */
	private volatile ExtendedJobCommandExecutor commandExecutor;

	/**
	 * All the JPA projects in the workspace.
	 */
	private final Vector<JpaProject> jpaProjects = new Vector<JpaProject>();

	/**
	 * Listen for<ul>
	 * <li>changes to<ul>
	 *     <li>projects
	 *     <li>package fragment roots
	 *     <li>files
	 *     </ul>
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
	 * Listen for Java changes (unless the Dali UI is active).
	 * @see #asyncEventListenersAreActive()
	 */
	private final JavaElementChangeListener javaElementChangeListener = new JavaElementChangeListener();

	/**
	 * The types of Java element change events that interest
	 * {@link #javaElementChangeListener}.
	 */
	private static final int JAVA_CHANGE_EVENT_TYPES =
			ElementChangedEvent.POST_CHANGE |
			ElementChangedEvent.POST_RECONCILE;

	/**
	 * A set of flags to activate/deactivate the asynchronous event listeners.
	 * <p>
	 * This set of flags hacks around our problems with bi-directional updates.
	 * We must worry about changes to the Java source code that are
	 * initiated by the Dali resource model because there is no way to either
	 * receive the corresponding Java events <em>synchronously</em> or suppress
	 * them. We need <em>not</em> worry about changes initiated by the Java
	 * source code since they are handled by the Java resource model in a way
	 * that does not return any changes back to the Java source code.
	 * <p>
	 * We can ignore Java events whenever Dali is manipulating the Java
	 * source code via the Dali model. We do this because the 0.5 sec delay
	 * between the Java source being changed in the Java Editor and the
	 * corresponding event being fired causes us no end of pain....
	 * <p>
	 * <strong>NB:</strong> The following may not be accurate in Eclipse 4.x....
	 * <p>
	 * Fortunately we <em>will</em> be active whenever a Java source file is
	 * saved, even when <Ctrl+S> is pressed from within a Dali composite. This
	 * is because, when the file is saved, the editor, presumably,
	 * takes the focus temporarily, activating this listener. Then, while this
	 * listener is active, we receive a Java change event for the compilation
	 * unit being saved. This allows us to re-cache our resource model text
	 * ranges so they are in sync with the Java source before execution of the
	 * validation job. Once the Java source file is saved, the Dali composite
	 * re-takes the focus and this listener is once again inactive.
	 */
	private final HashSet<BooleanReference> asyncEventListenerFlags = new HashSet<BooleanReference>();

	/**
	 * Support for modifying documents shared with the UI.
	 */
	private final ThreadLocalExtendedCommandExecutor modifySharedDocumentCommandExecutor = new ThreadLocalExtendedCommandExecutor();

	/**
	 * Log any exceptions appropriately.
	 */
	private final ExceptionHandler exceptionHandler;

	/**
	 * See org.eclipse.jpt.jpa.core.tests.JptJpaCoreTestsPlugin#start(org.osgi.framework.BundleContext).
	 */
	/* CU private */ volatile boolean test = false;


	// ********** constructor **********

	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptJpaCorePlugin#getJpaProjectManager_(IWorkspace) Dali plug-in}.
	 */
	public InternalJpaProjectManager(IWorkspace workspace) {
		super();
		this.workspace = workspace;
		this.exceptionHandler = this.buildExceptionHandler();
	}

	private ExceptionHandler buildExceptionHandler() {
		return new LocalExceptionHandler();
	}


	// ********** plug-in controlled life-cycle **********

	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptJpaCorePlugin#getJpaProjectManager_(IWorkspace) Dali plug-in}.
	 * The JPA project manager will not be returned to any clients by the
	 * {@link JptJpaCorePlugin Dali plug-in} until <em>after</em>
	 * the manager has been started (i.e. this method has been called).
	 * As a result, we need not synchronize with the scheduling rule.
	 */
	public void start() {
		debug("*** JPA project manager START ***"); //$NON-NLS-1$
		try {
			this.commandExecutor = this.buildAsynchronousCommandExecutor();
			this.buildJpaProjects();
			this.workspace.addResourceChangeListener(this.resourceChangeListener, RESOURCE_CHANGE_EVENT_TYPES);
			JavaCore.addElementChangedListener(this.javaElementChangeListener, JAVA_CHANGE_EVENT_TYPES);
		} catch (RuntimeException ex) {
			this.log(ex);
			this.stop();
		}
	}

	/**
	 * Internal: Called <em>only</em> by the
	 * {@link JptJpaCorePlugin#stop(org.osgi.framework.BundleContext) Dali plug-in}.
	 * The JPA project manager will <em>not</em> be restarted.
	 */
	public void stop() {
		debug("*** JPA project manager STOP ***"); //$NON-NLS-1$
		JavaCore.removeElementChangedListener(this.javaElementChangeListener);
		this.workspace.removeResourceChangeListener(this.resourceChangeListener);
		this.clearJpaProjects();
		// if the current executor is async, commands can continue to execute
		// after we replace it here, but there will be no JPA projects for them to process...
		this.commandExecutor = ExtendedJobCommandExecutor.Inactive.instance();
		debug("*** JPA project manager DEAD ***"); //$NON-NLS-1$
	}


	// ********** build JPA projects **********

	/**
	 * The JPA projects are built asynchronously in a job.
	 * Side-effect: {@link #jpaProjects} populated.
	 */
	private void buildJpaProjects() {
		debug("dispatch: build JPA projects"); //$NON-NLS-1$
		BuildJpaProjectsCommand command = new BuildJpaProjectsCommand();
		this.execute(command, JptCoreMessages.BUILD_JPA_PROJECTS_JOB_NAME, this.getWorkspaceRoot());
	}

	/* CU private */ class BuildJpaProjectsCommand
		implements JobCommand
	{
		public IStatus execute(IProgressMonitor monitor) {
			InternalJpaProjectManager.this.buildJpaProjects_(monitor);
			return Status.OK_STATUS;
		}
	}

	/* CU private */ void buildJpaProjects_(IProgressMonitor monitor) {
		debug("execute: build JPA projects"); //$NON-NLS-1$
		try {
			this.getWorkspaceRoot().accept(new ResourceProxyVisitor(monitor), IResource.NONE);
		} catch (Exception ex) {
			// if we have a problem, leave the currently built JPA projects in
			// place and keep executing (should be OK...)
			this.log(ex);
		}
		debug("end: build JPA projects"); //$NON-NLS-1$
	}


	// ********** clear JPA projects **********

	/**
	 * <strong>NB:</strong>
	 * {@link IJobManager#beginRule(ISchedulingRule, IProgressMonitor)}
	 * will jump <em>ahead</em> of any job scheduled with a conflicting rule(!).
	 * We should only use this method of synchronization in {@link #stop()}.
	 */
	private void clearJpaProjects() {
		try {
			this.getJobManager().beginRule(this.getWorkspaceRoot(), null);
			this.clearJpaProjects_();
		} finally {
			this.getJobManager().endRule(this.getWorkspaceRoot());
		}
	}

	private void clearJpaProjects_() {
		debug("execute: clear JPA projects"); //$NON-NLS-1$
		// clone the collection to prevent concurrent modification exception
		for (JpaProject jpaProject : this.getJpaProjects()) {
			this.removeJpaProject(jpaProject);
		}
		debug("end: clear JPA projects"); //$NON-NLS-1$
	}


	// ********** get JPA projects **********

	public Iterable<JpaProject> waitToGetJpaProjects() throws InterruptedException {
		debug("dispatch: get JPA projects"); //$NON-NLS-1$
		GetJpaProjectsCommand command = new GetJpaProjectsCommand();
		this.waitToExecute(command, JptCoreMessages.GET_JPA_PROJECTS_JOB_NAME, this.getWorkspaceRoot());
		return command.result;
	}

	/* CU private */ class GetJpaProjectsCommand
		implements Command
	{
		Iterable<JpaProject> result;
		public void execute() {
			this.result = InternalJpaProjectManager.this.getJpaProjects_();
		}
	}

	/**
	 * Pre-condition: called from {@link GetJpaProjectsCommand#execute()}
	 */
	/* CU private */ Iterable<JpaProject> getJpaProjects_() {
		debug("execute: get JPA projects: {0}", this.jpaProjects); //$NON-NLS-1$
		// clone the JPA projects immediately, while we have the lock
		return this.getJpaProjects();
	}

	public Iterable<JpaProject> getJpaProjects() {
		return new SnapshotCloneIterable<JpaProject>(this.jpaProjects);
	}

	public int getJpaProjectsSize() {
		return this.jpaProjects.size();
	}


	// ********** get JPA project **********

	/**
	 * @see ProjectAdapterFactory.JpaProjectReference#getValue()
	 */
	JpaProject waitToGetJpaProject(IProject project) throws InterruptedException {
		debug("dispatch: get JPA project: {0}", project.getName()); //$NON-NLS-1$
		GetJpaProjectCommand command = new GetJpaProjectCommand(project);
		this.waitToExecute(command, JptCoreMessages.GET_JPA_PROJECT_JOB_NAME, project);
		return command.result;
	}

	/**
	 * Not needed...?
	 * It's nigh pointless to put a time-out on this call, since there are many
	 * things that can trigger a time-out (e.g. validation). Either the client
	 * wants to wait for the JPA project and uses a
	 * {@link org.eclipse.jpt.jpa.core.JpaProject.Reference}
	 * or the client can use the event notification mechanism to be notified
	 * when the JPA project shows up....
	 */
	// @see ProjectAdapterFactory.JpaProjectReference#getValue(ModifiableObjectReference, long)
	@SuppressWarnings("unused")
	private boolean waitToGetJpaProject(ModifiableObjectReference<JpaProject> jpaProjectRef, IProject project, long timeout) throws InterruptedException {
		debug("dispatch: get JPA project (time-out): {0}", project.getName()); //$NON-NLS-1$
		GetJpaProjectCommand command = new GetJpaProjectCommand(project);
		boolean result = this.waitToExecute(command, JptCoreMessages.GET_JPA_PROJECT_JOB_NAME, project, timeout);
		if (result) {
			jpaProjectRef.setValue(command.result);
		} else {
			debug("time-out: get JPA project: {0}", project.getName()); //$NON-NLS-1$
		}
		return result;
	}

	/* CU private */ class GetJpaProjectCommand
		implements Command
	{
		private final IProject project;
		JpaProject result;
		GetJpaProjectCommand(IProject project) {
			this.project = project;
		}
		public void execute() {
			this.result = InternalJpaProjectManager.this.getJpaProjectUnsafe(this.project);
		}
	}

	/**
	 * Pre-condition: called from {@link GetJpaProjectCommand#execute()}
	 */
	/* CU private */ JpaProject getJpaProjectUnsafe(IProject project) {
		debug("execute: get JPA project: {0}", project.getName()); //$NON-NLS-1$
		// no need to clone here, since we have the lock
		JpaProject jpaProject = selectJpaProject(this.jpaProjects, project);
		if (jpaProject == null) {
			debug("not found: get JPA project: {0}", project.getName()); //$NON-NLS-1$
		}
		return jpaProject;
	}

	/**
	 * Called from {@link ProjectAdapterFactory#getJpaProject(IProject)}.
	 */
	JpaProject getJpaProject_(IProject project) {
		return selectJpaProject(this.getJpaProjects(), project);
	}

	private static JpaProject selectJpaProject(Iterable<JpaProject> jpaProjects, IProject project) {
		for (JpaProject jpaProject : jpaProjects) {
			if (jpaProject.getProject().equals(project)) {
				return jpaProject;
			}
		}
		return null;
	}


	// ********** rebuild JPA project **********

	/**
	 * @see ProjectAdapterFactory.JpaProjectReference#rebuild()
	 */
	// e.g. changed JPA platform
	JpaProject rebuildJpaProject(IProject project) throws InterruptedException {
		debug("dispatch: rebuild JPA project: {0}", project.getName()); //$NON-NLS-1$
		RebuildJpaProjectCommand command = new RebuildJpaProjectCommand(project);
		this.waitToExecute(command, JptCoreMessages.REBUILD_JPA_PROJECT_JOB_NAME, project);
		return command.result;
	}

	/* CU private */ class RebuildJpaProjectCommand
		implements JobCommand
	{
		private final IProject project;
		JpaProject result;
		RebuildJpaProjectCommand(IProject project) {
			this.project = project;
		}
		public IStatus execute(IProgressMonitor monitor) {
			this.result = InternalJpaProjectManager.this.rebuildJpaProject_(this.project, monitor);
			return Status.OK_STATUS;
		}
	}

	/* CU private */ JpaProject rebuildJpaProject_(IProject project, IProgressMonitor monitor) {
		debug("execute: rebuild JPA project: {0}", project.getName()); //$NON-NLS-1$
		this.removeJpaProject(this.getJpaProject_(project));
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
		return this.addJpaProject(project);
	}


	// ********** build validation messages **********

	/**
	 * @see ProjectAdapterFactory.JpaProjectReference#buildValidationMessages(IReporter)
	 */
	Iterable<IMessage> buildValidationMessages(IProject project, IReporter reporter) throws InterruptedException {
		debug("dispatch: build validation messages: {0}", project.getName()); //$NON-NLS-1$
		BuildValidationMessagesCommand command = new BuildValidationMessagesCommand(project, reporter);
		this.waitToExecute(command, JptCoreMessages.BUILD_VALIDATION_MESSAGES_JOB_NAME, project);
		return command.result;
	}

	/* CU private */ class BuildValidationMessagesCommand
		implements Command
	{
		private final IProject project;
		private final IReporter reporter;
		Iterable<IMessage> result;
		BuildValidationMessagesCommand(IProject project, IReporter reporter) {
			this.project = project;
			this.reporter = reporter;
		}
		public void execute() {
			this.result = InternalJpaProjectManager.this.buildValidationMessages_(this.project, this.reporter);
		}
	}

	/* CU private */ Iterable<IMessage> buildValidationMessages_(IProject project, IReporter reporter) {
		debug("execute: build validation messages: {0}", project.getName()); //$NON-NLS-1$
		JpaProject jpaProject = this.getJpaProject_(project);
		if (jpaProject == null) {
			return this.buildNoJpaProjectMessages(project);
		}
		try {
			return jpaProject.getValidationMessages(reporter);
		} catch (RuntimeException ex) {
			this.log(ex);
			return EmptyIterable.<IMessage>instance();
		}
	}

	private Iterable<IMessage> buildNoJpaProjectMessages(IProject project) {
		return new SingleElementIterable<IMessage>(this.buildNoJpaProjectMessage(project));
	}

	private IMessage buildNoJpaProjectMessage(IProject project) {
		return DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.NO_JPA_PROJECT,
					project
				);
	}


	// ********** add JPA project **********

	/**
	 * Pre-condition: The specified project is locked.
	 * If there are any problems building the JPA project,
	 * log the exception and do not add anything to the manager.
	 * Return the newly-created JPA project.
	 */
	/* CU private */ JpaProject addJpaProject(IProject project) {
		JpaProject jpaProject = this.buildJpaProject(project);
		// figure out exactly when JPA projects are added
		if (jpaProject == null) {
			dumpStackTrace("add JPA project fail: {0}", project); //$NON-NLS-1$
		} else {
			dumpStackTrace("add JPA project: {0}", jpaProject); //$NON-NLS-1$
		}
		// the JPA project will be null if we have any problems building it...
		// (e.g. if we have problems getting the JPA platform)
		if (jpaProject != null) {
			this.addItemToCollection(jpaProject, this.jpaProjects, JPA_PROJECTS_COLLECTION);
		}
		return jpaProject;
	}

	/**
	 * Return <code>null</code> if we have any problems....
	 */
	private JpaProject buildJpaProject(IProject project) {
		return this.buildJpaProject(this.buildJpaProjectConfig(project));
	}

	/**
	 * Return <code>null</code> if we have any problems....
	 */
	private JpaProject buildJpaProject(JpaProject.Config config) {
		return this.buildJpaProject(config.getJpaPlatform(), config);
	}

	/**
	 * Return <code>null</code> if we have any problems....
	 */
	private JpaProject buildJpaProject(JpaPlatform jpaPlatform, JpaProject.Config config) {
		try {
			return (jpaPlatform == null) ? null : jpaPlatform.getJpaFactory().buildJpaProject(config);
		} catch (RuntimeException ex) {
			this.log(ex);
			return null;
		}
	}

	private JpaProject.Config buildJpaProjectConfig(IProject project) {
		SimpleJpaProjectConfig config = new SimpleJpaProjectConfig();
		config.setJpaProjectManager(this);
		config.setProject(project);
		config.setJpaPlatform(JptJpaCorePlugin.getJpaPlatformManager().buildJpaPlatformImplementation(project));
		config.setConnectionProfileName(JptJpaCorePlugin.getConnectionProfileName(project));
		config.setUserOverrideDefaultCatalog(JptJpaCorePlugin.getUserOverrideDefaultCatalog(project));
		config.setUserOverrideDefaultSchema(JptJpaCorePlugin.getUserOverrideDefaultSchema(project));
		config.setDiscoverAnnotatedClasses(JptJpaCorePlugin.getDiscoverAnnotatedClasses(project));
		config.setMetamodelSourceFolderName(JptJpaCorePlugin.getMetamodelSourceFolderName(project));
		return config;
	}


	// ********** remove JPA project **********

	/**
	 * Pre-condition: The specified JPA project's project is locked.
	 */
	private void removeJpaProject(JpaProject jpaProject) {
		// figure out exactly when JPA projects are removed
		dumpStackTrace("remove JPA project: {0}", jpaProject); //$NON-NLS-1$
		this.removeItemFromCollection(jpaProject, this.jpaProjects, JPA_PROJECTS_COLLECTION);
		try {
			jpaProject.dispose();
		} catch (RuntimeException ex) {
			this.log(ex);
		}
	}


	// ********** Project POST_CHANGE **********

	/* CU private */ void projectChanged(IResourceDelta delta) {
		debug("dispatch: project changed: {0}", delta.getResource()); //$NON-NLS-1$
		ProjectChangeEventHandlerCommand command = new ProjectChangeEventHandlerCommand(delta);
		this.execute(command, JptCoreMessages.PROJECT_CHANGE_EVENT_HANDLER_JOB_NAME, this.getWorkspaceRoot());
	}

	/* CU private */ class ProjectChangeEventHandlerCommand
		implements JobCommand
	{
		private final IResourceDelta delta;
		ProjectChangeEventHandlerCommand(IResourceDelta delta) {
			this.delta = delta;
		}
		public IStatus execute(IProgressMonitor monitor) {
			InternalJpaProjectManager.this.projectChanged_(this.delta, monitor);
			return Status.OK_STATUS;
		}
	}

	/**
	 * Forward the specified resource delta to <em>all</em> the JPA projects;
	 * they will each determine whether the event is significant.
	 */
	/* CU private */ void projectChanged_(IResourceDelta delta, IProgressMonitor monitor) {
		debug("execute: project changed: {0}", delta.getResource()); //$NON-NLS-1$
//		debug("execute: project changed: ", ((org.eclipse.core.internal.events.ResourceDelta) delta).toDeepDebugString()); //$NON-NLS-1$
		for (JpaProject jpaProject : this.jpaProjects) {
			try {
				jpaProject.projectChanged(delta);
			} catch (RuntimeException ex) {
				this.log(ex);
			}
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
		}
	}


	// ********** Project POST_BUILD (CLEAN_BUILD) **********

	/* CU private */ void projectPostCleanBuild(IProject project) {
		debug("dispatch: post clean build: {0}", project.getName()); //$NON-NLS-1$
		ProjectPostCleanBuildEventHandlerCommand command = new ProjectPostCleanBuildEventHandlerCommand(project);
		this.execute(command, JptCoreMessages.PROJECT_POST_CLEAN_BUILD_EVENT_HANDLER_JOB_NAME, project);
	}

	/* CU private */ class ProjectPostCleanBuildEventHandlerCommand
		implements JobCommand
	{
		private final IProject project;
		ProjectPostCleanBuildEventHandlerCommand(IProject project) {
			this.project = project;
		}
		public IStatus execute(IProgressMonitor monitor) {
			InternalJpaProjectManager.this.projectPostCleanBuild_(this.project, monitor);
			return Status.OK_STATUS;
		}
	}

	/* CU private */ void projectPostCleanBuild_(IProject project, IProgressMonitor monitor) {
		debug("execute: post clean build: {0}", project.getName()); //$NON-NLS-1$
		JpaProject jpaProject = this.getJpaProject_(project);
		if (jpaProject != null) {
			this.removeJpaProject(jpaProject);
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
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
		debug("dispatch: project facet file changed: {0}", project.getName()); //$NON-NLS-1$
		FacetFileChangeEventHandlerCommand command = new FacetFileChangeEventHandlerCommand(project);
		this.execute(command, JptCoreMessages.FACET_FILE_CHANGE_EVENT_HANDLER_JOB_NAME, project);
	}

	/* CU private */ class FacetFileChangeEventHandlerCommand
		implements Command
	{
		private final IProject project;
		FacetFileChangeEventHandlerCommand(IProject project) {
			this.project = project;
		}
		public void execute() {
			InternalJpaProjectManager.this.checkForJpaFacetTransition_(this.project);
		}
	}

	/* CU private */ void checkForJpaFacetTransition_(IProject project) {
		debug("execute: project facet file changed: {0}", project.getName()); //$NON-NLS-1$
		JpaProject jpaProject = this.getJpaProject_(project);

		if (JpaFacet.isInstalled(project)) {
			if (jpaProject == null) {  // JPA facet added
				this.addJpaProject(project);
			}
		} else {
			if (jpaProject != null) {  // JPA facet removed
				this.removeJpaProject(jpaProject);
			}
		}
	}


	// ********** Java element changed **********

	/* CU private */ void javaElementChanged(ElementChangedEvent event) {
		if (this.handleJavaElementChangedEvent(event)) {
			debug("dispatch: Java element changed: {0}", event.getDelta()); //$NON-NLS-1$
			JavaChangeEventHandlerCommand command = new JavaChangeEventHandlerCommand(event);
			this.execute(command, JptCoreMessages.JAVA_CHANGE_EVENT_HANDLER_JOB_NAME, this.getWorkspaceRoot());
		}
	}

	/* CU private */ class JavaChangeEventHandlerCommand
		implements JobCommand
	{
		private final ElementChangedEvent event;
		JavaChangeEventHandlerCommand(ElementChangedEvent event) {
			this.event = event;
		}
		public IStatus execute(IProgressMonitor monitor) {
			InternalJpaProjectManager.this.javaElementChanged_(this.event, monitor);
			return Status.OK_STATUS;
		}
	}

	/**
	 * Forward the Java element changed event to <em>all</em> the JPA projects
	 * as the event could affect multiple projects.
	 */
	/* CU private */ void javaElementChanged_(ElementChangedEvent event, IProgressMonitor monitor) {
		debug("execute: Java element changed: {0}", event.getDelta()); //$NON-NLS-1$
		for (JpaProject jpaProject : this.jpaProjects) {
			try {
				jpaProject.javaElementChanged(event);
			} catch (RuntimeException ex) {
				this.log(ex);
			}
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
		}
	}

	/**
	 * Check to see if we should even handle this particular java event. If it
	 * needs to be handled then we will kick off our 'JPA Java Change Event Handler' job.
	 * If any change in the event needs to be handled, we short-circuit out and
	 * return true. Each JpaProject then makes the same checks to determine
	 * which changes are of concern. We are trying to limit the number
	 * of 'JPA Java Change Event Handler' jobs that are run.
	 * <br> <b>
	 * This code was copied and modified from AbstractJpaProject, so make sure
	 * to make changes in both locations.
	 * </b>
	 * @see AbstractJpaProject#processJavaDelta(IJavaElementDelta)
	 */
	private boolean handleJavaElementChangedEvent(ElementChangedEvent event) {
		return this.handleJavaDelta(event.getDelta());
	}

	/**
	 * We recurse back here from {@link #handleJavaDeltaChildren(IJavaElementDelta)}.
	 */
	protected boolean handleJavaDelta(IJavaElementDelta delta) {
		switch (delta.getElement().getElementType()) {
			case IJavaElement.JAVA_MODEL :
				return this.handleJavaModelDelta(delta);
			case IJavaElement.JAVA_PROJECT :
				return this.handleJavaProjectDelta(delta);
			case IJavaElement.PACKAGE_FRAGMENT_ROOT :
				return this.handleJavaPackageFragmentRootDelta(delta);
			case IJavaElement.PACKAGE_FRAGMENT :
				return this.processJavaPackageFragmentDelta(delta);
			case IJavaElement.COMPILATION_UNIT :
				return this.handleJavaCompilationUnitDelta(delta);
			default :
				break; // ignore the elements inside a compilation unit
		}
		return false;
	}

	protected boolean handleJavaDeltaChildren(IJavaElementDelta delta) {
		for (IJavaElementDelta child : delta.getAffectedChildren()) {
			if (this.handleJavaDelta(child)) { // recurse 
				return true;
			}
		}
		return false;
	}

	// ***** model
	protected boolean handleJavaModelDelta(IJavaElementDelta delta) {
		// process the Java model's projects
		return this.handleJavaDeltaChildren(delta);
	}

	// ***** project
	protected boolean handleJavaProjectDelta(IJavaElementDelta delta) {
		// process the Java project's package fragment roots
		return this.handleJavaDeltaChildren(delta) ||
				AbstractJpaProject.classpathHasChanged(delta);
	}

	// ***** package fragment root
	protected boolean handleJavaPackageFragmentRootDelta(IJavaElementDelta delta) {
		// process the Java package fragment root's package fragments
		return this.handleJavaDeltaChildren(delta) ||
				AbstractJpaProject.classpathEntryHasBeenAdded(delta) ||
				AbstractJpaProject.classpathEntryHasBeenRemoved(delta);
	}

	// ***** package fragment
	protected boolean processJavaPackageFragmentDelta(IJavaElementDelta delta) {
		// process the java package fragment's compilation units
		return this.handleJavaDeltaChildren(delta);
	}

	// ***** compilation unit
	protected boolean handleJavaCompilationUnitDelta(IJavaElementDelta delta) {
		// ignore the java compilation unit's children
		return AbstractJpaProject.javaCompilationUnitDeltaIsRelevant(delta);
	}


	// ********** support for modifying documents shared with the UI **********

	public ExtendedCommandExecutor getModifySharedDocumentCommandExecutor() {
		return this.modifySharedDocumentCommandExecutor;
	}

	private void setThreadLocalModifySharedDocumentCommandExecutor(ExtendedCommandExecutor commandExecutor) {
		this.modifySharedDocumentCommandExecutor.set(commandExecutor);
	}


	// ********** logging **********

	public void log(String msg) {
		JptJpaCorePlugin.log(msg);
	}

	public void log(Throwable throwable) {
		JptJpaCorePlugin.log(throwable);
	}

	public void log(String msg, Throwable throwable) {
		JptJpaCorePlugin.log(msg, throwable);
	}

	public ExceptionHandler getExceptionHandler() {
		return this.exceptionHandler;
	}

	/* CU private */ class LocalExceptionHandler
		implements ExceptionHandler
	{
		public void handleException(Throwable t) {
			InternalJpaProjectManager.this.log(t);
			if (InternalJpaProjectManager.this.test) {
				t.printStackTrace();
			}
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}


	// ********** misc **********

	public IWorkspace getWorkspace() {
		return this.workspace;
	}

	private IWorkspaceRoot getWorkspaceRoot() {
		return this.workspace.getRoot();
	}

	private ISchedulingRule getCurrentRule() {
		return this.getJobManager().currentRule();
	}

	private IJobManager getJobManager() {
		return Job.getJobManager();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getJpaProjects());
	}


	// ********** command execution **********

	/**
	 * Make sure the JPA project is still around when the command reaches the
	 * front of the command queue.
	 * <p>
	 * Called by JPA project <em>synchronizes</em> and <em>updates</em>:
	 * @see org.eclipse.jpt.jpa.core.internal.AbstractJpaProject.ManagerJobCommandExecutor#execute(JobCommand, String)
	 */
	public void execute(JobCommand command, String jobName, JpaProject jpaProject) {
		debug("dispatch: client command: {0}", command); //$NON-NLS-1$
		this.execute(new ClientJobCommandWrapper(command, jpaProject), jobName, jpaProject.getProject());
	}

	private void execute(Command command, String jobName, ISchedulingRule schedulingRule) {
		this.execute(new CommandJobCommandAdapter(command), jobName, schedulingRule);
	}

	private void execute(JobCommand command, String jobName, ISchedulingRule schedulingRule) {
		this.commandExecutor.execute(command, jobName, schedulingRule);
	}

	private void waitToExecute(Command command, String jobName, ISchedulingRule schedulingRule) throws InterruptedException {
		this.waitToExecute(new CommandJobCommandAdapter(command), jobName, schedulingRule);
	}

	private void waitToExecute(JobCommand command, String jobName, ISchedulingRule schedulingRule) throws InterruptedException {
		this.waitToExecute(command, jobName, schedulingRule, 0);
	}

	private boolean waitToExecute(Command command, String jobName, ISchedulingRule schedulingRule, long timeout) throws InterruptedException {
		return this.waitToExecute(new CommandJobCommandAdapter(command), jobName, schedulingRule, timeout);
	}

	/**
	 * Check whether the specified scheduling rule
	 * {@link ISchedulingRule#isConflicting(ISchedulingRule) conflicts} with the
	 * {@link IJobManager#currentRule() current rule}. If the rules conflict,
	 * execute the specified command directly (i.e. synchronously) to prevent a
	 * deadlock. This should not cause a problem because if the current rule
	 * conflicts with the specified rule, the current rule will also prevent any
	 * other, conflicting, JPA project manager commands from executing.
	 */
	private boolean waitToExecute(JobCommand command, String jobName, ISchedulingRule schedulingRule, long timeout) throws InterruptedException {
		ISchedulingRule currentRule = this.getCurrentRule();
		if ((currentRule != null) && schedulingRule.isConflicting(currentRule)) {
			dumpStackTrace("scheduling rule conflict: {0} vs. {1}", schedulingRule, currentRule); //$NON-NLS-1$
			command.execute(new NullProgressMonitor());
			return true;
		}
		return this.commandExecutor.waitToExecute(command, jobName, schedulingRule, timeout);
	}

	public void execute(Command command) throws InterruptedException {
		this.execute(command, null);
	}

	public void execute(Command command, ExtendedCommandExecutor threadLocalModifySharedDocumentCommandExecutor) throws InterruptedException {
		this.setThreadLocalModifySharedDocumentCommandExecutor(threadLocalModifySharedDocumentCommandExecutor);
		this.executeCommandsSynchronously();
		try {
			command.execute();
		} finally {
			this.executeCommandsAsynchronously();
		}
		// not really necessary - thread locals are GCed
		this.setThreadLocalModifySharedDocumentCommandExecutor(null);
	}

	/**
	 * <strong>NB:</strong>
	 * This method is called (via reflection) when the test plug-in is loaded.
	 * This is only useful during tests because none of the files are open in
	 * the UI, so all the Java events come to us synchronously (i.e. without the
	 * 0.5 second delay).
	 * <p>
	 * See org.eclipse.jpt.jpa.core.tests.JptJpaCoreTestsPlugin#start(org.osgi.framework.BundleContext).
	 * 
	 * @see #executeCommandsAsynchronously()
	 */
	private synchronized void executeCommandsSynchronously() throws InterruptedException {
		if ( ! (this.commandExecutor instanceof SimpleJobCommandExecutor)) {
			throw new IllegalStateException();
		}

		// de-activate async (Java) events
		this.addAsyncEventListenerFlag(BooleanReference.False.instance());
		// save the current executor
		SimpleJobCommandExecutor old = (SimpleJobCommandExecutor) this.commandExecutor;
		// install a new (not-yet-started) executor
		SingleUseQueueingExtendedJobCommandExecutor newCE = this.buildSynchronousCommandExecutor();
		this.commandExecutor = newCE;
		// wait for all the outstanding commands to finish
		old.waitToExecute(Command.Null.instance());
		// start up the new executor (it will now execute any commands that
		// arrived while we were waiting on the outstanding commands)
		newCE.start();
	}

	private SingleUseQueueingExtendedJobCommandExecutor buildSynchronousCommandExecutor() {
		return new SingleUseQueueingExtendedJobCommandExecutor();
	}

	private synchronized void executeCommandsAsynchronously() {
		if ( ! (this.commandExecutor instanceof SingleUseQueueingExtendedJobCommandExecutor)) {
			throw new IllegalStateException();
		}

		// no need to wait on a synchronous executor...
		this.commandExecutor = this.buildAsynchronousCommandExecutor();
		// re-activate async (Java) events
		this.removeAsyncEventListenerFlag(BooleanReference.False.instance());
	}

	private SimpleJobCommandExecutor buildAsynchronousCommandExecutor() {
		return new SimpleJobCommandExecutor(JptCommonCoreMessages.DALI_JOB_NAME);
	}


	// ********** job command wrapper **********

	/* CU private */ class ClientJobCommandWrapper
		implements JobCommand
	{
		private final JobCommand jobCommand;
		private final JpaProject jpaProject;

		ClientJobCommandWrapper(JobCommand jobCommand, JpaProject jpaProject) {
			super();
			if ((jobCommand == null) || (jpaProject == null)) {
				throw new NullPointerException();
			}
			this.jobCommand = jobCommand;
			this.jpaProject = jpaProject;
		}

		public IStatus execute(IProgressMonitor monitor) {
			InternalJpaProjectManager.this.execute_(this.jobCommand, monitor, this.jpaProject);
			return Status.OK_STATUS;
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.jobCommand);
		}
	}

	/**
	 * Execute the specified command only if the specified JPA project is still
	 * around. (i.e. The JPA project may have been removed between the time the
	 * client requested the JPA project manager to execute the command and the
	 * time the command reached the front of the execution queue.)
	 * Called by {@link ClientJobCommandWrapper#execute(IProgressMonitor)}.
	 */
	/* CU private */ void execute_(JobCommand command, IProgressMonitor monitor, JpaProject jpaProject) {
		if (this.jpaProjects.contains(jpaProject)) {
			debug("execute: client command: {0}", command); //$NON-NLS-1$
			command.execute(monitor);
		} else {
			debug("ignore: client command: {0}", command); //$NON-NLS-1$
		}
	}


	// ********** resource proxy visitor **********

	/**
	 * Visit the workspace resource tree, adding a JPA project to the
	 * JPA project manager for each open Eclipse project that has a JPA facet.
	 */
	/* CU private */ class ResourceProxyVisitor
		implements IResourceProxyVisitor
	{
		private final IProgressMonitor monitor;

		ResourceProxyVisitor(IProgressMonitor monitor) {
			super();
			this.monitor = monitor;
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
					InternalJpaProjectManager.this.addJpaProject(project);
				}
			}
			if (this.monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}


	// ********** resource change listener **********

	/* CU private */ class ResourceChangeListener
		implements IResourceChangeListener
	{
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
			debug("Resource POST_CHANGE event: {0}", event.getResource()); //$NON-NLS-1$
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
		 * Process the project <em>first</em> for the "opening" project case; so
		 * the JPA project will not be built until the children are processed
		 * and we see that the facet metadata file is added.
		 * Otherwise the JPA project would be built and <em>then</em> we would
		 * process the <code>ADDED</code> deltas for all the files in
		 * the project we would've discovered during JPA project construction
		 * (i.e. we would throw an exception because we would try to add a file
		 * to the JPA project that it already contained).
		 */
		private void processPostChangeProjectDelta(IResourceDelta delta) {
			InternalJpaProjectManager.this.projectChanged(delta);
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
					InternalJpaProjectManager.this.checkForJpaFacetTransition(file.getProject());
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
		 * Check for whether the build was a "clean" build and completely
		 * rebuild the appropriate JPA project.
		 */
		// ***** POST_BUILD
		private void processPostBuildEvent(IResourceChangeEvent event) {
			debug("Resource POST_BUILD event: {0}", event.getDelta().getResource()); //$NON-NLS-1$
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
			debug("\tProject CLEAN event: {0}", project.getName()); //$NON-NLS-1$
			InternalJpaProjectManager.this.projectPostCleanBuild(project);
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
	/* CU private */ class JavaElementChangeListener
		implements IElementChangedListener
	{
		public void elementChanged(ElementChangedEvent event) {
			if (this.isActive()) {
				InternalJpaProjectManager.this.javaElementChanged(event);
			}
		}

		private boolean isActive() {
			return InternalJpaProjectManager.this.asyncEventListenersAreActive();
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.isActive() ? "active" : "inactive"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}


	// ********** async events **********

	boolean asyncEventListenersAreActive() {
		synchronized (this.asyncEventListenerFlags) {
			return this.asyncEventListenersAreActive_();
		}
	}

	/**
	 * All the flags must be <code>true</code>.
	 */
	private boolean asyncEventListenersAreActive_() {
		for (BooleanReference flag : this.asyncEventListenerFlags) {
			if (flag.isFalse()) {
				return false;
			}
		}
		return true;
	}

	public void addAsyncEventListenerFlag(BooleanReference flag) {
		synchronized (this.asyncEventListenerFlags) {
			this.addAsyncEventListenerFlag_(flag);
		}
	}

	private void addAsyncEventListenerFlag_(BooleanReference flag) {
		if ( ! this.asyncEventListenerFlags.add(flag)) {
			throw new IllegalArgumentException("duplicate flag: " + flag); //$NON-NLS-1$
		}
	}

	public void removeAsyncEventListenerFlag(BooleanReference flag) {
		synchronized (this.asyncEventListenerFlags) {
			this.removeAsyncEventListenerFlag_(flag);
		}
	}

	private void removeAsyncEventListenerFlag_(BooleanReference flag) {
		if ( ! this.asyncEventListenerFlags.remove(flag)) {
			throw new IllegalArgumentException("missing flag: " + flag); //$NON-NLS-1$
		}
	}


	// ********** DEBUG **********

	/* CU private */ static void debug(String message) {
		debug(message, EMPTY_OBJECT_ARRAY);
	}
	private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

	/* CU private */ static void debug(String message, Object... args) {
		if (debug()) {
			debug_(message, args);
		}
	}

	private static void debug_(String message, Object... args) {
		JptJpaCorePlugin.instance().log(IStatus.INFO, NLS.bind(message, args));
	}

	private static boolean debug() {
		return JptJpaCorePlugin.instance().getBooleanDebugOption(DEBUG_OPTION);
	}
	private static final String DEBUG_OPTION = JpaProjectManager.class.getSimpleName();
	private static final String DEBUG_OPTION_ = DEBUG_OPTION + '.';

	/* CU private */ static void dumpStackTrace(String message) {
		dumpStackTrace(message, EMPTY_OBJECT_ARRAY);
	}

	/* CU private */ static void dumpStackTrace(String message, Object... args) {
		if (debug()) {
			dumpStackTrace_(message, args);
		}
	}

	private static void dumpStackTrace_(String message, Object... args) {
		Exception ex = debugStackTrace() ? new Exception() : null;
		JptJpaCorePlugin.instance().log(IStatus.INFO, NLS.bind(message, args), ex);
	}

	private static boolean debugStackTrace() {
		return JptJpaCorePlugin.instance().getBooleanDebugOption(DEBUG_STACK_TRACE_OPTION);
	}
	private static final String DEBUG_STACK_TRACE_OPTION = DEBUG_OPTION_ + "stack_trace"; //$NON-NLS-1$
}
