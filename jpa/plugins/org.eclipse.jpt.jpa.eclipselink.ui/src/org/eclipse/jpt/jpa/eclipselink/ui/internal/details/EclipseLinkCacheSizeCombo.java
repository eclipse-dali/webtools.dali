/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.swt.widgets.Composite;

/**
 * Here is the layout of this pane:
 * <pre>
 * ----------------------------------------------------------------------------
 * |                      ---------------                                      |
 * |   Size:              | I         |I|  Default (XXX)                       |
 * |                      ---------------                                      |
 * ----------------------------------------------------------------------------</pre>
 *
 * @see EclipseLinkCaching
 * @see EclipseLinkCachingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkCacheSizeCombo extends IntegerCombo<EclipseLinkCaching> {

	/**
	 * Creates a new <code>CacheSizeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkCacheSizeCombo(Pane<? extends EclipseLinkCaching> parentPane,
	                          Composite parent,
	                          PropertyValueModel<Boolean> enabledModel) {

		super(parentPane, parent, enabledModel);
	}

	@Override
	protected String getHelpId() {
		return null;//JpaHelpContextIds.MAPPING_COLUMN_LENGTH;
	}

	@Override
	protected PropertyValueModel<Integer> buildDefaultHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Integer>(getSubjectHolder(), EclipseLinkCaching.DEFAULT_SIZE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return Integer.valueOf(this.subject.getDefaultSize());
			}
		};
	}

	@Override
	protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Integer>(getSubjectHolder(), EclipseLinkCaching.SPECIFIED_SIZE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return this.subject.getSpecifiedSize();
			}

			@Override
			protected void setValue_(Integer value) {
				this.subject.setSpecifiedSize(value);
			}
		};
	}
}
