/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.ChangeEvent;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.CommandChangeListener;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;

/**
 * Extend {@link ValueAspectAdapter} to listen to all the aspects
 * of the value in the wrapped value model.
 */
public class ValueChangeAdapter<V extends Model>
	extends ValueAspectAdapter<V>
{
	/** Listener that listens to the value. */
	protected final ChangeListener valueAspectListener;


	// ********** constructors **********

	/**
	 * Construct a change adapter for the specified value.
	 */
	public ValueChangeAdapter(WritablePropertyValueModel<V> valueHolder) {
		super(valueHolder);
		this.valueAspectListener = this.buildValueAspectListener();
	}


	// ********** initialization **********

	protected ChangeListener buildValueAspectListener() {
		return new CommandChangeListener() {
			@Override
			protected void modelChanged(ChangeEvent event) {
				ValueChangeAdapter.this.valueAspectChanged(event);
			}
			@Override
			public String toString() {
				return "value change listener"; //$NON-NLS-1$
			}
		};
	}
	

	// ********** ValueAspectAdapter implementation **********

	@Override
	protected void engageValue_() {
		this.value.addChangeListener(this.valueAspectListener);
	}

	@Override
	protected void disengageValue_() {
		this.value.removeChangeListener(this.valueAspectListener);
	}


	// ********** change events **********

	protected void valueAspectChanged(@SuppressWarnings("unused") ChangeEvent event) {
		this.valueAspectChanged();
	}

}
