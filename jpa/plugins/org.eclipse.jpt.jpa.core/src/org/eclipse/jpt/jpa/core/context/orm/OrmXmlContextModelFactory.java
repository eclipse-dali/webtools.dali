/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.Orderable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.VirtualColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.VirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.VirtualUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSecondaryTable;
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

/**
 * <code>orm.xml</code> context model factory
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.3
 * @since 2.3
 */
// TODO bjv we need a factory interface for a 1.0 orm.xml in a 2.0 project...
// since it must return slightly different objects (e.g. 2.0 attribute mappings)
// OrmXml1_0ContextNodeFactory2_0 ?
public interface OrmXmlContextModelFactory
{
	EntityMappings buildEntityMappings(OrmXml parent, XmlEntityMappings entityMappings);

	OrmPersistenceUnitMetadata buildOrmPersistenceUnitMetadata(EntityMappings parent);

	OrmPersistenceUnitDefaults buildOrmPersistenceUnitDefaults(OrmPersistenceUnitMetadata parent);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.PersistentType2_0#getDeclaringTypeName()
	 */
	OrmPersistentType buildOrmPersistentType(EntityMappings parent, XmlTypeMapping resourceMapping);

	OrmEntity buildOrmEntity(OrmPersistentType parent, XmlEntity resourceMapping);

	OrmMappedSuperclass buildOrmMappedSuperclass(OrmPersistentType parent, XmlMappedSuperclass resourceMapping);

	OrmEmbeddable buildOrmEmbeddable(OrmPersistentType parent, XmlEmbeddable resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.PersistentAttribute2_0
	 */
	OrmModifiablePersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, XmlAttributeMapping xmlMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.PersistentAttribute2_0
	 */
	OrmPersistentAttribute buildVirtualOrmPersistentField(OrmPersistentType parent, JavaResourceField javaResourceField);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.PersistentAttribute2_0
	 */
	OrmPersistentAttribute buildVirtualOrmPersistentProperty(OrmPersistentType parent, JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter);

	OrmTable buildOrmTable(OrmEntity parent, Table.Owner owner);

	OrmSecondaryTable buildOrmSecondaryTable(OrmEntity parent, Table.Owner owner, XmlSecondaryTable xmlSecondaryTable);

	OrmVirtualSecondaryTable buildOrmVirtualSecondaryTable(OrmEntity parent, ReadOnlyTable.Owner owner, JavaSecondaryTable javaSecondaryTable);

	OrmSpecifiedPrimaryKeyJoinColumn buildOrmPrimaryKeyJoinColumn(JpaContextModel parent, ReadOnlyBaseJoinColumn.Owner owner, XmlPrimaryKeyJoinColumn resourcePrimaryKeyJoinColumn);

	OrmVirtualPrimaryKeyJoinColumn buildOrmVirtualPrimaryKeyJoinColumn(JpaContextModel parent, ReadOnlyBaseJoinColumn.Owner owner, JavaSpecifiedPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn);

	OrmJoinTable buildOrmJoinTable(OrmJoinTableRelationshipStrategy parent, Table.Owner owner);

	VirtualJoinTable buildOrmVirtualJoinTable(VirtualJoinTableRelationshipStrategy parent, ReadOnlyTable.Owner owner, ReadOnlyJoinTable overriddenTable);

	OrmSpecifiedJoinColumn buildOrmJoinColumn(JpaContextModel parent, ReadOnlyJoinColumn.Owner owner, XmlJoinColumn resourceJoinColumn);

	VirtualJoinColumn buildOrmVirtualJoinColumn(JpaContextModel parent, ReadOnlyJoinColumn.Owner owner, ReadOnlyJoinColumn overriddenColumn);

	OrmAttributeOverrideContainer buildOrmAttributeOverrideContainer(JpaContextModel parent, OrmAttributeOverrideContainer.Owner owner);

	OrmAssociationOverrideContainer buildOrmAssociationOverrideContainer(JpaContextModel parent, OrmAssociationOverrideContainer.Owner owner);

	OrmSpecifiedAttributeOverride buildOrmAttributeOverride(OrmAttributeOverrideContainer parent, XmlAttributeOverride xmlOverride);

	OrmVirtualAttributeOverride buildOrmVirtualAttributeOverride(OrmAttributeOverrideContainer parent, String name);

	OrmSpecifiedAssociationOverride buildOrmAssociationOverride(OrmAssociationOverrideContainer parent, XmlAssociationOverride xmlOverride);

	OrmVirtualAssociationOverride buildOrmVirtualAssociationOverride(OrmAssociationOverrideContainer parent, String name);

	OrmOverrideRelationship buildOrmOverrideRelationship(OrmSpecifiedAssociationOverride parent);

	VirtualOverrideRelationship buildOrmVirtualOverrideRelationship(OrmVirtualAssociationOverride parent);

	OrmDiscriminatorColumn buildOrmDiscriminatorColumn(OrmEntity parent, OrmDiscriminatorColumn.Owner owner);

	OrmColumn buildOrmColumn(JpaContextModel parent, OrmColumn.Owner owner);

	VirtualColumn buildOrmVirtualColumn(JpaContextModel parent, VirtualColumn.Owner owner);

	OrmGeneratedValue buildOrmGeneratedValue(JpaContextModel parent, XmlGeneratedValue resourceGeneratedValue);

	OrmGeneratorContainer buildOrmGeneratorContainer(JpaContextModel parent, XmlGeneratorContainer resourceGeneratorContainer);

	OrmSequenceGenerator buildOrmSequenceGenerator(JpaContextModel parent, XmlSequenceGenerator resourceSequenceGenerator);

	OrmTableGenerator buildOrmTableGenerator(JpaContextModel parent, XmlTableGenerator resourceTableGenerator);

	OrmQueryContainer buildOrmQueryContainer(JpaContextModel parent, XmlQueryContainer resourceQueryContainer);

	OrmNamedNativeQuery buildOrmNamedNativeQuery(JpaContextModel parent, XmlNamedNativeQuery resourceNamedQuery);

	OrmNamedQuery buildOrmNamedQuery(JpaContextModel parent, XmlNamedQuery resourceNamedQuery);

	OrmQueryHint buildOrmQueryHint(OrmQuery parent, XmlQueryHint resourceQueryhint);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmBasicMapping buildOrmBasicMapping(OrmModifiablePersistentAttribute parent, XmlBasic resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmManyToManyMapping buildOrmManyToManyMapping(OrmModifiablePersistentAttribute parent, XmlManyToMany resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmOneToManyMapping buildOrmOneToManyMapping(OrmModifiablePersistentAttribute parent, XmlOneToMany resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmManyToOneMapping buildOrmManyToOneMapping(OrmModifiablePersistentAttribute parent, XmlManyToOne resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmOneToOneMapping buildOrmOneToOneMapping(OrmModifiablePersistentAttribute parent, XmlOneToOne resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmEmbeddedIdMapping buildOrmEmbeddedIdMapping(OrmModifiablePersistentAttribute parent, XmlEmbeddedId resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmEmbeddedMapping buildOrmEmbeddedMapping(OrmModifiablePersistentAttribute parent, XmlEmbedded resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmIdMapping buildOrmIdMapping(OrmModifiablePersistentAttribute parent, XmlId resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmTransientMapping buildOrmTransientMapping(OrmModifiablePersistentAttribute parent, XmlTransient resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmVersionMapping buildOrmVersionMapping(OrmModifiablePersistentAttribute parent, XmlVersion resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmAttributeMapping buildOrmNullAttributeMapping(OrmModifiablePersistentAttribute parent, XmlNullAttributeMapping resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmAttributeMapping buildUnsupportedOrmAttributeMapping(OrmModifiablePersistentAttribute parent, XmlNullAttributeMapping resourceMapping);

	OrmUniqueConstraint buildOrmUniqueConstraint(JpaContextModel parent, UniqueConstraint.Owner owner, XmlUniqueConstraint resourceUniqueConstraint);

	VirtualUniqueConstraint buildOrmVirtualUniqueConstraint(JpaContextModel parent, ReadOnlyUniqueConstraint overriddenUniqueConstraint);

	OrmConverter buildOrmBaseEnumeratedConverter(OrmAttributeMapping parent, OrmBaseEnumeratedConverter.Owner owner);

	OrmConverter buildOrmBaseTemporalConverter(OrmAttributeMapping parent, OrmBaseTemporalConverter.Owner owner);

	OrmConverter buildOrmLobConverter(OrmAttributeMapping parent, OrmConverter.Owner owner);

	/**
	 * JPA 1.0 only
	 * <p>
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	Orderable buildOrmOrderable(OrmAttributeMapping parent);
}
