/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.common.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.common.core.resource.java.JavaResourceTypeCache;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.core.utility.command.JobCommand;
import org.eclipse.jpt.common.utility.ExceptionHandler;
import org.eclipse.jpt.common.utility.command.ExtendedCommandExecutor;
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * A JPA project is associated with an {@link IProject Eclipse project}
 * (and its corresponding  {@link IJavaProject Java project}).
 * It holds the <em>resource</em> model that corresponds to the 
 * various JPA-related resources (the <code>persistence.xml</code> file, its
 * mapping files [<code>orm.xml</code>], and the Java source files). It also
 * holds the <em>context</em> model that represents
 * the JPA metadata, as derived from spec-defined defaults, Java source code
 * annotations, and XML descriptors.
 * <p>
 * To retrieve the JPA project corresponding to an Eclipse project:
 * <pre>
 * IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("Foo Project");
 * JpaProject jpaProject = (JpaProject) project.getAdapter(JpaProject.class);
 * </pre>
 * This is a non-blocking call; and as a result it will return <code>null</code>
 * if the JPA project is currently under construction. Use a {@link
 * Reference JPA project reference} to retrieve a JPA project in a blocking
 * fashion that will return a JPA project once it has been constructed.
 * <p>
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.0
 * 
 * @see Reference
 * @see org.eclipse.jpt.jpa.core.internal.ProjectAdapterFactory
 */
public interface JpaProject
	extends JpaNode
{
	// ********** JPA facet **********

	/**
	 * The JPA project facet ID.
	 * <p>
	 * Value: {@value}
	 */
	String FACET_ID = "jpt.jpa"; //$NON-NLS-1$

	/**
	 * The JPA project facet.
	 */
	IProjectFacet FACET = ProjectFacetsManager.getProjectFacet(FACET_ID);

	/**
	 * The JPA project facet version string.
	 * <p>
	 * Value: {@value}
	 */
	String FACET_VERSION_STRING = "1.0"; //$NON-NLS-1$

	/**
	 * The JPA project facet version.
	 */
	IProjectFacetVersion FACET_VERSION = FACET.getVersion(FACET_VERSION_STRING);


	// ********** general **********

	/**
	 * Return the JPA project manager.
	 */
	Manager getManager();

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
	Iterable<JpaFile> getJpaFiles();

	/**
	 * Return the size of the JPA project's JPA files.
	 */
	int getJpaFilesSize();

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
	Iterable<JavaResourceCompilationUnit> getExternalJavaResourceCompilationUnits();

	/**
	 * Return the size of the JPA project's external Java resource compilation units.
	 */
	int getExternalJavaResourceCompilationUnitsSize();


	// ********** external Java resource persistent types **********

	/**
	 * Return the JPA project's external Java resource type cache.
	 */
	JavaResourceTypeCache getExternalJavaResourceTypeCache();


	// ********** XML resources **********
	
	/**
	 * Return the XML resource model corresponding to the file with runtime path
	 * <code>META-INF/persistence.xml</code> if that file has the persistence content type
	 * (<code>"org.eclipse.jpt.jpa.core.content.persistence"</code>).
	 * 
	 * @see XmlPersistence#DEFAULT_RUNTIME_PATH
	 * @see XmlPersistence#CONTENT_TYPE
	 */
	JptXmlResource getPersistenceXmlResource();
	
	/**
	 * Return the XML resource model corresponding to the file with the specified
	 * runtime path if that file has the mapping file content type
	 * (<code>org.eclipse.jpt.jpa.core.content.mappingFile</code>)
	 * 
	 * @see org.eclipse.jpt.jpa.core.resource.ResourceMappingFile.Root#CONTENT_TYPE
	 */
	JptXmlResource getMappingFileXmlResource(IPath runtimePath);

	/**
	 * Return the XML resource model corresponding to the file
	 * <code>META-INF/orm.xml</code> if that file has the mapping file content type.
	 * 
	 * @see XmlEntityMappings#DEFAULT_RUNTIME_PATH
	 */
	JptXmlResource getDefaultOrmXmlResource();
	
	
	// ********** Java resources **********

	/**
	 * Return the resource types of the JPA project's annotated Java classes
	 * (ignoring classes in JARs referenced in the <code>persistence.xml</code>).
	 */
	Iterable<JavaResourceAbstractType> getAnnotatedJavaSourceTypes();
	
	/**
	 * Return only the types of those valid <em>mapped</em> (i.e. annotated with
	 * <code>@Entity</code>, <code>@Embeddable</code>, etc.) Java resource
	 * types that are directly part of the JPA project, ignoring
	 * those in JARs referenced in <code>persistence.xml</code>.
	 */
	Iterable<JavaResourceAbstractType> getMappedJavaSourceTypes();

	Iterable<String> getTypeMappingAnnotationNames();

	/**
	 * Return the Java resource type for the fully qualified type name.
	 * Return null if invalid or absent.
	 */
	JavaResourceAbstractType getJavaResourceType(String typeName);

	/**
	 * Return the {@link JavaResourceAbstractType} with the fully qualified type name and astNodeType.
	 * Return null if invalid or absent or if the astNodeType does not match.
	 */
	JavaResourceAbstractType getJavaResourceType(String typeName, JavaResourceAnnotatedElement.AstNodeType astNodeType);

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
	 * Should only be used by [incorrectly written] tests....
	 * [The tests would be more valid if they modified the source code
	 * directly allowing resource/Java change events to <em>synchronize</em>
	 * the context model....]
	 */
	void synchronizeContextModel();


	// ********** utility **********

	/**
	 * Return an {@link IFile} that best represents the given runtime location
	 */
	IFile getPlatformFile(IPath runtimePath);


	// ********** validation **********

	/**
	 * Return JPA project's validation messages.
	 */
	Iterable<IMessage> getValidationMessages(IReporter reporter);


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


	// ********** manager **********

	/**
	 * The JPA project manager provides behavior to be used by all the JPA
	 * projects
	 */
	interface Manager {

		// ********** commands **********

		/**
		 * Execute the specified command, possibly asynchronously, synchronizing
		 * with all the other stuff manipulating the JPA projects
		 * (e.g. resource change events).
		 * If, once the command comes up for execution, the specified JPA project
		 * is no longer present, the command will <em>not</em> be executed by the
		 * JPA project manager.
		 */
		void execute(JobCommand command, String jobName, JpaProject jpaProject);


		// ********** modifying shared documents **********

		/**
		 * Return the project-wide implementation of the
		 * {@link ExtendedCommandExecutor} interface.
		 */
		ExtendedCommandExecutor getModifySharedDocumentCommandExecutor();


		// ********** logging **********

		// TODO remove (use plug-in API)
		/**
		 * Log the specified message.
		 */
		void log(String msg);

		/**
		 * Log the specified exception/error.
		 */
		void log(Throwable throwable);

		/**
		 * Log the specified message and exception/error.
		 */
		void log(String msg, Throwable throwable);

		// TODO remove (use plug-in API)
		/**
		 * Return an exception handler that can be used by the JPA model.
		 */
		ExceptionHandler getExceptionHandler();
	}


	// ********** construction config **********

	/**
	 * The settings used to construct a JPA project.
	 */
	interface Config {
		/**
		 * Return the JPA platform to be associated with the new JPA project.
		 */
		Manager getJpaProjectManager();

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


	// ********** reference **********

	/**
	 * Standard adapter for "synchronously" retrieving a
	 * {@link JpaProject JPA project}
	 * (i.e. if necessary, wait for the JPA project to be constructed;
	 * thus the {@link InterruptedException}):
	 * <pre>
	 * IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("Foo Project");
	 * JpaProject.Reference jpaProjectRef = (JpaProject.Reference) project.getAdapter(JpaProject.Reference.class);
	 * JpaProject jpaProject = jpaProjectRef.getValue();
	 * </pre>
	 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:org.eclipse.core.runtime.adapters</code>.
	 * @see org.eclipse.jpt.jpa.core.internal.ProjectAdapterFactory
	 */
	interface Reference {
		/**
		 * Return the JPA project corresponding to the reference's Eclipse project.
		 * Return <code>null</code> if unable to associate the Eclipse
		 * project with a JPA project. This method can be long-running.
		 */
		JpaProject getValue() throws InterruptedException;

		/**
		 * The JPA settings associated with the reference's Eclipse project
		 * have changed in such a way as to require the associated
		 * JPA project to be completely rebuilt
		 * (e.g. when the user changes a project's JPA platform).
		 * Return the new JPA project.
		 */
		JpaProject rebuild() throws InterruptedException;

		/**
		 * Build the JPA validation messages for the reference's
		 * Eclipse project.
		 */
		Iterable<IMessage> buildValidationMessages(IReporter reporter) throws InterruptedException;
	}
}
