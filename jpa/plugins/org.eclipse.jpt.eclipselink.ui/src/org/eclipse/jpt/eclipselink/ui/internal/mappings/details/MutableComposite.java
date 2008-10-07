/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import java.util.Collection;
import org.eclipse.jpt.eclipselink.core.context.Mutable;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * This composite simply shows a tri-state check box for the Mutable option.
 *
 * @see Mutable
 * @see EclipseLinkBasicMappingComposite - A container of this pane
 *
 * @version 1.0
 * @since 2.0
 */
public class MutableComposite extends FormPane<Mutable>
{
	/**
	 * Creates a new <code>MutableComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public MutableComposite(FormPane<?> parentPane, 
		PropertyValueModel<? extends Mutable> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		addLabeledComposite( 
            container, 
            addCheckBox( 
                 container, 
                 EclipseLinkUiMappingsMessages.MutableComposite_mutableLabel, 
                 buildMutableHolder(), 
                 null 
            ), 
            addMutableValueCombo(container).getControl(), 
            null 
       );
//
//		addTriStateCheckBoxWithDefault(
//			container,
//			EclipseLinkUiMappingsMessages.MutableComposite_mutableLabel,
//			buildMutableHolder(),
//			buildMutableStringHolder(),
//			null
//		);
	}

	private EnumFormComboViewer<Mutable, Boolean> addMutableValueCombo(Composite container) {

		return new EnumFormComboViewer<Mutable, Boolean>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Mutable.DEFAULT_MUTABLE_PROPERTY);
				propertyNames.add(Mutable.SPECIFIED_MUTABLE_PROPERTY);
			}

			@Override
			protected Boolean[] getChoices() {
				return new Boolean[]{Boolean.TRUE, Boolean.FALSE};
			}

			@Override
			protected Boolean getDefaultValue() {
				return getSubject().getDefaultMutable();
			}

			@Override
			protected String displayString(Boolean value) {
				if (value == null) {
					Boolean defaultValue = getDefaultValue();
					if (defaultValue != null) {
						String defaultStringValue = defaultValue.booleanValue() ? JptUiMappingsMessages.Boolean_True :
							JptUiMappingsMessages.Boolean_False;
	
						return NLS.bind(
							EclipseLinkUiMappingsMessages.MutableComposite_mutableLabelDefault,
							defaultStringValue);
					}
					return ""; //$NON-NLS-1$
				}
				return buildDisplayString(
					EclipseLinkUiMappingsMessages.class,
					MutableComposite.this,
					value
				);
			}

			@Override
			protected Boolean getValue() {
				return getSubject().getSpecifiedMutable();
			}

			@Override
			protected void setValue(Boolean value) {
				getSubject().setSpecifiedMutable(value);
			}
		};
	}

	
	private WritablePropertyValueModel<Boolean> buildMutableHolder() {
		return new PropertyAspectAdapter<Mutable, Boolean>(getSubjectHolder(), Mutable.MUTABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.hasMutable());
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setMutable(value.booleanValue());
			}
		};
	}
	
//
//	private WritablePropertyValueModel<Boolean> buildMutableValueHolder() {
//		return new PropertyAspectAdapter<Mutable, Boolean>(getSubjectHolder(), Mutable.SPECIFIED_MUTABLE_PROPERTY) {
//			@Override
//			protected Boolean buildValue_() {
//				return this.subject.getSpecifiedMutable();
//			}
//
//			@Override
//			protected void setValue_(Boolean value) {
//				this.subject.setSpecifiedMutable(value);
//			}
//
//			@Override
//			protected void subjectChanged() {
//				Object oldValue = this.getValue();
//				super.subjectChanged();
//				Object newValue = this.getValue();
//
//				// Make sure the default value is appended to the text
//				if (oldValue == newValue && newValue == null) {
//					this.fireAspectChange(Boolean.TRUE, newValue);
//				}
//			}
//		};
//	}
//
//	private PropertyValueModel<String> buildMutableStringHolder() {
//
//		return new TransformationPropertyValueModel<Boolean, String>(buildMutableHolder()) {
//
//			@Override
//			protected String transform(Boolean value) {
//
//				if ((getSubject() != null) && (value == null)) {
//
//					Boolean defaultValue = getSubject().getDefaultMutable();
//
//					if (defaultValue != null) {
//
//						String defaultStringValue = defaultValue ? JptUiMappingsMessages.Boolean_True :
//						                                           JptUiMappingsMessages.Boolean_False;
//
//						return NLS.bind(
//							EclipseLinkUiMappingsMessages.MutableComposite_mutableLabelDefault,
//							defaultStringValue
//						);
//					}
//				}
//
//				return EclipseLinkUiMappingsMessages.MutableComposite_mutableLabel;
//			}
//		};
//	}

}