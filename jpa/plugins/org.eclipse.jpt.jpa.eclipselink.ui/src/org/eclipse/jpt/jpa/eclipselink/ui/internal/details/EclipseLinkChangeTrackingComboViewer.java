/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkChangeTrackingType;
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
public class EclipseLinkChangeTrackingComboViewer extends EnumFormComboViewer<EclipseLinkChangeTracking, EclipseLinkChangeTrackingType> {

	/**
	 * Creates a new <code>ChangeTrackingComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkChangeTrackingComboViewer(Pane<?> parentPane, 
								PropertyValueModel<? extends EclipseLinkChangeTracking> subjectHolder,
								Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

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
		switch (value) {
			case ATTRIBUTE :
				return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CHANGE_TRACKING_COMPOSITE_ATTRIBUTE;
			case AUTO :
				return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CHANGE_TRACKING_COMPOSITE_AUTO;
			case DEFERRED :
				return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CHANGE_TRACKING_COMPOSITE_DEFERRED;
			case OBJECT :
				return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CHANGE_TRACKING_COMPOSITE_OBJECT;
			default :
				throw new IllegalStateException();
		}
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
}
