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

import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.PersistentType.Owner;
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
import org.eclipse.jpt.core.context.orm.OrmGeneratorContainer;
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
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaEmbeddable2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaPersistentAttribute2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaPersistentType2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaSequenceGenerator2_0;
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
import org.eclipse.jpt.core.internal.jpa2.context.persistence.GenericPersistenceUnit2_0;
import org.eclipse.jpt.core.internal.platform.GenericJpaFactory;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSequenceGenerator2_0;
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
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;

/**
 * Central class that allows extenders to easily replace implementations of
 * various Dali interfaces.
 */
public class Generic2_0JpaFactory extends GenericJpaFactory
{
	public Generic2_0JpaFactory() {
		super();
	}
	
	@Override
	public JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent) {
		return new GenericJavaEmbeddable2_0(parent);
	}
	
	// ********** Context Nodes **********
	
	public MappingFile buildMappingFile2_0(MappingFileRef parent, JpaXmlResource resource) {
		return buildOrmXml2_0(parent, resource);
	}
	
	protected GenericOrmXml2_0 buildOrmXml2_0(MappingFileRef parent, JpaXmlResource resource) {
		return new GenericOrmXml2_0(parent, resource);
	}

	
	// ********** Persistence Context Model **********
	
	@Override
	public PersistenceUnit buildPersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		return new GenericPersistenceUnit2_0(parent, xmlPersistenceUnit);
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
	
	public OrmGeneratorContainer buildOrmGeneratorContainer2_0(XmlContextNode parent, XmlGeneratorContainer resourceGeneratorContainer) {
		return super.buildOrmGeneratorContainer(parent, resourceGeneratorContainer);
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

	// ********** Java Context Model **********

	@Override
	public JavaPersistentAttribute buildJavaPersistentAttribute(PersistentType parent, JavaResourcePersistentAttribute jrpa) {
		return new GenericJavaPersistentAttribute2_0(parent, jrpa);
	}
	
	@Override
	public JavaPersistentType buildJavaPersistentType(Owner owner, JavaResourcePersistentType jrpt) {
		return new GenericJavaPersistentType2_0(owner, jrpt);
	}

	@Override
	public JavaSequenceGenerator buildJavaSequenceGenerator(JavaJpaContextNode parent) {
		return new GenericJavaSequenceGenerator2_0(parent);
	}

}
