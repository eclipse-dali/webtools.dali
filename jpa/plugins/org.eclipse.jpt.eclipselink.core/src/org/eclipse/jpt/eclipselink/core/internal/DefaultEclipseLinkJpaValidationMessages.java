/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.core.Message;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class DefaultEclipseLinkJpaValidationMessages {

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
		IMessage message = new EclipseLinkMessage(EclipseLinkJpaValidationMessages.BUNDLE_NAME, severity, messageId, parms, targetObject);
		if (textRange == null) {
			//log an exception and then continue without setting location information
			//At least the user will still get the validation message and will
			//be able to see other validation messages with valid textRanges
			JptCorePlugin.log(new IllegalArgumentException("Null text range for message ID: " + messageId)); //$NON-NLS-1$
		}
		else {
			message.setLineNo(textRange.getLineNumber());
			message.setOffset(textRange.getOffset());
			message.setLength(textRange.getLength());
		}
		return message;
	}
	
	
	private DefaultEclipseLinkJpaValidationMessages() {
		super();
		throw new UnsupportedOperationException();
	}

	/**
	 * Used so that we can find the resource bundle using this classLoader.
	 * Otherwise the wst validation message attempts to use the ClassLoader
	 * of JpaValidator which is in the org.eclipse.jpt.core plugin.
	 * 
	 * Another way we could potentially solve this is to have a separate
	 * EclispeLinkJpaValidator and set up the extension points so that
	 * it only runs against jpaProjects with the EclispeLinkPlatform
	 * while JpaValidator runs against jpaProjects with the GenericPlatform.
	 * I am unsure if this is possible
	 */
	private static class EclipseLinkMessage extends Message {
		public EclipseLinkMessage(String aBundleName, int aSeverity, String anId, String[] aParams, Object aTargetObject) {
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
