/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.EventListener;

import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

/**
 * This {@link AspectAdapter} provides basic property change support.
 * This converts an "aspect" (as defined by subclasses) into
 * a single {@link #VALUE} property.
 * <p>
 * The typical subclass will override the following methods:<ul>
 * <li>{@link #engageSubject_()}<p>
 *     implement this method to add the appropriate listener to the subject
 * <li>{@link #disengageSubject_()}<p>
 *     implement this method to remove the appropriate listener from the subject
 * <li>{@link #buildValue_()}<p>
 *     at the very minimum, override this method to return the value of the
 *     subject's aspect (or "virtual" aspect); it does not need to be
 *     overridden if {@link #buildValue()} is overridden and its behavior changed
 * <li>{@link #setValue_(Object)}<p>
 *     override this method if the client code needs to <em>set</em> the value of
 *     the subject's aspect; oftentimes, though, the client code (e.g. UI)
 *     will need only to <em>get</em> the value; it does not need to be
 *     overridden if {@link #setValue(Object)} is overridden and its behavior changed
 * <li>{@link #buildValue()}<p>
 *     override this method only if returning a <code>null</code> value when
 *     the subject is <code>null</code> is unacceptable
 * <li>{@link #setValue(Object)}<p>
 *     override this method only if something must be done when the subject
 *     is <code>null</code> (e.g. throw an exception)
 * </ul>
 * To notify listeners, subclasses can call {@link #aspectChanged()}
 * whenever the aspect has changed.
 * 
 * @param <S> the type of the model's subject
 * @param <V> the type of the subject's property aspect
 */
public abstract class AspectPropertyValueModelAdapter<S, V>
	extends AspectAdapter<S, V>
	implements ModifiablePropertyValueModel<V>
{
	/**
	 * Cache the current value of the aspect so we
	 * can pass an "old value" when we fire a property change event.
	 * We need this because the value may be calculated and may
	 * not be in the property change event fired by the subject,
	 * especially when dealing with multiple aspects.
	 */
	protected volatile V value;


	// ********** constructor **********

	/**
	 * Construct a property value model adapter for an aspect of the
	 * specified subject.
	 * The subject model cannot be <code>null</code>.
	 */
	protected AspectPropertyValueModelAdapter(PropertyValueModel<? extends S> subjectModel) {
		super(subjectModel);
		// our value is null when we are not listening to the subject
		this.value = null;
	}


	// ********** PropertyValueModel implementation **********

	/**
	 * Return the value of the subject's aspect.
	 */
	public final V getValue() {
		return this.value;
	}


	// ********** WritablePropertyValueModel implementation **********

	/**
	 * Set the value of the subject's aspect.
	 */
	public void setValue(V value) {
		if (this.subject != null) {
			this.setValue_(value);
		}
	}

	/**
	 * Set the value of the subject's aspect.
	 * At this point we can be sure the {@link #subject} is
	 * <em>not</em> <code>null</code>.
	 * @see #setValue(Object)
	 */
	protected void setValue_(@SuppressWarnings("unused") V value) {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}


	// ********** AspectAdapter implementation **********

	@Override
	protected V getAspectValue() {
		return this.value;
	}

	@Override
	protected Class<? extends EventListener> getListenerClass() {
		return PropertyChangeListener.class;
	}

	@Override
	protected String getListenerAspectName() {
		return VALUE;
	}

    @Override
	protected boolean hasListeners() {
		return this.hasAnyPropertyChangeListeners(VALUE);
	}

    @Override
	protected void fireAspectChanged(V oldValue, V newValue) {
		this.firePropertyChanged(VALUE, oldValue, newValue);
	}

    @Override
	protected void engageSubject() {
		super.engageSubject();
		// sync our value *after* we start listening to the subject,
		// since its value might change when a listener is added
		this.value = this.buildValue();
	}

    @Override
	protected void disengageSubject() {
		super.disengageSubject();
		// clear out our value when we are not listening to the subject
		this.value = null;
	}


	// ********** behavior **********

	/**
	 * Return the aspect's value.
	 * At this point the subject may be <code>null</code>.
	 */
	protected V buildValue() {
		return (this.subject == null) ? null : this.buildValue_();
	}

	/**
	 * Return the value of the subject's aspect.
	 * At this point we can be sure the {@link #subject} is
	 * <em>not</em> <code>null</code>.
	 * @see #buildValue()
	 */
	protected V buildValue_() {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}

	/**
	 * This method can be called by subclasses whenever the subject's aspect
	 * has changed; listeners will be notified appropriately.
	 */
	protected void aspectChanged() {
		V old = this.value;
		this.fireAspectChanged(old, this.value = this.buildValue());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}
}
