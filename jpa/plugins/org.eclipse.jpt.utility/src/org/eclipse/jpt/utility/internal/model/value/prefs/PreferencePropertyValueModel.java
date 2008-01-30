/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value.prefs;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

import org.eclipse.jpt.utility.internal.BidiStringConverter;
import org.eclipse.jpt.utility.internal.model.listener.ChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.AspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.StaticPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;

/**
 * This adapter wraps a Preference and converts it into a PropertyValueModel.
 * It listens for the appropriate "preference" changes and converts them into
 * VALUE property changes. It also allows the specification of a default value
 * for the Preference, which, by default, is null (and is probably *not* a very
 * good default).
 * 
 * You can configure whether the preference's value is returned,
 * unchanged, as a string or as some other object (e.g. an Integer) by
 * setting the adapter's converter. Internally, the preference's value
 * is stored as the converted object; and the conversions take place
 * when reading or writing from the preferences node or retrieving the
 * value from an event fired by the preferences node.
 * 
 * This adapter is a bit different from most other adapters because the
 * change events fired off by a Preferences node are asynchronous from
 * the change itself. (AbstractPreferences uses an event dispatch daemon.)
 * As a result, a client can set our value with #setValue(Object) and we
 * will return from that method before we ever receive notification from
 * the Preferences node that *it* has changed. This means we cannot
 * rely on that event to keep our internally cached value in synch.
 */
public class PreferencePropertyValueModel<P>
	extends AspectAdapter<Preferences>
	implements WritablePropertyValueModel<P>
{
	/** The key to the preference we use for the value. */
	protected final String key;

	/**
	 * Cache the current (object) value of the preference so we
	 * can pass an "old value" when we fire a property change event.
	 */
	protected P value;

	/**
	 * The default (object) value returned if there is no value
	 * associated with the preference.
	 */
	protected final P defaultValue;

	/**
	 * This converter is used to convert the preference's
	 * string value to and from an object.
	 */
	protected final BidiStringConverter<P> converter;

	/** A listener that listens to the appropriate preference. */
	protected final PreferenceChangeListener preferenceChangeListener;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified preference.
	 * The default value of the preference will be null.
	 */
	public PreferencePropertyValueModel(Preferences preferences, String key) {
		this(preferences, key, null);
	}

	/**
	 * Construct an adapter for the specified preference with
	 * the specified default value for the preference.
	 */
	public PreferencePropertyValueModel(Preferences preferences, String key, P defaultValue) {
		this(preferences, key, defaultValue, BidiStringConverter.Default.<P>instance());
	}

	/**
	 * Construct an adapter for the specified preference with
	 * the specified default value for the preference.
	 */
	public PreferencePropertyValueModel(Preferences preferences, String key, P defaultValue, BidiStringConverter<P> converter) {
		this(new StaticPropertyValueModel<Preferences>(preferences), key, defaultValue, converter);
	}

	/**
	 * Construct an adapter for the specified preference with
	 * the specified default value for the preference.
	 */
	public static PreferencePropertyValueModel<Boolean> forBoolean(Preferences preferences, String key, boolean defaultValue) {
		return new PreferencePropertyValueModel<Boolean>(
				preferences,
				key,
				defaultValue ? Boolean.TRUE : Boolean.FALSE,
				BidiStringConverter.BooleanConverter.instance()
			);
	}

	/**
	 * Construct an adapter for the specified preference with
	 * the specified default value for the preference.
	 */
	public static PreferencePropertyValueModel<Integer> forInteger(Preferences preferences, String key, int defaultValue) {
		return new PreferencePropertyValueModel<Integer>(
				preferences,
				key,
				new Integer(defaultValue),
				BidiStringConverter.IntegerConverter.instance()
			);
	}

	/**
	 * Construct an adapter for the specified preference.
	 * The default value of the preference will be null.
	 */
	public PreferencePropertyValueModel(PropertyValueModel<? extends Preferences> preferencesHolder, String key) {
		this(preferencesHolder, key, null);
	}

	/**
	 * Construct an adapter for the specified preference with
	 * the specified default value for the preference.
	 */
	public PreferencePropertyValueModel(PropertyValueModel<? extends Preferences> preferencesHolder, String key, P defaultValue) {
		this(preferencesHolder, key, defaultValue, BidiStringConverter.Default.<P>instance());
	}

	/**
	 * Construct an adapter for the specified preference with
	 * the specified default value for the preference.
	 */
	public PreferencePropertyValueModel(PropertyValueModel<? extends Preferences> preferencesHolder, String key, P defaultValue, BidiStringConverter<P> converter) {
		super(preferencesHolder);
		this.key = key;
		this.defaultValue = defaultValue;
		this.converter = converter;
		this.preferenceChangeListener = this.buildPreferenceChangeListener();
		// our value is null when we are not listening to the preference
		this.value = null;
	}


	// ********** initialization **********

	/**
	 * A preference has changed, notify the listeners if necessary.
	 */
	protected PreferenceChangeListener buildPreferenceChangeListener() {
		// transform the preference change events into VALUE property change events
		return new PreferenceChangeListener() {
			public void preferenceChange(PreferenceChangeEvent e) {
				PreferencePropertyValueModel.this.preferenceChanged(e.getKey(), e.getNewValue());
			}
			@Override
			public String toString() {
				return "preference change listener";
			}
		};
	}


	// ********** ValueModel implementation **********

	/**
	 * Return the cached (converted) value.
	 */
	@Override
	public synchronized P value() {
		return this.value;
	}


	// ********** PropertyValueModel implementation **********

	/**
	 * Set the cached value, then set the appropriate preference value.
	 */
	public synchronized void setValue(P value) {
		if (this.hasNoListeners()) {
			return;		// no changes allowed when we have no listeners
		}

		Object old = this.value;
		this.value = value;
		this.fireAspectChange(old, value);

		if ((this.subject != null) && this.shouldSetPreference(old, value)) {
			this.setValue_(value);
		}
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected Class<? extends ChangeListener> listenerClass() {
		return PropertyChangeListener.class;
	}

	@Override
	protected String listenerAspectName() {
		return VALUE;
	}

	@Override
	protected boolean hasListeners() {
		return this.hasAnyPropertyChangeListeners(VALUE);
	}

	@Override
	protected void fireAspectChange(Object oldValue, Object newValue) {
		this.firePropertyChanged(VALUE, oldValue, newValue);
	}

	@Override
	protected void engageSubject_() {
		this.subject.addPreferenceChangeListener(this.preferenceChangeListener);
		this.value = this.buildValue();
	}

	@Override
	protected void disengageSubject_() {
		try {
			this.subject.removePreferenceChangeListener(this.preferenceChangeListener);
		} catch (IllegalStateException ex) {
			// for some odd reason, we are not allowed to remove a listener from a "dead"
			// preferences node; so handle the exception that gets thrown here
			if ( ! ex.getMessage().equals("Node has been removed.")) {
				// if it is not the expected exception, re-throw it
				throw ex;
			}
		}
		this.value = null;
	}


	// ********** AbstractModel implementation **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.key);
		sb.append(" => ");
		sb.append(this.value);
	}


	// ********** public API **********

	/**
	 * Return the preference's key.
	 */
	public String key() {
		return this.key;
	}


	// ********** internal methods **********

	/**
	 * Return the preference's value.
	 * At this point the subject may be null.
	 */
	protected P buildValue() {
		return (this.subject == null) ? null : this.buildValue_();
	}

	/**
	 * Return the appropriate preference, converted to the appropriate object.
	 * At this point we can be sure that the subject is not null.
	 */
	protected P buildValue_() {
		return this.convertToObject(this.subject.get(this.key, this.convertToString(this.defaultValue)));
	}

	/**
	 * Set the appropriate preference after converting the value to a string.
	 * At this point we can be sure that the subject is not null.
	 */
	protected void setValue_(P value) {
		this.subject.put(this.key, this.convertToString(value));
	}

	/**
	 * Return whether the specified new value should be passed
	 * through to the preference. By default, only if the value has changed,
	 * will it be passed through to the preference. This also has the
	 * effect of not creating new preferences in the "backing store"
	 * if the new value is the same as the default value.
	 * 
	 * Subclasses can override this method to return true if they
	 * would like to ALWAYS pass through the new value to the preference.
	 */
	protected boolean shouldSetPreference(Object oldValue, Object newValue) {
		return this.attributeValueHasChanged(oldValue, newValue);
	}

	/**
	 * Convert the specified object to a string that can be stored as
	 * the value of the preference.
	 */
	protected String convertToString(P o) {
		return this.converter.convertToString(o);
	}

	/**
	 * Convert the specified preference value string to an
	 * appropriately-typed object to be returned to the client.
	 */
	protected P convertToObject(String s) {
		return this.converter.convertToObject(s);
	}

	protected void preferenceChanged(String prefKey, String newValue) {
		if (prefKey.equals(this.key)) {
			this.preferenceChanged();
		}
	}

	/**
	 * The underlying preference changed; either because we changed it
	 * in #setValue_(Object) or a third-party changed it.
	 * If this is called because of our own change, the event will be
	 * swallowed because the old and new values are the same.
	 */
	protected synchronized void preferenceChanged() {
		Object old = this.value;
		this.value = this.buildValue();
		this.fireAspectChange(old, this.value);
	}

}
