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
	 * Build a validation message with a default severity of "error".
	 * @see #buildValidationMessage(ValidationMessage, int, IResource, TextRange, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	public static IMessage buildErrorValidationMessage(ValidationMessage message, IResource target) {
		return buildValidationMessage(message, IMessage.HIGH_SEVERITY, target);
	}

	/**
	 * @see #buildValidationMessage(ValidationMessage, int, IResource, TextRange, Object[])
	 */
	public static IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, IResource target) {
		return buildValidationMessage(message, defaultSeverity, target, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Build a validation message with a default severity of "error".
	 * @see #buildValidationMessage(ValidationMessage, int, IResource, TextRange, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	public static IMessage buildErrorValidationMessage(ValidationMessage message, IResource target, Object... args) {
		return buildValidationMessage(message, IMessage.HIGH_SEVERITY, target, args);
	}

	/**
	 * @see #buildValidationMessage(ValidationMessage, int, IResource, TextRange, Object[])
	 */
	public static IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, IResource target, Object... args) {
		return buildValidationMessage(message, defaultSeverity, target, TextRange.Empty.instance(), args);
	}

	/**
	 * Build a validation message with a default severity of "error".
	 * @see #buildValidationMessage(ValidationMessage, int, IResource, TextRange, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	public static IMessage buildErrorValidationMessage(ValidationMessage message, IResource target, TextRange textRange) {
		return buildValidationMessage(message, IMessage.HIGH_SEVERITY, target, textRange);
	}

	/**
	 * @see #buildValidationMessage(ValidationMessage, int, IResource, TextRange, Object[])
	 */
	public static IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, IResource target, TextRange textRange) {
		return buildValidationMessage(message, defaultSeverity, target, textRange, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Build a validation message with a default severity of "error".
	 * @see #buildValidationMessage(ValidationMessage, int, IResource, TextRange, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	public static IMessage buildErrorValidationMessage(ValidationMessage message, IResource target, TextRange textRange, Object... args) {
		return buildValidationMessage(message, IMessage.HIGH_SEVERITY, target, textRange, args);
	}

	/**
	 * Build a validation message with the specified message, default severity
	 * (i.e. the severity of the message in the absence of a user-specified
	 * preference), target, text range, and arguments to be bound to the
	 * message template.
	 * @see IMessage#getSeverity()
	 */
	public static IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, IResource target, TextRange textRange, Object... args) {
		return message.buildValidationMessage(defaultSeverity, target, textRange, args);
	}
}
