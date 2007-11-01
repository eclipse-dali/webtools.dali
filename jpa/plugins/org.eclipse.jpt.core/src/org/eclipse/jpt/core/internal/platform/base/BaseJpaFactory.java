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
import org.eclipse.jpt.core.internal.context.base.ClassRef;
import org.eclipse.jpt.core.internal.context.base.IBaseJpaContent;
import org.eclipse.jpt.core.internal.context.base.IClassRef;
import org.eclipse.jpt.core.internal.context.base.IMappingFileRef;
import org.eclipse.jpt.core.internal.context.base.IPersistence;
import org.eclipse.jpt.core.internal.context.base.IPersistenceUnit;
import org.eclipse.jpt.core.internal.context.base.IPersistenceXml;
import org.eclipse.jpt.core.internal.context.base.MappingFileRef;
import org.eclipse.jpt.core.internal.context.base.Persistence;
import org.eclipse.jpt.core.internal.context.base.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.base.PersistenceXml;
import org.eclipse.jpt.core.internal.context.java.IJavaEntity;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.context.java.JavaEntity;
import org.eclipse.jpt.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.core.internal.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.jdtutility.DefaultAnnotationEditFormatter;
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
				jpaProject.modifySharedDocumentCommandExecutorProvider(),
				DefaultAnnotationEditFormatter.instance());
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
	
	public IContextModel buildContextModel(IJpaProject parent) {
		return new BaseJpaContent(parent);
	}
	
	public IPersistenceXml createPersistenceXml(IBaseJpaContent parent) {
		return new PersistenceXml(parent);
	}
	
	public IPersistence createPersistence(IPersistenceXml parent) {
		return new Persistence(parent);
	}
	
	public IPersistenceUnit createPersistenceUnit(IPersistence parent) {
		return new PersistenceUnit(parent);
	}
	
	public IMappingFileRef createMappingFileRef(IPersistenceUnit parent) {
		return new MappingFileRef(parent);
	}
	
	public IClassRef createClassRef(IPersistenceUnit parent) {
		return new ClassRef(parent);
	}
	
	public IJavaTypeMapping createJavaNullTypeMapping(IJavaPersistentType parent) {
		return new JavaNullTypeMapping(parent);
	}
	
	public IJavaPersistentType createJavaPersistentType(IClassRef parent) {
		return new JavaPersistentType(parent);
	}
	
	public IJavaEntity createJavaEntity(IJavaPersistentType parent) {
		return new JavaEntity(parent);
	}

}
