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

import java.util.ListIterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlEmbedded;
import org.eclipse.jpt.core.jpa2.context.java.JavaEmbeddedMapping2_0;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualXmlEmbedded2_0 extends XmlEmbedded
{
		
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaEmbeddedMapping2_0 javaAttributeMapping;

	protected final VirtualXmlEmbedded virtualXmlEmbedded;
		
	public VirtualXmlEmbedded2_0(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping2_0 javaEmbeddedMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaEmbeddedMapping;
		this.virtualXmlEmbedded = new VirtualXmlEmbedded(ormTypeMapping, javaEmbeddedMapping);
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlEmbedded.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlEmbedded.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlEmbedded.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlEmbedded.getNameTextRange();
	}

	@Override
	public EList<XmlAttributeOverride> getAttributeOverrides() {
		return this.virtualXmlEmbedded.getAttributeOverrides();
	}

	@Override
	public EList<XmlAssociationOverride> getAssociationOverrides() {
		EList<XmlAssociationOverride> associationOverrides = new EObjectContainmentEList<XmlAssociationOverride>(XmlAssociationOverride.class, this, OrmPackage.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES);
		ListIterator<JavaAssociationOverride> javaAssociationOverrides;
		if (!this.isOrmMetadataComplete()) {
			javaAssociationOverrides = this.javaAttributeMapping.getAssociationOverrideContainer().associationOverrides();
		}
		else {
			javaAssociationOverrides = this.javaAttributeMapping.getAssociationOverrideContainer().virtualAssociationOverrides();
		}
		for (JavaAssociationOverride javaAssociationOverride : CollectionTools.iterable(javaAssociationOverrides)) {
			XmlAssociationOverride xmlAssociationOverride = new VirtualXmlAssociationOverride2_0(javaAssociationOverride.getName(), this.ormTypeMapping, javaAssociationOverride.getRelationshipReference().getPredominantJoiningStrategy());
			associationOverrides.add(xmlAssociationOverride);
		}
		return associationOverrides;
	}

	@Override
	public AccessType getAccess() {
		return org.eclipse.jpt.core.context.AccessType.toOrmResourceModel(this.javaAttributeMapping.getPersistentAttribute().getAccess());
	}
	
	@Override
	public void setAccess(AccessType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
}
