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
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.PersistentAttributeTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericPersistentAttributeValidator
	extends AbstractPersistentAttributeValidator
{
	public GenericPersistentAttributeValidator(
		ReadOnlyPersistentAttribute persistentAttribute, JavaPersistentAttribute javaPersistentAttribute, PersistentAttributeTextRangeResolver textRangeResolver)
	{
		super(persistentAttribute, javaPersistentAttribute, textRangeResolver);
	}

	@Override
	protected void validateMappedAttribute(List<IMessage> messages) {
		if (this.javaPersistentAttribute.isField()) {
			this.validateMappedField(messages);
		} else {
			this.validateMappedProperty(messages);
		}
	}

	protected void validateMappedField(List<IMessage> messages) {
		if (this.javaPersistentAttribute.isFinal()) {
			messages.add(this.buildAttributeMessage(JpaValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_FIELD));
		}
		if (this.javaPersistentAttribute.isPublic()) {
			messages.add(this.buildAttributeMessage(JpaValidationMessages.PERSISTENT_ATTRIBUTE_PUBLIC_FIELD));
		}
	}

	protected void validateMappedProperty(List<IMessage> messages) {
		//TODO need to check both the getter and the setter
		if (this.javaPersistentAttribute.isFinal()) {
			messages.add(this.buildAttributeMessage(JpaValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_GETTER));
		}
	}
}
