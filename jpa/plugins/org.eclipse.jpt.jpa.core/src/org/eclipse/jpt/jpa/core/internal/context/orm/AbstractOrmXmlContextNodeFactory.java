/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.jpa.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOrderable;
import org.eclipse.jpt.jpa.core.context.orm.OrmOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualSecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericEntityMappings;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmAttributeOverride;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmBasicMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmEmbeddable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmEmbeddedMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmEntity;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmEnumeratedConverter;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmGeneratedValue;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmGeneratorContainer;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmIdMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmJoinColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmJoinTable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmLobConverter;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmManyToManyMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmManyToOneMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmMappedSuperclass;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmNamedNativeQuery;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmNamedQuery;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmNullAttributeMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmOrderable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmOverrideRelationship;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmPersistentType;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmQueryContainer;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmQueryHint;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmSecondaryTable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmSequenceGenerator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmTable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmTableGenerator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmTemporalConverter;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmTransientMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmVersionMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmVirtualColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmVirtualJoinColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmVirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmVirtualPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmVirtualUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.UnsupportedOrmAttributeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.jpa.core.resource.orm.XmlBasic;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntity;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.jpa.core.resource.orm.XmlGeneratorContainer;
import org.eclipse.jpt.jpa.core.resource.orm.XmlId;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.jpa.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.jpa.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.jpa.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlQueryContainer;
import org.eclipse.jpt.jpa.core.resource.orm.XmlQueryHint;
import org.eclipse.jpt.jpa.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.jpa.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTransient;
import org.eclipse.jpt.jpa.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.jpa.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.jpa.core.resource.orm.XmlVersion;

public abstract class AbstractOrmXmlContextNodeFactory
	implements OrmXmlContextNodeFactory
{
	public EntityMappings buildEntityMappings(OrmXml parent, XmlEntityMappings xmlEntityMappings) {
		return new GenericEntityMappings(parent, xmlEntityMappings);
	}
	
	public OrmPersistenceUnitMetadata buildOrmPersistenceUnitMetadata(EntityMappings parent) {
		return new GenericOrmPersistenceUnitMetadata(parent);
	}
	
	public OrmPersistenceUnitDefaults buildOrmPersistenceUnitDefaults(OrmPersistenceUnitMetadata parent) {
		return new GenericOrmPersistenceUnitDefaults(parent);
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
	
	public OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, XmlAttributeMapping xmlMapping) {
		return new GenericOrmPersistentAttribute(parent, xmlMapping);
	}
	
	public OrmReadOnlyPersistentAttribute buildVirtualOrmPersistentField(OrmPersistentType parent, JavaResourceField javaResourceField) {
		return new VirtualOrmPersistentAttribute(parent, javaResourceField);
	}

	public OrmReadOnlyPersistentAttribute buildVirtualOrmPersistentProperty(OrmPersistentType parent, JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter) {
		return new VirtualOrmPersistentAttribute(parent, javaResourceGetter, javaResourceSetter);
	}

	public OrmTable buildOrmTable(OrmEntity parent, Table.Owner owner) {
		return new GenericOrmTable(parent, owner);
	}
	
	public OrmSecondaryTable buildOrmSecondaryTable(OrmEntity parent, Table.Owner owner, XmlSecondaryTable xmlSecondaryTable) {
		return new GenericOrmSecondaryTable(parent, owner, xmlSecondaryTable);
	}

	public OrmVirtualSecondaryTable buildOrmVirtualSecondaryTable(OrmEntity parent, ReadOnlyTable.Owner owner, JavaSecondaryTable javaSecondaryTable) {
		return new GenericOrmVirtualSecondaryTable(parent, owner, javaSecondaryTable);
	}
	
	public OrmPrimaryKeyJoinColumn buildOrmPrimaryKeyJoinColumn(XmlContextNode parent, OrmReadOnlyBaseJoinColumn.Owner owner, XmlPrimaryKeyJoinColumn resourcePrimaryKeyJoinColumn) {
		return new GenericOrmPrimaryKeyJoinColumn(parent, owner, resourcePrimaryKeyJoinColumn);
	}

	public OrmVirtualPrimaryKeyJoinColumn buildOrmVirtualPrimaryKeyJoinColumn(XmlContextNode parent, OrmReadOnlyBaseJoinColumn.Owner owner, JavaPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn) {
		return new GenericOrmVirtualPrimaryKeyJoinColumn(parent, owner, javaPrimaryKeyJoinColumn);
	}
	
	public OrmJoinTable buildOrmJoinTable(OrmJoinTableRelationshipStrategy parent, Table.Owner owner) {
		return new GenericOrmJoinTable(parent, owner);
	}
	
	public OrmVirtualJoinTable buildOrmVirtualJoinTable(OrmVirtualJoinTableRelationshipStrategy parent, ReadOnlyTable.Owner owner, ReadOnlyJoinTable overriddenTable) {
		return new GenericOrmVirtualJoinTable(parent, owner, overriddenTable);
	}
	
	public OrmJoinColumn buildOrmJoinColumn(XmlContextNode parent, OrmReadOnlyJoinColumn.Owner owner, XmlJoinColumn xmlJoinColumn) {
		return new GenericOrmJoinColumn(parent, owner, xmlJoinColumn);
	}

	public OrmVirtualJoinColumn buildOrmVirtualJoinColumn(XmlContextNode parent, OrmReadOnlyJoinColumn.Owner owner, ReadOnlyJoinColumn overriddenColumn) {
		return new GenericOrmVirtualJoinColumn(parent, owner, overriddenColumn);
	}
	
	public OrmAttributeOverrideContainer buildOrmAttributeOverrideContainer(XmlContextNode parent, OrmAttributeOverrideContainer.Owner owner) {
		return new GenericOrmAttributeOverrideContainer(parent, owner);
	}
	
	public OrmAssociationOverrideContainer buildOrmAssociationOverrideContainer(XmlContextNode parent, OrmAssociationOverrideContainer.Owner owner) {
		return new GenericOrmAssociationOverrideContainer(parent, owner);
	}
	
	public OrmAttributeOverride buildOrmAttributeOverride(OrmAttributeOverrideContainer parent, XmlAttributeOverride xmlOverride) {
		return new GenericOrmAttributeOverride(parent, xmlOverride);
	}
	
	public OrmVirtualAttributeOverride buildOrmVirtualAttributeOverride(OrmAttributeOverrideContainer parent, String name) {
		return new GenericOrmVirtualAttributeOverride(parent, name);
	}
	
	public OrmAssociationOverride buildOrmAssociationOverride(OrmAssociationOverrideContainer parent, XmlAssociationOverride xmlOverride) {
		return new GenericOrmAssociationOverride(parent, xmlOverride);
	}
	
	public OrmVirtualAssociationOverride buildOrmVirtualAssociationOverride(OrmAssociationOverrideContainer parent, String name) {
		return new GenericOrmVirtualAssociationOverride(parent, name);
	}
	
	public OrmOverrideRelationship buildOrmOverrideRelationship(OrmAssociationOverride parent) {
		return new GenericOrmOverrideRelationship(parent);
	}
	
	public OrmVirtualOverrideRelationship buildOrmVirtualOverrideRelationship(OrmVirtualAssociationOverride parent) {
		return new GenericOrmVirtualOverrideRelationship(parent);
	}
	
	public OrmDiscriminatorColumn buildOrmDiscriminatorColumn(OrmEntity parent, OrmDiscriminatorColumn.Owner owner) {
		return new GenericOrmDiscriminatorColumn(parent, owner);
	}
	
	public OrmColumn buildOrmColumn(XmlContextNode parent, OrmColumn.Owner owner) {
		return new GenericOrmColumn(parent, owner);
	}
	
	public OrmVirtualColumn buildOrmVirtualColumn(XmlContextNode parent, OrmVirtualColumn.Owner owner) {
		return new GenericOrmVirtualColumn(parent, owner);
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

	public OrmVirtualUniqueConstraint buildOrmVirtualUniqueConstraint(XmlContextNode parent, ReadOnlyUniqueConstraint overriddenUniqueConstraint) {
		return new GenericOrmVirtualUniqueConstraint(parent, overriddenUniqueConstraint);
	}

	public OrmConverter buildOrmEnumeratedConverter(OrmAttributeMapping parent) {
		return new GenericOrmEnumeratedConverter(parent);
	}
	
	public OrmConverter buildOrmLobConverter(OrmAttributeMapping parent) {
		return new GenericOrmLobConverter(parent);
	}
	
	public OrmConverter buildOrmTemporalConverter(OrmAttributeMapping parent) {
		return new GenericOrmTemporalConverter(parent);
	}
	
	public OrmOrderable buildOrmOrderable(OrmAttributeMapping parent) {
		return new GenericOrmOrderable(parent);
	}
}
