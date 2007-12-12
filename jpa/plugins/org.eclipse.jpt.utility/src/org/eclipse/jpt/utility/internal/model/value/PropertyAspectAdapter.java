/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;

/**
 * This AspectAdapter provides basic PropertyChange support.
 * This allows us to convert a set of one or more properties into
 * a single property, VALUE.
 * 
 * The typical subclass will override the following methods:
 * #buildValue_()
 *     at the very minimum, override this method to return the value of the
 *     subject's property (or "virtual" property); it does not need to be
 *     overridden if #buildValue() is overridden and its behavior changed
 * #setValue_(Object)
 *     override this method if the client code needs to *set* the value of
 *     the subject's property; oftentimes, though, the client code (e.g. UI)
 *     will need only to *get* the value; it does not need to be
 *     overridden if #setValue(Object) is overridden and its behavior changed
 * #buildValue()
 *     override this method only if returning a null value when the subject is null
 *     is unacceptable
 * #setValue(Object)
 *     override this method only if something must be done when the subject
 *     is null (e.g. throw an exception)
 */
public abstract class PropertyAspectAdapter 
	extends AspectAdapter
	implements WritablePropertyValueModel
{
	/**
	 * Cache the current value of the aspect so we
	 * can pass an "old value" when we fire a property change event.
	 * We need this because the value may be calculated and may
	 * not be in the property change event fired by the subject,
	 * especially when dealing with multiple aspects.
	 */
	protected Object value;

	/** The name of the subject's properties that we use for the value. */
	protected final String[] propertyNames;
		private static final String[] EMPTY_PROPERTY_NAMES = new String[0];

	/** A listener that listens to the appropriate properties of the subject. */
	protected final PropertyChangeListener propertyChangeListener;


	// ********** constructors **********

	/**
	 * Construct a PropertyAspectAdapter for the specified subject
	 * and property.
	 */
	protected PropertyAspectAdapter(String propertyName, Model subject) {
		this(new String[] {propertyName}, subject);
	}

	/**
	 * Construct a PropertyAspectAdapter for the specified subject
	 * and properties.
	 */
	protected PropertyAspectAdapter(String[] propertyNames, Model subject) {
		this(new ReadOnlyPropertyValueModel(subject), propertyNames);
	}

	/**
	 * Construct a PropertyAspectAdapter for the specified subject holder
	 * and properties.
	 */
	protected PropertyAspectAdapter(PropertyValueModel subjectHolder, String... propertyNames) {
		super(subjectHolder);
		this.propertyNames = propertyNames;
		this.propertyChangeListener = this.buildPropertyChangeListener();
		// our value is null when we are not listening to the subject
		this.value = null;
	}

	/**
	 * Construct a PropertyAspectAdapter for the specified subject holder
	 * and properties.
	 */
	protected PropertyAspectAdapter(PropertyValueModel subjectHolder, Collection<String> propertyNames) {
		this(subjectHolder, propertyNames.toArray(new String[propertyNames.size()]));
	}

	/**
	 * Construct a PropertyAspectAdapter for an "unchanging" property in
	 * the specified subject. This is useful for a property aspect that does not
	 * change for a particular subject; but the subject will change, resulting in
	 * a new property.
	 */
	protected PropertyAspectAdapter(PropertyValueModel subjectHolder) {
		this(subjectHolder, EMPTY_PROPERTY_NAMES);
	}


	// ********** initialization **********

	/**
	 * The subject's property has changed, notify the listeners.
	 */
	protected PropertyChangeListener buildPropertyChangeListener() {
		// transform the subject's property change events into VALUE property change events
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				PropertyAspectAdapter.this.propertyChanged();
			}
			@Override
			public String toString() {
				return "property change listener: " + Arrays.asList(PropertyAspectAdapter.this.propertyNames);
			}
		};
	}


	// ********** ValueModel implementation **********

	/**
	 * Return the value of the subject's property.
	 */
	@Override
	public final Object value() {
		return this.value;
	}


	// ********** PropertyValueModel implementation **********

	/**
	 * Set the value of the subject's property.
	 */
	public void setValue(Object value) {
		if (this.subject != null) {
			this.setValue_(value);
		}
	}

	/**
	 * Set the value of the subject's property.
	 * At this point we can be sure that the subject is not null.
	 * @see #setValue(Object)
	 */
	protected void setValue_(Object value) {
		throw new UnsupportedOperationException();
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
	protected void engageSubject() {
		super.engageSubject();
		// synch our value *after* we start listening to the subject,
		// since its value might change when a listener is added
		this.value = this.buildValue();
	}

    @Override
	protected void engageNonNullSubject() {
		for (int i = this.propertyNames.length; i-- > 0; ) {
			((Model) this.subject).addPropertyChangeListener(this.propertyNames[i], this.propertyChangeListener);
		}
	}

    @Override
	protected void disengageSubject() {
		super.disengageSubject();
		// clear out our value when we are not listening to the subject
		this.value = null;
	}

    @Override
	protected void disengageNonNullSubject() {
		for (int i = this.propertyNames.length; i-- > 0; ) {
			((Model) this.subject).removePropertyChangeListener(this.propertyNames[i], this.propertyChangeListener);
		}
	}


	// ********** AbstractModel implementation **********

	@Override
	public void toString(StringBuilder sb) {
		for (int i = 0; i < this.propertyNames.length; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(this.propertyNames[i]);
		}
	}


	// ********** behavior **********

	/**
	 * Return the aspect's value.
	 * At this point the subject may be null.
	 */
	protected Object buildValue() {
		return (this.subject == null) ? null : this.buildValue_();
	}

	/**
	 * Return the value of the subject's property.
	 * At this point we can be sure that the subject is not null.
	 * @see #buildValue()
	 */
	protected Object buildValue_() {
		throw new UnsupportedOperationException();
	}

	protected void propertyChanged() {
		Object old = this.value;
		this.value = this.buildValue();
		this.fireAspectChange(old, this.value);
	}

}
