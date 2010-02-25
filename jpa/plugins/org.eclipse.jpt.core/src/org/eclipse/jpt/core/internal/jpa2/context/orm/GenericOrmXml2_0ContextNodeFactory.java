/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

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
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer.Owner;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNodeFactory;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmAssociationOverrideContainer;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaEmbeddedMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToManyMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToManyMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCacheable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCacheableHolder2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmCollectionTable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmEmbeddedMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverrideContainer;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.XmlTransient;
import org.eclipse.jpt.core.resource.orm.XmlVersion;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0;

public class GenericOrmXml2_0ContextNodeFactory extends AbstractOrmXmlContextNodeFactory
{	
	
	@Override
	public OrmEmbeddable buildOrmEmbeddable(OrmPersistentType parent, org.eclipse.jpt.core.resource.orm.XmlEmbeddable resourceMapping) {
		return new GenericOrmEmbeddable2_0(parent, resourceMapping);
	}
	
	@Override
	public OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, OrmPersistentAttribute.Owner owner, org.eclipse.jpt.core.resource.orm.XmlAttributeMapping resourceMapping) {
		return new GenericOrmPersistentAttribute2_0(parent, owner, resourceMapping);
	}
	
	@Override
	public OrmAssociationOverrideRelationshipReference buildOrmAssociationOverrideRelationshipReference(OrmAssociationOverride parent, org.eclipse.jpt.core.resource.orm.XmlAssociationOverride associationOverride) {
		return new GenericOrmAssociationOverrideRelationshipReference2_0(parent, associationOverride);
	}
	
	@Override
	public OrmSequenceGenerator buildOrmSequenceGenerator(XmlContextNode parent, org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator resourceSequenceGenerator) {
		return new GenericOrmSequenceGenerator2_0(parent, resourceSequenceGenerator);
	}
	
	@Override
	public OrmAssociationOverrideContainer buildOrmAssociationOverrideContainer(
			OrmEmbeddedMapping2_0 parent, 
			Owner owner, 
			XmlAssociationOverrideContainer resourceAssociationOverrideContainer) {
		
		return new GenericOrmAssociationOverrideContainer(parent, owner, resourceAssociationOverrideContainer);
	}
	
	@Override
	public OrmDerivedIdentity2_0 buildOrmDerivedIdentity(
			OrmSingleRelationshipMapping2_0 parent, XmlSingleRelationshipMapping_2_0 resource) {
		return new GenericOrmDerivedIdentity2_0(parent, resource);
	}
	
	@Override
	public OrmElementCollectionMapping2_0 buildOrmElementCollectionMapping2_0(
			OrmPersistentAttribute parent, XmlElementCollection resourceMapping) {
		
		return new GenericOrmElementCollectionMapping2_0(parent, resourceMapping);
	}
	
	@Override
	public OrmCacheable2_0 buildOrmCacheable(OrmCacheableHolder2_0 parent, XmlCacheable_2_0 resource) {
		return new GenericOrmCacheable2_0(parent, resource);
	}
	
	@Override
	public OrmOrphanRemovable2_0 buildOrmOrphanRemoval(OrmOrphanRemovalHolder2_0 parent, XmlOrphanRemovable_2_0 resource) {
		return new GenericOrmOrphanRemoval2_0(parent, resource);
	}

	@Override
	public OrmNamedQuery buildOrmNamedQuery(XmlContextNode parent, XmlNamedQuery resourceNamedQuery) {
		return new GenericOrmNamedQuery2_0(parent, resourceNamedQuery);
	}

	@Override
	public OrmCollectionTable2_0 buildOrmCollectionTable(OrmElementCollectionMapping2_0 parent, XmlCollectionTable resource) {
		return new GenericOrmCollectionTable2_0(parent, resource);
	}
	
	@Override
	public OrmOneToManyMapping buildOrmOneToManyMapping(OrmPersistentAttribute parent, XmlOneToMany resourceMapping) {
		return new GenericOrmOneToManyMapping2_0(parent, resourceMapping);
	}

	@Override
	public OrmManyToOneMapping buildOrmManyToOneMapping(OrmPersistentAttribute parent, XmlManyToOne resourceMapping) {
		return new GenericOrmManyToOneMapping2_0(parent, resourceMapping);
	}

	@Override
	public OrmOneToOneMapping buildOrmOneToOneMapping(OrmPersistentAttribute parent, XmlOneToOne resourceMapping) {
		return new GenericOrmOneToOneMapping2_0(parent, resourceMapping);
	}
	
	// ********** ORM Virtual Resource Model **********

	@Override
	public XmlAssociationOverride buildVirtualXmlAssociationOverride(String name, OrmTypeMapping parent, JoiningStrategy joiningStrategy) {
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
		return new VirtualXmlEmbedded2_0(ormTypeMapping, (JavaEmbeddedMapping2_0) javaEmbeddedMapping);
	}
	
	@Override
	public XmlManyToMany buildVirtualXmlManyToMany(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		return new VirtualXmlManyToMany2_0(ormTypeMapping, (JavaManyToManyMapping2_0) javaManyToManyMapping);
	}
	
	@Override
	public XmlManyToOne buildVirtualXmlManyToOne(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping) {
		return new VirtualXmlManyToOne2_0(ormTypeMapping, (JavaManyToOneMapping2_0) javaManyToOneMapping);
	}
	
	@Override
	public XmlOneToMany buildVirtualXmlOneToMany(OrmTypeMapping ormTypeMapping, JavaOneToManyMapping javaOneToManyMapping) {
		return new VirtualXmlOneToMany2_0(ormTypeMapping, (JavaOneToManyMapping2_0) javaOneToManyMapping);
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
	
	@Override
	public XmlElementCollection buildVirtualXmlElementCollection2_0(OrmTypeMapping ormTypeMapping, JavaElementCollectionMapping2_0 javaMapping) {
		return new VirtualXmlElementCollection2_0(ormTypeMapping, javaMapping);
	}
}
