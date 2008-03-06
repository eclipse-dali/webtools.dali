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

import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;

/**
 * A virtual primary key join column is used to represent the XmlPrimaryKeyJoinColumn resource object
 * within a virtual secondary table.  A virtual secondary table is one which is not specified
 * in the orm.xml file, but is implied from the underlying java.  Virtual pk join column
 * is not used when the secondary table is specified in the orm.xml.
 * 
 * A virtual pk join column delegates to the underlying java pk join column for its state.
 */
public class VirtualXmlPrimaryKeyJoinColumn extends AbstractJpaEObject implements XmlPrimaryKeyJoinColumn
{
	
	protected JavaPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn;

	
	protected VirtualXmlPrimaryKeyJoinColumn(JavaPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn) {
		super();
		this.javaPrimaryKeyJoinColumn = javaPrimaryKeyJoinColumn;
	}

	public String getName() {
		return this.javaPrimaryKeyJoinColumn.getName();
	}

	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getColumnDefinition() {
		return this.javaPrimaryKeyJoinColumn.getColumnDefinition();
	}
	
	public void setColumnDefinition(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public String getReferencedColumnName() {
		return this.javaPrimaryKeyJoinColumn.getReferencedColumnName();
	}
	
	public void setReferencedColumnName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public TextRange nameTextRange() {
		return null;
	}
	
	public TextRange referencedColumnNameTextRange() {
		return null;
	}
		
	public void update(JavaPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn) {
		this.javaPrimaryKeyJoinColumn = javaPrimaryKeyJoinColumn;
	}
}
