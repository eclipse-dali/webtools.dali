/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.PersistentAttributeTextRangeResolver;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class GenericPersistentAttributeValidator
	extends AbstractPersistentAttributeValidator
{
	public GenericPersistentAttributeValidator(
		PersistentAttribute persistentAttribute, JavaPersistentAttribute javaPersistentAttribute, PersistentAttributeTextRangeResolver textRangeResolver)
	{
		super(persistentAttribute, javaPersistentAttribute, textRangeResolver);
	}

	@Override
	protected void validateAttribute(List<IMessage> messages) {
		if (this.persistentAttribute.getMappingKey() == MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			return;
		}

		if (this.isFieldAttribute()) {
			if (this.isFinalAttribute()) {
				messages.add(this.buildAttributeMessage(JpaValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_FIELD));
			}
			if (this.isPublicAttribute()) {
				messages.add(this.buildAttributeMessage(JpaValidationMessages.PERSISTENT_ATTRIBUTE_PUBLIC_FIELD));
			}
		}
		else if (this.isPropertyAttribute()) {
			//TODO need to check both the getter and the setter
			if (this.isFinalAttribute()) {
				messages.add(this.buildAttributeMessage(JpaValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_GETTER));
			}
		}
	}

	protected IMessage buildAttributeMessage(String msgID) {
		return DefaultJpaValidationMessages.buildMessage(
			IMessage.HIGH_SEVERITY,
			msgID,
			new String[] {this.persistentAttribute.getName()},
			this.persistentAttribute, 
			this.textRangeResolver.getAttributeTextRange()
		);
	}
}
