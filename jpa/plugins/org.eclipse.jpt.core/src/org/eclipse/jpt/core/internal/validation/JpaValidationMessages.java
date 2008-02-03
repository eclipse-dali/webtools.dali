/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.validation;

import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.wst.validation.internal.core.Message;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class JpaValidationMessages
	implements IJpaValidationMessages
{	
	private static String[] DEFAULT_PARMS = new String[0];
	
	private static ITextRange DEFAULT_TEXT_RANGE = ITextRange.Empty.instance();
	
	public static IMessage buildMessage(
			int severity, String messageId, Object targetObject) {
		return buildMessage(severity, messageId, DEFAULT_PARMS, targetObject);
	}
	
	public static IMessage buildMessage(
			int severity, String messageId, String[] parms, Object targetObject) {
		return buildMessage(severity, messageId, parms, targetObject, DEFAULT_TEXT_RANGE);
	}
	
	public static IMessage buildMessage(
			int severity, String messageId, Object targetObject, ITextRange textRange) {
		return buildMessage(severity, messageId, DEFAULT_PARMS, targetObject, textRange);
	}
	
	public static IMessage buildMessage(
			int severity, String messageId, String[] parms, Object targetObject, ITextRange textRange) {
		IMessage message = new Message(BUNDLE, severity, messageId, parms, targetObject);
		message.setLineNo(textRange.getLineNumber());
		message.setOffset(textRange.getOffset());
		message.setLength(textRange.getLength());
		return message;
	}
	
	
	private JpaValidationMessages() {
		super();
		throw new UnsupportedOperationException();
	}

}
