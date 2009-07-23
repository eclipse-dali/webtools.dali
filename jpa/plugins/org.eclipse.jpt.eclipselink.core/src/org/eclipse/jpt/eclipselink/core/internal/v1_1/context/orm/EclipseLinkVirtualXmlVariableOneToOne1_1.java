/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm;

import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkVirtualXmlVariableOneToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.v1_1.resource.orm.XmlVariableOneToOne;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class EclipseLinkVirtualXmlVariableOneToOne1_1 extends XmlVariableOneToOne
{

	protected OrmTypeMapping ormTypeMapping;
	
	protected final EclipseLinkJavaVariableOneToOneMapping javaAttributeMapping;

	protected final EclipseLinkVirtualXmlVariableOneToOne virtualXmlVariableOneToOne;
		
	public EclipseLinkVirtualXmlVariableOneToOne1_1(OrmTypeMapping ormTypeMapping, EclipseLinkJavaVariableOneToOneMapping javaVariableOneToOneMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaVariableOneToOneMapping;
		this.virtualXmlVariableOneToOne = new EclipseLinkVirtualXmlVariableOneToOne(ormTypeMapping, javaVariableOneToOneMapping);
	}
	
	public boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlVariableOneToOne.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlVariableOneToOne.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlVariableOneToOne.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlVariableOneToOne.getNameTextRange();
	}

	@Override
	public XmlAccessMethods getAccessMethods() {
		return this.virtualXmlVariableOneToOne.getAccessMethods();
	}
	
	@Override
	public void setAccessMethods(XmlAccessMethods value) {
		this.virtualXmlVariableOneToOne.setAccessMethods(value);
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
