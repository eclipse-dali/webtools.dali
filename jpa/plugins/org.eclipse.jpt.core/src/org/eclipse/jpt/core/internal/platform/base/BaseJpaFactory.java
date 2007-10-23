/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform.base;

import java.io.IOException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.internal.IContextModel;
import org.eclipse.jpt.core.internal.IJpaDataSource;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.IResourceModel;
import org.eclipse.jpt.core.internal.JpaDataSource;
import org.eclipse.jpt.core.internal.JpaFile;
import org.eclipse.jpt.core.internal.JpaProject;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.BaseJpaContent;
import org.eclipse.jpt.core.internal.context.base.IBaseJpaContent;
import org.eclipse.jpt.core.internal.context.base.IPersistence;
import org.eclipse.jpt.core.internal.context.base.IPersistenceXml;
import org.eclipse.jpt.core.internal.context.base.Persistence;
import org.eclipse.jpt.core.internal.context.base.PersistenceXml;
import org.eclipse.jpt.core.internal.resource.java.JavaResourceModel;
import org.eclipse.jpt.core.internal.resource.orm.OrmResourceModel;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;

public abstract class BaseJpaFactory implements IJpaBaseContextFactory
{
	protected BaseJpaFactory() {
		super();
	}
	
	
	// **************** Core objects ******************************************
	
	public IJpaProject createJpaProject(IJpaProject.Config config) throws CoreException {
		return new JpaProject(config);
	}
	
	public IJpaDataSource createJpaDataSource(IJpaProject jpaProject, String connectionProfileName) {
		return new JpaDataSource(jpaProject, connectionProfileName);
	}
	
	public IJpaFile createJpaFile(IJpaProject jpaProject, IFile file, IResourceModel resourceModel) {
		return new JpaFile(jpaProject, file, resourceModel);
	}
	
	
	// **************** Resource objects **************************************
	
	public boolean hasRelevantContent(IFile file) {
		if (! JavaCore.create(file.getProject()).isOnClasspath(file)) {
			return false;
		}
		IContentType contentType = this.contentType(file);
		if (contentType == null) {
			return false;
		}
		String contentTypeId = contentType.getId();
		return supportsContentType(contentTypeId);
	}
	
	protected boolean supportsContentType(String contentTypeId) {
		return contentTypeId.equals(JavaCore.JAVA_SOURCE_CONTENT_TYPE)
				|| contentTypeId.equals(JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE)
				|| contentTypeId.equals(JptCorePlugin.ORM_XML_CONTENT_TYPE);
	}
	
	public IResourceModel buildResourceModel(IJpaProject jpaProject, IFile file) {
		if (! JavaCore.create(jpaProject.project()).isOnClasspath(file)) {
			return null;
		}
		IContentType contentType = this.contentType(file);
		if (contentType == null) {
			return null;
		}
		String contentTypeId = contentType.getId();
		return buildResourceModel(jpaProject, file, contentTypeId);
	}
	
	protected IResourceModel buildResourceModel(IJpaProject jpaProject, IFile file, String contentTypeId) {
		if (JavaCore.JAVA_SOURCE_CONTENT_TYPE.equals(contentTypeId)) {
			return buildJavaResourceModel(jpaProject, file);
		}
		else if (JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE.equals(contentTypeId)) {
			return buildPersistenceResourceModel(file);
		}
		else if (JptCorePlugin.ORM_XML_CONTENT_TYPE.equals(contentTypeId)) {
			return buildOrmResourceModel(file);
		}
		
		return null;
	}
	
	protected IResourceModel buildJavaResourceModel(IJpaProject jpaProject, IFile file) {
		return new JavaResourceModel(
				file, jpaProject.jpaPlatform().annotationProvider(), 
				jpaProject.modifySharedDocumentCommandExecutorProvider());
	}
	
	protected IResourceModel buildPersistenceResourceModel(IFile file) {
		PersistenceResourceModel resource = 
				(PersistenceResourceModel) WorkbenchResourceHelper.getResource(file, true);
		resource.accessForWrite();
		return resource;
	}
	
	protected IResourceModel buildOrmResourceModel(IFile file) {
		OrmResourceModel resource = 
				(OrmResourceModel) WorkbenchResourceHelper.getResource(file, true);
		resource.accessForWrite();
		return resource;
	}
	
	// attempting to get the contentType based on the file contents.
	// have to check the file contents instead of just the file name
	// because for xml we base it on the rootElement name
	private IContentType contentType(IFile file) {
		try {
			return Platform.getContentTypeManager().findContentTypeFor(file.getContents(), file.getName());
		}
		catch (IOException ex) {
			JptCorePlugin.log(ex);
		}
		catch (CoreException ex) {
			JptCorePlugin.log(ex);
		}
		return null;
	}
	
	
	// **************** Context objects ***************************************
	
	public IContextModel buildContextModel(IJpaProject jpaProject) {
		return new BaseJpaContent(jpaProject);
	}
	
	public IPersistenceXml createPersistenceXml(IBaseJpaContent baseJpaContent) {
		return new PersistenceXml(baseJpaContent);
	}
	
	public IPersistence createPersistence(IPersistenceXml persistenceXml) {
		return new Persistence(persistenceXml);
	}
}
