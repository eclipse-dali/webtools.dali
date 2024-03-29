/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.Orderable;
import org.eclipse.jpt.jpa.core.context.SpecifiedUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.VirtualColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.VirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.VirtualUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedSecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.jpa.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmQuery;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.jpa.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedJoinTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedSecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.orm.OrmTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmVirtualSecondaryTable;
import org.eclipse.jpt.jpa.core.context.orm.OrmXml;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericEntityMappings;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmBaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmBaseTemporalConverter;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmBasicMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmEmbeddable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmEmbeddedMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmEntity;
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
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmSpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmTable;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmTableGenerator;
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

public abstract class AbstractOrmXmlContextModelFactory
	implements OrmXmlContextModelFactory
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
	
	public OrmSpecifiedPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, XmlAttributeMapping xmlMapping) {
		return new GenericOrmPersistentAttribute(parent, xmlMapping);
	}
	
	public OrmPersistentAttribute buildVirtualOrmPersistentField(OrmPersistentType parent, JavaResourceField javaResourceField) {
		return new VirtualOrmPersistentAttribute(parent, javaResourceField);
	}

	public OrmPersistentAttribute buildVirtualOrmPersistentProperty(OrmPersistentType parent, JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter) {
		return new VirtualOrmPersistentAttribute(parent, javaResourceGetter, javaResourceSetter);
	}

	public OrmSpecifiedTable buildOrmTable(OrmTable.ParentAdapter parentAdapter) {
		return new GenericOrmTable(parentAdapter);
	}
	
	public OrmSpecifiedSecondaryTable buildOrmSecondaryTable(OrmSpecifiedSecondaryTable.ParentAdapter parentAdapter, XmlSecondaryTable xmlSecondaryTable) {
		return new GenericOrmSecondaryTable(parentAdapter, xmlSecondaryTable);
	}

	public OrmVirtualSecondaryTable buildOrmVirtualSecondaryTable(OrmVirtualSecondaryTable.ParentAdapter parentAdapter, JavaSpecifiedSecondaryTable javaSecondaryTable) {
		return new GenericOrmVirtualSecondaryTable(parentAdapter, javaSecondaryTable);
	}
	
	public OrmSpecifiedPrimaryKeyJoinColumn buildOrmPrimaryKeyJoinColumn(BaseJoinColumn.ParentAdapter parentAdapter, XmlPrimaryKeyJoinColumn resourcePrimaryKeyJoinColumn) {
		return new GenericOrmPrimaryKeyJoinColumn(parentAdapter, resourcePrimaryKeyJoinColumn);
	}

	public OrmVirtualPrimaryKeyJoinColumn buildOrmVirtualPrimaryKeyJoinColumn(BaseJoinColumn.ParentAdapter parentAdapter, JavaSpecifiedPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn) {
		return new GenericOrmVirtualPrimaryKeyJoinColumn(parentAdapter, javaPrimaryKeyJoinColumn);
	}
	
	public OrmSpecifiedJoinTable buildOrmJoinTable(OrmSpecifiedJoinTable.ParentAdapter parentAdapter) {
		return new GenericOrmJoinTable(parentAdapter);
	}
	
	public VirtualJoinTable buildOrmVirtualJoinTable(VirtualJoinTable.ParentAdapter parentAdapter, JoinTable overriddenTable) {
		return new GenericOrmVirtualJoinTable(parentAdapter, overriddenTable);
	}
	
	public OrmSpecifiedJoinColumn buildOrmJoinColumn(JoinColumn.ParentAdapter parentAdapter, XmlJoinColumn xmlJoinColumn) {
		return new GenericOrmJoinColumn(parentAdapter, xmlJoinColumn);
	}

	public VirtualJoinColumn buildOrmVirtualJoinColumn(JoinColumn.ParentAdapter parentAdapter, JoinColumn overriddenColumn) {
		return new GenericOrmVirtualJoinColumn(parentAdapter, overriddenColumn);
	}
	
	public OrmAttributeOverrideContainer buildOrmAttributeOverrideContainer(OrmAttributeOverrideContainer.ParentAdapter parentAdapter) {
		return new GenericOrmAttributeOverrideContainer(parentAdapter);
	}
	
	public OrmAssociationOverrideContainer buildOrmAssociationOverrideContainer(OrmAssociationOverrideContainer.ParentAdapter parentAdapter) {
		return new GenericOrmAssociationOverrideContainer(parentAdapter);
	}
	
	public OrmSpecifiedAttributeOverride buildOrmAttributeOverride(OrmAttributeOverrideContainer parent, XmlAttributeOverride xmlOverride) {
		return new GenericOrmSpecifiedAttributeOverride(parent, xmlOverride);
	}
	
	public OrmVirtualAttributeOverride buildOrmVirtualAttributeOverride(OrmAttributeOverrideContainer parent, String name) {
		return new GenericOrmVirtualAttributeOverride(parent, name);
	}
	
	public OrmSpecifiedAssociationOverride buildOrmAssociationOverride(OrmAssociationOverrideContainer parent, XmlAssociationOverride xmlOverride) {
		return new GenericOrmSpecifiedAssociationOverride(parent, xmlOverride);
	}
	
	public OrmVirtualAssociationOverride buildOrmVirtualAssociationOverride(OrmAssociationOverrideContainer parent, String name) {
		return new GenericOrmVirtualAssociationOverride(parent, name);
	}
	
	public OrmSpecifiedOverrideRelationship buildOrmOverrideRelationship(OrmSpecifiedAssociationOverride parent) {
		return new GenericOrmOverrideRelationship(parent);
	}
	
	public VirtualOverrideRelationship buildOrmVirtualOverrideRelationship(OrmVirtualAssociationOverride parent) {
		return new GenericOrmVirtualOverrideRelationship(parent);
	}
	
	public OrmSpecifiedDiscriminatorColumn buildOrmDiscriminatorColumn(OrmSpecifiedDiscriminatorColumn.ParentAdapter parentAdapter) {
		return new GenericOrmDiscriminatorColumn(parentAdapter);
	}
	
	public OrmSpecifiedColumn buildOrmColumn(OrmSpecifiedColumn.ParentAdapter parentAdapter) {
		return new GenericOrmColumn(parentAdapter);
	}
	
	public VirtualColumn buildOrmVirtualColumn(VirtualColumn.ParentAdapter parentAdapter) {
		return new GenericOrmVirtualColumn(parentAdapter);
	}
	
	public OrmGeneratedValue buildOrmGeneratedValue(JpaContextModel parent, XmlGeneratedValue resourceGeneratedValue) {
		return new GenericOrmGeneratedValue(parent, resourceGeneratedValue);
	}
	
	public OrmGeneratorContainer buildOrmGeneratorContainer(JpaContextModel parent, XmlGeneratorContainer resourceGeneratorContainer) {
		return new GenericOrmGeneratorContainer(parent, resourceGeneratorContainer);
	}
	
	public OrmSequenceGenerator buildOrmSequenceGenerator(JpaContextModel parent, XmlSequenceGenerator resourceSequenceGenerator) {
		return new GenericOrmSequenceGenerator(parent, resourceSequenceGenerator);
	}
	
	public OrmTableGenerator buildOrmTableGenerator(JpaContextModel parent, XmlTableGenerator resourceTableGenerator) {
		return new GenericOrmTableGenerator(parent, resourceTableGenerator);
	}
	
	public OrmQueryContainer buildOrmQueryContainer(JpaContextModel parent, XmlQueryContainer resourceQueryContainer) {
		return new GenericOrmQueryContainer(parent, resourceQueryContainer);
	}
	
	public OrmNamedNativeQuery buildOrmNamedNativeQuery(JpaContextModel parent, XmlNamedNativeQuery resourceNamedNativeQuery) {
		return new GenericOrmNamedNativeQuery(parent, resourceNamedNativeQuery);
	}
	
	public OrmNamedQuery buildOrmNamedQuery(JpaContextModel parent, XmlNamedQuery resourceNamedQuery) {
		return new GenericOrmNamedQuery(parent, resourceNamedQuery);
	}
	
	public OrmQueryHint buildOrmQueryHint(OrmQuery parent, XmlQueryHint resourceQueryHint) {
		return new GenericOrmQueryHint(parent, resourceQueryHint);
	}
	
	public OrmBasicMapping buildOrmBasicMapping(OrmSpecifiedPersistentAttribute parent, XmlBasic resourceMapping) {
		return new GenericOrmBasicMapping(parent, resourceMapping);
	}
	
	public OrmEmbeddedMapping buildOrmEmbeddedMapping(OrmSpecifiedPersistentAttribute parent, XmlEmbedded resourceMapping) {
		return new GenericOrmEmbeddedMapping(parent, resourceMapping);
	}
	
	public OrmEmbeddedIdMapping buildOrmEmbeddedIdMapping(OrmSpecifiedPersistentAttribute parent, XmlEmbeddedId resourceMapping) {
		return new GenericOrmEmbeddedIdMapping(parent, resourceMapping);
	}
	
	public OrmIdMapping buildOrmIdMapping(OrmSpecifiedPersistentAttribute parent, XmlId resourceMapping) {
		return new GenericOrmIdMapping(parent, resourceMapping);
	}
	
	public OrmManyToManyMapping buildOrmManyToManyMapping(OrmSpecifiedPersistentAttribute parent, XmlManyToMany resourceMapping) {
		return new GenericOrmManyToManyMapping(parent, resourceMapping);
	}
	
	public OrmManyToOneMapping buildOrmManyToOneMapping(OrmSpecifiedPersistentAttribute parent, XmlManyToOne resourceMapping) {
		return new GenericOrmManyToOneMapping(parent, resourceMapping);
	}
	
	public OrmOneToManyMapping buildOrmOneToManyMapping(OrmSpecifiedPersistentAttribute parent, XmlOneToMany resourceMapping) {
		return new GenericOrmOneToManyMapping(parent, resourceMapping);
	}
	
	public OrmOneToOneMapping buildOrmOneToOneMapping(OrmSpecifiedPersistentAttribute parent, XmlOneToOne resourceMapping) {
		return new GenericOrmOneToOneMapping(parent, resourceMapping);
	}
	
	public OrmTransientMapping buildOrmTransientMapping(OrmSpecifiedPersistentAttribute parent, XmlTransient resourceMapping) {
		return new GenericOrmTransientMapping(parent, resourceMapping);
	}
	
	public OrmVersionMapping buildOrmVersionMapping(OrmSpecifiedPersistentAttribute parent, XmlVersion resourceMapping) {
		return new GenericOrmVersionMapping(parent, resourceMapping);
	}
	
	public OrmAttributeMapping buildOrmNullAttributeMapping(OrmSpecifiedPersistentAttribute parent, XmlNullAttributeMapping resourceMapping) {
		return new GenericOrmNullAttributeMapping(parent, resourceMapping);
	}
	
	public OrmAttributeMapping buildUnsupportedOrmAttributeMapping(OrmSpecifiedPersistentAttribute parent, XmlNullAttributeMapping resourceMapping) {
		return new UnsupportedOrmAttributeMapping(parent, resourceMapping);
	}
	
	public OrmSpecifiedUniqueConstraint buildOrmUniqueConstraint(SpecifiedUniqueConstraint.Parent parent, XmlUniqueConstraint resourceUniqueConstraint) {
		return new GenericOrmUniqueConstraint(parent, resourceUniqueConstraint);
	}

	public VirtualUniqueConstraint buildOrmVirtualUniqueConstraint(JpaContextModel parent, UniqueConstraint overriddenUniqueConstraint) {
		return new GenericOrmVirtualUniqueConstraint(parent, overriddenUniqueConstraint);
	}

	public OrmConverter buildOrmBaseEnumeratedConverter(OrmBaseEnumeratedConverter.ParentAdapter parentAdapter) {
		return new GenericOrmBaseEnumeratedConverter(parentAdapter);
	}
	
	public OrmConverter buildOrmLobConverter(OrmConverter.ParentAdapter parentAdapter) {
		return new GenericOrmLobConverter(parentAdapter);
	}
	
	public OrmConverter buildOrmBaseTemporalConverter(OrmBaseTemporalConverter.ParentAdapter parentAdapter) {
		return new GenericOrmBaseTemporalConverter(parentAdapter);
	}
	
	public Orderable buildOrmOrderable(OrmAttributeMapping parent) {
		return new GenericOrmOrderable(parent);
	}
}
