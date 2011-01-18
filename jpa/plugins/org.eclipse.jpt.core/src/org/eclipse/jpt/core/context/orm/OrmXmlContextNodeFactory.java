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

import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
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
public interface OrmXmlContextNodeFactory
{
	// ********** ORM Context Model **********

	EntityMappings buildEntityMappings(OrmXml parent, XmlEntityMappings entityMappings);
	
	OrmPersistenceUnitMetadata buildOrmPersistenceUnitMetadata(EntityMappings parent);
	
	OrmPersistenceUnitDefaults buildOrmPersistenceUnitDefaults(OrmPersistenceUnitMetadata parent);
	
	OrmPersistentType buildOrmPersistentType(EntityMappings parent, XmlTypeMapping resourceMapping);
	
	OrmEntity buildOrmEntity(OrmPersistentType parent, XmlEntity resourceMapping);
	
	OrmMappedSuperclass buildOrmMappedSuperclass(OrmPersistentType parent, XmlMappedSuperclass resourceMapping);
	
	OrmEmbeddable buildOrmEmbeddable(OrmPersistentType parent, XmlEmbeddable resourceMapping);
	
	OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, XmlAttributeMapping xmlMapping);
	
	OrmReadOnlyPersistentAttribute buildVirtualOrmPersistentAttribute(OrmPersistentType parent, JavaResourcePersistentAttribute javaResourcePersistentAttribute);
	
	OrmTable buildOrmTable(OrmEntity parent, Table.Owner owner);
	
	OrmSecondaryTable buildOrmSecondaryTable(OrmEntity parent, Table.Owner owner, XmlSecondaryTable xmlSecondaryTable);
	
	OrmVirtualSecondaryTable buildOrmVirtualSecondaryTable(OrmEntity parent, JavaSecondaryTable javaSecondaryTable);
	
	OrmPrimaryKeyJoinColumn buildOrmPrimaryKeyJoinColumn(XmlContextNode parent, OrmBaseJoinColumn.Owner owner, XmlPrimaryKeyJoinColumn resourcePrimaryKeyJoinColumn);
	
	OrmVirtualPrimaryKeyJoinColumn buildOrmVirtualPrimaryKeyJoinColumn(XmlContextNode parent, ReadOnlyBaseJoinColumn.Owner owner, JavaPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn);
	
	OrmJoinTable buildOrmJoinTable(OrmJoinTableJoiningStrategy parent, Table.Owner owner);
	
	OrmVirtualJoinTable buildOrmVirtualJoinTable(OrmVirtualJoinTableJoiningStrategy parent, JoinTable overriddenTable);
	
	OrmJoinColumn buildOrmJoinColumn(XmlContextNode parent, OrmJoinColumn.Owner owner, XmlJoinColumn resourceJoinColumn);
	
	OrmVirtualJoinColumn buildOrmVirtualJoinColumn(XmlContextNode parent, ReadOnlyJoinColumn.Owner owner, JoinColumn joinColumn);
	
	OrmAttributeOverrideContainer buildOrmAttributeOverrideContainer(XmlContextNode parent, OrmAttributeOverrideContainer.Owner owner);

	OrmAssociationOverrideContainer buildOrmAssociationOverrideContainer(XmlContextNode parent, OrmAssociationOverrideContainer.Owner owner);

	OrmAttributeOverride buildOrmAttributeOverride(OrmAttributeOverrideContainer parent, XmlAttributeOverride xmlOverride);
	
	OrmVirtualAttributeOverride buildOrmVirtualAttributeOverride(OrmAttributeOverrideContainer parent, String name);
	
	OrmAssociationOverride buildOrmAssociationOverride(OrmAssociationOverrideContainer parent, XmlAssociationOverride xmlOverride);
	
	OrmVirtualAssociationOverride buildOrmVirtualAssociationOverride(OrmAssociationOverrideContainer parent, String name);
	
	OrmAssociationOverrideRelationshipReference buildOrmAssociationOverrideRelationshipReference(OrmAssociationOverride parent);

	OrmVirtualAssociationOverrideRelationshipReference buildOrmVirtualAssociationOverrideRelationshipReference(OrmVirtualAssociationOverride parent);

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
	
	OrmVirtualUniqueConstraint buildOrmVirtualUniqueConstraint(XmlContextNode parent, UniqueConstraint overriddenUniqueConstraint);
	
	OrmConverter buildOrmEnumeratedConverter(OrmAttributeMapping parent);
	
	OrmConverter buildOrmTemporalConverter(OrmAttributeMapping parent);
	
	OrmConverter buildOrmLobConverter(OrmAttributeMapping parent);

	/**
	 * JPA 1.0 only
	 */
	OrmOrderable buildOrmOrderable(OrmAttributeMapping parent);
}
