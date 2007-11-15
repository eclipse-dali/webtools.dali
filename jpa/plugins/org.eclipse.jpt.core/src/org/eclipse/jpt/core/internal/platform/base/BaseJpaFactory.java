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
import org.eclipse.jpt.core.internal.context.java.IJavaAttributeMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaBasicMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.java.IJavaEmbeddable;
import org.eclipse.jpt.core.internal.context.java.IJavaEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaEntity;
import org.eclipse.jpt.core.internal.context.java.IJavaIdMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.IJavaSecondaryTable;
import org.eclipse.jpt.core.internal.context.java.IJavaTable;
import org.eclipse.jpt.core.internal.context.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.internal.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.java.JavaEntity;
import org.eclipse.jpt.core.internal.context.java.JavaIdMapping;
import org.eclipse.jpt.core.internal.context.java.JavaNullAttributeMapping;
import org.eclipse.jpt.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.core.internal.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.JavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.internal.context.java.JavaTable;
import org.eclipse.jpt.core.internal.jdtutility.DefaultAnnotationEditFormatter;
import org.eclipse.jpt.core.internal.resource.java.JavaResourceModel;
import org.eclipse.jpt.core.internal.resource.orm.OrmResourceModel;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;

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
			throw new IllegalArgumentException("The file" + file + " is not on the project classpath");
		}
		IContentType contentType = this.contentType(file);
		if (contentType == null) {
			throw new IllegalArgumentException("The file" + file + " does not have a supported content type");
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
		return new PersistenceResourceModel(file);
	}
	
	protected IResourceModel buildOrmResourceModel(IFile file) {
		return new OrmResourceModel(file);
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
	
	
	public IJavaPersistentType createJavaPersistentType(IClassRef parent) {
		return new JavaPersistentType(parent);
	}
	
	public IJavaPersistentAttribute createJavaPersistentAttribute(IJavaPersistentType parent) {
		return new JavaPersistentAttribute(parent);
	}
	
	public IJavaTypeMapping createJavaNullTypeMapping(IJavaPersistentType parent) {
		return new JavaNullTypeMapping(parent);
	}
	
	public IJavaEntity createJavaEntity(IJavaPersistentType parent) {
		return new JavaEntity(parent);
	}

	public IJavaEmbeddable createJavaEmbeddable(IJavaPersistentType parent) {
		return new JavaEmbeddable(parent);
	}
	
	public IJavaTable createJavaTable(IJavaEntity parent) {
		return new JavaTable(parent);
	}
	
	public IJavaDiscriminatorColumn createJavaDiscriminatorColumn(IJavaEntity parent) {
		return new JavaDiscriminatorColumn(parent);
	}
	
	public IJavaSecondaryTable createJavaSecondaryTable(IJavaEntity parent) {
		return new JavaSecondaryTable(parent);
	}
	
	public IJavaBasicMapping createJavaBasicMapping(IJavaPersistentAttribute parent) {
		return new JavaBasicMapping(parent);
	}
	
	public IJavaEmbeddedMapping createJavaEmbeddedMapping(IJavaPersistentAttribute parent) {
		return new JavaEmbeddedMapping(parent);
	}
	
	public IJavaIdMapping createJavaIdMapping(IJavaPersistentAttribute parent) {
		return new JavaIdMapping(parent);
	}
	
	public IJavaAttributeMapping createJavaNullAttributeMapping(IJavaPersistentAttribute parent) {
		return new JavaNullAttributeMapping(parent);
	}
}
