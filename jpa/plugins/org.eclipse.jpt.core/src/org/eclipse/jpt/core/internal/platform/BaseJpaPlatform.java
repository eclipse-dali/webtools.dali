/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.io.IOException;
import java.util.Collection;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.IJpaFileContentProvider;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.IResourceModel;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.resource.java.JavaResourceModel;
import org.eclipse.jpt.core.internal.resource.orm.OrmResourceModel;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;

public abstract class BaseJpaPlatform implements IJpaPlatform
{
	public static final String DEFAULT_TABLE_SCHEMA_KEY = "table.schema";
	public static final String DEFAULT_TABLE_CATALOG_KEY = "table.catalog";
	public static final String DEFAULT_TABLE_NAME_KEY = "table.name";
	public static final String DEFAULT_ACCESS_KEY = "access";
	public static final String DEFAULT_ENTITY_NAME_KEY = "entity.name";
	public static final String DEFAULT_COLUMN_TABLE_KEY = "column.table";
	public static final String DEFAULT_COLUMN_NAME_KEY = "column.name";
	public static final String DEFAULT_JOIN_TABLE_NAME_KEY = "joinTable.name";
	public static final String DEFAULT_TARGET_ENTITY_KEY = "oneToMany.targetEntity";
	public static final String DEFAULT_JOIN_COLUMN_TABLE_KEY = "joinColumn.table";
	public static final String DEFAULT_JOIN_COLUMN_NAME_KEY = "joinColumn.name";
	public static final String DEFAULT_JOIN_COLUMN_REFERENCED_COLUMN_NAME_KEY = "joinColumn.referencedColumnName";
	public static final String DEFAULT_TABLE_GENERATOR_SCHEMA_KEY = "tableGenerator.schema";
	
	private String id;
	
	protected IJpaProject project;
	
	private Collection<IJpaFileContentProvider> contentProviders;
	
//	private Collection<IJavaAttributeMappingProvider> javaAttributeMappingProviders;
//	
//	private List<IDefaultJavaAttributeMappingProvider> defaultJavaAttributeMappingProviders;
//		
//	private Collection<IJavaTypeMappingProvider> javaTypeMappingProviders;
//	
//	private IJpaFactory jpaFactory;
//	
//	private IContext context;
	
	protected BaseJpaPlatform() {
		super();
//		this.jpaFactory = createJpaFactory();
	}
	
//	protected abstract IJpaFactory createJpaFactory();
	
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

//	public IJpaFactory getJpaFactory() {
//		return this.jpaFactory;
//	}
	
	// ********** Persistence Unit ********************************************

//	public boolean containsPersistenceUnitNamed(String name) {
//		return ((BaseJpaProjectContext)this.context).containsPersistenceUnitNamed(name);
//	}
//	
//	public PersistenceUnit persistenceUnitNamed(String name) {
//		return ((BaseJpaProjectContext)this.context).persistenceUnitNamed(name);
//	}
//	
//	public Iterator<PersistenceUnit> persistenceUnits() {
//		return ((BaseJpaProjectContext)this.context).persistenceUnits();
//	}
//	
//	public int persistenceUnitSize() {
//		return ((BaseJpaProjectContext)this.context).persistenceUnitContextsSize();
//	}

	
	// ********** Persistent Types ********************************************

//	public Iterator<IPersistentType> persistentTypes(String persistenceUnitName) {
//		PersistenceUnitContext puContext = 
//			((BaseJpaProjectContext) this.context).persistenceUnitContext(persistenceUnitName);
//		if (puContext == null) {
//			return EmptyIterator.instance();
//		}
//		return puContext.persistentTypes();
//		//hmm, compiler error with ternary operator
////		return (puContext == null) ? EmptyIterator.instance() : puContext.persistentTypes();
//	}
	
	
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
	
	
	// ************************************************************************
	
	//attempting to get the contentType based on the file contents.
	//have to check the file contents instead of just the file name
	//because for xml we base it on the rootElement name
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
	
//	public Iterator<IJpaFileContentProvider> jpaFileContentProviders() {
//		if (this.contentProviders == null) {
//			this.contentProviders = new ArrayList<IJpaFileContentProvider>();
//			addJpaFileContentProvidersTo(this.contentProviders);
//		}
//		return new CloneIterator<IJpaFileContentProvider>(this.contentProviders);
//	}
//	
//	public IJpaFileContentProvider fileContentProvider(String contentTypeId) {
//		for (Iterator<IJpaFileContentProvider> i = this.jpaFileContentProviders(); i.hasNext(); ) {
//			IJpaFileContentProvider provider = i.next();
//			if (provider.contentType().equals(contentTypeId)) {
//				return provider;
//			}
//		}
//		return null;
//	}
// 
//	/**
//	 * Override this to specify more or different JPA file content providers.
//	 * The default includes the JPA spec-defined content providers of
//	 * persistence.xml, orm.xml, and java files.
//	 */
//	protected void addJpaFileContentProvidersTo(Collection<IJpaFileContentProvider> providers) {
//		providers.add(JavaJpaFileContentProvider.instance());
//	}
//	
//	public Iterator<IJavaTypeMappingProvider> javaTypeMappingProviders() {
//		if (this.javaTypeMappingProviders == null) {
//			this.javaTypeMappingProviders = new ArrayList<IJavaTypeMappingProvider>();
//			this.addJavaTypeMappingProvidersTo(this.javaTypeMappingProviders);
//		}
//		return new CloneIterator<IJavaTypeMappingProvider>(this.javaTypeMappingProviders);
//	}
//
//	/**
//	 * Override this to specify more or different type mapping providers.
//	 * The default includes the JPA spec-defined type mappings of 
//	 * Entity, MappedSuperclass, and Embeddable
//	 */
//	protected void addJavaTypeMappingProvidersTo(Collection<IJavaTypeMappingProvider> providers) {
//		providers.add(JavaEntityProvider.instance());
//		providers.add(JavaMappedSuperclassProvider.instance());
//		providers.add(JavaEmbeddableProvider.instance());
//	}
//
//	public IJavaTypeMappingProvider javaTypeMappingProvider(String typeMappingKey) {
//		for (Iterator<IJavaTypeMappingProvider> i = this.javaTypeMappingProviders(); i.hasNext(); ) {
//			IJavaTypeMappingProvider provider = i.next();
//			if (provider.key() == typeMappingKey) {
//				return provider;
//			}
//		}
//		return null;
//	}
//	
//	public Iterator<IJavaAttributeMappingProvider> javaAttributeMappingProviders() {
//		if (this.javaAttributeMappingProviders == null) {
//			this.javaAttributeMappingProviders = new ArrayList<IJavaAttributeMappingProvider>();
//			this.addJavaAttributeMappingProvidersTo(this.javaAttributeMappingProviders);
//		}
//		return new CloneIterator<IJavaAttributeMappingProvider>(this.javaAttributeMappingProviders);
//	}
//	
//	/**
//	 * Override this to specify more or different attribute mapping providers.
//	 * The default includes the JPA spec-defined attribute mappings of 
//	 * Basic, Id, Transient OneToOne, OneToMany, ManyToOne, ManyToMany, Embeddable, EmbeddedId, Version.
//	 */
//	protected void addJavaAttributeMappingProvidersTo(Collection<IJavaAttributeMappingProvider> providers) {
//		providers.add(JavaBasicProvider.instance());
//		providers.add(JavaIdProvider.instance());
//		providers.add(JavaTransientProvider.instance());
//		providers.add(JavaOneToManyProvider.instance());
//		providers.add(JavaManyToOneProvider.instance());
//		providers.add(JavaManyToManyProvider.instance());
//		providers.add(JavaOneToOneProvider.instance());
//		providers.add(JavaEmbeddedProvider.instance());
//		providers.add(JavaEmbeddedIdProvider.instance());
//		providers.add(JavaVersionProvider.instance());
//	}
//	
//	/**
//	 * throw an exception if the provider is not found
//	 */
//	public IJavaAttributeMappingProvider javaAttributeMappingProvider(String attributeMappingKey) {
//		for (Iterator<IJavaAttributeMappingProvider> i = this.javaAttributeMappingProviders(); i.hasNext(); ) {
//			IJavaAttributeMappingProvider provider = i.next();
//			if (provider.key() == attributeMappingKey) {
//				return provider;
//			}
//		}
//		throw new IllegalArgumentException("Unsupported java attribute mapping key: " + attributeMappingKey);
//	}
//	
//	public ListIterator<IDefaultJavaAttributeMappingProvider> defaultJavaAttributeMappingProviders() {
//		if (this.defaultJavaAttributeMappingProviders == null) {
//			this.defaultJavaAttributeMappingProviders = new ArrayList<IDefaultJavaAttributeMappingProvider>();
//			this.addDefaultAttributeMappingProvidersTo(this.defaultJavaAttributeMappingProviders);
//		}
//		return new CloneListIterator<IDefaultJavaAttributeMappingProvider>(this.defaultJavaAttributeMappingProviders);
//	}
//	
//	/**
//	 * Override this to specify more or different default attribute mapping providers.
//	 * The default includes the JPA spec-defined attribute mappings of 
//	 * Embedded and Basic.
//	 */
//	protected void addDefaultAttributeMappingProvidersTo(List<IDefaultJavaAttributeMappingProvider> providers) {
//		providers.add(JavaEmbeddedProvider.instance()); //bug 190344 need to test default embedded before basic
//		providers.add(JavaBasicProvider.instance());
//	}
//
//	public IContext buildProjectContext() {
//		this.context = new BaseJpaProjectContext(getProject());
//		return this.context;
//	}
//	
//	public Iterator<IJpaFile> validPersistenceXmlFiles(){
//		return ((BaseJpaProjectContext)this.context).validPersistenceXmlFiles();
//	}
//	
//	public IContext buildJavaTypeContext(IContext parentContext, IJavaTypeMapping typeMapping) {
//		String key = typeMapping.getKey();
//		if (key == IMappingKeys.ENTITY_TYPE_MAPPING_KEY) {
//			return new JavaEntityContext(parentContext, (JavaEntity) typeMapping);
//		}
//		else if (key == IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY) {
//			return new JavaEmbeddableContext(parentContext, (JavaEmbeddable) typeMapping);
//		}
//		else if (key == IMappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY) {
//			return new JavaMappedSuperclassContext(parentContext, (JavaMappedSuperclass) typeMapping);
//		}
//		else if (key == null) {
//			return new JavaNullTypeMappingContext(parentContext, (JavaNullTypeMapping) typeMapping);
//		}
//		else {
//			throw new IllegalArgumentException(typeMapping.toString());
//		}
//	}
//	
//	public IContext buildJavaAttributeContext(IContext parentContext, IJavaAttributeMapping attributeMapping) {
//		String key = attributeMapping.getKey();
//		if (key == IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY) {
//			return new JavaBasicContext(parentContext, (JavaBasic) attributeMapping);
//		}
//		else if (key == IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY) {
//			return new JavaIdContext(parentContext, (JavaId) attributeMapping);
//		}
//		else if (key == IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY) {
//			return new JavaVersionContext(parentContext, (JavaVersion) attributeMapping);
//		}
//		else if (key == IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY) {
//			return new JavaEmbeddedContext(parentContext, (JavaEmbedded) attributeMapping);
//		}
//		else if (key == IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY) {
//			return new JavaEmbeddedIdContext(parentContext, (JavaEmbeddedId) attributeMapping);
//		}
//		else if (key == IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
//			return new JavaOneToOneContext(parentContext, (JavaOneToOne) attributeMapping);
//		}
//		else if (key == IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
//			return new JavaOneToManyContext(parentContext, (JavaOneToMany) attributeMapping);
//		}
//		else if (key == IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY) {
//			return new JavaManyToOneContext(parentContext, (JavaManyToOne) attributeMapping);
//		}
//		else if (key == IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY) {
//			return new JavaManyToManyContext(parentContext, (JavaManyToMany) attributeMapping);
//		}
//		else if (key == IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
//			return new JavaTransientContext(parentContext, (JavaTransient) attributeMapping);
//		}
//		else if (key == null) {
//			return new JavaNullAttributeMappingContext(parentContext, (JavaNullAttributeMapping) attributeMapping);
//		}
//		else {
//			throw new IllegalArgumentException(attributeMapping.toString());
//		}
//	}
//	
//	public void resynch(IContext contextHierarchy, IProgressMonitor monitor) {
//		((BaseJpaProjectContext) contextHierarchy).refreshDefaults(monitor);
//	}
//	
//	public void addToMessages(List<IMessage> messages) {
//		//I believe we need to be calling JpaProject.resynch() here.
//		//How can we handle this, we need to resynch and then wait until it is done
//		//resynching before calling this.  what happens if something changes out from
//		//under us while we are resynching??
//		BaseJpaProjectContext context = (BaseJpaProjectContext) buildProjectContext();
//		context.refreshDefaults(new NullProgressMonitor());
//		context.addToMessages(messages);
//	}
//	
//	public IGeneratorRepository generatorRepository(IPersistentType persistentType) {
//		return ((BaseJpaProjectContext) context).generatorRepository(persistentType);
//	}
}
