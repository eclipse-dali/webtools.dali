/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.details;

import java.util.Collection;

import org.eclipse.jpt.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.MapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.ui.internal.widgets.ComboPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class DerivedIdentity2_0Pane<T extends DerivedIdentity2_0>
	extends Pane<T>
{
	public DerivedIdentity2_0Pane(
			Pane<?> parentPane, 
			PropertyValueModel<T> subjectHolder, 
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		Composite composite = addCollapsibleSection(
				container,
				JptUiDetailsMessages2_0.DerivedIdentity_title);
		
		addNullDerivedIdentityPane(composite);
		addIdDerivedIdentityPane(composite);
		addMapsIdDerivedIdentityPane(composite);
		addSubPane(container, 5);
	}
	
	protected void addNullDerivedIdentityPane(Composite parent) {
		addRadioButton(
				parent,
				JptUiDetailsMessages2_0.DerivedIdentity_nullDerivedIdentity,
				buildUsesNullDerivedIdentityStrategyHolder(),
				null);
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
		addRadioButton(
				parent,
				JptUiDetailsMessages2_0.DerivedIdentity_idDerivedIdentity,
				buildUsesIdDerivedIdentityStrategyHolder(),
				null);
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
		addPaneForAlignment(buildMapsIdValueComboPane(parent));
	}
	
	protected ComboPane buildMapsIdValueComboPane(Composite parent) {
		return new MapsIdValueComboPane(this, buildMapsIdStrategyHolder(), parent);
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
				Composite parent) {
			
			super(parentPane, subjectHolder, parent);
		}
		
		
		@Override
		protected void initializeLayout(Composite container) {
			container = addContainer(container);
			((GridLayout) container.getLayout()).numColumns = 2;
			
			WritablePropertyValueModel<Boolean> usesMapsIdHolder = 
					buildUsesMapsIdDerivedIdentityStrategyHolder();
			
			addRadioButton(
					container,
					JptUiDetailsMessages2_0.DerivedIdentity_mapsIdDerivedIdentity,
					usesMapsIdHolder,
					null);
			
			super.initializeLayout(container);
			
			this.comboBox.setEnabled(false);
			SWTTools.controlEnabledState(usesMapsIdHolder, this.comboBox);
		}
		
		protected WritablePropertyValueModel<Boolean> buildUsesMapsIdDerivedIdentityStrategyHolder() {
			return new PropertyAspectAdapter<DerivedIdentity2_0, Boolean>(
					buildDerivedIdentityHolder(), DerivedIdentity2_0.PREDOMINANT_DERIVED_IDENTITY_STRATEGY_PROPERTY) {
				
				@Override
				protected Boolean buildValue_() {
					return this.subject.usesMapsIdDerivedIdentityStrategy();
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
		
		protected PropertyValueModel<DerivedIdentity2_0> buildDerivedIdentityHolder() {
			return new PropertyAspectAdapter<MapsIdDerivedIdentityStrategy2_0, DerivedIdentity2_0>(getSubjectHolder()) {
				@Override
				protected DerivedIdentity2_0 buildValue_() {
					return this.subject.getDerivedIdentity();
				}
			};
		}
		
		@Override
		protected void addPropertyNames(Collection<String> propertyNames) {
			super.addPropertyNames(propertyNames);
			propertyNames.add(MapsIdDerivedIdentityStrategy2_0.DEFAULT_VALUE_PROPERTY);
			propertyNames.add(MapsIdDerivedIdentityStrategy2_0.SPECIFIED_VALUE_PROPERTY);
		}
		
		@Override
		protected String getValue() {
			return (getSubject() == null) ? null : getSubject().getSpecifiedValue();
		}
		
		@Override
		protected void setValue(String value) {
			if (getSubject() != null) getSubject().setSpecifiedValue(value);
		}
		
		@Override
		protected String getDefaultValue() {
			return (getSubject() == null) ? null : getSubject().getDefaultValue();
		}
		
		@Override
		protected Iterable<String> getValues() {
			return (getSubject() == null) ? EmptyIterable.<String>instance() : getSubject().getSortedValueChoices();
		}
		
		@Override
		protected String buildNullDefaultValueEntry() {
			boolean useNullString = (getSubject() == null) ? false : getSubject().usesDefaultValue();
			return (useNullString) ? 
					buildNonNullDefaultValueEntry(JptUiDetailsMessages2_0.DerivedIdentity_mapsIdUnspecifiedValue)
					: "";
		}
	}
}
