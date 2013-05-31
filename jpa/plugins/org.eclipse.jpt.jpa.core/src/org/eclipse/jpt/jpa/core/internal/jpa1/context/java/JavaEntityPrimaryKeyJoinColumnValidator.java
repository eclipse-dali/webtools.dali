/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaEntity;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityPrimaryKeyJoinColumnValidator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class JavaEntityPrimaryKeyJoinColumnValidator
	extends EntityPrimaryKeyJoinColumnValidator
{
	protected final AbstractJavaEntity entity;

	public JavaEntityPrimaryKeyJoinColumnValidator(
			BaseJoinColumn column,
			BaseJoinColumn.ParentAdapter parentAdapter,
			AbstractJavaEntity entity) {
		super(column, parentAdapter);
		this.entity = entity;
	}

	@Override
	protected void validateJoinColumnName(List<IMessage> messages) {
		// Based on the spec., the PrimaryKeyJoinColumn annotation is really only meaningful when 
		// applying on an entity subclass so we only validate it when an entity is a descendant or an entity
		// has specified primary key join columns
		if (this.entity.isDescendant() || this.entity.getSpecifiedPrimaryKeyJoinColumnsSize() > 0) {
			super.validateJoinColumnName(messages);
		}
	}
}