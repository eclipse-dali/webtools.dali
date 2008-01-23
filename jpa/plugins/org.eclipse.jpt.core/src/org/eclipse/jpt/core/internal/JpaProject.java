/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.internal.node.Node;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.wst.common.componentcore.internal.util.IModuleConstants;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 */
public class JpaProject extends JpaNode implements IJpaProject {

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
	 * The model representing the collated resources associated
	 * with the JPA project.
	 */
	protected IContextModel contextModel;

	/**
	 * The visitor passed to resource deltas.
	 */
	protected final IResourceDeltaVisitor resourceDeltaVisitor;

	/**
	 * Support for modifying documents shared with the UI.
	 */
	protected final ThreadLocal<CommandExecutor> threadLocalModifySharedDocumentCommandExecutor;
	protected final CommandExecutorProvider modifySharedDocumentCommandExecutorProvider;

	/**
	 * A pluggable updater that can be used to "update" the project either
	 * synchronously or asynchronously (or not at all). An asynchronous
	 * updater is the default and is used when the project is being manipulated
	 * by the UI. A synchronous updater is used when the project is being
	 * manipulated by a "batch" (or non-UI) client (e.g. when testing the
	 * "update" behavior). A null updater is used during tests that
	 * do not care whether "updates" occur. Clients will need to explicitly
	 * configure the updater if they require something other than an
	 * asynchronous updater.
	 */
	protected Updater updater;

	/**
	 * Resource models notify this listener when they change. A project update
	 * should occur any time a resource model changes.
	 */
	protected IResourceModelListener resourceModelListener;


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
		this.jpaFiles = this.buildEmptyJpaFiles();

		this.resourceDeltaVisitor = this.buildResourceDeltaVisitor();
		this.threadLocalModifySharedDocumentCommandExecutor = this.buildThreadLocalModifySharedDocumentCommandExecutor();
		this.modifySharedDocumentCommandExecutorProvider = this.buildModifySharedDocumentCommandExecutorProvider();

		this.updater = this.buildUpdater();

		this.resourceModelListener = this.buildResourceModelListener();
		// build the JPA files corresponding to the Eclipse project's files
		this.project.accept(this.buildInitialResourceProxyVisitor(), IResource.NONE);

		this.contextModel = this.buildContextModel();

		this.update();
	}

	@Override
	protected void checkParent(Node parentNode) {
		if (parentNode != null) {
			throw new IllegalArgumentException("The parent node must be null");
		}
	}

	protected Vector<IJpaFile> buildEmptyJpaFiles() {
		return new Vector<IJpaFile>();
	}

	protected IResourceDeltaVisitor buildResourceDeltaVisitor() {
		return new ResourceDeltaVisitor();
	}

	protected ThreadLocal<CommandExecutor> buildThreadLocalModifySharedDocumentCommandExecutor() {
		return new ThreadLocal<CommandExecutor>();
	}

	protected CommandExecutorProvider buildModifySharedDocumentCommandExecutorProvider() {
		return new ModifySharedDocumentCommandExecutorProvider();
	}

	protected Updater buildUpdater() {
		return new AsynchronousJpaProjectUpdater(this);
	}

	protected IResourceModelListener buildResourceModelListener() {
		return new ResourceModelListener();
	}

	protected IResourceProxyVisitor buildInitialResourceProxyVisitor() {
		return new InitialResourceProxyVisitor();
	}

	protected IContextModel buildContextModel() {
		return this.jpaFactory().buildContextModel(this);
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
					JpaProject.this.addJpaFileInternal((IFile) resource.requestResource());
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
		return this.dataSource.connectionProfile();
	}

	@Override
	public Validator validator() {
		return NULL_VALIDATOR;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name());
	}


	// ********** JPA files **********

	public Iterator<IJpaFile> jpaFiles() {
		return new CloneIterator<IJpaFile>(this.jpaFiles);  // read-only
	}

	public int jpaFilesSize() {
		return this.jpaFiles.size();
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
		IJpaFile jpaFile = this.addJpaFileInternal(file);
		if (jpaFile != null) {
			this.fireItemAdded(JPA_FILES_COLLECTION, jpaFile);
			for (Iterator<IJpaFile> stream = this.jpaFiles(); stream.hasNext(); ) {
				stream.next().fileAdded(jpaFile);
			}
		}
	}

	/**
	 * Add a JPA file for the specified file, if appropriate, without firing
	 * an event; useful during construction.
	 * Return the new JPA file, null if it was not created.
	 */
	protected IJpaFile addJpaFileInternal(IFile file) {
		IJpaFile jpaFile = this.jpaPlatform.buildJpaFile(this, file);
		if (jpaFile == null) {
			return null;
		}
		this.jpaFiles.add(jpaFile);
		jpaFile.getResourceModel().addResourceModelChangeListener(this.resourceModelListener);
		return jpaFile;
	}

	/**
	 * Remove the specified JPA file and dispose it.
	 */
	protected void removeJpaFile(IJpaFile jpaFile) {
		jpaFile.getResourceModel().removeResourceModelChangeListener(this.resourceModelListener);
		if ( ! this.removeItemFromCollection(jpaFile, this.jpaFiles, JPA_FILES_COLLECTION)) {
			throw new IllegalArgumentException("JPA file: " + jpaFile.getFile().getName());
		}
		for (Iterator<IJpaFile> stream = this.jpaFiles(); stream.hasNext(); ) {
			stream.next().fileRemoved(jpaFile);
		}
		jpaFile.dispose();
	}

	protected boolean containsJpaFile(IFile file) {
		return (this.jpaFile(file) != null);
	}


	// ********** context model **********

	public IContextModel contextModel() {
		return this.contextModel;
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

	public JavaPersistentTypeResource javaPersistentTypeResource(String typeName) {
		for (JpaCompilationUnitResource jpCompilationUnitResource : CollectionTools.iterable(this.jpaCompilationUnitResources())) {
			JavaPersistentTypeResource jptr =  jpCompilationUnitResource.javaPersistentTypeResource(typeName);
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

	public Iterator<IMessage> validationMessages() {
		List<IMessage> messages = new ArrayList<IMessage>();
		this.jpaPlatform.addToMessages(messages);
		return messages.iterator();
	}


	// ********** root deploy location **********

	protected static final String WEB_PROJECT_ROOT_DEPLOY_LOCATION = J2EEConstants.WEB_INF_CLASSES;

	public String rootDeployLocation() {
		return this.isWebProject() ? WEB_PROJECT_ROOT_DEPLOY_LOCATION : "";
	}

	protected static final String JST_WEB_MODULE = IModuleConstants.JST_WEB_MODULE;

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
		this.updater.dispose();
		// use clone iterator while deleting JPA files
		for (Iterator<IJpaFile> stream = this.jpaFiles(); stream.hasNext(); ) {
			this.removeJpaFile(stream.next());
		}
		this.dataSource.dispose();
	}


	// ********** resource model listener **********

	protected class ResourceModelListener implements IResourceModelListener {
		protected ResourceModelListener() {
			super();
		}
		public void resourceModelChanged() {
			JpaProject.this.update();
		}
	}


	// ********** handling resource deltas **********

	public void synchronizeJpaFiles(IResourceDelta delta) throws CoreException {
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


	// ********** project "update" **********

	public Updater updater() {
		return this.updater;
	}

	public void setUpdater(Updater updater) {
		this.updater = updater;
	}

	/**
	 * Delegate to the updater so clients can configure how updates occur.
	 */
	public void update() {
		this.updater.update();
	}

	/**
	 * Called by the updater.
	 */
	public IStatus update(IProgressMonitor monitor) {
		try {
			this.contextModel.update(monitor);
		} catch (OperationCanceledException ex) {
			return Status.CANCEL_STATUS;
		} catch (Throwable ex) {
			// Exceptions can occur when the update is running and changes are
			// made concurrently to the Java source. When that happens, our
			// model might be in an inconsistent state because it is not yet in
			// sync with the changed Java source.
			// Log these exceptions and assume they won't happen when the
			// update runs again as a result of the concurrent Java source changes.
			JptCorePlugin.log(ex);
		}
		return Status.OK_STATUS;
	}

}
