/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.JpaNode;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidator;
import org.eclipse.jpt.jpa.eclipselink.core.validation.JptJpaEclipseLinkCoreValidationMessages;
import org.eclipse.wst.validation.internal.core.Message;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class DefaultEclipseLinkJpaValidationMessages {

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
		return DefaultJpaValidationMessages.buildMessage(defaultSeverity, messageId, parms, targetObject, textRange, MESSAGE_FACTORY);
	}


	private DefaultEclipseLinkJpaValidationMessages() {
		super();
		throw new UnsupportedOperationException();
	}


	private static final DefaultJpaValidationMessages.MessageFactory MESSAGE_FACTORY = new EclipseLinkMessageFactory();

	/* CU private */ static class EclipseLinkMessageFactory
		implements DefaultJpaValidationMessages.MessageFactory
	{
		public IMessage buildMessage(int severity, String messageId, String[] parms, IResource targetObject) {
			// TODO check for preference override
//			int prefSeverity = JpaValidationPreferences.getProblemSeverityPreference(targetObject, messageId);
//			if (prefSeverity != JpaValidationPreferences.NO_SEVERITY_PREFERENCE){
//				severity = prefSeverity;
//			}
			IMessage message = new EclipseLinkMessage(JptJpaEclipseLinkCoreValidationMessages.BUNDLE_NAME, severity, messageId, parms, targetObject);
			// TODO "EclipseLink JPA" validation marker?
			message.setMarkerId(JpaValidator.MARKER_ID);
			return message;
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}

	/**
	 * We have to build this message so {@link Message} uses the right class
	 * loader to retrieve the message bundles.
	 * <p>
	 * Another way we could potentially solve this is to have a separate
	 * EclispeLink JPA validator and set up the extension points so that
	 * it only runs against JPA projects with the EclispeLink platform....
	 */
	/* CU private */ static class EclipseLinkMessage
		extends Message
	{
		EclipseLinkMessage(String bundleName, int severity, String id, String[] params, Object target) {
			super(bundleName, severity, id, params, target);
		}

		@Override
		public ResourceBundle getBundle(Locale locale, ClassLoader classLoader) {
			try {
				return super.getBundle(locale, this.getClass().getClassLoader());
			} catch (MissingResourceException ex) {
				return super.getBundle(locale, classLoader);
			}
		}
	}
}
