/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.Orderable;
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
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmBaseJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.context.orm.OrmDiscriminatorColumn;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmNamedColumn;
import org.eclipse.jpt.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmQuery;
import org.eclipse.jpt.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmTable;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericEntityMappings;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmAssociationOverride;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmAssociationOverrideContainer;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmAttributeOverride;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmAttributeOverrideContainer;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmBasicMapping;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmColumn;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmDiscriminatorColumn;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmEmbeddable;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmEmbeddedMapping;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmEntity;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmEnumeratedConverter;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmGeneratedValue;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmGeneratorContainer;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmIdMapping;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmJoinColumn;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmJoinTable;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmLobConverter;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmManyToManyMapping;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmManyToOneMapping;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmMappedSuperclass;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmNamedNativeQuery;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmNamedQuery;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmNullAttributeMapping;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmNullConverter;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmOneToManyMapping;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmOneToOneMapping;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmOrderable;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmPersistentAttribute;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmPersistentType;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmQueryContainer;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmQueryHint;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmSecondaryTable;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmSequenceGenerator;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmTable;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmTableGenerator;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmTemporalConverter;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmTransientMapping;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmUniqueConstraint;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmVersionMapping;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericPersistenceUnitDefaults;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericPersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.jpa1.context.orm.UnsupportedOrmAttributeMapping;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmOrderColumn2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.NullOrmCacheable2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.NullOrmDerivedIdentity2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.NullOrmOrphanRemoval2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCacheable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCacheableHolder2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmEmbeddedMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrderColumn2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrderable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmXml2_0ContextNodeFactory;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;
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
import org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0;

public abstract class AbstractOrmXmlContextNodeFactory
	implements OrmXml2_0ContextNodeFactory
{
	public EntityMappings buildEntityMappings(OrmXml parent, XmlEntityMappings xmlEntityMappings) {
		return new GenericEntityMappings(parent, xmlEntityMappings);
	}
	
	public PersistenceUnitMetadata buildPersistenceUnitMetadata(EntityMappings parent) {
		return new GenericPersistenceUnitMetadata(parent);
	}
	
	public OrmPersistenceUnitDefaults buildPersistenceUnitDefaults(PersistenceUnitMetadata parent) {
		return new GenericPersistenceUnitDefaults(parent);
	}

	public OrmPersistentType buildOrmPersistentType(EntityMappings parent, XmlTypeMapping resourceMapping) {
		return new GenericOrmPersistentType(parent, resourceMapping);
	}
	
	public OrmEntity buildOrmEntity(OrmPersistentType parent, XmlEntity resourceMapping) {
		return new GenericOrmEntity(parent, resourceMapping);
	}
	
	public OrmMappedSuperclass buildOrmMappedSuperclass(OrmPersistentType parent, XmlMappedSuperclass resourceMapping) {
		return new GenericOrmMappedSuperclass(parent, resourceMapping);
	}
	
	public OrmEmbeddable buildOrmEmbeddable(OrmPersistentType parent, XmlEmbeddable resourceMapping) {
		return new GenericOrmEmbeddable(parent, resourceMapping);
	}
	
	public OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, OrmPersistentAttribute.Owner owner, XmlAttributeMapping resourceMapping) {
		return new GenericOrmPersistentAttribute(parent, owner, resourceMapping);
	}
	
	public OrmTable buildOrmTable(OrmEntity parent) {
		return new GenericOrmTable(parent);
	}
	
	public OrmSecondaryTable buildOrmSecondaryTable(OrmEntity parent, XmlSecondaryTable xmlSecondaryTable) {
		return new GenericOrmSecondaryTable(parent, xmlSecondaryTable);
	}
	
	public OrmPrimaryKeyJoinColumn buildOrmPrimaryKeyJoinColumn(XmlContextNode parent, OrmBaseJoinColumn.Owner owner, XmlPrimaryKeyJoinColumn resourcePkJoinColumn) {
		return new GenericOrmPrimaryKeyJoinColumn(parent, owner, resourcePkJoinColumn);
	}
	
	public OrmJoinTable buildOrmJoinTable(OrmJoinTableJoiningStrategy parent, XmlJoinTable resourceJoinTable) {
		return new GenericOrmJoinTable(parent, resourceJoinTable);
	}
	
	public OrmJoinColumn buildOrmJoinColumn(XmlContextNode parent, OrmJoinColumn.Owner owner, XmlJoinColumn resourceJoinColumn) {
		return new GenericOrmJoinColumn(parent, owner, resourceJoinColumn);
	}
	
	public OrmAttributeOverrideContainer buildOrmAttributeOverrideContainer(XmlContextNode parent, OrmAttributeOverrideContainer.Owner owner) {
		return new GenericOrmAttributeOverrideContainer(parent, owner);
	}
	
	public OrmAssociationOverrideContainer buildOrmAssociationOverrideContainer(XmlContextNode parent, OrmAssociationOverrideContainer.Owner owner) {
		return new GenericOrmAssociationOverrideContainer(parent, owner);
	}
	
	public OrmAssociationOverrideContainer buildOrmAssociationOverrideContainer(OrmEmbeddedMapping2_0 parent, OrmAssociationOverrideContainer.Owner owner) {
		return new NullOrmAssociationOverrideContainer(parent, owner);
	}
	
	public OrmAttributeOverride buildOrmAttributeOverride(OrmAttributeOverrideContainer parent, AttributeOverride.Owner owner, XmlAttributeOverride xmlAttributeOverride) {
		return new GenericOrmAttributeOverride(parent, owner, xmlAttributeOverride);
	}
	
	public OrmAssociationOverride buildOrmAssociationOverride(OrmAssociationOverrideContainer parent, AssociationOverride.Owner owner, XmlAssociationOverride xmlAssociationOverride) {
		return new GenericOrmAssociationOverride(parent, owner, xmlAssociationOverride);
	}
	
	public OrmAssociationOverrideRelationshipReference buildOrmAssociationOverrideRelationshipReference(OrmAssociationOverride parent, XmlAssociationOverride associationOverride) {
		return new GenericOrmAssociationOverrideRelationshipReference(parent, associationOverride);
	}
	
	public OrmDiscriminatorColumn buildOrmDiscriminatorColumn(OrmEntity parent, OrmDiscriminatorColumn.Owner owner) {
		return new GenericOrmDiscriminatorColumn(parent, owner);
	}
	
	public OrmColumn buildOrmColumn(XmlContextNode parent, OrmColumn.Owner owner) {
		return new GenericOrmColumn(parent, owner);
	}
	
	public OrmGeneratedValue buildOrmGeneratedValue(XmlContextNode parent, XmlGeneratedValue resourceGeneratedValue) {
		return new GenericOrmGeneratedValue(parent, resourceGeneratedValue);
	}
	
	public OrmGeneratorContainer buildOrmGeneratorContainer(XmlContextNode parent, XmlGeneratorContainer resourceGeneratorContainer) {
		return new GenericOrmGeneratorContainer(parent, resourceGeneratorContainer);
	}
	
	public OrmSequenceGenerator buildOrmSequenceGenerator(XmlContextNode parent, XmlSequenceGenerator resourceSequenceGenerator) {
		return new GenericOrmSequenceGenerator(parent, resourceSequenceGenerator);
	}
	
	public OrmTableGenerator buildOrmTableGenerator(XmlContextNode parent, XmlTableGenerator resourceTableGenerator) {
		return new GenericOrmTableGenerator(parent, resourceTableGenerator);
	}
	
	public OrmQueryContainer buildOrmQueryContainer(XmlContextNode parent, XmlQueryContainer resourceQueryContainer) {
		return new GenericOrmQueryContainer(parent, resourceQueryContainer);
	}
	
	public OrmNamedNativeQuery buildOrmNamedNativeQuery(XmlContextNode parent, XmlNamedNativeQuery resourceNamedNativeQuery) {
		return new GenericOrmNamedNativeQuery(parent, resourceNamedNativeQuery);
	}
	
	public OrmNamedQuery buildOrmNamedQuery(XmlContextNode parent, XmlNamedQuery resourceNamedQuery) {
		return new GenericOrmNamedQuery(parent, resourceNamedQuery);
	}
	
	public OrmQueryHint buildOrmQueryHint(OrmQuery parent, XmlQueryHint resourceQueryHint) {
		return new GenericOrmQueryHint(parent, resourceQueryHint);
	}
	
	public OrmBasicMapping buildOrmBasicMapping(OrmPersistentAttribute parent, XmlBasic resourceMapping) {
		return new GenericOrmBasicMapping(parent, resourceMapping);
	}
	
	public OrmEmbeddedMapping buildOrmEmbeddedMapping(OrmPersistentAttribute parent, XmlEmbedded resourceMapping) {
		return new GenericOrmEmbeddedMapping(parent, resourceMapping);
	}
	
	public OrmEmbeddedIdMapping buildOrmEmbeddedIdMapping(OrmPersistentAttribute parent, XmlEmbeddedId resourceMapping) {
		return new GenericOrmEmbeddedIdMapping(parent, resourceMapping);
	}
	
	public OrmIdMapping buildOrmIdMapping(OrmPersistentAttribute parent, XmlId resourceMapping) {
		return new GenericOrmIdMapping(parent, resourceMapping);
	}
	
	public OrmManyToManyMapping buildOrmManyToManyMapping(OrmPersistentAttribute parent, XmlManyToMany resourceMapping) {
		return new GenericOrmManyToManyMapping(parent, resourceMapping);
	}
	
	public OrmManyToOneMapping buildOrmManyToOneMapping(OrmPersistentAttribute parent, XmlManyToOne resourceMapping) {
		return new GenericOrmManyToOneMapping(parent, resourceMapping);
	}
	
	public OrmOneToManyMapping buildOrmOneToManyMapping(OrmPersistentAttribute parent, XmlOneToMany resourceMapping) {
		return new GenericOrmOneToManyMapping(parent, resourceMapping);
	}
	
	public OrmOneToOneMapping buildOrmOneToOneMapping(OrmPersistentAttribute parent, XmlOneToOne resourceMapping) {
		return new GenericOrmOneToOneMapping(parent, resourceMapping);
	}
	
	public OrmTransientMapping buildOrmTransientMapping(OrmPersistentAttribute parent, XmlTransient resourceMapping) {
		return new GenericOrmTransientMapping(parent, resourceMapping);
	}
	
	public OrmVersionMapping buildOrmVersionMapping(OrmPersistentAttribute parent, XmlVersion resourceMapping) {
		return new GenericOrmVersionMapping(parent, resourceMapping);
	}
	
	public OrmAttributeMapping buildOrmNullAttributeMapping(OrmPersistentAttribute parent, XmlNullAttributeMapping resourceMapping) {
		return new GenericOrmNullAttributeMapping(parent, resourceMapping);
	}
	
	public OrmAttributeMapping buildUnsupportedOrmAttributeMapping(OrmPersistentAttribute parent, XmlNullAttributeMapping resourceMapping) {
		return new UnsupportedOrmAttributeMapping(parent, resourceMapping);
	}
	
	public OrmUniqueConstraint buildOrmUniqueConstraint(XmlContextNode parent, UniqueConstraint.Owner owner, XmlUniqueConstraint resourceUniqueConstraint) {
		return new GenericOrmUniqueConstraint(parent, owner, resourceUniqueConstraint);
	}
	
	public OrmConverter buildOrmEnumeratedConverter(OrmAttributeMapping parent, XmlConvertibleMapping resourceMapping) {
		return new GenericOrmEnumeratedConverter(parent, resourceMapping);
	}
	
	public OrmConverter buildOrmLobConverter(OrmAttributeMapping parent, XmlConvertibleMapping resourceMapping) {
		return new GenericOrmLobConverter(parent, resourceMapping);
	}
	
	public OrmConverter buildOrmTemporalConverter(OrmAttributeMapping parent, XmlConvertibleMapping resourceMapping) {
		return new GenericOrmTemporalConverter(parent, resourceMapping);
	}
	
	public OrmConverter buildOrmNullConverter(OrmAttributeMapping parent) {
		return new GenericOrmNullConverter(parent);
	}
	
	public OrmOrderable2_0 buildOrmOrderable(OrmAttributeMapping parent, Orderable.Owner owner) {
		return new GenericOrmOrderable(parent, owner);
	}
	
	public OrmOrderColumn2_0 buildOrmOrderColumn(OrmOrderable2_0 parent, OrmNamedColumn.Owner owner) {
		return new GenericOrmOrderColumn2_0(parent, owner);
	}
	
	public OrmDerivedIdentity2_0 buildOrmDerivedIdentity(
			OrmSingleRelationshipMapping2_0 parent, XmlSingleRelationshipMapping_2_0 resource) {
		return new NullOrmDerivedIdentity2_0(parent);
	}
	
	public OrmElementCollectionMapping2_0 buildOrmElementCollectionMapping2_0(
			OrmPersistentAttribute parent, XmlElementCollection resourceMapping) {
		
		throw new UnsupportedOperationException();
	}
	
	public OrmCacheable2_0 buildOrmCacheable(OrmCacheableHolder2_0 parent, XmlCacheable_2_0 resource) {
		return new NullOrmCacheable2_0(parent);
	}
	
	public OrmOrphanRemovable2_0 buildOrmOrphanRemoval(OrmOrphanRemovalHolder2_0 parent, XmlOrphanRemovable_2_0 resource) {
		return new NullOrmOrphanRemoval2_0(parent);
	}
	
	public OrmCollectionTable2_0 buildOrmCollectionTable(OrmElementCollectionMapping2_0 parent, XmlCollectionTable resource) {
		throw new UnsupportedOperationException();
	}
	
	// ********** ORM Virtual Resource Model **********

	public XmlAssociationOverride buildVirtualXmlAssociationOverride(String name, OrmTypeMapping parent, JoiningStrategy joiningStrategy) {
		return new VirtualXmlAssociationOverride(name, parent, joiningStrategy);		
	}
	
	public XmlBasic buildVirtualXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		return new VirtualXmlBasic(ormTypeMapping, javaBasicMapping);
	}

	public XmlEmbeddedId buildVirtualXmlEmbeddedId(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		return new VirtualXmlEmbeddedId(ormTypeMapping, javaEmbeddedIdMapping);
	}

	public XmlEmbedded buildVirtualXmlEmbedded(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping) {
		return new VirtualXmlEmbedded(ormTypeMapping, javaEmbeddedMapping);
	}

	public XmlId buildVirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		return new VirtualXmlId(ormTypeMapping, javaIdMapping);
	}

	public XmlManyToMany buildVirtualXmlManyToMany(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		return new VirtualXmlManyToMany(ormTypeMapping, javaManyToManyMapping);
	}

	public XmlManyToOne buildVirtualXmlManyToOne(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping) {
		return new VirtualXmlManyToOne(ormTypeMapping, javaManyToOneMapping);
	}

	public XmlOneToMany buildVirtualXmlOneToMany(OrmTypeMapping ormTypeMapping, JavaOneToManyMapping javaOneToManyMapping) {
		return new VirtualXmlOneToMany(ormTypeMapping, javaOneToManyMapping);
	}

	public XmlOneToOne buildVirtualXmlOneToOne(OrmTypeMapping ormTypeMapping, JavaOneToOneMapping javaOneToOneMapping) {
		return new VirtualXmlOneToOne(ormTypeMapping, javaOneToOneMapping);
	}

	public XmlTransient buildVirtualXmlTransient(OrmTypeMapping ormTypeMapping, JavaTransientMapping javaTransientMapping) {
		return new VirtualXmlTransient(ormTypeMapping, javaTransientMapping);
	}

	public XmlVersion buildVirtualXmlVersion(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		return new VirtualXmlVersion(ormTypeMapping, javaVersionMapping);
	}
	
	public XmlNullAttributeMapping buildVirtualXmlNullAttributeMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping) {
		return new VirtualXmlNullAttributeMapping(ormTypeMapping, javaAttributeMapping);
	}
	
	public XmlElementCollection buildVirtualXmlElementCollection2_0(OrmTypeMapping ormTypeMapping, JavaElementCollectionMapping2_0 javaMapping) {
		throw new UnsupportedOperationException();
	}
}
