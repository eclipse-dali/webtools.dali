/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.platform;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.internal.jpa2.GenericJpaProject2_0;
import org.eclipse.jpt.core.internal.jpa2.context.GenericRootContextNode2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaAssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaEmbeddable2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaManyToOneMapping2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaOneToOneMapping2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaPersistentAttribute2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaPersistentType2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaSequenceGenerator2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.VirtualAssociationOverride2_0Annotation;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericEntityMappings2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmEmbeddable2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmPersistentAttribute2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmPersistentType2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmSequenceGenerator2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmXml2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericPersistenceUnitMetadata2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlBasic2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlEmbedded2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlEmbeddedId2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlId2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlManyToMany2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlManyToOne2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlNullAttributeMapping2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlOneToMany2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlOneToOne2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlTransient2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlVersion2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.GenericClassRef2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.GenericJarFileRef2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.GenericMappingFileRef2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.GenericPersistence2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.GenericPersistenceUnit2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.GenericPersistenceXml2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.ImpliedMappingFileRef2_0;
import org.eclipse.jpt.core.internal.platform.AbstractJpaFactory;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.core.jpa2.context.JpaRootContextNode2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSequenceGenerator2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.ClassRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.MappingFileRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.Persistence2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceXml2_0;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlBasic;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlEntity;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlId;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToOne;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlTransient;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlVersion;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;


/**
 * Central class that allows extenders to easily replace implementations of
 * various Dali interfaces.
 */
public class GenericJpaFactory2_0
	extends AbstractJpaFactory
	implements JpaFactory2_0
{
	public GenericJpaFactory2_0() {
		super();
	}
	
	
	// ********** Core Model **********

	@Override
	public JpaProject2_0 buildJpaProject(JpaProject.Config config) throws CoreException {
		return new GenericJpaProject2_0(config);
	}
	
	
	// ********** Context Nodes **********
	
	@Override
	public JpaRootContextNode2_0 buildRootContextNode(JpaProject parent) {
		return new GenericRootContextNode2_0((JpaProject2_0) parent);
	}

	public MappingFile buildMappingFile2_0(MappingFileRef parent, JpaXmlResource resource) {
		return buildOrmXml2_0((MappingFileRef2_0) parent, resource);
	}
	
	protected GenericOrmXml2_0 buildOrmXml2_0(MappingFileRef parent, JpaXmlResource resource) {
		return new GenericOrmXml2_0((MappingFileRef2_0) parent, resource);
	}

	
	// ********** Persistence Context Model **********
	
	@Override
	public PersistenceXml2_0 buildPersistenceXml(JpaRootContextNode parent, JpaXmlResource resource) {
		return new GenericPersistenceXml2_0((JpaRootContextNode2_0) parent, resource);
	}
	
	@Override
	public Persistence2_0 buildPersistence(PersistenceXml parent, XmlPersistence xmlPersistence) {
		return new GenericPersistence2_0((PersistenceXml2_0) parent, xmlPersistence);
	}
	
	@Override
	public PersistenceUnit2_0 buildPersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		return new GenericPersistenceUnit2_0((Persistence2_0) parent, xmlPersistenceUnit);
	}
	
	@Override
	public MappingFileRef2_0 buildMappingFileRef(PersistenceUnit parent, XmlMappingFileRef xmlMappingFileRef) {
		return new GenericMappingFileRef2_0((PersistenceUnit2_0) parent, xmlMappingFileRef);
	}
	
	@Override
	public MappingFileRef2_0 buildImpliedMappingFileRef(PersistenceUnit parent) {
		return new ImpliedMappingFileRef2_0((PersistenceUnit2_0) parent, JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH );
	}
	
	@Override
	public ClassRef2_0 buildClassRef(PersistenceUnit parent, XmlJavaClassRef classRef) {
		return new GenericClassRef2_0((PersistenceUnit2_0) parent, classRef);
	}
	
	@Override
	public ClassRef2_0 buildClassRef(PersistenceUnit parent, String className) {
		return new GenericClassRef2_0((PersistenceUnit2_0) parent, className);
	}
	
	@Override
	public JarFileRef buildJarFileRef(PersistenceUnit parent, XmlJarFileRef xmlJarFileRef) {
		return new GenericJarFileRef2_0((PersistenceUnit2_0) parent, xmlJarFileRef);
	}
	
	
	// ********** Java Context Model **********
	
	@Override
	public JavaPersistentType buildJavaPersistentType(PersistentType.Owner owner, JavaResourcePersistentType jrpt) {
		return new GenericJavaPersistentType2_0(owner, jrpt);
	}
	
	@Override
	public JavaPersistentAttribute buildJavaPersistentAttribute(PersistentType parent, JavaResourcePersistentAttribute jrpa) {
		return new GenericJavaPersistentAttribute2_0(parent, jrpa);
	}
	
	@Override
	public JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent) {
		return new GenericJavaEmbeddable2_0(parent);
	}
	
	@Override
	public JavaManyToOneMapping buildJavaManyToOneMapping(JavaPersistentAttribute parent) {
		return new GenericJavaManyToOneMapping2_0(parent);
	}
	
	@Override
	public JavaOneToOneMapping buildJavaOneToOneMapping(JavaPersistentAttribute parent) {
		return new GenericJavaOneToOneMapping2_0(parent);
	}
	
	@Override
	public JavaSequenceGenerator buildJavaSequenceGenerator(JavaJpaContextNode parent) {
		return new GenericJavaSequenceGenerator2_0(parent);
	}
	
	@Override
	public JavaAssociationOverrideRelationshipReference buildJavaAssociationOverrideRelationshipReference(JavaAssociationOverride parent) {
		return new GenericJavaAssociationOverrideRelationshipReference2_0(parent);
	}
	
	@Override
	public AssociationOverrideAnnotation buildJavaVirtualAssociationOverrideAnnotation(JavaResourcePersistentType jrpt, String name, JoiningStrategy joiningStrategy) {
		return new VirtualAssociationOverride2_0Annotation(jrpt, name, joiningStrategy);
	}
	
	
	// ********** Generic 2.0-specific ORM Context Model **********
	
	public EntityMappings buildEntityMappings2_0(GenericOrmXml2_0 parent, XmlEntityMappings xmlEntityMappings) {
		return new GenericEntityMappings2_0(parent, xmlEntityMappings);
	}
	
	public PersistenceUnitMetadata buildPersistenceUnitMetadata2_0(GenericEntityMappings2_0 parent, XmlEntityMappings xmlEntityMappings) {
		return new GenericPersistenceUnitMetadata2_0(parent, xmlEntityMappings);
	}
	
	public OrmPersistentType buildOrmPersistentType2_0(EntityMappings parent, XmlTypeMapping resourceMapping) {
		return new GenericOrmPersistentType2_0(parent, resourceMapping);
	}

	public OrmEmbeddable buildOrmEmbeddable2_0(OrmPersistentType parent, XmlEmbeddable resourceMapping) {
		return new GenericOrmEmbeddable2_0(parent, resourceMapping);
	}

	public OrmEntity buildOrmEntity2_0(OrmPersistentType parent, XmlEntity resourceMapping) {
		return buildOrmEntity(parent, resourceMapping);
	}

	public OrmMappedSuperclass buildOrmMappedSuperclass2_0(OrmPersistentType parent, XmlMappedSuperclass resourceMapping) {
		return buildOrmMappedSuperclass(parent, resourceMapping);
	}
	
	public OrmPersistentAttribute buildOrmPersistentAttribute2_0(OrmPersistentType parent, OrmPersistentAttribute.Owner owner, XmlAttributeMapping resourceMapping) {
		return new GenericOrmPersistentAttribute2_0(parent, owner, resourceMapping);
	}
	
	public OrmBasicMapping buildOrmBasicMapping2_0(OrmPersistentAttribute parent, XmlBasic resourceMapping) {
		return buildOrmBasicMapping(parent, resourceMapping);
	}
	
	public OrmEmbeddedMapping buildOrmEmbeddedMapping2_0(OrmPersistentAttribute parent, XmlEmbedded resourceMapping) {
		return buildOrmEmbeddedMapping(parent, resourceMapping);
	}
	
	public OrmEmbeddedIdMapping buildOrmEmbeddedIdMapping2_0(OrmPersistentAttribute parent, XmlEmbeddedId resourceMapping) {
		return buildOrmEmbeddedIdMapping(parent, resourceMapping);
	}
	
	public OrmIdMapping buildOrmIdMapping2_0(OrmPersistentAttribute parent, XmlId resourceMapping) {
		return buildOrmIdMapping(parent, resourceMapping);
	}
	
	public OrmManyToManyMapping buildOrmManyToManyMapping2_0(OrmPersistentAttribute parent, XmlManyToMany resourceMapping) {
		return buildOrmManyToManyMapping(parent, resourceMapping);
	}
	
	public OrmManyToOneMapping buildOrmManyToOneMapping2_0(OrmPersistentAttribute parent, XmlManyToOne resourceMapping) {
		return buildOrmManyToOneMapping(parent, resourceMapping);
	}
	
	public OrmOneToManyMapping buildOrmOneToManyMapping2_0(OrmPersistentAttribute parent, XmlOneToMany resourceMapping) {
		return buildOrmOneToManyMapping(parent, resourceMapping);
	}
	
	public OrmOneToOneMapping buildOrmOneToOneMapping2_0(OrmPersistentAttribute parent, XmlOneToOne resourceMapping) {
		return buildOrmOneToOneMapping(parent, resourceMapping);
	}
	
	public OrmTransientMapping buildOrmTransientMapping2_0(OrmPersistentAttribute parent, XmlTransient resourceMapping) {
		return buildOrmTransientMapping(parent, resourceMapping);
	}
	
	public OrmVersionMapping buildOrmVersionMapping2_0(OrmPersistentAttribute parent, XmlVersion resourceMapping) {
		return buildOrmVersionMapping(parent, resourceMapping);
	}
	
	public OrmAttributeMapping buildOrmNullAttributeMapping2_0(OrmPersistentAttribute parent, XmlNullAttributeMapping resourceMapping) {
		return buildOrmNullAttributeMapping(parent, resourceMapping);
	}

	public OrmSequenceGenerator2_0 buildOrmSequenceGenerator2_0(XmlContextNode parent, XmlSequenceGenerator resourceSequenceGenerator) {
		return new GenericOrmSequenceGenerator2_0(parent, resourceSequenceGenerator);
	}
	
	public XmlBasic buildVirtualXmlBasic2_0(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		return new VirtualXmlBasic2_0(ormTypeMapping, javaBasicMapping);
	}
	
	public XmlId buildVirtualXmlId2_0(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		return new VirtualXmlId2_0(ormTypeMapping, javaIdMapping);
	}
	
	public XmlEmbeddedId buildVirtualXmlEmbeddedId2_0(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		return new VirtualXmlEmbeddedId2_0(ormTypeMapping, javaEmbeddedIdMapping);
	}
	
	public XmlEmbedded buildVirtualXmlEmbedded2_0(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping) {
		return new VirtualXmlEmbedded2_0(ormTypeMapping, javaEmbeddedMapping);
	}
	
	public XmlManyToMany buildVirtualXmlManyToMany2_0(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		return new VirtualXmlManyToMany2_0(ormTypeMapping, javaManyToManyMapping);
	}
	
	public XmlManyToOne buildVirtualXmlManyToOne2_0(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping) {
		return new VirtualXmlManyToOne2_0(ormTypeMapping, javaManyToOneMapping);
	}
	
	public XmlOneToMany buildVirtualXmlOneToMany2_0(OrmTypeMapping ormTypeMapping, JavaOneToManyMapping javaOneToManyMapping) {
		return new VirtualXmlOneToMany2_0(ormTypeMapping, javaOneToManyMapping);
	}
	
	public XmlOneToOne buildVirtualXmlOneToOne2_0(OrmTypeMapping ormTypeMapping, JavaOneToOneMapping javaOneToOneMapping) {
		return new VirtualXmlOneToOne2_0(ormTypeMapping, javaOneToOneMapping);
	}
	
	public XmlTransient buildVirtualXmlTransient2_0(OrmTypeMapping ormTypeMapping, JavaTransientMapping javaTransientMapping) {
		return new VirtualXmlTransient2_0(ormTypeMapping, javaTransientMapping);
	}
	
	public XmlVersion buildVirtualXmlVersion2_0(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		return new VirtualXmlVersion2_0(ormTypeMapping, javaVersionMapping);
	}
	
	public XmlNullAttributeMapping buildVirtualXmlNullAttributeMapping2_0(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping) {
		return new VirtualXmlNullAttributeMapping2_0(ormTypeMapping, javaAttributeMapping);
	}
}
