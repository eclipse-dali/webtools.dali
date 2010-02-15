/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * A virtual column is used to represent the XmlColumn resource object
 * within a virtual mapping.  A virtual mapping is one which is not specified
 * in the orm.xml file, but is implied from the underlying java.  Virtual column
 * is not used when the mapping is specified in the orm.xml, but the column tag does not exist.
 * 
 * A virtual column delegates to the underlying java column for its state.  The metadataComplete
 * flag determines whether it will get specified or default information from the java column
 *
 */
public class VirtualXmlColumn extends XmlColumn
{
	
	protected Column column;

	protected OrmTypeMapping ormTypeMapping;
	
	
	public VirtualXmlColumn(OrmTypeMapping ormTypeMapping, Column column) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.column = column;
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}

	@Override
	public String getColumnDefinition() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		return this.column.getColumnDefinition();
	}

	@Override
	public void setColumnDefinition(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public Boolean getInsertable() {
		if (this.isOrmMetadataComplete()) {
			return Boolean.valueOf(this.column.isDefaultInsertable());
		}
		return Boolean.valueOf(this.column.isInsertable());
	}

	@Override
	public void setInsertable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public Integer getLength() {
		if (this.isOrmMetadataComplete()) {
			return Integer.valueOf(this.column.getDefaultLength());
		}
		return Integer.valueOf(this.column.getLength());
	}

	@Override
	public void setLength(Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public String getName() {
		if (this.isOrmMetadataComplete()) {
			return this.column.getDefaultName();
		}
		return this.column.getName();
	}

	@Override
	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public Boolean getNullable() {
		if (this.isOrmMetadataComplete()) {
			return Boolean.valueOf(this.column.isDefaultNullable());
		}
		return Boolean.valueOf(this.column.isNullable());
	}

	@Override
	public void setNullable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public Integer getPrecision() {
		if (this.isOrmMetadataComplete()) {
			return Integer.valueOf(this.column.getDefaultPrecision());
		}
		return Integer.valueOf(this.column.getPrecision());
	}

	@Override
	public void setPrecision(Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public Integer getScale() {
		if (this.isOrmMetadataComplete()) {
			return Integer.valueOf(this.column.getDefaultScale());
		}
		return Integer.valueOf(this.column.getScale());
	}

	@Override
	public void setScale(Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public String getTable() {
		if (this.isOrmMetadataComplete()) {
			return this.column.getDefaultTable();
		}
		return this.column.getTable();
	}

	@Override
	public void setTable(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public Boolean getUnique() {
		if (this.isOrmMetadataComplete()) {
			return Boolean.valueOf(this.column.isDefaultUnique());
		}
		return Boolean.valueOf(this.column.isUnique());
	}

	@Override
	public void setUnique(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public Boolean getUpdatable() {
		if (this.isOrmMetadataComplete()) {
			return Boolean.valueOf(this.column.isDefaultUpdatable());
		}
		return Boolean.valueOf(this.column.isUpdatable());
	}

	@Override
	public void setUpdatable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public TextRange getNameTextRange() {
		return null;
	}
	
	@Override
	public TextRange getTableTextRange() {
		return null;
	}
}
