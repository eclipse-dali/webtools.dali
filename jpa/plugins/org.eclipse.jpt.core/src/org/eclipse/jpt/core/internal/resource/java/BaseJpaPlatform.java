/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.IResourceModel;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.resource.orm.OrmResourceModel;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;

public abstract class BaseJpaPlatform implements IJpaPlatform
{
	private String id;
	
	protected IJpaProject project;
	
	/**
	 * Ordered list of possible type mapping annotations.  Ordered because this
	 * is used to determine the mapping in the case where 2 mapping annotations exist
	 */
	private List<MappingAnnotationProvider> javaTypeMappingAnnotationProviders;
	
	private Collection<AnnotationProvider> javaTypeAnnotationProviders;
	
	/**
	 * Ordered list of possible attribute mapping annotations.  Ordered because this
	 * is used to determine the mapping in the case where 2 mapping annotations exist
	 */
	private List<MappingAnnotationProvider> javaAttributeMappingAnnotationProviders;
	
	private Collection<AnnotationProvider> javaAttributeAnnotationProviders;
	
	
	protected BaseJpaPlatform() {
		super();
	}
	
	
	public String getId() {
		return this.id;
	}
	
	/**
	 * *************
	 * * IMPORTANT *   For INTERNAL use only !!
	 * *************
	 * 
	 * @see IJpaPlatform#setId(String)
	 */
	public void setId(String theId) {
		this.id = theId;
	}
	
	public IJpaProject getProject() {
		return this.project;
	}
	
	public void setProject(IJpaProject jpaProject) {
		this.project = jpaProject;
	}
	
	
	// **************** Resource models ***************************************
	
	public IResourceModel buildResourceModel(IJpaFile jpaFile) {
		IFile file = jpaFile.getFile();
		if (! JavaCore.create(project.getProject()).isOnClasspath(file)) {
			return null;
		}
		IContentType contentType = this.contentType(file);
		if (contentType == null) {
			return null;
		}
		String contentTypeId = contentType.getId();
		return buildResourceModel(jpaFile, contentTypeId);
	}
	
	protected IResourceModel buildResourceModel(IJpaFile jpaFile, String contentTypeId) {
		if (JptCorePlugin.JAVA_CONTENT_TYPE.equals(contentTypeId)) {
			return buildJavaResourceModel(jpaFile);
		}
		else if (JptCorePlugin.PERSISTENCE_XML_CONTENT_TYPE.equals(contentTypeId)) {
			return buildPersistenceResourceModel(jpaFile);
		}
		else if (JptCorePlugin.ORM_XML_CONTENT_TYPE.equals(contentTypeId)) {
			return buildOrmResourceModel(jpaFile);
		}
		
		return null;
	}
	
	protected IResourceModel buildJavaResourceModel(IJpaFile jpaFile) {
		return new JavaResourceModel();
	}
	
	protected IResourceModel buildPersistenceResourceModel(IJpaFile jpaFile) {
		IFile resourceFile = jpaFile.getFile();
		PersistenceResourceModel resource = 
				(PersistenceResourceModel) WorkbenchResourceHelper.getResource(resourceFile, true);
		resource.accessForWrite();
		return resource;
	}
	
	protected IResourceModel buildOrmResourceModel(IJpaFile jpaFile) {
		IFile resourceFile = jpaFile.getFile();
		OrmResourceModel resource = 
				(OrmResourceModel) WorkbenchResourceHelper.getResource(resourceFile, true);
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
	
	
	// ************************************************************************
	
	public ListIterator<MappingAnnotationProvider> javaTypeMappingAnnotationProviders() {
		if (this.javaTypeMappingAnnotationProviders == null) {
			this.javaTypeMappingAnnotationProviders = new ArrayList<MappingAnnotationProvider>();
			this.addJavaTypeMappingAnnotationProvidersTo(this.javaTypeMappingAnnotationProviders);
		}
		return new CloneListIterator<MappingAnnotationProvider>(this.javaTypeMappingAnnotationProviders);
	}
	
	/**
	 * Override this to specify more or different type mapping providers.
	 * The default includes the JPA spec-defined type mappings of 
	 * Embeddable, Entity, MappedSuperclass
	 */
	protected void addJavaTypeMappingAnnotationProvidersTo(List<MappingAnnotationProvider> providers) {
		providers.add(JavaEmbeddableProvider.instance());
		providers.add(JavaEntityProvider.instance());
		providers.add(JavaMappedSuperclassProvider.instance());
	}
	
	public Iterator<AnnotationProvider> javaTypeAnnotationProviders() {
		if (this.javaTypeAnnotationProviders == null) {
			this.javaTypeAnnotationProviders = new ArrayList<AnnotationProvider>();
			this.addJavaTypeAnnotationProvidersTo(this.javaTypeAnnotationProviders);
		}
		return new CloneIterator<AnnotationProvider>(this.javaTypeAnnotationProviders);
	}
	
	/**
	 * Override this to specify more or different type annotation providers.
	 * The default includes the JPA spec-defined annotations.
	 */
	protected void addJavaTypeAnnotationProvidersTo(Collection<AnnotationProvider> providers) {
		providers.add(JavaTableProvider.instance());
		providers.add(JavaSecondaryTableProvider.instance());
		providers.add(JavaSecondaryTablesProvider.instance());
	}
	
	public ListIterator<MappingAnnotationProvider> javaAttributeMappingAnnotationProviders() {
		if (this.javaAttributeMappingAnnotationProviders == null) {
			this.javaAttributeMappingAnnotationProviders = new ArrayList<MappingAnnotationProvider>();
			this.addJavaAttributeMappingAnnotationProvidersTo(this.javaAttributeMappingAnnotationProviders);
		}
		return new CloneListIterator<MappingAnnotationProvider>(this.javaAttributeMappingAnnotationProviders);
	}
	
	/**
	 * Override this to specify more or different attribute mapping providers.
	 * The default includes the JPA spec-defined attribute mappings of 
	 * Basic, Id, Transient OneToOne, OneToMany, ManyToOne, ManyToMany, Embedded, EmbeddedId, Version.
	 */
	protected void addJavaAttributeMappingAnnotationProvidersTo(List<MappingAnnotationProvider> providers) {
		providers.add(JavaBasicProvider.instance());
		providers.add(JavaEmbeddedProvider.instance());
		providers.add(JavaEmbeddedIdProvider.instance());
		providers.add(JavaIdProvider.instance());
		providers.add(JavaManyToManyProvider.instance());
		providers.add(JavaManyToOneProvider.instance());
		providers.add(JavaOneToManyProvider.instance());
		providers.add(JavaOneToOneProvider.instance());
		providers.add(JavaTransientProvider.instance());
		providers.add(JavaVersionProvider.instance());
	}
	
	public Iterator<AnnotationProvider> javaAttributeAnnotationProviders() {
		if (this.javaAttributeAnnotationProviders == null) {
			this.javaAttributeAnnotationProviders = new ArrayList<AnnotationProvider>();
			this.addJavaAttributeAnnotationProvidersTo(this.javaAttributeAnnotationProviders);
		}
		return new CloneIterator<AnnotationProvider>(this.javaAttributeAnnotationProviders);
	}
	
	/**
	 * Override this to specify more or different attribute annotation providers.
	 * The default includes the JPA spec-defined annotations.
	 */
	protected void addJavaAttributeAnnotationProvidersTo(Collection<AnnotationProvider> providers) {
		providers.add(JavaColumnProvider.instance());
		providers.add(JavaGeneratedValueProvider.instance());
	}
}
