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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.ContentTypeReference;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.JptResourceModelListener;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryTypeCache;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceTypeCompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.command.NotifyingRepeatingJobCommandWrapper;
import org.eclipse.jpt.common.core.internal.utility.command.RepeatingJobCommandWrapper;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackageInfoCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceTypeCache;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.core.utility.command.JobCommandExecutor;
import org.eclipse.jpt.common.core.utility.command.NotifyingRepeatingJobCommand;
import org.eclipse.jpt.common.core.utility.command.RepeatingJobCommand;
import org.eclipse.jpt.common.utility.internal.BitTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaDataSource;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCoreMessages;
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.jpa2.MetamodelSynchronizer;
import org.eclipse.jpt.jpa.core.jpa2.context.JpaRootContextNode2_0;
import org.eclipse.jpt.jpa.core.jpa2_1.JpaProject2_1;
import org.eclipse.jpt.jpa.core.libprov.JpaLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jpa.core.resource.ResourceMappingFile;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.jst.common.project.facet.core.libprov.ILibraryProvider;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryProviderFramework;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * JPA project. Holds all the JPA stuff.
 * <p>
 * The JPA platform provides the hooks for vendor-specific stuff.
 * <p>
 * The JPA files are the "resource" model (i.e. objects that correspond directly
 * to Eclipse resources; e.g. Java source code files, XML files, JAR files).
 * <p>
 * The root context node is the "context" model (i.e. objects that attempt to
 * model the JPA spec, using the "resource" model as an adapter to the Eclipse
 * resources).
 * <p>
 * The data source is an adapter to the DTP meta-data model.
 */
public abstract class AbstractJpaProject
	extends AbstractJpaNode
	implements JpaProject2_1
{
	/**
	 * The JPA project manager.
	 */
	protected final Manager manager;

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
	 * key - IFile associated with the JpaFile.
	 * value - the JpaFile
	 * The JPA files associated with the JPA project:
	 *     persistence.xml
	 *     orm.xml
	 *     java
	 */
	protected final Hashtable<IFile, JpaFile> jpaFiles = new Hashtable<IFile, JpaFile>();

	/**
	 * The "external" Java resource compilation units (source). Populated upon demand.
	 */
	protected final Vector<JavaResourceCompilationUnit> externalJavaResourceCompilationUnits = new Vector<JavaResourceCompilationUnit>();

	/**
	 * The "external" Java resource types (binary). Populated upon demand.
	 */
	protected final JavaResourceTypeCache externalJavaResourceTypeCache;

	/**
	 * Resource models notify this listener when they change. A project update
	 * should occur any time a resource model changes.
	 */
	protected final JptResourceModelListener resourceModelListener;

	/**
	 * The root of the model representing the collated resources associated with
	 * the JPA project.
	 */
	protected final JpaRootContextNode rootContextNode;

	/**
	 * A repeating command that keeps the JPA project's context model
	 * synchronized with its resource model, either synchronously or
	 * asynchronously (or not at all), depending on the JPA project manager.
	 */
	protected volatile RepeatingJobCommand synchronizeContextModelCommand;
	protected volatile boolean synchronizingContextModel = false;

	/**
	 * A pluggable synchronizer that "updates" the JPA project, either
	 * synchronously or asynchronously (or not at all), depending on the JPA
	 * project manager.
	 */
	protected volatile NotifyingRepeatingJobCommand updateCommand;
	protected final NotifyingRepeatingJobCommand.Listener updateCommandListener;

	/**
	 * The data source that wraps the DTP model.
	 */
	// TODO move to persistence unit... :-(
	protected final JpaDataSource dataSource;

	/**
	 * A catalog name used to override the connection's default catalog.
	 */
	protected volatile String userOverrideDefaultCatalog;

	/**
	 * A schema name used to override the connection's default schema.
	 */
	protected volatile String userOverrideDefaultSchema;

	/**
	 * Flag indicating whether the project should "discover" annotated
	 * classes automatically, as opposed to requiring the classes to be
	 * listed in <code>persistence.xml</code>. Stored as a preference.
	 * @see #setDiscoversAnnotatedClasses(boolean)
	 */
	protected volatile boolean discoversAnnotatedClasses;

	/**
	 * The name of the Java project source folder that holds the generated
	 * metamodel. If the name is <code>null</code> the metamodel is not
	 * generated.
	 */
	protected volatile String metamodelSourceFolderName;


	// ********** constructor/initialization **********

	protected AbstractJpaProject(JpaProject.Config config) {
		super(null);  // JPA project is the root of the containment tree
		if ((config.getJpaProjectManager() == null) || (config.getProject() == null) || (config.getJpaPlatform() == null)) {
			throw new NullPointerException();
		}
		this.manager = config.getJpaProjectManager();
		this.project = config.getProject();
		this.synchronizeContextModelCommand = this.buildSynchronizeContextModelCommand();
		this.updateCommand = this.buildTempUpdateCommand();  // temporary command
		this.jpaPlatform = config.getJpaPlatform();
		this.dataSource = this.getJpaFactory().buildJpaDataSource(this, config.getConnectionProfileName());
		this.userOverrideDefaultCatalog = config.getUserOverrideDefaultCatalog();
		this.userOverrideDefaultSchema = config.getUserOverrideDefaultSchema();
		this.discoversAnnotatedClasses = config.discoverAnnotatedClasses();

		this.resourceModelListener = this.buildResourceModelListener();
		// build the JPA files corresponding to the Eclipse project's files
		InitialResourceProxyVisitor visitor = this.buildInitialResourceProxyVisitor();
		visitor.visitProject(this.project);

		this.externalJavaResourceTypeCache = this.buildExternalJavaResourceTypeCache();

		if (this.isJpa2_0Compatible()) {
			this.metamodelSourceFolderName = ((JpaProject2_0.Config) config).getMetamodelSourceFolderName();
			if (this.metamodelSoureFolderNameIsInvalid()) {
				this.metamodelSourceFolderName = null;
			}
		}

		this.rootContextNode = this.buildRootContextNode();

		this.updateCommandListener = this.buildUpdateCommandListener();
		this.initializeContextModel();

		// start listening to this cache once the context model has been built
		// and all the external types are faulted in
		this.externalJavaResourceTypeCache.addResourceModelListener(this.resourceModelListener);
	}

	@Override
	protected boolean requiresParent() {
		return false;
	}

	@Override
	public IResource getResource() {
		return this.project;
	}

	protected JavaResourceTypeCache buildExternalJavaResourceTypeCache() {
		return new BinaryTypeCache(this.jpaPlatform.getAnnotationProvider());
	}

	protected JpaRootContextNode buildRootContextNode() {
		return this.getJpaFactory().buildRootContextNode(this);
	}

	/**
	 * Execute a synchronous <em>sync</em> and <em>update</em> before returning
	 * from the constructor.
	 */
	protected void initializeContextModel() {
		// there *shouldn't* be any changes to the resource model yet...
		this.synchronizeContextModelCommand.start();

		// perform a synchronous update...
		this.updateCommand.start();
		this.update();
		try {
			this.updateCommand.stop();
		} catch (InterruptedException ex) {
			// the initial update is synchronous, so this shouldn't happen...
			// but let our thread know it was interrupted during a wait
			Thread.currentThread().interrupt();
		}

		// ...then delegate further updates to the JPA project manager
		this.updateCommand = this.buildUpdateCommand();
		this.updateCommand.addListener(this.updateCommandListener);
		this.updateCommand.start();
	}


	// ********** initial resource proxy visitor **********

	protected InitialResourceProxyVisitor buildInitialResourceProxyVisitor() {
		return new InitialResourceProxyVisitor();
	}

	protected class InitialResourceProxyVisitor
		implements IResourceProxyVisitor
	{
		protected InitialResourceProxyVisitor() {
			super();
		}
		protected void visitProject(IProject p) {
			try {
				p.accept(this, IResource.NONE);
			} catch (CoreException ex) {
				// shouldn't happen - we don't throw any CoreExceptions
				throw new RuntimeException(ex);
			}
		}
		// add a JPA file for every [appropriate] file encountered by the visitor
		public boolean visit(IResourceProxy resource) {
			switch (resource.getType()) {
				case IResource.ROOT :  // shouldn't happen
					return true;  // visit children
				case IResource.PROJECT :
					return true;  // visit children
				case IResource.FOLDER :
					return true;  // visit children
				case IResource.FILE :
					AbstractJpaProject.this.addJpaFileMaybe_((IFile) resource.requestResource());
					return false;  // no children
				default :
					return false;  // no children
			}
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, AbstractJpaProject.this);
		}
	}


	// ********** misc **********

	/**
	 * Ignore changes to this collection. Adds can be ignored since they are triggered
	 * by requests that will, themselves, trigger updates (typically during the
	 * update of an object that calls a setter with the newly-created resource
	 * type). Deletes will be accompanied by manual updates.
	 */
	@Override
	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
		super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
		nonUpdateAspectNames.add(EXTERNAL_JAVA_RESOURCE_COMPILATION_UNITS_COLLECTION);
	}


	// ********** general queries **********

	@Override
	public JpaProject getJpaProject() {
		return this;
	}

	public Manager getManager() {
		return this.manager;
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

	@Override
	public IJavaProject getJavaProject() {
		return JavaCore.create(this.project);
	}

	@Override
	public JpaPlatform getJpaPlatform() {
		return this.jpaPlatform;
	}

	@SuppressWarnings("unchecked")
	protected Iterable<JavaResourceCompilationUnit> getCombinedJavaResourceCompilationUnits() {
		return IterableTools.concatenate(
					this.getInternalJavaResourceCompilationUnits(),
					this.getExternalJavaResourceCompilationUnits()
				);
	}


	// ********** database **********

	@Override
	public JpaDataSource getDataSource() {
		return this.dataSource;
	}

	public ConnectionProfile getConnectionProfile() {
		return this.dataSource.getConnectionProfile();
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	public Catalog getDefaultDbCatalog() {
		String catalog = this.getDefaultCatalog();
		return (catalog == null) ? null : this.resolveDbCatalog(catalog);
	}

	public String getDefaultCatalog() {
		String catalog = this.getUserOverrideDefaultCatalog();
		return (catalog != null) ? catalog : this.getDatabaseDefaultCatalog();
	}

	protected String getDatabaseDefaultCatalog() {
		Database db = this.getDatabase();
		return (db == null ) ? null : db.getDefaultCatalogIdentifier();
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em> catalog),
	 * then the database probably does not support catalogs; and we need to
	 * get the schema directly from the database.
	 */
	public SchemaContainer getDefaultDbSchemaContainer() {
		String catalog = this.getDefaultCatalog();
		return (catalog != null) ? this.resolveDbCatalog(catalog) : this.getDatabase();
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
		if (catalog == null) {
			// if there is no default catalog (either user-override or database-determined),
			// the database probably does not support catalogs;
			// return the database's default schema
			return this.getDatabaseDefaultSchema();
		}

		Catalog dbCatalog = this.resolveDbCatalog(catalog);
		if (dbCatalog != null) {
			return dbCatalog.getDefaultSchemaIdentifier();
		}

		// if we could not find a catalog on the database that matches the default
		// catalog name, return the database's default schema(?) - hmmm
		return this.getDatabaseDefaultSchema();
	}

	protected String getDatabaseDefaultSchema() {
		Database db = this.getDatabase();
		return (db == null ) ? null : db.getDefaultSchemaIdentifier();
	}


	// ********** user override default catalog **********

	public String getUserOverrideDefaultCatalog() {
		return this.userOverrideDefaultCatalog;
	}

	public void setUserOverrideDefaultCatalog(String catalog) {
		String old = this.userOverrideDefaultCatalog;
		this.userOverrideDefaultCatalog = catalog;
		if (this.firePropertyChanged(USER_OVERRIDE_DEFAULT_CATALOG_PROPERTY, old, catalog)) {
			JpaPreferences.setUserOverrideDefaultCatalog(this.project, catalog);
		}
	}


	// ********** user override default schema **********

	public String getUserOverrideDefaultSchema() {
		return this.userOverrideDefaultSchema;
	}

	public void setUserOverrideDefaultSchema(String schema) {
		String old = this.userOverrideDefaultSchema;
		this.userOverrideDefaultSchema = schema;
		if (this.firePropertyChanged(USER_OVERRIDE_DEFAULT_SCHEMA_PROPERTY, old, schema)) {
			JpaPreferences.setUserOverrideDefaultSchema(this.project, schema);
		}
	}


	// ********** discover annotated classes **********

	public boolean discoversAnnotatedClasses() {
		return this.discoversAnnotatedClasses;
	}

	public void setDiscoversAnnotatedClasses(boolean discoversAnnotatedClasses) {
		boolean old = this.discoversAnnotatedClasses;
		this.discoversAnnotatedClasses = discoversAnnotatedClasses;
		JpaPreferences.setDiscoverAnnotatedClasses(this.project, discoversAnnotatedClasses);
		this.firePropertyChanged(DISCOVERS_ANNOTATED_CLASSES_PROPERTY, old, discoversAnnotatedClasses);
	}


	// ********** JPA files **********

	public Iterable<JpaFile> getJpaFiles() {
		return IterableTools.cloneLive(this.jpaFiles.values());  // read-only
	}

	public int getJpaFilesSize() {
		return this.jpaFiles.size();
	}

	protected Iterable<JpaFile> getJpaFiles(final IContentType contentType) {
		return IterableTools.filter(
				this.getJpaFiles(),
				new ContentTypeReference.ContentIsKindOf(contentType)
			);
	}

	@Override
	public JpaFile getJpaFile(IFile file) {
		return file == null ? null : this.jpaFiles.get(file);
	}

	/**
	 * Add a JPA file for the specified file, if appropriate.
	 * Return true if a JPA File was created and added, false otherwise
	 */
	protected boolean addJpaFileMaybe(IFile file) {
		JpaFile jpaFile = this.addJpaFileMaybe_(file);
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
	protected JpaFile addJpaFileMaybe_(IFile file) {
		if (this.fileIsJavaRelated(file)) {
			if ( ! this.getJavaProject().isOnClasspath(file)) {
				return null;  // java-related files must be on the Java classpath
			}
		}
		else if ( ! this.fileResourceLocationIsValid(file)) {
			return null;
		}

		JpaFile jpaFile = this.buildJpaFile(file);
		if (jpaFile == null) {
			return null;
		}
		jpaFile.getResourceModel().addResourceModelListener(this.resourceModelListener);
		this.jpaFiles.put(file, jpaFile);
		return jpaFile;
	}

	/**
	 * <code>.java</code> or <code>.jar</code>
	 */
	protected boolean fileIsJavaRelated(IFile file) {
		IContentType contentType = getContentType(file);
		return (contentType != null) && this.contentTypeIsJavaRelated(contentType);
	}

	/**
	 * pre-condition: content type is not <code>null</code>
	 */
	protected boolean contentTypeIsJavaRelated(IContentType contentType) {
		return contentType.isKindOf(JavaResourceCompilationUnit.CONTENT_TYPE) ||
				contentType.isKindOf(JavaResourcePackageFragmentRoot.JAR_CONTENT_TYPE);
	}

	protected boolean fileResourceLocationIsValid(IFile file) {
		return this.getProjectResourceLocator().locationIsValid(file.getParent());
	}

	protected ProjectResourceLocator getProjectResourceLocator() {
		return (ProjectResourceLocator) this.project.getAdapter(ProjectResourceLocator.class);
	}
	
	/**
	 * Log any developer exceptions and don't build a JPA file rather
	 * than completely failing to build the JPA Project.
	 */
	protected JpaFile buildJpaFile(IFile file) {
		try {
			return this.getJpaPlatform().buildJpaFile(this, file);
		} catch (Exception ex) {
			JptJpaCorePlugin.instance().logError(ex, "Error building JPA file: " + file.getFullPath()); //$NON-NLS-1$
			return null;
		}
	}

	/**
	 * Remove the JPA file corresponding to the specified IFile, if it exists.
	 * Return true if a JPA File was removed, false otherwise
	 */
	protected boolean removeJpaFile(IFile file) {
		JpaFile jpaFile = this.getJpaFile(file);
		if (jpaFile != null) {  // a JpaFile is not added for every IFile
			this.removeJpaFile(jpaFile);
			return true;
		}
		return false;
	}

	/**
	 * Stop listening to the JPA file and remove it.
	 */
	protected void removeJpaFile(JpaFile jpaFile) {
		jpaFile.getResourceModel().removeResourceModelListener(this.resourceModelListener);
		if (this.jpaFiles.remove(jpaFile.getFile()) == null) {
			throw new IllegalArgumentException(jpaFile.toString());
		}
		this.fireItemRemoved(JPA_FILES_COLLECTION, jpaFile);
	}


	// ********** external Java resource types (source or binary) **********

	protected JavaResourceAbstractType buildExternalJavaResourceType(String typeName) {
		IType jdtType = this.findType(typeName);
		return (jdtType == null) ? null : this.buildExternalJavaResourceType(jdtType);
	}

	/**
	 * If the Java project has a class named <code>Foo</code> in the default package,
	 * {@link IJavaProject#findType(String)} will return the {@link IType}
	 * corresponding to <code>Foo</code> if the named passed to it is <code>".Foo"</code>.
	 * This is not what we are expecting! So we had to put in a check for any
	 * type name beginning with <code>'.'</code>.
	 * See JDT bug 377710.
	 */
	protected IType findType(String typeName) {
		try {
			return typeName.startsWith(".") ? null : this.getJavaProject().findType(typeName); //$NON-NLS-1$
		} catch (JavaModelException ex) {
			return null;  // ignore exception? resource exception was probably already logged by JDT
		}
	}

	protected JavaResourceAbstractType buildExternalJavaResourceType(IType jdtType) {
		return jdtType.isBinary() ?
				this.buildBinaryExternalJavaResourceType(jdtType) :
				this.buildSourceExternalJavaResourceType(jdtType);
	}

	protected JavaResourceAbstractType buildBinaryExternalJavaResourceType(IType jdtType) {
		return this.externalJavaResourceTypeCache.addType(jdtType);
	}

	protected JavaResourceAbstractType buildSourceExternalJavaResourceType(IType jdtType) {
		JavaResourceCompilationUnit jrcu = this.getExternalJavaResourceCompilationUnit(jdtType.getCompilationUnit());
		String jdtTypeName = jdtType.getFullyQualifiedName('.');  // JDT member type names use '$'
		for (JavaResourceAbstractType jrat : jrcu.getTypes()) {
			if (jrat.getTypeBinding().getQualifiedName().equals(jdtTypeName)) {
				return jrat;
			}
		}
		// we can get here if the project JRE is removed;
		// see SourceCompilationUnit#getPrimaryType(CompilationUnit)
		// bug 225332
		return null;
	}


	// ********** external Java resource persistent types (binary) **********

	public JavaResourceTypeCache getExternalJavaResourceTypeCache() {
		return this.externalJavaResourceTypeCache;
	}


	// ********** external Java resource compilation units (source) **********

	public Iterable<JavaResourceCompilationUnit> getExternalJavaResourceCompilationUnits() {
		return IterableTools.cloneLive(this.externalJavaResourceCompilationUnits);  // read-only
	}

	public int getExternalJavaResourceCompilationUnitsSize() {
		return this.externalJavaResourceCompilationUnits.size();
	}

	/**
	 * Return the resource model compilation unit corresponding to the specified
	 * JDT compilation unit. If it does not exist, build it.
	 */
	protected JavaResourceCompilationUnit getExternalJavaResourceCompilationUnit(ICompilationUnit jdtCompilationUnit) {
		for (JavaResourceCompilationUnit jrcu : this.getExternalJavaResourceCompilationUnits()) {
			if (jrcu.getCompilationUnit().equals(jdtCompilationUnit)) {
				// we will get here if the JRCU could not build its persistent type...
				return jrcu;
			}
		}
		return this.addExternalJavaResourceCompilationUnit(jdtCompilationUnit);
	}

	/**
	 * Add an external Java resource compilation unit.
	 */
	protected JavaResourceCompilationUnit addExternalJavaResourceCompilationUnit(ICompilationUnit jdtCompilationUnit) {
		JavaResourceCompilationUnit jrcu = this.buildJavaResourceCompilationUnit(jdtCompilationUnit);
		this.addItemToCollection(jrcu, this.externalJavaResourceCompilationUnits, EXTERNAL_JAVA_RESOURCE_COMPILATION_UNITS_COLLECTION);
		jrcu.addResourceModelListener(this.resourceModelListener);
		return jrcu;
	}

	protected JavaResourceCompilationUnit buildJavaResourceCompilationUnit(ICompilationUnit jdtCompilationUnit) {
		return new SourceTypeCompilationUnit(
					jdtCompilationUnit,
					this.jpaPlatform.getAnnotationProvider(),
					this.jpaPlatform.getAnnotationEditFormatter(),
					this.manager.getModifySharedDocumentCommandExecutor()
				);
	}

	protected boolean removeExternalJavaResourceCompilationUnit(IFile file) {
		for (JavaResourceCompilationUnit jrcu : this.getExternalJavaResourceCompilationUnits()) {
			if (jrcu.getFile().equals(file)) {
				this.removeExternalJavaResourceCompilationUnit(jrcu);
				return true;
			}
		}
		return false;
	}

	protected void removeExternalJavaResourceCompilationUnit(JavaResourceCompilationUnit jrcu) {
		jrcu.removeResourceModelListener(this.resourceModelListener);
		this.removeItemFromCollection(jrcu, this.externalJavaResourceCompilationUnits, EXTERNAL_JAVA_RESOURCE_COMPILATION_UNITS_COLLECTION);
	}


	// ********** context model **********

	public JpaRootContextNode getRootContextNode() {
		return this.rootContextNode;
	}


	// ********** utility **********

	public IFile getPlatformFile(IPath runtimePath) {
		return this.getProjectResourceLocator().getPlatformFile(runtimePath);
	}


	// ********** XML files **********

	public JptXmlResource getPersistenceXmlResource() {
		return (JptXmlResource) this.getResourceModel(
				XmlPersistence.DEFAULT_RUNTIME_PATH,
				XmlPersistence.CONTENT_TYPE
			);
	}

	public JptXmlResource getDefaultOrmXmlResource() {
		return this.getMappingFileXmlResource(XmlEntityMappings.DEFAULT_RUNTIME_PATH);
	}

	public JptXmlResource getMappingFileXmlResource(IPath runtimePath) {
		return (JptXmlResource) this.getResourceModel(runtimePath, ResourceMappingFile.Root.CONTENT_TYPE);
	}

	/**
	 * If the specified file exists, is significant to the JPA project, and its
	 * content is a "kind of" the specified content type, return the JPA
	 * resource model corresponding to the file; otherwise, return null.
	 */
	protected JptResourceModel getResourceModel(IPath runtimePath, IContentType contentType) {
		IFile file = this.getPlatformFile(runtimePath);
		return ((file != null) && file.exists()) ? this.getResourceModel(file, contentType) :  null;
	}

	/**
	 * If the specified file is significant to the JPA project and its content
	 * is a "kind of" the specified content type, return the JPA resource model
	 * corresponding to the file; otherwise, return null.
	 */
	protected JptResourceModel getResourceModel(IFile file, IContentType contentType) {
		JpaFile jpaFile = this.getJpaFile(file);
		return (jpaFile == null) ? null : jpaFile.getResourceModel(contentType);
	}


	// ********** annotated Java source classes **********

	/**
	 * Return only those valid annotated Java resource types that are
	 * directly part of the JPA project, ignoring those in JARs referenced in
	 * <code>persistence.xml</code>.
	 * @see JavaResourceAbstractType#isAnnotated()
	 */
	public Iterable<JavaResourceAbstractType> getAnnotatedJavaSourceTypes() {
		// i.e. the type has a valid JPA type annotation
		return IterableTools.filter(this.getInternalSourceJavaResourceTypes(), JavaResourceAnnotatedElement.IS_ANNOTATED);
	}

	/**
	 * Return only the types of those valid <em>managed</em> (i.e. annotated with
	 * <code>@Entity</code>, <code>@Embeddable</code>, <code>@Converter</code>etc.) 
	 * Java resource types that are directly part of the JPA project, ignoring
	 * those in JARs referenced in <code>persistence.xml</code>.
	 */
	public Iterable<JavaResourceAbstractType> getPotentialJavaSourceTypes() {
		return getInternalPotentialSourceJavaResourceTypes();
	}

	/**
	 * Return only those valid <em>managed</em> (i.e. annotated with
	 * <code>@Entity</code>, <code>@Embeddable</code>, <code>@Converter</code>etc.) Java resource
	 * types that are directly part of the JPA project, ignoring
	 * those in JARs referenced in <code>persistence.xml</code>.
	 */
	protected Iterable<JavaResourceAbstractType> getInternalPotentialSourceJavaResourceTypes() {
		Iterable<String> annotationNames = this.getManagedTypeAnnotationNames();
		return IterableTools.filter(
				this.getAnnotatedJavaSourceTypes(),
				new JavaResourceAnnotatedElement.IsAnnotatedWithAnyOf(annotationNames)
			);
	}

	public Iterable<String> getTypeMappingAnnotationNames() {
		return IterableTools.transform(this.getJpaPlatform().getJavaTypeMappingDefinitions(), JavaTypeMappingDefinition.ANNOTATION_NAME_TRANSFORMER);
	}

	public Iterable<String> getManagedTypeAnnotationNames() {
		return IterableTools.concatenate(
				IterableTools.transform(
					this.getJpaPlatform().getJavaManagedTypeDefinitions(), 
					new JavaManagedTypeDefinition.AnnotationNameTransformer(this.getJpaProject())));		
	}

	/**
	 * Return only those Java resource persistent types that are directly
	 * part of the JPA project, ignoring those in JARs referenced in
	 * <code>persistence.xml</code>
	 */
	protected Iterable<JavaResourceAbstractType> getInternalSourceJavaResourceTypes() {
		// get *all* the types in each compilation unit
		return IterableTools.children(this.getInternalJavaResourceCompilationUnits(), JavaResourceNode.Root.TYPES_TRANSFORMER);
	}

	/**
	 * Return the JPA project's resource compilation units.
	 */
	protected Iterable<JavaResourceCompilationUnit> getInternalJavaResourceCompilationUnits() {
		return IterableTools.downCast(IterableTools.transform(this.getJavaSourceJpaFiles(), JpaFile.RESOURCE_MODEL_TRANSFORMER));
	}

	/**
	 * Return the JPA project's JPA files with Java source <em>content</em>.
	 */
	protected Iterable<JpaFile> getJavaSourceJpaFiles() {
		return this.getJpaFiles(JavaResourceCompilationUnit.CONTENT_TYPE);
	}


	// ********** Java resource persistent type look-up **********

	public JavaResourceAbstractType getJavaResourceType(String typeName) {
		for (JavaResourceAbstractType jraType : this.getJavaResourceTypes()) {
			if (jraType.getTypeBinding().getQualifiedName().equals(typeName)) {
				return jraType;
			}
		}
		// if we don't have a type already, try to build new one from the project classpath
		return this.buildExternalJavaResourceType(typeName);
	}

	public JavaResourceAbstractType getJavaResourceType(String typeName, JavaResourceAnnotatedElement.AstNodeType astNodeType) {
		JavaResourceAbstractType resourceType = this.getJavaResourceType(typeName);
		if ((resourceType == null) || (resourceType.getAstNodeType() != astNodeType)) {
			return null;
		}
		return resourceType;
	}


	/**
	 * return *all* the Java resource persistent types, including those in JARs referenced in
	 * persistence.xml
	 */
	protected Iterable<JavaResourceAbstractType> getJavaResourceTypes() {
		return IterableTools.children(this.getJavaResourceNodeRoots(), JavaResourceNode.Root.TYPES_TRANSFORMER);
	}

	@SuppressWarnings("unchecked")
	protected Iterable<JavaResourceNode.Root> getJavaResourceNodeRoots() {
		return IterableTools.concatenate(
					this.getInternalJavaResourceCompilationUnits(),
					this.getInternalJavaResourcePackageFragmentRoots(),
					this.getExternalJavaResourceCompilationUnits(),
					Collections.singleton(this.externalJavaResourceTypeCache)
				);
	}


	// ********** Java resource persistent package look-up **********

	public JavaResourcePackage getJavaResourcePackage(String packageName) {
		for (JavaResourcePackage jrp : this.getJavaResourcePackages()) {
			if (jrp.getName().equals(packageName)) {
				return jrp;
			}
		}
		return null;
	}

	public Iterable<JavaResourcePackage> getJavaResourcePackages(){
		return IterableTools.removeNulls(IterableTools.transform(this.getPackageInfoSourceJpaFiles(), JPA_FILE_JAVA_RESOURCE_PACKAGE_TRANSFORMER));
	}

	protected static final Transformer<JpaFile, JavaResourcePackage> JPA_FILE_JAVA_RESOURCE_PACKAGE_TRANSFORMER = new JpaFileJavaResourcePackageTransformer();
	public static class JpaFileJavaResourcePackageTransformer
		extends TransformerAdapter<JpaFile, JavaResourcePackage>
	{
		@Override
		public JavaResourcePackage transform(JpaFile jpaFile) {
			return ((JavaResourcePackageInfoCompilationUnit) jpaFile.getResourceModel()).getPackage();
		}
	}

	/**
	 * return JPA files with package-info source "content"
	 */
	protected Iterable<JpaFile> getPackageInfoSourceJpaFiles() {
		return this.getJpaFiles(JavaResourceCompilationUnit.PACKAGE_INFO_CONTENT_TYPE);
	}


	// ********** JARs **********

	// TODO
	public JavaResourcePackageFragmentRoot getJavaResourcePackageFragmentRoot(String jarFileName) {
//		return this.getJarResourcePackageFragmentRoot(this.convertToPlatformFile(jarFileName));
		return this.getJavaResourcePackageFragmentRoot(this.project.getFile(jarFileName));
	}

	protected JavaResourcePackageFragmentRoot getJavaResourcePackageFragmentRoot(IFile jarFile) {
		for (JavaResourcePackageFragmentRoot pfr : this.getInternalJavaResourcePackageFragmentRoots()) {
			if (pfr.getFile().equals(jarFile)) {
				return pfr;
			}
		}
		return null;
	}

	protected Iterable<JavaResourcePackageFragmentRoot> getInternalJavaResourcePackageFragmentRoots() {
		return IterableTools.downCast(IterableTools.transform(this.getJarJpaFiles(), JpaFile.RESOURCE_MODEL_TRANSFORMER));
	}

	/**
	 * return JPA files with JAR "content"
	 */
	public Iterable<JpaFile> getJarJpaFiles() {
		return this.getJpaFiles(JavaResourcePackageFragmentRoot.JAR_CONTENT_TYPE);
	}


	// ********** metamodel **********

	public Iterable<JavaResourceAbstractType> getGeneratedMetamodelTopLevelTypes() {
		if (this.metamodelSourceFolderName == null) {
			return EmptyIterable.instance();
		}
		IPackageFragmentRoot genSourceFolder = this.getMetamodelPackageFragmentRoot();
		return IterableTools.filter(
				this.getInternalSourceJavaResourceTypes(),
				new MetamodelSynchronizer.MetamodelTools.IsGeneratedMetamodelTopLevelType(genSourceFolder)
			);
	}

	public JavaResourceAbstractType getGeneratedMetamodelTopLevelType(IFile file) {
		JavaResourceCompilationUnit jrcu = this.getJavaResourceCompilationUnit(file);
		if (jrcu == null) {
			return null;  // hmmm...
		}
		JavaResourceAbstractType primaryType = jrcu.getPrimaryType();
		if (primaryType == null) {
			return null;  // no types in the file
		}
		return MetamodelSynchronizer.MetamodelTools.isGeneratedMetamodelTopLevelType(primaryType) ? primaryType : null;
	}

	protected JavaResourceCompilationUnit getJavaResourceCompilationUnit(IFile file) {
		return (JavaResourceCompilationUnit) this.getResourceModel(file, JavaResourceCompilationUnit.CONTENT_TYPE);
	}

	public String getMetamodelSourceFolderName() {
		return this.metamodelSourceFolderName;
	}

	public void setMetamodelSourceFolderName(String folderName) {
		if (this.setMetamodelSourceFolderName_(folderName)) {
			JpaPreferences.setMetamodelSourceFolderName(this.project, folderName);
			if (folderName == null) {
				this.disposeMetamodel();
			} else {
				this.initializeMetamodel();
			}
		}
	}

	protected boolean setMetamodelSourceFolderName_(String folderName) {
		String old = this.metamodelSourceFolderName;
		this.metamodelSourceFolderName = folderName;
		return this.firePropertyChanged(METAMODEL_SOURCE_FOLDER_NAME_PROPERTY, old, folderName);
	}

	protected void initializeMetamodel() {
		((JpaRootContextNode2_0) this.rootContextNode).initializeMetamodel();
	}

	/**
	 * Synchronize the metamodel for 2.0-compatible JPA projects.
	 */
	protected void synchronizeMetamodel() {
		if (this.isJpa2_0Compatible()) {
			if (this.metamodelSourceFolderName != null) {
				this.scheduleSynchronizeMetamodelJob();
			}
		}
	}

	/**
	 * We dispatch a job even when the JPA project manager is "synchronous"
	 * because we will synchronously execute an update when we receive a
	 * resource change event that is fired when a file is changed; and we
	 * cannot modify another file in response to a file change because the
	 * rules are incompatible. For example, if the <code>persistence.xml</code>
	 * file changes, we will receive a resource change event while the
	 * <code>persistence.xml</code> file is locked. We will not be able to
	 * lock and modify the necessary metamodel source files under this lock.
	 */
	protected void scheduleSynchronizeMetamodelJob() {
		this.buildSynchronizeMetamodelJob().schedule();
	}

	protected Job buildSynchronizeMetamodelJob() {
		Job job = this.buildSynchronizeMetamodelJob_();
		// lock the project so we are synchronized with the JPA project manager
		job.setRule(this.project);
		return job;
	}

	protected Job buildSynchronizeMetamodelJob_() {
		return new SynchronizeMetamodelJob(this.buildSynchronizeMetamodelJobName());
	}

	protected String buildSynchronizeMetamodelJobName() {
		return NLS.bind(JptJpaCoreMessages.METAMODEL_SYNC_JOB_NAME, this.getName());
	}

	/**
	 * Use a {@link WorkspaceJob} to
	 * suppress the resource change events until we are finished synchronizing
	 * the metamodel.
	 */
	protected class SynchronizeMetamodelJob
		extends WorkspaceJob
	{
		protected SynchronizeMetamodelJob(String name) {
			super(name);
		}
		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			return AbstractJpaProject.this.synchronizeMetamodel_(monitor);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, AbstractJpaProject.this);
		}
	}

	/**
	 * Called by the {@link SynchronizeMetamodelJob#runInWorkspace(IProgressMonitor)}.
	 */
	protected IStatus synchronizeMetamodel_(IProgressMonitor monitor) {
		return ((JpaRootContextNode2_0) this.rootContextNode).synchronizeMetamodel(monitor);
	}

	protected void disposeMetamodel() {
		((JpaRootContextNode2_0) this.rootContextNode).disposeMetamodel();
	}

	public IPackageFragmentRoot getMetamodelPackageFragmentRoot() {
		return this.getJavaProject().getPackageFragmentRoot(this.getMetaModelSourceFolder());
	}

	protected IFolder getMetaModelSourceFolder() {
		return this.project.getFolder(this.metamodelSourceFolderName);
	}

	/**
	 * If the metamodel source folder is no longer a Java project source
	 * folder, clear it out.
	 */
	protected void checkMetamodelSourceFolderName() {
		if (this.metamodelSoureFolderNameIsInvalid()) {
			this.setMetamodelSourceFolderName(null);
		}
	}

	protected boolean metamodelSoureFolderNameIsInvalid() {
		return ! this.metamodelSourceFolderNameIsValid();
	}

	protected boolean metamodelSourceFolderNameIsValid() {
		return IterableTools.contains(this.getJavaSourceFolderNames(), this.metamodelSourceFolderName);
	}


	// ********** Java source folder names **********

	public Iterable<String> getJavaSourceFolderNames() {
		try {
			return this.getJavaSourceFolderNames_();
		} catch (JavaModelException ex) {
			JptJpaCorePlugin.instance().logError(ex);
			return EmptyIterable.instance();
		}
	}

	protected Iterable<String> getJavaSourceFolderNames_() throws JavaModelException {
		return IterableTools.transform(this.getJavaSourceFolders(), PACKAGE_FRAGMENT_ROOT_PATH_TRANSFORMER);
	}

	protected static final Transformer<IPackageFragmentRoot, String> PACKAGE_FRAGMENT_ROOT_PATH_TRANSFORMER = new PackageFragmentRootPathTransformer();
	public static class PackageFragmentRootPathTransformer
		extends TransformerAdapter<IPackageFragmentRoot, String>
	{
		@Override
		public String transform(IPackageFragmentRoot pfr) {
			try {
				return this.transform_(pfr);
			} catch (JavaModelException ex) {
				return "Error: " + pfr.getPath(); //$NON-NLS-1$
			}
		}
		protected String transform_(IPackageFragmentRoot pfr) throws JavaModelException {
			return pfr.getUnderlyingResource().getProjectRelativePath().toString();
		}
	}

	protected Iterable<IPackageFragmentRoot> getJavaSourceFolders() throws JavaModelException {
		return IterableTools.filter(
				this.getPackageFragmentRoots(),
				SOURCE_PACKAGE_FRAGMENT_ROOT_FILTER
			);
	}

	protected static final Predicate<IPackageFragmentRoot> SOURCE_PACKAGE_FRAGMENT_ROOT_FILTER =
		new Predicate<IPackageFragmentRoot>() {
			public boolean evaluate(IPackageFragmentRoot pfr) {
				try {
					return this.accept_(pfr);
				} catch (JavaModelException ex) {
					return false;
				}
			}
			private boolean accept_(IPackageFragmentRoot pfr) throws JavaModelException {
				return pfr.exists() && (pfr.getKind() == IPackageFragmentRoot.K_SOURCE);
			}
		};

	protected Iterable<IPackageFragmentRoot> getPackageFragmentRoots() throws JavaModelException {
		return IterableTools.iterable(this.getJavaProject().getPackageFragmentRoots());
	}


	// ********** Java events **********

	// TODO handle changes to external projects
	public void javaElementChanged(ElementChangedEvent event) {
		this.processJavaDelta(event.getDelta());
	}

	/**
	 * We recurse back here from {@link #processJavaDeltaChildren(IJavaElementDelta)}.
	 * 
	 * <br> <b>
	 * This code has been copied and modified in InternalJpaProjectManager, so make sure
	 * to make changes in both locations.
	 * </b>
	 * @see InternalJpaProjectManager#javaElementChanged(ElementChangedEvent)
	 */
	protected void processJavaDelta(IJavaElementDelta delta) {
		switch (delta.getElement().getElementType()) {
			case IJavaElement.JAVA_MODEL :
				this.processJavaModelDelta(delta);
				break;
			case IJavaElement.JAVA_PROJECT :
				this.processJavaProjectDelta(delta);
				break;
			case IJavaElement.PACKAGE_FRAGMENT_ROOT :
				this.processJavaPackageFragmentRootDelta(delta);
				break;
			case IJavaElement.PACKAGE_FRAGMENT :
				this.processJavaPackageFragmentDelta(delta);
				break;
			case IJavaElement.COMPILATION_UNIT :
				this.processJavaCompilationUnitDelta(delta);
				break;
			default :
				break; // ignore the elements inside a compilation unit
		}
	}

	protected void processJavaDeltaChildren(IJavaElementDelta delta) {
		for (IJavaElementDelta child : delta.getAffectedChildren()) {
			this.processJavaDelta(child); // recurse
		}
	}

	/**
	 * Return whether the specified Java element delta is for a
	 * {@link IJavaElementDelta#CHANGED CHANGED}
	 * (as opposed to {@link IJavaElementDelta#ADDED ADDED} or
	 * {@link IJavaElementDelta#REMOVED REMOVED}) Java element
	 * and the specified flag is set.
	 * (The delta flags are only significant if the delta
	 * {@link IJavaElementDelta#getKind() kind} is
	 * {@link IJavaElementDelta#CHANGED CHANGED}.)
	 */
	protected static boolean deltaFlagIsSet(IJavaElementDelta delta, int flag) {
		return (delta.getKind() == IJavaElementDelta.CHANGED) &&
				BitTools.flagIsSet(delta.getFlags(), flag);
	}

	// ***** model
	protected void processJavaModelDelta(IJavaElementDelta delta) {
		// process the Java model's projects
		this.processJavaDeltaChildren(delta);
	}

	// ***** project
	protected void processJavaProjectDelta(IJavaElementDelta delta) {
		// process the Java project's package fragment roots
		this.processJavaDeltaChildren(delta);

		// a classpath change can have pretty far-reaching effects...
		if (classpathHasChanged(delta)) {
			this.rebuild((IJavaProject) delta.getElement());
		}
	}

	/**
	 * The specified Java project's classpath changed. Rebuild the JPA project
	 * as appropriate.
	 */
	protected void rebuild(IJavaProject javaProject) {
		// if the classpath has changed, we need to update everything since
		// class references could now be resolved (or not) etc.
		if (javaProject.equals(this.getJavaProject())) {
			this.removeDeadJpaFiles();
			this.checkMetamodelSourceFolderName();
			this.synchronizeWithJavaSource(this.getInternalJavaResourceCompilationUnits());
		} else {
			// TODO see if changed project is on our classpath?
			this.synchronizeWithJavaSource(this.getExternalJavaResourceCompilationUnits());
		}
	}

	/**
	 * Loop through all our JPA files, remove any that are no longer on the
	 * classpath.
	 */
	protected void removeDeadJpaFiles() {
		for (JpaFile jpaFile : this.getJpaFiles()) {
			if (this.jpaFileIsDead(jpaFile)) {
				this.removeJpaFile(jpaFile);
			}
		}
	}

	protected boolean jpaFileIsDead(JpaFile jpaFile) {
		return ! this.jpaFileIsAlive(jpaFile);
	}

	/**
	 * Sometimes (e.g. during tests), when a project has been deleted, we get a
	 * Java change event that indicates the Java project is CHANGED (as
	 * opposed to REMOVED, which is what typically happens). The event's delta
	 * indicates that everything in the Java project has been deleted and the
	 * classpath has changed. All entries in the classpath have been removed;
	 * but single entry for the Java project's root folder has been added. (!)
	 * This means any file in the project is on the Java project's classpath.
	 * This classpath change is what triggers us to rebuild the JPA project; so
	 * we put an extra check here to make sure the JPA file's resource file is
	 * still present.
	 * <p>
	 * This would not be a problem if Dali received the resource change event
	 * <em>before</em> JDT and simply removed the JPA project; but JDT receives
	 * the resource change event first and converts it into the problematic
	 * Java change event....
	 */
	protected boolean jpaFileIsAlive(JpaFile jpaFile) {
		IFile file = jpaFile.getFile();
		if ( ! file.exists()) {
			return false;
		}
		if (this.fileIsJavaRelated(file)) {
			return this.getJavaProject().isOnClasspath(file);
		}
		return this.fileResourceLocationIsValid(file);
	}

	/**
	 * pre-condition:
	 * delta.getElement().getElementType() == IJavaElement.JAVA_PROJECT
	 */
	protected static boolean classpathHasChanged(IJavaElementDelta delta) {
		return deltaFlagIsSet(delta, IJavaElementDelta.F_RESOLVED_CLASSPATH_CHANGED);
	}

	protected void synchronizeWithJavaSource(Iterable<JavaResourceCompilationUnit> javaResourceCompilationUnits) {
		for (JavaResourceCompilationUnit javaResourceCompilationUnit : javaResourceCompilationUnits) {
			javaResourceCompilationUnit.synchronizeWithJavaSource();
		}
	}

	// ***** package fragment root
	protected void processJavaPackageFragmentRootDelta(IJavaElementDelta delta) {
		// process the Java package fragment root's package fragments
		this.processJavaDeltaChildren(delta);

		if (classpathEntryHasBeenAdded(delta)) {
			// TODO bug 277218
		} else if (classpathEntryHasBeenRemoved(delta)) {  // should be mutually-exclusive w/added (?)
			// TODO bug 277218
		}
	}

	/**
	 * pre-condition:
	 * delta.getElement().getElementType() == IJavaElement.PACKAGE_FRAGMENT_ROOT
	 */
	protected static boolean classpathEntryHasBeenAdded(IJavaElementDelta delta) {
		return deltaFlagIsSet(delta, IJavaElementDelta.F_ADDED_TO_CLASSPATH);
	}

	/**
	 * pre-condition:
	 * delta.getElement().getElementType() == IJavaElement.PACKAGE_FRAGMENT_ROOT
	 */
	protected static boolean classpathEntryHasBeenRemoved(IJavaElementDelta delta) {
		return deltaFlagIsSet(delta, IJavaElementDelta.F_REMOVED_FROM_CLASSPATH);
	}

	// ***** package fragment
	protected void processJavaPackageFragmentDelta(IJavaElementDelta delta) {
		// process the java package fragment's compilation units
		this.processJavaDeltaChildren(delta);
	}

	// ***** compilation unit
	protected void processJavaCompilationUnitDelta(IJavaElementDelta delta) {
		if (javaCompilationUnitDeltaIsRelevant(delta)) {
			ICompilationUnit compilationUnit = (ICompilationUnit) delta.getElement();
			for (JavaResourceCompilationUnit jrcu : this.getCombinedJavaResourceCompilationUnits()) {
				if (jrcu.getCompilationUnit().equals(compilationUnit)) {
					CompilationUnit astRoot = delta.getCompilationUnitAST(); //this will be null for POST_CHANGE jdt events
					//I don't think we can guarantee hasResolvedBindings() is true, so check for this and don't pass it in if false.
					if (astRoot != null && astRoot.getAST().hasResolvedBindings()) { 

						jrcu.synchronizeWithJavaSource(astRoot);
					}
					else {
						jrcu.synchronizeWithJavaSource();
					}
					// TODO ? this.resolveJavaTypes();  // might have new member types now...
					break;  // there *shouldn't* be any more...
				}
			}
		}
		// ignore the java compilation unit's children
	}

	protected static boolean javaCompilationUnitDeltaIsRelevant(IJavaElementDelta delta) {
		// ignore Java notification for ADDED or REMOVED;
		// these are handled via resource notification
		if (delta.getKind() != IJavaElementDelta.CHANGED) {
			return false;
		}

		// ignore changes to/from primary working copy - no content has changed;
		// and make sure there are no other flags set that indicate *both* a
		// change to/from primary working copy *and* content has changed
		if (BitTools.onlyFlagIsSet(delta.getFlags(), IJavaElementDelta.F_PRIMARY_WORKING_COPY)) {
			return false;
		}

		// ignore when the compilation unit's resource is deleted;
		// because the AST parser will log an exception for the missing file. 
		// IJavaElementDelta.F_PRIMARY_RESOURCE is the only flag set when refactor
		// renaming a type directly in the java editor when the file is modified..
		// IJavaElementDelta.F_AST_AFFECTED is the only flag set when I refactor
		// rename a type directly in the java editor and the file is *not* modified.
		if (BitTools.onlyFlagIsSet(delta.getFlags(), IJavaElementDelta.F_PRIMARY_RESOURCE)
			|| BitTools.onlyFlagIsSet(delta.getFlags(), IJavaElementDelta.F_AST_AFFECTED)) {
			ICompilationUnit compilationUnit = (ICompilationUnit) delta.getElement();
			if ( !compilationUnitResourceExists(compilationUnit)) {
				return false;
			}
		}

		return true;
	}


	protected static boolean compilationUnitResourceExists(ICompilationUnit compilationUnit) {
		try {
			return compilationUnit.getCorrespondingResource().exists();
		} catch (JavaModelException ex) {
			return false;
		}
	}


	// ********** validation **********

	public Iterable<IMessage> getValidationMessages(IReporter reporter) {
		ArrayList<IMessage> messages = new ArrayList<IMessage>();
		this.validate(messages, reporter);
		return messages;
	}

	// TODO about the only use for the reporter is to check for cancellation;
	// we should check for cancellation...
	protected void validate(List<IMessage> messages, IReporter reporter) {
		if (reporter.isCancelled()) {
			throw new ValidationCancelledException();
		}
		this.validateLibraryProvider(messages);
		this.validateConnection(messages);
		this.rootContextNode.validate(messages, reporter);
	}

	protected void validateLibraryProvider(List<IMessage> messages) {
		try {
			this.validateLibraryProvider_(messages);
		} catch (CoreException ex) {
			JptJpaCorePlugin.instance().logError(ex);
		}
	}

	protected void validateLibraryProvider_(List<IMessage> messages) throws CoreException {
		Map<String, Object> enablementVariables = new HashMap<String, Object>();
		enablementVariables.put(JpaLibraryProviderInstallOperationConfig.JPA_PLATFORM_ENABLEMENT_EXP, this.getJpaPlatform().getId());
		enablementVariables.put(JpaLibraryProviderInstallOperationConfig.JPA_PLATFORM_DESCRIPTION_ENABLEMENT_EXP, this.getJpaPlatform().getConfig());

		ILibraryProvider libraryProvider = LibraryProviderFramework.getCurrentProvider(this.project, JpaProject.FACET);
		IFacetedProject facetedProject = ProjectFacetsManager.create(this.project);
		IProjectFacetVersion facetVersion = facetedProject.getInstalledVersion(JpaProject.FACET);
		if ( ! libraryProvider.isEnabledFor(facetedProject, facetVersion, enablementVariables)) {
			messages.add(
				ValidationMessageTools.buildValidationMessage(
					this.getResource(),
					JptJpaCoreValidationMessages.PROJECT_INVALID_LIBRARY_PROVIDER
				)
			);
		}
	}

	protected void validateConnection(List<IMessage> messages) {
		String cpName = this.dataSource.getConnectionProfileName();
		if (StringTools.isBlank(cpName)) {
			messages.add(
				ValidationMessageTools.buildValidationMessage(
					this.getResource(),
					JptJpaCoreValidationMessages.PROJECT_NO_CONNECTION
				)
			);
			return;
		}
		ConnectionProfile cp = this.dataSource.getConnectionProfile();
		if (cp == null) {
			messages.add(
				ValidationMessageTools.buildValidationMessage(
					this.getResource(),
					JptJpaCoreValidationMessages.PROJECT_INVALID_CONNECTION,
					cpName
				)
			);
			return;
		}
		if (cp.isInactive()) {
			messages.add(
				ValidationMessageTools.buildValidationMessage(
					this.getResource(),
					JptJpaCoreValidationMessages.PROJECT_INACTIVE_CONNECTION,
					cpName
				)
			);
		}
	}


	// ********** dispose **********

	public void dispose() {
		this.stopCommand(this.synchronizeContextModelCommand);
		this.stopCommand(this.updateCommand);
		this.updateCommand.removeListener(this.updateCommandListener);
		this.dataSource.dispose();
		// the XML resources are held indefinitely by the WTP translator framework,
		// so we better remove our listener or the JPA project will not be GCed
		for (JpaFile jpaFile : this.getJpaFiles()) {
			jpaFile.getResourceModel().removeResourceModelListener(this.resourceModelListener);
		}
	}

	protected void stopCommand(RepeatingJobCommand command) {
		try {
			command.stop();
		} catch (InterruptedException ex) {
			// allow the dispose to complete;
			// but let our thread know it was interrupted during a wait
			Thread.currentThread().interrupt();
		}
	}


	// ********** resource model listener **********

	protected JptResourceModelListener buildResourceModelListener() {
		return new ResourceModelListener();
	}

	protected class ResourceModelListener
		implements JptResourceModelListener
	{
		protected ResourceModelListener() {
			super();
		}
		public void resourceModelChanged(JptResourceModel jpaResourceModel) {
//			String msg = Thread.currentThread() + " resource model change: " + jpaResourceModel;
//			System.out.println(msg);
//			new Exception(msg).printStackTrace(System.out);
			AbstractJpaProject.this.synchronizeContextModel(jpaResourceModel);
		}

		public void resourceModelReverted(JptResourceModel jpaResourceModel) {
			IFile file = WorkbenchResourceHelper.getFile((JptXmlResource)jpaResourceModel);
			AbstractJpaProject.this.removeJpaFile(file);
			AbstractJpaProject.this.addJpaFileMaybe(file);
		}

		public void resourceModelUnloaded(JptResourceModel jpaResourceModel) {
			IFile file = WorkbenchResourceHelper.getFile((JptXmlResource)jpaResourceModel);
			AbstractJpaProject.this.removeJpaFile(file);
			if (file.exists()) { //false if file delete caused the unload event
				//go ahead and re-add the JPA file here, otherwise a resource change event
				//will cause it to be added.
				AbstractJpaProject.this.addJpaFileMaybe(file);
			}
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, AbstractJpaProject.this);
		}
	}

	/**
	 * Called from {@link ResourceModelListener#resourceModelChanged(JptResourceModel)}.
	 */
	// TODO pass down the resource model (for a possible optimization?)
	protected void synchronizeContextModel(@SuppressWarnings("unused") JptResourceModel jpaResourceModel) {
		this.synchronizeContextModel();
	}


	// ********** resource events **********

	// TODO need to do the same thing for external projects and compilation units
	public void projectChanged(IResourceDelta delta) {
		if (delta.getResource().equals(this.project)) {
			this.internalProjectChanged(delta);
		} else {
			this.externalProjectChanged(delta);
		}
	}

	protected void internalProjectChanged(IResourceDelta delta) {
		ResourceDeltaVisitor resourceDeltaVisitor = this.buildInternalResourceDeltaVisitor();
		resourceDeltaVisitor.visitDelta(delta);
		// at this point, if we have added and/or removed JpaFiles, an "update" will have been triggered;
		// any changes to the resource model during the "resolve" will trigger further "updates";
		// there should be no need to "resolve" external Java types (they can't have references to
		// the internal Java types)
		if (resourceDeltaVisitor.encounteredSignificantChange()) {
			this.resolveInternalJavaTypes();
		}
	}

	protected ResourceDeltaVisitor buildInternalResourceDeltaVisitor() {
		return new InternalResourceDeltaVisitor();
	}

	protected class InternalResourceDeltaVisitor
		extends ResourceDeltaVisitor
	{
		protected InternalResourceDeltaVisitor() {
			super();
		}
		@Override
		public boolean fileChangeIsSignificant(IFile file, int deltaKind) {
			return AbstractJpaProject.this.synchronizeJpaFiles(file, deltaKind);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, AbstractJpaProject.this);
		}
	}

	/**
	 * Internal resource delta visitor callback.
	 * Return true if a JpaFile was either added or removed.
	 */
	protected boolean synchronizeJpaFiles(IFile file, int deltaKind) {
		switch (deltaKind) {
			case IResourceDelta.ADDED :
				return this.addJpaFileMaybe(file);
			case IResourceDelta.REMOVED :
				return this.removeJpaFile(file);
			case IResourceDelta.CHANGED :
				return this.checkForChangedFileContent(file);
			case IResourceDelta.ADDED_PHANTOM :
				break;  // ignore
			case IResourceDelta.REMOVED_PHANTOM :
				break;  // ignore
			default :
				break;  // only worried about added/removed/changed files
		}

		return false;
	}

	protected boolean checkForChangedFileContent(IFile file) {
		JpaFile jpaFile = this.getJpaFile(file);
		if (jpaFile == null) {
			// the file might have changed its content to something significant to Dali
			return this.addJpaFileMaybe(file);
		}

		if (jpaFile.getContentType().equals(getContentType(file))) {
			// content has not changed - ignore
			return false;
		}

		// the content type changed, we need to remove the old JPA file and build a new one
		// (e.g. the schema of an orm.xml file changed from JPA to EclipseLink)
		this.removeJpaFile(jpaFile);
		this.addJpaFileMaybe(file);
		return true;  // at the least, we have removed a JPA file
	}

	protected void resolveInternalJavaTypes() {
		for (JavaResourceCompilationUnit jrcu : this.getInternalJavaResourceCompilationUnits()) {
			jrcu.resolveTypes();
		}
	}

	protected void externalProjectChanged(IResourceDelta delta) {
		if (this.getJavaProject().isOnClasspath(delta.getResource())) {
			ResourceDeltaVisitor resourceDeltaVisitor = this.buildExternalResourceDeltaVisitor();
			resourceDeltaVisitor.visitDelta(delta);
			// force an "update" here since adding and/or removing an external Java type
			// will only trigger an "update" if the "resolve" causes something in the resource model to change
			if (resourceDeltaVisitor.encounteredSignificantChange()) {
				this.update();
				this.resolveExternalJavaTypes();
				this.resolveInternalJavaTypes();
			}
		}
	}

	protected ResourceDeltaVisitor buildExternalResourceDeltaVisitor() {
		return new ExternalResourceDeltaVisitor();
	}

	protected class ExternalResourceDeltaVisitor
		extends ResourceDeltaVisitor
	{
		protected ExternalResourceDeltaVisitor() {
			super();
		}
		@Override
		public boolean fileChangeIsSignificant(IFile file, int deltaKind) {
			return AbstractJpaProject.this.synchronizeExternalFiles(file, deltaKind);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, AbstractJpaProject.this);
		}
	}

	/**
	 * external resource delta visitor callback
	 * Return true if an "external" Java resource compilation unit
	 * was added or removed.
	 */
	protected boolean synchronizeExternalFiles(IFile file, int deltaKind) {
		switch (deltaKind) {
			case IResourceDelta.ADDED :
				return this.externalFileAdded(file);
			case IResourceDelta.REMOVED :
				return this.externalFileRemoved(file);
			case IResourceDelta.CHANGED :
				break;  // ignore
			case IResourceDelta.ADDED_PHANTOM :
				break;  // ignore
			case IResourceDelta.REMOVED_PHANTOM :
				break;  // ignore
			default :
				break;  // only worried about added/removed/changed files
		}

		return false;
	}

	protected boolean externalFileAdded(IFile file) {
		IContentType contentType = getContentType(file);
		if (contentType == null) {
			return false;
		}
		if (contentType.equals(JavaResourceCompilationUnit.CONTENT_TYPE)) {
			return true;
		}
		if (contentType.equals(JavaResourcePackageFragmentRoot.JAR_CONTENT_TYPE)) {
			return true;
		}
		return false;
	}

	protected boolean externalFileRemoved(IFile file) {
		IContentType contentType = getContentType(file);
		if (contentType == null) {
			return false;
		}
		if (contentType.equals(JavaResourceCompilationUnit.CONTENT_TYPE)) {
			return this.removeExternalJavaResourceCompilationUnit(file);
		}
		if (contentType.equals(JavaResourcePackageFragmentRoot.JAR_CONTENT_TYPE)) {
			return this.externalJavaResourceTypeCache.removeTypes(file);
		}
		return false;
	}

	protected IContentType getContentType(IFile file) {
		return this.getJpaPlatform().getContentType(file);
	}

	protected void resolveExternalJavaTypes() {
		for (JavaResourceCompilationUnit jrcu : this.getExternalJavaResourceCompilationUnits()) {
			jrcu.resolveTypes();
		}
	}

	// ***** resource delta visitors
	/**
	 * add or remove a JPA file for every [appropriate] file encountered by the visitor
	 */
	protected abstract class ResourceDeltaVisitor
		implements IResourceDeltaVisitor
	{
		protected boolean encounteredSignificantChange = false;

		protected ResourceDeltaVisitor() {
			super();
		}

		protected void visitDelta(IResourceDelta delta) {
			try {
				delta.accept(this);
			} catch (CoreException ex) {
				// shouldn't happen - we don't throw any CoreExceptions
				throw new RuntimeException(ex);
			}
		}

		public boolean visit(IResourceDelta delta) {
			IResource res = delta.getResource();
			switch (res.getType()) {
				case IResource.ROOT :
					return true;  // visit children
				case IResource.PROJECT :
					return true;  // visit children
				case IResource.FOLDER :
					return true;  // visit children
				case IResource.FILE :
					this.fileChanged((IFile) res, delta.getKind());
					return false;  // no children
				default :
					return false;  // no children (probably shouldn't get here...)
			}
		}

		protected void fileChanged(IFile file, int deltaKind) {
			if (this.fileChangeIsSignificant(file, deltaKind)) {
				this.encounteredSignificantChange = true;
			}
		}

		protected abstract boolean fileChangeIsSignificant(IFile file, int deltaKind);

		/**
		 * Return whether the visitor encountered some sort of "significant"
		 * change while traversing the IResourceDelta
		 * (e.g. a JPA file was added or removed).
		 */
		protected boolean encounteredSignificantChange() {
			return this.encounteredSignificantChange;
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, AbstractJpaProject.this);
		}
	}


	// ********** synchronize context model with resource model **********

	protected RepeatingJobCommand buildSynchronizeContextModelCommand() {
		return new RepeatingJobCommandWrapper(
					this.buildSynchronizeContextModelJobCommand(),
					this.buildStartSynchronizeContextModelJobCommandExecutor(),
					JptJpaCorePlugin.instance().getExceptionHandler()
				);
	}

	protected JobCommand buildSynchronizeContextModelJobCommand() {
		return new SynchronizeContextModelJobCommand();
	}

	protected JobCommandExecutor buildStartSynchronizeContextModelJobCommandExecutor() {
		return new ManagerJobCommandExecutor(this.buildSynchronizeContextModelJobName());
	}

	protected String buildSynchronizeContextModelJobName() {
		return NLS.bind(JptJpaCoreMessages.CONTEXT_MODEL_SYNC_JOB_NAME, this.getName());
	}

	/**
	 * The JPA project's resource model has changed; synchronize the JPA
	 * project's context model with it. This method is typically called when the
	 * resource model state has changed when it is synchronized with its
	 * underlying Eclipse resource as the result of an Eclipse resource change
	 * event. This method can also be called when a client (e.g. a JUnit test
	 * case) has manipulated the resource model via its API (as opposed to
	 * modifying the underlying Eclipse resource directly) and needs the context
	 * model to be synchronized accordingly (since manipulating the resource
	 * model via its API will not trigger this method). Whether the context
	 * model is synchronously (or asynchronously) depends on the JPA project
	 * manager.
	 */
	public void synchronizeContextModel() {
		try {
			this.synchronizingContextModel = true;
			this.synchronizeContextModelCommand.execute(null);  // this progress monitor is ignored
		} finally {
			this.synchronizingContextModel = false;
		}

		// There are some changes to the resource model that will not change
		// the existing context model and trigger an update (e.g. adding an
		// @Entity annotation when the JPA project is automatically
		// discovering annotated classes); so we explicitly execute an update
		// here to discover those changes.
		// TODO change sync so it will *always* trigger an update?
		this.update();
	}

	protected class SynchronizeContextModelJobCommand
		implements JobCommand
	{
		public IStatus execute(IProgressMonitor monitor) {
			return AbstractJpaProject.this.synchronizeContextModel(monitor);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, AbstractJpaProject.this);
		}
	}

	/**
	 * Called by the {@link SynchronizeContextModelJobCommand#execute(IProgressMonitor)}.
	 */
	// TODO pass the monitor down
	protected IStatus synchronizeContextModel(@SuppressWarnings("unused") IProgressMonitor monitor) {
		this.rootContextNode.synchronizeWithResourceModel();
		return Status.OK_STATUS;
	}


	// ********** JPA project "update" **********

	/**
	 * The first update is executed synchronously during construction.
	 * Once that is complete, we delegate to the JPA project manager.
	 */
	protected NotifyingRepeatingJobCommand buildTempUpdateCommand() {
		return new NotifyingRepeatingJobCommandWrapper(this.buildUpdateJobCommand(), JptJpaCorePlugin.instance().getExceptionHandler());
	}

	protected NotifyingRepeatingJobCommand buildUpdateCommand() {
		return new NotifyingRepeatingJobCommandWrapper(
					this.buildUpdateJobCommand(),
					this.buildStartUpdateJobCommandExecutor(),
					JptJpaCorePlugin.instance().getExceptionHandler()
				);
	}

	protected JobCommand buildUpdateJobCommand() {
		return new UpdateJobCommand();
	}

	protected JobCommandExecutor buildStartUpdateJobCommandExecutor() {
		return new ManagerJobCommandExecutor(this.buildUpdateJobName());
	}

	protected String buildUpdateJobName() {
		return NLS.bind(JptJpaCoreMessages.UPDATE_JOB_NAME, this.getName());
	}

	@Override
	public void stateChanged() {
		super.stateChanged();
		this.update();
	}

	/**
	 * The JPA project's state has changed, "update" those parts of the
	 * JPA project that are dependent on other parts of the JPA project.
	 * <p>
	 * Delegate to the JPA project manager so clients can configure how
	 * updates occur.
	 * <p>
	 * Ignore any <em>updates</em> that occur while we are synchronizing
	 * the context model with the resource model because we will <em>update</em>
	 * the context model at the completion of the <em>sync</em>. This is really
	 * only useful for synchronous <em>syncs</em> and <em>updates</em>; since
	 * the job scheduling rules will prevent the <em>sync</em> and
	 * <em>update</em> jobs from running concurrently.
	 */
	protected void update() {
		if ( ! this.synchronizingContextModel) {
			this.updateCommand.execute(null);  // this progress monitor is ignored
		}
	}

	protected class UpdateJobCommand
		implements JobCommand
	{
		public IStatus execute(IProgressMonitor monitor) {
			return AbstractJpaProject.this.update(monitor);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, AbstractJpaProject.this);
		}
	}

	/**
	 * Called by the {@link UpdateJobCommand#execute(IProgressMonitor)}.
	 */
	// TODO pass the monitor down
	protected IStatus update(@SuppressWarnings("unused") IProgressMonitor monitor) {
		this.rootContextNode.update();
		this.updateRootStructureNodes();
		return Status.OK_STATUS;
	}

	protected void updateRootStructureNodes() {
		for (JpaFile jpaFile : this.getJpaFiles()) {
			jpaFile.updateRootStructureNodes();
		}
	}

	// ********** update command listener **********

	protected NotifyingRepeatingJobCommand.Listener buildUpdateCommandListener() {
		return new UpdateCommandListener();
	}

	protected class UpdateCommandListener
		implements NotifyingRepeatingJobCommand.Listener
	{
		public void executionQuiesced(JobCommand command) {
			AbstractJpaProject.this.updateQuiesced();
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, AbstractJpaProject.this);
		}
	}

	/**
	 * This is the callback used by the update command to notify the JPA
	 * project that the "update" has quiesced (i.e. the "update" has completed
	 * and there are no outstanding requests for further "updates").
	 * Called by {@link UpdateCommandListener#executionQuiesced(JobCommand)}.
	 */
 	protected void updateQuiesced() {
		this.synchronizeMetamodel();
	}


	// ********** job command executor **********

 	/**
	 * Delegate execution to the JPA project manager, which will determine
	 * whether commands are executed synchronously or asynchronously.
	 * 
	 * @see #buildStartSynchronizeContextModelJobCommandExecutor()
	 * @see #buildStartUpdateJobCommandExecutor()
	 */
	protected class ManagerJobCommandExecutor
		implements JobCommandExecutor
	{
		protected final String defaultJobName;
		protected ManagerJobCommandExecutor(String defaultJobName) {
			super();
			if (defaultJobName == null) {
				throw new NullPointerException();
			}
			this.defaultJobName = defaultJobName;
		}
		/**
		 * This should be the only method called on this executor....
		 */
		public void execute(JobCommand command) {
			this.execute(command, this.defaultJobName);
		}
		public void execute(JobCommand command, String jobName) {
			AbstractJpaProject.this.manager.execute(command, jobName, AbstractJpaProject.this);
		}
		public void execute(JobCommand command, String jobName, ISchedulingRule schedulingRule) {
			// the JPA project manager will supply the scheduling rule
			this.execute(command, jobName);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, this.defaultJobName);
		}
	}
}
