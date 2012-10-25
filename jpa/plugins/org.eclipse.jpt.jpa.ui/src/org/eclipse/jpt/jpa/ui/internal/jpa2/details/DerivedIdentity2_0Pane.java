/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.ComboPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MapsIdDerivedIdentityStrategy2_0;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class DerivedIdentity2_0Pane
	extends Pane<DerivedIdentity2_0>
{
	public DerivedIdentity2_0Pane(
			Pane<?> parentPane, 
			PropertyValueModel<? extends DerivedIdentity2_0> subjectHolder, 
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	@Override
	protected Composite addComposite(Composite container) {
		Section section = getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptUiDetailsMessages2_0.DerivedIdentity_title);

		Composite client = this.getWidgetFactory().createComposite(section);
		client.setLayout(new GridLayout(2, false));
		client.setLayoutData(new GridData(GridData.FILL_BOTH));
		section.setClient(client);

		return client;
	}

	@Override
	protected void initializeLayout(Composite container) {
		//null derived identity
		Button nullDerivedIdentityButton = this.addRadioButton(
			container,
			JptUiDetailsMessages2_0.DerivedIdentity_nullDerivedIdentity,
			buildUsesNullDerivedIdentityStrategyHolder(),
			null);
		((GridData) nullDerivedIdentityButton.getLayoutData()).horizontalSpan = 2;

		//id derived identity
		Button button = this.addRadioButton(
			container,
			JptUiDetailsMessages2_0.DerivedIdentity_idDerivedIdentity,
			buildUsesIdDerivedIdentityStrategyHolder(),
			null);
		((GridData) button.getLayoutData()).horizontalSpan = 2;

		//maps id derived identity
		ModifiablePropertyValueModel<Boolean> usesMapsIdModel = buildUsesMapsIdDerivedIdentityStrategyHolder();
		this.addRadioButton(
			container,
			JptUiDetailsMessages2_0.DerivedIdentity_mapsIdDerivedIdentity,
			usesMapsIdModel,
			null);

		this.buildMapsIdValueComboPane(container, usesMapsIdModel);
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildUsesNullDerivedIdentityStrategyHolder() {
		return new PropertyAspectAdapter<DerivedIdentity2_0, Boolean>(
				getSubjectHolder(), DerivedIdentity2_0.PREDOMINANT_DERIVED_IDENTITY_STRATEGY_PROPERTY) {
			
			@Override
			protected Boolean buildValue_() {
				return this.subject.usesNullDerivedIdentityStrategy();
			}
			
			@Override
			protected void setValue_(Boolean value) {
				// radio button - should only have true values here
				if (value) {
					this.subject.setNullDerivedIdentityStrategy();
				}
			}
		};
	}
		
	protected ModifiablePropertyValueModel<Boolean> buildUsesIdDerivedIdentityStrategyHolder() {
		return new PropertyAspectAdapter<DerivedIdentity2_0, Boolean>(
				getSubjectHolder(), DerivedIdentity2_0.PREDOMINANT_DERIVED_IDENTITY_STRATEGY_PROPERTY) {
			
			@Override
			protected Boolean buildValue_() {
				return this.subject.usesIdDerivedIdentityStrategy();
			}
			
			@Override
			protected void setValue_(Boolean value) {
				// radio button - should only have true values here
				if (value) {
					this.subject.setIdDerivedIdentityStrategy();
				}
			}
		};
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildUsesMapsIdDerivedIdentityStrategyHolder() {
		return new PropertyAspectAdapter<DerivedIdentity2_0, Boolean>(
				getSubjectHolder(), DerivedIdentity2_0.PREDOMINANT_DERIVED_IDENTITY_STRATEGY_PROPERTY) {
			
			@Override
			protected Boolean buildValue() {
				return (this.subject == null) ? Boolean.FALSE : this.subject.usesMapsIdDerivedIdentityStrategy();
			}
			
			@Override
			protected void setValue_(Boolean value) {
				// radio button - should only have true values here
				if (value) {
					this.subject.setMapsIdDerivedIdentityStrategy();
				}
			}
		};
	}
	
	protected ComboPane buildMapsIdValueComboPane(Composite parent, PropertyValueModel<Boolean> usesMapsIdModel) {
		return new MapsIdValueCombo(this, buildMapsIdStrategyHolder(), usesMapsIdModel, parent);
	}
	
	protected PropertyValueModel<MapsIdDerivedIdentityStrategy2_0> buildMapsIdStrategyHolder() {
		return new PropertyAspectAdapter<DerivedIdentity2_0, MapsIdDerivedIdentityStrategy2_0>(getSubjectHolder()) {
			@Override
			protected MapsIdDerivedIdentityStrategy2_0 buildValue_() {
				return this.subject.getMapsIdDerivedIdentityStrategy();
			}
		};
	}
	
	
	private class MapsIdValueCombo
		extends ComboPane<MapsIdDerivedIdentityStrategy2_0>
	{
		public MapsIdValueCombo(
				Pane<?> parentPane,
				PropertyValueModel<? extends MapsIdDerivedIdentityStrategy2_0> subjectHolder,
				PropertyValueModel<Boolean> enabledModel,
				Composite parent) {
			
			super(parentPane, subjectHolder, enabledModel, parent);
		}
		
		
		@Override
		protected void addPropertyNames(Collection<String> propertyNames) {
			super.addPropertyNames(propertyNames);
			propertyNames.add(MapsIdDerivedIdentityStrategy2_0.DEFAULT_ID_ATTRIBUTE_NAME_PROPERTY);
			propertyNames.add(MapsIdDerivedIdentityStrategy2_0.SPECIFIED_ID_ATTRIBUTE_NAME_PROPERTY);
		}
		
		@Override
		protected String getValue() {
			return (getSubject() == null) ? null : getSubject().getSpecifiedIdAttributeName();
		}
		
		@Override
		protected void setValue(String value) {
			if (getSubject() != null) getSubject().setSpecifiedIdAttributeName(value);
		}
		
		@Override
		protected boolean usesDefaultValue() {
			return (getSubject() != null) && getSubject().defaultIdAttributeNameIsPossible();
		}
		
		@Override
		protected String getDefaultValue() {
			return (getSubject() == null) ? null : getSubject().getDefaultIdAttributeName();
		}
		
		@Override
		protected Iterable<String> getValues() {
			return (getSubject() == null) ? EmptyIterable.<String>instance() : getSubject().getSortedCandidateIdAttributeNames();
		}
		
		@Override
		protected String buildNullDefaultValueEntry() {
			return buildNonNullDefaultValueEntry(JptUiDetailsMessages2_0.DerivedIdentity_mapsIdUnspecifiedValue);
		}
	}
}
