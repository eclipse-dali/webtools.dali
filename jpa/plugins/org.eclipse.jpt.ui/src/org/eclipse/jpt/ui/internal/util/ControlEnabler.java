/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.util;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Control;

/**
 * This <code>ControlEnabler</code> keeps the "enabled" state of a collection of
 * controls in synch with the provided boolean holder.
 *
 * @version 2.0
 * @since 2.0
 */
public class ControlEnabler extends StateController
{
	/**
	 * Creates a new <code>ControlEnabler</code> with a default value of
	 * <code>false</code> (i.e. disabled).
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controls The collection of controls whose "enabled" state is
	 * kept in sync with the boolean holder's value
	 */
	public ControlEnabler(PropertyValueModel<Boolean> booleanHolder,
	                      Collection<? extends Control> controls) {

		this(booleanHolder, controls, false);
	}

	/**
	 * Creates a new <code>ControlEnabler</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controls The collection of controls whose "enabled" state is
	 * kept in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	public ControlEnabler(PropertyValueModel<Boolean> booleanHolder,
	                      Collection<? extends Control> controls,
	                      boolean defaultValue) {

		this(booleanHolder, controls.iterator(), defaultValue);
	}

	/**
	 * Creates a new <code>ControlEnabler</code> with a default value of
	 * <code>false</code> (i.e. disabled).
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param control The control whose "enabled" state is
	 * kept in sync with the boolean holder's value
	 */
	public ControlEnabler(PropertyValueModel<Boolean> booleanHolder,
	                      Control control) {

		this(booleanHolder, control, false);
	}

	/**
	 * Creates a new <code>ControlEnabler</code> with a default value of
	 * <code>false</code> (i.e. disabled).
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controls The collection of controls whose "enabled" state is
	 * kept in sync with the boolean holder's value
	 */
	public ControlEnabler(PropertyValueModel<Boolean> booleanHolder,
	                      Control... controls) {

		this(booleanHolder, CollectionTools.iterator(controls), false);
	}

	/**
	 * Creates a new <code>ControlEnabler</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param control The control whose "enabled" state is
	 * kept in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	public ControlEnabler(PropertyValueModel<Boolean> booleanHolder,
	                      Control control,
	                      boolean defaultValue) {

		this(booleanHolder, CollectionTools.singletonIterator(control), false);
	}

	/**
	 * Creates a new <code>ControlEnabler</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controls The collection of controls whose "enabled" state is
	 * kept in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	public ControlEnabler(PropertyValueModel<Boolean> booleanHolder,
	                      Control[] controls,
	                      boolean defaultValue) {

		this(booleanHolder, CollectionTools.iterator(controls), defaultValue);
	}

	/**
	 * Creates a new <code>ControlEnabler</code> with a default value of
	 * <code>false</code> (i.e. disabled).
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controls An iterator on the collection of controls whose
	 * "enabled" state is kept in sync with the boolean holder's value
	 */
	public ControlEnabler(PropertyValueModel<Boolean> booleanHolder,
	                      Iterator<? extends Control> controls) {

		this(booleanHolder, controls, false);
	}

	/**
	 * Creates a new <code>ControlEnabler</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controls An iterator on the collection of controls whose
	 * "enabled" state is kept in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	public ControlEnabler(PropertyValueModel<Boolean> booleanHolder,
	                      Iterator<? extends Control> controls,
	                      boolean defaultValue) {

		super(booleanHolder, wrap(controls), defaultValue);
	}

	private static Collection<IControlHolder> wrap(Iterator<? extends Control> controls) {
		return CollectionTools.collection(new TransformationIterator<Control, IControlHolder>(controls) {
			@Override
			protected IControlHolder transform(Control control) {
				return new ControlHolder(control);
			}
		});
	}

	/**
	 * This holder holds onto a <code>Control</code> and update its enabled state.
	 */
	private static class ControlHolder implements IControlHolder {

		private final Control control;

		ControlHolder(Control control) {
			super();
			this.control = control;
		}

		public void updateState(boolean state) {
			if (!this.control.isDisposed()) {
				this.control.setEnabled(state);
			}
		}
	}
}