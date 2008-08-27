/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.swt;

import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.DateTime;

/**
 * This adapter can be used to keep a DateTime widget in synch with
 * model integers hours, minutes, and seconds.  Has default hours,
 * minutes and seconds of 0 which corresponds to 12:00:00 AM. This model
 * adapter can only be used for a DateTime widget with the style SWT.TIME
 */
@SuppressWarnings("nls")
public class DateTimeModelAdapter {

	/**
	 * A value model on the underlying model hours integer.
	 */
	protected final WritablePropertyValueModel<Integer> hoursHolder;
	
	/**
	 * A value model on the underlying model minutes integer.
	 */
	protected final WritablePropertyValueModel<Integer> minutesHolder;
	
	/**
	 * A value model on the underlying model seconds integer.
	 */
	protected final WritablePropertyValueModel<Integer> secondsHolder;

	/**
	 * A listener that allows us to synchronize the dateTime's selection state with
	 * the model hours integer.
	 */
	protected final PropertyChangeListener hoursPropertyChangeListener;
	
	/**
	 * A listener that allows us to synchronize the dateTime's selection state with
	 * the model minutes integer.
	 */
	protected final PropertyChangeListener minutesPropertyChangeListener;
	
	/**
	 * A listener that allows us to synchronize the dateTime's selection state with
	 * the model seconds integer.
	 */
	protected final PropertyChangeListener secondsPropertyChangeListener;

	/**
	 * The dateTime we keep synchronized with the model integers.
	 */
	protected final DateTime dateTime;

	/**
	 * A listener that allows us to synchronize our selection number holder
	 * with the spinner's value.
	 */
	protected final SelectionListener dateTimeSelectionListener;

	/**
	 * A listener that allows us to stop listening to stuff when the dateTime
	 * is disposed.
	 */
	protected final DisposeListener dateTimeDisposeListener;

	/**
	 * This lock is used to prevent the listeners to be notified when the value
	 * changes from the spinner or from the holder.
	 */
	private boolean locked;

	// ********** static methods **********

	/**
	 * Adapt the specified model integer holders to the specified dateTime.
	 */
	public static DateTimeModelAdapter adapt(
			WritablePropertyValueModel<Integer> hoursHolder,
			WritablePropertyValueModel<Integer> minutesHolder,
			WritablePropertyValueModel<Integer> secondsHolder,
			DateTime dateTime)
	{
		return new DateTimeModelAdapter(hoursHolder, minutesHolder, secondsHolder, dateTime);
	}


	// ********** constructors **********

	/**
	 * Constructor - the hoursHolder, minutesHolder, secondsHolder, and dateTime are required
	 */
	protected DateTimeModelAdapter(WritablePropertyValueModel<Integer> hoursHolder,
									WritablePropertyValueModel<Integer> minutesHolder,
									WritablePropertyValueModel<Integer> secondsHolder,
									DateTime dateTime) {
		super();
		if ((hoursHolder == null) 
			|| (minutesHolder == null) 
			|| (secondsHolder == null) 
			|| (dateTime == null)) {
			throw new NullPointerException();
		}
		this.hoursHolder = hoursHolder;
		this.minutesHolder = minutesHolder;
		this.secondsHolder = secondsHolder;
		this.dateTime = dateTime;

		this.hoursPropertyChangeListener = this.buildHoursPropertyChangeListener();
		this.hoursHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.hoursPropertyChangeListener);

		this.minutesPropertyChangeListener = this.buildMinutesPropertyChangeListener();
		this.minutesHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.minutesPropertyChangeListener);

		this.secondsPropertyChangeListener = this.buildSecondsPropertyChangeListener();
		this.secondsHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.secondsPropertyChangeListener);
		
		this.dateTimeSelectionListener = this.buildDateTimeSelectionListener();
		this.dateTime.addSelectionListener(this.dateTimeSelectionListener);

		this.dateTimeDisposeListener = this.buildDateTimeDisposeListener();
		this.dateTime.addDisposeListener(this.dateTimeDisposeListener);

		this.updateDateTimeHours(hoursHolder.getValue());
		this.updateDateTimeMinutes(minutesHolder.getValue());
		this.updateDateTimeSeconds(secondsHolder.getValue());
	}


	// ********** initialization **********

	protected PropertyChangeListener buildHoursPropertyChangeListener() {
		return new SWTPropertyChangeListenerWrapper(this.buildHoursPropertyChangeListener_());
	}

	protected PropertyChangeListener buildHoursPropertyChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				DateTimeModelAdapter.this.hoursChanged(event);
			}
			@Override
			public String toString() {
				return "dateTime hours listener";
			}
		};
	}
	
	protected PropertyChangeListener buildMinutesPropertyChangeListener() {
		return new SWTPropertyChangeListenerWrapper(this.buildMinutesPropertyChangeListener_());
	}

	protected PropertyChangeListener buildMinutesPropertyChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				DateTimeModelAdapter.this.minutesChanged(event);
			}
			@Override
			public String toString() {
				return "dateTime minutes listener";
			}
		};
	}
	
	protected PropertyChangeListener buildSecondsPropertyChangeListener() {
		return new SWTPropertyChangeListenerWrapper(this.buildSecondsPropertyChangeListener_());
	}

	protected PropertyChangeListener buildSecondsPropertyChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				DateTimeModelAdapter.this.secondsChanged(event);
			}
			@Override
			public String toString() {
				return "dateTime seconds listener";
			}
		};
	}

	protected SelectionListener buildDateTimeSelectionListener() {
		return new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				DateTimeModelAdapter.this.dateTimeSelected(e);
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {				
			}
			
			@Override
			public String toString() {
				return "dateTime selection listener";
			}
		};
	}

	protected DisposeListener buildDateTimeDisposeListener() {
		return new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				DateTimeModelAdapter.this.dateTimeDisposed(event);
			}
			@Override
			public String toString() {
				return "dateTime dispose listener";
			}
		};
	}


	// ********** model events **********

	protected void hoursChanged(PropertyChangeEvent event) {
		if (!this.locked) {
			this.updateDateTimeHours((Integer) event.getNewValue());
		}
	}

	protected void minutesChanged(PropertyChangeEvent event) {
		if (!this.locked) {
			this.updateDateTimeMinutes((Integer) event.getNewValue());
		}
	}

	protected void secondsChanged(PropertyChangeEvent event) {
		if (!this.locked) {
			this.updateDateTimeSeconds((Integer) event.getNewValue());
		}
	}

	// ********** dateTime events **********

	protected void dateTimeSelected(SelectionEvent event) {
		if (!this.locked) {
			this.locked = true;
			try {
				//too bad they didn't split the event up
				hoursSelected();
				minutesSelected();
				secondsSelected();
			}
			finally {
				this.locked = false;
			}
		}
	}
	
	protected void hoursSelected() {
		Integer hours = null;
		if (this.dateTime.getHours() != 0) {
			hours = Integer.valueOf(this.dateTime.getHours());
		}
		this.hoursHolder.setValue(hours);
	}
	
	protected void minutesSelected() {
		Integer minutes = null;
		if (this.dateTime.getMinutes() != 0) {
			minutes = Integer.valueOf(this.dateTime.getMinutes());
		}
		this.minutesHolder.setValue(minutes);
	}
	
	protected void secondsSelected() {
		Integer seconds = null;
		if (this.dateTime.getSeconds() != 0) {
			seconds = Integer.valueOf(this.dateTime.getSeconds());
		}
		this.secondsHolder.setValue(seconds);
	}

	protected void dateTimeDisposed(DisposeEvent event) {
		// the dateTime is not yet "disposed" when we receive this event
		// so we can still remove our listeners
		this.dateTime.removeDisposeListener(this.dateTimeDisposeListener);
		this.dateTime.removeSelectionListener(this.dateTimeSelectionListener);
		this.hoursHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.hoursPropertyChangeListener);
		this.minutesHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.minutesPropertyChangeListener);
		this.secondsHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.secondsPropertyChangeListener);
	}

	// ********** update **********

	protected void updateDateTimeHours(Integer hours) {
		if (this.dateTime.isDisposed()) {
			return;
		}
		if (hours == null) {
			hours = Integer.valueOf(0);//TODO defaultHours
		}
		this.locked = true;
		try {
			this.dateTime.setHours(hours.intValue());
		}
		finally {
			this.locked = false;
		}
	}
	
	protected void updateDateTimeMinutes(Integer minutes) {
		if (this.dateTime.isDisposed()) {
			return;
		}
		if (minutes == null) {
			minutes = Integer.valueOf(0);//TODO defaultMinutes
		}
		this.locked = true;
		try {
			this.dateTime.setMinutes(minutes.intValue());
		}
		finally {
			this.locked = false;
		}
	}
	
	protected void updateDateTimeSeconds(Integer seconds) {
		if (this.dateTime.isDisposed()) {
			return;
		}
		if (seconds == null) {
			seconds = Integer.valueOf(0);//TODO defaultSeconds
		}
		this.locked = true;
		try {
			this.dateTime.setSeconds(seconds.intValue());
		}
		finally {
			this.locked = false;
		}
	}

	// ********** standard methods **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.hoursHolder);
	}
}
