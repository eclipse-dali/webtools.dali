/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlOneToMany;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.JoinFetch;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaOneToManyMappingImpl;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualEclipseLinkXmlOneToMany extends VirtualXmlOneToMany implements XmlOneToMany
{
		
	public VirtualEclipseLinkXmlOneToMany(OrmTypeMapping ormTypeMapping, JavaOneToManyMapping javaOneToManyMapping) {
		super(ormTypeMapping, javaOneToManyMapping);
	}

	public XmlJoinFetchType getJoinFetch() {
		if (isOrmMetadataComplete()) {
			return JoinFetchType.toOrmResourceModel(JoinFetch.DEFAULT_VALUE);
		}
		return JoinFetchType.toOrmResourceModel(((EclipseLinkJavaOneToManyMappingImpl) this.javaAttributeMapping).getJoinFetch().getValue());
	}
	
	public void setJoinFetch(@SuppressWarnings("unused") XmlJoinFetchType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public boolean isPrivateOwned() {
		if (isOrmMetadataComplete()) {
			return false;
		}
		return ((EclipseLinkJavaOneToManyMappingImpl) this.javaAttributeMapping).getPrivateOwned().isPrivateOwned();
	}
	
	public void setPrivateOwned(@SuppressWarnings("unused") boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public TextRange getJoinFetchTextRange() {
		return null;
	}
	
	public TextRange getPrivateOwnedTextRange() {
		return null;
	}
}
