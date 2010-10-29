/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.jaxb.core.context.JaxbRootContextNode;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatform;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.utility.CommandExecutor;
import org.eclipse.jpt.utility.synchronizers.CallbackSynchronizer;
import org.eclipse.jpt.utility.synchronizers.Synchronizer;

/**
 * A JAXB project is associated with an Eclipse project (and its corresponding
 * Java project). It holds the "resource" model that corresponds to the various
 * JPA-related resources (the <code>persistence.xml</code> file, its mapping files
 * [<code>orm.xml</code>],
 * and the Java source files). It also holds the "context" model that represents
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
 * @since 3.0
 */
public interface JaxbProject
	extends JaxbNode
{

	// ********** general **********

	/**
	 * Return the JAXB project's name, which is the same as the associated
	 * Eclipse project's name.
	 */
	String getName();

	/**
	 * Return the Eclipse project associated with the JAXB project.
	 */
	IProject getProject();

	/**
	 * Return the Java project associated with the JAXB project.
	 */
	IJavaProject getJavaProject();

	/**
	 * Return the vendor-specific JAXB platform that builds the JAXB project
	 * and its contents.
	 */
	JaxbPlatform getJaxbPlatform();

	/**
	 * Return the root of the JAXB project's context model.
	 */
	JaxbRootContextNode getRootContextNode();

	/**
	 * The JAXB project has been removed from the JJAXBPA model. Clean up any
	 * hooks to external resources etc.
	 */
	void dispose();


	// ********** JAXB files **********

	/** 
	 * ID string used when the JAXB project's collection of JAXB files changes.
	 * @see #addCollectionChangeListener(String, org.eclipse.jpt.utility.model.listener.CollectionChangeListener)
	 */
	String JAXB_FILES_COLLECTION = "jaxbFiles"; //$NON-NLS-1$

	/**
	 * Return the JAXB project's JAXB files.
	 */
	Iterable<JaxbFile> getJaxbFiles();

	/**
	 * Return the size of the JAXB project's JAXB files.
	 */
	int getJaxbFilesSize();

	/**
	 * Return the JAXB file corresponding to the specified file.
	 * Return null if there is no JAXB file associated with the specified file.
	 */
	JaxbFile getJaxbFile(IFile file);


//	// ********** external Java resource compilation units **********
//
//	/** 
//	 * ID string used when the JPA project's collection of external Java
//	 * resource compilation units changes.
//	 * @see #addCollectionChangeListener(String, org.eclipse.jpt.utility.model.listener.CollectionChangeListener)
//	 */
//	String EXTERNAL_JAVA_RESOURCE_COMPILATION_UNITS_COLLECTION = "externalJavaResourceCompilationUnits"; //$NON-NLS-1$
//
//	/**
//	 * Return the JPA project's external Java resource compilation units.
//	 */
//	Iterator<JavaResourceCompilationUnit> externalJavaResourceCompilationUnits();
//
//	/**
//	 * Return the size of the JPA project's external Java resource compilation units.
//	 */
//	int externalJavaResourceCompilationUnitsSize();
//
//
//	// ********** external Java resource persistent types **********
//
//	/**
//	 * Return the JPA project's external Java resource persistent type cache.
//	 */
//	JavaResourcePersistentTypeCache getExternalJavaResourcePersistentTypeCache();
//
	
	// ********** Java resources **********

	/**
	 * Return the names of the JAXB project's annotated Java classes
	 */
	Iterable<String> getAnnotatedJavaSourceClassNames();

	/**
	 * Return the Java resource persistent type for the specified type.
	 * Return null if invalid or absent.
	 */
	JavaResourceType getJavaResourceType(String typeName);

	/**
	 * Return the Java resource package for the specified package.
	 * Return null if invalid or absent.
	 */
	JavaResourcePackage getJavaResourcePackage(String packageName);

	/**
	 * Return the Java resource packages for the project.
	 * Return null if invalid or absent. These correspond to package-info.java files
	 */
	Iterable<JavaResourcePackage> getJavaResourcePackages();

	/**
	 * Return the java resource packages that are annotated with
	 * 1 or more valid JAXB package annotations
	 */
	Iterable<JavaResourcePackage> getAnnotatedJavaResourcePackages();

//	/**
//	 * Return the Java resource package fragement root for the specified JAR.
//	 * Return null if absent.
//	 */
//	JavaResourcePackageFragmentRoot getJavaResourcePackageFragmentRoot(String jarFileName);


	// ********** model synchronization **********

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
	 * The JAXB project's resource model has changed; synchronize the JPA
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
	 * Force the JAXB project's context model to synchronize with it resource
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
	 * it has any changes. This allows any intra-JAXB project dependencies to
	 * be updated.
	 */
	CallbackSynchronizer getUpdateSynchronizer();

	/**
	 * Set the synchronizer that will update the context model whenever
	 * it has any changes. This allows any intra-JAXB project dependencies to
	 * be updated.
	 * Before setting the update synchronizer, clients should save the current
	 * synchronizer so it can be restored later.
	 * 
	 * @see #getUpdateSynchronizer()
	 */
	void setUpdateSynchronizer(CallbackSynchronizer synchronizer);

	/**
	 * Force the JAXB project to "update" <em>synchronously</em>.
	 * 
	 * @see #synchronizeContextModelAndWait()
	 */
	void updateAndWait();

	/**
	 * This is the callback used by the update synchronizer to perform the
	 * actual "update".
	 */
	IStatus update(IProgressMonitor monitor);




//	// ********** utility **********
//
//	/**
//	 * Return an {@link IFile} that best represents the given runtime location
//	 */
//	IFile getPlatformFile(IPath runtimePath);
//
//
//	// ********** validation **********
//
//	/**
//	 * Return JPA project's validation messages.
//	 */
//	Iterator<IMessage> validationMessages(IReporter reporter);



	// ********** modifying shared documents **********

	/**
	 * Set a thread-specific implementation of the {@link CommandExecutor}
	 * interface that will be used to execute a command to modify a shared
	 * document. If necessary, the command executor can be cleared by
	 * setting it to null.
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
	 * The settings used to construct a JAXB project.
	 */
	interface Config {

		/**
		 * Return the Eclipse project to be associated with the new JAXB project.
		 */
		IProject getProject();

		/**
		 * Return the JAXB platform to be associated with the new JAXB project.
		 */
		JaxbPlatform getJaxbPlatform();

	}

}
