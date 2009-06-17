/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.internal.context.orm;

import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlVersion;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.EnumType;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt2_0.core.resource.orm.XmlVersion;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class Generic2_0VirtualXmlVersion extends XmlVersion
{
		
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaVersionMapping javaAttributeMapping;

	protected final VirtualXmlVersion virtualXmlVersion;
	
	public Generic2_0VirtualXmlVersion(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaVersionMapping;
		this.virtualXmlVersion = new VirtualXmlVersion(ormTypeMapping, javaVersionMapping);
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlVersion.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlVersion.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlVersion.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlVersion.getNameTextRange();
	}

	@Override
	public XmlColumn getColumn() {
		return this.virtualXmlVersion.getColumn();
	}

	@Override
	public void setColumn(XmlColumn value) {
		this.virtualXmlVersion.setColumn(value);
	}

	@Override
	public TemporalType getTemporal() {
		return this.virtualXmlVersion.getTemporal();
	}

	@Override
	public void setTemporal(TemporalType newTemporal){
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public EnumType getEnumerated() {
		return this.virtualXmlVersion.getEnumerated();
	}
	
	@Override
	public void setEnumerated(EnumType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public boolean isLob() {
		return this.virtualXmlVersion.isLob();
	}
	
	@Override
	public void setLob(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}	
	
	@Override
	public TextRange getEnumeratedTextRange() {
		return this.virtualXmlVersion.getEnumeratedTextRange();
	}
	
	@Override
	public TextRange getLobTextRange() {
		return this.virtualXmlVersion.getLobTextRange();
	}
	
	@Override
	public TextRange getTemporalTextRange() {
		return this.virtualXmlVersion.getTemporalTextRange();
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
