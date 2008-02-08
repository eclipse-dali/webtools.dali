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
import org.eclipse.jpt.core.internal.context.base.EnumType;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
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
 * @see IBasicMapping
 * @see BasicMappingComposite - A container of this widget
 *
 * @version 2.0
 * @since 1.0
 */
public class EnumTypeComposite extends AbstractFormPane<IBasicMapping>
{
	/**
	 * Creates a new <code>FetchTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EnumTypeComposite(AbstractFormPane<? extends IBasicMapping> parentPane,
	                         Composite parent) {

		super(parentPane, parent);
	}

	private EnumFormComboViewer<IBasicMapping, EnumType> buildEnumTypeCombo(Composite container) {

		return new EnumFormComboViewer<IBasicMapping, EnumType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(IBasicMapping.DEFAULT_ENUMERATED_PROPERTY);
				propertyNames.add(IBasicMapping.SPECIFIED_ENUMERATED_PROPERTY);
			}

			@Override
			protected EnumType[] choices() {
				return EnumType.values();
			}

			@Override
			protected EnumType defaultValue() {
				return subject().getDefaultEnumerated();
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
				return subject().getSpecifiedEnumerated();
			}

			@Override
			protected void setValue(EnumType value) {
				subject().setSpecifiedEnumerated(value);
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
			JptUiMappingsMessages.BasicGeneralSection_enumeratedLabel,
			buildEnumTypeCombo(container),
			IJpaHelpContextIds.MAPPING_ENUMERATED
		);
	}
}
