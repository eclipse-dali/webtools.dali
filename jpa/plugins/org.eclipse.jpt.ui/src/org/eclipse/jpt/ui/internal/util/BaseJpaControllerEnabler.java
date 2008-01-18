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
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;

/**
 * This <code>BaseJpaControllerEnabler</code> keeps the "enabled" state of a collection of
 * controls in synch with the provided boolean holder.
 *
 * @version 2.0
 * @since 2.0
 */
public class BaseJpaControllerEnabler extends AbstractControlEnabler
{
	/**
	 * Creates a new <code>BaseJpaControllerEnabler</code> with a default value of
	 * <code>false</code> (i.e. disabled).
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controller The controller whose "enabled" state is
	 * kept in sync with the boolean holder's value
	 */
	public BaseJpaControllerEnabler(PropertyValueModel<Boolean> booleanHolder,
	                                BaseJpaController<?> controller) {

		this(booleanHolder, controller, false);
	}

	/**
	 * Creates a new <code>BaseJpaControllerEnabler</code> with a default value of
	 * <code>false</code> (i.e. disabled).
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controllers The collection of controllers whose "enabled" state is
	 * kept in sync with the boolean holder's value
	 */
	public BaseJpaControllerEnabler(PropertyValueModel<Boolean> booleanHolder,
	                                BaseJpaController<?>... controllers) {

		this(booleanHolder, CollectionTools.collection(controllers), false);
	}

	/**
	 * Creates a new <code>BaseJpaControllerEnabler</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controller The controller whose "enabled" state is
	 * kept in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	public BaseJpaControllerEnabler(PropertyValueModel<Boolean> booleanHolder,
	                                BaseJpaController<?> controller,
	                                boolean defaultValue) {

		this(booleanHolder, CollectionTools.singletonIterator(controller), false);
	}

	/**
	 * Creates a new <code>BaseJpaControllerEnabler</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controllers The collection of controllers whose "enabled" state is
	 * kept in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	public BaseJpaControllerEnabler(PropertyValueModel<Boolean> booleanHolder,
	                                BaseJpaController<?>[] controllers,
	                                boolean defaultValue) {

		this(booleanHolder, CollectionTools.iterator(controllers), defaultValue);
	}

	/**
	 * Creates a new <code>BaseJpaControllerEnabler</code> with a default value
	 * of* <code>false</code> (i.e. disabled).
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controllers The collection of controllers whose "enabled" state is
	 * kept in sync with the boolean holder's value
	 */
	public BaseJpaControllerEnabler(PropertyValueModel<Boolean> booleanHolder,
	                                Collection<? extends BaseJpaController<?>> controllers) {

		this(booleanHolder, controllers, false);
	}

	/**
	 * Creates a new <code>BaseJpaControllerEnabler</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controllers The collection of controllers whose "enabled" state is
	 * kept in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	public BaseJpaControllerEnabler(PropertyValueModel<Boolean> booleanHolder,
	                                Collection<? extends BaseJpaController<?>> controllers,
	                                boolean defaultValue) {

		this(booleanHolder, controllers.iterator(), defaultValue);
	}

	/**
	 * Creates a new <code>BaseJpaControllerEnabler</code> with a default value of
	 * <code>false</code> (i.e. disabled).
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controllers An iterator on the collection of controllers whose
	 * "enabled" state is kept in sync with the boolean holder's value
	 */
	public BaseJpaControllerEnabler(PropertyValueModel<Boolean> booleanHolder,
	                                Iterator<? extends BaseJpaController<?>> controllers) {

		this(booleanHolder, controllers, false);
	}

	/**
	 * Creates a new <code>BaseJpaControllerEnabler</code>.
	 *
	 * @param booleanHolder A value model on the underlying boolean model
	 * @param controllers An iterator on the collection of controllers whose
	 * "enabled" state is kept in sync with the boolean holder's value
	 * @param defaultValue The value to use when the underlying model is
	 * <code>null</code>
	 */
	public BaseJpaControllerEnabler(PropertyValueModel<Boolean> booleanHolder,
	                                Iterator<? extends BaseJpaController<?>> controllers,
	                                boolean defaultValue) {

		super(booleanHolder, wrap(controllers), defaultValue);
	}

	private static Collection<IControlHolder> wrap(Iterator<? extends BaseJpaController<?>> controllers) {
		return CollectionTools.collection(new TransformationIterator<BaseJpaController<?>, IControlHolder>(controllers) {
			@Override
			protected IControlHolder transform(BaseJpaController<?> controller) {
				return new BaseJpaControllerHolder(controller);
			}
		});
	}

	private static class BaseJpaControllerHolder implements IControlHolder {
		private final BaseJpaController<?> controller;

		BaseJpaControllerHolder(BaseJpaController<?> controller) {
			super();
			this.controller = controller;
		}

		public void setEnabled(boolean enabled) {
			this.controller.enableWidgets(enabled);
		}
	}
}
