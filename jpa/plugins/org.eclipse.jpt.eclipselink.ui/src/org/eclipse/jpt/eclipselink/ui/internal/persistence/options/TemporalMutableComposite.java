/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.options;

import org.eclipse.jpt.eclipselink.core.context.persistence.options.Options;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 *  TemporalMutableComposite
 */
public class TemporalMutableComposite extends Pane<Options>
{
	/**
	 * Creates a new <code>TemporalMutableComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public TemporalMutableComposite(
					Pane<? extends Options> parentComposite,
					Composite parent) {

		super(parentComposite, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlOptionsTab_temporalMutableLabel,
			this.buildTemporalMutableHolder(),
			this.buildTemporalMutableStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_OPTIONS
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildTemporalMutableHolder() {
		return new PropertyAspectAdapter<Options, Boolean>(getSubjectHolder(), Options.TEMPORAL_MUTABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getTemporalMutable();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setTemporalMutable(value);
			}
		};
	}

	private PropertyValueModel<String> buildTemporalMutableStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultTemporalMutableHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? EclipseLinkUiMessages.Boolean_True : EclipseLinkUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlOptionsTab_temporalMutableLabelDefault, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlOptionsTab_temporalMutableLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultTemporalMutableHolder() {
		return new PropertyAspectAdapter<Options, Boolean>(
			getSubjectHolder(),
			Options.TEMPORAL_MUTABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getTemporalMutable() != null) {
					return null;
				}
				return this.subject.getDefaultTemporalMutable();
			}
		};
	}
}
