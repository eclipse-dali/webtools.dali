/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.CollectionMapping;
import org.eclipse.jpt.jpa.core.context.OrderBy;
import org.eclipse.jpt.jpa.core.context.Orderable;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractOrderingComposite<T extends Orderable>
		extends Pane<CollectionMapping> {
	
	protected AbstractOrderingComposite(Pane<? extends CollectionMapping> parentPane, Composite parentComposite) {
		super(parentPane, parentComposite);
	}
	
	protected PropertyValueModel<T> buildOrderableModel() {
		return new PropertyAspectAdapterXXXX<CollectionMapping, T>(getSubjectHolder()) {
			@SuppressWarnings("unchecked")
			@Override
			protected T buildValue_() {
				return (T) this.subject.getOrderable();
			}
		};
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildNoOrderingModel(PropertyValueModel<T> orderableModel) {
		return new PropertyAspectAdapterXXXX<T, Boolean>(orderableModel, Orderable.NO_ORDERING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isNoOrdering());
			}
			
			@Override
			protected void setValue_(Boolean b) {
				if (b.booleanValue()) {
					this.subject.setNoOrdering();
				}
			}
		};
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildOrderByOrderingModel(PropertyValueModel<T> orderableModel) {
		return new PropertyAspectAdapterXXXX<T, Boolean>(orderableModel, Orderable.ORDER_BY_ORDERING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isOrderByOrdering());
			}
			
			@Override
			protected void setValue_(Boolean b) {
				if (b.booleanValue()) {
					this.subject.setOrderByOrdering();	
				}
			}
		};
	}
	
	protected PropertyValueModel<OrderBy> buildOrderByModel(PropertyValueModel<T> orderableModel) {
		return PropertyValueModelTools.transform(orderableModel, Orderable.ORDER_BY_TRANSFORMER);
	}
}
