/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IColumnMapping;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * ----------------------------------------------------------------------------â??
 * |           --------------------------------------------------------------â?? |
 * | Temporal: |                                                           |v| |
 * |           --------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IColumnMapping
 * @see BasicMappingComposite - A container of this widget
 * @see IdMappingComposite - A container of this widget
 * @see VersionMappingComposite - A container of this widget
 *
 * @version 2.0
 * @since 1.0
 */
public class TemporalTypeComposite extends BaseJpaComposite<IColumnMapping> {

	/**
	 * Creates a new <code>TemporalTypeComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 */
	public TemporalTypeComposite(BaseJpaComposite<? extends IColumnMapping> parentComposite,
	                             Composite parent) {

		super(parentComposite, parent);
	}

	private EnumComboViewer<IColumnMapping, TemporalType> buildTemporalCombo(Composite container) {

		return new EnumComboViewer<IColumnMapping, TemporalType>(getSubjectHolder(), container, getWidgetFactory()) {
			@Override
			protected TemporalType[] choices() {
				return TemporalType.values();
			}

			@Override
			protected TemporalType defaultValue() {
				return null;
			}

			@Override
			protected String displayString(TemporalType value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					TemporalTypeComposite.this,
					value.name()
				);
			}

			@Override
			protected TemporalType getValue() {
				return subject().getTemporal();
			}

			@Override
			protected String propertyName() {
				return IColumnMapping.TEMPORAL_PROPERTY;
			}

			@Override
			protected void setValue(TemporalType value) {
				subject().setTemporal(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		EnumComboViewer<IColumnMapping, TemporalType> temporalCombo =
			this.buildTemporalCombo(container);

		this.buildLabeledComposite(
			container,
			JptUiMappingsMessages.BasicGeneralSection_temporalLabel,
			temporalCombo.getControl(),
			IJpaHelpContextIds.MAPPING_TEMPORAL
		);

		this.registerSubPane(temporalCombo);
	}
}
