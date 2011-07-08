/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.jpa.core.JpaDataSource;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaLobConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOrderable;
import org.eclipse.jpt.jpa.core.context.java.JavaOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryHint;
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.jpa.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaTable;
import org.eclipse.jpt.jpa.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaTemporalConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.internal.context.java.GenericJavaVirtualJoinTable;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.GenericJpaDataSource;
import org.eclipse.jpt.jpa.core.internal.jpa1.GenericJpaFile;
import org.eclipse.jpt.jpa.core.internal.jpa1.GenericJpaProject;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericRootContextNode;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaAttributeOverride;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaBasicMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaEmbeddable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaEmbeddedMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaEntity;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaEnumeratedConverter;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaGeneratedValue;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaIdMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaJoinColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaJoinTable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaLobConverter;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaManyToManyMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaManyToOneMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaNamedNativeQuery;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaNamedQuery;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaNullAttributeMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaOneToManyMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaOrderable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaOverrideRelationship;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaQueryContainer;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaQueryHint;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaSecondaryTable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaSequenceGenerator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaTable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaTableGenerator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaTemporalConverter;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaTransientMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaVersionMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaVirtualColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaVirtualJoinColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaVirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaVirtualUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXml;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence.GenericPersistenceXml;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EnumeratedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.LobAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.jpa.core.resource.xml.JpaXmlResource;

/**
 * Central class that allows extenders to easily replace implementations of
 * various Dali interfaces.
 */
public abstract class AbstractJpaFactory
	implements JpaFactory
{
	protected AbstractJpaFactory() {
		super();
	}
	
	
	// ********** Core Model **********
	
	public JpaProject buildJpaProject(JpaProject.Config config) {
		return new GenericJpaProject(config);
	}
	
	public JpaDataSource buildJpaDataSource(JpaProject jpaProject, String connectionProfileName) {
		return new GenericJpaDataSource(jpaProject, connectionProfileName);
	}
	
	public JpaFile buildJpaFile(JpaProject jpaProject, IFile file, IContentType contentType, JptResourceModel resourceModel) {
		return new GenericJpaFile(jpaProject, file, contentType, resourceModel);
	}


	// ********** Context Nodes **********
	
	public JpaRootContextNode buildRootContextNode(JpaProject parent) {
		return new GenericRootContextNode(parent);
	}


	// ********** XML Context Model **********

	public PersistenceXml buildPersistenceXml(JpaRootContextNode parent, JpaXmlResource resource) {
		return new GenericPersistenceXml(parent, resource);
	}
	
	public OrmXml buildMappingFile(MappingFileRef parent, JpaXmlResource resource) {
		return new GenericOrmXml(parent, resource);
	}


	// ********** Java Context Model **********
	
	public JavaPersistentType buildJavaPersistentType(PersistentType.Owner owner, JavaResourcePersistentType jrpt) {
		return new GenericJavaPersistentType(owner, jrpt);
	}
	
	public JavaPersistentAttribute buildJavaPersistentAttribute(PersistentType parent, JavaResourcePersistentAttribute jrpa) {
		return new GenericJavaPersistentAttribute(parent, jrpa);
	}
	
	public JavaTypeMapping buildJavaNullTypeMapping(JavaPersistentType parent) {
		return new JavaNullTypeMapping(parent);
	}
	
	public JavaEntity buildJavaEntity(JavaPersistentType parent, EntityAnnotation entityAnnotation) {
		return new GenericJavaEntity(parent, entityAnnotation);
	}

	public JavaMappedSuperclass buildJavaMappedSuperclass(JavaPersistentType parent, MappedSuperclassAnnotation mappedSuperclassAnnotation) {
		return new GenericJavaMappedSuperclass(parent, mappedSuperclassAnnotation);
	}

	public JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent, EmbeddableAnnotation embeddableAnnotation) {
		return new GenericJavaEmbeddable(parent, embeddableAnnotation);
	}
	
	public JavaTable buildJavaTable(JavaEntity parent, Table.Owner owner) {
		return new GenericJavaTable(parent, owner);
	}
	
	public JavaColumn buildJavaColumn(JavaJpaContextNode parent, JavaColumn.Owner owner) {
		return new GenericJavaColumn(parent, owner);
	}
	
	public JavaVirtualColumn buildJavaVirtualColumn(JavaJpaContextNode parent, JavaVirtualColumn.Owner owner) {
		return new GenericJavaVirtualColumn(parent, owner);
	}
	
	public JavaDiscriminatorColumn buildJavaDiscriminatorColumn(JavaEntity parent, JavaDiscriminatorColumn.Owner owner) {
		return new GenericJavaDiscriminatorColumn(parent, owner);
	}
	
	public JavaJoinColumn buildJavaJoinColumn(JavaJpaContextNode parent, JavaReadOnlyJoinColumn.Owner owner, JoinColumnAnnotation joinColumnAnnotation) {
		return new GenericJavaJoinColumn(parent, owner, joinColumnAnnotation);
	}

	public JavaVirtualJoinColumn buildJavaVirtualJoinColumn(JavaJpaContextNode parent, JavaReadOnlyJoinColumn.Owner owner, ReadOnlyJoinColumn overriddenColumn) {
		return new GenericJavaVirtualJoinColumn(parent, owner, overriddenColumn);
	}
	
	public JavaJoinTable buildJavaJoinTable(JavaJoinTableRelationshipStrategy parent, Table.Owner owner) {
		return new GenericJavaJoinTable(parent, owner);
	}
	
	public JavaVirtualJoinTable buildJavaVirtualJoinTable(JavaVirtualJoinTableRelationshipStrategy parent, ReadOnlyTable.Owner owner, ReadOnlyJoinTable overriddenTable) {
		return new GenericJavaVirtualJoinTable(parent, owner, overriddenTable);
	}
	
	public JavaSecondaryTable buildJavaSecondaryTable(JavaEntity parent, Table.Owner owner, SecondaryTableAnnotation tableAnnotation) {
		return new GenericJavaSecondaryTable(parent, owner, tableAnnotation);
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
	
	public JavaGeneratorContainer buildJavaGeneratorContainer(JavaJpaContextNode parent, JavaGeneratorContainer.Owner owner) {
		return new GenericJavaGeneratorContainer(parent, owner);
	}
	
	public JavaSequenceGenerator buildJavaSequenceGenerator(JavaJpaContextNode parent, SequenceGeneratorAnnotation sequenceGeneratorAnnotation) {
		return new GenericJavaSequenceGenerator(parent, sequenceGeneratorAnnotation);
	}
	
	public JavaTableGenerator buildJavaTableGenerator(JavaJpaContextNode parent, TableGeneratorAnnotation tableGeneratorAnnotation) {
		return new GenericJavaTableGenerator(parent, tableGeneratorAnnotation);
	}
	
	public JavaGeneratedValue buildJavaGeneratedValue(JavaIdMapping parent, GeneratedValueAnnotation generatedValueAnnotation) {
		return new GenericJavaGeneratedValue(parent, generatedValueAnnotation);
	}
	
	public JavaPrimaryKeyJoinColumn buildJavaPrimaryKeyJoinColumn(JavaJpaContextNode parent, JavaReadOnlyBaseJoinColumn.Owner owner, PrimaryKeyJoinColumnAnnotation pkJoinColumnAnnotation) {
		return new GenericJavaPrimaryKeyJoinColumn(parent, owner, pkJoinColumnAnnotation);
	}
	
	public JavaAttributeOverrideContainer buildJavaAttributeOverrideContainer(JavaJpaContextNode parent, JavaAttributeOverrideContainer.Owner owner) {
		return new GenericJavaAttributeOverrideContainer(parent, owner);
	}
	
	public JavaAssociationOverrideContainer buildJavaAssociationOverrideContainer(JavaJpaContextNode parent, JavaAssociationOverrideContainer.Owner owner) {
		return new GenericJavaAssociationOverrideContainer(parent, owner);
	}
	
	public JavaAttributeOverride buildJavaAttributeOverride(JavaAttributeOverrideContainer parent, AttributeOverrideAnnotation annotation) {
		return new GenericJavaAttributeOverride(parent, annotation);
	}
	
	public JavaVirtualAttributeOverride buildJavaVirtualAttributeOverride(JavaAttributeOverrideContainer parent, String name) {
		return new GenericJavaVirtualAttributeOverride(parent, name);
	}
	
	public JavaAssociationOverride buildJavaAssociationOverride(JavaAssociationOverrideContainer parent, AssociationOverrideAnnotation annotation) {
		return new GenericJavaAssociationOverride(parent, annotation);
	}
	
	public JavaVirtualAssociationOverride buildJavaVirtualAssociationOverride(JavaAssociationOverrideContainer parent, String name) {
		return new GenericJavaVirtualAssociationOverride(parent, name);
	}
	
	public JavaOverrideRelationship buildJavaOverrideRelationship(JavaAssociationOverride parent) {
		return new GenericJavaOverrideRelationship(parent);
	}
	
	public JavaVirtualOverrideRelationship buildJavaVirtualOverrideRelationship(JavaVirtualAssociationOverride parent) {
		return new GenericJavaVirtualOverrideRelationship(parent);
	}
	
	public JavaQueryContainer buildJavaQueryContainer(JavaJpaContextNode parent, JavaQueryContainer.Owner owner) {
		return new GenericJavaQueryContainer(parent, owner);
	}
	
	public JavaNamedQuery buildJavaNamedQuery(JavaJpaContextNode parent, NamedQueryAnnotation namedQueryAnnotation) {
		return new GenericJavaNamedQuery(parent, namedQueryAnnotation);
	}
	
	public JavaNamedNativeQuery buildJavaNamedNativeQuery(JavaJpaContextNode parent, NamedNativeQueryAnnotation namedNativeQueryAnnotation) {
		return new GenericJavaNamedNativeQuery(parent, namedNativeQueryAnnotation);
	}
	
	public JavaQueryHint buildJavaQueryHint(JavaQuery parent, QueryHintAnnotation queryHintAnnotation) {
		return new GenericJavaQueryHint(parent, queryHintAnnotation);
	}
	
	public JavaUniqueConstraint buildJavaUniqueConstraint(JavaJpaContextNode parent, UniqueConstraint.Owner owner, UniqueConstraintAnnotation constraintAnnotation) {
		return new GenericJavaUniqueConstraint(parent, owner, constraintAnnotation);
	}
	
	public JavaVirtualUniqueConstraint buildJavaVirtualUniqueConstraint(JavaJpaContextNode parent, ReadOnlyUniqueConstraint overriddenUniqueConstraint) {
		return new GenericJavaVirtualUniqueConstraint(parent, overriddenUniqueConstraint);
	}
	
	public JavaEnumeratedConverter buildJavaEnumeratedConverter(JavaAttributeMapping parent, EnumeratedAnnotation annotation) {
		return new GenericJavaEnumeratedConverter(parent, annotation);
	}
	
	public JavaTemporalConverter buildJavaTemporalConverter(JavaAttributeMapping parent, TemporalAnnotation annotation) {
		return new GenericJavaTemporalConverter(parent, annotation);
	}
	
	public JavaLobConverter buildJavaLobConverter(JavaAttributeMapping parent, LobAnnotation annotation) {
		return new GenericJavaLobConverter(parent, annotation);
	}
	
	public JavaOrderable buildJavaOrderable(JavaAttributeMapping parent) {
		return new GenericJavaOrderable(parent);
	}
}
