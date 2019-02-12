/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.Collection;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
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
 * 
 * @param <S> the type of the model's subject
 * @param <V> the type of the subject's property aspect
 */
public abstract class PropertyAspectAdapter<S extends Model, V>
	extends AspectPropertyValueModelAdapter<S, V>
{
	/** The name of the subject's properties that we use for the value. */
	protected final String[] aspectNames;
		protected static final String[] EMPTY_ASPECT_NAMES = new String[0];

	/** A listener that listens to the appropriate properties of the subject. */
	protected final PropertyChangeListener aspectChangeListener;


	// ********** constructors **********

	/**
	 * Construct a property aspect adapter for the specified subject
	 * and property aspect.
	 */
	protected PropertyAspectAdapter(String aspectName, S subject) {
		this(new String[] {aspectName}, subject);
	}

	/**
	 * Construct a property aspect adapter for the specified subject
	 * and property aspects.
	 */
	protected PropertyAspectAdapter(String[] aspectNames, S subject) {
		this(new StaticPropertyValueModel<S>(subject), aspectNames);
	}

	/**
	 * Construct a property aspect adapter for the specified subject model
	 * and property aspects.
	 */
	protected PropertyAspectAdapter(PropertyValueModel<? extends S> subjectModel, String... aspectNames) {
		super(subjectModel);
		if (aspectNames == null) {
			throw new NullPointerException();
		}
		this.aspectNames = aspectNames;
		this.aspectChangeListener = this.buildAspectChangeListener();
	}

	/**
	 * Construct a property aspect adapter for the specified subject model
	 * and property aspects.
	 */
	protected PropertyAspectAdapter(PropertyValueModel<? extends S> subjectModel, Collection<String> aspectNames) {
		this(subjectModel, aspectNames.toArray(new String[aspectNames.size()]));
	}

	/**
	 * Construct a property aspect adapter for an "unchanging" property aspect in
	 * the specified subject. This is useful for a property aspect that does not
	 * change for a particular subject; but the subject will change, resulting in
	 * a new property. (A {@link TransformationPropertyValueModel} could also be
	 * used in this situation.)
	 */
	protected PropertyAspectAdapter(PropertyValueModel<? extends S> subjectModel) {
		this(subjectModel, EMPTY_ASPECT_NAMES);
	}


	// ********** initialization **********

	protected PropertyChangeListener buildAspectChangeListener() {
		return new AspectChangeListener();
	}

	/**
	 * Transform the subject's property change events into
	 * {@link #VALUE} property change events.
	 */
	protected class AspectChangeListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			PropertyAspectAdapter.this.aspectChanged(event);
		}
	}


	// ********** AspectAdapter implementation **********

    @Override
	protected void engageSubject_() {
    	for (String propertyName : this.aspectNames) {
			this.subject.addPropertyChangeListener(propertyName, this.aspectChangeListener);
		}
	}

    @Override
	protected void disengageSubject_() {
    	for (String propertyName : this.aspectNames) {
			this.subject.removePropertyChangeListener(propertyName, this.aspectChangeListener);
		}
	}

	protected void aspectChanged(@SuppressWarnings("unused") PropertyChangeEvent event) {
 		//TODO we have multiple PropertyAspectAdapters that depend on the aspectChanged()
    	//behavior because they do a transformation in the buildValue(). It would be
    	//nice to use the new value from the event instead of rebuilding it.
    	//this.fireAspectChanged((V) event.getOldValue(), this.value = (V) event.getNewValue());
    	aspectChanged();
	}
}
