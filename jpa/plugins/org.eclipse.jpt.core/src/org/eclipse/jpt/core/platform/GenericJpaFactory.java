/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.platform;

import java.io.IOException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.core.ContextModel;
import org.eclipse.jpt.core.JpaDataSource;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.ResourceModel;
import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.IBaseJpaContent;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaQuery;
import org.eclipse.jpt.core.context.java.JavaQueryHint;
import org.eclipse.jpt.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.core.context.java.JavaTable;
import org.eclipse.jpt.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmDiscriminatorColumn;
import org.eclipse.jpt.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmTable;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.internal.GenericJpaDataSource;
import org.eclipse.jpt.core.internal.GenericJpaFile;
import org.eclipse.jpt.core.internal.GenericJpaProject;
import org.eclipse.jpt.core.internal.context.BaseJpaContent;
import org.eclipse.jpt.core.internal.context.java.GenericJavaAssociationOverride;
import org.eclipse.jpt.core.internal.context.java.GenericJavaAttributeOverride;
import org.eclipse.jpt.core.internal.context.java.GenericJavaBasicMapping;
import org.eclipse.jpt.core.internal.context.java.GenericJavaColumn;
import org.eclipse.jpt.core.internal.context.java.GenericJavaDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.java.GenericJavaEmbeddable;
import org.eclipse.jpt.core.internal.context.java.GenericJavaEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.context.java.GenericJavaEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.java.GenericJavaEntity;
import org.eclipse.jpt.core.internal.context.java.GenericJavaGeneratedValue;
import org.eclipse.jpt.core.internal.context.java.GenericJavaIdMapping;
import org.eclipse.jpt.core.internal.context.java.GenericJavaJoinColumn;
import org.eclipse.jpt.core.internal.context.java.GenericJavaJoinTable;
import org.eclipse.jpt.core.internal.context.java.GenericJavaManyToManyMapping;
import org.eclipse.jpt.core.internal.context.java.GenericJavaManyToOneMapping;
import org.eclipse.jpt.core.internal.context.java.GenericJavaMappedSuperclass;
import org.eclipse.jpt.core.internal.context.java.GenericJavaNamedNativeQuery;
import org.eclipse.jpt.core.internal.context.java.GenericJavaNamedQuery;
import org.eclipse.jpt.core.internal.context.java.GenericJavaNullAttributeMapping;
import org.eclipse.jpt.core.internal.context.java.GenericJavaOneToManyMapping;
import org.eclipse.jpt.core.internal.context.java.GenericJavaOneToOneMapping;
import org.eclipse.jpt.core.internal.context.java.GenericJavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.GenericJavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.GenericJavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.java.GenericJavaQueryHint;
import org.eclipse.jpt.core.internal.context.java.GenericJavaSecondaryTable;
import org.eclipse.jpt.core.internal.context.java.GenericJavaSequenceGenerator;
import org.eclipse.jpt.core.internal.context.java.GenericJavaTable;
import org.eclipse.jpt.core.internal.context.java.GenericJavaTableGenerator;
import org.eclipse.jpt.core.internal.context.java.GenericJavaTransientMapping;
import org.eclipse.jpt.core.internal.context.java.GenericJavaVersionMapping;
import org.eclipse.jpt.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmRelationshipMapping;
import org.eclipse.jpt.core.internal.context.orm.GenericEntityMappings;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmAssociationOverride;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmAttributeOverride;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmColumn;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEmbeddable;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEntity;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmGeneratedValue;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmJoinColumn;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmJoinTable;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmMappedSuperclass;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmSecondaryTable;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmSequenceGenerator;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmTable;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmTableGenerator;
import org.eclipse.jpt.core.internal.context.orm.GenericPersistenceUnitDefaults;
import org.eclipse.jpt.core.internal.context.orm.GenericPersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.context.orm.OrmXmlImpl;
import org.eclipse.jpt.core.internal.context.persistence.GeenericPersistenceXml;
import org.eclipse.jpt.core.internal.context.persistence.GenericClassRef;
import org.eclipse.jpt.core.internal.context.persistence.GenericMappingFileRef;
import org.eclipse.jpt.core.internal.context.persistence.GenericPersistence;
import org.eclipse.jpt.core.internal.context.persistence.GenericPersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.GenericProperty;
import org.eclipse.jpt.core.internal.jdtutility.DefaultAnnotationEditFormatter;
import org.eclipse.jpt.core.internal.resource.java.JavaResourceModel;
import org.eclipse.jpt.core.resource.orm.OrmResourceModel;
import org.eclipse.jpt.core.resource.orm.XmlRelationshipMapping;
import org.eclipse.jpt.core.resource.persistence.PersistenceResourceModel;

public class GenericJpaFactory implements JpaFactory
{
	protected GenericJpaFactory() {
		super();
	}
	
	
	// **************** Core objects ******************************************
	
	public JpaProject buildJpaProject(JpaProject.Config config) throws CoreException {
		return new GenericJpaProject(config);
	}
	
	public JpaDataSource buildJpaDataSource(JpaProject jpaProject, String connectionProfileName) {
		return new GenericJpaDataSource(jpaProject, connectionProfileName);
	}
	
	public JpaFile buildJpaFile(JpaProject jpaProject, IFile file, ResourceModel resourceModel) {
		return new GenericJpaFile(jpaProject, file, resourceModel);
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
	
	public ResourceModel buildResourceModel(JpaProject jpaProject, IFile file) {
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
	
	protected ResourceModel buildResourceModel(JpaProject jpaProject, IFile file, String contentTypeId) {
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
	
	protected ResourceModel buildJavaResourceModel(JpaProject jpaProject, IFile file) {
		return new JavaResourceModel(
				file, jpaProject.jpaPlatform().annotationProvider(), 
				jpaProject.modifySharedDocumentCommandExecutorProvider(),
				DefaultAnnotationEditFormatter.instance());
	}
	
	protected ResourceModel buildPersistenceResourceModel(IFile file) {
		return new PersistenceResourceModel(file);
	}
	
	protected ResourceModel buildOrmResourceModel(IFile file) {
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
	
	public ContextModel buildContextModel(JpaProject parent) {
		return new BaseJpaContent(parent);
	}
	
	public PersistenceXml buildPersistenceXml(IBaseJpaContent parent) {
		return new GeenericPersistenceXml(parent);
	}
	
	public OrmXml buildOrmXml(MappingFileRef parent) {
		return new OrmXmlImpl(parent);
	}
	
	public EntityMappings buildEntityMappings(OrmXml parent) {
		return new GenericEntityMappings(parent);
	}
	
	public PersistenceUnitMetadata buildPersistenceUnitMetadata(EntityMappings parent) {
		return new GenericPersistenceUnitMetadata(parent);
	}
	
	public PersistenceUnitDefaults buildPersistenceUnitDefaults(PersistenceUnitMetadata parent) {
		return new GenericPersistenceUnitDefaults(parent);
	}
	
	public Persistence buildPersistence(PersistenceXml parent) {
		return new GenericPersistence(parent);
	}
	
	public PersistenceUnit buildPersistenceUnit(Persistence parent) {
		return new GenericPersistenceUnit(parent);
	}
	
	public MappingFileRef buildMappingFileRef(PersistenceUnit parent) {
		return new GenericMappingFileRef(parent);
	}
	
	public ClassRef buildClassRef(PersistenceUnit parent) {
		return new GenericClassRef(parent);
	}
	
	public Property buildProperty(PersistenceUnit parent) {
		return new GenericProperty(parent);
	}
	
	public JavaPersistentType buildJavaPersistentType(JpaContextNode parent) {
		return new GenericJavaPersistentType(parent);
	}
	
	public JavaPersistentAttribute buildJavaPersistentAttribute(JavaPersistentType parent) {
		return new GenericJavaPersistentAttribute(parent);
	}
	
	public JavaTypeMapping buildJavaNullTypeMapping(JavaPersistentType parent) {
		return new JavaNullTypeMapping(parent);
	}
	
	public JavaEntity buildJavaEntity(JavaPersistentType parent) {
		return new GenericJavaEntity(parent);
	}

	public JavaMappedSuperclass buildJavaMappedSuperclass(JavaPersistentType parent) {
		return new GenericJavaMappedSuperclass(parent);
	}

	public JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent) {
		return new GenericJavaEmbeddable(parent);
	}
	
	public JavaTable buildJavaTable(JavaEntity parent) {
		return new GenericJavaTable(parent);
	}
	
	public JavaColumn buildJavaColumn(JavaJpaContextNode parent, JavaColumn.Owner owner) {
		return new GenericJavaColumn(parent, owner);
	}
	
	public JavaDiscriminatorColumn buildJavaDiscriminatorColumn(JavaEntity parent, NamedColumn.Owner owner) {
		return new GenericJavaDiscriminatorColumn(parent, owner);
	}
	
	public JavaJoinColumn buildJavaJoinColumn(JavaJpaContextNode parent, JoinColumn.Owner owner) {
		return new GenericJavaJoinColumn(parent, owner);
	}
	
	public JavaJoinTable buildJavaJoinTable(JavaRelationshipMapping parent) {
		return new GenericJavaJoinTable(parent);
	}
	
	public JavaSecondaryTable buildJavaSecondaryTable(JavaEntity parent) {
		return new GenericJavaSecondaryTable(parent);
	}
	
	public JavaBasicMapping buildJavaBasicMapping(JavaPersistentAttribute parent) {
		return new GenericJavaBasicMapping(parent);
	}
	
	public JavaEmbeddedIdMapping buildJavaEmbeddedIdMapping(JavaPersistentAttribute parent) {
		return new GenericJavaEmbeddedIdMapping(parent);
	}
	
	public JavaEmbeddedMapping buildJavaEmbeddedMapping(JavaPersistentAttribute parent) {
		return new GenericJavaEmbeddedMapping(parent);
	}
	
	public JavaIdMapping buildJavaIdMapping(JavaPersistentAttribute parent) {
		return new GenericJavaIdMapping(parent);
	}
	
	public JavaManyToManyMapping buildJavaManyToManyMapping(JavaPersistentAttribute parent) {
		return new GenericJavaManyToManyMapping(parent);
	}
	
	public JavaManyToOneMapping buildJavaManyToOneMapping(JavaPersistentAttribute parent) {
		return new GenericJavaManyToOneMapping(parent);
	}
	
	public JavaOneToManyMapping buildJavaOneToManyMapping(JavaPersistentAttribute parent) {
		return new GenericJavaOneToManyMapping(parent);
	}
	
	public JavaOneToOneMapping buildJavaOneToOneMapping(JavaPersistentAttribute parent) {
		return new GenericJavaOneToOneMapping(parent);
	}
	
	public JavaTransientMapping buildJavaTransientMapping(JavaPersistentAttribute parent) {
		return new GenericJavaTransientMapping(parent);
	}
	
	public JavaVersionMapping buildJavaVersionMapping(JavaPersistentAttribute parent) {
		return new GenericJavaVersionMapping(parent);
	}
	
	public JavaAttributeMapping buildJavaNullAttributeMapping(JavaPersistentAttribute parent) {
		return new GenericJavaNullAttributeMapping(parent);
	}
	
	public JavaSequenceGenerator buildJavaSequenceGenerator(JavaJpaContextNode parent) {
		return new GenericJavaSequenceGenerator(parent);
	}
	
	public JavaTableGenerator buildJavaTableGenerator(JavaJpaContextNode parent) {
		return new GenericJavaTableGenerator(parent);
	}
	
	public JavaGeneratedValue buildJavaGeneratedValue(JavaAttributeMapping parent) {
		return new GenericJavaGeneratedValue(parent);
	}
	
	public JavaPrimaryKeyJoinColumn buildJavaPrimaryKeyJoinColumn(JavaJpaContextNode parent, AbstractJoinColumn.Owner owner) {
		return new GenericJavaPrimaryKeyJoinColumn(parent, owner);
	}
	
	public JavaAttributeOverride buildJavaAttributeOverride(JavaJpaContextNode parent, AttributeOverride.Owner owner) {
		return new GenericJavaAttributeOverride(parent, owner);
	}
	
	public JavaAssociationOverride buildJavaAssociationOverride(JavaJpaContextNode parent, AssociationOverride.Owner owner) {
		return new GenericJavaAssociationOverride(parent, owner);
	}
	
	public JavaNamedQuery buildJavaNamedQuery(JavaJpaContextNode parent) {
		return new GenericJavaNamedQuery(parent);
	}
	
	public JavaNamedNativeQuery buildJavaNamedNativeQuery(JavaJpaContextNode parent) {
		return new GenericJavaNamedNativeQuery(parent);
	}
	
	public JavaQueryHint buildJavaQueryHint(JavaQuery<?> parent) {
		return new GenericJavaQueryHint(parent);
	}
	
	public OrmPersistentType buildOrmPersistentType(EntityMappings parent, String mappingKey) {
		return new GenericOrmPersistentType(parent, mappingKey);
	}
	
	public GenericOrmEntity buildXmlEntity(OrmPersistentType parent) {
		return new GenericOrmEntity(parent);
	}
	
	public GenericOrmMappedSuperclass buildXmlMappedSuperclass(OrmPersistentType parent) {
		return new GenericOrmMappedSuperclass(parent);
	}
	
	public GenericOrmEmbeddable buildXmlEmbeddable(OrmPersistentType parent) {
		return new GenericOrmEmbeddable(parent);
	}
	
	public OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, String mappingKey) {
		return new GenericOrmPersistentAttribute(parent, mappingKey);
	}
	
	public OrmTable buildOrmTable(GenericOrmEntity parent) {
		return new GenericOrmTable(parent);
	}
	
	public OrmSecondaryTable buildOrmSecondaryTable(GenericOrmEntity parent) {
		return new GenericOrmSecondaryTable(parent);
	}
	
	public OrmPrimaryKeyJoinColumn buildOrmPrimaryKeyJoinColumn(JpaContextNode parent, AbstractJoinColumn.Owner owner) {
		return new GenericOrmPrimaryKeyJoinColumn(parent, owner);
	}
	
	public OrmJoinTable buildOrmJoinTable(AbstractOrmRelationshipMapping<? extends XmlRelationshipMapping> parent) {
		return new GenericOrmJoinTable(parent);
	}
	
	public OrmJoinColumn buildOrmJoinColumn(JpaContextNode parent, JoinColumn.Owner owner) {
		return new GenericOrmJoinColumn(parent, owner);
	}
	
	public OrmAttributeOverride buildOrmAttributeOverride(JpaContextNode parent, AttributeOverride.Owner owner) {
		return new GenericOrmAttributeOverride(parent, owner);
	}
	
	public OrmAssociationOverride buildOrmAssociationOverride(JpaContextNode parent, AssociationOverride.Owner owner) {
		return new GenericOrmAssociationOverride(parent, owner);
	}
	
	public OrmDiscriminatorColumn buildOrmDiscriminatorColumn(GenericOrmEntity parent, NamedColumn.Owner owner) {
		return new GenericOrmDiscriminatorColumn(parent, owner);
	}
	
	public OrmColumn buildOrmColumn(JpaContextNode parent, OrmColumn.Owner owner) {
		return new GenericOrmColumn(parent, owner);
	}
	
	public OrmGeneratedValue buildOrmGeneratedValue(JpaContextNode parent) {
		return new GenericOrmGeneratedValue(parent);
	}
	
	public OrmSequenceGenerator buildOrmSequenceGenerator(JpaContextNode parent) {
		return new GenericOrmSequenceGenerator(parent);
	}
	
	public OrmTableGenerator buildOrmTableGenerator(JpaContextNode parent) {
		return new GenericOrmTableGenerator(parent);
	}
}
