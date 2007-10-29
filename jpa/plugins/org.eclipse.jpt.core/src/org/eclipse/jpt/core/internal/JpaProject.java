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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaResourceModel;
import org.eclipse.jpt.core.internal.resource.java.JpaCompilationUnitResource;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.CommandExecutor;
import org.eclipse.jpt.utility.internal.CommandExecutorProvider;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.model.ChangeEventDispatcher;
import org.eclipse.jpt.utility.internal.model.DefaultChangeEventDispatcher;
import org.eclipse.jpt.utility.internal.node.Node;

/**
 * 
 */
public class JpaProject extends JpaNodeModel implements IJpaProject 
{
	/**
	 * The Eclipse project corresponding to the JPA project.
	 */
	protected final IProject project;

	/**
	 * The vendor-specific JPA platform that builds the JPA project
	 * and all its contents.
	 */
	protected final IJpaPlatform jpaPlatform;

	/**
	 * The data source that wraps the DTP model.
	 */
	protected final IJpaDataSource dataSource;

	/**
	 * The JPA files associated with the JPA project.
	 */
	protected final Vector<IJpaFile> jpaFiles;

	/**
	 * Flag indicating whether the project should "discover" annotated
	 * classes automatically, as opposed to requiring the classes to be
	 * listed in persistence.xml.
	 */
	protected boolean discoversAnnotatedClasses;
	
	/**
	 * The model representing the collated resources associated with this JPA project.
	 */
	protected IContextModel contextModel;

	/**
	 * The visitor passed to resource deltas.
	 */
	protected final IResourceDeltaVisitor resourceDeltaVisitor;

	/**
	 * The dispatcher can be changed so it is UI-aware.
	 */
	protected volatile ChangeEventDispatcher changeEventDispatcher;

	/**
	 * Support for modifying documents shared with the UI.
	 */
	protected final ThreadLocal<CommandExecutor> threadLocalModifySharedDocumentCommandExecutor;
	protected final CommandExecutorProvider modifySharedDocumentCommandExecutorProvider;

	/**
	 * Scheduler that uses a job to update the JPA project when changes occur.
	 * @see #update()
	 */
	protected final UpdateJpaProjectJobScheduler updateJpaProjectJobScheduler;


	// ********** constructor/initialization **********

	/**
	 * The project and JPA platform are required.
	 */
	public JpaProject(IJpaProject.Config config) throws CoreException {
		super(null);  // JPA project is the root of the containment tree
		if ((config.project() == null) || (config.jpaPlatform() == null)) {
			throw new NullPointerException();
		}
		this.project = config.project();
		this.jpaPlatform = config.jpaPlatform();
		this.dataSource = this.jpaFactory().createJpaDataSource(this, config.connectionProfileName());
		this.discoversAnnotatedClasses = config.discoverAnnotatedClasses();
		this.jpaFiles = this.buildJpaFiles();

		this.resourceDeltaVisitor = this.buildResourceDeltaVisitor();
		this.changeEventDispatcher = this.buildChangeEventDispatcher();
		this.threadLocalModifySharedDocumentCommandExecutor = this.buildThreadLocalModifySharedDocumentCommandExecutor();
		this.modifySharedDocumentCommandExecutorProvider = this.buildModifySharedDocumentCommandExecutorProvider();

		this.updateJpaProjectJobScheduler = this.buildUpdateJpaProjectJobScheduler();
		// build the JPA files corresponding to the Eclipse project's files
		this.project.accept(this.buildInitialResourceProxyVisitor(), IResource.NONE);

		this.contextModel = jpaFactory().buildContextModel(this);
		
		this.update();
	}

	@Override
	protected void checkParent(Node parentNode) {
		if (parentNode != null) {
			throw new IllegalArgumentException("The parent node must be null");
		}
	}

	protected Vector<IJpaFile> buildJpaFiles() {
		return new Vector<IJpaFile>();
	}

	protected IResourceDeltaVisitor buildResourceDeltaVisitor() {
		return new ResourceDeltaVisitor();
	}

	protected ChangeEventDispatcher buildChangeEventDispatcher() {
		return DefaultChangeEventDispatcher.instance();
	}

	protected ThreadLocal<CommandExecutor> buildThreadLocalModifySharedDocumentCommandExecutor() {
		return new ThreadLocal<CommandExecutor>();
	}

	protected CommandExecutorProvider buildModifySharedDocumentCommandExecutorProvider() {
		return new ModifySharedDocumentCommandExecutorProvider();
	}

	protected IResourceProxyVisitor buildInitialResourceProxyVisitor() {
		return new InitialResourceProxyVisitor();
	}

	protected UpdateJpaProjectJobScheduler buildUpdateJpaProjectJobScheduler() {
		return new UpdateJpaProjectJobScheduler(this, this.project);
	}

	// ***** inner class
	protected class InitialResourceProxyVisitor implements IResourceProxyVisitor {
		protected InitialResourceProxyVisitor() {
			super();
		}
		// add a JPA file for every [appropriate] file encountered by the visitor
		public boolean visit(IResourceProxy resource) throws CoreException {
			switch (resource.getType()) {
				case IResource.ROOT :  // shouldn't happen
				case IResource.PROJECT :
				case IResource.FOLDER :
					return true;  // visit children
				case IResource.FILE :
					JpaProject.this.addJpaFile((IFile) resource.requestResource());
					return false;  // no children
				default :
					return false;  // no children
			}
		}
	}


	// ********** general queries **********

	@Override
	public IJpaProject root() {
		return this;
	}

	public IProject project() {
		return this.project;
	}

	public String name() {
		return this.project.getName();
	}

	public IJavaProject javaProject() {
		return JavaCore.create(this.project);
	}

	@Override
	public IJpaPlatform jpaPlatform() {
		return this.jpaPlatform;
	}

	public IJpaDataSource dataSource() {
		return this.dataSource;
	}

	@Override
	public ConnectionProfile connectionProfile() {
		return this.dataSource.getConnectionProfile();
	}

	@Override
	public Validator validator() {
		return NULL_VALIDATOR;
	}

	@Override
	public void toString(StringBuffer sb) {
		sb.append(this.name());
	}


	// ********** JPA files **********

	public Iterator<IJpaFile> jpaFiles() {
		return new CloneIterator<IJpaFile>(this.jpaFiles);  // read-only
	}

	public IJpaFile jpaFile(IFile file) {
		synchronized (this.jpaFiles) {
			for (IJpaFile jpaFile : this.jpaFiles) {
				if (jpaFile.getFile().equals(file)) {
					return jpaFile;
				}
			}
		}
		return null;
	}

	public Iterator<IJpaFile> jpaFiles(final String resourceType) {
		return new FilteringIterator<IJpaFile>(this.jpaFiles()) {
			@Override
			protected boolean accept(Object o) {
				return ((IJpaFile) o).getResourceType().equals(resourceType);
			}
		};
	}

	public Iterator<IJpaFile> javaJpaFiles() {
		return this.jpaFiles(IResourceModel.JAVA_RESOURCE_TYPE);
	}

	/**
	 * Add a JPA file for the specified file, if appropriate.
	 */
	protected void addJpaFile(IFile file) {
		IJpaFile jpaFile = this.jpaPlatform.buildJpaFile(this, file);
		if (jpaFile != null) {
			this.addItemToCollection(jpaFile, this.jpaFiles, JPA_FILES_COLLECTION);
		}
	}

	/**
	 * Remove the specified JPA file and dispose it.
	 */
	protected void removeJpaFile(IJpaFile jpaFile) {
		if ( ! this.removeItemFromCollection(jpaFile, this.jpaFiles, JPA_FILES_COLLECTION)) {
			throw new IllegalArgumentException("JPA file: " + jpaFile.getFile().getName());
		}
		jpaFile.dispose();
	}

	protected boolean containsJpaFile(IFile file) {
		return (this.jpaFile(file) != null);
	}
	
	
	// **************** context model *****************************************
	
	/**
	 * @see IJpaProject#getContextModel()
	 */
	public IContextModel contextModel() {
		return contextModel;
	}
	
	void setContextModel(IContextModel contextModel) {
		this.contextModel = contextModel;
	}
	
	
	// ********** change event dispatcher **********

	@Override
	public ChangeEventDispatcher changeEventDispatcher() {
		return this.changeEventDispatcher;
	}

	@Override
	public void setChangeEventDispatcher(ChangeEventDispatcher changeEventDispatcher) {
		this.changeEventDispatcher = changeEventDispatcher;
	}


	// ********** more queries **********

	protected Iterator<JpaCompilationUnitResource> jpaCompilationUnitResources() {
		return new TransformationIterator<IJpaFile, JpaCompilationUnitResource>(this.javaJpaFiles()) {
			@Override
			protected JpaCompilationUnitResource transform(IJpaFile jpaFile) {
				return ((JavaResourceModel) jpaFile.getResourceModel()).getCompilationUnitResource();
			}
		};
	}

	public JavaPersistentTypeResource javaPersistentTypeResource(String fullyQualifiedTypeName) {
		for (JpaCompilationUnitResource jpCompilationUnitResource : CollectionTools.iterable(this.jpaCompilationUnitResources())) {
			JavaPersistentTypeResource jptr =  jpCompilationUnitResource.javaPersistentTypeResource(fullyQualifiedTypeName);
			if (jptr != null) {
				return jptr;
			}
		}
		return null;
	}


	// ********** Java change **********

	public void javaElementChanged(ElementChangedEvent event) {
		for (Iterator<IJpaFile> stream = this.jpaFiles(); stream.hasNext(); ) {
			stream.next().javaElementChanged(event);
		}
	}


	// ********** validation **********

	@SuppressWarnings("restriction")
	public Iterator<org.eclipse.wst.validation.internal.provisional.core.IMessage> validationMessages() {
		List<org.eclipse.wst.validation.internal.provisional.core.IMessage> messages = new ArrayList<org.eclipse.wst.validation.internal.provisional.core.IMessage>();
		this.jpaPlatform.addToMessages(messages);
		return messages.iterator();
	}


	// ********** root deploy location **********

	@SuppressWarnings("restriction")
	protected static final String WEB_PROJECT_ROOT_DEPLOY_LOCATION = org.eclipse.jst.j2ee.internal.J2EEConstants.WEB_INF_CLASSES;

	public String rootDeployLocation() {
		return this.isWebProject() ? WEB_PROJECT_ROOT_DEPLOY_LOCATION : "";
	}

	@SuppressWarnings("restriction")
	protected static final String JST_WEB_MODULE = org.eclipse.wst.common.componentcore.internal.util.IModuleConstants.JST_WEB_MODULE;

	protected boolean isWebProject() {
		return JptCorePlugin.projectHasWebFacet(this.project);
	}


	// ********** auto-discovery **********

	public boolean discoversAnnotatedClasses() {
		return this.discoversAnnotatedClasses;
	}

	public void setDiscoversAnnotatedClasses(boolean discoversAnnotatedClasses) {
		boolean old = this.discoversAnnotatedClasses;
		this.discoversAnnotatedClasses = discoversAnnotatedClasses;
		this.firePropertyChanged(DISCOVERS_ANNOTATED_CLASSES_PROPERTY, old, discoversAnnotatedClasses);
	}


	// ********** dispose **********

	public void dispose() {
		this.updateJpaProjectJobScheduler.dispose();
		// use clone iterator while deleting JPA files
		for (Iterator<IJpaFile> stream = this.jpaFiles(); stream.hasNext(); ) {
			this.removeJpaFile(stream.next());
		}
		this.dataSource.dispose();
	}


	// ********** handling resource deltas **********

	public void checkForAddedOrRemovedJpaFiles(IResourceDelta delta) throws CoreException {
		delta.accept(this.resourceDeltaVisitor);
	}

	/**
	 * resource delta visitor callback
	 */
	protected void synchronizeJpaFiles(IFile file, int deltaKind) {
		switch (deltaKind) {
			case IResourceDelta.ADDED :
				if ( ! this.containsJpaFile(file)) {
					this.addJpaFile(file);
				}
				break;
			case IResourceDelta.REMOVED :
				IJpaFile jpaFile = this.jpaFile(file);
				if (jpaFile != null) {
					this.removeJpaFile(jpaFile);
				}
				break;
			case IResourceDelta.CHANGED :
			case IResourceDelta.ADDED_PHANTOM :
			case IResourceDelta.REMOVED_PHANTOM :
			default :
				break;  // only worried about added and removed files
		}
	}

	// ***** inner class
	/**
	 * add a JPA file for every [appropriate] file encountered by the visitor
	 */
	protected class ResourceDeltaVisitor implements IResourceDeltaVisitor {
		protected ResourceDeltaVisitor() {
			super();
		}
		public boolean visit(IResourceDelta delta) throws CoreException {
			IResource res = delta.getResource();
			switch (res.getType()) {
				case IResource.ROOT :
				case IResource.PROJECT :
				case IResource.FOLDER :
					return true;  // visit children
				case IResource.FILE :
					JpaProject.this.synchronizeJpaFiles((IFile) res, delta.getKind());
					return false;  // no children
				default :
					return false;  // no children
			}
		}
	}


	// ********** support for modifying documents shared with the UI **********

	/**
	 * If there is no thread-specific command executor, use the default
	 * implementation, which simply executes the command directly.
	 */
	protected CommandExecutor threadLocalModifySharedDocumentCommandExecutor() {
		CommandExecutor ce = this.threadLocalModifySharedDocumentCommandExecutor.get();
		return (ce != null) ? ce : CommandExecutor.Default.instance();
	}

	public void setThreadLocalModifySharedDocumentCommandExecutor(CommandExecutor commandExecutor) {
		this.threadLocalModifySharedDocumentCommandExecutor.set(commandExecutor);
	}

	public CommandExecutorProvider modifySharedDocumentCommandExecutorProvider() {
		return this.modifySharedDocumentCommandExecutorProvider;
	}

	// ***** inner class
	protected class ModifySharedDocumentCommandExecutorProvider implements CommandExecutorProvider {
		protected ModifySharedDocumentCommandExecutorProvider() {
			super();
		}
		public CommandExecutor commandExecutor() {
			return JpaProject.this.threadLocalModifySharedDocumentCommandExecutor();
		}
	}



	// ********** update project **********

	public IStatus update(IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			return Status.CANCEL_STATUS;
		}

		try {
			contextModel().update(monitor);
		} catch (OperationCanceledException ex) {
			return Status.CANCEL_STATUS;
		} catch (Throwable ex) {
			//exceptions can occur when this thread is running and changes are
			//made to the java source.  our model is not yet updated to the changed java source.
			//log these exceptions and assume they won't happen when the resynch runs again
			//as a result of the java source changes.
			JptCorePlugin.log(ex);
		}
		return Status.OK_STATUS;
	}

	public void update() {
		if (contextModel() != null) {
			contextModel().update(null); //TODO put this back on a job, running synchronously for tests
			//this.updateJpaProjectJobScheduler.schedule();
		}
	}

	protected static class UpdateJpaProjectJobScheduler {
		/**
		 * The job is built during construction and cleared out during dispose.
		 * All the "public" methods check to make sure the job is not null,
		 * doing nothing if it is (preventing anything from happening after
		 * dispose).
		 */
		protected Job job;

		protected UpdateJpaProjectJobScheduler(IJpaProject jpaProject, ISchedulingRule rule) {
			super();
			this.job = this.buildJob(jpaProject, rule);
		}

		protected Job buildJob(IJpaProject jpaProject, ISchedulingRule rule) {
			Job j = new UpdateJpaProjectJob(jpaProject);
			j.setRule(rule);
			return j;
		}

		/**
		 * Stop the job if it is currently running, reschedule it to
		 * run again, and return without waiting.
		 */
		protected synchronized void schedule() {
			if (this.job != null) {
				this.job.cancel();
				this.job.schedule();
			}
		}

		/**
		 * Stop the job if it is currently running, reschedule it to
		 * run again, and wait until it is finished.
		 */
		protected synchronized void scheduleAndWait() {
			if (this.job != null) {
				this.job.cancel();
				this.join();
				this.job.schedule();
				this.join();
			}
		}

		/**
		 * Stop the job if it is currently running, wait until
		 * it is finished, then clear the job out so it cannot
		 * be scheduled again.
		 */
		protected synchronized void dispose() {
			if (this.job != null) {
				this.job.cancel();
				this.join();
				this.job = null;
			}
		}

		protected synchronized void join() {
			try {
				this.job.join();
			} catch (InterruptedException ex) {
				// the thread was interrupted while waiting, job must be finished
			}
		}

		protected static class UpdateJpaProjectJob extends Job {
			protected final IJpaProject jpaProject;

			protected UpdateJpaProjectJob(IJpaProject jpaProject) {
				super("Update JPA project");  // TODO i18n
				this.jpaProject = jpaProject;
			}

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				return this.jpaProject.update(monitor);
			}

		}

	}
}
