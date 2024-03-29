/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jpa.core.JpaDataSource;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.JpaContextRoot;
import org.eclipse.jpt.jpa.core.context.Orderable;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.SpecifiedUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.VirtualColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.VirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.VirtualUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.Accessor;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaLobConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryHint;
import org.eclipse.jpt.jpa.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedSecondaryTable;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedTable;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaTable;
import org.eclipse.jpt.jpa.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.internal.context.java.GenericJavaVirtualJoinTable;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.GenericJpaDataSource;
import org.eclipse.jpt.jpa.core.internal.jpa1.GenericJpaFile;
import org.eclipse.jpt.jpa.core.internal.jpa1.GenericJpaProject;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericContextRoot;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaBaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaBaseTemporalConverter;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaBasicMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaEmbeddable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaEmbeddedMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaEntity;
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
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaSpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaTable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaTableGenerator;
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
import org.eclipse.jpt.jpa.core.resource.java.BaseEnumeratedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.BaseTemporalAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.CompleteJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.LobAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;

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
	
	public JpaProject buildJpaProject(JpaProject.Config config, IProgressMonitor monitor) {
		return new GenericJpaProject(config, monitor);
	}
	
	public JpaDataSource buildJpaDataSource(JpaProject jpaProject, String connectionProfileName) {
		return new GenericJpaDataSource(jpaProject, connectionProfileName);
	}
	
	public JpaFile buildJpaFile(JpaProject jpaProject, IFile file, IContentType contentType, JptResourceModel resourceModel) {
		return new GenericJpaFile(jpaProject, file, contentType, resourceModel);
	}


	// ********** Context Nodes **********
	
	public JpaContextRoot buildContextRoot(JpaProject parent) {
		return new GenericContextRoot(parent);
	}


	// ********** XML Context Model **********

	public PersistenceXml buildPersistenceXml(JpaContextRoot parent, JptXmlResource resource) {
		return new GenericPersistenceXml(parent, resource);
	}
	
	public OrmXml buildMappingFile(MappingFileRef parent, Object resourceMappingFile) {
		return new GenericOrmXml(parent, (JptXmlResource) resourceMappingFile);
	}


	// ********** Java Context Model **********
	
	public JavaPersistentType buildJavaPersistentType(JavaPersistentType.Parent parent, JavaResourceType jrt) {
		return new GenericJavaPersistentType(parent, jrt);
	}

	public JavaSpecifiedPersistentAttribute buildJavaPersistentAttribute(PersistentType parent, Accessor accessor) {
		return new GenericJavaPersistentAttribute(parent, accessor);
	}

	public JavaSpecifiedPersistentAttribute buildJavaPersistentField(PersistentType parent, JavaResourceField resourceField) {
		return new GenericJavaPersistentAttribute(parent, resourceField);
	}

	public JavaSpecifiedPersistentAttribute buildJavaPersistentProperty(PersistentType parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		return new GenericJavaPersistentAttribute(parent, resourceGetter, resourceSetter);
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
	
	public JavaSpecifiedTable buildJavaTable(JavaTable.ParentAdapter parentAdapter) {
		return new GenericJavaTable(parentAdapter);
	}
	
	public JavaSpecifiedColumn buildJavaColumn(JavaSpecifiedColumn.ParentAdapter parentAdapter) {
		return new GenericJavaColumn(parentAdapter);
	}
	
	public VirtualColumn buildJavaVirtualColumn(VirtualColumn.ParentAdapter parentAdapter) {
		return new GenericJavaVirtualColumn(parentAdapter);
	}
	
	public JavaSpecifiedDiscriminatorColumn buildJavaDiscriminatorColumn(JavaSpecifiedDiscriminatorColumn.ParentAdapter parentAdapter) {
		return new GenericJavaDiscriminatorColumn(parentAdapter);
	}
	
	public JavaSpecifiedJoinColumn buildJavaJoinColumn(JoinColumn.ParentAdapter parentAdapter, CompleteJoinColumnAnnotation joinColumnAnnotation) {
		return new GenericJavaJoinColumn(parentAdapter, joinColumnAnnotation);
	}

	public VirtualJoinColumn buildJavaVirtualJoinColumn(JoinColumn.ParentAdapter parentAdapter, JoinColumn overriddenColumn) {
		return new GenericJavaVirtualJoinColumn(parentAdapter, overriddenColumn);
	}
	
	public JavaSpecifiedJoinTable buildJavaJoinTable(JavaSpecifiedJoinTable.ParentAdapter parentAdapter) {
		return new GenericJavaJoinTable(parentAdapter);
	}
	
	public VirtualJoinTable buildJavaVirtualJoinTable(VirtualJoinTable.ParentAdapter parentAdapter, JoinTable overriddenTable) {
		return new GenericJavaVirtualJoinTable(parentAdapter, overriddenTable);
	}
	
	public JavaSpecifiedSecondaryTable buildJavaSecondaryTable(JavaSpecifiedSecondaryTable.ParentAdapter parentAdapter, SecondaryTableAnnotation tableAnnotation) {
		return new GenericJavaSecondaryTable(parentAdapter, tableAnnotation);
	}
	
	public JavaBasicMapping buildJavaBasicMapping(JavaSpecifiedPersistentAttribute parent) {
		return new GenericJavaBasicMapping(parent);
	}
	
	public JavaEmbeddedIdMapping buildJavaEmbeddedIdMapping(JavaSpecifiedPersistentAttribute parent) {
		return new GenericJavaEmbeddedIdMapping(parent);
	}
	
	public JavaEmbeddedMapping buildJavaEmbeddedMapping(JavaSpecifiedPersistentAttribute parent) {
		return new GenericJavaEmbeddedMapping(parent);
	}
	
	public JavaIdMapping buildJavaIdMapping(JavaSpecifiedPersistentAttribute parent) {
		return new GenericJavaIdMapping(parent);
	}
	
	public JavaManyToManyMapping buildJavaManyToManyMapping(JavaSpecifiedPersistentAttribute parent) {
		return new GenericJavaManyToManyMapping(parent);
	}
	
	public JavaManyToOneMapping buildJavaManyToOneMapping(JavaSpecifiedPersistentAttribute parent) {
		return new GenericJavaManyToOneMapping(parent);
	}
	
	public JavaOneToManyMapping buildJavaOneToManyMapping(JavaSpecifiedPersistentAttribute parent) {
		return new GenericJavaOneToManyMapping(parent);
	}
	
	public JavaOneToOneMapping buildJavaOneToOneMapping(JavaSpecifiedPersistentAttribute parent) {
		return new GenericJavaOneToOneMapping(parent);
	}
	
	public JavaTransientMapping buildJavaTransientMapping(JavaSpecifiedPersistentAttribute parent) {
		return new GenericJavaTransientMapping(parent);
	}
	
	public JavaVersionMapping buildJavaVersionMapping(JavaSpecifiedPersistentAttribute parent) {
		return new GenericJavaVersionMapping(parent);
	}
	
	public JavaAttributeMapping buildJavaNullAttributeMapping(JavaSpecifiedPersistentAttribute parent) {
		return new GenericJavaNullAttributeMapping(parent);
	}
	
	public JavaGeneratorContainer buildJavaGeneratorContainer(JavaGeneratorContainer.Parent parentAdapter) {
		return new GenericJavaGeneratorContainer(parentAdapter);
	}
	
	public JavaSequenceGenerator buildJavaSequenceGenerator(JavaGeneratorContainer parent, SequenceGeneratorAnnotation sequenceGeneratorAnnotation) {
		return new GenericJavaSequenceGenerator(parent, sequenceGeneratorAnnotation);
	}
	
	public JavaTableGenerator buildJavaTableGenerator(JavaGeneratorContainer parent, TableGeneratorAnnotation tableGeneratorAnnotation) {
		return new GenericJavaTableGenerator(parent, tableGeneratorAnnotation);
	}
	
	public JavaGeneratedValue buildJavaGeneratedValue(JavaAttributeMapping parent, GeneratedValueAnnotation generatedValueAnnotation) {
		return new GenericJavaGeneratedValue(parent, generatedValueAnnotation);
	}
	
	public JavaSpecifiedPrimaryKeyJoinColumn buildJavaPrimaryKeyJoinColumn(BaseJoinColumn.ParentAdapter parentAdapter, PrimaryKeyJoinColumnAnnotation pkJoinColumnAnnotation) {
		return new GenericJavaPrimaryKeyJoinColumn(parentAdapter, pkJoinColumnAnnotation);
	}
	
	public JavaAttributeOverrideContainer buildJavaAttributeOverrideContainer(JavaAttributeOverrideContainer.ParentAdapter parentAdapter) {
		return new GenericJavaAttributeOverrideContainer(parentAdapter);
	}
	
	public JavaAssociationOverrideContainer buildJavaAssociationOverrideContainer(JavaAssociationOverrideContainer.ParentAdapter parentAdapter) {
		return new GenericJavaAssociationOverrideContainer(parentAdapter);
	}
	
	public JavaSpecifiedAttributeOverride buildJavaAttributeOverride(JavaAttributeOverrideContainer parent, AttributeOverrideAnnotation annotation) {
		return new GenericJavaSpecifiedAttributeOverride(parent, annotation);
	}
	
	public JavaVirtualAttributeOverride buildJavaVirtualAttributeOverride(JavaAttributeOverrideContainer parent, String name) {
		return new GenericJavaVirtualAttributeOverride(parent, name);
	}
	
	public JavaSpecifiedAssociationOverride buildJavaAssociationOverride(JavaAssociationOverrideContainer parent, AssociationOverrideAnnotation annotation) {
		return new GenericJavaSpecifiedAssociationOverride(parent, annotation);
	}
	
	public JavaVirtualAssociationOverride buildJavaVirtualAssociationOverride(JavaAssociationOverrideContainer parent, String name) {
		return new GenericJavaVirtualAssociationOverride(parent, name);
	}
	
	public JavaSpecifiedOverrideRelationship buildJavaOverrideRelationship(JavaSpecifiedAssociationOverride parent) {
		return new GenericJavaOverrideRelationship(parent);
	}
	
	public VirtualOverrideRelationship buildJavaVirtualOverrideRelationship(JavaVirtualAssociationOverride parent) {
		return new GenericJavaVirtualOverrideRelationship(parent);
	}
	
	public JavaQueryContainer buildJavaQueryContainer(JavaQueryContainer.Parent parent) {
		return new GenericJavaQueryContainer(parent);
	}
	
	public JavaNamedQuery buildJavaNamedQuery(JavaQueryContainer parent, NamedQueryAnnotation namedQueryAnnotation) {
		return new GenericJavaNamedQuery(parent, namedQueryAnnotation);
	}
	
	public JavaNamedNativeQuery buildJavaNamedNativeQuery(JavaQueryContainer parent, NamedNativeQueryAnnotation namedNativeQueryAnnotation) {
		return new GenericJavaNamedNativeQuery(parent, namedNativeQueryAnnotation);
	}
	
	public JavaQueryHint buildJavaQueryHint(JavaQuery parent, QueryHintAnnotation queryHintAnnotation) {
		return new GenericJavaQueryHint(parent, queryHintAnnotation);
	}
	
	public JavaSpecifiedUniqueConstraint buildJavaUniqueConstraint(SpecifiedUniqueConstraint.Parent parent, UniqueConstraintAnnotation constraintAnnotation) {
		return new GenericJavaUniqueConstraint(parent, constraintAnnotation);
	}
	
	public VirtualUniqueConstraint buildJavaVirtualUniqueConstraint(JpaContextModel parent, UniqueConstraint overriddenUniqueConstraint) {
		return new GenericJavaVirtualUniqueConstraint(parent, overriddenUniqueConstraint);
	}
	
	public JavaBaseEnumeratedConverter buildJavaBaseEnumeratedConverter(Converter.ParentAdapter<JavaAttributeMapping> parentAdapter, BaseEnumeratedAnnotation annotation) {
		return new GenericJavaBaseEnumeratedConverter(parentAdapter, annotation);
	}
	
	public JavaBaseTemporalConverter buildJavaBaseTemporalConverter(Converter.ParentAdapter<JavaAttributeMapping> parentAdapter, BaseTemporalAnnotation annotation) {
		return new GenericJavaBaseTemporalConverter(parentAdapter, annotation);
	}
	
	public JavaLobConverter buildJavaLobConverter(Converter.ParentAdapter<JavaAttributeMapping> parentAdapter, LobAnnotation annotation) {
		return new GenericJavaLobConverter(parentAdapter, annotation);
	}
	
	public Orderable buildJavaOrderable(JavaAttributeMapping parent) {
		return new GenericJavaOrderable(parent);
	}
}
