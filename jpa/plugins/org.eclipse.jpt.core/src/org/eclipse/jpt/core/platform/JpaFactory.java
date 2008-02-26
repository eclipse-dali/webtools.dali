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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.ContextModel;
import org.eclipse.jpt.core.JpaDataSource;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
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
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmTable;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmRelationshipMapping;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEmbeddable;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEntity;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmMappedSuperclass;
import org.eclipse.jpt.core.internal.context.orm.OrmDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlRelationshipMapping;

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
 * @see GenericJpaFactory
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
	
	PersistenceXml buildPersistenceXml(IBaseJpaContent parent);
	
	Persistence buildPersistence(PersistenceXml parent);
	
	PersistenceUnit buildPersistenceUnit(Persistence parent);
	
	MappingFileRef buildMappingFileRef(PersistenceUnit parent);
	
	ClassRef buildClassRef(PersistenceUnit parent);
	
	Property buildProperty(PersistenceUnit parent);
	
	
	// **************** orm context objects ************************************
	
	OrmXml buildOrmXml(MappingFileRef parent);

	EntityMappings buildEntityMappings(OrmXml parent);
	
	PersistenceUnitMetadata buildPersistenceUnitMetadata(EntityMappings parent);
	
	PersistenceUnitDefaults buildPersistenceUnitDefaults(PersistenceUnitMetadata parent);
	
	OrmPersistentType buildOrmPersistentType(EntityMappings parent, String mappingKey);
	
	GenericOrmEntity buildXmlEntity(OrmPersistentType parent);
	
	GenericOrmMappedSuperclass buildXmlMappedSuperclass(OrmPersistentType parent);
	
	GenericOrmEmbeddable buildXmlEmbeddable(OrmPersistentType parent);
	
	OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, String mappingKey);
	
	OrmTable buildOrmTable(GenericOrmEntity parent);
	
	OrmSecondaryTable buildOrmSecondaryTable(GenericOrmEntity parent);
	
	OrmPrimaryKeyJoinColumn buildOrmPrimaryKeyJoinColumn(JpaContextNode parent, AbstractJoinColumn.Owner owner);
	
	OrmJoinTable buildOrmJoinTable(AbstractOrmRelationshipMapping<? extends XmlRelationshipMapping> parent);
	
	OrmJoinColumn buildOrmJoinColumn(JpaContextNode parent, JoinColumn.Owner owner);
	
	OrmAttributeOverride buildOrmAttributeOverride(JpaContextNode parent, AttributeOverride.Owner owner);
	
	OrmAssociationOverride buildOrmAssociationOverride(JpaContextNode parent, AssociationOverride.Owner owner);

	OrmDiscriminatorColumn buildOrmDiscriminatorColumn(GenericOrmEntity parent, NamedColumn.Owner owner);
	
	OrmColumn buildOrmColumn(JpaContextNode parent, OrmColumn.Owner owner);
	
	OrmGeneratedValue buildOrmGeneratedValue(JpaContextNode parent);
	
	// **************** java context objects ***********************************
	
	JavaPersistentType buildJavaPersistentType(JpaContextNode parent);
	
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

	JavaDiscriminatorColumn buildJavaDiscriminatorColumn(JavaEntity parent, NamedColumn.Owner owner);
	
	JavaJoinColumn buildJavaJoinColumn(JavaJpaContextNode parent, JoinColumn.Owner owner);

	JavaSecondaryTable buildJavaSecondaryTable(JavaEntity parent);
	
	JavaSequenceGenerator buildJavaSequenceGenerator(JavaJpaContextNode parent);
	
	JavaTableGenerator buildJavaTableGenerator(JavaJpaContextNode parent);
	
	JavaGeneratedValue buildJavaGeneratedValue(JavaAttributeMapping parent);
	
	JavaPrimaryKeyJoinColumn buildJavaPrimaryKeyJoinColumn(JavaJpaContextNode parent, AbstractJoinColumn.Owner owner);
	
	JavaAttributeOverride buildJavaAttributeOverride(JavaJpaContextNode parent, AttributeOverride.Owner owner);
	
	JavaAssociationOverride buildJavaAssociationOverride(JavaJpaContextNode parent, AssociationOverride.Owner owner);

	JavaNamedQuery buildJavaNamedQuery(JavaJpaContextNode parent);
	
	JavaNamedNativeQuery buildJavaNamedNativeQuery(JavaJpaContextNode parent);
	
	JavaQueryHint buildJavaQueryHint(JavaQuery<?> parent);
}
