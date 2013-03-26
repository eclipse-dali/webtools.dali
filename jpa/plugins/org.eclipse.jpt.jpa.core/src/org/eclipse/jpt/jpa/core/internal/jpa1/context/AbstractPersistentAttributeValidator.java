/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractPersistentAttributeValidator
	implements JpaValidator
{
	protected PersistentAttribute persistentAttribute;


	protected AbstractPersistentAttributeValidator(
		PersistentAttribute persistentAttribute) {
		this.persistentAttribute = persistentAttribute;
	}

	public final boolean validate(List<IMessage> messages, IReporter reporter) {
		if (this.persistentAttribute.getMappingKey() != MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			this.validateMappedAttribute(messages);
		}
		return true;
	}

	protected abstract void validateMappedAttribute(List<IMessage> messages);

	protected IMessage buildAttributeMessage(ValidationMessage msg) {
		return ValidationMessageTools.buildValidationMessage(
				this.persistentAttribute.getResource(),
				this.persistentAttribute.getValidationTextRange(),
				msg,
				this.persistentAttribute.getName()
			);
	}
}
