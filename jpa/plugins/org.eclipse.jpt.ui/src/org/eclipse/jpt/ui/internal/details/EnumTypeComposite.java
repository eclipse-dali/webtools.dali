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

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.EnumType;
import org.eclipse.jpt.core.context.EnumeratedConverter;
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
public class EnumTypeComposite extends Pane<EnumeratedConverter>
{
	/**
	 * Creates a new <code>EnumTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EnumTypeComposite(PropertyValueModel<? extends EnumeratedConverter> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private EnumFormComboViewer<EnumeratedConverter, EnumType> addEnumTypeCombo(Composite container) {

		return new EnumFormComboViewer<EnumeratedConverter, EnumType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EnumeratedConverter.SPECIFIED_ENUM_TYPE_PROPERTY);
			}

			@Override
			protected EnumType[] getChoices() {
				return EnumType.values();
			}

			@Override
			protected EnumType getDefaultValue() {
				return getSubject().getDefaultEnumType();
			}

			@Override
			protected String displayString(EnumType value) {
				return buildDisplayString(
					JptUiDetailsMessages.class,
					EnumTypeComposite.this,
					value
				);
			}

			@Override
			protected EnumType getValue() {
				return getSubject().getSpecifiedEnumType();
			}

			@Override
			protected void setValue(EnumType value) {
				getSubject().setSpecifiedEnumType(value);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		//JpaHelpContextIds.MAPPING_ENUMERATED
		addEnumTypeCombo(container);
		
		new PaneEnabler(buildBooleanHolder(), this);
	}
	
	
	protected PropertyValueModel<Boolean> buildBooleanHolder() {
		return new TransformationPropertyValueModel<EnumeratedConverter, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform(EnumeratedConverter value) {
				if (getSubject() != null && getSubject().getParent().getPersistentAttribute().isVirtual()) {
					return Boolean.FALSE;
				}
				return Boolean.valueOf(value != null);
			}
		};
	}
}
