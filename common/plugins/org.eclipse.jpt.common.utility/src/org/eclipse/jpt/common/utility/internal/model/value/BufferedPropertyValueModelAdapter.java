/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.ModelTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * This adapter is used to hold a temporary copy of the value
 * in another property value model (the <em>wrapped</em> value model). The application
 * can modify this temporary copy, ad nauseam; but the temporary copy is only
 * passed through to the <em>wrapped</em> value model when the trigger <em>accepts</em> the
 * buffered value. Alternatively, the application can <em>reset</em> the buffered value
 * to the original, <em>wrapped</em> value.
 * <p>
 * The trigger is an object that can be used to <em>accept</em> or <em>reset</em>
 * the buffered value on one or more buffered value models.
 * <p>
 * Typically, in a dialog:<ul>
 * <li>pressing the "OK" button will trigger an <em>accept</em> and close the dialog
 * <li>pressing the "Cancel" button will simply close the dialog,
 *     dropping the <em>buffered</em> values into the bit bucket
 * <li>pressing the "Apply" button will trigger an <em>accept</em> and leave the
 *     dialog open
 * <li>pressing the "Restore" button will trigger a <em>reset</em> and leave the
 *     dialog open
 * </ul>
 * As an example: A number of buffered property value models can wrap another set of
 * property aspect adapters that adapt the various aspects of a single
 * domain model. All the buffered property value models can be hooked to the
 * same trigger, and that trigger is controlled by the application, typically
 * via the "OK" button in a dialog.
 * 
 * @param <V> the type of the model's value
 * @see PropertyAspectAdapterXXXX
 */
public final class BufferedPropertyValueModelAdapter<V>
	implements PluggableModifiablePropertyValueModel.Adapter<V>, PropertyChangeListener
{
	/**
	 *  The <em>wrapped</em> model.
	 */
	private final ModifiablePropertyValueModel<V> wrappedValueModel;

	/**
	 * The cached value of {@link #wrappedValueModel}.
	 */
	private volatile V wrappedValue;

	/**
	 * The trigger that <em>accepts</em> or <em>resets</em> {@link #bufferedValue}.
	 */
	private final Trigger trigger;
	private final Trigger.Listener triggerListener;

	/**
	 * The <em>real</em> adapter.
	 */
	private final BasePluggablePropertyValueModel.Adapter.Listener<V> listener;

	/**
	 * This flag indicates whether {@link #bufferedValue} has been assigned
	 * a value and is out of sync with {@link #wrappedValue}.
	 */
	private final SimplePropertyValueModel<Boolean> bufferingModel;

	/**
	 * We cache the value here until it is accepted and passed
	 * through to {@link #wrappedValueModel}.
	 */
	private volatile V bufferedValue;


	// ********** constructors **********

	public BufferedPropertyValueModelAdapter(Factory<V> factory, BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
		super();
		if (factory == null) {
			throw new NullPointerException();
		}
		this.wrappedValueModel = factory.wrappedValueModel;
		this.trigger = factory.trigger;
		this.triggerListener = this.buildTriggerListener();
		this.bufferingModel = factory.bufferingModel;

		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	private Trigger.Listener buildTriggerListener() {
		return new TriggerListener();
	}

	/* CU private */ class TriggerListener
		implements Trigger.Listener
	{
		public void accept() {
			BufferedPropertyValueModelAdapter.this.accept();
		}
		public void reset() {
			BufferedPropertyValueModelAdapter.this.reset();
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}


	// ********** PluggableModifiablePropertyValueModel.Adapter **********

	public void setValue(V value) {
		if (this.isBuffering()) {
			if (ObjectTools.equals(value, this.wrappedValue)) {
				// our value is being set back to the original wrapped value - stop buffering
				this.bufferedValue = null;
				this.setBuffering(false);
				this.listener.valueChanged(this.wrappedValue);
			} else {
				// our (buffered) value is being changed
				this.listener.valueChanged(this.bufferedValue = value);
			}
		} else {
			if (ObjectTools.equals(value, this.wrappedValue)) {
				// our value is being set to the same as the original wrapped value - ignore
			} else {
				// our value is being changed for the first time - start buffering
				this.setBuffering(true);
				this.listener.valueChanged(this.bufferedValue = value);
			}
		}
	}

	public V engageModel() {
		this.trigger.addListener(this.triggerListener);
		this.wrappedValueModel.addPropertyChangeListener(PropertyValueModel.VALUE, this);
		return this.wrappedValue = this.wrappedValueModel.getValue();
	}

	public V disengageModel() {
		this.trigger.removeListener(this.triggerListener);
		this.wrappedValueModel.removePropertyChangeListener(PropertyValueModel.VALUE, this);
		this.setBuffering(false);
		this.bufferedValue = null;
		return this.wrappedValue = null;
	}


	// ********** PropertyChangeListener **********

	public void propertyChanged(PropertyChangeEvent event) {
		@SuppressWarnings("unchecked")
		V newWrappedValue = (V) event.getNewValue();
		this.wrappedValueChanged(newWrappedValue);
	}

	/**
	 * If we have a <em>buffered</em> value, check whether the <em>wrapped</em> value has
	 * changed to be the same as the <em>buffered</em> value. If it has, stop <em>buffering</em>;
	 * if not, do nothing.
	 * If we do not yet have a <em>buffered</em> value, simply propagate the
	 * change notification with the buffered model as the source.
	 * <p>
	 * <strong>NB:</strong>
	 * By default, if we have a <em>buffered</em> value and the <em>wrapped</em> value changes
	 * to something other than the current <em>buffered</em> value,
	 * we simply ignore the new <em>wrapped</em> value and simply overlay it with the
	 * <em>buffered</em> value if it is <em>accepted</em>. ("Last One In Wins" concurrency model)
	 * Subclasses can override this method to change that behavior with a
	 * different concurrency model. For example, it could drop the <em>buffered</em> value
	 * and replace it with the new <em>wrapped</em> value, or it could throw an
	 * exception.
	 */
	private void wrappedValueChanged(V newWrappedValue) {
		this.wrappedValue = newWrappedValue;
		if (this.isBuffering()) {
			if (ObjectTools.equals(this.bufferedValue, this.wrappedValue)) {
				// if the buffered value is now the same as the original value,
				// there is no need to continue "buffering"
				this.bufferedValue = null;
				this.setBuffering(false);
			} else {
				// NOP - see note in method comment
			}
		} else {
			this.listener.valueChanged(this.wrappedValue);
		}
	}


	// ********** Trigger calls **********

	/**
	 * The trigger has been accepted.
	 * If we are buffering, push the {@link #bufferedValue} to the {@link #wrappedValue}.
	 * The resulting change event will clear the {@link #bufferedValue}
	 * and the {@link #bufferingModel buffering} flag.
	 */
	/* CU private */ void accept() {
		if (this.isBuffering()) {
			// the resulting change event will clear the 'bufferedValue' and the 'buffering' flag
			this.wrappedValueModel.setValue(this.bufferedValue);
		}
	}

	/**
	 * The trigger has been reset.
	 * If we are buffering, clear the {@link #bufferedValue}
	 * and the {@link #bufferingModel buffering} flag and notify our listener
	 * that our value has reverted to the original {@link #wrappedValue}.
	 */
	/* CU private */ void reset() {
		if (this.isBuffering()) {
			this.bufferedValue = null;
			this.setBuffering(false);
			this.listener.valueChanged(this.wrappedValue);
		}
	}


	// ********** misc **********

	private boolean isBuffering() {
		return this.bufferingModel.getValue().booleanValue();
	}

	private void setBuffering(boolean buffering) {
		this.bufferingModel.setValue(Boolean.valueOf(buffering));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (this.isBuffering()) {
			sb.append(this.bufferedValue);
			sb.append(" ["); //$NON-NLS-1$
			sb.append(this.wrappedValue);
			sb.append("]"); //$NON-NLS-1$
		} else {
			sb.append(this.wrappedValue);
		}
		return ObjectTools.toString(this, sb.toString());
	}


	// ********** Trigger **********

	/**
	 * A trigger is used to <em>accept</em> or <em>reset</em> one or more
	 * buffered value models.
	 */
	public static final class Trigger {
		private final ListenerList<Listener> listenerList = ModelTools.listenerList();

		public Trigger() {
			super();
		}

		/**
		 * Accept the trigger.
		 */
		public void accept() {
			for (Listener listener : this.listenerList) {
				listener.accept();
			}
		}

		/**
		 * Reset the trigger.
		 */
		public void reset() {
			for (Listener listener : this.listenerList) {
				listener.reset();
			}
		}

		public void addListener(Listener listener) {
			this.listenerList.add(listener);
		}

		public void removeListener(Listener listener) {
			this.listenerList.remove(listener);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}

		public interface Listener {
			void reset();
			void accept();
		}
	}


	// ********** Factory **********

	public static final class Factory<V>
		implements PluggableModifiablePropertyValueModel.Adapter.Factory<V>
	{
		/* CU private */ final ModifiablePropertyValueModel<V> wrappedValueModel;
		/* CU private */ final Trigger trigger;
		/* CU private */ final SimplePropertyValueModel<Boolean> bufferingModel = new SimplePropertyValueModel<>(Boolean.FALSE);

		public Factory(ModifiablePropertyValueModel<V> wrappedValueModel, Trigger trigger) {
			super();
			if (wrappedValueModel == null) {
				throw new NullPointerException();
			}
			this.wrappedValueModel = wrappedValueModel;
			if (trigger == null) {
				throw new NullPointerException();
			}
			this.trigger = trigger;
		}

		public PropertyValueModel<Boolean> getBufferingModel() {
			return this.bufferingModel;
		}

		public BufferedPropertyValueModelAdapter<V> buildAdapter(BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
			return new BufferedPropertyValueModelAdapter<>(this, listener);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}
