/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.CollectionMapping;
import org.eclipse.jpt.core.context.Orderable;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | - Ordering -------------------------------------------------------------- |
 * | |                                                                       | |
 * | | o None                                                                | |
 * | |                                                                       | |
 * | | o Primary Key                                                         | |
 * | |                                                                       | |
 * | | o Custom                                                              | |
 * | |   ------------------------------------------------------------------- | |
 * | |   | I                                                               | | |
 * | |   ------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see CollectionMapping
 * @see ManyToManyMappingComposite - A container of this pane
 * @see OneToManyMappingComposite - A container of this pane
 *
 * @version 3.0
 * @since 1.0
 */
public abstract class AbstractOrderingComposite extends Pane<CollectionMapping>
{
	/**
	 * Creates a new <code>OrderingComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	protected AbstractOrderingComposite(Pane<? extends CollectionMapping> parentPane,
	                         Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>OrderingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IMultiRelationshipMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected AbstractOrderingComposite(PropertyValueModel<? extends CollectionMapping> subjectHolder,
	                         Composite parent,
	                         WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	
	protected PropertyValueModel<Orderable> buildOrderableHolder() {
		return new PropertyAspectAdapter<CollectionMapping, Orderable>(getSubjectHolder()) {
			@Override
			protected Orderable buildValue_() {
				return this.subject.getOrderable();
			}
		};
	}

	protected WritablePropertyValueModel<Boolean> buildNoOrderingHolder(PropertyValueModel<Orderable> orderableHolder) {
		return new PropertyAspectAdapter<Orderable, Boolean>(orderableHolder, Orderable.NO_ORDERING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isNoOrdering());
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setNoOrdering(value.booleanValue());
			}
		};
	}

	protected WritablePropertyValueModel<Boolean> buildPrimaryKeyOrderingHolder(PropertyValueModel<Orderable> orderableHolder) {
		return new PropertyAspectAdapter<Orderable, Boolean>(orderableHolder, Orderable.PK_ORDERING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isPkOrdering());
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setPkOrdering(value.booleanValue());
			}
		};
	}

	protected WritablePropertyValueModel<Boolean> buildCustomOrderingHolder(PropertyValueModel<Orderable> orderableHolder) {
		return new PropertyAspectAdapter<Orderable, Boolean>(orderableHolder, Orderable.CUSTOM_ORDERING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isCustomOrdering());
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setCustomOrdering(value.booleanValue());
			}
		};
	}

	protected WritablePropertyValueModel<String> buildSpecifiedOrderByHolder(PropertyValueModel<Orderable> orderableHolder) {
		return new PropertyAspectAdapter<Orderable, String>(orderableHolder, Orderable.SPECIFIED_ORDER_BY_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getSpecifiedOrderBy();
			}

			@Override
			protected void setValue_(String value) {
				this.subject.setSpecifiedOrderBy(value);
			}
		};
	}

}