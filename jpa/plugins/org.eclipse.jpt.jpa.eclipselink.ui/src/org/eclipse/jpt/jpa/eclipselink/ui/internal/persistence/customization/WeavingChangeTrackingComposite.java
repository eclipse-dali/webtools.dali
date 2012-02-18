/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization;

import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Customization;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * WeavingChangeTrackingComposite
 */
public class WeavingChangeTrackingComposite extends Pane<Customization>
{
	/**
	 * Creates a new <code>WeavingChangeTrackingComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public WeavingChangeTrackingComposite(
					Pane<? extends Customization> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlCustomizationTab_weavingChangeTrackingLabel,
			this.buildWeavingChangeTrackingHolder(),
			this.buildWeavingChangeTrackingStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION
		);
	}
	
	private ModifiablePropertyValueModel<Boolean> buildWeavingChangeTrackingHolder() {
		return new PropertyAspectAdapter<Customization, Boolean>(getSubjectHolder(), Customization.WEAVING_CHANGE_TRACKING_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getWeavingChangeTracking();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setWeavingChangeTracking(value);
			}
		};
	}

	private PropertyValueModel<String> buildWeavingChangeTrackingStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultWeavingChangeTrackingHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlCustomizationTab_weavingChangeTrackingLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlCustomizationTab_weavingChangeTrackingLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultWeavingChangeTrackingHolder() {
		return new PropertyAspectAdapter<Customization, Boolean>(
			getSubjectHolder(),
			Customization.WEAVING_CHANGE_TRACKING_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getWeavingChangeTracking() != null) {
					return null;
				}
				return this.subject.getDefaultWeavingChangeTracking();
			}
		};
	}
}
