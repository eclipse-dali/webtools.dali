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

import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;

public class VirtualXmlJoinColumn extends XmlJoinColumn
{	
	protected JoinColumn javaJoinColumn;

	protected boolean metadataComplete;

	public VirtualXmlJoinColumn(JoinColumn javaJoinColumn, boolean metadataComplete) {
		super();
		this.javaJoinColumn = javaJoinColumn;
		this.metadataComplete = metadataComplete;
	}
	
	@Override
	public String getColumnDefinition() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaJoinColumn.getColumnDefinition();
	}

	@Override
	public void setColumnDefinition(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public Boolean getInsertable() {
		if (this.metadataComplete) {
			return Boolean.valueOf(this.javaJoinColumn.isDefaultInsertable());
		}
		return Boolean.valueOf(this.javaJoinColumn.isInsertable());
	}

	@Override
	public void setInsertable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public String getName() {
		if (this.metadataComplete) {
			return this.javaJoinColumn.getDefaultName();
		}
		return this.javaJoinColumn.getName();
	}

	@Override
	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public String getReferencedColumnName() {
		if (this.metadataComplete) {
			return this.javaJoinColumn.getDefaultReferencedColumnName();
		}
		return this.javaJoinColumn.getReferencedColumnName();
	}

	@Override
	public void setReferencedColumnName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public Boolean getNullable() {
		if (this.metadataComplete) {
			return Boolean.valueOf(this.javaJoinColumn.isDefaultNullable());
		}
		return Boolean.valueOf(this.javaJoinColumn.isNullable());
	}

	@Override
	public void setNullable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public String getTable() {
		if (this.metadataComplete) {
			return this.javaJoinColumn.getDefaultTable();
		}
		return this.javaJoinColumn.getTable();
	}

	@Override
	public void setTable(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public Boolean getUnique() {
		if (this.metadataComplete) {
			return Boolean.valueOf(this.javaJoinColumn.isDefaultUnique());
		}
		return Boolean.valueOf(this.javaJoinColumn.isUnique());
	}

	@Override
	public void setUnique(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public Boolean getUpdatable() {
		if (this.metadataComplete) {
			return Boolean.valueOf(this.javaJoinColumn.isDefaultUpdatable());
		}
		return Boolean.valueOf(this.javaJoinColumn.isUpdatable());
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
	
	@Override
	public TextRange getReferencedColumnNameTextRange() {
		return null;
	}
	
}
