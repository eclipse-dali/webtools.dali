/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

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
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.JpaResourceModel;
import org.eclipse.jpt.core.JpaResourceModelListener;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.utility.PlatformTools;
import org.eclipse.jpt.core.resource.ResourceLocator;
import org.eclipse.jpt.core.resource.java.JavaResourceCompilationUnit;
import org.eclipse.jpt.jaxb.core.JaxbFile;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatform;
import org.eclipse.jpt.utility.CommandExecutor;
import org.eclipse.jpt.utility.internal.BitTools;
import org.eclipse.jpt.utility.internal.ThreadLocalCommandExecutor;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;

/**
 * JPA project. Holds all the JPA stuff.
 * 
 * The JPA platform provides the hooks for vendor-specific stuff.
 * 
 * The JPA files are the "resource" model (i.e. objects that correspond directly
 * to Eclipse resources; e.g. Java source code files, XML files, JAR files).
 * 
 * The root context node is the "context"model (i.e. objects that attempt to
 * model the JPA spec, using the "resource" model as an adapter to the Eclipse
 * resources).
 * 
 * The data source is an adapter to the DTP meta-data model.
 */
public abstract class AbstractJaxbProject
	extends AbstractJaxbNode
	implements JaxbProject
{
	/**
	 * The Eclipse project corresponding to the JAXB project.
	 */
	protected final IProject project;

	/**
	 * The vendor-specific JAXB platform that builds the JAXB project
	 * and all its contents.
	 */
	protected final JaxbPlatform jaxbPlatform;

	/**
	 * The JPA files associated with the JPA project:
	 *     persistence.xml
	 *     orm.xml
	 *     java
	 */
	protected final Vector<JaxbFile> jaxbFiles = new Vector<JaxbFile>();

//	/**
//	 * The "external" Java resource compilation units (source). Populated upon demand.
//	 */
//	protected final Vector<JavaResourceCompilationUnit> externalJavaResourceCompilationUnits = new Vector<JavaResourceCompilationUnit>();
//
//	/**
//	 * The "external" Java resource persistent types (binary). Populated upon demand.
//	 */
//	protected final JavaResourcePersistentTypeCache externalJavaResourcePersistentTypeCache;

	/**
	 * Resource models notify this listener when they change. A project update
	 * should occur any time a resource model changes.
	 */
	protected final JpaResourceModelListener resourceModelListener;

//	/**
//	 * The root of the model representing the collated resources associated with 
//	 * the JPA project.
//	 */
//	protected final JpaRootContextNode rootContextNode;

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
	 * Support for modifying documents shared with the UI.
	 */
	protected final ThreadLocalCommandExecutor modifySharedDocumentCommandExecutor;


	// ********** constructor/initialization **********

	protected AbstractJaxbProject(JaxbProject.Config config) {
		super(null);  // JPA project is the root of the containment tree
		if ((config.getProject() == null) || (config.getJaxbPlatform() == null)) {
			throw new NullPointerException();
		}
		this.project = config.getProject();
		this.jaxbPlatform = config.getJaxbPlatform();

		this.modifySharedDocumentCommandExecutor = this.buildModifySharedDocumentCommandExecutor();

		this.resourceModelListener = this.buildResourceModelListener();
		// build the JPA files corresponding to the Eclipse project's files
		InitialResourceProxyVisitor visitor = this.buildInitialResourceProxyVisitor();
		visitor.visitProject(this.project);
//
//		this.externalJavaResourcePersistentTypeCache = this.buildExternalJavaResourcePersistentTypeCache();
//
//		this.rootContextNode = this.buildRootContextNode();

		// "update" the project before returning
		this.setUpdater_(new SynchronousJaxbProjectUpdater(this));
//
//		// start listening to this cache once the context model has been built
//		// and all the external types are faulted in
//		this.externalJavaResourcePersistentTypeCache.addResourceModelListener(this.resourceModelListener);
	}

	@Override
	protected boolean requiresParent() {
		return false;
	}
	
	@Override
	public IResource getResource() {
		return this.project;
	}

	protected ThreadLocalCommandExecutor buildModifySharedDocumentCommandExecutor() {
		return new ThreadLocalCommandExecutor();
	}

	protected JpaResourceModelListener buildResourceModelListener() {
		return new DefaultResourceModelListener();
	}

	protected InitialResourceProxyVisitor buildInitialResourceProxyVisitor() {
		return new InitialResourceProxyVisitor();
	}
//
//	protected JavaResourcePersistentTypeCache buildExternalJavaResourcePersistentTypeCache() {
//		return new BinaryPersistentTypeCache(this.jpaPlatform.getAnnotationProvider());
//	}
//
//	protected JpaRootContextNode buildRootContextNode() {
//		return this.getJpaFactory().buildRootContextNode(this);
//	}

	// ***** inner class
	protected class InitialResourceProxyVisitor implements IResourceProxyVisitor {
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
//					AbstractJaxbProject.this.addJpaFile_((IFile) resource.requestResource());
					return false;  // no children
				default :
					return false;  // no children
			}
		}
	}


//	// ********** miscellaneous **********
//
//	/**
//	 * Ignore changes to this collection. Adds can be ignored since they are triggered
//	 * by requests that will, themselves, trigger updates (typically during the
//	 * update of an object that calls a setter with the newly-created resource
//	 * type). Deletes will be accompanied by manual updates.
//	 */
//	@Override
//	protected void addNonUpdateAspectNamesTo(Set<String> nonUpdateAspectNames) {
//		super.addNonUpdateAspectNamesTo(nonUpdateAspectNames);
//		nonUpdateAspectNames.add(EXTERNAL_JAVA_RESOURCE_COMPILATION_UNITS_COLLECTION);
//	}


	// ********** general queries **********

	@Override
	public JaxbProject getJaxbProject() {
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
	public JaxbPlatform getJaxbPlatform() {
		return this.jaxbPlatform;
	}

	@SuppressWarnings("unchecked")
	protected Iterable<JavaResourceCompilationUnit> getCombinedJavaResourceCompilationUnits() {
		return this.getInternalJavaResourceCompilationUnits();
//		return new CompositeIterable<JavaResourceCompilationUnit>(
//					this.getInternalJavaResourceCompilationUnits(),
//					this.getExternalJavaResourceCompilationUnits()
//				);
	}
	
	
	// ********** JAXB files **********

	public Iterable<JaxbFile> getJaxbFiles() {
		return new LiveCloneIterable<JaxbFile>(this.jaxbFiles);  // read-only
	}

	public int getJaxbFilesSize() {
		return this.jaxbFiles.size();
	}

	protected Iterable<JaxbFile> getJaxbFiles(final IContentType contentType) {
		return new FilteringIterable<JaxbFile>(this.getJaxbFiles()) {
			@Override
			protected boolean accept(JaxbFile jaxbFile) {
				return jaxbFile.getContentType().isKindOf(contentType);
			}
		};
	}

	@Override
	public JaxbFile getJaxbFile(IFile file) {
		for (JaxbFile jaxbFile : this.getJaxbFiles()) {
			if (jaxbFile.getFile().equals(file)) {
				return jaxbFile;
			}
		}
		return null;
	}

	/**
	 * Add a JAXB file for the specified file, if appropriate.
	 * Return true if a JAXB File was created and added, false otherwise
	 */
	protected boolean addJaxbFile(IFile file) {
		JaxbFile jaxbFile = this.addJaxbFile_(file);
		if (jaxbFile != null) {
			this.fireItemAdded(JAXB_FILES_COLLECTION, jaxbFile);
			return true;
		}
		return false;
	}
	
	/**
	 * Add a JAXB file for the specified file, if appropriate, without firing
	 * an event; useful during construction.
	 * Return the new JAXB file, null if it was not created.
	 */
	protected JaxbFile addJaxbFile_(IFile file) {
		if (isJavaFile(file)) {
			if (! getJavaProject().isOnClasspath(file)) {
				// a java (.jar or .java) file must be on the Java classpath
				return null;
			}
		}
		else if (! isInAcceptableResourceLocation(file)) {
			return null;  
		}

		JaxbFile jaxbFile = null;
		try {
			jaxbFile = this.getJaxbPlatform().buildJaxbFile(this, file);
		}
		catch (Exception e) {
			//log any developer exceptions and don't build a JaxbFile rather
			//than completely failing to build the JaxbProject
			JptJaxbCorePlugin.log(e);
		}
		if (jaxbFile == null) {
			return null;
		}
		jaxbFile.getResourceModel().addResourceModelListener(this.resourceModelListener);
		this.jaxbFiles.add(jaxbFile);
		return jaxbFile;
	}
	
	/* file is .java or .jar */
	protected boolean isJavaFile(IFile file) {
		IContentType contentType = PlatformTools.getContentType(file);
		return contentType != null 
				&& (contentType.isKindOf(JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE)
					|| contentType.isKindOf(JptCorePlugin.JAR_CONTENT_TYPE));
	}
	
	/* (non-java resource) file is in acceptable resource location */
	protected boolean isInAcceptableResourceLocation(IFile file) {
		ResourceLocator resourceLocator = JptCorePlugin.getResourceLocator(getProject());
		return resourceLocator.acceptResourceLocation(getProject(), file.getParent());
	}
	
	/**
	 * Remove the JAXB file corresponding to the specified IFile, if it exists.
	 * Return true if a JAXB File was removed, false otherwise
	 */
	protected boolean removeJaxbFile(IFile file) {
		JaxbFile jaxbFile = this.getJaxbFile(file);
		if (jaxbFile != null) {  // a JpaFile is not added for every IFile
			this.removeJaxbFile(jaxbFile);
			return true;
		}
		return false;
	}

	/**
	 * Stop listening to the JAXB file and remove it.
	 */
	protected void removeJaxbFile(JaxbFile jaxbFile) {
		jaxbFile.getResourceModel().removeResourceModelListener(this.resourceModelListener);
		if ( ! this.removeItemFromCollection(jaxbFile, this.jaxbFiles, JAXB_FILES_COLLECTION)) {
			throw new IllegalArgumentException(jaxbFile.toString());
		}
	}


//	// ********** external Java resource persistent types (source or binary) **********
//
//	protected JavaResourcePersistentType buildPersistableExternalJavaResourcePersistentType(String typeName) {
//		IType jdtType = this.findType(typeName);
//		return (jdtType == null) ? null : this.buildPersistableExternalJavaResourcePersistentType(jdtType);
//	}
//
//	protected IType findType(String typeName) {
//		try {
//			return this.getJavaProject().findType(typeName);
//		} catch (JavaModelException ex) {
//			return null;  // ignore exception?
//		}
//	}
//
//	protected JavaResourcePersistentType buildPersistableExternalJavaResourcePersistentType(IType jdtType) {
//		JavaResourcePersistentType jrpt = this.buildExternalJavaResourcePersistentType(jdtType);
//		return ((jrpt != null) && jrpt.isPersistable()) ? jrpt : null;
//	}
//
//	protected JavaResourcePersistentType buildExternalJavaResourcePersistentType(IType jdtType) {
//		return jdtType.isBinary() ?
//				this.buildBinaryExternalJavaResourcePersistentType(jdtType) :
//				this.buildSourceExternalJavaResourcePersistentType(jdtType);
//	}
//
//	protected JavaResourcePersistentType buildBinaryExternalJavaResourcePersistentType(IType jdtType) {
//		return this.externalJavaResourcePersistentTypeCache.addPersistentType(jdtType);
//	}
//
//	protected JavaResourcePersistentType buildSourceExternalJavaResourcePersistentType(IType jdtType) {
//		JavaResourceCompilationUnit jrcu = this.getExternalJavaResourceCompilationUnit(jdtType.getCompilationUnit());
//		String jdtTypeName = jdtType.getFullyQualifiedName('.');  // JDT member type names use '$'
//		for (Iterator<JavaResourcePersistentType> stream = jrcu.persistentTypes(); stream.hasNext(); ) {
//			JavaResourcePersistentType jrpt = stream.next();
//			if (jrpt.getQualifiedName().equals(jdtTypeName)) {
//				return jrpt;
//			}
//		}
//		// we can get here if the project JRE is removed;
//		// see SourceCompilationUnit#getPrimaryType(CompilationUnit)
//		// bug 225332
//		return null;
//	}
//
//
//	// ********** external Java resource persistent types (binary) **********
//
//	public JavaResourcePersistentTypeCache getExternalJavaResourcePersistentTypeCache() {
//		return this.externalJavaResourcePersistentTypeCache;
//	}
//
//
//	// ********** external Java resource compilation units (source) **********
//
//	public Iterator<JavaResourceCompilationUnit> externalJavaResourceCompilationUnits() {
//		return this.getExternalJavaResourceCompilationUnits().iterator();
//	}
//
//	protected Iterable<JavaResourceCompilationUnit> getExternalJavaResourceCompilationUnits() {
//		return new LiveCloneIterable<JavaResourceCompilationUnit>(this.externalJavaResourceCompilationUnits);  // read-only
//	}
//
//	public int externalJavaResourceCompilationUnitsSize() {
//		return this.externalJavaResourceCompilationUnits.size();
//	}
//
//	/**
//	 * Return the resource model compilation unit corresponding to the specified
//	 * JDT compilation unit. If it does not exist, build it.
//	 */
//	protected JavaResourceCompilationUnit getExternalJavaResourceCompilationUnit(ICompilationUnit jdtCompilationUnit) {
//		for (JavaResourceCompilationUnit jrcu : this.getExternalJavaResourceCompilationUnits()) {
//			if (jrcu.getCompilationUnit().equals(jdtCompilationUnit)) {
//				// we will get here if the JRCU could not build its persistent type...
//				return jrcu;
//			}
//		}
//		return this.addExternalJavaResourceCompilationUnit(jdtCompilationUnit);
//	}
//
//	/**
//	 * Add an external Java resource compilation unit.
//	 */
//	protected JavaResourceCompilationUnit addExternalJavaResourceCompilationUnit(ICompilationUnit jdtCompilationUnit) {
//		JavaResourceCompilationUnit jrcu = this.buildJavaResourceCompilationUnit(jdtCompilationUnit);
//		this.addItemToCollection(jrcu, this.externalJavaResourceCompilationUnits, EXTERNAL_JAVA_RESOURCE_COMPILATION_UNITS_COLLECTION);
//		jrcu.addResourceModelListener(this.resourceModelListener);
//		return jrcu;
//	}
//
//	protected JavaResourceCompilationUnit buildJavaResourceCompilationUnit(ICompilationUnit jdtCompilationUnit) {
//		return new SourceTypeCompilationUnit(
//					jdtCompilationUnit,
//					this.jpaPlatform.getAnnotationProvider(),
//					this.jpaPlatform.getAnnotationEditFormatter(),
//					this.modifySharedDocumentCommandExecutor
//				);
//	}
//
//	protected boolean removeExternalJavaResourceCompilationUnit(IFile file) {
//		for (JavaResourceCompilationUnit jrcu : this.getExternalJavaResourceCompilationUnits()) {
//			if (jrcu.getFile().equals(file)) {
//				this.removeExternalJavaResourceCompilationUnit(jrcu);
//				return true;
//			}
//		}
//		return false;
//	}
//
//	protected void removeExternalJavaResourceCompilationUnit(JavaResourceCompilationUnit jrcu) {
//		jrcu.removeResourceModelListener(this.resourceModelListener);
//		this.removeItemFromCollection(jrcu, this.externalJavaResourceCompilationUnits, EXTERNAL_JAVA_RESOURCE_COMPILATION_UNITS_COLLECTION);
//	}
//
//
//	// ********** context model **********
//
//	public JpaRootContextNode getRootContextNode() {
//		return this.rootContextNode;
//	}
//
//
//	// ********** utility **********
//
//	public IFile getPlatformFile(IPath runtimePath) {
//		return JptCorePlugin.getPlatformFile(this.project, runtimePath);
//	}


//
//	/**
//	 * If the specified file exists, is significant to the JPA project, and its
//	 * content is a "kind of" the specified content type, return the JPA
//	 * resource model corresponding to the file; otherwise, return null.
//	 */
//	protected JpaResourceModel getResourceModel(IPath runtimePath, IContentType contentType) {
//		IFile file = this.getPlatformFile(runtimePath);
//		return file.exists() ? this.getResourceModel(file, contentType) :  null;
//	}
//
//	/**
//	 * If the specified file is significant to the JPA project and its content
//	 * is a "kind of" the specified content type, return the JPA resource model
//	 * corresponding to the file; otherwise, return null.
//	 */
//	protected JpaResourceModel getResourceModel(IFile file, IContentType contentType) {
//		JpaFile jpaFile = this.getJpaFile(file);
//		return (jpaFile == null) ? null : jpaFile.getResourceModel(contentType);
//	}
//
//
//	// ********** annotated Java source classes **********
//	
//	public Iterator<String> annotatedJavaSourceClassNames() {
//		return this.getAnnotatedJavaSourceClassNames().iterator();
//	}
//	
//	protected Iterable<String> getAnnotatedJavaSourceClassNames() {
//		return new TransformationIterable<JavaResourcePersistentType, String>(this.getInternalAnnotatedSourceJavaResourcePersistentTypes()) {
//			@Override
//			protected String transform(JavaResourcePersistentType jrpType) {
//				return jrpType.getQualifiedName();
//			}
//		};
//	}
//	
//	/**
//	 * return only those valid annotated Java resource persistent types that are part of the 
//	 * JPA project, ignoring those in JARs referenced in persistence.xml
//	 * @see org.eclipse.jpt.core.internal.utility.jdt.JPTTools#typeIsPersistable(org.eclipse.jpt.core.internal.utility.jdt.JPTTools.TypeAdapter)
//	 */
//	protected Iterable<JavaResourcePersistentType> getInternalAnnotatedSourceJavaResourcePersistentTypes() {
//		return new FilteringIterable<JavaResourcePersistentType>(this.getInternalSourceJavaResourcePersistentTypes()) {
//			@Override
//			protected boolean accept(JavaResourcePersistentType jrpType) {
//				return jrpType.isPersistable() && jrpType.isAnnotated();  // i.e. the type is valid and has a valid type annotation
//			}
//		};
//	}
//
//	public Iterable<String> getMappedJavaSourceClassNames() {
//		return new TransformationIterable<JavaResourcePersistentType, String>(this.getInternalMappedSourceJavaResourcePersistentTypes()) {
//			@Override
//			protected String transform(JavaResourcePersistentType jrpType) {
//				return jrpType.getQualifiedName();
//			}
//		};
//	}
//
//	/**
//	 * return only those valid "mapped" (i.e. annotated with @Entity, @Embeddable, etc.) Java 
//	 * resource persistent types that are part of the JPA project, ignoring those in JARs 
//	 * referenced in persistence.xml
//	 */
//	protected Iterable<JavaResourcePersistentType> getInternalMappedSourceJavaResourcePersistentTypes() {
//		return new FilteringIterable<JavaResourcePersistentType>(this.getInternalAnnotatedSourceJavaResourcePersistentTypes()) {
//			@Override
//			protected boolean accept(JavaResourcePersistentType jrpType) {
//				return jrpType.isMapped();  // i.e. the type is already persistable and annotated
//			}
//		};
//	}
//
//	/**
//	 * return only those Java resource persistent types that are
//	 * part of the JPA project, ignoring those in JARs referenced in persistence.xml
//	 */
//	protected Iterable<JavaResourcePersistentType2_0> getInternalSourceJavaResourcePersistentTypes2_0() {
//		return new SubIterableWrapper<JavaResourcePersistentType, JavaResourcePersistentType2_0>(this.getInternalSourceJavaResourcePersistentTypes());
//	}
//
//	/**
//	 * return only those Java resource persistent types that are
//	 * part of the JPA project, ignoring those in JARs referenced in persistence.xml
//	 */
//	protected Iterable<JavaResourcePersistentType> getInternalSourceJavaResourcePersistentTypes() {
//		return new CompositeIterable<JavaResourcePersistentType>(this.getInternalSourceJavaResourcePersistentTypeSets());
//	}
//
//	/**
//	 * return only those Java resource persistent types that are
//	 * part of the JPA project, ignoring those in JARs referenced in persistence.xml
//	 */
//	protected Iterable<Iterable<JavaResourcePersistentType>> getInternalSourceJavaResourcePersistentTypeSets() {
//		return new TransformationIterable<JavaResourceCompilationUnit, Iterable<JavaResourcePersistentType>>(this.getInternalJavaResourceCompilationUnits()) {
//			@Override
//			protected Iterable<JavaResourcePersistentType> transform(final JavaResourceCompilationUnit compilationUnit) {
//				return new Iterable<JavaResourcePersistentType>() {
//					public Iterator<JavaResourcePersistentType> iterator() {
//						return compilationUnit.persistentTypes();  // *all* the types in the compilation unit
//					}
//				};
//			}
//		};
//	}

	protected Iterable<JavaResourceCompilationUnit> getInternalJavaResourceCompilationUnits() {
		return new TransformationIterable<JaxbFile, JavaResourceCompilationUnit>(this.getJavaSourceJaxbFiles()) {
			@Override
			protected JavaResourceCompilationUnit transform(JaxbFile jaxbFile) {
				return (JavaResourceCompilationUnit) jaxbFile.getResourceModel();
			}
		};
	}

	/**
	 * return JAXB files with Java source "content"
	 */
	protected Iterable<JaxbFile> getJavaSourceJaxbFiles() {
		return this.getJaxbFiles(JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE);
	}


//	// ********** Java resource persistent type look-up **********
//
//	public JavaResourcePersistentType getJavaResourcePersistentType(String typeName) {
//		for (JavaResourcePersistentType jrpType : this.getPersistableJavaResourcePersistentTypes()) {
//			if (jrpType.getQualifiedName().equals(typeName)) {
//				return jrpType;
//			}
//		}
//		// if we don't have a type already, try to build new one from the project classpath
//		return this.buildPersistableExternalJavaResourcePersistentType(typeName);
//	}
//
//	/**
//	 * return *all* the "persistable" Java resource persistent types, including those in JARs referenced in
//	 * persistence.xml
//	 * @see org.eclipse.jpt.core.internal.utility.jdt.JPTTools#typeIsPersistable(org.eclipse.jpt.core.internal.utility.jdt.JPTTools.TypeAdapter)
//	 */
//	protected Iterable<JavaResourcePersistentType> getPersistableJavaResourcePersistentTypes() {
//		return new FilteringIterable<JavaResourcePersistentType>(this.getJavaResourcePersistentTypes()) {
//			@Override
//			protected boolean accept(JavaResourcePersistentType jrpType) {
//				return jrpType.isPersistable();
//			}
//		};
//	}
//
//	/**
//	 * return *all* the Java resource persistent types, including those in JARs referenced in
//	 * persistence.xml
//	 */
//	protected Iterable<JavaResourcePersistentType> getJavaResourcePersistentTypes() {
//		return new CompositeIterable<JavaResourcePersistentType>(this.getJavaResourcePersistentTypeSets());
//	}
//
//	/**
//	 * return *all* the Java resource persistent types, including those in JARs referenced in
//	 * persistence.xml
//	 */
//	protected Iterable<Iterable<JavaResourcePersistentType>> getJavaResourcePersistentTypeSets() {
//		return new TransformationIterable<JavaResourceNode.Root, Iterable<JavaResourcePersistentType>>(this.getJavaResourceNodeRoots()) {
//			@Override
//			protected Iterable<JavaResourcePersistentType> transform(final JavaResourceNode.Root root) {
//				return new Iterable<JavaResourcePersistentType>() {
//					public Iterator<JavaResourcePersistentType> iterator() {
//						return root.persistentTypes();  // *all* the types held by the root
//					}
//				};
//			}
//		};
//	}
//
//	@SuppressWarnings("unchecked")
//	protected Iterable<JavaResourceNode.Root> getJavaResourceNodeRoots() {
//		return new CompositeIterable<JavaResourceNode.Root>(
//					this.getInternalJavaResourceCompilationUnits(),
//					this.getInternalJavaResourcePackageFragmentRoots(),
//					this.getExternalJavaResourceCompilationUnits(),
//					Collections.singleton(this.externalJavaResourcePersistentTypeCache)
//				);
//	}
//
//
//	// ********** Java resource persistent package look-up **********
//
//	public JavaResourcePackage getJavaResourcePackage(String packName) {
//		for (JavaResourcePackage jrpp : this.getJavaResourcePackages()) {
//			if (jrpp.getName().equals(packName)) {
//				return jrpp;
//			}
//		}
//		return null;
//	}
//
//	public Iterable<JavaResourcePackage> getJavaResourcePackages(){
//		return new FilteringIterable<JavaResourcePackage>( 
//				new TransformationIterable<JpaFile, JavaResourcePackage>(this.getPackageInfoSourceJpaFiles()) {
//				@Override
//				protected JavaResourcePackage transform(JpaFile jpaFile) {
//					return ((JavaResourcePackageInfoCompilationUnit) jpaFile.getResourceModel()).getPackage();
//				}
//			}) 
//			{
//			@Override
//			protected boolean accept(JavaResourcePackage packageInfo) {
//				return packageInfo != null;
//			}
//		};
//	}
//
//	/**
//	 * return JPA files with package-info source "content"
//	 */
//	protected Iterable<JpaFile> getPackageInfoSourceJpaFiles() {
//		return this.getJpaFiles(JptCorePlugin.JAVA_SOURCE_PACKAGE_INFO_CONTENT_TYPE);
//	}
//
//
//	// ********** JARs **********
//
//	// TODO
//	public JavaResourcePackageFragmentRoot getJavaResourcePackageFragmentRoot(String jarFileName) {
////		return this.getJarResourcePackageFragmentRoot(this.convertToPlatformFile(jarFileName));
//		return this.getJavaResourcePackageFragmentRoot(this.getProject().getFile(jarFileName));
//	}
//
//	protected JavaResourcePackageFragmentRoot getJavaResourcePackageFragmentRoot(IFile jarFile) {
//		for (JavaResourcePackageFragmentRoot pfr : this.getInternalJavaResourcePackageFragmentRoots()) {
//			if (pfr.getFile().equals(jarFile)) {
//				return pfr;
//			}
//		}
//		return null;
//	}
//
//	protected Iterable<JavaResourcePackageFragmentRoot> getInternalJavaResourcePackageFragmentRoots() {
//		return new TransformationIterable<JpaFile, JavaResourcePackageFragmentRoot>(this.getJarJpaFiles()) {
//			@Override
//			protected JavaResourcePackageFragmentRoot transform(JpaFile jpaFile) {
//				return (JavaResourcePackageFragmentRoot) jpaFile.getResourceModel();
//			}
//		};
//	}
//
//	/**
//	 * return JPA files with JAR "content"
//	 */
//	protected Iterable<JpaFile> getJarJpaFiles() {
//		return this.getJpaFiles(JptCorePlugin.JAR_CONTENT_TYPE);
//	}



//
//	// ********** Java source folder names **********
//
//	public Iterable<String> getJavaSourceFolderNames() {
//		try {
//			return this.getJavaSourceFolderNames_();
//		} catch (JavaModelException ex) {
//			JptCorePlugin.log(ex);
//			return EmptyIterable.instance();
//		}
//	}
//
//	protected Iterable<String> getJavaSourceFolderNames_() throws JavaModelException {
//		return new TransformationIterable<IPackageFragmentRoot, String>(this.getJavaSourceFolders()) {
//			@Override
//			protected String transform(IPackageFragmentRoot pfr) {
//				try {
//					return this.transform_(pfr);
//				} catch (JavaModelException ex) {
//					return "Error: " + pfr.getPath(); //$NON-NLS-1$
//				}
//			}
//			private String transform_(IPackageFragmentRoot pfr) throws JavaModelException {
//				return pfr.getUnderlyingResource().getProjectRelativePath().toString();
//			}
//		};
//	}
//
//	protected Iterable<IPackageFragmentRoot> getJavaSourceFolders() throws JavaModelException {
//		return new FilteringIterable<IPackageFragmentRoot>(
//				this.getPackageFragmentRoots(),
//				SOURCE_PACKAGE_FRAGMENT_ROOT_FILTER
//			);
//	}
//
//	protected static final Filter<IPackageFragmentRoot> SOURCE_PACKAGE_FRAGMENT_ROOT_FILTER =
//		new Filter<IPackageFragmentRoot>() {
//			public boolean accept(IPackageFragmentRoot pfr) {
//				try {
//					return this.accept_(pfr);
//				} catch (JavaModelException ex) {
//					return false;
//				}
//			}
//			private boolean accept_(IPackageFragmentRoot pfr) throws JavaModelException {
//				return pfr.exists() && (pfr.getKind() == IPackageFragmentRoot.K_SOURCE);
//			}
//		};
//
//	protected Iterable<IPackageFragmentRoot> getPackageFragmentRoots() throws JavaModelException {
//		return new ArrayIterable<IPackageFragmentRoot>(this.getJavaProject().getPackageFragmentRoots());
//	}


	// ********** Java events **********

	// TODO handle changes to external projects
	public void javaElementChanged(ElementChangedEvent event) {
		this.processJavaDelta(event.getDelta());
	}

	/**
	 * We recurse back here from {@link #processJavaDeltaChildren(IJavaElementDelta)}.
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
	protected boolean deltaFlagIsSet(IJavaElementDelta delta, int flag) {
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
		if (this.classpathHasChanged(delta)) {
			this.rebuild((IJavaProject) delta.getElement());
		}
	}

	/**
	 * The specified Java project's classpath changed. Rebuild the JPA project
	 * as appropriate.
	 */
	protected void rebuild(IJavaProject javaProject) {
//		// if the classpath has changed, we need to update everything since
//		// class references could now be resolved (or not) etc.
//		if (javaProject.equals(this.getJavaProject())) {
//			this.removeDeadJpaFiles();
//			this.update(this.getInternalJavaResourceCompilationUnits());
//		} else {
//			// TODO see if changed project is on our classpath?
//			this.update(this.getExternalJavaResourceCompilationUnits());
//		}
	}
//
//	/**
//	 * Loop through all our JPA files, remove any that are no longer on the
//	 * classpath.
//	 */
//	protected void removeDeadJpaFiles() {
//		for (JpaFile jpaFile : this.getJpaFiles()) {
//			if (this.jpaFileIsDead(jpaFile)) {
//				this.removeJpaFile(jpaFile);
//			}
//		}
//	}
//
//	protected boolean jpaFileIsDead(JpaFile jpaFile) {
//		return ! this.jpaFileIsAlive(jpaFile);
//	}
//
//	/**
//	 * Sometimes (e.g. during tests), when a project has been deleted, we get a
//	 * Java change event that indicates the Java project is CHANGED (as
//	 * opposed to REMOVED, which is what typically happens). The event's delta
//	 * indicates that everything in the Java project has been deleted and the
//	 * classpath has changed. All entries in the classpath have been removed;
//	 * but single entry for the Java project's root folder has been added. (!)
//	 * This means any file in the project is on the Java project's classpath.
//	 * This classpath change is what triggers us to rebuild the JPA project; so
//	 * we put an extra check here to make sure the JPA file's resource file is
//	 * still present.
//	 * <p>
//	 * This would not be a problem if Dali received the resource change event
//	 * <em>before</em> JDT and simply removed the JPA project; but JDT receives
//	 * the resource change event first and converts it into the problematic
//	 * Java change event.... 
//	 */
//	protected boolean jpaFileIsAlive(JpaFile jpaFile) {
//		IFile file = jpaFile.getFile();
//		return this.getJavaProject().isOnClasspath(file) &&
//				file.exists();
//	}

	/**
	 * pre-condition:
	 * delta.getElement().getElementType() == IJavaElement.JAVA_PROJECT
	 */
	protected boolean classpathHasChanged(IJavaElementDelta delta) {
		return this.deltaFlagIsSet(delta, IJavaElementDelta.F_RESOLVED_CLASSPATH_CHANGED);
	}

	protected void update(Iterable<JavaResourceCompilationUnit> javaResourceCompilationUnits) {
		for (JavaResourceCompilationUnit javaResourceCompilationUnit : javaResourceCompilationUnits) {
			javaResourceCompilationUnit.synchronizeWithJavaSource();
		}
	}

	// ***** package fragment root
	protected void processJavaPackageFragmentRootDelta(IJavaElementDelta delta) {
		// process the Java package fragment root's package fragments
		this.processJavaDeltaChildren(delta);

		if (this.classpathEntryHasBeenAdded(delta)) {
			// TODO bug 277218
		} else if (this.classpathEntryHasBeenRemoved(delta)) {  // should be mutually-exclusive w/added (?)
			// TODO bug 277218
		}
	}

	/**
	 * pre-condition:
	 * delta.getElement().getElementType() == IJavaElement.PACKAGE_FRAGMENT_ROOT
	 */
	protected boolean classpathEntryHasBeenAdded(IJavaElementDelta delta) {
		return this.deltaFlagIsSet(delta, IJavaElementDelta.F_ADDED_TO_CLASSPATH);
	}

	/**
	 * pre-condition:
	 * delta.getElement().getElementType() == IJavaElement.PACKAGE_FRAGMENT_ROOT
	 */
	protected boolean classpathEntryHasBeenRemoved(IJavaElementDelta delta) {
		return this.deltaFlagIsSet(delta, IJavaElementDelta.F_REMOVED_FROM_CLASSPATH);
	}

	// ***** package fragment
	protected void processJavaPackageFragmentDelta(IJavaElementDelta delta) {
		// process the java package fragment's compilation units
		this.processJavaDeltaChildren(delta);
	}

	// ***** compilation unit
	protected void processJavaCompilationUnitDelta(IJavaElementDelta delta) {
		if (this.javaCompilationUnitDeltaIsRelevant(delta)) {
			ICompilationUnit compilationUnit = (ICompilationUnit) delta.getElement();
			for (JavaResourceCompilationUnit jrcu : this.getCombinedJavaResourceCompilationUnits()) {
				if (jrcu.getCompilationUnit().equals(compilationUnit)) {
					jrcu.synchronizeWithJavaSource();
					// TODO ? this.resolveJavaTypes();  // might have new member types now...
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
//	
//	public Iterator<IMessage> validationMessages(IReporter reporter) {
//		List<IMessage> messages = new ArrayList<IMessage>();
//		this.validate(messages, reporter);
//		return messages.iterator();
//	}
//	
//	protected void validate(List<IMessage> messages, IReporter reporter) {
//		if (reporter.isCancelled()) {
//			throw new ValidationCancelledException();
//		}
//		validateLibraryProvider(messages);
//		validateConnection(messages);
//		this.rootContextNode.validate(messages, reporter);
//	}
//	
//	protected void validateLibraryProvider(List<IMessage> messages) {
//		try {
//			ILibraryProvider libraryProvider = LibraryProviderFramework.getCurrentProvider(getProject(), JpaFacet.FACET);
//			IFacetedProject facetedProject = ProjectFacetsManager.create(getProject());
//			IProjectFacetVersion facetVersion = facetedProject.getInstalledVersion(JpaFacet.FACET);
//			if (! libraryProvider.isEnabledFor(
//					facetedProject, facetVersion)) {
//				messages.add(
//						DefaultJpaValidationMessages.buildMessage(
//							IMessage.HIGH_SEVERITY,
//							JpaValidationMessages.PROJECT_INVALID_LIBRARY_PROVIDER,
//							this));
//			}
//		}
//		catch (CoreException ce) {
//			// fall through
//			JptCorePlugin.log(ce);
//		}
//	}


	
	// ********** dispose **********

	public void dispose() {
		this.updater.stop();
		// the XML resources are held indefinitely by the WTP translator framework,
		// so we better remove our listener or the JPA project will not be GCed
//		for (JpaFile jpaFile : this.getJpaFiles()) {
//			jpaFile.getResourceModel().removeResourceModelListener(this.resourceModelListener);
//		}
	}
	
	
	// ********** resource model listener **********
	
	protected class DefaultResourceModelListener 
		implements JpaResourceModelListener 
	{
		protected DefaultResourceModelListener() {
			super();
		}
		
		public void resourceModelChanged(JpaResourceModel jpaResourceModel) {
//			String msg = Thread.currentThread() + " resource model change: " + jpaResourceModel;
//			System.out.println(msg);
//			new Exception(msg).printStackTrace(System.out);
			AbstractJaxbProject.this.synchronizeContextModel(jpaResourceModel);
		}
		
		public void resourceModelReverted(JpaResourceModel jpaResourceModel) {
//			IFile file = WorkbenchResourceHelper.getFile((JpaXmlResource)jpaResourceModel);
//			AbstractJaxbProject.this.removeJpaFile(file);
//			AbstractJaxbProject.this.addJpaFile(file);
		}
		
		public void resourceModelUnloaded(JpaResourceModel jpaResourceModel) {
//			IFile file = WorkbenchResourceHelper.getFile((JpaXmlResource)jpaResourceModel);
//			AbstractJaxbProject.this.removeJpaFile(file);
		}
	}
	
	
	// ********** resource events **********
	
	// TODO need to do the same thing for external projects and compilation units
	public void projectChanged(IResourceDelta delta) {
		if (delta.getResource().equals(this.getProject())) {
			this.internalProjectChanged(delta);
		} else {
//			this.externalProjectChanged(delta);
		}
	}

	protected void internalProjectChanged(IResourceDelta delta) {
		if (delta.getKind() == IResourceDelta.REMOVED) {
			setUpdater(JaxbProject.Updater.Null.instance());
		}
//		ResourceDeltaVisitor resourceDeltaVisitor = this.buildInternalResourceDeltaVisitor();
//		resourceDeltaVisitor.visitDelta(delta);
//		// at this point, if we have added and/or removed JpaFiles, an "update" will have been triggered;
//		// any changes to the resource model during the "resolve" will trigger further "updates";
//		// there should be no need to "resolve" external Java types (they can't have references to
//		// the internal Java types)
//		if (resourceDeltaVisitor.encounteredSignificantChange()) {
//			this.resolveInternalJavaTypes();
//		}
	}
//
//	protected ResourceDeltaVisitor buildInternalResourceDeltaVisitor() {
//		return new ResourceDeltaVisitor() {
//			@Override
//			public boolean fileChangeIsSignificant(IFile file, int deltaKind) {
//				return AbstractJaxbProject.this.synchronizeJpaFiles(file, deltaKind);
//			}
//		};
//	}
//
//	/**
//	 * Internal resource delta visitor callback.
//	 * Return true if a JpaFile was either added or removed.
//	 */
//	protected boolean synchronizeJpaFiles(IFile file, int deltaKind) {
//		switch (deltaKind) {
//			case IResourceDelta.ADDED :
//				return this.addJpaFile(file);
//			case IResourceDelta.REMOVED :
//				return this.removeJpaFile(file);
//			case IResourceDelta.CHANGED :
//				return this.checkForChangedFileContent(file);
//			case IResourceDelta.ADDED_PHANTOM :
//				break;  // ignore
//			case IResourceDelta.REMOVED_PHANTOM :
//				break;  // ignore
//			default :
//				break;  // only worried about added/removed/changed files
//		}
//
//		return false;
//	}
//
//	protected boolean checkForChangedFileContent(IFile file) {
//		JpaFile jpaFile = this.getJpaFile(file);
//		if (jpaFile == null) {
//			// the file might have changed its content to something that we are interested in
//			return this.addJpaFile(file);
//		}
//
//		if (jpaFile.getContentType().equals(PlatformTools.getContentType(file))) {
//			// content has not changed - ignore
//			return false;
//		}
//
//		// the content type changed, we need to build a new JPA file
//		// (e.g. the schema of an orm.xml file changed from JPA to EclipseLink)
//		this.removeJpaFile(jpaFile);
//		this.addJpaFile(file);
//		return true;  // at the least, we have removed a JPA file
//	}
//
//	protected void resolveInternalJavaTypes() {
//		for (JavaResourceCompilationUnit jrcu : this.getInternalJavaResourceCompilationUnits()) {
//			jrcu.resolveTypes();
//		}
//	}
//
//	protected void externalProjectChanged(IResourceDelta delta) {
//		if (this.getJavaProject().isOnClasspath(delta.getResource())) {
//			ResourceDeltaVisitor resourceDeltaVisitor = this.buildExternalResourceDeltaVisitor();
//			resourceDeltaVisitor.visitDelta(delta);
//			// force an "update" here since adding and/or removing an external Java type
//			// will only trigger an "update" if the "resolve" causes something in the resource model to change
//			if (resourceDeltaVisitor.encounteredSignificantChange()) {
//				this.update();
//				this.resolveExternalJavaTypes();
//				this.resolveInternalJavaTypes();
//			}
//		}
//	}
//
//	protected ResourceDeltaVisitor buildExternalResourceDeltaVisitor() {
//		return new ResourceDeltaVisitor() {
//			@Override
//			public boolean fileChangeIsSignificant(IFile file, int deltaKind) {
//				return AbstractJaxbProject.this.synchronizeExternalFiles(file, deltaKind);
//			}
//		};
//	}
//
//	/**
//	 * external resource delta visitor callback
//	 * Return true if an "external" Java resource compilation unit
//	 * was added or removed.
//	 */
//	protected boolean synchronizeExternalFiles(IFile file, int deltaKind) {
//		switch (deltaKind) {
//			case IResourceDelta.ADDED :
//				return this.externalFileAdded(file);
//			case IResourceDelta.REMOVED :
//				return this.externalFileRemoved(file);
//			case IResourceDelta.CHANGED :
//				break;  // ignore
//			case IResourceDelta.ADDED_PHANTOM :
//				break;  // ignore
//			case IResourceDelta.REMOVED_PHANTOM :
//				break;  // ignore
//			default :
//				break;  // only worried about added/removed/changed files
//		}
//
//		return false;
//	}
//
//	protected boolean externalFileAdded(IFile file) {
//		IContentType contentType = PlatformTools.getContentType(file);
//		if (contentType == null) {
//			return false;
//		}
//		if (contentType.equals(JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE)) {
//			return true;
//		}
//		if (contentType.equals(JptCorePlugin.JAR_CONTENT_TYPE)) {
//			return true;
//		}
//		return false;
//	}
//
//	protected boolean externalFileRemoved(IFile file) {
//		IContentType contentType = PlatformTools.getContentType(file);
//		if (contentType == null) {
//			return false;
//		}
//		if (contentType.equals(JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE)) {
//			return this.removeExternalJavaResourceCompilationUnit(file);
//		}
//		if (contentType.equals(JptCorePlugin.JAR_CONTENT_TYPE)) {
//			return this.externalJavaResourcePersistentTypeCache.removePersistentTypes(file);
//		}
//		return false;
//	}
//
//	protected void resolveExternalJavaTypes() {
//		for (JavaResourceCompilationUnit jrcu : this.getExternalJavaResourceCompilationUnits()) {
//			jrcu.resolveTypes();
//		}
//	}

	// ***** resource delta visitors
	/**
	 * add or remove a JPA file for every [appropriate] file encountered by the visitor
	 */
	protected abstract class ResourceDeltaVisitor implements IResourceDeltaVisitor {
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

	}


	// ********** support for modifying documents shared with the UI **********

	public void setThreadLocalModifySharedDocumentCommandExecutor(CommandExecutor commandExecutor) {
		this.modifySharedDocumentCommandExecutor.set(commandExecutor);
	}

	public CommandExecutor getModifySharedDocumentCommandExecutor() {
		return this.modifySharedDocumentCommandExecutor;
	}


	// ********** synchronize context model with resource model **********

	// TODO ...
	protected void synchronizeContextModel(@SuppressWarnings("unused") JpaResourceModel jpaResourceModel) {
		this.synchronizeContextModel();
	}

	public void synchronizeContextModel() {
		this.update();
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
		this.update_(monitor);
		return Status.OK_STATUS;
	}

	protected void update_(IProgressMonitor monitor) {
//		this.rootContextNode.update(monitor);
//		this.rootContextNode.postUpdate();
	}

	/**
	 * Also called by the updater.
	 */
	public void updateQuiesced() {
		//nothing yet
	}

}
