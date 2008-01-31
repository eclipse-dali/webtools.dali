/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm;

import org.eclipse.jpt.core.internal.context.java.IJavaJoinColumn;
import org.eclipse.jpt.core.internal.resource.common.JpaEObject;

public class VirtualJoinColumn extends JpaEObject implements JoinColumn
{	
	protected IJavaJoinColumn javaJoinColumn;

	protected boolean metadataComplete;

	protected VirtualJoinColumn(IJavaJoinColumn javaJoinColumn, boolean metadataComplete) {
		super();
		this.javaJoinColumn = javaJoinColumn;
		this.metadataComplete = metadataComplete;
	}
	
	public String getColumnDefinition() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaJoinColumn.getColumnDefinition();
	}

	public void setColumnDefinition(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Boolean getInsertable() {
		if (this.metadataComplete) {
			return this.javaJoinColumn.getDefaultInsertable();
		}
		return this.javaJoinColumn.getInsertable();
	}

	public void setInsertable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getName() {
		if (this.metadataComplete) {
			return this.javaJoinColumn.getDefaultName();
		}
		return this.javaJoinColumn.getName();
	}

	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getReferencedColumnName() {
		if (this.metadataComplete) {
			return this.javaJoinColumn.getDefaultReferencedColumnName();
		}
		return this.javaJoinColumn.getReferencedColumnName();
	}

	public void setReferencedColumnName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public Boolean getNullable() {
		if (this.metadataComplete) {
			return this.javaJoinColumn.getDefaultNullable();
		}
		return this.javaJoinColumn.getNullable();
	}

	public void setNullable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getTable() {
		if (this.metadataComplete) {
			return this.javaJoinColumn.getDefaultTable();
		}
		return this.javaJoinColumn.getTable();
	}

	public void setTable(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Boolean getUnique() {
		if (this.metadataComplete) {
			return this.javaJoinColumn.getDefaultUnique();
		}
		return this.javaJoinColumn.getUnique();
	}

	public void setUnique(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Boolean getUpdatable() {
		if (this.metadataComplete) {
			return this.javaJoinColumn.getDefaultUpdatable();
		}
		return this.javaJoinColumn.getUpdatable();
	}

	public void setUpdatable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public void update(IJavaJoinColumn javaJoinColumn) {
		this.javaJoinColumn = javaJoinColumn;
	}

}
