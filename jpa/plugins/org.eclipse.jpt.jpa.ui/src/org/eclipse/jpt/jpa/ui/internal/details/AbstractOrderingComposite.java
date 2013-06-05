/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
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
		return new PropertyAspectAdapter<CollectionMapping, T>(getSubjectHolder()) {
			@SuppressWarnings("unchecked")
			@Override
			protected T buildValue_() {
				return (T) this.subject.getOrderable();
			}
		};
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildNoOrderingHolder(PropertyValueModel<T> orderableHolder) {
		return new PropertyAspectAdapter<T, Boolean>(orderableHolder, Orderable.NO_ORDERING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isNoOrdering());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				if (value) {
					this.subject.setNoOrdering();
				}
			}
		};
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildOrderByOrderingHolder(PropertyValueModel<T> orderableHolder) {
		return new PropertyAspectAdapter<T, Boolean>(orderableHolder, Orderable.ORDER_BY_ORDERING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isOrderByOrdering());
			}
			
			@Override
			protected void setValue_(Boolean value) {
				if (value) {
					this.subject.setOrderByOrdering();	
				}
			}
		};
	}
	
	protected PropertyValueModel<OrderBy> buildOrderByHolder(PropertyValueModel<T> orderableHolder) {
		return new TransformationPropertyValueModel<T, OrderBy>(orderableHolder) {
			@Override
			protected OrderBy transform_(T v) {
				return v.getOrderBy();
			}
		};
	}
}
