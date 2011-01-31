/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.Collection;

import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.FetchableMapping;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
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
 * @see FetchableMapping
 * @see BasicMappingComposite - A container of this widget
 * @see ManyToManyMappingComposite - A container of this widget
 * @see ManyToOneMappingComposite - A container of this widget
 * @see OneToManyMappingComposite - A container of this widget
 * @see OneToOneMappingComposite - A container of this widget
 *
 * @version 2.0
 * @since 1.0
 */
public class FetchTypeComposite extends Pane<FetchableMapping> {

	/**
	 * Creates a new <code>FetchTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public FetchTypeComposite(Pane<? extends FetchableMapping> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}

	private EnumFormComboViewer<FetchableMapping, FetchType> addFetchTypeCombo(Composite container) {

		return new EnumFormComboViewer<FetchableMapping, FetchType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(FetchableMapping.DEFAULT_FETCH_PROPERTY);
				propertyNames.add(FetchableMapping.SPECIFIED_FETCH_PROPERTY);
			}

			@Override
			protected FetchType[] getChoices() {
				return FetchType.values();
			}

			@Override
			protected FetchType getDefaultValue() {
				return getSubject().getDefaultFetch();
			}

			@Override
			protected String displayString(FetchType value) {
				return buildDisplayString(
					JptUiDetailsMessages.class,
					FetchTypeComposite.this,
					value
				);
			}

			@Override
			protected FetchType getValue() {
				return getSubject().getSpecifiedFetch();
			}

			@Override
			protected void setValue(FetchType value) {
				getSubject().setSpecifiedFetch(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		addLabeledComposite(
			container,
			JptUiDetailsMessages.BasicGeneralSection_fetchLabel,
			addFetchTypeCombo(container),
			JpaHelpContextIds.MAPPING_FETCH_TYPE
		);
	}
}
