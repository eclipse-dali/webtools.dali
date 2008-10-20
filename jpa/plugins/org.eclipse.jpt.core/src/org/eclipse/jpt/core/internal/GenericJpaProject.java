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
import org.eclipse.jpt.core.JpaDataSource;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.ResourceModelListener;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.utility.CommandExecutor;
import org.eclipse.jpt.utility.CommandExecutorProvider;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
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
	 * A catalog name used to override the connection's default catalog.
	 */
	protected String userOverrideDefaultCatalog;

	/**
	 * A schema name used to override the connection's default schema.
	 */
	protected String userOverrideDefaultSchema;

	/**
	 * Flag indicating whether the project should "discover" annotated
	 * classes automatically, as opposed to requiring the classes to be
	 * listed in persistence.xml.
	 */
	protected boolean discoversAnnotatedClasses;

	/**
	 * The JPA files associated with the JPA project:
	 *     java
	 *     persistence.xml
	 *     orm.xml
	 */
	protected final Vector<JpaFile> jpaFiles;

	/**
	 * The root of the model representing the collated resources associated with 
	 * the JPA project.
	 */
	protected JpaRootContextNode rootContextNode;

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

	public GenericJpaProject(JpaProject.Config config) throws CoreException {
		super(null);  // JPA project is the root of the containment tree
		if ((config.getProject() == null) || (config.getJpaPlatform() == null)) {
			throw new NullPointerException();
		}
		this.project = config.getProject();
		this.jpaPlatform = config.getJpaPlatform();
		this.dataSource = this.getJpaFactory().buildJpaDataSource(this, config.getConnectionProfileName());
		this.userOverrideDefaultCatalog = config.getUserOverrideDefaultCatalog();
		this.userOverrideDefaultSchema = config.getUserOverrideDefaultSchema();
		this.discoversAnnotatedClasses = config.discoverAnnotatedClasses();
		this.jpaFiles = this.buildEmptyJpaFiles();

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

	protected ResourceDeltaVisitor buildResourceDeltaVisitor() {
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
		return this.getJpaFactory().buildRootContextNode(this);
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
					GenericJpaProject.this.addJpaFile_((IFile) resource.requestResource());
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
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
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


	// ********** database **********

	@Override
	public JpaDataSource getDataSource() {
		return this.dataSource;
	}

	public ConnectionProfile getConnectionProfile() {
		return this.dataSource.getConnectionProfile();
	}

	public Catalog getDefaultDbCatalog() {
		String catalog = this.getDefaultCatalog();
		return (catalog == null) ? null : this.getDbCatalog(catalog);
	}

	public String getDefaultCatalog() {
		String catalog = this.getUserOverrideDefaultCatalog();
		return (catalog != null) ? catalog : this.getDatabaseDefaultCatalog();
	}

	protected String getDatabaseDefaultCatalog() {
		Catalog dbCatalog = this.getDatabaseDefaultDbCatalog();
		return (dbCatalog == null ) ? null : dbCatalog.getIdentifier();
	}

	protected Catalog getDatabaseDefaultDbCatalog() {
		Database db = this.getDatabase();
		return (db == null ) ? null : db.getDefaultCatalog();
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a *default* catalog),
	 * then the database probably does not support catalogs; and we need to
	 * get the schema directly from the database.
	 */
	public SchemaContainer getDefaultDbSchemaContainer() {
		String catalog = this.getDefaultCatalog();
		return (catalog != null) ? this.getDbCatalog(catalog) : this.getDatabase();
	}

	public Schema getDefaultDbSchema() {
		SchemaContainer sc = this.getDefaultDbSchemaContainer();
		return (sc == null) ? null : sc.getSchemaForIdentifier(this.getDefaultSchema());
	}

	public String getDefaultSchema() {
		String schema = this.getUserOverrideDefaultSchema();
		if (schema != null) {
			return schema;
		}
		String catalog = this.getDefaultCatalog();
		if (catalog != null) {
			Catalog dbCatalog = this.getDbCatalog(catalog);
			if (dbCatalog != null) {
				Schema dbSchema = dbCatalog.getDefaultSchema();
				if (dbSchema != null) {
					return dbSchema.getIdentifier();
				}
			}
		}
		// if there is no default catalog,
		// the database probably does not support catalogs;
		// return the database's default schema
		return this.getDatabaseDefaultSchema();
	}

	protected String getDatabaseDefaultSchema() {
		Schema schema = this.getDatabaseDefaultDbSchema();
		return (schema == null ) ? null : schema.getIdentifier();
	}

	protected Schema getDatabaseDefaultDbSchema() {
		Database db = this.getDatabase();
		return (db == null ) ? null : db.getDefaultSchema();
	}


	// **************** user override default catalog **********************
	
	public String getUserOverrideDefaultCatalog() {
		return this.userOverrideDefaultCatalog;
	}
	
	public void setUserOverrideDefaultCatalog(String catalog) {
		String old = this.userOverrideDefaultCatalog;
		this.userOverrideDefaultCatalog = catalog;
		this.firePropertyChanged(USER_OVERRIDE_DEFAULT_CATALOG_PROPERTY, old, catalog);
	}
	
	
	// **************** user override default schema **********************
	
	public String getUserOverrideDefaultSchema() {
		return this.userOverrideDefaultSchema;
	}
	
	public void setUserOverrideDefaultSchema(String schema) {
		String old = this.userOverrideDefaultSchema;
		this.userOverrideDefaultSchema = schema;
		this.firePropertyChanged(USER_OVERRIDE_DEFAULT_SCHEMA_PROPERTY, old, schema);
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
	
	
	// ********** JPA files **********

	public Iterator<JpaFile> jpaFiles() {
		return new CloneIterator<JpaFile>(this.jpaFiles);  // read-only
	}

	public int jpaFilesSize() {
		return this.jpaFiles.size();
	}

	@Override
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

	/**
	 * Add a JPA file for the specified file, if appropriate.
	 * Return true if a JPA File was created and added, false otherwise
	 */
	protected boolean addJpaFile(IFile file) {
		JpaFile jpaFile = this.addJpaFile_(file);
		if (jpaFile != null) {
			this.fireItemAdded(JPA_FILES_COLLECTION, jpaFile);
			return true;
		}
		return false;
	}

	/**
	 * Add a JPA file for the specified file, if appropriate, without firing
	 * an event; useful during construction.
	 * Return the new JPA file, null if it was not created.
	 */
	protected JpaFile addJpaFile_(IFile file) {
		if ( ! this.getJavaProject().isOnClasspath(file)) {
			return null;  // the file must be on the Java classpath
		}

		JpaFile jpaFile = this.getJpaPlatform().buildJpaFile(this, file);
		if (jpaFile == null) {
			return null;
		}
		jpaFile.addResourceModelListener(this.resourceModelListener);
		this.jpaFiles.add(jpaFile);
		return jpaFile;
	}

	/**
	 * Remove the JPA file corresponding to the specified IFile, if it exists.
	 * Return true if a JPA File was removed, false otherwise
	 */
	protected boolean removeJpaFile(IFile file) {
		synchronized (this.jpaFiles) {
			JpaFile jpaFile = this.getJpaFile(file);
			if (jpaFile != null) {  // a JpaFile is not added for every IFile
				this.removeJpaFile(jpaFile);
				return true;
			}
			return false;
		}
	}
	
	/**
	 * Remove the JPA file and dispose of it
	 */
	protected void removeJpaFile(JpaFile jpaFile) {
		jpaFile.removeResourceModelListener(this.resourceModelListener);
		if ( ! this.removeItemFromCollection(jpaFile, this.jpaFiles, JPA_FILES_COLLECTION)) {
			throw new IllegalArgumentException(jpaFile.toString());
		}
	}

	protected boolean containsJpaFile(IFile file) {
		return (this.getJpaFile(file) != null);
	}


	// ********** context model **********

	public JpaRootContextNode getRootContextNode() {
		return this.rootContextNode;
	}


	// ********** more queries **********

	public Iterator<String> annotatedClassNames() {
		return new TransformationIterator<JavaResourcePersistentType, String>(this.persistedJavaResourcePersistentTypes()) {
			@Override
			protected String transform(JavaResourcePersistentType jrpt) {
				return jrpt.getQualifiedName();
			}
		};
	}
	
	protected Iterator<JavaResourcePersistentType> persistedJavaResourcePersistentTypes() {
		return new FilteringIterator<JavaResourcePersistentType, JavaResourcePersistentType>(this.persistableJavaResourcePersistentTypes()) {
			@Override
			protected boolean accept(JavaResourcePersistentType jrpt) {
				return jrpt.isPersisted();
			}
		};
	}
	
	protected Iterator<JavaResourcePersistentType> persistableJavaResourcePersistentTypes() {
		return new CompositeIterator<JavaResourcePersistentType>(this.persistableJavaResourcePersistentTypeIterators());
	}

	protected Iterator<Iterator<JavaResourcePersistentType>> persistableJavaResourcePersistentTypeIterators() {
		return new TransformationIterator<JpaFile, Iterator<JavaResourcePersistentType>>(this.jpaFiles()) {
			@Override
			protected Iterator<JavaResourcePersistentType> transform(JpaFile jpaFile) {
				return jpaFile.persistableTypes();
			}
		};
	}

	public JavaResourcePersistentType getJavaResourcePersistentType(String typeName) {
		for (Iterator<JavaResourcePersistentType> stream = this.persistableJavaResourcePersistentTypes(); stream.hasNext(); ) {
			JavaResourcePersistentType pt =  stream.next();
			if (pt.getQualifiedName().equals(typeName)) {
				return pt;
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
		this.validate(messages);
		return messages.iterator();
	}
	
	protected void validate(List<IMessage> messages) {
		this.validateConnection(messages);
		this.rootContextNode.validate(messages);
	}

	protected void validateConnection(List<IMessage> messages) {
		String cpName = this.dataSource.getConnectionProfileName();
		if (StringTools.stringIsEmpty(cpName)) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.NORMAL_SEVERITY,
					JpaValidationMessages.PROJECT_NO_CONNECTION,
					this
				)
			);
			return;
		}
		ConnectionProfile cp = this.dataSource.getConnectionProfile();
		if (cp == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.NORMAL_SEVERITY,
					JpaValidationMessages.PROJECT_INVALID_CONNECTION,
					new String[] {cpName},
					this
				)
			);
			return;
		}
		if (cp.isInactive()) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.NORMAL_SEVERITY,
					JpaValidationMessages.PROJECT_INACTIVE_CONNECTION,
					new String[] {cpName},
					this
				)
			);
		}
	}
	
	
	// ********** root deploy location **********

	protected static final String WEB_PROJECT_ROOT_DEPLOY_LOCATION = J2EEConstants.WEB_INF_CLASSES;

	public String getRootDeployLocation() {
		return this.isWebProject() ? WEB_PROJECT_ROOT_DEPLOY_LOCATION : ""; //$NON-NLS-1$
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
		ResourceDeltaVisitor resourceDeltaVisitor = this.buildResourceDeltaVisitor();
		delta.accept(resourceDeltaVisitor);
		if (resourceDeltaVisitor.jpaFilesChanged()) {
			for (Iterator<JpaFile> stream = this.jpaFiles(); stream.hasNext(); ) {
				stream.next().jpaFilesChanged();
			}
		}
	}

	/**
	 * resource delta visitor callback
	 * Return true if a JpaFile was either added or removed
	 */
	protected boolean synchronizeJpaFiles(IFile file, int deltaKind) {
		switch (deltaKind) {
			case IResourceDelta.ADDED :
				return this.addJpaFile(file);
			case IResourceDelta.REMOVED :
				return this.removeJpaFile(file);
			case IResourceDelta.CHANGED :
			case IResourceDelta.ADDED_PHANTOM :
			case IResourceDelta.REMOVED_PHANTOM :
			default :
				break;  // only worried about added and removed files
		}

		return false;
	}

	// ***** inner class
	/**
	 * add or remove a JPA file for every [appropriate] file encountered by the visitor
	 */
	protected class ResourceDeltaVisitor implements IResourceDeltaVisitor {
		private boolean jpaFilesChanged = false;
		
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
					if (GenericJpaProject.this.synchronizeJpaFiles((IFile) res, delta.getKind())) {
						this.jpaFilesChanged = true;
					}
					return false;  // no children
				default :
					return false;  // no children
			}
		}

		/**
		 * Return whether the collection of JPA files was modified while
		 * traversing the IResourceDelta (i.e. a JPA file was added or removed).
		 */
		protected boolean jpaFilesChanged() {
			return this.jpaFilesChanged;
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
		if (this.updater != null) {  // first time through, the updater will be null
			this.updater.dispose();
		}
		this.updater = updater;
		this.updater.start();
	}

	/**
	 * Delegate to the updater so clients can configure how updates occur.
	 */
	public void update() {
		if (this.updater == null) {
			throw new IllegalStateException("updater is null, use #setUpdater(Updater) after construction of GenericJpaProject"); //$NON-NLS-1$
		}
		this.updater.update();
	}

	/**
	 * Called by the updater.
	 */
	public IStatus update(IProgressMonitor monitor) {
		try {
			this.rootContextNode.update(monitor);
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
