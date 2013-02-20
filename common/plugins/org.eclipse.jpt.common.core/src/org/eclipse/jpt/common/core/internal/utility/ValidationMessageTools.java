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
	 * @see #buildValidationMessage(IResource, TextRange, int, ValidationMessage, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	public static IMessage buildErrorValidationMessage(IResource target, ValidationMessage message) {
		return buildValidationMessage(target, IMessage.HIGH_SEVERITY, message);
	}

	/**
	 * @see #buildValidationMessage(IResource, TextRange, int, ValidationMessage, Object[])
	 */
	public static IMessage buildValidationMessage(IResource target, int defaultSeverity, ValidationMessage message) {
		return buildValidationMessage(target, defaultSeverity, message, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Build a validation message with a default severity of "error".
	 * @see #buildValidationMessage(IResource, TextRange, int, ValidationMessage, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	public static IMessage buildErrorValidationMessage(IResource target, ValidationMessage message, Object... args) {
		return buildValidationMessage(target, IMessage.HIGH_SEVERITY, message, args);
	}

	/**
	 * @see #buildValidationMessage(IResource, TextRange, int, ValidationMessage, Object[])
	 */
	public static IMessage buildValidationMessage(IResource target, int defaultSeverity, ValidationMessage message, Object... args) {
		return buildValidationMessage(target, TextRange.Empty.instance(), defaultSeverity, message, args);
	}

	/**
	 * Build a validation message with a default severity of "error".
	 * @see #buildValidationMessage(IResource, TextRange, int, ValidationMessage, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	public static IMessage buildErrorValidationMessage(IResource target, TextRange textRange, ValidationMessage message) {
		return buildValidationMessage(target, textRange, IMessage.HIGH_SEVERITY, message);
	}

	/**
	 * @see #buildValidationMessage(IResource, TextRange, int, ValidationMessage, Object[])
	 */
	public static IMessage buildValidationMessage(IResource target, TextRange textRange, int defaultSeverity, ValidationMessage message) {
		return buildValidationMessage(target, textRange, defaultSeverity, message, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Build a validation message with a default severity of "error".
	 * @see #buildValidationMessage(IResource, TextRange, int, ValidationMessage, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	public static IMessage buildErrorValidationMessage(IResource target, TextRange textRange, ValidationMessage message, Object... args) {
		return buildValidationMessage(target, textRange, IMessage.HIGH_SEVERITY, message, args);
	}

	/**
	 * Build a validation message with the specified message, default severity
	 * (i.e. the severity of the message in the absence of a user-specified
	 * preference), target, text range, and arguments to be bound to the
	 * message template.
	 * @see IMessage#getSeverity()
	 */
	public static IMessage buildValidationMessage(IResource target, TextRange textRange, int defaultSeverity, ValidationMessage message, Object... args) {
		return message.buildValidationMessage(target, textRange, defaultSeverity, args);
	}
}
