/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * This {@link AspectPropertyValueModelAdapter} provides basic property change support.
 * This converts a set of one or more standard properties into
 * a single {@link #VALUE} property.
 * <p>
 * The typical subclass will override the following methods (see the descriptions
 * in {@link AspectPropertyValueModelAdapter}):<ul>
 * <li>{@link #buildValue_()}
 * <li>{@link #setValue_(Object)}
 * <li>{@link #buildValue()}
 * <li>{@link #setValue(Object)}
 * </ul>
 */
public abstract class PropertyAspectAdapter<S extends Model, V>
	extends AspectPropertyValueModelAdapter<S, V>
{
	/** The name of the subject's properties that we use for the value. */
	protected final String[] propertyNames;
		protected static final String[] EMPTY_PROPERTY_NAMES = new String[0];

	/** A listener that listens to the appropriate properties of the subject. */
	protected final PropertyChangeListener propertyChangeListener;


	// ********** constructors **********

	/**
	 * Construct a property aspect adapter for the specified subject
	 * and property.
	 */
	protected PropertyAspectAdapter(String propertyName, S subject) {
		this(new String[] {propertyName}, subject);
	}

	/**
	 * Construct a property aspect adapter for the specified subject
	 * and properties.
	 */
	protected PropertyAspectAdapter(String[] propertyNames, S subject) {
		this(new StaticPropertyValueModel<S>(subject), propertyNames);
	}

	/**
	 * Construct a property aspect adapter for the specified subject holder
	 * and properties.
	 */
	protected PropertyAspectAdapter(PropertyValueModel<? extends S> subjectHolder, String... propertyNames) {
		super(subjectHolder);
		this.propertyNames = propertyNames;
		this.propertyChangeListener = this.buildPropertyChangeListener();
	}

	/**
	 * Construct a property aspect adapter for the specified subject holder
	 * and properties.
	 */
	protected PropertyAspectAdapter(PropertyValueModel<? extends S> subjectHolder, Collection<String> propertyNames) {
		this(subjectHolder, propertyNames.toArray(new String[propertyNames.size()]));
	}

	/**
	 * Construct a property aspect adapter for an "unchanging" property in
	 * the specified subject. This is useful for a property aspect that does not
	 * change for a particular subject; but the subject will change, resulting in
	 * a new property. (A {@link TransformationPropertyValueModel} could also be
	 * used in this situation.)
	 */
	protected PropertyAspectAdapter(PropertyValueModel<? extends S> subjectHolder) {
		this(subjectHolder, EMPTY_PROPERTY_NAMES);
	}


	// ********** initialization **********

	protected PropertyChangeListener buildPropertyChangeListener() {
		// transform the subject's property change events into VALUE property change events
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				PropertyAspectAdapter.this.propertyChanged(event);
			}
			@Override
			public String toString() {
				return "property change listener: " + Arrays.asList(PropertyAspectAdapter.this.propertyNames); //$NON-NLS-1$
			}
		};
	}


	// ********** AspectAdapter implementation **********

    @Override
	protected void engageSubject_() {
    	for (String propertyName : this.propertyNames) {
			((Model) this.subject).addPropertyChangeListener(propertyName, this.propertyChangeListener);
		}
	}

    @Override
	protected void disengageSubject_() {
    	for (String propertyName : this.propertyNames) {
			((Model) this.subject).removePropertyChangeListener(propertyName, this.propertyChangeListener);
		}
	}

    protected void propertyChanged(@SuppressWarnings("unused") PropertyChangeEvent event) {
		this.propertyChanged();
	}

}
