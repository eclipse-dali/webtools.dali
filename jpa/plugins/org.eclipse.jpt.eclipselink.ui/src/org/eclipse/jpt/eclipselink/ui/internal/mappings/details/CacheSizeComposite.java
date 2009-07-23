/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
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
 * @see CachingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class CacheSizeComposite extends FormPane<EclipseLinkCaching> {

	/**
	 * Creates a new <code>CacheSizeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public CacheSizeComposite(FormPane<? extends EclipseLinkCaching> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		addSizeCombo(container);
	}
	
	private void addSizeCombo(Composite container) {
		new IntegerCombo<EclipseLinkCaching>(this, container) {
			
			@Override
			protected String getLabelText() {
				return EclipseLinkUiMappingsMessages.CacheSizeComposite_size;
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
			protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
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
		};
	}

}
