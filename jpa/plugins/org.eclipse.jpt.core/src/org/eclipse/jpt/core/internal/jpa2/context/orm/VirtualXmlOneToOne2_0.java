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
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlOneToOne;
import org.eclipse.jpt.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.OneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;

public class VirtualXmlOneToOne2_0 extends XmlOneToOne
{		
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaOneToOneMapping2_0 javaAttributeMapping;
	
	protected final VirtualXmlOneToOne virtualXmlOneToOne;
	
	protected final VirtualXmlCascadeType2_0 virtualXmlCascadeType;
	
	
	public VirtualXmlOneToOne2_0(
			OrmTypeMapping ormTypeMapping, 
			JavaOneToOneMapping2_0 javaOneToOneMapping) {
		
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaOneToOneMapping;
		this.virtualXmlOneToOne = new VirtualXmlOneToOne(ormTypeMapping, javaOneToOneMapping);
		this.virtualXmlCascadeType = 
				new VirtualXmlCascadeType2_0(javaOneToOneMapping.getCascade(), isOrmMetadataComplete());
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlOneToOne.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlOneToOne.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlOneToOne.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlOneToOne.getNameTextRange();
	}
	
	@Override
	public FetchType getFetch() {
		return this.virtualXmlOneToOne.getFetch();
	}

	@Override
	public void setFetch(FetchType newFetch) {
		this.virtualXmlOneToOne.setFetch(newFetch);
	}

	@Override
	public Boolean getOptional() {
		return this.virtualXmlOneToOne.getOptional();
	}

	@Override
	public void setOptional(Boolean newOptional) {
		this.virtualXmlOneToOne.setOptional(newOptional);
	}

	@Override
	public EList<XmlJoinColumn> getJoinColumns() {
		return this.virtualXmlOneToOne.getJoinColumns();
	}
	
	@Override
	public CascadeType getCascade() {
		return this.virtualXmlCascadeType;
	}
	
	@Override
	public void setCascade(CascadeType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	private JavaOrphanRemovable2_0 getOrphanRemovalOf(OneToOneMapping2_0 oneToOneMapping) {
		return ((JavaOrphanRemovalHolder2_0) oneToOneMapping).getOrphanRemoval();
	}
	
	@Override
	public Boolean getOrphanRemoval() {
		JavaOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(this.javaAttributeMapping);
		if (this.isOrmMetadataComplete()) {
			return Boolean.valueOf(mappingsOrphanRemoval.isDefaultOrphanRemoval());
		}
		return Boolean.valueOf(mappingsOrphanRemoval.isOrphanRemoval());
	}

	@Override
	public void setOrphanRemoval(Boolean newOrphanRemoval) {
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
		this.virtualXmlOneToOne.setJoinTable(value);
	}
	
	@Override
	public String getTargetEntity() {
		return this.virtualXmlOneToOne.getTargetEntity();
	}

	@Override
	public void setTargetEntity(String value) {
		this.virtualXmlOneToOne.setTargetEntity(value);
	}

	@Override
	public String getMappedBy() {
		return this.virtualXmlOneToOne.getMappedBy();
	}

	@Override
	public void setMappedBy(String value) {
		this.virtualXmlOneToOne.setMappedBy(value);
	}

	@Override
	public EList<XmlPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.virtualXmlOneToOne.getPrimaryKeyJoinColumns();
	}
	
	@Override
	public TextRange getMappedByTextRange() {
		return this.virtualXmlOneToOne.getMappedByTextRange();
	}
	
	@Override
	public TextRange getTargetEntityTextRange() {
		return this.virtualXmlOneToOne.getTargetEntityTextRange();
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
