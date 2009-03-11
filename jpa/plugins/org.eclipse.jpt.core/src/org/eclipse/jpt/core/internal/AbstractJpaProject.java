/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.JpaDataSource;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaResourceModel;
import org.eclipse.jpt.core.JpaResourceModelListener;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.internal.utility.PlatformTools;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.jar.JarResourcePackageFragmentRoot;
import org.eclipse.jpt.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.utility.CommandExecutor;
import org.eclipse.jpt.utility.internal.BitTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.ThreadLocalCommandExecutor;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * 
 */
public abstract class AbstractJpaProject
	extends AbstractJpaNode
	implements JpaProject
{
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
	 * The JPA files associated with the JPA project:
	 *     persistence.xml
	 *     orm.xml
	 *     java
	 */
	protected final Vector<JpaFile> jpaFiles;

	/**
	 * Resource models notify this listener when they change. A project update
	 * should occur any time a resource model changes.
	 */
	protected final JpaResourceModelListener resourceModelListener;

	/**
	 * The root of the model representing the collated resources associated with 
	 * the JPA project.
	 */
	protected final JpaRootContextNode rootContextNode;

	/**
	 * A pluggable updater that can be used to "update" the JPA project either
	 * synchronously or asynchronously (or not at all). A synchronous updater
	 * is the default, allowing a newly-constructed JPA project to be "updated"
	 * upon return from the constructor. For performance reasons, a UI should
	 * immediately change this to an asynchronous updater. A synchronous
	 * updater can be used when the project is being manipulated by a "batch"
	 * (or non-UI) client (e.g. when testing the "update" behavior). A null
	 * updater can used during tests that do not care whether "updates" occur.
	 * Clients will need to explicitly configure the updater if they require
	 * something other than a synchronous updater.
	 */
	protected Updater updater;

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
	 * Support for modifying documents shared with the UI.
	 */
	protected final ThreadLocalCommandExecutor modifySharedDocumentCommandExecutor;


	// ********** constructor/initialization **********

	public AbstractJpaProject(JpaProject.Config config) throws CoreException {
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

		this.modifySharedDocumentCommandExecutor = this.buildModifySharedDocumentCommandExecutor();

		this.resourceModelListener = this.buildResourceModelListener();
		// build the JPA files corresponding to the Eclipse project's files
		this.project.accept(this.buildInitialResourceProxyVisitor(), IResource.NONE);

		this.rootContextNode = this.buildRootContextNode();

		// "update" the project before returning
		this.setUpdater_(new SynchronousJpaProjectUpdater(this));
	}

	@Override
	protected boolean requiresParent() {
		return false;
	}
	
	@Override
	public IResource getResource() {
		return this.project;
	}

	protected Vector<JpaFile> buildEmptyJpaFiles() {
		return new Vector<JpaFile>();
	}

	protected ThreadLocalCommandExecutor buildModifySharedDocumentCommandExecutor() {
		return new ThreadLocalCommandExecutor();
	}

	protected JpaResourceModelListener buildResourceModelListener() {
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
					AbstractJpaProject.this.addJpaFile_((IFile) resource.requestResource());
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

	protected Iterator<JpaFile> jpaFiles(final IContentType contentType) {
		return new FilteringIterator<JpaFile, JpaFile>(this.jpaFiles()) {
			@Override
			protected boolean accept(JpaFile jpaFile) {
				return jpaFile.getContentType().isKindOf(contentType);
			}
		};
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
		jpaFile.getResourceModel().addResourceModelListener(this.resourceModelListener);
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
		jpaFile.getResourceModel().removeResourceModelListener(this.resourceModelListener);
		if ( ! this.removeItemFromCollection(jpaFile, this.jpaFiles, JPA_FILES_COLLECTION)) {
			throw new IllegalArgumentException(jpaFile.toString());
		}
	}


	// ********** context model **********

	public JpaRootContextNode getRootContextNode() {
		return this.rootContextNode;
	}


	// ********** utility **********

	public IFile convertToPlatformFile(String fileName) {
		return JptCorePlugin.getPlatformFile(this.project, fileName);
	}


	// ********** XML files **********

	public JpaXmlResource getPersistenceXmlResource() {
		return (JpaXmlResource) this.getResourceModel(
				JptCorePlugin.DEFAULT_PERSISTENCE_XML_FILE_PATH,
				JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE
			);
	}

	public JpaXmlResource getDefaultOrmXmlResource() {
		return this.getMappingFileXmlResource(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
	}

	public JpaXmlResource getMappingFileXmlResource(String fileName) {
		return (JpaXmlResource) this.getResourceModel(fileName, JptCorePlugin.MAPPING_FILE_CONTENT_TYPE);
	}

	/**
	 * If the specified file exists, is significant to the JPA project, and its
	 * content is a "kind of" the specified content type, return the JPA
	 * resource model corresponding to the file; otherwise, return null.
	 */
	protected JpaResourceModel getResourceModel(String fileName, IContentType contentType) {
		IFile file = this.convertToPlatformFile(fileName);
		return file.exists() ? this.getResourceModel(file, contentType) :  null;
	}

	/**
	 * If the specified file is significant to the JPA project and its content
	 * is a "kind of" the specified content type, return the JPA resource model
	 * corresponding to the file; otherwise, return null.
	 */
	protected JpaResourceModel getResourceModel(IFile file, IContentType contentType) {
		JpaFile jpaFile = this.getJpaFile(file);
		return (jpaFile == null) ? null : jpaFile.getResourceModel(contentType);
	}


	// ********** Java source classes **********

	public Iterator<String> annotatedClassNames() {
		return new TransformationIterator<JavaResourcePersistentType, String>(this.persistedSourceJavaResourcePersistentTypes()) {
			@Override
			protected String transform(JavaResourcePersistentType jrpType) {
				return jrpType.getQualifiedName();
			}
		};
	}

	/**
	 * return only those "persisted" Java resource persistent types that are
	 * part of the JPA project, ignoring those in JARs referenced in persistence.xml
	 */
	protected Iterator<JavaResourcePersistentType> persistedSourceJavaResourcePersistentTypes() {
		return new FilteringIterator<JavaResourcePersistentType, JavaResourcePersistentType>(this.persistableSourceJavaResourcePersistentTypes()) {
			@Override
			protected boolean accept(JavaResourcePersistentType jrpType) {
				return jrpType.isPersisted();  // i.e. the type has a type mapping annotation
			}
		};
	}
	
	/**
	 * return only those "persistable" Java resource persistent types that are
	 * part of the JPA project, ignoring those in JARs referenced in persistence.xml
	 * @see org.eclipse.jpt.core.internal.utility.jdt.JPTTools#typeIsPersistable(org.eclipse.jdt.core.dom.ITypeBinding)
	 */
	protected Iterator<JavaResourcePersistentType> persistableSourceJavaResourcePersistentTypes() {
		return new CompositeIterator<JavaResourcePersistentType>(this.persistableSourceJavaResourcePersistentTypeIterators());
	}

	/**
	 * return only those "persistable" Java resource persistent types that are
	 * part of the JPA project, ignoring those in JARs referenced in persistence.xml
	 * @see org.eclipse.jpt.core.internal.utility.jdt.JPTTools#typeIsPersistable(org.eclipse.jdt.core.dom.ITypeBinding)
	 */
	protected Iterator<Iterator<JavaResourcePersistentType>> persistableSourceJavaResourcePersistentTypeIterators() {
		return new TransformationIterator<JavaResourceCompilationUnit, Iterator<JavaResourcePersistentType>>(this.javaResourceCompilationUnits()) {
			@Override
			protected Iterator<JavaResourcePersistentType> transform(JavaResourceCompilationUnit compilationUnit) {
				return compilationUnit.persistableTypes();  // i.e. only the types that are *allowed* to be mapped
			}
		};
	}

	protected Iterator<JavaResourceCompilationUnit> javaResourceCompilationUnits() {
		return new TransformationIterator<JpaFile, JavaResourceCompilationUnit>(this.javaSourceJpaFiles()) {
			@Override
			protected JavaResourceCompilationUnit transform(JpaFile jpaFile) {
				return (JavaResourceCompilationUnit) jpaFile.getResourceModel();
			}
		};
	}

	/**
	 * return JPA files with Java source "content"
	 */
	protected Iterator<JpaFile> javaSourceJpaFiles() {
		return this.jpaFiles(JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE);
	}


	// ********** Java resource persistent type look-up **********

	public JavaResourcePersistentType getJavaResourcePersistentType(String typeName) {
		for (Iterator<JavaResourcePersistentType> stream = this.persistableJavaResourcePersistentTypes(); stream.hasNext(); ) {
			JavaResourcePersistentType jrpType =  stream.next();
			if (jrpType.getQualifiedName().equals(typeName)) {
				return jrpType;
			}
		}
		return null;
	}

	/**
	 * return *all* the "persistable" persistent types, including those in JARs referenced in
	 * persistence.xml
	 * @see org.eclipse.jpt.core.internal.utility.jdt.JPTTools#typeIsPersistable(org.eclipse.jdt.core.dom.ITypeBinding)
	 */
	protected Iterator<JavaResourcePersistentType> persistableJavaResourcePersistentTypes() {
		return new CompositeIterator<JavaResourcePersistentType>(this.persistableJavaResourcePersistentTypeIterators());
	}

	/**
	 * return *all* the "persistable" persistent types, including those in JARs referenced in
	 * persistence.xml
	 * @see org.eclipse.jpt.core.internal.utility.jdt.JPTTools#typeIsPersistable(org.eclipse.jdt.core.dom.ITypeBinding)
	 */
	protected Iterator<Iterator<JavaResourcePersistentType>> persistableJavaResourcePersistentTypeIterators() {
		return new TransformationIterator<JavaResourceNode.Root, Iterator<JavaResourcePersistentType>>(this.javaResourceNodeRoots()) {
			@Override
			protected Iterator<JavaResourcePersistentType> transform(JavaResourceNode.Root root) {
				return root.persistableTypes();  // i.e. only the types that are *allowed* to be mapped
			}
		};
	}

	@SuppressWarnings("unchecked")
	protected Iterator<JavaResourceNode.Root> javaResourceNodeRoots() {
		return new CompositeIterator<JavaResourceNode.Root>(
					this.javaResourceCompilationUnits(),
					this.jarResourcePackageFragmentRoots()
				);
	}


	// ********** JARs **********

	// TODO
	public JarResourcePackageFragmentRoot getJarResourcePackageFragmentRoot(String jarFileName) {
//		return this.getJarResourcePackageFragmentRoot(this.convertToPlatformFile(jarFileName));
		return this.getJarResourcePackageFragmentRoot(this.getProject().getFile(jarFileName));
	}

	protected JarResourcePackageFragmentRoot getJarResourcePackageFragmentRoot(IFile jarFile) {
		for (Iterator<JarResourcePackageFragmentRoot> stream = this.jarResourcePackageFragmentRoots(); stream.hasNext(); ) {
			JarResourcePackageFragmentRoot pfr = stream.next();
			if (pfr.getFile().equals(jarFile)) {
				return pfr;
			}
		}
		return null;
	}

	protected Iterator<JarResourcePackageFragmentRoot> jarResourcePackageFragmentRoots() {
		return new TransformationIterator<JpaFile, JarResourcePackageFragmentRoot>(this.jarJpaFiles()) {
			@Override
			protected JarResourcePackageFragmentRoot transform(JpaFile jpaFile) {
				return (JarResourcePackageFragmentRoot) jpaFile.getResourceModel();
			}
		};
	}

	/**
	 * return JPA files with JAR "content"
	 */
	protected Iterator<JpaFile> jarJpaFiles() {
		return this.jpaFiles(JptCorePlugin.JAR_CONTENT_TYPE);
	}


	// ********** Java change **********

	public void javaElementChanged(ElementChangedEvent event) {
		this.synchWithJavaDelta(event.getDelta());
	}

	/**
	 * We recurse back here when processing 'affectedChildren'.
	 */
	protected void synchWithJavaDelta(IJavaElementDelta delta) {
		switch (delta.getElement().getElementType()) {
			case IJavaElement.JAVA_MODEL :
				this.javaModelChanged(delta);
				break;
			case IJavaElement.JAVA_PROJECT :
				this.javaProjectChanged(delta);
				break;
			case IJavaElement.PACKAGE_FRAGMENT_ROOT :
				this.javaPackageFragmentRootChanged(delta);
				break;
			case IJavaElement.PACKAGE_FRAGMENT :
				this.javaPackageFragmentChanged(delta);
				break;
			case IJavaElement.COMPILATION_UNIT :
				this.javaCompilationUnitChanged(delta);
				break;
			default :
				break; // ignore the elements inside a compilation unit
		}
	}

	// ***** model
	protected void javaModelChanged(IJavaElementDelta delta) {
		// process the java model's projects
		this.synchWithJavaDeltaChildren(delta);
	}

	protected void synchWithJavaDeltaChildren(IJavaElementDelta delta) {
		for (IJavaElementDelta child : delta.getAffectedChildren()) {
			this.synchWithJavaDelta(child); // recurse
		}
	}

	// ***** project
	protected void javaProjectChanged(IJavaElementDelta delta) {
		if ( ! delta.getElement().equals(this.getJavaProject())) {
			return;  // ignore
		}

		// process the java project's package fragment roots
		this.synchWithJavaDeltaChildren(delta);

		if (this.classpathHasChanged(delta)) {
			// the jars were processed above, now force all the JPA files to update
			this.updateFromJava();
		}
	}

	protected boolean classpathHasChanged(IJavaElementDelta delta) {
		return BitTools.flagIsSet(delta.getFlags(), IJavaElementDelta.F_RESOLVED_CLASSPATH_CHANGED);
	}

	protected void updateFromJava() {
		for (Iterator<JavaResourceCompilationUnit> stream = this.javaResourceCompilationUnits(); stream.hasNext(); ) {
			stream.next().update();
		}
	}

	// ***** package fragment root
	protected void javaPackageFragmentRootChanged(IJavaElementDelta delta) {
		// process the java package fragment root's package fragments
		this.synchWithJavaDeltaChildren(delta);

		if (this.classpathEntryHasBeenAdded(delta)) {
			// TODO
		} else if (this.classpathEntryHasBeenRemoved(delta)) {  // should be mutually-exclusive w/added (?)
			// TODO
		}
	}

	protected boolean classpathEntryHasBeenAdded(IJavaElementDelta delta) {
		return BitTools.flagIsSet(delta.getFlags(), IJavaElementDelta.F_ADDED_TO_CLASSPATH);
	}

	protected boolean classpathEntryHasBeenRemoved(IJavaElementDelta delta) {
		return BitTools.flagIsSet(delta.getFlags(), IJavaElementDelta.F_REMOVED_FROM_CLASSPATH);
	}

	// ***** package fragment
	protected void javaPackageFragmentChanged(IJavaElementDelta delta) {
		// process the java package fragment's compilation units
		this.synchWithJavaDeltaChildren(delta);
	}

	// ***** compilation unit
	protected void javaCompilationUnitChanged(IJavaElementDelta delta) {
		if (this.javaCompilationUnitDeltaIsRelevant(delta)) {
			ICompilationUnit compilationUnit = (ICompilationUnit) delta.getElement();
			for (Iterator<JavaResourceCompilationUnit> stream = this.javaResourceCompilationUnits(); stream.hasNext(); ) {
				JavaResourceCompilationUnit jrcu = stream.next();
				if (jrcu.getCompilationUnit().equals(compilationUnit)) {
					jrcu.update();
					break;  // there *shouldn't* be any more...
				}
			}
		}
		// ignore the java compilation unit's children
	}

	protected boolean javaCompilationUnitDeltaIsRelevant(IJavaElementDelta delta) {
		// ignore changes to/from primary working copy - no content has changed;
		// and make sure there are no other flags set that indicate *both* a
		// change to/from primary working copy *and* content has changed
		if (BitTools.onlyFlagIsSet(delta.getFlags(), IJavaElementDelta.F_PRIMARY_WORKING_COPY)) {
			return false;
		}

		// ignore java notification for ADDED or REMOVED;
		// these are handled via resource notification
		return delta.getKind() == IJavaElementDelta.CHANGED;
	}


	// ********** validation **********
	
	public Iterator<IMessage> validationMessages(IReporter reporter) {
		List<IMessage> messages = new ArrayList<IMessage>();
		this.validate(messages, reporter);
		return messages.iterator();
	}
	
	protected void validate(List<IMessage> messages, IReporter reporter) {
		if (reporter.isCancelled()) {
			throw new ValidationCancelledException();
		}
		this.validateConnection(messages);
		this.rootContextNode.validate(messages, reporter);
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
	
	
	// ********** dispose **********

	public void dispose() {
		this.updater.stop();
		this.dataSource.dispose();
	}


	// ********** resource model listener **********

	protected class DefaultResourceModelListener implements JpaResourceModelListener {
		protected DefaultResourceModelListener() {
			super();
		}
		public void resourceModelChanged() {
			AbstractJpaProject.this.update();
		}
	}


	// ********** handling resource deltas **********

	public void projectChanged(IResourceDelta delta) throws CoreException {
		ResourceDeltaVisitor resourceDeltaVisitor = this.buildResourceDeltaVisitor();
		delta.accept(resourceDeltaVisitor);
		if (resourceDeltaVisitor.jpaFilesChanged()) {
			for (Iterator<JavaResourceCompilationUnit> stream = this.javaResourceCompilationUnits(); stream.hasNext(); ) {
				stream.next().resolveTypes();
			}
		}
		// update JARs???
		// TODO
	}

	protected ResourceDeltaVisitor buildResourceDeltaVisitor() {
		return new ResourceDeltaVisitor();
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
				return this.checkForChangedJpaFile(file);
			case IResourceDelta.ADDED_PHANTOM :
			case IResourceDelta.REMOVED_PHANTOM :
			default :
				break;  // only worried about added/removed/changed files
		}

		return false;
	}

	protected boolean checkForChangedJpaFile(IFile file) {
		JpaFile jpaFile = this.getJpaFile(file);
		if (jpaFile == null) {
			// the file might have changed its content to something that we are interested in
			return this.addJpaFile(file);
		}

		if (jpaFile.getContentType().equals(PlatformTools.getContentType(file))) {
			// content has not changed - ignore
			return false;
		}

		// the content type changed, we need to build a new JPA file
		// (e.g. the schema of an orm.xml file changed from JPA to EclipseLink)
		this.removeJpaFile(jpaFile);
		this.addJpaFile(file);
		return true;
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
					if (AbstractJpaProject.this.synchronizeJpaFiles((IFile) res, delta.getKind())) {
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

	public void setThreadLocalModifySharedDocumentCommandExecutor(CommandExecutor commandExecutor) {
		this.modifySharedDocumentCommandExecutor.set(commandExecutor);
	}

	public CommandExecutor getModifySharedDocumentCommandExecutor() {
		return this.modifySharedDocumentCommandExecutor;
	}


	// ********** project "update" **********

	public Updater getUpdater() {
		return this.updater;
	}

	public void setUpdater(Updater updater) {
		if (updater == null) {
			throw new NullPointerException();
		}
		this.updater.stop();
		this.setUpdater_(updater);
	}

	protected void setUpdater_(Updater updater) {
		this.updater = updater;
		this.updater.start();
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
		this.rootContextNode.postUpdate();
		return Status.OK_STATUS;
	}

}
