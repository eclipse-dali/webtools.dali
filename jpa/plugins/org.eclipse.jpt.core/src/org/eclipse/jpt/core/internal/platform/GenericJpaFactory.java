/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.JpaDataSource;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaBaseJoinColumn;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaEnumeratedConverter;
import org.eclipse.jpt.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaLobConverter;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaNamedColumn;
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
import org.eclipse.jpt.core.context.java.JavaTemporalConverter;
import org.eclipse.jpt.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmBaseJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmDiscriminatorColumn;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmNamedColumn;
import org.eclipse.jpt.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmQuery;
import org.eclipse.jpt.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmTable;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.internal.GenericJpaDataSource;
import org.eclipse.jpt.core.internal.GenericJpaProject;
import org.eclipse.jpt.core.internal.JavaJpaFile;
import org.eclipse.jpt.core.internal.XmlJpaFile;
import org.eclipse.jpt.core.internal.context.GenericRootContextNode;
import org.eclipse.jpt.core.internal.context.java.GenericJavaAssociationOverride;
import org.eclipse.jpt.core.internal.context.java.GenericJavaAttributeOverride;
import org.eclipse.jpt.core.internal.context.java.GenericJavaBasicMapping;
import org.eclipse.jpt.core.internal.context.java.GenericJavaColumn;
import org.eclipse.jpt.core.internal.context.java.GenericJavaDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.java.GenericJavaEmbeddable;
import org.eclipse.jpt.core.internal.context.java.GenericJavaEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.context.java.GenericJavaEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.java.GenericJavaEntity;
import org.eclipse.jpt.core.internal.context.java.GenericJavaEnumeratedConverter;
import org.eclipse.jpt.core.internal.context.java.GenericJavaGeneratedValue;
import org.eclipse.jpt.core.internal.context.java.GenericJavaIdMapping;
import org.eclipse.jpt.core.internal.context.java.GenericJavaJoinColumn;
import org.eclipse.jpt.core.internal.context.java.GenericJavaJoinTable;
import org.eclipse.jpt.core.internal.context.java.GenericJavaLobConverter;
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
import org.eclipse.jpt.core.internal.context.java.GenericJavaTemporalConverter;
import org.eclipse.jpt.core.internal.context.java.GenericJavaTransientMapping;
import org.eclipse.jpt.core.internal.context.java.GenericJavaUniqueConstraint;
import org.eclipse.jpt.core.internal.context.java.GenericJavaVersionMapping;
import org.eclipse.jpt.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.GenericEntityMappings;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmAssociationOverride;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmAttributeOverride;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmBasicMapping;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmColumn;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEmbeddable;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEntity;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmGeneratedValue;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmIdMapping;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmJoinColumn;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmJoinTable;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmManyToManyMapping;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmManyToOneMapping;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmMappedSuperclass;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmNamedNativeQuery;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmNamedQuery;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmNullAttributeMapping;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmOneToManyMapping;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmOneToOneMapping;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmQueryHint;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmSecondaryTable;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmSequenceGenerator;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmTable;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmTableGenerator;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmTransientMapping;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmUniqueConstraint;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmVersionMapping;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmXml;
import org.eclipse.jpt.core.internal.context.orm.GenericPersistenceUnitDefaults;
import org.eclipse.jpt.core.internal.context.orm.GenericPersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlBasic;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlEmbedded;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlEmbeddedId;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlId;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlManyToMany;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlManyToOne;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlNullAttributeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlOneToMany;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlOneToOne;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlTransient;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlVersion;
import org.eclipse.jpt.core.internal.context.persistence.GenericClassRef;
import org.eclipse.jpt.core.internal.context.persistence.GenericMappingFileRef;
import org.eclipse.jpt.core.internal.context.persistence.GenericPersistence;
import org.eclipse.jpt.core.internal.context.persistence.GenericPersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.GenericPersistenceXml;
import org.eclipse.jpt.core.internal.context.persistence.GenericProperty;
import org.eclipse.jpt.core.internal.resource.orm.OrmResourceModelProvider;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModelProvider;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.OrmResource;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlQueryHint;
import org.eclipse.jpt.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTransient;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.core.resource.orm.XmlVersion;
import org.eclipse.jpt.core.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.persistence.XmlProperty;

/**
 * Central class that allows extenders to easily replace implementations of
 * various Dali interfaces.
 */
public class GenericJpaFactory
	implements JpaFactory
{
	public GenericJpaFactory() {
		super();
	}
	
	
	// ********** Core Model **********
	
	public JpaProject buildJpaProject(JpaProject.Config config) throws CoreException {
		return new GenericJpaProject(config);
	}
	
	public JpaDataSource buildJpaDataSource(JpaProject jpaProject, String connectionProfileName) {
		return new GenericJpaDataSource(jpaProject, connectionProfileName);
	}
	
	public JpaFile buildJavaJpaFile(JpaProject jpaProject, IFile file, String resourceType) {
		return new JavaJpaFile(jpaProject, file, resourceType);
	}
	
	public JpaFile buildOrmJpaFile(JpaProject jpaProject, IFile file, String resourceType) {
		return new XmlJpaFile(jpaProject, file, resourceType, this.buildOrmResource(file));
	}

	protected JpaXmlResource buildOrmResource(IFile file) {
		return OrmResourceModelProvider.getModelProvider(file).getResource();
	}

	public JpaFile buildPersistenceJpaFile(JpaProject jpaProject, IFile file, String resourceType) {
		return new XmlJpaFile(jpaProject, file, resourceType, this.buildPersistenceResource(file));
	}
	
	protected JpaXmlResource buildPersistenceResource(IFile file) {
		return PersistenceResourceModelProvider.getModelProvider(file).getResource();
	}


	// ********** Context Nodes **********
	
	public JpaRootContextNode buildRootContextNode(JpaProject parent) {
		return new GenericRootContextNode(parent);
	}
	
	public MappingFile buildMappingFile(MappingFileRef parent, OrmResource resource) {
		return this.buildOrmXml(parent, resource);
	}
	

	// ********** Persistence Context Model **********
	
	public PersistenceXml buildPersistenceXml(JpaRootContextNode parent, PersistenceResource persistenceResource) {
		return new GenericPersistenceXml(parent, persistenceResource);
	}
	
	public Persistence buildPersistence(PersistenceXml parent, XmlPersistence xmlPersistence) {
		return new GenericPersistence(parent, xmlPersistence);
	}
	
	public PersistenceUnit buildPersistenceUnit(Persistence parent, XmlPersistenceUnit persistenceUnit) {
		return new GenericPersistenceUnit(parent, persistenceUnit);
	}
	
	public MappingFileRef buildMappingFileRef(PersistenceUnit parent, XmlMappingFileRef xmlMappingFileRef) {
		return new GenericMappingFileRef(parent, xmlMappingFileRef);
	}
	
	public ClassRef buildClassRef(PersistenceUnit parent, XmlJavaClassRef classRef) {
		return new GenericClassRef(parent, classRef);
	}
	
	public ClassRef buildClassRef(PersistenceUnit parent, String className) {
		return new GenericClassRef(parent, className);
	}
	
	public Property buildProperty(PersistenceUnit parent, XmlProperty xmlProperty) {
		return new GenericProperty(parent, xmlProperty);
	}
	

	// ********** ORM Context Model **********
	
	public OrmXml buildOrmXml(MappingFileRef parent, OrmResource ormResource) {
		return new GenericOrmXml(parent, ormResource);
	}
	
	public EntityMappings buildEntityMappings(OrmXml parent, XmlEntityMappings xmlEntityMappings) {
		return new GenericEntityMappings(parent, xmlEntityMappings);
	}
	
	public PersistenceUnitMetadata buildPersistenceUnitMetadata(EntityMappings parent, XmlEntityMappings xmlEntityMappings) {
		return new GenericPersistenceUnitMetadata(parent, xmlEntityMappings);
	}
	
	public OrmPersistenceUnitDefaults buildPersistenceUnitDefaults(PersistenceUnitMetadata parent, XmlEntityMappings xmlEntityMappings) {
		return new GenericPersistenceUnitDefaults(parent, xmlEntityMappings);
	}

	public OrmPersistentType buildOrmPersistentType(EntityMappings parent, String mappingKey) {
		return new GenericOrmPersistentType(parent, mappingKey);
	}
	
	public OrmEntity buildOrmEntity(OrmPersistentType parent) {
		return new GenericOrmEntity(parent);
	}
	
	public OrmMappedSuperclass buildOrmMappedSuperclass(OrmPersistentType parent) {
		return new GenericOrmMappedSuperclass(parent);
	}
	
	public OrmEmbeddable buildOrmEmbeddable(OrmPersistentType parent) {
		return new GenericOrmEmbeddable(parent);
	}
	
	public OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, String mappingKey) {
		return new GenericOrmPersistentAttribute(parent, mappingKey);
	}
	
	public OrmTable buildOrmTable(OrmEntity parent) {
		return new GenericOrmTable(parent);
	}
	
	public OrmSecondaryTable buildOrmSecondaryTable(OrmEntity parent, XmlSecondaryTable xmlSecondaryTable) {
		return new GenericOrmSecondaryTable(parent, xmlSecondaryTable);
	}
	
	public OrmPrimaryKeyJoinColumn buildOrmPrimaryKeyJoinColumn(XmlContextNode parent, OrmBaseJoinColumn.Owner owner, XmlPrimaryKeyJoinColumn resourcePkJoinColumn) {
		return new GenericOrmPrimaryKeyJoinColumn(parent, owner, resourcePkJoinColumn);
	}
	
	public OrmJoinTable buildOrmJoinTable(OrmRelationshipMapping parent) {
		return new GenericOrmJoinTable(parent);
	}
	
	public OrmJoinColumn buildOrmJoinColumn(XmlContextNode parent, OrmJoinColumn.Owner owner, XmlJoinColumn resourceJoinColumn) {
		return new GenericOrmJoinColumn(parent, owner, resourceJoinColumn);
	}
	
	public OrmAttributeOverride buildOrmAttributeOverride(XmlContextNode parent, AttributeOverride.Owner owner, XmlAttributeOverride xmlAttributeOverride) {
		return new GenericOrmAttributeOverride(parent, owner, xmlAttributeOverride);
	}
	
	public OrmAssociationOverride buildOrmAssociationOverride(XmlContextNode parent, AssociationOverride.Owner owner, XmlAssociationOverride xmlAssociationOverride) {
		return new GenericOrmAssociationOverride(parent, owner, xmlAssociationOverride);
	}
	
	public OrmDiscriminatorColumn buildOrmDiscriminatorColumn(OrmEntity parent, OrmNamedColumn.Owner owner) {
		return new GenericOrmDiscriminatorColumn(parent, owner);
	}
	
	public OrmColumn buildOrmColumn(XmlContextNode parent, OrmColumn.Owner owner) {
		return new GenericOrmColumn(parent, owner);
	}
	
	public OrmGeneratedValue buildOrmGeneratedValue(XmlContextNode parent, XmlGeneratedValue resourceGeneratedValue) {
		return new GenericOrmGeneratedValue(parent, resourceGeneratedValue);
	}
	
	public OrmSequenceGenerator buildOrmSequenceGenerator(XmlContextNode parent, XmlSequenceGenerator resourceSequenceGenerator) {
		return new GenericOrmSequenceGenerator(parent, resourceSequenceGenerator);
	}
	
	public OrmTableGenerator buildOrmTableGenerator(XmlContextNode parent, XmlTableGenerator resourceTableGenerator) {
		return new GenericOrmTableGenerator(parent, resourceTableGenerator);
	}
	
	public OrmNamedNativeQuery buildOrmNamedNativeQuery(XmlContextNode parent, XmlNamedNativeQuery resourceNamedNativeQuery) {
		return new GenericOrmNamedNativeQuery(parent, resourceNamedNativeQuery);
	}
	
	public OrmNamedQuery buildOrmNamedQuery(XmlContextNode parent, XmlNamedQuery resourceNamedQuery) {
		return new GenericOrmNamedQuery(parent, resourceNamedQuery);
	}
	
	public OrmQueryHint buildOrmQueryHint(OrmQuery parent, XmlQueryHint resourceQueryHint) {
		return new GenericOrmQueryHint(parent, resourceQueryHint);
	}
	
	public OrmBasicMapping buildOrmBasicMapping(OrmPersistentAttribute parent) {
		return new GenericOrmBasicMapping(parent);
	}
	
	public OrmEmbeddedMapping buildOrmEmbeddedMapping(OrmPersistentAttribute parent) {
		return new GenericOrmEmbeddedMapping(parent);
	}
	
	public OrmEmbeddedIdMapping buildOrmEmbeddedIdMapping(OrmPersistentAttribute parent) {
		return new GenericOrmEmbeddedIdMapping(parent);
	}
	
	public OrmIdMapping buildOrmIdMapping(OrmPersistentAttribute parent) {
		return new GenericOrmIdMapping(parent);
	}
	
	public OrmManyToManyMapping buildOrmManyToManyMapping(OrmPersistentAttribute parent) {
		return new GenericOrmManyToManyMapping(parent);
	}
	
	public OrmManyToOneMapping buildOrmManyToOneMapping(OrmPersistentAttribute parent) {
		return new GenericOrmManyToOneMapping(parent);
	}
	
	public OrmOneToManyMapping buildOrmOneToManyMapping(OrmPersistentAttribute parent) {
		return new GenericOrmOneToManyMapping(parent);
	}
	
	public OrmOneToOneMapping buildOrmOneToOneMapping(OrmPersistentAttribute parent) {
		return new GenericOrmOneToOneMapping(parent);
	}
	
	public OrmTransientMapping buildOrmTransientMapping(OrmPersistentAttribute parent) {
		return new GenericOrmTransientMapping(parent);
	}
	
	public OrmVersionMapping buildOrmVersionMapping(OrmPersistentAttribute parent) {
		return new GenericOrmVersionMapping(parent);
	}
	
	public OrmAttributeMapping buildOrmNullAttributeMapping(OrmPersistentAttribute parent) {
		return new GenericOrmNullAttributeMapping(parent);
	}
	
	public OrmUniqueConstraint buildOrmUniqueConstraint(XmlContextNode parent, UniqueConstraint.Owner owner, XmlUniqueConstraint resourceUniqueConstraint) {
		return new GenericOrmUniqueConstraint(parent, owner, resourceUniqueConstraint);
	}
	

	// ********** ORM Virtual Resource Model **********

	public XmlBasic buildVirtualXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		return new VirtualXmlBasic(ormTypeMapping, javaBasicMapping);
	}

	public XmlEmbeddedId buildVirtualXmlEmbeddedId(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		return new VirtualXmlEmbeddedId(ormTypeMapping, javaEmbeddedIdMapping);
	}

	public XmlEmbedded buildVirtualXmlEmbedded(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping) {
		return new VirtualXmlEmbedded(ormTypeMapping, javaEmbeddedMapping);
	}

	public XmlId buildVirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		return new VirtualXmlId(ormTypeMapping, javaIdMapping);
	}

	public XmlManyToMany buildVirtualXmlManyToMany(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		return new VirtualXmlManyToMany(ormTypeMapping, javaManyToManyMapping);
	}

	public XmlManyToOne buildVirtualXmlManyToOne(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping) {
		return new VirtualXmlManyToOne(ormTypeMapping, javaManyToOneMapping);
	}

	public XmlOneToMany buildVirtualXmlOneToMany(OrmTypeMapping ormTypeMapping, JavaOneToManyMapping javaOneToManyMapping) {
		return new VirtualXmlOneToMany(ormTypeMapping, javaOneToManyMapping);
	}

	public XmlOneToOne buildVirtualXmlOneToOne(OrmTypeMapping ormTypeMapping, JavaOneToOneMapping javaOneToOneMapping) {
		return new VirtualXmlOneToOne(ormTypeMapping, javaOneToOneMapping);
	}

	public XmlTransient buildVirtualXmlTransient(OrmTypeMapping ormTypeMapping, JavaTransientMapping javaTransientMapping) {
		return new VirtualXmlTransient(ormTypeMapping, javaTransientMapping);
	}

	public XmlVersion buildVirtualXmlVersion(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		return new VirtualXmlVersion(ormTypeMapping, javaVersionMapping);
	}
	
	public XmlNullAttributeMapping buildVirtualXmlNullAttributeMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping) {
		return new VirtualXmlNullAttributeMapping(ormTypeMapping, javaAttributeMapping);
	}
	

	// ********** Java Context Model **********
	
	public JavaPersistentType buildJavaPersistentType(PersistentType.Owner owner, JavaResourcePersistentType jrpt) {
		return new GenericJavaPersistentType(owner, jrpt);
	}
	
	public JavaPersistentAttribute buildJavaPersistentAttribute(JavaPersistentType parent, JavaResourcePersistentAttribute jrpa) {
		return new GenericJavaPersistentAttribute(parent, jrpa);
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
	
	public JavaDiscriminatorColumn buildJavaDiscriminatorColumn(JavaEntity parent, JavaNamedColumn.Owner owner) {
		return new GenericJavaDiscriminatorColumn(parent, owner);
	}
	
	public JavaJoinColumn buildJavaJoinColumn(JavaJpaContextNode parent, JavaJoinColumn.Owner owner) {
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
	
	public JavaGeneratedValue buildJavaGeneratedValue(JavaIdMapping parent) {
		return new GenericJavaGeneratedValue(parent);
	}
	
	public JavaPrimaryKeyJoinColumn buildJavaPrimaryKeyJoinColumn(JavaJpaContextNode parent, JavaBaseJoinColumn.Owner owner) {
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
	
	public JavaQueryHint buildJavaQueryHint(JavaQuery parent) {
		return new GenericJavaQueryHint(parent);
	}
	
	public JavaUniqueConstraint buildJavaUniqueConstraint(JavaJpaContextNode parent, UniqueConstraint.Owner owner) {
		return new GenericJavaUniqueConstraint(parent, owner);
	}
	
	public JavaEnumeratedConverter buildJavaEnumeratedConverter(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa) {
		return new GenericJavaEnumeratedConverter(parent, jrpa);
	}
	
	public JavaTemporalConverter buildJavaTemporalConverter(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa) {
		return new GenericJavaTemporalConverter(parent, jrpa);
	}
	
	public JavaLobConverter buildJavaLobConverter(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa) {
		return new GenericJavaLobConverter(parent, jrpa);
	}
	
}
