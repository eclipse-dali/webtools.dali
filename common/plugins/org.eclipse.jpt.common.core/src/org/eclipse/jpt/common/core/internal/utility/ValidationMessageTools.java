/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * {@link ValidationMessage} utility methods
 */
public final class ValidationMessageTools {

	/**
	 * @see #buildValidationMessage(IResource, TextRange, ValidationMessage, Object[])
	 */
	public static IMessage buildValidationMessage(IResource target, ValidationMessage message) {
		return buildValidationMessage(target, message, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * @see #buildValidationMessage(IResource, TextRange, ValidationMessage, Object[])
	 */
	public static IMessage buildValidationMessage(IResource target, ValidationMessage message, Object... args) {
		return buildValidationMessage(target, EmptyTextRange.instance(), message, args);
	}

	/**
	 * @see #buildValidationMessage(IResource, TextRange, ValidationMessage, Object[])
	 */
	public static IMessage buildValidationMessage(IResource target, TextRange textRange, ValidationMessage message) {
		return buildValidationMessage(target, textRange, message, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Build a validation message with the specified target, text range,
	 * message, and arguments to be bound to the message template.
	 * @see ValidationMessage#buildValidationMessage(IResource, TextRange, Object[])
	 */
	public static IMessage buildValidationMessage(IResource target, TextRange textRange, ValidationMessage message, Object... args) {
		return message.buildValidationMessage(target, textRange, args);
	}
}
