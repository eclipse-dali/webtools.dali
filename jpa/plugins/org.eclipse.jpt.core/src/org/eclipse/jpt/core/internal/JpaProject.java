/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.internal.content.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.content.java.JpaCompilationUnit;
import org.eclipse.jpt.core.internal.facet.JpaFacetUtils;
import org.eclipse.jpt.core.internal.platform.IContext;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.JptDbPlugin;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyIterator;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.wst.common.componentcore.internal.util.IModuleConstants;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Jpa Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.JpaProject#getPlatform <em>Platform</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.JpaProject#getDataSource <em>Data Source</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.JpaProject#isDiscoverAnnotatedClasses <em>Discover Annotated Classes</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.JpaProject#getFiles <em>Files</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaProject()
 * @model kind="class"
 * @generated
 */
public class JpaProject extends JpaEObject implements IJpaProject
{
	/**
	 * The IProject associated with this JPA project
	 */
	protected IProject project;

	/**
	 * The IJpaPlatform associated with this JPA project
	 */
	protected IJpaPlatform platform;

	/**
	 * The cached value of the '{@link #getDataSource() <em>Data Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataSource()
	 * @generated
	 * @ordered
	 */
	protected IJpaDataSource dataSource;

	/**
	 * The default value of the '{@link #isDiscoverAnnotatedClasses() <em>Discover Annotated Classes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDiscoverAnnotatedClasses()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DISCOVER_ANNOTATED_CLASSES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDiscoverAnnotatedClasses() <em>Discover Annotated Classes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDiscoverAnnotatedClasses()
	 * @generated
	 * @ordered
	 */
	protected boolean discoverAnnotatedClasses = DISCOVER_ANNOTATED_CLASSES_EDEFAULT;

	/**
	 * This is true if the Discover Annotated Classes attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean discoverAnnotatedClassesESet;

	/**
	 * The cached value of the '{@link #getFiles() <em>Files</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFiles()
	 * @generated
	 * @ordered
	 */
	protected EList<IJpaFile> files;

	/**
	 * Flag to indicate whether this project has been filled with files
	 */
	private boolean filled = false;

	/**
	 * Flag to indicate that the resynchJob has been scheduled or is running.
	 * This is set to false when that job is completed 
	 */
	boolean resynching = false;

	/**
	 * Flag to indicate that the resynchJob needs to be run.  This is
	 * set to true if resynching = true so that the next time the job completes
	 * it will be run again. 
	 */
	boolean needsToResynch = false;

	/**
	 * Job used to reconnect the model parts throughout the project containment hierarchy
	 * @see IJpaProject#resynch()
	 */
	Job resynchJob;

	private IJobChangeListener resynchJobListener;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected JpaProject() {
		super();
		this.resynchJob = buildResynchJob();
		this.resynchJobListener = buildResynchJobListener();
		Job.getJobManager().addJobChangeListener(this.resynchJobListener);
	}

	private Job buildResynchJob() {
		return new Job("Resynching JPA model ...") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IContext contextHierarchy = getPlatform().buildProjectContext();
				getPlatform().resynch(contextHierarchy);
				return Status.OK_STATUS;
			}
		};
	}

	private IJobChangeListener buildResynchJobListener() {
		return new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				super.done(event);
				if (event.getJob() == JpaProject.this.resynchJob) {
					JpaProject.this.resynching = false;
					if (JpaProject.this.needsToResynch) {
						resynch();
					}
				}
			}
		};
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaCorePackage.Literals.JPA_PROJECT;
	}

	/**
	 * @see IJpaProject#getProject()
	 */
	public IProject getProject() {
		return project;
	}

	void setProject(IProject theProject) {
		project = theProject;
	}

	public IJavaProject getJavaProject() {
		return JavaCore.create(getProject());
	}

	/**
	 * @see IJpaProject#getModel()
	 */
	public IJpaModel getModel() {
		return (IJpaModel) eContainer();
	}

	/**
	 * Returns the value of the '<em><b>Platform</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Platform</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Platform</em>' reference.
	 * @see #setPlatform(IJpaPlatform)
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaProject_Platform()
	 * @model resolveProxies="false" required="true" ordered="false"
	 * @generated
	 */
	public IJpaPlatform getPlatformGen() {
		return platform;
	}

	public IJpaPlatform getPlatform() {
		if (platform == null) {
			setPlatform(JpaFacetUtils.getPlatform(project));
		}
		return getPlatformGen();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.JpaProject#getPlatform <em>Platform</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Platform</em>' reference.
	 * @see #getPlatform()
	 * @generated
	 */
	public void setPlatformGen(IJpaPlatform newPlatform) {
		IJpaPlatform oldPlatform = platform;
		platform = newPlatform;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaCorePackage.JPA_PROJECT__PLATFORM, oldPlatform, platform));
	}

	public void setPlatform(IJpaPlatform jpaPlatform) {
		jpaPlatform.setProject(this);
		setPlatformGen(jpaPlatform);
	}

	/**
	 * @see IJpaProject#setPlatform(String)
	 */
	public void setPlatform(String platformId) {
		setPlatform(JpaPlatformRegistry.INSTANCE.getJpaPlatform(platformId));
	}

	/**
	 * Returns the value of the '<em><b>Data Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Source</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Source</em>' containment reference.
	 * @see #setDataSource(IJpaDataSource)
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaProject_DataSource()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	public IJpaDataSource getDataSourceGen() {
		return dataSource;
	}

	public synchronized IJpaDataSource getDataSource() {
		if (dataSource == null) {
			setDataSource(JpaFacetUtils.getConnectionName(project));
		}
		return getDataSourceGen();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDataSource(IJpaDataSource newDataSource, NotificationChain msgs) {
		IJpaDataSource oldDataSource = dataSource;
		dataSource = newDataSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JpaCorePackage.JPA_PROJECT__DATA_SOURCE, oldDataSource, newDataSource);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.JpaProject#getDataSource <em>Data Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Source</em>' containment reference.
	 * @see #getDataSource()
	 * @generated
	 */
	public void setDataSource(IJpaDataSource newDataSource) {
		if (newDataSource != dataSource) {
			NotificationChain msgs = null;
			if (dataSource != null)
				msgs = ((InternalEObject) dataSource).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JpaCorePackage.JPA_PROJECT__DATA_SOURCE, null, msgs);
			if (newDataSource != null)
				msgs = ((InternalEObject) newDataSource).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JpaCorePackage.JPA_PROJECT__DATA_SOURCE, null, msgs);
			msgs = basicSetDataSource(newDataSource, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaCorePackage.JPA_PROJECT__DATA_SOURCE, newDataSource, newDataSource));
	}

	/**
	 * @see IJpaProject#setDataSource(String)
	 */
	public void setDataSource(String connectionProfileName) {
		if (dataSource == null) {
			JpaDataSource ds = JpaCoreFactory.eINSTANCE.createJpaDataSource();
			setDataSource(ds);
		}
		dataSource.setConnectionProfileName(connectionProfileName);
	}

	/**
	 * Returns the value of the '<em><b>Discover Annotated Classes</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discover Annotated Classes</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discover Annotated Classes</em>' attribute.
	 * @see #isSetDiscoverAnnotatedClasses()
	 * @see #unsetDiscoverAnnotatedClasses()
	 * @see #setDiscoverAnnotatedClasses(boolean)
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaProject_DiscoverAnnotatedClasses()
	 * @model default="false" unique="false" unsettable="true" required="true" ordered="false"
	 * @generated
	 */
	public boolean isDiscoverAnnotatedClassesGen() {
		return discoverAnnotatedClasses;
	}

	public boolean isDiscoverAnnotatedClasses() {
		if (!isSetDiscoverAnnotatedClasses()) {
			setDiscoverAnnotatedClasses(JpaFacetUtils.getDiscoverAnnotatedClasses(project));
		}
		return isDiscoverAnnotatedClassesGen();
	}

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.JpaProject#isDiscoverAnnotatedClasses <em>Discover Annotated Classes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discover Annotated Classes</em>' attribute.
	 * @see #isSetDiscoverAnnotatedClasses()
	 * @see #unsetDiscoverAnnotatedClasses()
	 * @see #isDiscoverAnnotatedClasses()
	 * @generated
	 */
	public void setDiscoverAnnotatedClasses(boolean newDiscoverAnnotatedClasses) {
		boolean oldDiscoverAnnotatedClasses = discoverAnnotatedClasses;
		discoverAnnotatedClasses = newDiscoverAnnotatedClasses;
		boolean oldDiscoverAnnotatedClassesESet = discoverAnnotatedClassesESet;
		discoverAnnotatedClassesESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JpaCorePackage.JPA_PROJECT__DISCOVER_ANNOTATED_CLASSES, oldDiscoverAnnotatedClasses, discoverAnnotatedClasses, !oldDiscoverAnnotatedClassesESet));
	}

	/**
	 * Unsets the value of the '{@link org.eclipse.jpt.core.internal.JpaProject#isDiscoverAnnotatedClasses <em>Discover Annotated Classes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDiscoverAnnotatedClasses()
	 * @see #isDiscoverAnnotatedClasses()
	 * @see #setDiscoverAnnotatedClasses(boolean)
	 * @generated
	 */
	public void unsetDiscoverAnnotatedClasses() {
		boolean oldDiscoverAnnotatedClasses = discoverAnnotatedClasses;
		boolean oldDiscoverAnnotatedClassesESet = discoverAnnotatedClassesESet;
		discoverAnnotatedClasses = DISCOVER_ANNOTATED_CLASSES_EDEFAULT;
		discoverAnnotatedClassesESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, JpaCorePackage.JPA_PROJECT__DISCOVER_ANNOTATED_CLASSES, oldDiscoverAnnotatedClasses, DISCOVER_ANNOTATED_CLASSES_EDEFAULT, oldDiscoverAnnotatedClassesESet));
	}

	/**
	 * Returns whether the value of the '{@link org.eclipse.jpt.core.internal.JpaProject#isDiscoverAnnotatedClasses <em>Discover Annotated Classes</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Discover Annotated Classes</em>' attribute is set.
	 * @see #unsetDiscoverAnnotatedClasses()
	 * @see #isDiscoverAnnotatedClasses()
	 * @see #setDiscoverAnnotatedClasses(boolean)
	 * @generated
	 */
	public boolean isSetDiscoverAnnotatedClasses() {
		return discoverAnnotatedClassesESet;
	}
	
	public String rootDeployLocation() {
		String metaInfLocation = "";
		try {
			if (FacetedProjectFramework.hasProjectFacet(project, IModuleConstants.JST_WEB_MODULE)) {
				metaInfLocation = J2EEConstants.WEB_INF_CLASSES;
			}
		}
		catch (CoreException ce) {
			// if exception occurs, we'll take the default location
			JptCorePlugin.log(ce);
		}
		return metaInfLocation;
	}

	@Override
	public IJpaProject getJpaProject() {
		return this;
	}

	/**
	 * Returns the value of the '<em><b>Files</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.internal.IJpaFile}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Files</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Files</em>' containment reference list.
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getJpaProject_Files()
	 * @model type="org.eclipse.jpt.core.internal.IJpaFile" containment="true"
	 * @generated
	 */
	public EList<IJpaFile> getFiles() {
		if (files == null) {
			files = new EObjectContainmentEList<IJpaFile>(IJpaFile.class, this, JpaCorePackage.JPA_PROJECT__FILES);
		}
		return files;
	}

	/**
	 * INTERNAL ONLY
	 * Fill project with file resources
	 */
	void fill() throws CoreException {
		if (filled) {
			return;
		}
		IResourceProxyVisitor visitor = new IResourceProxyVisitor() {
			public boolean visit(IResourceProxy resource) throws CoreException {
				switch (resource.getType()) {
					case IResource.PROJECT :
					case IResource.FOLDER :
						return true;
					case IResource.FILE :
						IFile file = (IFile) resource.requestResource();
						if (getJpaFileInternal(file) == null) {
							createJpaFile(file);
						}
					default :
						return false;
				}
			}
		};
		getProject().accept(visitor, IResource.NONE);
		resynch();
		filled = true;
	}

	/**
	 * @see IJpaProject#getJpaFile(IFile)
	 */
	public synchronized IJpaFile getJpaFile(IFile file) {
		IJpaFile jpaFile = getJpaFileInternal(file);
		if (jpaFile != null) {
			return jpaFile;
		}
		else if (!filled) {
			return createJpaFile(file);
		}
		else {
			return null;
		}
	}

	synchronized IJpaFile getJpaFileInternal(IFile file) {
		for (IJpaFile next : this.getFiles()) {
			if (next.getFile().equals(file)) {
				return next;
			}
		}
		return null;
	}

	public Iterator<IJpaFile> jpaFiles() {
		return new ReadOnlyIterator<IJpaFile>(getFiles().iterator());
	}

	public Collection<IJpaFile> jpaFiles(String contentType) {
		Collection<IJpaFile> jpaFiles = new ArrayList<IJpaFile>();
		for (Iterator<IJpaFile> stream = jpaFiles(); stream.hasNext();) {
			IJpaFile next = stream.next();
			if (next.getContentId().equals(contentType)) {
				jpaFiles.add(next);
			}
		}
		return jpaFiles;
	}

	public JavaPersistentType findJavaPersistentType(IType type) {
		if (type == null) {
			return null;
		}
		Collection<IJpaFile> persistenceFiles = jpaFiles(JptCorePlugin.JAVA_CONTENT_TYPE);
		for (IJpaFile jpaFile : persistenceFiles) {
			JpaCompilationUnit compilationUnit = (JpaCompilationUnit) jpaFile.getContent();
			for (JavaPersistentType persistentType : compilationUnit.getTypes()) {
				if (type.equals(persistentType.getType().getJdtMember())) {
					return persistentType;
				}
			}
		}
		return null;
	}

	/**
	 * INTERNAL ONLY
	 * Dispose of project before it is removed
	 */
	void dispose() {
		for (Iterator<IJpaFile> stream = new CloneIterator<IJpaFile>(getFiles()); stream.hasNext();) {
			disposeFile((JpaFile) stream.next());
		}
		Job.getJobManager().removeJobChangeListener(this.resynchJobListener);
	}

	/**
	 * INTERNAL ONLY
	 * Dispose file and remove it
	 */
	void disposeFile(JpaFile jpaFile) {
		jpaFile.dispose();
		getFiles().remove(jpaFile);
	}

	/**
	 * INTERNAL ONLY
	 * Synch internal JPA resources.
	 */
	void synchInternalResources(IResourceDelta delta) throws CoreException {
		delta.accept(this.buildResourceDeltaVisitor());
	}

	private IResourceDeltaVisitor buildResourceDeltaVisitor() {
		return new IResourceDeltaVisitor() {
			private IResource currentResource = getProject();

			public boolean visit(IResourceDelta delta) throws CoreException {
				IResource res = delta.getResource();
				if (res.equals(currentResource))
					return true;
				if (res.getType() == IResource.FILE) {
					IFile file = (IFile) res;
					switch (delta.getKind()) {
						case IResourceDelta.ADDED :
							if (getJpaFile(file) == null) {
								createJpaFile(file);
								JpaProject.this.resynch();//TODO different api for this?
							}
							break;
						case IResourceDelta.REMOVED :
							JpaFile jpaFile = (JpaFile) getJpaFile(file);
							if (jpaFile != null) {
								disposeFile(jpaFile);
								JpaProject.this.resynch();//TODO different api for this?
							}
							break;
						case IResourceDelta.CHANGED :
							// shouldn't have to worry, since all changes we're looking for have to do with file name
					}
				}
				return true;
			}
		};
	}

	synchronized IJpaFile createJpaFile(IFile file) {
		if (!JavaCore.create(getProject()).isOnClasspath(file)) {
			return null;
		}
		IJpaFile jpaFile = JpaFileContentRegistry.getFile(this, file);
		// PWFTODO 
		// Return a NullPersistenceFile if no content found?
		if (jpaFile != null) {
			getFiles().add(jpaFile);
			return jpaFile;
		}
		return null;
	}

	/**
	 * INTERNAL ONLY
	 * Handle java element change event.
	 */
	void handleEvent(ElementChangedEvent event) {
		if (filled) {
			for (Iterator<IJpaFile> stream = jpaFiles(); stream.hasNext();) {
				((JpaFile) stream.next()).handleEvent(event);
			}
			resynch();
		}
	}

	public Iterator<IMessage> validationMessages() {
		List<IMessage> messages = new ArrayList<IMessage>();
		getPlatform().addToMessages(messages);
		return messages.iterator();
	}

	//leaving this at the JpaProject level for now instead of
	//passing it on to the JpaModel.  We don't currently support
	//multiple projects having cross-references
	public void resynch() {
		if (!this.resynching) {
			this.resynching = true;
			this.needsToResynch = false;
			this.resynchJob.schedule();
		}
		else {
			this.needsToResynch = true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JpaCorePackage.JPA_PROJECT__DATA_SOURCE :
				return basicSetDataSource(null, msgs);
			case JpaCorePackage.JPA_PROJECT__FILES :
				return ((InternalEList<?>) getFiles()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JpaCorePackage.JPA_PROJECT__PLATFORM :
				return getPlatform();
			case JpaCorePackage.JPA_PROJECT__DATA_SOURCE :
				return getDataSource();
			case JpaCorePackage.JPA_PROJECT__DISCOVER_ANNOTATED_CLASSES :
				return isDiscoverAnnotatedClasses() ? Boolean.TRUE : Boolean.FALSE;
			case JpaCorePackage.JPA_PROJECT__FILES :
				return getFiles();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case JpaCorePackage.JPA_PROJECT__PLATFORM :
				setPlatform((IJpaPlatform) newValue);
				return;
			case JpaCorePackage.JPA_PROJECT__DATA_SOURCE :
				setDataSource((IJpaDataSource) newValue);
				return;
			case JpaCorePackage.JPA_PROJECT__DISCOVER_ANNOTATED_CLASSES :
				setDiscoverAnnotatedClasses(((Boolean) newValue).booleanValue());
				return;
			case JpaCorePackage.JPA_PROJECT__FILES :
				getFiles().clear();
				getFiles().addAll((Collection<? extends IJpaFile>) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case JpaCorePackage.JPA_PROJECT__PLATFORM :
				setPlatform((IJpaPlatform) null);
				return;
			case JpaCorePackage.JPA_PROJECT__DATA_SOURCE :
				setDataSource((IJpaDataSource) null);
				return;
			case JpaCorePackage.JPA_PROJECT__DISCOVER_ANNOTATED_CLASSES :
				unsetDiscoverAnnotatedClasses();
				return;
			case JpaCorePackage.JPA_PROJECT__FILES :
				getFiles().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case JpaCorePackage.JPA_PROJECT__PLATFORM :
				return platform != null;
			case JpaCorePackage.JPA_PROJECT__DATA_SOURCE :
				return dataSource != null;
			case JpaCorePackage.JPA_PROJECT__DISCOVER_ANNOTATED_CLASSES :
				return isSetDiscoverAnnotatedClasses();
			case JpaCorePackage.JPA_PROJECT__FILES :
				return files != null && !files.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (" + getProject().toString() + ")");
		return result.toString();
	}

	public ConnectionProfile connectionProfile() {
		String profileName = getDataSource().getConnectionProfileName();
		return JptDbPlugin.getDefault().getConnectionProfileRepository().profileNamed(profileName);
	}
}
