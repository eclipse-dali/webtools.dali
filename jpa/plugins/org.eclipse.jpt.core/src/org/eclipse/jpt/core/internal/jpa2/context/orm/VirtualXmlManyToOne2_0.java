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

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlJoinTable;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlManyToOne;
import org.eclipse.jpt.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToOneMapping2_0;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.core.utility.TextRange;

public class VirtualXmlManyToOne2_0 extends XmlManyToOne
{		
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaManyToOneMapping2_0 javaAttributeMapping;
	
	protected final VirtualXmlManyToOne virtualXmlManyToOne;
	
	protected final VirtualXmlCascadeType2_0 virtualXmlCascadeType;
	
	
	public VirtualXmlManyToOne2_0(
			OrmTypeMapping ormTypeMapping, 
			JavaManyToOneMapping2_0 javaManyToOneMapping) {
		
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaManyToOneMapping;
		this.virtualXmlManyToOne = new VirtualXmlManyToOne(ormTypeMapping, javaManyToOneMapping);
		this.virtualXmlCascadeType = new VirtualXmlCascadeType2_0(javaManyToOneMapping.getCascade());
	}
	
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlManyToOne.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlManyToOne.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlManyToOne.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlManyToOne.getNameTextRange();
	}

	@Override
	public FetchType getFetch() {
		return this.virtualXmlManyToOne.getFetch();
	}

	@Override
	public void setFetch(FetchType newFetch) {
		this.virtualXmlManyToOne.setFetch(newFetch);
	}

	@Override
	public Boolean getOptional() {
		return this.virtualXmlManyToOne.getOptional();
	}

	@Override
	public void setOptional(Boolean newOptional) {
		this.virtualXmlManyToOne.setOptional(newOptional);
	}
	
	@Override
	public EList<XmlJoinColumn> getJoinColumns() {
		return this.virtualXmlManyToOne.getJoinColumns();
	}

	@Override
	public CascadeType getCascade() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		return this.virtualXmlCascadeType;
	}
	
	@Override
	public void setCascade(CascadeType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public XmlJoinTable getJoinTable() {
		if (this.javaAttributeMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable() != null) {
			return 	new VirtualXmlJoinTable(
				this.ormTypeMapping, 
				this.javaAttributeMapping.getRelationshipReference().
					getJoinTableJoiningStrategy().getJoinTable());
		}
		return null;
	}

	@Override
	public void setJoinTable(XmlJoinTable value) {
		this.virtualXmlManyToOne.setJoinTable(value);
	}
	
	@Override
	public String getTargetEntity() {
		return this.virtualXmlManyToOne.getTargetEntity();
	}

	@Override
	public void setTargetEntity(String value) {
		this.virtualXmlManyToOne.setTargetEntity(value);
	}
	
	@Override
	public TextRange getTargetEntityTextRange() {
		return this.virtualXmlManyToOne.getTargetEntityTextRange();
	}
	
	@Override
	public AccessType getAccess() {
		return org.eclipse.jpt.core.context.AccessType.toOrmResourceModel(this.javaAttributeMapping.getPersistentAttribute().getAccess());
	}
	
	@Override
	public void setAccess(AccessType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public Boolean getId() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		boolean javaIdValue = this.javaAttributeMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue();
		return (javaIdValue) ? Boolean.TRUE : null;
	}
	
	@Override
	public void setId(Boolean newId) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public String getMapsId() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		DerivedIdentity2_0 derivedIdentity = this.javaAttributeMapping.getDerivedIdentity();
		if (derivedIdentity.usesMapsIdDerivedIdentityStrategy()) {
			return derivedIdentity.getMapsIdDerivedIdentityStrategy().getValue();
		}
		else {
			return derivedIdentity.getMapsIdDerivedIdentityStrategy().getSpecifiedValue();
		}
	}
	
	@Override
	public void setMapsId(String newMapsId) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
}
