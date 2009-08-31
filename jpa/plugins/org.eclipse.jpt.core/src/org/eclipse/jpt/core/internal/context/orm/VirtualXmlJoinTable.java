/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class VirtualXmlJoinTable 
	extends XmlJoinTable
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected JoinTable joinTable;	
	
	
	public VirtualXmlJoinTable(OrmTypeMapping ormTypeMapping, JoinTable joinTable) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.joinTable = joinTable;
	}
	
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getName() {
		if (this.isOrmMetadataComplete()) {
			return this.joinTable.getDefaultName();
		}
		return this.joinTable.getName();		
	}
	
	@Override
	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public String getCatalog() {
		if (this.isOrmMetadataComplete()) {
			return this.joinTable.getDefaultCatalog();
		}
		return this.joinTable.getCatalog();
	}
	
	@Override
	public void setCatalog(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public String getSchema() {
		if (this.isOrmMetadataComplete()) {
			return this.joinTable.getDefaultSchema();
		}
		return this.joinTable.getSchema();
	}
	
	@Override
	public void setSchema(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	//VirtualXmlJoinTable is rebuilt every time, so just rebuilding the joinColumns list as well
	@Override
	public EList<XmlJoinColumn> getJoinColumns() {
		EList<XmlJoinColumn> joinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.XML_JOIN_TABLE__JOIN_COLUMNS);
		if (this.joinTable == null) {
			return joinColumns;
		}
		for (JoinColumn joinColumn : CollectionTools.iterable(this.joinTable.specifiedJoinColumns())) {
			XmlJoinColumn xmlJoinColumn = new VirtualXmlJoinColumn(joinColumn, isOrmMetadataComplete());
			joinColumns.add(xmlJoinColumn);
		}
		return joinColumns;
	}
	
	//VirtualXmlJoinTable is rebuilt every time, so just rebuilding the joinColumns list as well
	@Override
	public EList<XmlJoinColumn> getInverseJoinColumns() {
		EList<XmlJoinColumn> inverseJoinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.XML_JOIN_TABLE__INVERSE_JOIN_COLUMNS);
		if (this.joinTable == null) {
			return inverseJoinColumns;
		}
		for (JoinColumn joinColumn : CollectionTools.iterable(this.joinTable.specifiedInverseJoinColumns())) {
			XmlJoinColumn xmlJoinColumn = new VirtualXmlJoinColumn(joinColumn, isOrmMetadataComplete());
			inverseJoinColumns.add(xmlJoinColumn);
		}

		return inverseJoinColumns;
	}
	
	@Override
	public EList<XmlUniqueConstraint> getUniqueConstraints() {
		EList<XmlUniqueConstraint> xmlUniqueConstraints = new EObjectContainmentEList<XmlUniqueConstraint>(XmlUniqueConstraint.class, this, OrmPackage.XML_JOIN_TABLE__UNIQUE_CONSTRAINTS);
		for (UniqueConstraint uniqueConstraint : CollectionTools.iterable(this.joinTable.uniqueConstraints())) {
			XmlUniqueConstraint xmlUniqueConstraint = new VirtualXmlUniqueConstraint(uniqueConstraint, isOrmMetadataComplete());
			xmlUniqueConstraints.add(xmlUniqueConstraint);
		}
		
		return xmlUniqueConstraints;
	}
	
	@Override
	public TextRange getNameTextRange() {
		return null;
	}
	
	@Override
	public TextRange getCatalogTextRange() {
		return null;
	}
	
	@Override
	public TextRange getSchemaTextRange() {
		return null;
	}
}
