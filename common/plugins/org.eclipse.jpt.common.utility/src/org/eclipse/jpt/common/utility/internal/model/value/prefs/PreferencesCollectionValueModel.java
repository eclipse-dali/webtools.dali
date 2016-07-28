/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value.prefs;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.prefs.BackingStoreException;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.model.value.AspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * This adapter wraps a Preferences node and converts its preferences into a
 * CollectionValueModel of PreferencePropertyValueModels. It listens for
 * "preference" changes and converts them into VALUE collection changes.
 */
public class PreferencesCollectionValueModel<P>
	extends AspectAdapter<Preferences, Object>
	implements CollectionValueModel<PreferencePropertyValueModel<P>>
{
	/** Cache the current preferences, stored in models and keyed by name. */
	protected final HashMap<String, PreferencePropertyValueModel<P>> preferenceModels = new HashMap<>();

	/** A listener that listens to the preferences node for added or removed preferences. */
	protected final PreferenceChangeListener preferenceChangeListener;

	/** Adapter to convert a preferences node into a property value model. */
	protected final Adapter<P> adapter;


	// ********** constructors **********

	/**
	 * Construct an adapter for the specified preferences node.
	 */
	public PreferencesCollectionValueModel(Preferences preferences, Adapter<P> adapter) {
		this(PropertyValueModelTools.staticModel(preferences), adapter);
	}

	/**
	 * Construct an adapter for the specified preferences node.
	 */
	public PreferencesCollectionValueModel(PropertyValueModel<? extends Preferences> preferencesModel, Adapter<P> adapter) {
		super(preferencesModel);
		if (adapter == null) {
			throw new NullPointerException();
		}
		this.preferenceChangeListener = this.buildPreferenceChangeListener();
		this.adapter = adapter;
	}


	// ********** initialization **********

	/**
	 * A preferences have changed, notify the listeners.
	 */
	protected PreferenceChangeListener buildPreferenceChangeListener() {
		return new LocalPreferenceChangeListener();
	}

	protected class LocalPreferenceChangeListener
		implements PreferenceChangeListener
	{
		/**
		 * Transform the preference change events into <code>VALUE</code>
		 * collection change events.
		 */
		public void preferenceChange(PreferenceChangeEvent event) {
			PreferencesCollectionValueModel.this.preferenceChanged(event.getKey(), event.getNewValue());
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}


	// ********** CollectionValueModel implementation **********

	/**
	 * Return an iterator on the preference models.
	 */
	public synchronized Iterator<PreferencePropertyValueModel<P>> iterator() {
		return this.preferenceModels.values().iterator();
	}

	public synchronized int size() {
		return this.preferenceModels.size();
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected Object getAspectValue() {
		return this.iterator();
	}

	@Override
	protected Class<? extends EventListener> getListenerClass() {
		return CollectionChangeListener.class;
	}

	@Override
	protected String getListenerAspectName() {
		return VALUES;
	}

    @Override
    public boolean hasListeners() {
		return this.hasAnyCollectionChangeListeners(VALUES);
	}

	@Override
	protected void fireAspectChanged(Object oldValue, Object newValue) {
    	@SuppressWarnings("unchecked") Iterator<PreferencePropertyValueModel<P>> iterator = (Iterator<PreferencePropertyValueModel<P>>) newValue;
		this.fireCollectionChanged(VALUES, CollectionTools.hashBag(iterator));
	}

    @Override
	protected void engageSubject_() {
		this.subject.addPreferenceChangeListener(this.preferenceChangeListener);
		for (PreferencePropertyValueModel<P> preferenceModel : this.getPreferenceModels()) {
			this.preferenceModels.put(preferenceModel.getKey(), preferenceModel);
		}
	}

    @Override
	protected void disengageSubject_() {
		try {
			this.subject.removePreferenceChangeListener(this.preferenceChangeListener);
		} catch (IllegalStateException ex) {
			// for some odd reason, we are not allowed to remove a listener from a "dead"
			// preferences node; so handle the exception that gets thrown here
			if ( ! ex.getMessage().equals("Node has been removed.")) { //$NON-NLS-1$
				// if it is not the expected exception, re-throw it
				throw ex;
			}
		}
		this.preferenceModels.clear();
	}


	// ********** AbstractModel implementation **********

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.subject);
	}


	// ********** internal methods **********

	/**
	 * Return the preference models.
	 * At this point we can be sure that the subject is not <code>null</code>.
	 */
	protected Iterable<PreferencePropertyValueModel<P>> getPreferenceModels() {
		return new TransformationIterable<>(this.getPreferenceKeys(), new PreferenceKeyTransformer());
	}

	protected Iterable<String> getPreferenceKeys() {
		return IterableTools.iterable(this.getPreferenceKeys_());
	}

	protected String[] getPreferenceKeys_() {
		try {
			return this.subject.keys();
		} catch (BackingStoreException ex) {
			throw new RuntimeException(ex);
		}
	}

	protected class PreferenceKeyTransformer
		extends TransformerAdapter<String, PreferencePropertyValueModel<P>>
	{
		@Override
		public PreferencePropertyValueModel<P> transform(String key) {
			return PreferencesCollectionValueModel.this.buildPreferenceModel(key);
		}
	}

	/**
	 * Override this method to tweak the model used to wrap the
	 * specified preference (e.g. to customize the model's converter).
	 */
	protected PreferencePropertyValueModel<P> buildPreferenceModel(String key) {
		return this.adapter.buildPreferenceModel(this.subjectModel, key);
//		return new PreferencePropertyValueModel<P>(this.subjectModel, key, null, Transformer.Null.<P>instance());
	}

	protected synchronized void preferenceChanged(String key, String newValue) {
		if (newValue == null) {
			// a preference was removed
			PreferencePropertyValueModel<P> preferenceModel = this.preferenceModels.remove(key);
			this.fireItemRemoved(VALUES, preferenceModel);
		} else if ( ! this.preferenceModels.containsKey(key)) {
			// a preference was added
			PreferencePropertyValueModel<P> preferenceModel = this.buildPreferenceModel(key);
			this.preferenceModels.put(key, preferenceModel);
			this.fireItemAdded(VALUES, preferenceModel);
		} else {
			// a preference's value changed - do nothing
		}
	}

	public interface Adapter<P> {
		PreferencePropertyValueModel<P> buildPreferenceModel(PropertyValueModel<? extends Preferences> preferencesModel, String key);
	}
}
