/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context;

import java.util.List;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.TypeMappingTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractTypeMappingValidator<T extends TypeMapping>
	implements JptValidator
{
	protected T typeMapping;

	protected JavaResourceType jrt;

	protected TypeMappingTextRangeResolver textRangeResolver;


	protected AbstractTypeMappingValidator(
			T typeMapping, JavaResourceType jrt, TypeMappingTextRangeResolver textRangeResolver) {
		this.typeMapping = typeMapping;
		this.jrt = jrt;
		this.textRangeResolver = textRangeResolver;
	}


	public boolean validate(List<IMessage> messages, IReporter reporter) {
		this.validateType(messages);
		return true;
	}

	protected abstract void validateType(List<IMessage> messages);

	protected boolean isMemberType() {
		if (this.jrt == null) {
			return false;
		}
		return this.jrt.isMemberType();
	}

	protected boolean isStaticType() {
		if (this.jrt == null) {
			return false;
		}
		return this.jrt.isStatic();
	}

	protected boolean isFinalType() {
		if (this.jrt == null) {
			return false;
		}
		return this.jrt.isFinal();
	}

	protected boolean hasPrivateNoArgConstructor() {
		if (this.jrt == null) {
			return false;
		}
		return this.jrt.hasPrivateNoArgConstructor();
	}

	protected boolean hasNoArgConstructor() {
		if (this.jrt == null) {
			return false;
		}
		return this.jrt.hasNoArgConstructor();
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
