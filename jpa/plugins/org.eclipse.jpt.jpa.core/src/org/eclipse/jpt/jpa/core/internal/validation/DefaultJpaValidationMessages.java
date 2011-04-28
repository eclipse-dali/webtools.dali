/*******************************************************************************
 * Copyright (c) 2005, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.validation;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.wst.validation.internal.core.Message;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class DefaultJpaValidationMessages {

	private static String[] DEFAULT_PARMS = new String[0];
	private static TextRange DEFAULT_TEXT_RANGE = TextRange.Empty.instance();
	
	public static IMessage buildMessage(
			int defaultSeverity, String messageId, Object targetObject) {
		return buildMessage(defaultSeverity, messageId, DEFAULT_PARMS, targetObject);
	}
	
	public static IMessage buildMessage(
			int defaultSeverity, String messageId, String[] parms, Object targetObject) {
		return buildMessage(defaultSeverity, messageId, parms, targetObject, DEFAULT_TEXT_RANGE);
	}
	
	public static IMessage buildMessage(
			int defaultSeverity, String messageId, Object targetObject, TextRange textRange) {
		return buildMessage(defaultSeverity, messageId, DEFAULT_PARMS, targetObject, textRange);
	}
	
	public static IMessage buildMessage(
			int defaultSeverity, String messageId, String[] parms, Object targetObject, TextRange textRange) {
		
		//determine whether default severity should be overridden
		int severity = defaultSeverity;
		int severityPreference = JpaValidationPreferences.getProblemSeverityPreference(targetObject, messageId);
		if (severityPreference!=JpaValidationPreferences.NO_SEVERITY_PREFERENCE){
			severity = severityPreference;
		}
		IMessage message = new Message(JpaValidationMessages.BUNDLE_NAME, severity, messageId, parms, targetObject);
		message.setMarkerId(JptJpaCorePlugin.VALIDATION_MARKER_ID);
		if (textRange == null) {
			//log an exception and then continue without setting location information
			//At least the user will still get the validation message and will
			//be able to see other validation messages with valid textRanges
			JptJpaCorePlugin.log(new NullPointerException("Null text range for message ID: " + messageId)); //$NON-NLS-1$
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
