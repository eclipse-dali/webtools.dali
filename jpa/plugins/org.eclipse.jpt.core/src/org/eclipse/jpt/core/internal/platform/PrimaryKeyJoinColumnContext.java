/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;

public class PrimaryKeyJoinColumnContext extends AbstractJoinColumnContext<IPrimaryKeyJoinColumn>
{
	
	public PrimaryKeyJoinColumnContext(IContext parentContext, IPrimaryKeyJoinColumn column) {
		super(parentContext, column);
	}


	protected String buildDefaultReferencedColumnName() {
		return this.buildDefaultName();
	}

	protected String buildDefaultName() {
		IEntity entity = (IEntity) getColumn().getOwner().getTypeMapping();
		
		if (entity.getPrimaryKeyJoinColumns().size() != 1) {
			return null;
		}
		String pkColumnName = entity.parentEntity().primaryKeyColumnName();
		return pkColumnName;
	}


}
