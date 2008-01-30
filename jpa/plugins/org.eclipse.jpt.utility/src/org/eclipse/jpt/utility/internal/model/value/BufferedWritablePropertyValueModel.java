/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;

/**
 * A BufferedPropertyValueModel is used to hold a temporary copy of the value
 * in another property value model (the "wrapped" value holder). The application
 * can modify this temporary copy, ad nauseam; but the temporary copy is only
 * passed through to the "wrapped" value holder when the trigger "accepts" the
 * buffered value. Alternatively, the application can "reset" the buffered value
 * to the original, "wrapped" value.
 * 
 * The trigger is another value model that holds a Boolean and the application
 * changes the trigger's value to true on "accept", false on "reset". Typically,
 * in a dialog:
 * 	- pressing the OK button will trigger an "accept" and close the dialog
 * 	- pressing the Cancel button will simply close the dialog,
 * 		dropping the "buffered" values into the bit bucket
 * 	- pressing the Apply button will trigger an "accept" and leave the dialog open
 * 	- pressing the Restore button will trigger a "reset" and leave the dialog open
 * 
 * A number of buffered property value models can wrap another set of
 * property aspect adapters that adapt the various aspects of a single
 * domain model. All the bufferd property value models can be hooked to the
 * same trigger, and that trigger is controlled by the application, typically
 * via the OK button in a dialog.
 * 
 * @see PropertyAspectAdapter
 */
public class BufferedWritablePropertyValueModel<T>
	extends PropertyValueModelWrapper<T>
	implements WritablePropertyValueModel<T>
{

	/**
	 * We cache the value here until it is accepted and passed
	 * through to the wrapped value holder.
	 */
	protected T bufferedValue;

	/**
	 * This is set to true when we are "accepting" the buffered value
	 * and passing it through to the wrapped value holder. This allows
	 * us to ignore the property change event fired by the wrapped
	 * value holder.
	 * (We can't stop listening to the wrapped value holder, because
	 * if we are the only listener that could "deactivate" the wrapped
	 * value holder.)
	 */
	protected boolean accepting;

	/**
	 * This is the trigger that indicates whether the buffered value
	 * should be accepted or reset.
	 */
	protected final PropertyValueModel<Boolean> triggerHolder;

	/** This listens to the trigger holder. */
	protected final PropertyChangeListener triggerChangeListener;

	/**
	 * This flag indicates whether our buffered value has been assigned
	 * a value and is possibly out of synch with the wrapped value.
	 */
	protected boolean buffering;


	// ********** constructors **********

	/**
	 * Construct a buffered property value model with the specified wrapped
	 * property value model and trigger holder.
	 */
	public BufferedWritablePropertyValueModel(WritablePropertyValueModel<T> valueHolder, PropertyValueModel<Boolean> triggerHolder) {
		super(valueHolder);
		if (triggerHolder == null) {
			throw new NullPointerException();
		}
		this.triggerHolder = triggerHolder;
		this.bufferedValue = null;
		this.buffering = false;
		this.accepting = false;
		this.triggerChangeListener = this.buildTriggerChangeListener();
	}
	

	// ********** initialization **********

	protected PropertyChangeListener buildTriggerChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				BufferedWritablePropertyValueModel.this.triggerChanged(e);
			}
			@Override
			public String toString() {
				return "trigger change listener";
			}
		};
	}
	

	// ********** ValueModel implementation **********

	/**
	 * If we are currently "buffering" a value, return that;
	 * otherwise, return the wrapped value.
	 */
	public T value() {
		return this.buffering ? this.bufferedValue : this.valueHolder.value();
	}

	/**
	 * Assign the new value to our "buffered" value.
	 * It will be forwarded to the wrapped value holder
	 * when the trigger is "accepted".
	 */
	public void setValue(T value) {
		Object old = this.value();
		this.bufferedValue = value;
		this.buffering = true;
		this.firePropertyChanged(VALUE, old, this.bufferedValue);
	}


	// ********** PropertyValueModelWrapper extensions **********

	/**
	 * extend to engage the trigger holder also
	 */
	@Override
	protected void engageValueHolder() {
		super.engageValueHolder();
		this.triggerHolder.addPropertyChangeListener(VALUE, this.triggerChangeListener);
	}

	/**
	 * extend to disengage the trigger holder also
	 */
	@Override
	protected void disengageValueHolder() {
		this.triggerHolder.removePropertyChangeListener(VALUE, this.triggerChangeListener);
		super.disengageValueHolder();
	}


	// ********** behavior **********

	/**
	 * If we do not yet have a "buffered" value, simply propagate the
	 * change notification with the buffered model as the source.
	 * If we do have a "buffered" value, do nothing.
	 */
	@Override
	protected void valueChanged(PropertyChangeEvent e) {
		if (this.accepting) {
			// if we are currently "accepting" the value, ignore change notifications,
			// since we caused them and our own listeners are already aware of the change
			return;
		}
		if (this.buffering) {
			this.handleChangeConflict(e);
		} else {
			this.firePropertyChanged(e.cloneWithSource(this));
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
	protected void handleChangeConflict(PropertyChangeEvent e) {
		// the default is to do nothing
	}
	
	/**
	 * The trigger changed:
	 * If it is now true, "accept" the buffered value and forward
	 * it to the wrapped value holder.
	 * If it is now false, "reset" the buffered value to its original value.
	 */
	protected void triggerChanged(PropertyChangeEvent e) {
		if ( ! this.buffering) {
			// if nothing has been "buffered", we don't need to do anything:
			// nothing needs to be passed through; nothing needs to be reset;
			return;
		}
		if (((Boolean) e.newValue()).booleanValue()) {
			// set the accepting flag so we ignore any events
			// fired by the wrapped value holder
			this.accepting = true;
			this.valueHolder().setValue(this.bufferedValue);
			this.bufferedValue = null;
			this.buffering = false;
			// clear the flag once the "accept" is complete
			this.accepting = false;
		} else {
			// notify our listeners that our value has been reset
			Object old = this.bufferedValue;
			this.bufferedValue = null;
			this.buffering = false;
			this.firePropertyChanged(VALUE, old, this.valueHolder.value());
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value());
	}


	// ********** convenience methods **********

	/**
	 * Return whether the buffered model is currently "buffering"
	 * a value.
	 */
	public boolean isBuffering() {
		return this.buffering;
	}

	/**
	 * Our constructor accepts only a WritablePropertyValueModel<T>.
	 */
	@SuppressWarnings("unchecked")
	protected WritablePropertyValueModel<T> valueHolder() {
		return (WritablePropertyValueModel<T>) this.valueHolder;
	}


	// ********** inner class **********

	/**
	 * Trigger is a special property value model that only maintains its
	 * value (of true or false) during the change notification caused by
	 * the #setValue(Object) method. In other words, a Trigger object
	 * only has a valid value 
	 */
	public static class Trigger extends SimplePropertyValueModel<Boolean> {


		// ********** constructor **********

		/**
		 * Construct a trigger with a null value.
		 */
		public Trigger() {
			super();
		}


		// ********** ValueModel implementation **********

		/**
		 * Extend so that this method can only be invoked during
		 * change notification triggered by #setValue(Object).
		 */
		@Override
		public Boolean value() {
			if (this.value == null) {
				throw new IllegalStateException("The method Trigger.value() may only be called during change notification.");
			}
			return this.value;
		}

		/**
		 * Extend to reset the value to null once all the
		 * listeners have been notified.
		 */
		@Override
		public void setValue(Boolean value) {
			super.setValue(value);
			this.value = null;
		}


		// ********** convenience methods **********

		/**
		 * Set the trigger's value:
		 * 	- true indicates "accept"
		 * 	- false indicates "reset"
		 */
		public void setValue(boolean value) {
			this.setValue(Boolean.valueOf(value));
		}

		/**
		 * Return the trigger's value:
		 * 	- true indicates "accept"
		 * 	- false indicates "reset"
		 */
		public boolean booleanValue() {
			return this.value().booleanValue();
		}

		/**
		 * Accept the trigger (i.e. set its value to true).
		 */
		public void accept() {
			this.setValue(true);
		}

		/**
		 * Return whether the trigger has been accepted
		 * (i.e. its value was changed to true).
		 */
		public boolean isAccepted() {
			return this.booleanValue();
		}

		/**
		 * Reset the trigger (i.e. set its value to false).
		 */
		public void reset() {
			this.setValue(false);
		}

		/**
		 * Return whether the trigger has been reset
		 * (i.e. its value was changed to false).
		 */
		public boolean isReset() {
			return ! this.booleanValue();
		}

	}

}
