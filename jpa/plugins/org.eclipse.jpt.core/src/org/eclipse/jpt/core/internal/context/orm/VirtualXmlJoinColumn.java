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

import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;

public class VirtualXmlJoinColumn extends AbstractJpaEObject implements XmlJoinColumn
{	
	protected JavaJoinColumn javaJoinColumn;

	protected boolean metadataComplete;

	protected VirtualXmlJoinColumn(JavaJoinColumn javaJoinColumn, boolean metadataComplete) {
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

	public void setColumnDefinition(@SuppressWarnings("unused")String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public Boolean getInsertable() {
		if (this.metadataComplete) {
			return Boolean.valueOf(this.javaJoinColumn.isDefaultInsertable());
		}
		return Boolean.valueOf(this.javaJoinColumn.isInsertable());
	}

	public void setInsertable(@SuppressWarnings("unused")Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public String getName() {
		if (this.metadataComplete) {
			return this.javaJoinColumn.getDefaultName();
		}
		return this.javaJoinColumn.getName();
	}

	public void setName(@SuppressWarnings("unused")String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public String getReferencedColumnName() {
		if (this.metadataComplete) {
			return this.javaJoinColumn.getDefaultReferencedColumnName();
		}
		return this.javaJoinColumn.getReferencedColumnName();
	}

	public void setReferencedColumnName(@SuppressWarnings("unused")String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public Boolean getNullable() {
		if (this.metadataComplete) {
			return Boolean.valueOf(this.javaJoinColumn.isDefaultNullable());
		}
		return Boolean.valueOf(this.javaJoinColumn.isNullable());
	}

	public void setNullable(@SuppressWarnings("unused")Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public String getTable() {
		if (this.metadataComplete) {
			return this.javaJoinColumn.getDefaultTable();
		}
		return this.javaJoinColumn.getTable();
	}

	public void setTable(@SuppressWarnings("unused")String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public Boolean getUnique() {
		if (this.metadataComplete) {
			return Boolean.valueOf(this.javaJoinColumn.isDefaultUnique());
		}
		return Boolean.valueOf(this.javaJoinColumn.isUnique());
	}

	public void setUnique(@SuppressWarnings("unused")Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public Boolean getUpdatable() {
		if (this.metadataComplete) {
			return Boolean.valueOf(this.javaJoinColumn.isDefaultUpdatable());
		}
		return Boolean.valueOf(this.javaJoinColumn.isUpdatable());
	}

	public void setUpdatable(@SuppressWarnings("unused") Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public TextRange getNameTextRange() {
		return null;
	}
	
	public TextRange getTableTextRange() {
		return null;
	}
	
	public TextRange getReferencedColumnNameTextRange() {
		return null;
	}
	
}
