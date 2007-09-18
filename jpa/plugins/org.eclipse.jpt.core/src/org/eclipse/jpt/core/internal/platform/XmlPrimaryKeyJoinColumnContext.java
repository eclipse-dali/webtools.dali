/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;

public class XmlPrimaryKeyJoinColumnContext extends PrimaryKeyJoinColumnContext
{
	public XmlPrimaryKeyJoinColumnContext(ParentContext parentContext, IPrimaryKeyJoinColumn column) {
		super(parentContext, column);
	}
	
	@Override
	public ParentContext getParentContext() {
		return (ParentContext) super.getParentContext();
	}

	protected String buildDefaultName() {
		if (getColumn().isVirtual()) {
			if (!((XmlTypeMapping) getColumn().getOwner().getTypeMapping()).isXmlMetadataComplete()) {
				IPrimaryKeyJoinColumn javaJoinColumn = javaPrimaryKeyJoinColumn(getColumn().getOwner().indexOf(getColumn()));
				if (javaJoinColumn != null) {
					return javaJoinColumn.getName();
				}
			}
		}
		return super.buildDefaultName();
	}
	
	@Override
	protected String buildDefaultReferencedColumnName() {
		if (getColumn().isVirtual()) {
			if (!((XmlTypeMapping) getColumn().getOwner().getTypeMapping()).isXmlMetadataComplete()) {
				IPrimaryKeyJoinColumn javaJoinColumn = javaPrimaryKeyJoinColumn(getColumn().getOwner().indexOf(getColumn()));
				if (javaJoinColumn != null) {
					return javaJoinColumn.getReferencedColumnName();
				}
			}
		}
		return super.buildDefaultReferencedColumnName();
	}
	
	
	public interface ParentContext extends IContext {
		/**
		 * Return the JavaJoinColumn that corresponds to the the joinColumn
		 * at the given index.  Return null if it does not exist
		 */
		IPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn(int index);
	}
	
	private IPrimaryKeyJoinColumn javaPrimaryKeyJoinColumn(int index) {
		return getParentContext().javaPrimaryKeyJoinColumn(index);
	}

}
