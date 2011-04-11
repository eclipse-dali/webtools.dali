/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.validation;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.eclipselink.core.JptJaxbEclipseLinkCorePlugin;
import org.eclipse.wst.validation.internal.core.Message;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class ELJaxbValidationMessageBuilder {
	
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
		IMessage message = new ELJaxbValidationMessage(ELJaxbValidationMessages.BUNDLE_NAME, severity, messageId, parms, targetObject);
		message.setMarkerId(JptJaxbCorePlugin.VALIDATION_MARKER_ID); //TODO do we need an 'EclipseLink' problem marker?
		if (textRange == null) {
			//log an exception and then continue without setting location information
			//At least the user will still get the validation message and will
			//be able to see other validation messages with valid textRanges
			JptJaxbEclipseLinkCorePlugin.log(new IllegalArgumentException("Null text range for message ID: " + messageId)); //$NON-NLS-1$
		}
		else {
			message.setLineNo(textRange.getLineNumber());
			message.setOffset(textRange.getOffset());
			message.setLength(textRange.getLength());
		}
		return message;
	}
	
	
	private ELJaxbValidationMessageBuilder() {
		super();
		throw new UnsupportedOperationException();
	}

	/**
	 * Used so that we can find the resource bundle using this classLoader.
	 * Otherwise the wst validation message attempts to use the ClassLoader
	 * of the generic jaxb validator which is in the org.eclipse.jpt.jaxb.core plugin.
	 */
	private static class ELJaxbValidationMessage
			extends Message {
		
		public ELJaxbValidationMessage(String aBundleName, int aSeverity, String anId, String[] aParams, Object aTargetObject) {
			super(aBundleName, aSeverity, anId, aParams, aTargetObject);
		}
		
		@Override
		public ResourceBundle getBundle(Locale locale, ClassLoader classLoader) {
			ResourceBundle bundle = null;
			try {
				bundle = ResourceBundle.getBundle(getBundleName(), locale, getClass().getClassLoader());
			} catch (MissingResourceException e) {
				return super.getBundle(locale, classLoader);
			}
			return bundle;
		}
	}
}
