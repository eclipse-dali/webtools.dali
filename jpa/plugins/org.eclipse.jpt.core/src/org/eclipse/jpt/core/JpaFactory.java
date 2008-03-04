/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseJpaContent;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.java.JavaAbstractJoinColumn;
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
import org.eclipse.jpt.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmAbstractJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
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
import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmNamedColumn;
import org.eclipse.jpt.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
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
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.OrmResource;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.persistence.XmlProperty;

/**
 * Use JpaFactory to create any core (e.g. JpaProject), resource 
 * (e.g. PersistenceResource), or context (e.g. AttributeMapping) model objects.
 * 
 * Assumes a base JPA project context structure 
 * corresponding to the JPA spec:
 * 
 * 	RootContent
 * 	 |- persistence.xml
 * 	     |- persistence unit(s)
 *           |- mapping file(s)  (e.g. orm.xml)
 *           |   |- persistent type mapping(s)  (e.g. Entity)
 *           |        |- persistent attribute mapping(s)  (e.g. Basic)
 *           |- persistent type mapping(s)
 *   
 *   ... and associated objects.
 *
 * @see org.eclipse.jpt.core.internal.platform.GenericJpaFactory
 */
public interface JpaFactory 
{
	// **************** core objects *******************************************
	
	/**
	 * Construct a JpaProject for the specified config, to be
	 * added to the specified JPA project. Return null if unable to create
	 * the JPA file (e.g. the content type is unrecognized).
	 */
	JpaProject buildJpaProject(JpaProject.Config config) throws CoreException;
	
	JpaDataSource buildJpaDataSource(JpaProject jpaProject, String connectionProfileName);
	
	/**
	 * Construct a JPA file for the specified file and with the specified resource
	 * model, to be added to the specified JPA project.
	 * This should be non-null iff (if and only if) {@link #hasRelevantContent(IFile)}
	 * returns true.
	 */
	JpaFile buildJpaFile(JpaProject jpaProject, IFile file, ResourceModel resourceModel);
	
	/**
	 * Return true if a resource model will be provided for the given file
	 */
	boolean hasRelevantContent(IFile file);
	
	/**
	 * Build a resource model to be associated with the given file.
	 * This should be non-null iff (if and only if) {@link #hasRelevantContent(IFile)}
	 * returns true. 
	 */
	ResourceModel buildResourceModel(JpaProject jpaProject, IFile file);
	
	/**
	 * Build a (updated) context model to be associated with the given JPA project.
	 * The context model will be built once, but updated many times.
	 * @see JpaProject.update(ProgressMonitor)
	 */
	ContextModel buildContextModel(JpaProject jpaProject);
	
	
	// **************** persistence context objects ****************************
	
	PersistenceXml buildPersistenceXml(BaseJpaContent parent, PersistenceResource persistenceResource);
	
	Persistence buildPersistence(PersistenceXml parent, XmlPersistence xmlPersistence);
	
	PersistenceUnit buildPersistenceUnit(Persistence parent, XmlPersistenceUnit persistenceUnit);
	
	/**
	 * xmlMappingFileRef is allowed to be null, this would be used for the implied mapping file ref
	 */
	MappingFileRef buildMappingFileRef(PersistenceUnit parent, XmlMappingFileRef xmlMappingFileRef);
	
	ClassRef buildClassRef(PersistenceUnit parent, XmlJavaClassRef xmlClassRef);
	
	ClassRef buildClassRef(PersistenceUnit parent, String className);

	Property buildProperty(PersistenceUnit parent, XmlProperty property);
	
	
	// **************** orm context objects ************************************
	
	OrmXml buildOrmXml(MappingFileRef parent, OrmResource ormResource);

	EntityMappings buildEntityMappings(OrmXml parent, XmlEntityMappings entityMappings);
	
	PersistenceUnitMetadata buildPersistenceUnitMetadata(EntityMappings parent, XmlEntityMappings entityMappings);
	
	PersistenceUnitDefaults buildPersistenceUnitDefaults(PersistenceUnitMetadata parent, XmlEntityMappings entityMappings);
	
	OrmPersistentType buildOrmPersistentType(EntityMappings parent, String mappingKey);
	
	OrmEntity buildOrmEntity(OrmPersistentType parent);
	
	OrmMappedSuperclass buildOrmMappedSuperclass(OrmPersistentType parent);
	
	OrmEmbeddable buildOrmEmbeddable(OrmPersistentType parent);
	
	OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, String mappingKey);
	
	OrmTable buildOrmTable(OrmEntity parent);
	
	OrmSecondaryTable buildOrmSecondaryTable(OrmEntity parent);
	
	OrmPrimaryKeyJoinColumn buildOrmPrimaryKeyJoinColumn(OrmJpaContextNode parent, OrmAbstractJoinColumn.Owner owner);
	
	OrmJoinTable buildOrmJoinTable(OrmRelationshipMapping parent);
	
	OrmJoinColumn buildOrmJoinColumn(OrmJpaContextNode parent, OrmJoinColumn.Owner owner);
	
	OrmAttributeOverride buildOrmAttributeOverride(OrmJpaContextNode parent, AttributeOverride.Owner owner, XmlAttributeOverride xmlAttributeOverride);
	
	OrmAssociationOverride buildOrmAssociationOverride(OrmJpaContextNode parent, AssociationOverride.Owner owner, XmlAssociationOverride associationOverride);

	OrmDiscriminatorColumn buildOrmDiscriminatorColumn(OrmEntity parent, OrmNamedColumn.Owner owner);
	
	OrmColumn buildOrmColumn(OrmJpaContextNode parent, OrmColumn.Owner owner);
	
	OrmGeneratedValue buildOrmGeneratedValue(OrmJpaContextNode parent);
	
	OrmSequenceGenerator buildOrmSequenceGenerator(OrmJpaContextNode parent);
	
	OrmTableGenerator buildOrmTableGenerator(OrmJpaContextNode parent);
	
	OrmNamedNativeQuery buildOrmNamedNativeQuery(OrmJpaContextNode parent);

	OrmNamedQuery buildOrmNamedQuery(OrmJpaContextNode parent);
	
	OrmQueryHint buildOrmQueryHint(OrmQuery parent);

	OrmBasicMapping buildOrmBasicMapping(OrmPersistentAttribute parent);
	
	OrmManyToManyMapping buildOrmManyToManyMapping(OrmPersistentAttribute parent);
	
	OrmOneToManyMapping buildOrmOneToManyMapping(OrmPersistentAttribute parent);
	
	OrmManyToOneMapping buildOrmManyToOneMapping(OrmPersistentAttribute parent);
	
	OrmOneToOneMapping buildOrmOneToOneMapping(OrmPersistentAttribute parent);
	
	OrmEmbeddedIdMapping buildOrmEmbeddedIdMapping(OrmPersistentAttribute parent);
	
	OrmEmbeddedMapping buildOrmEmbeddedMapping(OrmPersistentAttribute parent);
	
	OrmIdMapping buildOrmIdMapping(OrmPersistentAttribute parent);
	
	OrmTransientMapping buildOrmTransientMapping(OrmPersistentAttribute parent);
	
	OrmVersionMapping buildOrmVersionMapping(OrmPersistentAttribute parent);
	
	OrmAttributeMapping buildOrmNullAttributeMapping(OrmPersistentAttribute parent);
	
	// **************** java context objects ***********************************
	
	JavaPersistentType buildJavaPersistentType(JpaContextNode parent, JavaResourcePersistentType resourcePersistentType);
	
	JavaEntity buildJavaEntity(JavaPersistentType parent);
	
	JavaMappedSuperclass buildJavaMappedSuperclass(JavaPersistentType parent);
	
	JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent);
	
	JavaTypeMapping buildJavaNullTypeMapping(JavaPersistentType parent);
	
	JavaPersistentAttribute buildJavaPersistentAttribute(JavaPersistentType parent);

	JavaBasicMapping buildJavaBasicMapping(JavaPersistentAttribute parent);
	
	JavaEmbeddedIdMapping buildJavaEmbeddedIdMapping(JavaPersistentAttribute parent);
	
	JavaEmbeddedMapping buildJavaEmbeddedMapping(JavaPersistentAttribute parent);
	
	JavaIdMapping buildJavaIdMapping(JavaPersistentAttribute parent);
	
	JavaManyToManyMapping buildJavaManyToManyMapping(JavaPersistentAttribute parent);
	
	JavaManyToOneMapping buildJavaManyToOneMapping(JavaPersistentAttribute parent);
	
	JavaOneToManyMapping buildJavaOneToManyMapping(JavaPersistentAttribute parent);
	
	JavaOneToOneMapping buildJavaOneToOneMapping(JavaPersistentAttribute parent);

	JavaTransientMapping buildJavaTransientMapping(JavaPersistentAttribute parent);
	
	JavaVersionMapping buildJavaVersionMapping(JavaPersistentAttribute parent);
	
	JavaAttributeMapping buildJavaNullAttributeMapping(JavaPersistentAttribute parent);
	
	JavaTable buildJavaTable(JavaEntity parent);
	
	JavaJoinTable buildJavaJoinTable(JavaRelationshipMapping parent);
	
	JavaColumn buildJavaColumn(JavaJpaContextNode parent, JavaColumn.Owner owner);

	JavaDiscriminatorColumn buildJavaDiscriminatorColumn(JavaEntity parent, JavaNamedColumn.Owner owner);
	
	JavaJoinColumn buildJavaJoinColumn(JavaJpaContextNode parent, JavaJoinColumn.Owner owner);

	JavaSecondaryTable buildJavaSecondaryTable(JavaEntity parent);
	
	JavaSequenceGenerator buildJavaSequenceGenerator(JavaJpaContextNode parent);
	
	JavaTableGenerator buildJavaTableGenerator(JavaJpaContextNode parent);
	
	JavaGeneratedValue buildJavaGeneratedValue(JavaAttributeMapping parent);
	
	JavaPrimaryKeyJoinColumn buildJavaPrimaryKeyJoinColumn(JavaJpaContextNode parent, JavaAbstractJoinColumn.Owner owner);
	
	JavaAttributeOverride buildJavaAttributeOverride(JavaJpaContextNode parent, AttributeOverride.Owner owner);
	
	JavaAssociationOverride buildJavaAssociationOverride(JavaJpaContextNode parent, AssociationOverride.Owner owner);

	JavaNamedQuery buildJavaNamedQuery(JavaJpaContextNode parent);
	
	JavaNamedNativeQuery buildJavaNamedNativeQuery(JavaJpaContextNode parent);
	
	JavaQueryHint buildJavaQueryHint(JavaQuery parent);
}
