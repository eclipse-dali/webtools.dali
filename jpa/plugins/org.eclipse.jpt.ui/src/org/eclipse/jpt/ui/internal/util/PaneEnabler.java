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
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;

/**
 * This <code>PaneEnabler</code> keeps the "enabled" state of a collection of
 * controls in synch with the provided boolean holder.
 *
 * @version 2.0
 * @since 2.0
 */
public class PaneEnabler extends AbstractControlEnabler
{
	/**
	 * Creates a new <code>PaneEnabler</code> with a default value of
	 * <code>false</code> (i.e. disabled).
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param pane The pane whose "enabled" state is kept in sync with the
	 * boolean holder's value
	 */
	public PaneEnabler(PropertyValueModel<Boolean> booleanHolder,
	                   AbstractPane<?> pane) {

		this(booleanHolder, pane, false);
	}

	/**
	 * Creates a new <code>PaneEnabler</code> with a default value of
	 * <code>false</code> (i.e. disabled).
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param panes The collection of panes whose "enabled" state is kept in sync
	 * with the boolean holder's value
	 */
	public PaneEnabler(PropertyValueModel<Boolean> booleanHolder,
	                   AbstractPane<?>... panes) {

		this(booleanHolder, CollectionTools.collection(panes), false);
	}

	/**
	 * Creates a new <code>PaneEnabler</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param pane The pane whose "enabled" state is kept in sync with the
	 * boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	public PaneEnabler(PropertyValueModel<Boolean> booleanHolder,
	                   AbstractPane<?> pane,
	                   boolean defaultValue) {

		this(booleanHolder, CollectionTools.singletonIterator(pane), false);
	}

	/**
	 * Creates a new <code>PaneEnabler</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param panes The collection of panes whose "enabled" state is kept in sync
	 * with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	public PaneEnabler(PropertyValueModel<Boolean> booleanHolder,
	                   AbstractPane<?>[] panes,
	                   boolean defaultValue) {

		this(booleanHolder, CollectionTools.iterator(panes), defaultValue);
	}

	/**
	 * Creates a new <code>BaseJpaControllerEnabler</code> with a default value
	 * of* <code>false</code> (i.e. disabled).
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param panes The collection of panes whose "enabled" state is kept in sync
	 * with the boolean holder's value
	 */
	public PaneEnabler(PropertyValueModel<Boolean> booleanHolder,
	                   Collection<? extends AbstractPane<?>> panes) {

		this(booleanHolder, panes, false);
	}

	/**
	 * Creates a new <code>BaseJpaControllerEnabler</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param panes The collection of panes whose "enabled" state is kept in sync
	 * with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	public PaneEnabler(PropertyValueModel<Boolean> booleanHolder,
	                   Collection<? extends AbstractPane<?>> panes,
	                   boolean defaultValue) {

		this(booleanHolder, panes.iterator(), defaultValue);
	}

	/**
	 * Creates a new <code>BaseJpaControllerEnabler</code> with a default value of
	 * <code>false</code> (i.e. disabled).
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param panes An iterator on the collection of panes whose "enabled" state
	 * is kept in sync with the boolean holder's value
	 */
	public PaneEnabler(PropertyValueModel<Boolean> booleanHolder,
	                   Iterator<? extends AbstractPane<?>> panes) {

		this(booleanHolder, panes, false);
	}

	/**
	 * Creates a new <code>BaseJpaControllerEnabler</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param panes An iterator on the collection of panes whose "enabled" state
	 * is kept in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	public PaneEnabler(PropertyValueModel<Boolean> booleanHolder,
	                   Iterator<? extends AbstractPane<?>> panes,
	                   boolean defaultValue) {

		super(booleanHolder, wrap(panes), defaultValue);
	}

	private static Collection<IControlHolder> wrap(Iterator<? extends AbstractPane<?>> panes) {
		return CollectionTools.collection(new TransformationIterator<AbstractPane<?>, IControlHolder>(panes) {
			@Override
			protected IControlHolder transform(AbstractPane<?> pane) {
				return new PaneHolder(pane);
			}
		});
	}

	private static class PaneHolder implements IControlHolder {
		private final AbstractPane<?> pane;

		PaneHolder(AbstractPane<?> pane) {
			super();
			this.pane = pane;
		}

		public void setEnabled(boolean enabled) {
			this.pane.enableWidgets(enabled);
		}
	}
}
