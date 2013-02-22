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
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

//AbstractTypeMappingTypeValidator might be a better name
public abstract class AbstractTypeMappingValidator<M extends TypeMapping>
	implements JptValidator
{
	protected final M typeMapping;


	protected AbstractTypeMappingValidator(M typeMapping) {
		super();
		if (typeMapping.getJavaResourceType() == null) {
			throw new NullPointerException();
		}
		this.typeMapping = typeMapping;
	}


	public boolean validate(List<IMessage> messages, IReporter reporter) {
		this.validateType(messages);
		return true;
	}

	protected abstract void validateType(List<IMessage> messages);

	/**
	 * This method should never return <code>null</code>;
	 */
	protected JavaResourceType getJavaResourceType() {
		return this.typeMapping.getJavaResourceType();
	}

	protected IMessage buildTypeMessage(ValidationMessage msg) {
		return ValidationMessageTools.buildValidationMessage(
				this.typeMapping.getResource(),
				this.typeMapping.getValidationTextRange(),
				msg,
				this.typeMapping.getName()
			);
	}
}
