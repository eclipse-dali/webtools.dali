/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
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
public class VirtualXmlColumn extends AbstractJpaEObject implements XmlColumn
{
	
	protected Column column;

	protected OrmTypeMapping ormTypeMapping;
	
	
	protected VirtualXmlColumn(OrmTypeMapping ormTypeMapping, Column column) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.column = column;
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}

	public String getColumnDefinition() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		return this.column.getColumnDefinition();
	}

	public void setColumnDefinition(@SuppressWarnings("unused")String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public Boolean getInsertable() {
		if (this.isOrmMetadataComplete()) {
			return this.column.getDefaultInsertable();
		}
		return this.column.getInsertable();
	}

	public void setInsertable(@SuppressWarnings("unused")Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public Integer getLength() {
		if (this.isOrmMetadataComplete()) {
			return this.column.getDefaultLength();
		}
		return this.column.getLength();
	}

	public void setLength(@SuppressWarnings("unused")Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public String getName() {
		if (this.isOrmMetadataComplete()) {
			return this.column.getDefaultName();
		}
		return this.column.getName();
	}

	public void setName(@SuppressWarnings("unused")String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public Boolean getNullable() {
		if (this.isOrmMetadataComplete()) {
			return this.column.getDefaultNullable();
		}
		return this.column.getNullable();
	}

	public void setNullable(@SuppressWarnings("unused")Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public Integer getPrecision() {
		if (this.isOrmMetadataComplete()) {
			return this.column.getDefaultPrecision();
		}
		return this.column.getPrecision();
	}

	public void setPrecision(@SuppressWarnings("unused")Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public Integer getScale() {
		if (this.isOrmMetadataComplete()) {
			return this.column.getDefaultScale();
		}
		return this.column.getScale();
	}

	public void setScale(@SuppressWarnings("unused")Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public String getTable() {
		if (!this.isOrmMetadataComplete()) {
			if (this.column.getSpecifiedTable() != null) {
				return this.column.getSpecifiedTable();
			}	
		}
		return this.ormTypeMapping.getPrimaryTableName();
	}

	public void setTable(@SuppressWarnings("unused")String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public Boolean getUnique() {
		if (this.isOrmMetadataComplete()) {
			return this.column.getDefaultUnique();
		}
		return this.column.getUnique();
	}

	public void setUnique(@SuppressWarnings("unused")Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public Boolean getUpdatable() {
		if (this.isOrmMetadataComplete()) {
			return this.column.getDefaultUpdatable();
		}
		return this.column.getUpdatable();
	}

	public void setUpdatable(@SuppressWarnings("unused")Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public void update(Column column) {
		this.column = column;
	}
	
	public TextRange getNameTextRange() {
		return null;
	}
	
	public TextRange getTableTextRange() {
		return null;
	}
}
