/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.Orderable;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.UniqueConstraint;
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
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlQueryContainer;
import org.eclipse.jpt.core.resource.orm.XmlQueryHint;
import org.eclipse.jpt.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTransient;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.core.resource.orm.XmlVersion;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface OrmXmlContextNodeFactory
{
	// ********** ORM Context Model **********

	EntityMappings buildEntityMappings(OrmXml parent, XmlEntityMappings entityMappings);
	
	PersistenceUnitMetadata buildPersistenceUnitMetadata(EntityMappings parent);
	
	OrmPersistenceUnitDefaults buildPersistenceUnitDefaults(PersistenceUnitMetadata parent);
	
	OrmPersistentType buildOrmPersistentType(EntityMappings parent, XmlTypeMapping resourceMapping);
	
	OrmEntity buildOrmEntity(OrmPersistentType parent, XmlEntity resourceMapping);
	
	OrmMappedSuperclass buildOrmMappedSuperclass(OrmPersistentType parent, XmlMappedSuperclass resourceMapping);
	
	OrmEmbeddable buildOrmEmbeddable(OrmPersistentType parent, XmlEmbeddable resourceMapping);
	
	OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, OrmPersistentAttribute.Owner owner, XmlAttributeMapping resourceMapping);
	
	OrmTable buildOrmTable(OrmEntity parent, Table.Owner owner);
	
	OrmSecondaryTable buildOrmSecondaryTable(OrmEntity parent, Table.Owner owner, XmlSecondaryTable xmlSecondaryTable);
	
	OrmPrimaryKeyJoinColumn buildOrmPrimaryKeyJoinColumn(XmlContextNode parent, OrmBaseJoinColumn.Owner owner, XmlPrimaryKeyJoinColumn resourcePkJoinColumn);
	
	OrmJoinTable buildOrmJoinTable(OrmJoinTableJoiningStrategy parent, Table.Owner owner, XmlJoinTable resourceJoinTable);
	
	OrmJoinColumn buildOrmJoinColumn(XmlContextNode parent, OrmJoinColumn.Owner owner, XmlJoinColumn resourceJoinColumn);
	
	OrmAttributeOverrideContainer buildOrmAttributeOverrideContainer(XmlContextNode parent, OrmAttributeOverrideContainer.Owner owner);

	OrmAssociationOverrideContainer buildOrmAssociationOverrideContainer(XmlContextNode parent, OrmAssociationOverrideContainer.Owner owner);

	OrmAttributeOverride buildOrmAttributeOverride(OrmAttributeOverrideContainer parent, OrmAttributeOverride.Owner owner, XmlAttributeOverride xmlAttributeOverride);
	
	OrmAssociationOverride buildOrmAssociationOverride(OrmAssociationOverrideContainer parent, OrmAssociationOverride.Owner owner, XmlAssociationOverride associationOverride);
	
	OrmAssociationOverrideRelationshipReference buildOrmAssociationOverrideRelationshipReference(OrmAssociationOverride parent, XmlAssociationOverride associationOverride);

	OrmDiscriminatorColumn buildOrmDiscriminatorColumn(OrmEntity parent, OrmDiscriminatorColumn.Owner owner);
	
	OrmColumn buildOrmColumn(XmlContextNode parent, OrmColumn.Owner owner);
	
	OrmGeneratedValue buildOrmGeneratedValue(XmlContextNode parent, XmlGeneratedValue resourceGeneratedValue);
	
	OrmGeneratorContainer buildOrmGeneratorContainer(XmlContextNode parent, XmlGeneratorContainer resourceGeneratorContainer);

	OrmSequenceGenerator buildOrmSequenceGenerator(XmlContextNode parent, XmlSequenceGenerator resourceSequenceGenerator);
	
	OrmTableGenerator buildOrmTableGenerator(XmlContextNode parent, XmlTableGenerator resourceTableGenerator);
	
	OrmQueryContainer buildOrmQueryContainer(XmlContextNode parent, XmlQueryContainer resourceQueryContainer);

	OrmNamedNativeQuery buildOrmNamedNativeQuery(XmlContextNode parent, XmlNamedNativeQuery resourceNamedQuery);
	
	OrmNamedQuery buildOrmNamedQuery(XmlContextNode parent, XmlNamedQuery resourceNamedQuery);
	
	OrmQueryHint buildOrmQueryHint(OrmQuery parent, XmlQueryHint resourceQueryhint);
	
	OrmBasicMapping buildOrmBasicMapping(OrmPersistentAttribute parent, XmlBasic resourceMapping);
	
	OrmManyToManyMapping buildOrmManyToManyMapping(OrmPersistentAttribute parent, XmlManyToMany resourceMapping);
	
	OrmOneToManyMapping buildOrmOneToManyMapping(OrmPersistentAttribute parent, XmlOneToMany resourceMapping);
	
	OrmManyToOneMapping buildOrmManyToOneMapping(OrmPersistentAttribute parent, XmlManyToOne resourceMapping);
	
	OrmOneToOneMapping buildOrmOneToOneMapping(OrmPersistentAttribute parent, XmlOneToOne resourceMapping);
	
	OrmEmbeddedIdMapping buildOrmEmbeddedIdMapping(OrmPersistentAttribute parent, XmlEmbeddedId resourceMapping);
	
	OrmEmbeddedMapping buildOrmEmbeddedMapping(OrmPersistentAttribute parent, XmlEmbedded resourceMapping);
	
	OrmIdMapping buildOrmIdMapping(OrmPersistentAttribute parent, XmlId resourceMapping);
	
	OrmTransientMapping buildOrmTransientMapping(OrmPersistentAttribute parent, XmlTransient resourceMapping);
	
	OrmVersionMapping buildOrmVersionMapping(OrmPersistentAttribute parent, XmlVersion resourceMapping);
	
	OrmAttributeMapping buildOrmNullAttributeMapping(OrmPersistentAttribute parent, XmlNullAttributeMapping resourceMapping);
	
	OrmAttributeMapping buildUnsupportedOrmAttributeMapping(OrmPersistentAttribute parent, XmlNullAttributeMapping resourceMapping);

	OrmUniqueConstraint buildOrmUniqueConstraint(XmlContextNode parent, UniqueConstraint.Owner owner, XmlUniqueConstraint resourceUniqueConstraint);
	
	OrmConverter buildOrmEnumeratedConverter(OrmAttributeMapping parent, XmlConvertibleMapping resourceMapping);
	
	OrmConverter buildOrmTemporalConverter(OrmAttributeMapping parent, XmlConvertibleMapping resourceMapping);
	
	OrmConverter buildOrmLobConverter(OrmAttributeMapping parent, XmlConvertibleMapping resourceMapping);

	OrmConverter buildOrmNullConverter(OrmAttributeMapping parent);
	
	OrmOrderable buildOrmOrderable(OrmAttributeMapping parent, Orderable.Owner owner);
	
	// ********** ORM Virtual Resource Model **********
	
	XmlAssociationOverride buildVirtualXmlAssociationOverride(String name, OrmTypeMapping parent, JoiningStrategy joiningStrategy);
	
	XmlBasic buildVirtualXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping);
	
	XmlEmbeddedId buildVirtualXmlEmbeddedId(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping);
	
	XmlEmbedded buildVirtualXmlEmbedded(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping);
	
	XmlId buildVirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping);
	
	XmlManyToOne buildVirtualXmlManyToOne(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping);
	
	XmlManyToMany buildVirtualXmlManyToMany(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping);
	
	XmlOneToMany buildVirtualXmlOneToMany(OrmTypeMapping ormTypeMapping, JavaOneToManyMapping javaOneToManyMapping);
	
	XmlOneToOne buildVirtualXmlOneToOne(OrmTypeMapping ormTypeMapping, JavaOneToOneMapping javaOneToOneMapping);
	
	XmlTransient buildVirtualXmlTransient(OrmTypeMapping ormTypeMapping, JavaTransientMapping javaTransientMapping);
	
	XmlVersion buildVirtualXmlVersion(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping);
	
	XmlNullAttributeMapping buildVirtualXmlNullAttributeMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping);

}
