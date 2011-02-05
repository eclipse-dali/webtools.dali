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

import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.utility.CommandExecutor;
import org.eclipse.jpt.common.utility.synchronizers.CallbackSynchronizer;
import org.eclipse.jpt.common.utility.synchronizers.Synchronizer;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentTypeCache;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * A JPA project is associated with an Eclipse project (and its corresponding
 * Java project). It holds the <em>resource</em> model that corresponds to the 
 * various JPA-related resources (the <code>persistence.xml</code> file, its
 * mapping files [<code>orm.xml</code>], and the Java source files). It also
 * holds the <em>context</em> model that represents
 * the JPA metadata, as derived from spec-defined defaults, Java source code
 * annotations, and XML descriptors.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.0
 */
public interface JpaProject
	extends JpaNode
{

	// ********** general **********

	/**
	 * Return the JPA project's name, which is the same as the associated
	 * Eclipse project's name.
	 */
	String getName();

	/**
	 * Return the Eclipse project associated with the JPA project.
	 */
	IProject getProject();

	/**
	 * Return the Java project associated with the JPA project.
	 */
	IJavaProject getJavaProject();

	/**
	 * Return the vendor-specific JPA platform that builds the JPA project
	 * and its contents.
	 */
	JpaPlatform getJpaPlatform();

	/**
	 * Return the root of the JPA project's context model.
	 */
	JpaRootContextNode getRootContextNode();

	/**
	 * The JPA project has been removed from the JPA model. Clean up any
	 * hooks to external resources etc.
	 */
	void dispose();


	// ********** JPA files **********

	/** 
	 * ID string used when the JPA project's collection of JPA files changes.
	 * @see #addCollectionChangeListener(String, org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener)
	 */
	String JPA_FILES_COLLECTION = "jpaFiles"; //$NON-NLS-1$

	/**
	 * Return the JPA project's JPA files.
	 */
	Iterator<JpaFile> jpaFiles();

	/**
	 * Return the size of the JPA project's JPA files.
	 */
	int jpaFilesSize();

	/**
	 * Return the JPA file corresponding to the specified file.
	 * Return null if there is no JPA file associated with the specified file.
	 */
	JpaFile getJpaFile(IFile file);


	// ********** external Java resource compilation units **********

	/** 
	 * ID string used when the JPA project's collection of external Java
	 * resource compilation units changes.
	 * @see #addCollectionChangeListener(String, org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener)
	 */
	String EXTERNAL_JAVA_RESOURCE_COMPILATION_UNITS_COLLECTION = "externalJavaResourceCompilationUnits"; //$NON-NLS-1$

	/**
	 * Return the JPA project's external Java resource compilation units.
	 */
	Iterator<JavaResourceCompilationUnit> externalJavaResourceCompilationUnits();

	/**
	 * Return the size of the JPA project's external Java resource compilation units.
	 */
	int externalJavaResourceCompilationUnitsSize();


	// ********** external Java resource persistent types **********

	/**
	 * Return the JPA project's external Java resource persistent type cache.
	 */
	JavaResourcePersistentTypeCache getExternalJavaResourcePersistentTypeCache();


	// ********** XML resources **********
	
	/**
	 * Return the XML resource model corresponding to the file with runtime path
	 * <code>META-INF/persistence.xml</code> if that file has the persistence content type
	 * (<code>"org.eclipse.jpt.core.content.persistence"</code>).
	 * 
	 * @see JptCorePlugin#DEFAULT_PERSISTENCE_XML_RUNTIME_PATH
	 * @see JptCorePlugin#PERSISTENCE_XML_CONTENT_TYPE
	 */
	JpaXmlResource getPersistenceXmlResource();
	
	/**
	 * Return the XML resource model corresponding to the file with the specified
	 * runtime path if that file has the mapping file content type
	 * (<code>"org.eclipse.jpt.core.content.mappingFile"</code>)
	 * 
	 * @see JptCorePlugin#MAPPING_FILE_CONTENT_TYPE
	 */
	JpaXmlResource getMappingFileXmlResource(IPath runtimePath);

	/**
	 * Return the XML resource model corresponding to the file
	 * <code>META-INF/orm.xml</code> if that file has the mapping file content type.
	 * 
	 * @see JptCorePlugin#DEFAULT_ORM_XML_RUNTIME_PATH
	 */
	JpaXmlResource getDefaultOrmXmlResource();
	
	
	// ********** Java resources **********

	/**
	 * Return the names of the JPA project's annotated Java classes
	 * (ignoring classes in JARs referenced in the <code>persistence.xml</code>).
	 */
	Iterator<String> annotatedJavaSourceClassNames();
	
	/**
	 * Return only the names of those valid <em>mapped</em> (i.e. annotated with
	 * <code>@Entity</code>, <code>@Embeddable</code>, etc.) Java resource
	 * persistent types that are directly part of the JPA project, ignoring
	 * those in JARs referenced in <code>persistence.xml</code>.
	 */
	Iterable<String> getMappedJavaSourceClassNames();

	/**
	 * Return the Java resource persistent type for the specified type.
	 * Return null if invalid or absent.
	 */
	JavaResourcePersistentType getJavaResourcePersistentType(String typeName);

	/**
	 * Return the Java resource package for the specified package.
	 * Return null if invalid or absent.
	 */
	JavaResourcePackage getJavaResourcePackage(String packageName);

	/**
	 * Return the Java resource packages for the project.
	 * Return null if invalid or absent.
	 */
	Iterable<JavaResourcePackage> getJavaResourcePackages();

	/**
	 * Return the Java resource package fragement root for the specified JAR.
	 * Return null if absent.
	 */
	JavaResourcePackageFragmentRoot getJavaResourcePackageFragmentRoot(String jarFileName);

	/**
	 * Return the JPA project's JPA files for jars.
	 */
	Iterable<JpaFile> getJarJpaFiles();


	// ********** external events **********

	/**
	 * Synchronize the JPA project with the specified project resource
	 * delta, watching for added and removed files in particular.
	 */
	void projectChanged(IResourceDelta delta);

	/**
	 * Synchronize the JPA project with the specified Java change.
	 */
	void javaElementChanged(ElementChangedEvent event);


	// ********** synchronize context model with resource model **********

	/**
	 * Return the synchronizer that will synchronize the context model with
	 * the resource model whenever the resource model changes.
	 */
	Synchronizer getContextModelSynchronizer();

	/**
	 * Set the synchronizer that will keep the context model synchronized with
	 * the resource model whenever the resource model changes.
	 * Before setting the synchronizer, clients should save the current
	 * synchronizer so it can be restored later.
	 * 
	 * @see #getContextModelSynchronizer()
	 */
	void setContextModelSynchronizer(Synchronizer synchronizer);

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
	 * model is synchronously (or asynchronously) depends on the current context
	 * model synchronizer.
	 * 
	 * @see #synchronizeContextModelAndWait()
	 */
	void synchronizeContextModel();

	/**
	 * Force the JPA project's context model to synchronize with it resource
	 * model <em>synchronously</em>.
	 * 
	 * @see #synchronizeContextModel()
	 * @see #updateAndWait()
	 */
	void synchronizeContextModelAndWait();

	/**
	 * This is the callback used by the context model synchronizer to perform
	 * the actual "synchronize".
	 */
	IStatus synchronizeContextModel(IProgressMonitor monitor);


	// ********** project "update" **********

	/**
	 * Return the synchronizer that will update the context model whenever
	 * it has any changes. This allows any intra-JPA project dependencies to
	 * be updated.
	 */
	CallbackSynchronizer getUpdateSynchronizer();

	/**
	 * Set the synchronizer that will update the context model whenever
	 * it has any changes. This allows any intra-JPA project dependencies to
	 * be updated.
	 * Before setting the update synchronizer, clients should save the current
	 * synchronizer so it can be restored later.
	 * 
	 * @see #getUpdateSynchronizer()
	 */
	void setUpdateSynchronizer(CallbackSynchronizer synchronizer);

	/**
	 * Force the JPA project to "update" <em>synchronously</em>.
	 * 
	 * @see #synchronizeContextModelAndWait()
	 */
	void updateAndWait();

	/**
	 * This is the callback used by the update synchronizer to perform the
	 * actual "update".
	 */
	IStatus update(IProgressMonitor monitor);


	// ********** utility **********

	/**
	 * Return an {@link IFile} that best represents the given runtime location
	 */
	IFile getPlatformFile(IPath runtimePath);


	// ********** validation **********

	/**
	 * Return JPA project's validation messages.
	 */
	Iterator<IMessage> validationMessages(IReporter reporter);


	// ********** database **********

	/**
	 * Return the data source the JPA project is mapped to.
	 */
	JpaDataSource getDataSource();

	/**
	 * Return the JPA project's connection.
	 * The connection profile is null if the JPA project's connection profile
	 * name does not match the name of a DTP connection profile.
	 */
	ConnectionProfile getConnectionProfile();

	/**
	 * Return the JPA project's default database schema container;
	 * which is either the JPA project's default catalog or the JPA project's
	 * database, depending on how the DTP model is implemented.
	 */
	SchemaContainer getDefaultDbSchemaContainer();

	/**
	 * Return the JPA project's default catalog; which is either the user
	 * override catalog or the database's default catalog.
	 */
	String getDefaultCatalog();

	/**
	 * Return the JPA project's default database catalog.
	 * @see #getDefaultCatalog()
	 */
	Catalog getDefaultDbCatalog();

	/**
	 * Return the JPA project's default schema; which can be one of the
	 * following:<ul>
	 * <li>the user override schema
	 * <li>the default catalog's default schema
	 * <li>the database's default schema (if catalogs are not supported)
	 * </ul>
	 */
	String getDefaultSchema();

	/**
	 * Return the JPA project's default database schema.
	 * @see #getDefaultSchema()
	 * @see #getDefaultDbSchemaContainer()
	 */
	Schema getDefaultDbSchema();


	// ********** user override default catalog **********

	/** 
	 * ID string used when the JPA project's user override default catalog changes.
	 * @see #addPropertyChangeListener(String, org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener)
	 */
	String USER_OVERRIDE_DEFAULT_CATALOG_PROPERTY = "userOverrideDefaultCatalog"; //$NON-NLS-1$

	/**
	 * Return the <em>identifier</em> of the catalog to be used as a default
	 * for the JPA project instead of the one that is associated by default
	 * with the connection profile.
	 * @return The catalog identifier. May be null (implying the connection profile
	 * default catalog should be used).
	 */
	String getUserOverrideDefaultCatalog();

	/**
	 * Set the <em>identifier</em> of the catalog to be used as a default
	 * for the JPA project instead of the one that is associated by default
	 * with the connection profile.
	 * @parameter catalog The catalog identifier. May be null (implying the connection profile
	 * default catalog should be used).
	 */
	void setUserOverrideDefaultCatalog(String catalog);


	// ********** user override default schema **********

	/** 
	 * ID string used when the JPA project's user override default schema changes.
	 * @see #addPropertyChangeListener(String, org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener)
	 */
	String USER_OVERRIDE_DEFAULT_SCHEMA_PROPERTY = "userOverrideDefaultSchema"; //$NON-NLS-1$

	/**
	 * Return the <em>identifier</em> of the schema to be used as a default
	 * for the JPA project instead of the one that is associated by default
	 * with the connection profile.
	 * @return The schema identifier. May be null (implying the connection profile
	 * default schema should be used).
	 */
	String getUserOverrideDefaultSchema();

	/**
	 * Set the <em>identifier</em> of the schema to be used as a default
	 * for the JPA project instead of the one that is associated by default
	 * with the connection profile.
	 * @parameter schema The schema identifier. May be null (implying the connection profile
	 * default schema should be used).
	 */
	void setUserOverrideDefaultSchema(String schema);


	// ********** discover annotated classes **********

	/** 
	 * ID string used when discoversAnnotatedClasses property is changed.
	 * @see #addPropertyChangeListener(String, org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener)
	 */
	String DISCOVERS_ANNOTATED_CLASSES_PROPERTY = "discoversAnnotatedClasses"; //$NON-NLS-1$

	/**
	 * Return whether the JPA project will not generate error messages for any
	 * annotated classes that are not listed in the <code>persistence.xml</code>
	 * file or one of its mapping files. If this flag is set to
	 * <code>false</code>, error messages will be generated for all of the
	 * annotated classes that are not explicitly listed. The JPA project
	 * <em>always</em> "discovers" annotated classes and allows the user to
	 * reference them throughout the model; this flag simply controls whether
	 * the error messages are generated during validation.
	 * <p>
	 * This is a user-specified preference that is probably
	 * only helpful when deploying to a JavaSE environment. The JPA spec
	 * says annotated classes are to be discovered automatically in a JavaEE
	 * environment; while the managed persistence classes must be explicitly
	 * listed in a JavaSE environment: "A list of all named managed persistence
	 * classes must be specified in Java SE environments to insure portability".
	 * This flag allows Dali to behave consistently with the user's JPA
	 * implementation, which may allow "discovery" in a JavaSE environment
	 * (e.g. EclipseLink). This setting can also be used when the user wants
	 * to explicitly list classes, even when the classes are "discovered"
	 * by the JPA implementation.
	 */
	boolean discoversAnnotatedClasses();

	/**
	 * Set whether the JPA project will not generate error messages for any
	 * annotated classes that are not listed in the <code>persistence.xml</code>
	 * file or one of its mapping files.
	 * @see #discoversAnnotatedClasses()
	 */
	void setDiscoversAnnotatedClasses(boolean discoversAnnotatedClasses);


	// ********** modifying shared documents **********

	/**
	 * Set a thread-specific implementation of the {@link CommandExecutor}
	 * interface that will be used to execute a command to modify a shared
	 * document. If necessary, the command executor can be cleared by
	 * setting it to <code>null</code>.
	 * This allows background clients to modify documents that are
	 * already present in the UI. See implementations of {@link CommandExecutor}.
	 */
	void setThreadLocalModifySharedDocumentCommandExecutor(CommandExecutor commandExecutor);

	/**
	 * Return the project-wide implementation of the
	 * {@link CommandExecutor} interface.
	 */
	CommandExecutor getModifySharedDocumentCommandExecutor();


	// ********** construction config **********

	/**
	 * The settings used to construct a JPA project.
	 */
	interface Config
	{
		/**
		 * Return the Eclipse project to be associated with the new JPA project.
		 */
		IProject getProject();

		/**
		 * Return the JPA platform to be associated with the new JPA project.
		 */
		JpaPlatform getJpaPlatform();

		/**
		 * Return the name of the connection profile to be associated
		 * with the new JPA project. (This connection profile wraps a DTP
		 * connection profile.)
		 */
		String getConnectionProfileName();

		/**
		 * Return the catalog to use instead of the connection profile's
		 * default catalog.
		 * May be null.
		 */
		String getUserOverrideDefaultCatalog();

		/**
		 * Return the name of the schema to use instead of the default schema
		 * of the connection profile.
		 * May be null.
		 */
		String getUserOverrideDefaultSchema();

		/**
		 * Return whether the new JPA project is to "discover" annotated
		 * classes.
		 */
		boolean discoverAnnotatedClasses();
	}
}
