/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;

/**
 * A <code>BufferedModifiablePropertyValueModel</code> is used to hold a temporary
 * copy of the value
 * in another property value model (the "wrapped" value model). The application
 * can modify this temporary copy, ad nauseam; but the temporary copy is only
 * passed through to the "wrapped" value model when the trigger "accepts" the
 * buffered value. Alternatively, the application can "reset" the buffered value
 * to the original, "wrapped" value.
 * <p>
 * The trigger is another {@link PropertyValueModel} that holds a
 * {@link Boolean} and the application changes the trigger's value to
 * <code>true</code> on "accept", <code>false</code> on "reset".
 * <p>
 * Typically, in a dialog:<ul>
 * <li>pressing the "OK" button will trigger an "accept" and close the dialog
 * <li>pressing the "Cancel" button will simply close the dialog,
 *     dropping the "buffered" values into the bit bucket
 * <li>pressing the "Apply" button will trigger an "accept" and leave the
 *     dialog open
 * <li>pressing the "Restore" button will trigger a "reset" and leave the
 *     dialog open
 * </ul>
 * A number of buffered property value models can wrap another set of
 * property aspect adapters that adapt the various aspects of a single
 * domain model. All the buffered property value models can be hooked to the
 * same trigger, and that trigger is controlled by the application, typically
 * via the "OK" button in a dialog.
 * 
 * @param <V> the type of the model's value
 * @see PropertyAspectAdapter
 */
public class BufferedModifiablePropertyValueModel<V>
	extends PropertyValueModelWrapper<V>
	implements ModifiablePropertyValueModel<V>
{
	/**
	 * We cache the value here until it is accepted and passed
	 * through to the wrapped value model.
	 */
	protected volatile V bufferedValue;

	/**
	 * This is set to true when we are "accepting" the buffered value
	 * and passing it through to the wrapped value model. This allows
	 * us to ignore the property change event fired by the wrapped
	 * value model.
	 * (We can't stop listening to the wrapped value model, because
	 * if we are the only listener that could "deactivate" the wrapped
	 * value model.)
	 */
	protected volatile boolean accepting;

	/**
	 * This is the trigger that indicates whether the buffered value
	 * should be accepted or reset.
	 */
	protected final PropertyValueModel<Boolean> triggerModel;

	/** This listens to the trigger model. */
	protected final PropertyChangeListener triggerListener;

	/**
	 * This flag indicates whether our buffered value has been assigned
	 * a value and is possibly out of sync with the wrapped value.
	 */
	protected volatile boolean buffering;


	// ********** constructor/initialization **********

	/**
	 * Construct a buffered property value model with the specified wrapped
	 * property value model and trigger model.
	 */
	// TODO wrap the value model in a CachingPVMWrapper and get rid of accepting flag
	public BufferedModifiablePropertyValueModel(ModifiablePropertyValueModel<V> valueModel, PropertyValueModel<Boolean> triggerModel) {
		super(valueModel);
		if (triggerModel == null) {
			throw new NullPointerException();
		}
		this.triggerModel = triggerModel;
		this.bufferedValue = null;
		this.buffering = false;
		this.accepting = false;
		this.triggerListener = this.buildTriggerListener();
	}

	protected PropertyChangeListener buildTriggerListener() {
		return new TriggerListener();
	}

	protected class TriggerListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			BufferedModifiablePropertyValueModel.this.triggerChanged(event);
		}
	}


	// ********** value **********

	/**
	 * If we are currently "buffering" a value, return that;
	 * otherwise, return the wrapped value.
	 */
	public V getValue() {
		return this.buffering ? this.bufferedValue : this.valueModel.getValue();
	}

	/**
	 * Assign the new value to our "buffered" value.
	 * It will be pushed to the wrapped value model
	 * when the trigger is "accepted".
	 */
	public void setValue(V value) {
		if (this.buffering) {
			if (this.valuesAreEqual(value, this.valueModel.getValue())) {
				// the buffered value is being set back to the original value
				this.reset();
			} else {
				// the buffered value is being changed
				Object old = this.bufferedValue;
				this.bufferedValue = value;
				this.firePropertyChanged(VALUE, old, value);
			}
		} else {
			if (this.valuesAreEqual(value, this.valueModel.getValue())) {
				// the buffered value is being set to the same value as the original value - ignore
			} else {
				// the buffered value is being set for the first time
				Object old = this.valueModel.getValue();
				this.bufferedValue = value;
				this.buffering = true;
				this.firePropertyChanged(VALUE, old, value);
			}
		}
	}


	// ********** PropertyValueModelWrapper extensions **********

	/**
	 * Extend to engage the trigger model also.
	 */
	@Override
	protected void engageModel() {
		super.engageModel();
		this.triggerModel.addPropertyChangeListener(VALUE, this.triggerListener);
	}

	/**
	 * Extend to disengage the trigger model also.
	 */
	@Override
	protected void disengageModel() {
		this.triggerModel.removePropertyChangeListener(VALUE, this.triggerListener);
		super.disengageModel();
	}


	// ********** behavior **********

	/**
	 * If we are currently "accepting" the value (i.e passing it on to the
	 * "wrapped" model), ignore change notifications, since we caused
	 * them and our own listeners are already aware of the change.
	 */
	@Override
	protected void wrappedValueChanged(PropertyChangeEvent event) {
		if ( ! this.accepting) {
			this.wrappedValueChanged_(event);
		}
	}

	/**
	 * If we have a "buffered" value, check whether the "wrapped" value has
	 * changed to be the same as the "buffered" value. If it has, stop "buffering";
	 * if not, do nothing.
	 * If we do not yet have a "buffered" value, simply propagate the
	 * change notification with the buffered model as the source.
	 */
	protected void wrappedValueChanged_(PropertyChangeEvent event) {
		if (this.buffering) {
			if (this.valuesAreEqual(event.getNewValue(), this.bufferedValue)) {
				// the buffered value is being set back to the original value
				this.reset();
			} else {
				this.handleChangeConflict(event);
			}
		} else {
			this.firePropertyChanged(event.clone(this));
		}
	}

	/**
	 * By default, if we have a "buffered" value and the "wrapped" value changes,
	 * we simply ignore the new "wrapped" value and simply overlay it with the
	 * "buffered" value if it is "accepted". ("Last One In Wins" concurrency model)
	 * Subclasses can override this method to change that behavior with a
	 * different concurrency model. For example, you could drop the "buffered" value
	 * and replace it with the new "wrapped" value, or you could throw an
	 * exception.
	 */
	protected void handleChangeConflict(@SuppressWarnings("unused") PropertyChangeEvent event) {
		// the default is to do nothing
	}

	protected void triggerChanged(PropertyChangeEvent event) {
		this.triggerChanged(((Boolean) event.getNewValue()).booleanValue());
	}

	/**
	 * The trigger changed:<ul>
	 * <li>If it is now <code>true</code>, "accept" the buffered value and push
	 *     it to the wrapped value model.
	 * <li>If it is now <code>false</code>, "reset" the buffered value to its
	 *     original value.
	 * </ul>
	 */
	protected void triggerChanged(boolean triggerValue) {
		// if nothing has been "buffered", we don't need to do anything:
		// nothing needs to be passed through; nothing needs to be reset;
		if (this.buffering) {
			if (triggerValue) {
				this.accept();
			} else {
				this.reset();
			}
		}
	}

	protected void accept() {
		// set the accepting flag so we ignore any events
		// fired by the wrapped value model
		this.accepting = true;
		try {
			this.getValueModel().setValue(this.bufferedValue);
		} finally {
			this.bufferedValue = null;
			this.buffering = false;
			// clear the flag once the "accept" is complete
			this.accepting = false;
		}
	}

	protected void reset() {
		// notify our listeners that our value has been reset
		Object old = this.bufferedValue;
		this.bufferedValue = null;
		this.buffering = false;
		this.firePropertyChanged(VALUE, old, this.valueModel.getValue());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getValue());
	}


	// ********** misc **********

	/**
	 * Return whether the buffered model is currently "buffering"
	 * a value.
	 */
	public boolean isBuffering() {
		return this.buffering;
	}

	/**
	 * Our constructor accepts only a {@link ModifiablePropertyValueModel}{@code<T>}.
	 */
	@SuppressWarnings("unchecked")
	protected ModifiablePropertyValueModel<V> getValueModel() {
		return (ModifiablePropertyValueModel<V>) this.valueModel;
	}


	// ********** trigger **********

	/**
	 * <code>Trigger</code> is a special property value model that only maintains its
	 * value (of <code>true</code> or <code>false</code>) during the change notification caused by
	 * {@link #setValue(Object)}. In other words, a <code>Trigger</code>
	 * only has a valid value when it is being set.
	 */
	public static class Trigger
		extends SimplePropertyValueModel<Boolean>
	{
		/**
		 * Construct a trigger with a null value.
		 */
		public Trigger() {
			super();
		}


		// ********** ValueModel implementation **********

		/**
		 * Extend so that this method can only be invoked during
		 * change notification triggered by {@link #setValue(Object)}.
		 */
		@Override
		public Boolean getValue() {
			if (this.value == null) {
				throw new IllegalStateException("The method Trigger.getValue() may only be called during change notification."); //$NON-NLS-1$
			}
			return this.value;
		}

		/**
		 * Extend to reset the value to <code>null</code> once all the
		 * listeners have been notified.
		 */
		@Override
		public void setValue(Boolean value) {
			super.setValue(value);
			this.value = null;
		}


		// ********** convenience methods **********

		/**
		 * Set the trigger's value:<ul>
		 * 	<li><code>true</code> indicates "accept"
		 * 	<li><code>false</code> indicates "reset"
		 * </ul>
		 */
		public void setValue(boolean value) {
			this.setValue(Boolean.valueOf(value));
		}

		/**
		 * Return the trigger's value:<ul>
		 * 	<li><code>true</code> indicates "accept"
		 * 	<li><code>false</code> indicates "reset"
		 * </ul>
		 * This method can only be invoked during change notification.
		 */
		public boolean booleanValue() {
			return this.getValue().booleanValue();
		}

		/**
		 * Accept the trigger (i.e. set its value to <code>true</code>).
		 */
		public void accept() {
			this.setValue(true);
		}

		/**
		 * Return whether the trigger has been accepted
		 * (i.e. its value was changed to <code>true</code>).
		 * This method can only be invoked during change notification.
		 */
		public boolean isAccepted() {
			return this.booleanValue();
		}

		/**
		 * Reset the trigger (i.e. set its value to <code>false</code>).
		 */
		public void reset() {
			this.setValue(false);
		}

		/**
		 * Return whether the trigger has been reset
		 * (i.e. its value was changed to <code>false</code>).
		 * This method can only be invoked during change notification.
		 */
		public boolean isReset() {
			return ! this.booleanValue();
		}
	}
}
