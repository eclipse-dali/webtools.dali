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
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTrackingType;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here is the layout of this pane:
 * <pre>
 * -------------------------------------------------------------------------
 * |       			  		---------------------------------------------- |
 * | Change Tracking :      |                                          |v| |
 * |       					---------------------------------------------- |
 * -------------------------------------------------------------------------</pre>
 *
 * @see EclipseLinkChangeTracking
 *
 * @version 2.1
 * @since 2.1
 */
public class ChangeTrackingComposite extends FormPane<EclipseLinkChangeTracking> {

	/**
	 * Creates a new <code>ChangeTrackingComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public ChangeTrackingComposite(FormPane<?> parentPane, 
								PropertyValueModel<? extends EclipseLinkChangeTracking> subjectHolder,
								Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		addLabeledComposite( 
            container,
            addLabel( 
                 container, 
                 EclipseLinkUiMappingsMessages.ChangeTrackingComposite_label), 
            addChangeTrackingTypeCombo(container).getControl(), 
            null 
       );
	}

	private EnumFormComboViewer<EclipseLinkChangeTracking, EclipseLinkChangeTrackingType> addChangeTrackingTypeCombo(Composite container) {

		return new EnumFormComboViewer<EclipseLinkChangeTracking, EclipseLinkChangeTrackingType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkChangeTracking.DEFAULT_TYPE_PROPERTY);
				propertyNames.add(EclipseLinkChangeTracking.SPECIFIED_TYPE_PROPERTY);
			}

			@Override
			protected EclipseLinkChangeTrackingType[] getChoices() {
				return EclipseLinkChangeTrackingType.values();
			}

			@Override
			protected EclipseLinkChangeTrackingType getDefaultValue() {
				return getSubject().getDefaultType();
			}

			@Override
			protected String displayString(EclipseLinkChangeTrackingType value) {
				return buildDisplayString(
					EclipseLinkUiMappingsMessages.class,
					ChangeTrackingComposite.this,
					value
				);
			}

			@Override
			protected EclipseLinkChangeTrackingType getValue() {
				return getSubject().getSpecifiedType();
			}

			@Override
			protected void setValue(EclipseLinkChangeTrackingType value) {
				getSubject().setSpecifiedType(value);
			}
			
			@Override
			protected boolean sortChoices() {
				return false;
			}
		};
	}
}
