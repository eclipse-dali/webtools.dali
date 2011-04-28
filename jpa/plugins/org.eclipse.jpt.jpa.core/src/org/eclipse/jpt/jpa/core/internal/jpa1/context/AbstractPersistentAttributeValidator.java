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
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.PersistentAttributeTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractPersistentAttributeValidator
	implements JptValidator
{
	protected PersistentAttribute persistentAttribute;

	protected JavaPersistentAttribute javaPersistentAttribute;

	protected PersistentAttributeTextRangeResolver textRangeResolver;


	protected AbstractPersistentAttributeValidator(
		PersistentAttribute persistentAttribute, JavaPersistentAttribute javaPersistentAttribute, PersistentAttributeTextRangeResolver textRangeResolver) {
		this.persistentAttribute = persistentAttribute;
		this.javaPersistentAttribute = javaPersistentAttribute;
		this.textRangeResolver = textRangeResolver;
	}


	public final boolean validate(List<IMessage> messages, IReporter reporter) {
		if (this.persistentAttribute.getMappingKey() != MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			this.validateMappedAttribute(messages);
		}
		return true;
	}

	protected abstract void validateMappedAttribute(List<IMessage> messages);

	protected boolean attributeIsField() {
		return (this.javaPersistentAttribute != null) &&
				this.javaPersistentAttribute.isField();
	}

	protected boolean attributeIsProperty() {
		return (this.javaPersistentAttribute != null) &&
				this.javaPersistentAttribute.isProperty();
	}

	protected boolean attributeIsFinal() {
		return (this.javaPersistentAttribute != null) &&
				this.javaPersistentAttribute.isFinal();
	}

	protected boolean attributeIsPublic() {
		return (this.javaPersistentAttribute != null) &&
				this.javaPersistentAttribute.isPublic();
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
