/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.validation;

import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.core.Message;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class DefaultJpaValidationMessages
	implements JpaValidationMessages
{	
	private static String[] DEFAULT_PARMS = new String[0];
	
	private static TextRange DEFAULT_TEXT_RANGE = TextRange.Empty.instance();
	
	public static IMessage buildMessage(
			int severity, String messageId, Object targetObject) {
		return buildMessage(severity, messageId, DEFAULT_PARMS, targetObject);
	}
	
	public static IMessage buildMessage(
			int severity, String messageId, String[] parms, Object targetObject) {
		return buildMessage(severity, messageId, parms, targetObject, DEFAULT_TEXT_RANGE);
	}
	
	public static IMessage buildMessage(
			int severity, String messageId, Object targetObject, TextRange textRange) {
		return buildMessage(severity, messageId, DEFAULT_PARMS, targetObject, textRange);
	}
	
	public static IMessage buildMessage(
			int severity, String messageId, String[] parms, Object targetObject, TextRange textRange) {
		IMessage message = new Message(BUNDLE, severity, messageId, parms, targetObject);
		if (textRange == null) {
			//log an exception and then continue without setting location information
			//At least the user will still get the validation message and will
			//be able to see other validation messages with valid textRanges
			JptCorePlugin.log(new IllegalArgumentException("The textRange is null for messageId: " + messageId));
		}
		else {
			message.setLineNo(textRange.getLineNumber());
			message.setOffset(textRange.getOffset());
			message.setLength(textRange.getLength());
		}
		return message;
	}
	
	
	private DefaultJpaValidationMessages() {
		super();
		throw new UnsupportedOperationException();
	}

}
