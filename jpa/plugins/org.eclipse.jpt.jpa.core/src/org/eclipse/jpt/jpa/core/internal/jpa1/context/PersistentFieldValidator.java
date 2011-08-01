/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.PersistentAttributeTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.java.FieldAccessor;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class PersistentFieldValidator
	extends AbstractPersistentAttributeValidator
{

	protected FieldAccessor fieldAccessor;

	public PersistentFieldValidator(
		ReadOnlyPersistentAttribute persistentAttribute, FieldAccessor fieldAccessor, PersistentAttributeTextRangeResolver textRangeResolver)
	{
		super(persistentAttribute, textRangeResolver);
		this.fieldAccessor = fieldAccessor;
	}

	@Override
	protected void validateMappedAttribute(List<IMessage> messages) {
		if (this.fieldAccessor.isFinal()) {
			messages.add(this.buildAttributeMessage(JpaValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_FIELD));
		}
		if (this.fieldAccessor.isPublic()) {
			messages.add(this.buildAttributeMessage(JpaValidationMessages.PERSISTENT_ATTRIBUTE_PUBLIC_FIELD));
		}
	}
}
