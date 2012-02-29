/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.validation;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.prefs.JpaValidationPreferencesManager;
import org.eclipse.wst.validation.internal.core.Message;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class DefaultJpaValidationMessages {

	private static String[] DEFAULT_PARMS = new String[0];
	private static TextRange DEFAULT_TEXT_RANGE = TextRange.Empty.instance();

	public static IMessage buildMessage(int defaultSeverity, String messageId, JpaNode targetObject) {
		return buildMessage(defaultSeverity, messageId, targetObject.getResource());
	}

	public static IMessage buildMessage(int defaultSeverity, String messageId, IResource targetObject) {
		return buildMessage(defaultSeverity, messageId, DEFAULT_PARMS, targetObject);
	}

	public static IMessage buildMessage(int defaultSeverity, String messageId, String[] parms, JpaNode targetObject) {
		return buildMessage(defaultSeverity, messageId, parms, targetObject.getResource());
	}

	public static IMessage buildMessage(int defaultSeverity, String messageId, String[] parms, IResource targetObject) {
		return buildMessage(defaultSeverity, messageId, parms, targetObject, DEFAULT_TEXT_RANGE);
	}

	public static IMessage buildMessage(int defaultSeverity, String messageId, JpaNode targetObject, TextRange textRange) {
		return buildMessage(defaultSeverity, messageId, targetObject.getResource(), textRange);
	}

	public static IMessage buildMessage(int defaultSeverity, String messageId, IResource targetObject, TextRange textRange) {
		return buildMessage(defaultSeverity, messageId, DEFAULT_PARMS, targetObject, textRange);
	}

	public static IMessage buildMessage(int defaultSeverity, String messageId, String[] parms, JpaNode targetObject, TextRange textRange) {
		return buildMessage(defaultSeverity, messageId, parms, targetObject.getResource(), textRange);
	}

	public static IMessage buildMessage(int defaultSeverity, String messageId, String[] parms, IResource targetObject, TextRange textRange) {
		return buildMessage(defaultSeverity, messageId, parms, targetObject, textRange, MESSAGE_FACTORY);
	}

	public static IMessage buildMessage(int defaultSeverity, String messageId, String[] parms, IResource targetObject, TextRange textRange, MessageFactory messageFactory) {
		IMessage message = messageFactory.buildMessage(defaultSeverity, messageId, parms, targetObject);
		if (textRange == null) {
			textRange = DEFAULT_TEXT_RANGE;
			// log the exception but allow the message to still be used
			JptJpaCorePlugin.log(new NullPointerException("Null text range for message ID: " + messageId)); //$NON-NLS-1$
		}
		int lineNumber = textRange.getLineNumber();
		message.setLineNo(lineNumber);
		if (lineNumber == IMessage.LINENO_UNSET) {
			message.setAttribute(IMarker.LOCATION, " "); //$NON-NLS-1$
		}
		message.setOffset(textRange.getOffset());
		message.setLength(textRange.getLength());
		return message;
	}


	private DefaultJpaValidationMessages() {
		super();
		throw new UnsupportedOperationException();
	}


	// ********** message factory **********

	/**
	 * Allow clients to specify a message factory so the message can load the
	 * appropriate resource bundle.
	 */
	public interface MessageFactory {
		IMessage buildMessage(int severity, String messageId, String[] parms, IResource targetObject);
	}

	private static final MessageFactory MESSAGE_FACTORY = new GenericMessageFactory();

	/* CU private */ static class GenericMessageFactory
		implements MessageFactory
	{
		public IMessage buildMessage(int severity, String messageId, String[] parms, IResource targetObject) {
			// check for preference override
			int prefSeverity = JpaValidationPreferencesManager.getProblemSeverityPreference(targetObject, messageId);
			if (prefSeverity != JpaValidationPreferencesManager.NO_SEVERITY_PREFERENCE){
				severity = prefSeverity;
			}
			IMessage message = new Message(JpaValidationMessages.BUNDLE_NAME, severity, messageId, parms, targetObject);
			message.setMarkerId(JptJpaCorePlugin.VALIDATION_MARKER_ID);
			return message;
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}
}
