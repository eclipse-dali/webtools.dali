/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MapsIdDerivedIdentityStrategy2_0;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

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
	protected void initializeLayout(Composite container) {
		Composite composite = addCollapsibleSection(
				container,
				JptUiDetailsMessages2_0.DerivedIdentity_title);
		((GridLayout) composite.getLayout()).numColumns = 2;
		
		addNullDerivedIdentityPane(composite);
		addIdDerivedIdentityPane(composite);
		addMapsIdDerivedIdentityPane(composite);
	}
	
	protected void addNullDerivedIdentityPane(Composite parent) {
		Button button = addRadioButton(
				parent,
				JptUiDetailsMessages2_0.DerivedIdentity_nullDerivedIdentity,
				buildUsesNullDerivedIdentityStrategyHolder(),
				null);
		((GridData) button.getLayoutData()).horizontalSpan = 2;
	}
	
	protected WritablePropertyValueModel<Boolean> buildUsesNullDerivedIdentityStrategyHolder() {
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
	
	protected void addIdDerivedIdentityPane(Composite parent) {
		Button button = addRadioButton(
				parent,
				JptUiDetailsMessages2_0.DerivedIdentity_idDerivedIdentity,
				buildUsesIdDerivedIdentityStrategyHolder(),
				null);
		((GridData) button.getLayoutData()).horizontalSpan = 2;
	}
	
	protected WritablePropertyValueModel<Boolean> buildUsesIdDerivedIdentityStrategyHolder() {
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
	
	protected void addMapsIdDerivedIdentityPane(Composite parent) {
		WritablePropertyValueModel<Boolean> usesMapsIdModel = buildUsesMapsIdDerivedIdentityStrategyHolder();
		addRadioButton(
			parent,
			JptUiDetailsMessages2_0.DerivedIdentity_mapsIdDerivedIdentity,
			usesMapsIdModel,
			null);

		buildMapsIdValueComboPane(parent, usesMapsIdModel);
	}
	
	protected WritablePropertyValueModel<Boolean> buildUsesMapsIdDerivedIdentityStrategyHolder() {
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
		return new MapsIdValueComboPane(this, buildMapsIdStrategyHolder(), parent, usesMapsIdModel);
	}
	
	protected PropertyValueModel<MapsIdDerivedIdentityStrategy2_0> buildMapsIdStrategyHolder() {
		return new PropertyAspectAdapter<DerivedIdentity2_0, MapsIdDerivedIdentityStrategy2_0>(getSubjectHolder()) {
			@Override
			protected MapsIdDerivedIdentityStrategy2_0 buildValue_() {
				return this.subject.getMapsIdDerivedIdentityStrategy();
			}
		};
	}
	
	
	private class MapsIdValueComboPane
		extends ComboPane<MapsIdDerivedIdentityStrategy2_0>
	{
		public MapsIdValueComboPane(
				Pane<?> parentPane,
				PropertyValueModel<? extends MapsIdDerivedIdentityStrategy2_0> subjectHolder,
				Composite parent,
				PropertyValueModel<Boolean> enabledModel) {
			
			super(parentPane, subjectHolder, parent, enabledModel);
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
