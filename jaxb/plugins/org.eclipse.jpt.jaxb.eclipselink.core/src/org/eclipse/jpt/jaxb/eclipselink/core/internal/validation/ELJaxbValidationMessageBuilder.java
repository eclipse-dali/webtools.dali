/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.validation;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.core.JaxbNode;
import org.eclipse.wst.validation.internal.core.Message;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidator;

public class ELJaxbValidationMessageBuilder {

	private static String[] DEFAULT_PARMS = new String[0];
	private static TextRange DEFAULT_TEXT_RANGE = TextRange.Empty.instance();

	public static IMessage buildMessage(int defaultSeverity, String messageId, JaxbNode targetObject) {
		return buildMessage(defaultSeverity, messageId, targetObject.getResource());
	}

	public static IMessage buildMessage(int defaultSeverity, String messageId, IResource targetObject) {
		return buildMessage(defaultSeverity, messageId, DEFAULT_PARMS, targetObject);
	}

	public static IMessage buildMessage(int defaultSeverity, String messageId, String[] parms, JaxbNode targetObject) {
		return buildMessage(defaultSeverity, messageId, parms, targetObject.getResource());
	}

	public static IMessage buildMessage(int defaultSeverity, String messageId, String[] parms, IResource targetObject) {
		return buildMessage(defaultSeverity, messageId, parms, targetObject, DEFAULT_TEXT_RANGE);
	}

	public static IMessage buildMessage(int defaultSeverity, String messageId, JaxbNode targetObject, TextRange textRange) {
		return buildMessage(defaultSeverity, messageId, targetObject.getResource(), textRange);
	}

	public static IMessage buildMessage(int defaultSeverity, String messageId, IResource targetObject, TextRange textRange) {
		return buildMessage(defaultSeverity, messageId, DEFAULT_PARMS, targetObject, textRange);
	}

	public static IMessage buildMessage(int defaultSeverity, String messageId, String[] parms, JaxbNode targetObject, TextRange textRange) {
		return buildMessage(defaultSeverity, messageId, parms, targetObject.getResource(), textRange);
	}

	public static IMessage buildMessage(int defaultSeverity, String messageId, String[] parms, IResource targetObject, TextRange textRange) {
		return DefaultValidationMessages.buildMessage(defaultSeverity, messageId, parms, targetObject, textRange, MESSAGE_FACTORY);
	}


	private ELJaxbValidationMessageBuilder() {
		super();
		throw new UnsupportedOperationException();
	}


	private static final DefaultValidationMessages.MessageFactory MESSAGE_FACTORY = new EclipseLinkMessageFactory();

	/* CU private */ static class EclipseLinkMessageFactory
		implements DefaultValidationMessages.MessageFactory
	{
		public IMessage buildMessage(int severity, String messageId, String[] parms, IResource targetObject) {
			// TODO check for preference override
//			int prefSeverity = JpaValidationPreferences.getProblemSeverityPreference(targetObject, messageId);
//			if (prefSeverity != JpaValidationPreferences.NO_SEVERITY_PREFERENCE){
//				severity = prefSeverity;
//			}
			IMessage message = new EclipseLinkMessage(ELJaxbValidationMessages.BUNDLE_NAME, severity, messageId, parms, targetObject);
			// TODO "Oxm JAXB" validation marker?
			message.setMarkerId(JaxbValidator.MARKER_ID);
			return message;
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
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
