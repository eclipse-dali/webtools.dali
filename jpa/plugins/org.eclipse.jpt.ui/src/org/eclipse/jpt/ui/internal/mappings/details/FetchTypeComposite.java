/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IFetchable;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |        ------------------------------------------------------------------ |
 * | Fetch: |                                                              |v| |
 * |        ------------------------------------------------------------------ |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IFetchable
 * @see BasicMappingComposite - A container of this widget
 *
 * @version 2.0
 * @since 1.0
 */
public class FetchTypeComposite extends BaseJpaComposite<IFetchable> {

	/**
	 * Creates a new <code>FetchTypeComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 */
	public FetchTypeComposite(BaseJpaComposite<? extends IFetchable> parentComposite,
	                          Composite parent) {

		super(parentComposite, parent);
	}

	private EnumComboViewer<IFetchable, FetchType> buildFetchTypeCombo(Composite container) {

		return new EnumComboViewer<IFetchable, FetchType>(getSubjectHolder(), container, getWidgetFactory()) {
			@Override
			protected FetchType[] choices() {
				return FetchType.values();
			}

			@Override
			protected FetchType defaultValue() {
				return subject().getDefaultFetch();
			}

			@Override
			protected String displayString(FetchType value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					FetchTypeComposite.this,
					value
				);
			}

			@Override
			protected FetchType getValue() {
				return subject().getSpecifiedFetch();
			}

			@Override
			protected String propertyName() {
				return IFetchable.SPECIFIED_FETCH_PROPERTY;
			}

			@Override
			protected void setValue(FetchType value) {
				subject().setSpecifiedFetch(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		EnumComboViewer<IFetchable, FetchType> fetchTypeCombo =
			this.buildFetchTypeCombo(container);

		this.buildLabeledComposite(
			container,
			JptUiMappingsMessages.BasicGeneralSection_fetchLabel,
			fetchTypeCombo.getControl(),
			IJpaHelpContextIds.MAPPING_FETCH_TYPE
		);

		this.registerSubPane(fetchTypeCombo);
	}
}
