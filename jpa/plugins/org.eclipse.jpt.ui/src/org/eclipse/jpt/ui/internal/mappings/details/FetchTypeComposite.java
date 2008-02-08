/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Collection;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IFetchable;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
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
 * @see ManyToManyMappingComposite - A container of this widget
 * @see ManyToOneMappingComposite - A container of this widget
 * @see OneToManyMappingComposite - A container of this widget
 * @see OneToOneMappingComposite - A container of this widget
 *
 * @version 2.0
 * @since 1.0
 */
public class FetchTypeComposite extends AbstractFormPane<IFetchable> {

	/**
	 * Creates a new <code>FetchTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public FetchTypeComposite(AbstractFormPane<? extends IFetchable> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}

	private EnumFormComboViewer<IFetchable, FetchType> buildFetchTypeCombo(Composite container) {

		return new EnumFormComboViewer<IFetchable, FetchType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(IFetchable.DEFAULT_FETCH_PROPERTY);
				propertyNames.add(IFetchable.SPECIFIED_FETCH_PROPERTY);
			}

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

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.BasicGeneralSection_fetchLabel,
			buildFetchTypeCombo(container),
			IJpaHelpContextIds.MAPPING_FETCH_TYPE
		);
	}
}
