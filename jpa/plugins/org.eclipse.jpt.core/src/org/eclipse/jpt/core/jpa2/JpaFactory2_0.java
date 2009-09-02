/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.MappingFile;
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
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericEntityMappings2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmXml2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmAssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSequenceGenerator2_0;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlAssociationOverride;
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
import org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;

/**
 * JPA 2.0 factory
 *<p> 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @see org.eclipse.jpt.core.internal.platform.GenericJpaFactory2_0
 */
public interface JpaFactory2_0
	extends JpaFactory
{
	MappingFile buildMappingFile2_0(MappingFileRef parent, JpaXmlResource resource);

	EntityMappings buildEntityMappings2_0(GenericOrmXml2_0 parent, XmlEntityMappings xmlEntityMappings);

	PersistenceUnitMetadata buildPersistenceUnitMetadata2_0(GenericEntityMappings2_0 parent, XmlEntityMappings xmlEntityMappings);
	
	OrmPersistentType buildOrmPersistentType2_0(EntityMappings parent, XmlTypeMapping resourceMapping);

	OrmEmbeddable buildOrmEmbeddable2_0(OrmPersistentType parent, XmlEmbeddable resourceMapping);

	OrmEntity buildOrmEntity2_0(OrmPersistentType parent, XmlEntity resourceMapping);

	OrmMappedSuperclass buildOrmMappedSuperclass2_0(OrmPersistentType parent, XmlMappedSuperclass resourceMapping);

	OrmPersistentAttribute buildOrmPersistentAttribute2_0(OrmPersistentType parent, OrmPersistentAttribute.Owner owner, XmlAttributeMapping resourceMapping);

	OrmBasicMapping buildOrmBasicMapping2_0(OrmPersistentAttribute parent, XmlBasic resourceMapping);

	OrmIdMapping buildOrmIdMapping2_0(OrmPersistentAttribute parent, XmlId resourceMapping);

	OrmEmbeddedMapping buildOrmEmbeddedMapping2_0(OrmPersistentAttribute parent, XmlEmbedded resourceMapping);

	OrmEmbeddedIdMapping buildOrmEmbeddedIdMapping2_0(OrmPersistentAttribute parent, XmlEmbeddedId resourceMapping);

	OrmManyToManyMapping buildOrmManyToManyMapping2_0(OrmPersistentAttribute parent, XmlManyToMany resourceMapping);

	OrmManyToOneMapping buildOrmManyToOneMapping2_0(OrmPersistentAttribute parent, XmlManyToOne resourceMapping);

	OrmOneToManyMapping buildOrmOneToManyMapping2_0(OrmPersistentAttribute parent, XmlOneToMany resourceMapping);

	OrmOneToOneMapping buildOrmOneToOneMapping2_0(OrmPersistentAttribute parent, XmlOneToOne resourceMapping);

	OrmTransientMapping buildOrmTransientMapping2_0(OrmPersistentAttribute parent, XmlTransient resourceMapping);

	OrmVersionMapping buildOrmVersionMapping2_0(OrmPersistentAttribute parent, XmlVersion resourceMapping);

	OrmAttributeMapping buildOrmNullAttributeMapping2_0(OrmPersistentAttribute parent, XmlNullAttributeMapping resourceMapping);

	OrmGeneratorContainer buildOrmGeneratorContainer2_0(XmlContextNode parent, XmlGeneratorContainer resourceGeneratorContainer);

	OrmSequenceGenerator2_0 buildOrmSequenceGenerator2_0(XmlContextNode parent, XmlSequenceGenerator resourceSequenceGenerator);

	OrmAssociationOverride buildOrmAssociationOverride2_0(XmlContextNode parent, AssociationOverride.Owner owner, XmlAssociationOverride associationOverride);

	OrmAssociationOverrideRelationshipReference2_0 buildOrmAssociationOverrideRelationshipReference2_0(OrmAssociationOverride parent, XmlAssociationOverride associationOverride);

	XmlBasic buildVirtualXmlBasic2_0(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping);

	XmlId buildVirtualXmlId2_0(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping);

	XmlEmbeddedId buildVirtualXmlEmbeddedId2_0(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping);

	XmlEmbedded buildVirtualXmlEmbedded2_0(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping);

	XmlManyToMany buildVirtualXmlManyToMany2_0(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping);

	XmlManyToOne buildVirtualXmlManyToOne2_0(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping);

	XmlOneToMany buildVirtualXmlOneToMany2_0(OrmTypeMapping ormTypeMapping, JavaOneToManyMapping javaOneToManyMapping);

	XmlOneToOne buildVirtualXmlOneToOne2_0(OrmTypeMapping ormTypeMapping, JavaOneToOneMapping javaOneToOneMapping);

	XmlTransient buildVirtualXmlTransient2_0(OrmTypeMapping ormTypeMapping, JavaTransientMapping javaTransientMapping);

	XmlVersion buildVirtualXmlVersion2_0(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping);

	XmlNullAttributeMapping buildVirtualXmlNullAttributeMapping2_0(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping);

}
