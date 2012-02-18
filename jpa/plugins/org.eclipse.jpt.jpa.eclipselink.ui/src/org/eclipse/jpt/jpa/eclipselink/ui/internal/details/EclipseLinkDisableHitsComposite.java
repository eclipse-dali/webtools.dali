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

import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * This composite simply shows a tri-state check box for the Disable Hits option.
 * 
 * @see EclipseLinkCaching
 * @see EclipseLinkCachingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkDisableHitsComposite extends Pane<EclipseLinkCaching>
{
	/**
	 * Creates a new <code>OptionalComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkDisableHitsComposite(Pane<? extends EclipseLinkCaching> parentPane,
	                         Composite parent)
	{
		super(parentPane, parent);
	}
	
	@Override
	protected void initializeLayout(Composite container) {

		addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkDisableHitsComposite_disableHitsLabel,
			buildDisableHitsHolder(),
			buildDisableHitsStringHolder(),
			EclipseLinkHelpContextIds.CACHING_DISABLE_HITS
		);
	}
	
	private ModifiablePropertyValueModel<Boolean> buildDisableHitsHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(getSubjectHolder(), EclipseLinkCaching.SPECIFIED_DISABLE_HITS_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedDisableHits();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedDisableHits(value);
			}
		};
	}

	private PropertyValueModel<String> buildDisableHitsStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultDisableHitsHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiDetailsMessages.EclipseLinkDisableHitsComposite_disableHitsDefault, defaultStringValue);
				}
				return EclipseLinkUiDetailsMessages.EclipseLinkDisableHitsComposite_disableHitsLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultDisableHitsHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(
			getSubjectHolder(),
			EclipseLinkCaching.SPECIFIED_DISABLE_HITS_PROPERTY,
			EclipseLinkCaching.DEFAULT_DISABLE_HITS_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedDisableHits() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultDisableHits());
			}
		};
	}
}