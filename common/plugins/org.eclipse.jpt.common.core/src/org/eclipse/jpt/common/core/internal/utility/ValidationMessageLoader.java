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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.BitTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.SimpleAssociation;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * @see org.eclipse.osgi.util.NLS
 */
public class ValidationMessageLoader {

	/**
	 * Adapter passed to {@link #load(Class, String, String, String, PreferencesAdapter)}
	 * and forwared to the validation messages, allowing each message to
	 * retrieve its user-specified severity.
	 */
	public interface PreferencesAdapter {
		/**
		 * Return the specified message's user-specified severity. If there is
		 * no user-specified preference for the message's severity, return the
		 * specified default severity. Return <code>-1</code> if the
		 * user-specified severity is "ignore".
		 * @see IMessage#getSeverity()
		 */
		int getSeverity(IProject project, String messageID, int defaultSeverity);
	}

	/**
	 * Load the the specified class's validation message fields from the
	 * specified message and description bundles. Pass the specified marker type
	 * and preferences adapter to each validation message.
	 * @see org.eclipse.core.resources.IMarker#getType()
	 */
	public static void load(Class<?> clazz, String messageBundleName, String descriptionBundleName, String markerType, PreferencesAdapter preferencesAdapter) {
		if (System.getSecurityManager() == null) {
			load_(clazz, messageBundleName, descriptionBundleName, markerType, preferencesAdapter);
		} else {
			AccessController.doPrivileged(new LoadAction(clazz, messageBundleName, descriptionBundleName, markerType, preferencesAdapter));
		}
	}

	/**
	 * Privileged action for loading the messages when the JVM has a
	 * {@link System#getSecurityManager() security manager}.
	 */
	private static class LoadAction
		implements PrivilegedAction<Object>
	{
		private final Class<?> clazz;
		private final String messageBundleName;
		private final String descriptionBundleName;
		private final String markerType;
		private final PreferencesAdapter preferencesAdapter;

		LoadAction(Class<?> clazz, String messageBundleName, String descriptionBundleName, String markerType, PreferencesAdapter preferencesAdapter) {
			super();
			this.clazz = clazz;
			this.messageBundleName = messageBundleName;
			this.markerType = markerType;
			this.preferencesAdapter = preferencesAdapter;
			this.descriptionBundleName = descriptionBundleName;
		}

		public Object run() {
			load_(this.clazz, this.messageBundleName, this.descriptionBundleName, this.markerType, this.preferencesAdapter);
			return null;
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.clazz.getSimpleName());
		}
	}

	/* CU private */ static void load_(Class<?> clazz, String messageBundleName, String descriptionBundleName, String markerType, PreferencesAdapter preferencesAdapter) {
		// Gather up all the relevant fields and build a FieldMessage for each.
		Field[] fields = clazz.getDeclaredFields();
		boolean classIsNotPublic = BitTools.flagIsOff(clazz.getModifiers(), Modifier.PUBLIC);
		HashMap<String, FieldMessage> messages = new HashMap<String, FieldMessage>(BitTools.twice(fields.length));
		for (Field field : fields) {
			// fields must be 'public static' but not 'final'
			int modifiers = field.getModifiers();
			if (BitTools.allFlagsAreSet(modifiers, PUBLIC_STATIC) && BitTools.flagIsOff(modifiers, Modifier.FINAL)) {
				if (classIsNotPublic) {
					field.setAccessible(true);
				}
				String fieldName = field.getName();
				// use the field name as the message's ID
				messages.put(fieldName, new FieldMessage(field, new SimpleValidationMessage(fieldName, markerType, preferencesAdapter)));
			}
		}

		ArrayList<SimpleAssociation<String, MessagesProperties.Adapter>> pairs = new ArrayList<SimpleAssociation<String, MessagesProperties.Adapter>>(2);
		pairs.add(new SimpleAssociation<String, MessagesProperties.Adapter>(messageBundleName, MESSAGE_TEMPLATE_ADAPTER));
		pairs.add(new SimpleAssociation<String, MessagesProperties.Adapter>(descriptionBundleName, MESSAGE_DESCRIPTION_ADAPTER));

		// Populate the FieldMessages' templates/descriptions from the existing properties files.
		// Read the properties files from most-specific to most-general
		// (i.e. the first message found wins, subsequent messages are ignored).
		ClassLoader classLoader = clazz.getClassLoader();
		for (SimpleAssociation<String, MessagesProperties.Adapter> pair : pairs) {
			String bundleName = pair.getKey();
			MessagesProperties.Adapter adapter = pair.getValue();
			for (String propertiesFileName : buildPropertiesFileNames(bundleName)) {
				// class loader will be null if it is the Java bootstrap class loader
				InputStream stream = (classLoader == null) ?
						ClassLoader.getSystemResourceAsStream(propertiesFileName) :
						classLoader.getResourceAsStream(propertiesFileName);
				if (stream != null) {
					// pass the same messages map to each MessagesProperties
					MessagesProperties properties = new MessagesProperties(messages, propertiesFileName, adapter);
					try {
						properties.load(stream);
					} catch (IOException ex) {
						logError(ex, "Error loading {0}", propertiesFileName); //$NON-NLS-1$
					} finally {
						try {
							stream.close();
						} catch (IOException ex) {
							// ignore
						}
					}
				}
			}
		}

		// Set the values of the fields.
		for (Map.Entry<String, FieldMessage> entry : messages.entrySet()) {
			FieldMessage fieldMessage = entry.getValue();
			Field field = fieldMessage.field;
			SimpleValidationMessage message = fieldMessage.message;
			String template = message.template;
			if (template == null) {
				template = bind("NLS missing message in bundle {0}: {1} ({2})", messageBundleName, entry.getKey(), clazz.getName()); //$NON-NLS-1$
				message.template = template;
				logWarning(template);
			}
			String description = message.description;
			if (description == null) {
				description = bind("NLS missing description in bundle {0}: {1} ({2})", descriptionBundleName, entry.getKey(), clazz.getName()); //$NON-NLS-1$
				message.description = description;
				logWarning(description);
			}
			try {
				field.set(null, message);
			} catch (Exception ex) {
				// field's value will be null - not much we can do :-(
				logError(ex, "Error setting the message value for: {0}", field); //$NON-NLS-1$
			}
		}
	}
	private static final int PUBLIC_STATIC = Modifier.PUBLIC | Modifier.STATIC;

	/**
	 * Return an array of names of properties files to search.
	 * The returned array contains the names of
	 * the properties files in the order most-specific to most-general.
	 * <p>
	 * For example, if the {@link Locale#getDefault() default local}
	 * is <code>en_US_WIN</code> and the bundle name is
	 * <code>"foo"</code>:<code><ol>
	 * <li>"foo_en_US_WIN.properties"
	 * <li>"foo_en_US.properties"
	 * <li>"foo_en.properties"
	 * <li>"foo.properties"
	 * </ol></code>
	 */
	private static String[] buildPropertiesFileNames(String bundleName) {
		bundleName = bundleName.replace('.', '/');
		int len = LOCALE_SUFFIXES.length;
		String[] names = new String[len];
		for (int i = len; i-- > 0;) {
			names[i] = bundleName + LOCALE_SUFFIXES[i];
		}
		return names;
	}

	/**
	 * The list of suffixes (as derived from the current default locale)
	 * for loading resource bundles, in the order most-specific to
	 * most-general:<ol>
	 * <li>variant (e.g. <code>"_en_US_WIN.properties"</code>)
	 * <li>country (e.g. <code>"_en_US.properties"</code>)
	 * <li>language (e.g. <code>"_en.properties"</code>)
	 * <li>default (e.g. <code>".properties"</code>)
	 * </ol>
	 */
	private static /* final */ String[] LOCALE_SUFFIXES = buildLocaleSuffixes();

	private static String[] buildLocaleSuffixes() {
		return buildLocaleSuffixes(Locale.getDefault());
	}

	/**
	 * Possible {@link Locale} variations:<code><ul>
	 * <li>"en_US_WIN"
	 * <li>"en_US"
	 * <li>"en__WIN"
	 * <li>"en"
	 * <li>"_US_WIN"
	 * <li>"_US"
	 * <li>"__WIN"
	 * <li>""
	 * </ul></code>
	 * @see java.util.ResourceBundle#getBundle(String, Locale, ClassLoader)
	 */
	private static String[] buildLocaleSuffixes(Locale locale) {
		String language = locale.getLanguage();
		int languageLength = language.length();
		String country = locale.getCountry();
		int countryLength = country.length();
		String variant = locale.getVariant();
		int variantLength = variant.length();

		// build the array most-specific to most-general
		String[] suffixes = new String[MAX_SUFFIXES];
		int i = 0;
		StringBuilder sb = new StringBuilder(languageLength + countryLength + variantLength + FIXED_MAX_SUFFIX_PORTION_LENGTH);
		sb.append('_').append(language).append('_').append(country).append('_').append(variant).append(_PROPERTIES_FILE_EXTENSION);
		int start = languageLength + countryLength + 2;  // 2 underscores
		if (variantLength != 0) {
			suffixes[i++] = sb.toString();
			sb.delete(start, start + variantLength + 1);  // 1 underscore
		} else {
			sb.deleteCharAt(start);
		}
		start = languageLength + 1;  // 1 underscore
		if (countryLength != 0) {
			suffixes[i++] = sb.toString();
			sb.delete(start, start + countryLength + 1);  // 1 underscore
		} else {
			sb.deleteCharAt(start);
		}
		if (languageLength != 0) {
			suffixes[i++] = sb.toString();
		}
		suffixes[i++] = _PROPERTIES_FILE_EXTENSION;  // the empty (most-general) suffix
		// strip off trailing nulls
		return ArrayTools.subArray(suffixes, 0, i);
	}
	private static final int MAX_SUFFIXES = 4;  // language, country, variant, default
	private static final String PROPERTIES_FILE_EXTENSION = "properties"; //$NON-NLS-1$
	private static final String _PROPERTIES_FILE_EXTENSION = '.' + PROPERTIES_FILE_EXTENSION;
	private static final int FIXED_MAX_SUFFIX_PORTION_LENGTH = _PROPERTIES_FILE_EXTENSION.length() + 3;  // max 3 underscores (e.g. "_en_US_WIN.properties")


	/**
	 * This not really a {@link Properties} class; we are extending
	 * {@link Properties} to take advantage of its abilities to
	 * {@link Properties#load(InputStream) load a Java properties file}.
	 * We store the properties in the field messages collecting parameter
	 * passed into the constructor.
	 */
	private static class MessagesProperties
		extends Properties
	{
		private final Map<String, FieldMessage> fieldMessages;
		private final String propertiesFileName;
		private final Adapter adapter;

		private static final long serialVersionUID = 1L;

		MessagesProperties(Map<String, FieldMessage> fieldMessages, String propertiesFileName, Adapter adapter) {
			super();
			this.fieldMessages = fieldMessages;
			this.propertiesFileName = propertiesFileName;
			this.adapter = adapter;
		}

		/**
		 * For the properties file entry:<pre>
		 *   FOO=Foo message: {0}
		 * </pre>
		 * The key will be <code>"FOO"</code>;
		 * and the value will be <code>"Foo message: {0}"</code>
		 * <p>
		 * If the class has a public, static, non-final field named
		 * FOO, store the value/message so the field's value can be set to it later.
		 * If there is no such field, log a warning message.
		 * If the value was set previously, leave it in place
		 * (i.e. first message encountered wins).
		 */
		@Override
		public synchronized Object put(Object key, Object value) {
			FieldMessage fieldMessage = this.fieldMessages.get(key);
			if (fieldMessage != null) {
				this.adapter.put(fieldMessage.message, value);
			} else {
				// there is no field with the name listed in the properties file
				logWarning("NLS unused message in bundle file {0}: {1}", this.propertiesFileName, key); //$NON-NLS-1$
			}
			return null;
		}

		interface Adapter {
			void put(SimpleValidationMessage message, Object value);
		}
	}

	private static final MessagesProperties.Adapter MESSAGE_TEMPLATE_ADAPTER = new MessageTemplateAdapter();
	/* CU private */ static class MessageTemplateAdapter
		implements MessagesProperties.Adapter
	{
		public void put(SimpleValidationMessage message, Object value) {
			if (message.template == null) {
				// force new backing char[] (bug 287183)
				message.template = new String(value.toString().toCharArray());
			}
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}

	private static final MessagesProperties.Adapter MESSAGE_DESCRIPTION_ADAPTER = new MessageDescriptionAdapter();
	/* CU private */ static class MessageDescriptionAdapter
		implements MessagesProperties.Adapter
	{
		public void put(SimpleValidationMessage message, Object value) {
			if (message.description == null) {
				// force new backing char[] (bug 287183)
				message.description = new String(value.toString().toCharArray());
			}
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}


	/**
	 * Simple state object.
	 */
	private static class FieldMessage {
		final Field field;
		final SimpleValidationMessage message;

		FieldMessage(Field field, SimpleValidationMessage message) {
			super();
			this.field = field;
			this.message = message;
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.field.getName());
		}
	}


	/**
	 * Straightforward implementation of the validation message interface.
	 */
	private static class SimpleValidationMessage
		implements ValidationMessage
	{
		private final String id;
		private final String markerType;
		private final PreferencesAdapter preferencesAdapter;
		/* final */ String template;
		/* final */ String description;
		private int defaultSeverity = IMessage.HIGH_SEVERITY;


		SimpleValidationMessage(String id, String markerType, PreferencesAdapter preferencesAdapter) {
			super();
			this.id = id;
			this.markerType = markerType;
			this.preferencesAdapter = preferencesAdapter;
		}

		public String getID() {
			return this.id;
		}

		public String getDescription() {
			return this.description;
		}

		public int getDefaultSeverity() {
			return this.defaultSeverity;
		}

		public void setDefaultSeverity(int severity) {
			this.defaultSeverity = severity;
		}

		public IMessage buildValidationMessage(IResource target, TextRange textRange, Object... args) {
			int severity = this.preferencesAdapter.getSeverity(target.getProject(), this.id, this.defaultSeverity);
			String localizedMessage = this.bind(args);

			IMessage message = new LocalizedValidationMessage(severity, this.id, target, localizedMessage);

			message.setMarkerId(this.markerType);

			if (textRange == null) {
				textRange = EmptyTextRange.instance();
				// log the exception but allow the message to still be used
				logError(new NullPointerException(), "Null text range for message ID: {0}", this.id); //$NON-NLS-1$
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

		private String bind(Object... args) {
			return NLS.bind(this.template, args);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.id);
		}
	}


	private static String bind(String message, Object... args) {
		return NLS.bind(message, args);
	}

	/* CU private */ static void logWarning(String message, Object... args) {
		JptCommonCorePlugin.instance().log(IStatus.WARNING, message, args);
	}

	/* CU private */ static void logError(Throwable throwable, String message, Object... args) {
		JptCommonCorePlugin.instance().logError(throwable, message, args);
	}
}
