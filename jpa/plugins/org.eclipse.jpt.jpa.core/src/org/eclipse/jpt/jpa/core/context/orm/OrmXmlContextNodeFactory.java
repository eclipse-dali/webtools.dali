/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
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
 * <code>orm.xml</code> context node factory
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 2.3
 * @since 2.3
 */
// TODO bjv we need a factory interface for a 1.0 orm.xml in a 2.0 project...
// since it must return slightly different objects (e.g. 2.0 attribute mappings)
// OrmXml1_0ContextNodeFactory2_0 ?
public interface OrmXmlContextNodeFactory
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
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.ReadOnlyPersistentAttribute2_0
	 */
	OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, XmlAttributeMapping xmlMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.ReadOnlyPersistentAttribute2_0
	 */
	OrmReadOnlyPersistentAttribute buildVirtualOrmPersistentAttribute(OrmPersistentType parent, JavaResourcePersistentAttribute javaResourcePersistentAttribute);

	OrmTable buildOrmTable(OrmEntity parent, Table.Owner owner);

	OrmSecondaryTable buildOrmSecondaryTable(OrmEntity parent, Table.Owner owner, XmlSecondaryTable xmlSecondaryTable);

	OrmVirtualSecondaryTable buildOrmVirtualSecondaryTable(OrmEntity parent, ReadOnlyTable.Owner owner, JavaSecondaryTable javaSecondaryTable);

	OrmPrimaryKeyJoinColumn buildOrmPrimaryKeyJoinColumn(XmlContextNode parent, OrmReadOnlyBaseJoinColumn.Owner owner, XmlPrimaryKeyJoinColumn resourcePrimaryKeyJoinColumn);

	OrmVirtualPrimaryKeyJoinColumn buildOrmVirtualPrimaryKeyJoinColumn(XmlContextNode parent, OrmReadOnlyBaseJoinColumn.Owner owner, JavaPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn);

	OrmJoinTable buildOrmJoinTable(OrmJoinTableRelationshipStrategy parent, Table.Owner owner);

	OrmVirtualJoinTable buildOrmVirtualJoinTable(OrmVirtualJoinTableRelationshipStrategy parent, ReadOnlyTable.Owner owner, ReadOnlyJoinTable overriddenTable);

	OrmJoinColumn buildOrmJoinColumn(XmlContextNode parent, OrmReadOnlyJoinColumn.Owner owner, XmlJoinColumn resourceJoinColumn);

	OrmVirtualJoinColumn buildOrmVirtualJoinColumn(XmlContextNode parent, OrmReadOnlyJoinColumn.Owner owner, ReadOnlyJoinColumn overriddenColumn);

	OrmAttributeOverrideContainer buildOrmAttributeOverrideContainer(XmlContextNode parent, OrmAttributeOverrideContainer.Owner owner);

	OrmAssociationOverrideContainer buildOrmAssociationOverrideContainer(XmlContextNode parent, OrmAssociationOverrideContainer.Owner owner);

	OrmAttributeOverride buildOrmAttributeOverride(OrmAttributeOverrideContainer parent, XmlAttributeOverride xmlOverride);

	OrmVirtualAttributeOverride buildOrmVirtualAttributeOverride(OrmAttributeOverrideContainer parent, String name);

	OrmAssociationOverride buildOrmAssociationOverride(OrmAssociationOverrideContainer parent, XmlAssociationOverride xmlOverride);

	OrmVirtualAssociationOverride buildOrmVirtualAssociationOverride(OrmAssociationOverrideContainer parent, String name);

	OrmOverrideRelationship buildOrmOverrideRelationship(OrmAssociationOverride parent);

	OrmVirtualOverrideRelationship buildOrmVirtualOverrideRelationship(OrmVirtualAssociationOverride parent);

	OrmDiscriminatorColumn buildOrmDiscriminatorColumn(OrmEntity parent, OrmDiscriminatorColumn.Owner owner);

	OrmColumn buildOrmColumn(XmlContextNode parent, OrmColumn.Owner owner);

	OrmVirtualColumn buildOrmVirtualColumn(XmlContextNode parent, OrmVirtualColumn.Owner owner);

	OrmGeneratedValue buildOrmGeneratedValue(XmlContextNode parent, XmlGeneratedValue resourceGeneratedValue);

	OrmGeneratorContainer buildOrmGeneratorContainer(XmlContextNode parent, XmlGeneratorContainer resourceGeneratorContainer);

	OrmSequenceGenerator buildOrmSequenceGenerator(XmlContextNode parent, XmlSequenceGenerator resourceSequenceGenerator);

	OrmTableGenerator buildOrmTableGenerator(XmlContextNode parent, XmlTableGenerator resourceTableGenerator);

	OrmQueryContainer buildOrmQueryContainer(XmlContextNode parent, XmlQueryContainer resourceQueryContainer);

	OrmNamedNativeQuery buildOrmNamedNativeQuery(XmlContextNode parent, XmlNamedNativeQuery resourceNamedQuery);

	OrmNamedQuery buildOrmNamedQuery(XmlContextNode parent, XmlNamedQuery resourceNamedQuery);

	OrmQueryHint buildOrmQueryHint(OrmQuery parent, XmlQueryHint resourceQueryhint);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmBasicMapping buildOrmBasicMapping(OrmPersistentAttribute parent, XmlBasic resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmManyToManyMapping buildOrmManyToManyMapping(OrmPersistentAttribute parent, XmlManyToMany resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmOneToManyMapping buildOrmOneToManyMapping(OrmPersistentAttribute parent, XmlOneToMany resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmManyToOneMapping buildOrmManyToOneMapping(OrmPersistentAttribute parent, XmlManyToOne resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmOneToOneMapping buildOrmOneToOneMapping(OrmPersistentAttribute parent, XmlOneToOne resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmEmbeddedIdMapping buildOrmEmbeddedIdMapping(OrmPersistentAttribute parent, XmlEmbeddedId resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmEmbeddedMapping buildOrmEmbeddedMapping(OrmPersistentAttribute parent, XmlEmbedded resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmIdMapping buildOrmIdMapping(OrmPersistentAttribute parent, XmlId resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmTransientMapping buildOrmTransientMapping(OrmPersistentAttribute parent, XmlTransient resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmVersionMapping buildOrmVersionMapping(OrmPersistentAttribute parent, XmlVersion resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmAttributeMapping buildOrmNullAttributeMapping(OrmPersistentAttribute parent, XmlNullAttributeMapping resourceMapping);

	/**
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmAttributeMapping buildUnsupportedOrmAttributeMapping(OrmPersistentAttribute parent, XmlNullAttributeMapping resourceMapping);

	OrmUniqueConstraint buildOrmUniqueConstraint(XmlContextNode parent, UniqueConstraint.Owner owner, XmlUniqueConstraint resourceUniqueConstraint);

	OrmVirtualUniqueConstraint buildOrmVirtualUniqueConstraint(XmlContextNode parent, ReadOnlyUniqueConstraint overriddenUniqueConstraint);

	OrmConverter buildOrmEnumeratedConverter(OrmAttributeMapping parent);

	OrmConverter buildOrmTemporalConverter(OrmAttributeMapping parent);

	OrmConverter buildOrmLobConverter(OrmAttributeMapping parent);

	/**
	 * JPA 1.0 only
	 * <p>
	 * NB: A factory for a version 1.0 <code>orm.xml</code> in a JPA 2.0 project
	 * must build objects that implement the appropriate behavior.
	 * @see org.eclipse.jpt.jpa.core.jpa2.context.AttributeMapping2_0
	 */
	OrmOrderable buildOrmOrderable(OrmAttributeMapping parent);
}
