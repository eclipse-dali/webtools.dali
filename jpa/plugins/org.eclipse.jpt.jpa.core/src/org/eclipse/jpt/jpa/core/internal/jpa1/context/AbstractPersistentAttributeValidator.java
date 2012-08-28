/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractPersistentAttributeValidator
	implements JptValidator
{
	protected ReadOnlyPersistentAttribute persistentAttribute;


	protected AbstractPersistentAttributeValidator(
		ReadOnlyPersistentAttribute persistentAttribute) {
		this.persistentAttribute = persistentAttribute;
	}

	public final boolean validate(List<IMessage> messages, IReporter reporter) {
		if (this.persistentAttribute.getMappingKey() != MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			this.validateMappedAttribute(messages);
		}
		return true;
	}

	protected abstract void validateMappedAttribute(List<IMessage> messages);

	protected IMessage buildAttributeMessage(String msgID) {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			msgID,
			new String[] {this.persistentAttribute.getName()},
			this.persistentAttribute, 
			this.persistentAttribute.getValidationTextRange()
		);
	}
}
