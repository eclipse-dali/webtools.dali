/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlBasic;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.EnumType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.utility.TextRange;
/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualXmlBasic2_0 extends XmlBasic
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaBasicMapping javaAttributeMapping;
	
	protected final VirtualXmlBasic virtualXmlBasic;
		
	public VirtualXmlBasic2_0(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaBasicMapping;
		this.virtualXmlBasic = new VirtualXmlBasic(ormTypeMapping, javaBasicMapping);
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlBasic.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlBasic.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlBasic.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlBasic.getNameTextRange();
	}

	@Override
	public XmlColumn getColumn() {
		return this.virtualXmlBasic.getColumn();
	}

	@Override
	public void setColumn(XmlColumn value) {
		this.virtualXmlBasic.setColumn(value);
	}
	
	@Override
	public FetchType getFetch() {
		return this.virtualXmlBasic.getFetch();
	}

	@Override
	public void setFetch(FetchType newFetch) {
		this.virtualXmlBasic.setFetch(newFetch);
	}

	@Override
	public Boolean getOptional() {
		return this.virtualXmlBasic.getOptional();
	}

	@Override
	public void setOptional(Boolean newOptional) {
		this.virtualXmlBasic.setOptional(newOptional);
	}

	@Override
	public boolean isLob() {
		return this.virtualXmlBasic.isLob();
	}

	@Override
	public void setLob(boolean newLob) {
		this.virtualXmlBasic.setLob(newLob);
	}
	
	@Override
	public TextRange getLobTextRange() {
		return this.virtualXmlBasic.getLobTextRange();
	}

	@Override
	public TemporalType getTemporal() {
		return this.virtualXmlBasic.getTemporal();
	}

	@Override
	public void setTemporal(TemporalType setTemporal){
		this.virtualXmlBasic.setTemporal(setTemporal);
	}
	
	@Override
	public TextRange getTemporalTextRange() {
		return this.virtualXmlBasic.getTemporalTextRange();
	}

	@Override
	public EnumType getEnumerated() {
		return this.virtualXmlBasic.getEnumerated();
	}

	@Override
	public void setEnumerated(EnumType setEnumerated) {
		this.virtualXmlBasic.setEnumerated(setEnumerated);
	}
	
	@Override
	public TextRange getEnumeratedTextRange() {
		return null;
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
