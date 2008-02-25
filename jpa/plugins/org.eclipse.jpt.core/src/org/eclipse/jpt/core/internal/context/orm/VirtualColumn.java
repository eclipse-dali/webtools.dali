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

import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.XmlColumn;

public class VirtualColumn extends AbstractJpaEObject implements XmlColumn
{
	
	protected JavaColumn javaColumn;

	protected boolean metadataComplete;

	protected VirtualColumn(JavaColumn javaColumn, boolean metadataComplete) {
		super();
		this.javaColumn = javaColumn;
		this.metadataComplete = metadataComplete;
	}
	
	public String getColumnDefinition() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaColumn.getColumnDefinition();
	}

	public void setColumnDefinition(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Boolean getInsertable() {
		if (this.metadataComplete) {
			return this.javaColumn.getDefaultInsertable();
		}
		return this.javaColumn.getInsertable();
	}

	public void setInsertable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Integer getLength() {
		if (this.metadataComplete) {
			return this.javaColumn.getDefaultLength();
		}
		return this.javaColumn.getLength();
	}

	public void setLength(Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getName() {
		if (this.metadataComplete) {
			return this.javaColumn.getDefaultName();
		}
		return this.javaColumn.getName();
	}

	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Boolean getNullable() {
		if (this.metadataComplete) {
			return this.javaColumn.getDefaultNullable();
		}
		return this.javaColumn.getNullable();
	}

	public void setNullable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Integer getPrecision() {
		if (this.metadataComplete) {
			return this.javaColumn.getDefaultPrecision();
		}
		return this.javaColumn.getPrecision();
	}

	public void setPrecision(Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Integer getScale() {
		if (this.metadataComplete) {
			return this.javaColumn.getDefaultScale();
		}
		return this.javaColumn.getScale();
	}

	public void setScale(Integer value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getTable() {
		if (this.metadataComplete) {
			return this.javaColumn.getDefaultTable();
		}
		return this.javaColumn.getTable();
	}

	public void setTable(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Boolean getUnique() {
		if (this.metadataComplete) {
			return this.javaColumn.getDefaultUnique();
		}
		return this.javaColumn.getUnique();
	}

	public void setUnique(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Boolean getUpdatable() {
		if (this.metadataComplete) {
			return this.javaColumn.getDefaultUpdatable();
		}
		return this.javaColumn.getUpdatable();
	}

	public void setUpdatable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public void update(JavaColumn javaColumn) {
		this.javaColumn = javaColumn;
	}
}
