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
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.TypeMappingTextRangeResolver;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractTypeMappingValidator<T extends TypeMapping>
	implements JptValidator
{
	protected T typeMapping;

	protected JavaResourcePersistentType jrpt;

	protected TypeMappingTextRangeResolver textRangeResolver;


	protected AbstractTypeMappingValidator(
			T typeMapping, JavaResourcePersistentType jrpt, TypeMappingTextRangeResolver textRangeResolver) {
		this.typeMapping = typeMapping;
		this.jrpt = jrpt;
		this.textRangeResolver = textRangeResolver;
	}


	public boolean validate(List<IMessage> messages, IReporter reporter) {
		this.validateType(messages);
		return true;
	}

	protected abstract void validateType(List<IMessage> messages);

	protected boolean isMemberType() {
		if (this.jrpt == null) {
			return false;
		}
		return this.jrpt.isMemberType();
	}

	protected boolean isStaticType() {
		if (this.jrpt == null) {
			return false;
		}
		return this.jrpt.isStatic();
	}

	protected boolean isFinalType() {
		if (this.jrpt == null) {
			return false;
		}
		return this.jrpt.isFinal();
	}

	protected boolean hasPrivateNoArgConstructor() {
		if (this.jrpt == null) {
			return false;
		}
		return this.jrpt.hasPrivateNoArgConstructor();
	}

	protected boolean hasNoArgConstructor() {
		if (this.jrpt == null) {
			return false;
		}
		return this.jrpt.hasNoArgConstructor();
	}

	protected IMessage buildTypeMessage(String msgID) {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				msgID,
				new String[] {this.typeMapping.getName()},
				this.typeMapping,
				this.textRangeResolver.getTypeMappingTextRange()
			);
	}
}
