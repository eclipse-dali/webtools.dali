/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverrideContainer;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public class GenericOrmAttributeOverride extends AbstractOrmOverride
	implements OrmAttributeOverride, OrmColumn.Owner
{

	protected final OrmColumn column;

	public GenericOrmAttributeOverride(OrmAttributeOverrideContainer parent, AttributeOverride.Owner owner, XmlAttributeOverride resourceAttributeOverride) {
		super(parent, owner, resourceAttributeOverride);
		this.column = getXmlContextNodeFactory().buildOrmColumn(this, this);
		this.column.initialize(resourceAttributeOverride.getColumn());
	}

	public OrmAttributeOverride setVirtual(boolean virtual) {
		return (OrmAttributeOverride) getOwner().setVirtual(virtual, this);
	}

	@Override
	public Owner getOwner() {
		return (Owner) super.getOwner();
	}

	@Override
	protected XmlAttributeOverride getResourceOverride() {
		return (XmlAttributeOverride) super.getResourceOverride();
	}

	public void update(XmlAttributeOverride xmlAttributeOverride) {
		super.update(xmlAttributeOverride);
		this.column.update(xmlAttributeOverride.getColumn());
	}
	

	// ********************* column ****************

	public OrmColumn getColumn() {
		return this.column;
	}

	//************* NamedColumn.Owner implementation **************
	public TypeMapping getTypeMapping() {
		return getOwner().getTypeMapping();
	}

	public Table getDbTable(String tableName) {
		return this.getOwner().getDbTable(tableName);
	}

	public String getDefaultColumnName() {
		Column column = resolveOverriddenColumn();
		if (column == null) {
			return null;
		}
		return column.getName();
	}
	
	//************* BaseColumn.Owner implementation **************
	
	public String getDefaultTableName() {
		Column column = resolveOverriddenColumn();
		if (column == null) {
			return null;
		}
		String tableName = column.getSpecifiedTable();
		if (tableName != null) {
			return tableName;
		}
		return getOwner().getDefaultTableName();
	}
	
	protected Column resolveOverriddenColumn() {
		return getOwner().resolveOverriddenColumn(getName());
	}

	public boolean tableNameIsInvalid(String tableName) {
		return getOwner().tableNameIsInvalid(tableName);
	}

	public Iterator<String> candidateTableNames() {
		return getOwner().candidateTableNames();
	}

	public boolean isVirtual() {
		return getOwner().isVirtual(this);
	}


	//***************** OrmColumn.Owner implementation ****************
	
	public XmlColumn getResourceColumn() {
		return this.getResourceOverride().getColumn();
	}
	
	public void addResourceColumn() {
		this.getResourceOverride().setColumn(OrmFactory.eINSTANCE.createXmlColumn());
	}
	
	public void removeResourceColumn() {
		this.getResourceOverride().setColumn(null);
	}


	//****************** validation ********************

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		getColumn().validate(messages, reporter);
	}

	public IMessage buildUnresolvedNameMessage(NamedColumn column, TextRange textRange) {
		return getOwner().buildColumnUnresolvedNameMessage(this, column, textRange);
	}

	public IMessage buildTableNotValidMessage(BaseColumn column, TextRange textRange) {
		return getOwner().buildColumnTableNotValidMessage(this, column, textRange);
	}
}
