/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * WeavingLazyComposite
 */
public class WeavingLazyComposite extends Pane<Customization>
{
	/**
	 * Creates a new <code>WeavingLazyComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public WeavingLazyComposite(
					Pane<? extends Customization> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlCustomizationTab_weavingLazyLabel,
			this.buildWeavingLazyHolder(),
			this.buildWeavingLazyStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildWeavingLazyHolder() {
		return new PropertyAspectAdapter<Customization, Boolean>(getSubjectHolder(), Customization.WEAVING_LAZY_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getWeavingLazy();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setWeavingLazy(value);
			}
		};
	}

	private PropertyValueModel<String> buildWeavingLazyStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultWeavingLazyHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlCustomizationTab_weavingLazyLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlCustomizationTab_weavingLazyLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultWeavingLazyHolder() {
		return new PropertyAspectAdapter<Customization, Boolean>(
			getSubjectHolder(),
			Customization.WEAVING_LAZY_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getWeavingLazy() != null) {
					return null;
				}
				return this.subject.getDefaultWeavingLazy();
			}
		};
	}
}
