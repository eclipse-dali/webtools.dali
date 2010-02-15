/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.context.orm;

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
import org.eclipse.jpt.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideContainer.Owner;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmAssociationOverrideContainer;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmAssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmCacheable2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmCollectionTable2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmDerivedIdentity2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmElementCollectionMapping2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmNamedQuery2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmOrphanRemoval2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmSequenceGenerator2_0;
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlAssociationOverride2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaEmbeddedMapping2_0;
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
import org.eclipse.jpt.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlOrphanRemovable_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlSingleRelationshipMapping_2_0;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkBasicMapMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkTransformationMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlContextNodeFactory;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.OrmEclipseLinkPersistentAttribute1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtuaEclipseLinklXmlNullAttributeMapping1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlBasic1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlBasicCollection1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlBasicMap1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlEmbeddedId1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlId1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlManyToOne1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlTransformation1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlTransient1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlVariableOneToOne1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlVersion1_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasic;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicCollection;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlBasicMap;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlId;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransformation;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTransient;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlVariableOneToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlVersion;

public class EclipseLinkOrmXml2_0ContextNodeFactory extends EclipseLinkOrmXmlContextNodeFactory
{	

	@Override
	public OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, OrmPersistentAttribute.Owner owner, org.eclipse.jpt.core.resource.orm.XmlAttributeMapping resourceMapping) {
		return new OrmEclipseLinkPersistentAttribute1_1(parent, owner, (XmlAttributeMapping) resourceMapping);
	}

	@Override
	public OrmAssociationOverrideContainer buildOrmAssociationOverrideContainer(OrmEmbeddedMapping2_0 parent, Owner owner, XmlAssociationOverrideContainer resourceAssociationOverrideContainer) {
		return new GenericOrmAssociationOverrideContainer(parent, owner, resourceAssociationOverrideContainer);
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
	public OrmDerivedIdentity2_0 buildOrmDerivedIdentity(
			OrmSingleRelationshipMapping2_0 parent, XmlSingleRelationshipMapping_2_0 resource) {
		return new GenericOrmDerivedIdentity2_0(parent, resource);
	}
	
	@Override
	public OrmElementCollectionMapping2_0 buildOrmElementCollectionMapping2_0(OrmPersistentAttribute parent, XmlElementCollection resourceMapping) {
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


	// ********** ORM Virtual Resource Model **********
	@Override
	public XmlBasic buildVirtualXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		return new VirtualEclipseLinkXmlBasic1_1(ormTypeMapping, javaBasicMapping);
	}
	
	@Override
	public XmlId buildVirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping) {
		return new VirtualEclipseLinkXmlId1_1(ormTypeMapping, javaIdMapping);
	}
	
	@Override
	public XmlEmbeddedId buildVirtualXmlEmbeddedId(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		return new VirtualEclipseLinkXmlEmbeddedId1_1(ormTypeMapping, javaEmbeddedIdMapping);
	}
	
	@Override
	public XmlEmbedded buildVirtualXmlEmbedded(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping) {
		return new VirtualEclipseLinkXmlEmbedded2_0(ormTypeMapping, (JavaEmbeddedMapping2_0) javaEmbeddedMapping);
	}
	
	@Override
	public XmlManyToMany buildVirtualXmlManyToMany(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		return new VirtualEclipseLinkXmlManyToMany2_0(ormTypeMapping, javaManyToManyMapping);
	}
	
	@Override
	public XmlManyToOne buildVirtualXmlManyToOne(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping) {
		return new VirtualEclipseLinkXmlManyToOne1_1(ormTypeMapping, javaManyToOneMapping);
	}
	
	@Override
	public XmlOneToMany buildVirtualXmlOneToMany(OrmTypeMapping ormTypeMapping, JavaOneToManyMapping javaOneToManyMapping) {
		return new VirtualEclipseLinkXmlOneToMany2_0(ormTypeMapping, (JavaEclipseLinkOneToManyMapping) javaOneToManyMapping);
	}
	
	@Override
	public XmlVersion buildVirtualXmlVersion(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		return new VirtualEclipseLinkXmlVersion1_1(ormTypeMapping, javaVersionMapping);
	}
	
	@Override
	public XmlBasicCollection buildVirtualEclipseLinkXmlBasicCollection(OrmTypeMapping ormTypeMapping, JavaEclipseLinkBasicCollectionMapping javaBasicCollectionMapping) {
		return new VirtualEclipseLinkXmlBasicCollection1_1(ormTypeMapping, javaBasicCollectionMapping);
	}
	
	@Override
	public XmlBasicMap buildVirtualEclipseLinkXmlBasicMap(OrmTypeMapping ormTypeMapping, JavaEclipseLinkBasicMapMapping javaBasicMapMapping) {
		return new VirtualEclipseLinkXmlBasicMap1_1(ormTypeMapping, javaBasicMapMapping);
	}
	
	@Override
	public XmlTransformation buildVirtualEclipseLinkXmlTransformation(OrmTypeMapping ormTypeMapping, JavaEclipseLinkTransformationMapping javaTransformationMapping) {
		return new VirtualEclipseLinkXmlTransformation1_1(ormTypeMapping, javaTransformationMapping);
	}
	
	@Override
	public XmlVariableOneToOne buildVirtualEclipseLinkXmlVariableOneToOne(OrmTypeMapping ormTypeMapping, JavaEclipseLinkVariableOneToOneMapping javaVariableOneToOneMapping) {
		return new VirtualEclipseLinkXmlVariableOneToOne1_1(ormTypeMapping, javaVariableOneToOneMapping);
	}
	
	@Override
	public XmlTransient buildVirtualXmlTransient(OrmTypeMapping ormTypeMapping, JavaTransientMapping javaAttributeMapping) {
		return new VirtualEclipseLinkXmlTransient1_1(ormTypeMapping, javaAttributeMapping);
	}
	
	@Override
	public XmlNullAttributeMapping buildVirtualXmlNullAttributeMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping) {
		return new VirtuaEclipseLinklXmlNullAttributeMapping1_1(ormTypeMapping, javaAttributeMapping);
	}
	
	@Override
	public XmlAssociationOverride buildVirtualXmlAssociationOverride(String name, OrmTypeMapping parent, JoiningStrategy joiningStrategy) {
		return new VirtualXmlAssociationOverride2_0(name, parent, joiningStrategy);		
	}
	
	@Override
	public XmlElementCollection buildVirtualXmlElementCollection2_0(OrmTypeMapping ormTypeMapping, JavaElementCollectionMapping2_0 javaMapping) {
		return new VirtualEclipseLinkXmlElementCollection2_0(ormTypeMapping, javaMapping);
	}
}
