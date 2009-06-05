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

import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * A virtual primary key join column is used to represent the XmlPrimaryKeyJoinColumn resource object
 * within a virtual secondary table.  A virtual secondary table is one which is not specified
 * in the orm.xml file, but is implied from the underlying java.  Virtual pk join column
 * is not used when the secondary table is specified in the orm.xml.
 * 
 * A virtual pk join column delegates to the underlying java pk join column for its state.
 */
public class VirtualXmlPrimaryKeyJoinColumn extends XmlPrimaryKeyJoinColumn
{
	
	protected JavaPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn;

	
	protected VirtualXmlPrimaryKeyJoinColumn(JavaPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn) {
		super();
		this.javaPrimaryKeyJoinColumn = javaPrimaryKeyJoinColumn;
	}

	@Override
	public String getName() {
		return this.javaPrimaryKeyJoinColumn.getName();
	}

	@Override
	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public String getColumnDefinition() {
		return this.javaPrimaryKeyJoinColumn.getColumnDefinition();
	}
	
	@Override
	public void setColumnDefinition(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public String getReferencedColumnName() {
		return this.javaPrimaryKeyJoinColumn.getReferencedColumnName();
	}
	
	@Override
	public void setReferencedColumnName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public TextRange getNameTextRange() {
		return null;
	}
	
	@Override
	public TextRange getReferencedColumnNameTextRange() {
		return null;
	}
}
