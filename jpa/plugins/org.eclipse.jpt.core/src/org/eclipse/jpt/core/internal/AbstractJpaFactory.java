/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaDataSource;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaResourceModel;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.java.JarFile;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaBaseJoinColumn;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaCascade;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaEnumeratedConverter;
import org.eclipse.jpt.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaLobConverter;
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
import org.eclipse.jpt.core.context.java.JavaQueryContainer;
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
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.core.internal.jpa1.GenericJpaDataSource;
import org.eclipse.jpt.core.internal.jpa1.GenericJpaFile;
import org.eclipse.jpt.core.internal.jpa1.GenericJpaProject;
import org.eclipse.jpt.core.internal.jpa1.context.GenericRootContextNode;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJarFile;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaAssociationOverride;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaAssociationOverrideContainer;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaAttributeOverride;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaAttributeOverrideContainer;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaBasicMapping;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaCascade;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaColumn;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaDiscriminatorColumn;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaEmbeddable;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaEmbeddedMapping;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaEntity;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaEnumeratedConverter;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaGeneratedValue;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaGeneratorContainer;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaIdMapping;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaJoinColumn;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaJoinTable;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaLobConverter;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaManyToManyMapping;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaManyToOneMapping;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaMappedSuperclass;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaNamedNativeQuery;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaNamedQuery;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaNullAttributeMapping;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaNullConverter;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaOneToManyMapping;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaOneToOneMapping;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaPersistentAttribute;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaPersistentType;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaQueryContainer;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaQueryHint;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaSecondaryTable;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaSequenceGenerator;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaTable;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaTableGenerator;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaTemporalConverter;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaTransientMapping;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaUniqueConstraint;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaVersionMapping;
import org.eclipse.jpt.core.internal.jpa1.context.java.NullJavaAssociationOverrideContainer;
import org.eclipse.jpt.core.internal.jpa1.context.java.VirtualAssociationOverride1_0Annotation;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.GenericClassRef;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.GenericJarFileRef;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.GenericMappingFileRef;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.GenericPersistence;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.GenericPersistenceUnit;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.GenericPersistenceUnitProperty;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.GenericPersistenceXml;
import org.eclipse.jpt.core.internal.jpa1.context.persistence.ImpliedMappingFileRef;
import org.eclipse.jpt.core.internal.jpa2.NullPersistentTypeStaticMetamodelSynchronizer;
import org.eclipse.jpt.core.internal.jpa2.NullStaticMetamodelSynchronizer;
import org.eclipse.jpt.core.internal.jpa2.context.java.NullJavaDerivedId2_0;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.core.jpa2.PersistentTypeStaticMetamodelSynchronizer;
import org.eclipse.jpt.core.jpa2.StaticMetamodelSynchronizer;
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedId2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaEmbeddedMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePackageFragmentRoot;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.persistence.XmlProperty;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;

/**
 * Central class that allows extenders to easily replace implementations of
 * various Dali interfaces.
 */
public abstract class AbstractJpaFactory
	implements JpaFactory2_0
{
	protected AbstractJpaFactory() {
		super();
	}
	
	
	// ********** Core Model **********
	
	public JpaProject buildJpaProject(JpaProject.Config config) throws CoreException {
		return new GenericJpaProject(config);
	}
	
	public StaticMetamodelSynchronizer buildStaticMetamodelSynchronizer(JpaProject2_0 jpaProject) {
		return new NullStaticMetamodelSynchronizer(jpaProject);
	}
	
	public PersistentTypeStaticMetamodelSynchronizer buildPersistentTypeStaticMetamodelSynchronizer(StaticMetamodelSynchronizer staticMetamodelSynchronizer, PersistentType persistentType) {
		return new NullPersistentTypeStaticMetamodelSynchronizer();
	}
	
	public JpaDataSource buildJpaDataSource(JpaProject jpaProject, String connectionProfileName) {
		return new GenericJpaDataSource(jpaProject, connectionProfileName);
	}
	
	public JpaFile buildJpaFile(JpaProject jpaProject, IFile file, IContentType contentType, JpaResourceModel resourceModel) {
		return new GenericJpaFile(jpaProject, file, contentType, resourceModel);
	}


	// ********** Context Nodes **********
	
	public JpaRootContextNode buildRootContextNode(JpaProject parent) {
		return new GenericRootContextNode(parent);
	}

	
	// ********** Persistence Context Model **********
	
	public PersistenceXml buildPersistenceXml(JpaRootContextNode parent, JpaXmlResource resource) {
		return new GenericPersistenceXml(parent, resource);
	}
	
	public Persistence buildPersistence(PersistenceXml parent, XmlPersistence xmlPersistence) {
		return new GenericPersistence(parent, xmlPersistence);
	}
	
	public PersistenceUnit buildPersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		return new GenericPersistenceUnit(parent, xmlPersistenceUnit);
	}
	
	public JarFileRef buildJarFileRef(PersistenceUnit parent, XmlJarFileRef xmlJarFileRef) {
		return new GenericJarFileRef(parent, xmlJarFileRef);
	}
	
	public MappingFileRef buildMappingFileRef(PersistenceUnit parent, XmlMappingFileRef xmlMappingFileRef) {
		return new GenericMappingFileRef(parent, xmlMappingFileRef);
	}
	
	public MappingFileRef buildImpliedMappingFileRef(PersistenceUnit parent) {
		return new ImpliedMappingFileRef(parent, JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH );
	}
	
	public ClassRef buildClassRef(PersistenceUnit parent, XmlJavaClassRef classRef) {
		return new GenericClassRef(parent, classRef);
	}
	
	public ClassRef buildClassRef(PersistenceUnit parent, String className) {
		return new GenericClassRef(parent, className);
	}
	
	public PersistenceUnit.Property buildProperty(PersistenceUnit parent, XmlProperty xmlProperty) {
		return new GenericPersistenceUnitProperty(parent, xmlProperty);
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
	
	public JavaCascade buildJavaCascade(JavaRelationshipMapping parent) {
		return new GenericJavaCascade(parent);
	}
	
	public JavaColumn buildJavaColumn(JavaJpaContextNode parent, JavaColumn.Owner owner) {
		return new GenericJavaColumn(parent, owner);
	}
	
	public JavaDiscriminatorColumn buildJavaDiscriminatorColumn(JavaEntity parent, JavaDiscriminatorColumn.Owner owner) {
		return new GenericJavaDiscriminatorColumn(parent, owner);
	}
	
	public JavaJoinColumn buildJavaJoinColumn(JavaJpaContextNode parent, JavaJoinColumn.Owner owner) {
		return new GenericJavaJoinColumn(parent, owner);
	}
	
	public JavaJoinTable buildJavaJoinTable(JavaJoinTableJoiningStrategy parent) {
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
	
	public JavaGeneratorContainer buildJavaGeneratorContainer(JavaJpaContextNode parent) {
		return new GenericJavaGeneratorContainer(parent);
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
	
	public JavaAttributeOverrideContainer buildJavaAttributeOverrideContainer(JavaJpaContextNode parent, JavaAttributeOverrideContainer.Owner owner) {
		return new GenericJavaAttributeOverrideContainer(parent, owner);
	}
	
	public JavaAssociationOverrideContainer buildJavaAssociationOverrideContainer(JavaEntity parent, AssociationOverrideContainer.Owner owner) {
		return new GenericJavaAssociationOverrideContainer(parent, owner);
	}
	
	public JavaAssociationOverrideContainer buildJavaAssociationOverrideContainer(JavaEmbeddedMapping2_0 parent, AssociationOverrideContainer.Owner owner) {
		return new NullJavaAssociationOverrideContainer(parent, owner);
	}
	
	public JavaAttributeOverride buildJavaAttributeOverride(JavaJpaContextNode parent, AttributeOverride.Owner owner) {
		return new GenericJavaAttributeOverride(parent, owner);
	}
	
	public JavaAssociationOverride buildJavaAssociationOverride(JavaJpaContextNode parent, AssociationOverride.Owner owner) {
		return new GenericJavaAssociationOverride(parent, owner);
	}
	
	public JavaAssociationOverrideRelationshipReference buildJavaAssociationOverrideRelationshipReference(JavaAssociationOverride parent) {
		return new GenericJavaAssociationOverrideRelationshipReference(parent);
	}
	
	public AssociationOverrideAnnotation buildJavaVirtualAssociationOverrideAnnotation(JavaResourcePersistentMember jrpm, String name, JoiningStrategy joiningStrategy) {
		return new VirtualAssociationOverride1_0Annotation(jrpm, name, joiningStrategy);
	}
	
	public JavaQueryContainer buildJavaQueryContainer(JavaJpaContextNode parent) {
		return new GenericJavaQueryContainer(parent);
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
	
	public JavaConverter buildJavaNullConverter(JavaAttributeMapping parent) {
		return new GenericJavaNullConverter(parent);
	}
	
	public JavaDerivedId2_0 buildJavaDerivedId(JavaSingleRelationshipMapping2_0 parent) {
		return new NullJavaDerivedId2_0(parent);
	}
	
	
	// ********** JAR Context Model **********

	public JarFile buildJarFile(JarFileRef parent, JavaResourcePackageFragmentRoot jarResourcePackageFragmentRoot) {
		return new GenericJarFile(parent, jarResourcePackageFragmentRoot);
	}
	
}
