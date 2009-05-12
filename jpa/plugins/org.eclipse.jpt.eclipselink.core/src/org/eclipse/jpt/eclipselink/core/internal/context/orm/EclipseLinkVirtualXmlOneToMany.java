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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlJoinColumn;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlOneToMany;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class EclipseLinkVirtualXmlOneToMany<T extends EclipseLinkJavaOneToManyMapping>
	extends VirtualXmlOneToMany<T>
	implements XmlOneToMany
{	
	public EclipseLinkVirtualXmlOneToMany(
			OrmTypeMapping ormTypeMapping, T javaOneToManyMapping) {
		super(ormTypeMapping, javaOneToManyMapping);
	}
	
	
	@Override
	public EList<XmlJoinColumn> getJoinColumns() {
		//TODO need to check metadataComplete here
		EList<XmlJoinColumn> joinColumns = 
			new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.XML_ONE_TO_ONE__JOIN_COLUMNS);
		//TODO here i'm using joinColumns() while VirtualXmlJoinTable uses specifiedJoinColumns()???
		for (JavaJoinColumn joinColumn : 
				CollectionTools.iterable(
					this.javaAttributeMapping.getRelationshipReference().
						getJoinColumnJoiningStrategy().joinColumns())) {
			XmlJoinColumn xmlJoinColumn = new VirtualXmlJoinColumn(joinColumn, this.isOrmMetadataComplete());
			joinColumns.add(xmlJoinColumn);
		}
		return joinColumns;
	}

	public XmlJoinFetchType getJoinFetch() {
		if (isOrmMetadataComplete()) {
			return null; //don't return default value, it only applies for an empty @JoinFetch
		}
		return JoinFetchType.toOrmResourceModel(((EclipseLinkJavaOneToManyMapping) this.javaAttributeMapping).getJoinFetch().getValue());
	}
	
	public void setJoinFetch(XmlJoinFetchType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public boolean isPrivateOwned() {
		if (isOrmMetadataComplete()) {
			return false;
		}
		return ((EclipseLinkJavaOneToManyMapping) this.javaAttributeMapping).getPrivateOwned().isPrivateOwned();
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
