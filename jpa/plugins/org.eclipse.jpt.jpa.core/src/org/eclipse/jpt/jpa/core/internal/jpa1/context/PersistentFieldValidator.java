/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.FieldAccessor;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class PersistentFieldValidator
	extends AbstractPersistentAttributeValidator
{

	protected FieldAccessor fieldAccessor;

	public PersistentFieldValidator(
		PersistentAttribute persistentAttribute, FieldAccessor fieldAccessor)
	{
		super(persistentAttribute);
		this.fieldAccessor = fieldAccessor;
	}

	@Override
	protected void validateMappedAttribute(List<IMessage> messages) {
		if (this.fieldAccessor.isFinal()) {
			messages.add(this.buildAttributeMessage(JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_FIELD));
		}
		if (this.fieldAccessor.isPublic()) {
			messages.add(this.buildAttributeMessage(JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_PUBLIC_FIELD));
		}
	}
}
