/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.xml;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jem.util.emf.workbench.EMFWorkbenchContextBase;
import org.eclipse.jem.util.emf.workbench.FlexibleProjectResourceSet;
import org.eclipse.jem.util.emf.workbench.IEMFContextContributor;
import org.eclipse.jem.util.emf.workbench.ProjectResourceSet;
import org.eclipse.jem.util.emf.workbench.WorkbenchResourceHelperBase;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.common.core.resource.xml.JpaXmlResourceProvider;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.wst.common.componentcore.internal.impl.WTPResourceFactoryRegistry;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;
import org.eclipse.wst.common.internal.emfworkbench.validateedit.ResourceStateInputProvider;
import org.eclipse.wst.common.internal.emfworkbench.validateedit.ResourceStateValidator;
import org.eclipse.wst.common.internal.emfworkbench.validateedit.ResourceStateValidatorImpl;
import org.eclipse.wst.common.internal.emfworkbench.validateedit.ResourceStateValidatorPresenter;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.2
 */
public abstract class AbstractJptXmlResourceProvider
	implements JpaXmlResourceProvider, IEMFContextContributor, ResourceStateInputProvider, ResourceStateValidator
{
	protected IProject project;
	
	protected URI fileUri;
	
	protected JptXmlResource resource;
	
	protected IContentType contentType;
	
	protected ResourceStateValidator stateValidator;
	
	
	/**
	 * Create a new AbstractResourceModelProvider for the given project and 
	 * resourcePath.  The resourcePath may be either 
	 * 	a) an absolute workspace-relative platform resource path (e.g. "/MyProject/src/META-INF/foobar.xml") 
	 * 	or b) a relative runtime path (e.g. "META-INF/foobar.xml".)  
	 * In either case, {@link #buildFileUri(IPath)} will attempt to build an absolutely pathed 
	 * URI for the given path.
	 */
	public AbstractJptXmlResourceProvider(IProject project, IPath resourcePath, IContentType contentType) {
		super();
		this.project = project;
		this.fileUri = buildFileUri(resourcePath);
		this.contentType = contentType;
	}
	
	protected URI buildFileUri(IPath resourcePath) {
		if ( ! resourcePath.isAbsolute()) {
			resourcePath = this.getProjectResourceLocator().getResourcePath(resourcePath);
		}
		URI resourceURI = URI.createPlatformResourceURI(resourcePath.toString(), false);
		
		URIConverter uriConverter = this.getResourceSet().getURIConverter();
		return uriConverter.normalize(resourceURI);
	}
	
	protected ProjectResourceLocator getProjectResourceLocator() {
		return (ProjectResourceLocator) this.project.getAdapter(ProjectResourceLocator.class);
	}
	
	/**
	 * Return the resource, if it exists.  If no file exists for this resource, 
	 * this will return a stub resource.  You must call #createResource() to 
	 * create the file on the file system.
	 */
	public JptXmlResource getXmlResource() {
		if (this.resource == null) {
			JptXmlResource newResource = (JptXmlResource) WorkbenchResourceHelper.getOrCreateResource(this.fileUri, getResourceSet());
			if (newResource == null) {
				//ResourceSet "isReleasing", deleting the project causes this
				return null;
			}
			//EMF caches resources based on URI.  If the resource has changed content types (say the schema was changed
			//from orm to eclipselink-orm), then the resource will be of the wrong type and we need to create a new one.
			if (newResource.getContentType().equals(this.contentType)) {
				this.resource = newResource;
			}
			else {
				this.createResourceAndLoad();
			}
		}
		return this.resource;
	}
	
	protected JptXmlResource createResourceAndLoad() {
		this.resource = createResource();
		this.loadResource();
		return this.resource;
	}
	
	protected JptXmlResource createResource() {
		Resource.Factory resourceFactory = 
			WTPResourceFactoryRegistry.INSTANCE.getFactory(this.fileUri, this.contentType.getDefaultDescription());
		return (JptXmlResource) getResourceSet().createResource(this.fileUri, resourceFactory);		
	}
	
	protected void loadResource() {
		try {
			this.resource.load(((ProjectResourceSet) getResourceSet()).getLoadOptions());
		}
		catch (IOException e) {
			JptCommonCorePlugin.instance().logError(e);
		}
	}
	
	protected void createResourceAndUnderlyingFile(Object config) {
		this.resource = createResource();
		if (this.resource.fileExists()) { //always possible that the file already exists when the jpa facet is added
			loadResource();
		}
		else {
			populateRoot(config);
			try {
				this.resource.saveIfNecessary(); //this writes out the file
			}
			catch (Exception e) {
				JptCommonCorePlugin.instance().logError(e);
			}
		}
	}
	
	/**
	 * This will actually create the underlying file and the JptXmlResource that corresponds to it.
	 * It also populates the root of the file.
	 * @param config - A configuration object used to specify options for creation of the resource
	 */
	public JptXmlResource createFileAndResource(Object config, IProgressMonitor monitor) throws CoreException {
		IWorkspace workspace = this.project.getWorkspace();
		IWorkspaceRunnable runnable = new CreateFileAndResourceWorkspaceRunnable(config);
		workspace.run(runnable, this.project, IWorkspace.AVOID_UPDATE, monitor);
		return this.resource;	
	}

	class CreateFileAndResourceWorkspaceRunnable
		implements IWorkspaceRunnable
	{
		private final Object config;
		CreateFileAndResourceWorkspaceRunnable(Object config) {
			super();
			this.config = config;
		}
		public void run(IProgressMonitor monitor) {
			AbstractJptXmlResourceProvider.this.createResourceAndUnderlyingFile(this.config);
		}
	}
	
	/**
	 * Used to optionally fill in the root information of a resource if it does not 
	 * exist as a file
	 */
	protected void populateRoot(@SuppressWarnings("unused") Object config) {
		//TODO potentially call resource.populateRoot() instead of the resourceProvider doing this
	}
	

	/**
	 * minimize the scope of the suppressed warnings
	 */
	protected EList<EObject> getResourceContents() {
		return this.resource.getContents();
	}
	
	protected FlexibleProjectResourceSet getResourceSet() {
		return (FlexibleProjectResourceSet) getEmfContext().getResourceSet();
	}
	
	protected EMFWorkbenchContextBase getEmfContext() {
		return WorkbenchResourceHelperBase.createEMFContext(this.project, this);
	}
	
	public IProject getProject() {
		return this.project;
	}
	
	public IStatus validateEdit(Object context) {
		IWorkspace work = this.project.getWorkspace();
		IFile file = WorkbenchResourceHelper.getFile(this.resource);
		if (file != null) {
			IFile[] files = { file };
			if (context == null) {
				context = IWorkspace.VALIDATE_PROMPT;
			}
			return work.validateEdit(files, context);
		} 
		return Status.OK_STATUS;
	}
	
	
	// **************** IEMFContextContributor impl ***************************
	
	private boolean contributedToEmfContext = false;
	
	public void primaryContributeToContext(EMFWorkbenchContextBase context) {
		if (! this.contributedToEmfContext) {
			context.getResourceSet().setResourceFactoryRegistry(WTPResourceFactoryRegistry.INSTANCE);
			this.contributedToEmfContext = true;
		}
	}
	
	public void secondaryContributeToContext(EMFWorkbenchContextBase aNature) {
		// no op
	}
	
	
	// **************** ResourceStateValidator impl ****************************
	
	public ResourceStateValidator getStateValidator() {
		if (this.stateValidator == null) {
			this.stateValidator = createStateValidator();
		}
		return this.stateValidator;
	}
	
	private ResourceStateValidator createStateValidator() {
		return new ResourceStateValidatorImpl(this);
	}
	
	public void checkActivation(ResourceStateValidatorPresenter presenter) throws CoreException {
		getStateValidator().checkActivation(presenter);	
	}
	
	public void lostActivation(ResourceStateValidatorPresenter presenter) throws CoreException {
		getStateValidator().lostActivation(presenter);	
	}
	
	public IStatus validateState(ResourceStateValidatorPresenter presenter) throws CoreException {
		return (presenter == null) ? Status.OK_STATUS : getStateValidator().validateState(presenter);
	}
	
	public boolean checkSave(ResourceStateValidatorPresenter presenter) throws CoreException {
		return getStateValidator().checkSave(presenter);
	}
	
	public boolean checkReadOnly() {
		return getStateValidator().checkReadOnly();
	}
	
	
	// **************** ResourceStateInputProvider impl ************************
	
	public boolean isDirty() {	
		return this.resource.isModified();
	}
	
	@SuppressWarnings("rawtypes")
	public List getNonResourceFiles() {
		return Collections.emptyList();
	}
	
	@SuppressWarnings("rawtypes")
	public List getNonResourceInconsistentFiles() {
		return Collections.emptyList();
	}
	
	@SuppressWarnings("rawtypes")
	public List getResources() {
		return Collections.singletonList(getXmlResource());
	}
	
	@SuppressWarnings("rawtypes")
	public void cacheNonResourceValidateState(List roNonResourceFiles) {
		// do nothing
	}
}
