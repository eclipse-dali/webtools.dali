/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlOneToOne;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaOneToOneMappingImpl;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class EclipseLinkVirtualXmlOneToOne extends VirtualXmlOneToOne implements XmlOneToOne
{
		
	public EclipseLinkVirtualXmlOneToOne(OrmTypeMapping ormTypeMapping, JavaOneToOneMapping javaOneToOneMapping) {
		super(ormTypeMapping, javaOneToOneMapping);
	}

	public XmlJoinFetchType getJoinFetch() {
		if (isOrmMetadataComplete()) {
			return null; //don't return default value, it only applies for an empty @JoinFetch
		}
		return JoinFetchType.toOrmResourceModel(((EclipseLinkJavaOneToOneMappingImpl) this.javaAttributeMapping).getJoinFetch().getValue());
	}
	
	public void setJoinFetch(XmlJoinFetchType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public boolean isPrivateOwned() {
		if (isOrmMetadataComplete()) {
			return false;
		}
		return ((EclipseLinkJavaOneToOneMappingImpl) this.javaAttributeMapping).getPrivateOwned().isPrivateOwned();
	}
	
	public void setPrivateOwned(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public XmlAccessMethods getAccessMethods() {
		return null;
	}
	
	public void setAccessMethods(XmlAccessMethods value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$		
	}
	
	public EList<XmlProperty> getProperties() {
		// TODO get from java annotations
		return null;
	}
	
	public TextRange getJoinFetchTextRange() {
		return null;
	}
	
	public TextRange getPrivateOwnedTextRange() {
		return null;
	}
}
