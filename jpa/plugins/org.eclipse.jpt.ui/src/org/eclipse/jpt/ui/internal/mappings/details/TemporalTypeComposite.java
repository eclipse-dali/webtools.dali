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
import org.eclipse.jpt.core.internal.context.base.IColumnMapping;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |           --------------------------------------------------------------- |
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
public class TemporalTypeComposite extends AbstractFormPane<IColumnMapping> {

	/**
	 * Creates a new <code>TemporalTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public TemporalTypeComposite(AbstractFormPane<? extends IColumnMapping> parentPane,
	                             Composite parent) {

		super(parentPane, parent);
	}

	private EnumFormComboViewer<IColumnMapping, TemporalType> buildTemporalCombo(Composite container) {

		return new EnumFormComboViewer<IColumnMapping, TemporalType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(IColumnMapping.TEMPORAL_PROPERTY);
			}

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

		EnumFormComboViewer<IColumnMapping, TemporalType> temporalCombo =
			buildTemporalCombo(container);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.BasicGeneralSection_temporalLabel,
			temporalCombo.getControl(),
			IJpaHelpContextIds.MAPPING_TEMPORAL
		);
	}
}
