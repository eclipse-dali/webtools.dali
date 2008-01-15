/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.EnumType;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * ----------------------------------------------------------------------------â??
 * |            -------------------------------------------------------------â?? |
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
public class EnumTypeComposite extends BaseJpaComposite<IBasicMapping>
{
	/**
	 * Creates a new <code>FetchTypeComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 */
	public EnumTypeComposite(BaseJpaComposite<? extends IBasicMapping> parentComposite,
	                         Composite parent) {

		super(parentComposite, parent);
	}

	private EnumComboViewer<IBasicMapping, EnumType> buildEnumTypeCombo(Composite container) {

		return new EnumComboViewer<IBasicMapping, EnumType>(getSubjectHolder(), container, getWidgetFactory()) {
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
			protected String propertyName() {
				return IBasicMapping.SPECIFIED_ENUMERATED_PROPERTY;
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

		EnumComboViewer<IBasicMapping, EnumType> enumTypeCombo =
			this.buildEnumTypeCombo(container);

		this.buildLabeledComposite(
			container,
			JptUiMappingsMessages.BasicGeneralSection_enumeratedLabel,
			enumTypeCombo.getControl(),
			IJpaHelpContextIds.MAPPING_ENUMERATED
		);

		this.registerSubPane(enumTypeCombo);
	}
}
