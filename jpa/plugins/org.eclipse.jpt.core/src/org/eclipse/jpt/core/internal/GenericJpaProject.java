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
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.JpaDataSource;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.ResourceModel;
import org.eclipse.jpt.core.ResourceModelListener;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JpaCompilationUnit;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.CommandExecutor;
import org.eclipse.jpt.utility.CommandExecutorProvider;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.wst.common.componentcore.internal.util.IModuleConstants;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 */
public class GenericJpaProject extends AbstractJpaNode implements JpaProject {

	/**
	 * The Eclipse project corresponding to the JPA project.
	 */
	protected final IProject project;

	/**
	 * The vendor-specific JPA platform that builds the JPA project
	 * and all its contents.
	 */
	protected final JpaPlatform jpaPlatform;

	/**
	 * The data source that wraps the DTP model.
	 */
	protected final JpaDataSource dataSource;

	/**
	 * Flag indicating whether the project should "discover" annotated
	 * classes automatically, as opposed to requiring the classes to be
	 * listed in persistence.xml.
	 */
	protected boolean discoversAnnotatedClasses;

	/**
	 * The JPA files associated with the JPA project.
	 */
	protected final Vector<JpaFile> jpaFiles;

	/**
	 * The root of the model representing the collated resources associated with 
	 * the JPA project.
	 */
	protected JpaRootContextNode rootContextNode;

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
	protected ResourceModelListener resourceModelListener;


	// ********** constructor/initialization **********

	/**
	 * The project and JPA platform are required.
	 */
	public GenericJpaProject(JpaProject.Config config) throws CoreException {
		super(null);  // JPA project is the root of the containment tree
		if ((config.getProject() == null) || (config.getJpaPlatform() == null)) {
			throw new NullPointerException();
		}
		this.project = config.getProject();
		this.jpaPlatform = config.getJpaPlatform();
		this.dataSource = this.getJpaFactory().buildJpaDataSource(this, config.getConnectionProfileName());
		this.discoversAnnotatedClasses = config.discoverAnnotatedClasses();
		this.jpaFiles = this.buildEmptyJpaFiles();

		this.resourceDeltaVisitor = this.buildResourceDeltaVisitor();
		this.threadLocalModifySharedDocumentCommandExecutor = this.buildThreadLocalModifySharedDocumentCommandExecutor();
		this.modifySharedDocumentCommandExecutorProvider = this.buildModifySharedDocumentCommandExecutorProvider();

		this.resourceModelListener = this.buildResourceModelListener();
		// build the JPA files corresponding to the Eclipse project's files
		this.project.accept(this.buildInitialResourceProxyVisitor(), IResource.NONE);

		this.rootContextNode = this.buildRootContextNode();
	}

	@Override
	protected boolean requiresParent() {
		return false;
	}
	
	@Override
	public IResource getResource() {
		return getProject();
	}

	protected Vector<JpaFile> buildEmptyJpaFiles() {
		return new Vector<JpaFile>();
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

	protected ResourceModelListener buildResourceModelListener() {
		return new DefaultResourceModelListener();
	}

	protected IResourceProxyVisitor buildInitialResourceProxyVisitor() {
		return new InitialResourceProxyVisitor();
	}

	protected JpaRootContextNode buildRootContextNode() {
		return this.getJpaFactory().buildRootContext(this);
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
					GenericJpaProject.this.addJpaFileInternal((IFile) resource.requestResource());
					return false;  // no children
				default :
					return false;  // no children
			}
		}
	}


	// ********** general queries **********

	@Override
	public JpaProject getJpaProject() {
		return this;
	}

	public String getName() {
		return this.project.getName();
	}

	public IProject getProject() {
		return this.project;
	}

	public IJavaProject getJavaProject() {
		return JavaCore.create(this.project);
	}

	@Override
	public JpaPlatform getJpaPlatform() {
		return this.jpaPlatform;
	}

	public JpaDataSource getDataSource() {
		return this.dataSource;
	}

	@Override
	public ConnectionProfile getConnectionProfile() {
		return this.dataSource.getConnectionProfile();
	}

	public Schema getDefaultSchema() {
		return getConnectionProfile().getDefaultSchema();
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}


	// **************** discover annotated classes *****************************
	
	public boolean discoversAnnotatedClasses() {
		return this.discoversAnnotatedClasses;
	}
	
	public void setDiscoversAnnotatedClasses(boolean discoversAnnotatedClasses) {
		boolean old = this.discoversAnnotatedClasses;
		this.discoversAnnotatedClasses = discoversAnnotatedClasses;
		this.firePropertyChanged(DISCOVERS_ANNOTATED_CLASSES_PROPERTY, old, discoversAnnotatedClasses);
	}
	
	
	// **************** JPA files **********************************************

	public Iterator<JpaFile> jpaFiles() {
		return new CloneIterator<JpaFile>(this.jpaFiles);  // read-only
	}

	public int jpaFilesSize() {
		return this.jpaFiles.size();
	}

	public JpaFile getJpaFile(IFile file) {
		synchronized (this.jpaFiles) {
			for (JpaFile jpaFile : this.jpaFiles) {
				if (jpaFile.getFile().equals(file)) {
					return jpaFile;
				}
			}
		}
		return null;
	}

	public Iterator<JpaFile> jpaFiles(final String resourceType) {
		return new FilteringIterator<JpaFile, JpaFile>(this.jpaFiles()) {
			@Override
			protected boolean accept(JpaFile o) {
				return o.getResourceType().equals(resourceType);
			}
		};
	}

	/**
	 * Add a JPA file for the specified file, if appropriate.
	 */
	protected void addJpaFile(IFile file) {
		JpaFile jpaFile = this.addJpaFileInternal(file);
		if (jpaFile != null) {
			this.fireItemAdded(JPA_FILES_COLLECTION, jpaFile);
			for (Iterator<JpaFile> stream = this.jpaFiles(); stream.hasNext(); ) {
				stream.next().fileAdded(jpaFile);
			}
		}
	}

	/**
	 * Add a JPA file for the specified file, if appropriate, without firing
	 * an event; useful during construction.
	 * Return the new JPA file, null if it was not created.
	 */
	protected JpaFile addJpaFileInternal(IFile file) {
		JpaFile jpaFile = this.jpaPlatform.buildJpaFile(this, file);
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
	protected void removeJpaFile(JpaFile jpaFile) {
		jpaFile.getResourceModel().removeResourceModelChangeListener(this.resourceModelListener);
		if ( ! this.removeItemFromCollection(jpaFile, this.jpaFiles, JPA_FILES_COLLECTION)) {
			throw new IllegalArgumentException("JPA file: " + jpaFile.getFile().getName());
		}
		for (Iterator<JpaFile> stream = this.jpaFiles(); stream.hasNext(); ) {
			stream.next().fileRemoved(jpaFile);
		}
		jpaFile.dispose();
	}

	protected boolean containsJpaFile(IFile file) {
		return (this.getJpaFile(file) != null);
	}


	// ********** context model **********

	public JpaRootContextNode getRootContext() {
		return this.rootContextNode;
	}


	// ********** more queries **********

	public Iterator<IType> annotatedClasses() {
		return new FilteringIterator<IType, IType>(
				new TransformationIterator<JavaResourcePersistentType, IType>(annotatedJavaPersistentTypes()) {
					@Override
					protected IType transform(JavaResourcePersistentType next) {
						try {
							return getJavaProject().findType(next.getQualifiedName(), new NullProgressMonitor());
						}
						catch (JavaModelException jme) {
							return null;
						}
					}
				}) {
			@Override
			protected boolean accept(IType o) {
				return o != null;
			}
		};
	}
	
	protected Iterator<JavaResourcePersistentType> annotatedJavaPersistentTypes() {
		return new FilteringIterator<JavaResourcePersistentType, JavaResourcePersistentType>(
				new TransformationIterator<JpaCompilationUnit, JavaResourcePersistentType>(jpaCompilationUnitResources()) {
					@Override
					protected JavaResourcePersistentType transform(JpaCompilationUnit next) {
						return next.getPersistentType();
					}
				}) {
			@Override
			protected boolean accept(JavaResourcePersistentType persistentType) {
				return persistentType == null ? false : persistentType.isPersisted();
			}
		};
	}
	
	public Iterator<JpaFile> javaJpaFiles() {
		return this.jpaFiles(ResourceModel.JAVA_RESOURCE_TYPE);
	}
	
	protected Iterator<JpaCompilationUnit> jpaCompilationUnitResources() {
		return new TransformationIterator<JpaFile, JpaCompilationUnit>(this.javaJpaFiles()) {
			@Override
			protected JpaCompilationUnit transform(JpaFile jpaFile) {
				return ((JavaResourceModel) jpaFile.getResourceModel()).getResource();
			}
		};
	}

	// look for binary stuff here...
	public JavaResourcePersistentType getJavaPersistentTypeResource(String typeName) {
		for (JpaCompilationUnit jpCompilationUnitResource : CollectionTools.iterable(this.jpaCompilationUnitResources())) {
			JavaResourcePersistentType jptr =  jpCompilationUnitResource.getJavaPersistentTypeResource(typeName);
			if (jptr != null) {
				return jptr;
			}
		}
//		this.javaProject().findType(typeName);
		return null;
	}


	// ********** Java change **********

	public void javaElementChanged(ElementChangedEvent event) {
		for (Iterator<JpaFile> stream = this.jpaFiles(); stream.hasNext(); ) {
			stream.next().javaElementChanged(event);
		}
	}


	// ********** validation **********
	
	public Iterator<IMessage> validationMessages() {
		List<IMessage> messages = new ArrayList<IMessage>();
		this.jpaPlatform.addToMessages(this, messages);
		return messages.iterator();
	}
	
	/* If this is true, it may be assumed that all the requirements are valid 
	 * for further validation.  For example, if this is true at the point we
	 * are validating persistence units, it may be assumed that there is a 
	 * single persistence.xml and that it has valid content down to the 
	 * persistence unit level.  */
	private boolean okToContinueValidation = true;
	
	public void addToMessages(List<IMessage> messages) {
		//start with the project - then down
		//project validation
		addProjectLevelMessages(messages);
		
		//context model validation
		getRootContext().addToMessages(messages);
	}

	protected void addProjectLevelMessages(List<IMessage> messages) {
		addConnectionMessages(messages);
		addMultiplePersistenceXmlMessage(messages);
	}
	
	protected void addConnectionMessages(List<IMessage> messages) {
		addNoConnectionMessage(messages);
		addInactiveConnectionMessage(messages);
	}
	
	protected boolean okToProceedForConnectionValidation = true;
	
	protected void addNoConnectionMessage(List<IMessage> messages) {
		if (! this.getDataSource().hasAConnection()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						JpaValidationMessages.PROJECT_NO_CONNECTION,
						this)
				);
			okToProceedForConnectionValidation = false;
		}
	}
	
	protected void addInactiveConnectionMessage(List<IMessage> messages) {
		if (okToProceedForConnectionValidation && ! this.getDataSource().connectionProfileIsActive()) {
			messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.NORMAL_SEVERITY,
						JpaValidationMessages.PROJECT_INACTIVE_CONNECTION,
						new String[] {this.getDataSource().getConnectionProfileName()},
						this)
				);
		}
		okToProceedForConnectionValidation = true;
	}
	
	protected void addMultiplePersistenceXmlMessage(List<IMessage> messages) {
//		if (validPersistenceXmlFiles.size() > 1) {
//			messages.add(
//					JpaValidationMessages.buildMessage(
//						IMessage.HIGH_SEVERITY,
//						IJpaValidationMessages.PROJECT_MULTIPLE_PERSISTENCE_XML,
//						jpaProject)
//				);
//			okToContinueValidation = false;
//		}
	}
	
	
	// ********** root deploy location **********

	protected static final String WEB_PROJECT_ROOT_DEPLOY_LOCATION = J2EEConstants.WEB_INF_CLASSES;

	public String getRootDeployLocation() {
		return this.isWebProject() ? WEB_PROJECT_ROOT_DEPLOY_LOCATION : "";
	}

	protected static final String JST_WEB_MODULE = IModuleConstants.JST_WEB_MODULE;

	protected boolean isWebProject() {
		return JptCorePlugin.projectHasWebFacet(this.project);
	}


	// ********** dispose **********

	public void dispose() {
		if (this.updater != null) {
			this.updater.dispose();
		}
		// use clone iterator while deleting JPA files
		for (Iterator<JpaFile> stream = this.jpaFiles(); stream.hasNext(); ) {
			this.removeJpaFile(stream.next());
		}
		this.dataSource.dispose();
	}


	// ********** resource model listener **********

	protected class DefaultResourceModelListener implements ResourceModelListener {
		protected DefaultResourceModelListener() {
			super();
		}
		public void resourceModelChanged() {
			GenericJpaProject.this.update();
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
				JpaFile jpaFile = this.getJpaFile(file);
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
					GenericJpaProject.this.synchronizeJpaFiles((IFile) res, delta.getKind());
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
	protected CommandExecutor getThreadLocalModifySharedDocumentCommandExecutor() {
		CommandExecutor ce = this.threadLocalModifySharedDocumentCommandExecutor.get();
		return (ce != null) ? ce : CommandExecutor.Default.instance();
	}

	public void setThreadLocalModifySharedDocumentCommandExecutor(CommandExecutor commandExecutor) {
		this.threadLocalModifySharedDocumentCommandExecutor.set(commandExecutor);
	}

	public CommandExecutorProvider getModifySharedDocumentCommandExecutorProvider() {
		return this.modifySharedDocumentCommandExecutorProvider;
	}

	// ***** inner class
	protected class ModifySharedDocumentCommandExecutorProvider implements CommandExecutorProvider {
		protected ModifySharedDocumentCommandExecutorProvider() {
			super();
		}
		public CommandExecutor getCommandExecutor() {
			return GenericJpaProject.this.getThreadLocalModifySharedDocumentCommandExecutor();
		}
	}


	// ********** project "update" **********

	public Updater getUpdater() {
		return this.updater;
	}

	public void setUpdater(Updater updater) {
		this.updater = updater;
		this.update();
	}

	/**
	 * Delegate to the updater so clients can configure how updates occur.
	 */
	public void update() {
		if (this.updater == null) {
			throw new IllegalStateException("updater is null, use setUpdater(Updater) after construction of GenericJpaProject");
		}
		this.updater.update();
	}

	/**
	 * Called by the updater.
	 */
	public IStatus update(IProgressMonitor monitor) {
		try {
			this.getRootContext().update(monitor);
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
