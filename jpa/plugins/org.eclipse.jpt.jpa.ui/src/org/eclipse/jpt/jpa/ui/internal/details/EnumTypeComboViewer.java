/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.EnumType;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |            -------------------------------------------------------------- |
 * | Enum Type: |                                                          |v| |
 * |            -------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see BasicMappingComposite - A container of this widget
 *
 * @version 2.0
 * @since 1.0
 */
public class EnumTypeComboViewer extends Pane<BaseEnumeratedConverter>
{
	private Combo combo;

	public EnumTypeComboViewer(Pane<?> parentPane, PropertyValueModel<? extends BaseEnumeratedConverter> subjectHolder, Composite parentComposite) {
		super(parentPane, subjectHolder, parentComposite);
	}

	@Override
	protected boolean addsComposite() {
		return false;
	}

	@Override
	public Control getControl() {
		return this.combo;
	}

	@Override
	protected void initializeLayout(Composite container) {		
		this.combo = this.addEnumTypeCombo(container).getControl();
	}

	private EnumFormComboViewer<BaseEnumeratedConverter, EnumType> addEnumTypeCombo(Composite container) {

		return new EnumFormComboViewer<BaseEnumeratedConverter, EnumType>(this, container, this.buildSubjectIsNotNullModel()) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(BaseEnumeratedConverter.SPECIFIED_ENUM_TYPE_PROPERTY);
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
				switch (value) {
					case ORDINAL :
						return JptJpaUiDetailsMessages.ENUM_TYPE_COMPOSITE_ORDINAL;
					case STRING :
						return JptJpaUiDetailsMessages.ENUM_TYPE_COMPOSITE_STRING;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected EnumType getValue() {
				return getSubject().getSpecifiedEnumType();
			}

			@Override
			protected void setValue(EnumType value) {
				getSubject().setSpecifiedEnumType(value);
			}

			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.MAPPING_ENUMERATED;
			}
		};
	}
}
