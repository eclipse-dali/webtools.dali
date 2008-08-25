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
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.EnumType;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |            -------------------------------------------------------------- |
 * | Enum Type: |                                                          |v| |
 * |            -------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see BasicMapping
 * @see BasicMappingComposite - A container of this widget
 *
 * @version 2.0
 * @since 1.0
 */
public class EnumTypeComposite extends FormPane<BasicMapping>
{
	/**
	 * Creates a new <code>FetchTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EnumTypeComposite(FormPane<? extends BasicMapping> parentPane,
	                         Composite parent) {

		super(parentPane, parent);
	}

	private EnumFormComboViewer<BasicMapping, EnumType> addEnumTypeCombo(Composite container) {

		return new EnumFormComboViewer<BasicMapping, EnumType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(BasicMapping.DEFAULT_ENUMERATED_PROPERTY);
				propertyNames.add(BasicMapping.SPECIFIED_ENUMERATED_PROPERTY);
			}

			@Override
			protected EnumType[] getChoices() {
				return EnumType.values();
			}

			@Override
			protected EnumType getDefaultValue() {
				return getSubject().getDefaultEnumerated();
			}

			@Override
			protected String displayString(EnumType value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					EnumTypeComposite.this,
					value
				);
			}

			@Override
			protected EnumType getValue() {
				return getSubject().getSpecifiedEnumerated();
			}

			@Override
			protected void setValue(EnumType value) {
				getSubject().setSpecifiedEnumerated(value);
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
			JptUiMappingsMessages.BasicGeneralSection_enumeratedLabel,
			addEnumTypeCombo(container),
			JpaHelpContextIds.MAPPING_ENUMERATED
		);
	}
}
