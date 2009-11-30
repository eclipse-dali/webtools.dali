/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.jpa2.context.OrderColumn2_0;
import org.eclipse.jpt.core.resource.orm.XmlOrderColumn;
import org.eclipse.jpt.core.utility.TextRange;

public class VirtualXmlOrderColumn extends XmlOrderColumn
{	
	protected OrderColumn2_0 javaOrderColumn;

	protected boolean metadataComplete;

	public VirtualXmlOrderColumn(OrderColumn2_0 javaOrderColumn, boolean metadataComplete) {
		super();
		this.javaOrderColumn = javaOrderColumn;
		this.metadataComplete = metadataComplete;
	}
	
	@Override
	public String getColumnDefinition() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaOrderColumn.getColumnDefinition();
	}

	@Override
	public void setColumnDefinition(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public Boolean getInsertable() {
		if (this.metadataComplete) {
			return Boolean.valueOf(this.javaOrderColumn.isDefaultInsertable());
		}
		return Boolean.valueOf(this.javaOrderColumn.isInsertable());
	}

	@Override
	public void setInsertable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public String getName() {
		if (this.metadataComplete) {
			return this.javaOrderColumn.getDefaultName();
		}
		return this.javaOrderColumn.getName();
	}

	@Override
	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	
	@Override
	public Boolean getNullable() {
		if (this.metadataComplete) {
			return Boolean.valueOf(this.javaOrderColumn.isDefaultNullable());
		}
		return Boolean.valueOf(this.javaOrderColumn.isNullable());
	}

	@Override
	public void setNullable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public Boolean getUpdatable() {
		if (this.metadataComplete) {
			return Boolean.valueOf(this.javaOrderColumn.isDefaultUpdatable());
		}
		return Boolean.valueOf(this.javaOrderColumn.isUpdatable());
	}

	@Override
	public void setUpdatable(Boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public TextRange getNameTextRange() {
		return null;
	}
}
