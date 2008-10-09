/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import java.util.Collection;
import org.eclipse.jpt.eclipselink.core.context.ChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.ChangeTrackingType;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here is the layout of this pane:
 * <pre>
 * -------------------------------------------------------------------------
 * |       			  		---------------------------------------------- |
 * | x Change Tracking :    |                                          |v| |
 * |       					---------------------------------------------- |
 * -------------------------------------------------------------------------</pre>
 *
 * @see ChangeTracking
 *
 * @version 2.1
 * @since 2.1
 */
public class ChangeTrackingComposite extends FormPane<ChangeTracking> {

	/**
	 * Creates a new <code>ChangeTrackingComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public ChangeTrackingComposite(FormPane<?> parentPane, 
								PropertyValueModel<? extends ChangeTracking> subjectHolder,
								Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		addLabeledComposite( 
            container, 
            addCheckBox( 
                 container, 
                 EclipseLinkUiMappingsMessages.ChangeTrackingComposite_label, 
                 buildChangeTrackingHolder(), 
                 null 
            ), 
            addChangeTrackingTypeCombo(container).getControl(), 
            null 
       );
	}

	private EnumFormComboViewer<ChangeTracking, ChangeTrackingType> addChangeTrackingTypeCombo(Composite container) {

		return new EnumFormComboViewer<ChangeTracking, ChangeTrackingType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(ChangeTracking.DEFAULT_CHANGE_TRACKING_TYPE_PROPERTY);
				propertyNames.add(ChangeTracking.SPECIFIED_CHANGE_TRACKING_TYPE_PROPERTY);
			}

			@Override
			protected ChangeTrackingType[] getChoices() {
				return ChangeTrackingType.values();
			}

			@Override
			protected ChangeTrackingType getDefaultValue() {
				return getSubject().getDefaultChangeTrackingType();
			}

			@Override
			protected String displayString(ChangeTrackingType value) {
				return buildDisplayString(
					EclipseLinkUiMappingsMessages.class,
					ChangeTrackingComposite.this,
					value
				);
			}

			@Override
			protected ChangeTrackingType getValue() {
				return getSubject().getSpecifiedChangeTrackingType();
			}

			@Override
			protected void setValue(ChangeTrackingType value) {
				getSubject().setSpecifiedChangeTrackingType(value);
			}
			
			@Override
			protected boolean sortChoices() {
				return false;
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildChangeTrackingHolder() {
		return new PropertyAspectAdapter<ChangeTracking, Boolean>(getSubjectHolder(), ChangeTracking.CHANGE_TRACKING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.hasChangeTracking());
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setChangeTracking(value.booleanValue());
			}
		};
	}

}
