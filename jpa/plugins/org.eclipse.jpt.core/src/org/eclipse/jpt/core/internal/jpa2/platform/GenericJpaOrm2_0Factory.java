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

import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.internal.jpa2.context.GenericOrmGeneratorContainer2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericEntityMappings2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmAssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmEmbeddable2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmEntity2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmManyToOneMapping2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmOneToOneMapping2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmPersistentAttribute2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmPersistentType2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmSequenceGenerator2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmXml2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericPersistenceUnitMetadata2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlAssociationOverride2_0;
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
import org.eclipse.jpt.core.internal.platform.AbstractOrmXmlContextNodeFactory;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.MappingFileRef2_0;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlBasic;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlEntity;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlId;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlManyToOne;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlTransient;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlVersion;
import org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;

public class GenericJpaOrm2_0Factory extends AbstractOrmXmlContextNodeFactory
{	

	public OrmXml buildMappingFile(MappingFileRef parent, JpaXmlResource resource) {
		return new GenericOrmXml2_0((MappingFileRef2_0) parent, resource);
	}

	@Override
	public EntityMappings buildEntityMappings(OrmXml parent, org.eclipse.jpt.core.resource.orm.XmlEntityMappings xmlEntityMappings) {
		return new GenericEntityMappings2_0(parent, (XmlEntityMappings) xmlEntityMappings);
	}
	
	@Override
	public PersistenceUnitMetadata buildPersistenceUnitMetadata(EntityMappings parent, org.eclipse.jpt.core.resource.orm.XmlEntityMappings xmlEntityMappings) {
		return new GenericPersistenceUnitMetadata2_0(parent, xmlEntityMappings);
	}

	@Override
	public OrmPersistentType buildOrmPersistentType(EntityMappings parent, XmlTypeMapping resourceMapping) {
		return new GenericOrmPersistentType2_0(parent, resourceMapping);
	}
	
	@Override
	public OrmEntity buildOrmEntity(OrmPersistentType parent, org.eclipse.jpt.core.resource.orm.XmlEntity resourceMapping) {
		return new GenericOrmEntity2_0(parent, (XmlEntity) resourceMapping);
	}
	
	@Override
	public OrmEmbeddable buildOrmEmbeddable(OrmPersistentType parent, org.eclipse.jpt.core.resource.orm.XmlEmbeddable resourceMapping) {
		return new GenericOrmEmbeddable2_0(parent, (XmlEmbeddable) resourceMapping);
	}
	
	@Override
	public OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, OrmPersistentAttribute.Owner owner, org.eclipse.jpt.core.resource.orm.XmlAttributeMapping resourceMapping) {
		return new GenericOrmPersistentAttribute2_0(parent, owner, resourceMapping);
	}
	
	@Override
	public OrmAssociationOverrideRelationshipReference buildOrmAssociationOverrideRelationshipReference(OrmAssociationOverride parent, org.eclipse.jpt.core.resource.orm.XmlAssociationOverride associationOverride) {
		return new GenericOrmAssociationOverrideRelationshipReference2_0(parent, (XmlAssociationOverride) associationOverride);
	}
	
	@Override
	public OrmGeneratorContainer buildOrmGeneratorContainer(XmlContextNode parent, XmlGeneratorContainer resourceGeneratorContainer) {
		return new GenericOrmGeneratorContainer2_0(parent, resourceGeneratorContainer);
	}
	
	@Override
	public OrmSequenceGenerator buildOrmSequenceGenerator(XmlContextNode parent, org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator resourceSequenceGenerator) {
		return new GenericOrmSequenceGenerator2_0(parent, (XmlSequenceGenerator) resourceSequenceGenerator);
	}
		
	@Override
	public OrmManyToOneMapping buildOrmManyToOneMapping(OrmPersistentAttribute parent, org.eclipse.jpt.core.resource.orm.XmlManyToOne resourceMapping) {
		return new GenericOrmManyToOneMapping2_0(parent, (XmlManyToOne) resourceMapping);
	}
	
	@Override
	public OrmOneToOneMapping buildOrmOneToOneMapping(OrmPersistentAttribute parent, org.eclipse.jpt.core.resource.orm.XmlOneToOne resourceMapping) {
		return new GenericOrmOneToOneMapping2_0(parent, (XmlOneToOne) resourceMapping);
	}

	
	// ********** ORM Virtual Resource Model **********

	@Override
	public XmlAssociationOverride buildVirtualXmlAssociationOverride(String name, OrmEntity parent, JoiningStrategy joiningStrategy) {
		return new VirtualXmlAssociationOverride2_0(name, parent, joiningStrategy);		
	}

	@Override
	public XmlBasic buildVirtualXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		return new VirtualXmlBasic2_0(ormTypeMapping, javaBasicMapping);
	}
	
	@Override
	public XmlId buildVirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		return new VirtualXmlId2_0(ormTypeMapping, javaIdMapping);
	}
	
	@Override
	public XmlEmbeddedId buildVirtualXmlEmbeddedId(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		return new VirtualXmlEmbeddedId2_0(ormTypeMapping, javaEmbeddedIdMapping);
	}
	
	@Override
	public XmlEmbedded buildVirtualXmlEmbedded(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping) {
		return new VirtualXmlEmbedded2_0(ormTypeMapping, javaEmbeddedMapping);
	}
	
	@Override
	public XmlManyToMany buildVirtualXmlManyToMany(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		return new VirtualXmlManyToMany2_0(ormTypeMapping, javaManyToManyMapping);
	}
	
	@Override
	public XmlManyToOne buildVirtualXmlManyToOne(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping) {
		return new VirtualXmlManyToOne2_0(ormTypeMapping, (JavaManyToOneMapping2_0) javaManyToOneMapping);
	}
	
	@Override
	public XmlOneToMany buildVirtualXmlOneToMany(OrmTypeMapping ormTypeMapping, JavaOneToManyMapping javaOneToManyMapping) {
		return new VirtualXmlOneToMany2_0(ormTypeMapping, javaOneToManyMapping);
	}
	
	@Override
	public XmlOneToOne buildVirtualXmlOneToOne(OrmTypeMapping ormTypeMapping, JavaOneToOneMapping javaOneToOneMapping) {
		return new VirtualXmlOneToOne2_0(ormTypeMapping, (JavaOneToOneMapping2_0) javaOneToOneMapping);
	}
	
	@Override
	public XmlTransient buildVirtualXmlTransient(OrmTypeMapping ormTypeMapping, JavaTransientMapping javaTransientMapping) {
		return new VirtualXmlTransient2_0(ormTypeMapping, javaTransientMapping);
	}
	
	@Override
	public XmlVersion buildVirtualXmlVersion(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		return new VirtualXmlVersion2_0(ormTypeMapping, javaVersionMapping);
	}
	
	@Override
	public XmlNullAttributeMapping buildVirtualXmlNullAttributeMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping) {
		return new VirtualXmlNullAttributeMapping2_0(ormTypeMapping, javaAttributeMapping);
	}
}
