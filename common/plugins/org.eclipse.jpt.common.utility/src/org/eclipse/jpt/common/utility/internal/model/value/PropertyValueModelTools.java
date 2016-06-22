/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * Value Model utility methods.
 */
public final class PropertyValueModelTools {

	/**
	 * Construct a property value model adapter for the specified adapter factory.
	 * @see PluggablePropertyValueModel
	 */
	public static <V> PropertyValueModel<V> propertyValueModel(PluggablePropertyValueModel.Adapter.Factory<V> adapterFactory) {
		return new PluggablePropertyValueModel<>(adapterFactory);
	}

	/**
	 * Construct a modifiable property value model adapter for the specified adapter factory.
	 * @see PluggableModifiablePropertyValueModel
	 */
	public static <V> ModifiablePropertyValueModel<V> modifiablePropertyValueModel(PluggableModifiablePropertyValueModel.Adapter.Factory<V> adapterFactory) {
		return new PluggableModifiablePropertyValueModel<>(adapterFactory);
	}

	/**
	 * Construct a <em>modifiable</em> property value model adapter for the specified
	 * property value model adapter adapter factory and closure.
	 * The specified closure is invoked when the model's value is set.
	 */
	public static <V> ModifiablePropertyValueModel<V> pluggableModifiablePropertyValueModel(AbstractPluggablePropertyValueModel.Adapter.Factory<V, ? extends AbstractPluggablePropertyValueModel.Adapter<V>> factory, Closure<V> setValueClosure) {
		return new PluggableModifiablePropertyValueModel<>(new PluggableModifiablePropertyValueModelAdapter.Factory<>(factory, setValueClosure));
	}


	// ********** double PVMs **********

	/**
	 * Construct a double property value model for the specified
	 * <em>middle</em> property value model.
	 * @see AbstractDoublePropertyValueModel
	 */
	public static <V> PropertyValueModel<V> wrap(PropertyValueModel<? extends PropertyValueModel<? extends V>> valueModel) {
		return new DoublePropertyValueModel<>(valueModel);
	}

	/**
	 * Construct a modifiable double property value model for the specified
	 * <em>middle</em> property value model.
	 * @see AbstractDoublePropertyValueModel
	 */
	public static <V> ModifiablePropertyValueModel<V> wrapModifiable(PropertyValueModel<? extends ModifiablePropertyValueModel<V>> valueModel) {
		return new DoubleModifiablePropertyValueModel<>(valueModel);
	}


	// ********** suppressed constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private PropertyValueModelTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
