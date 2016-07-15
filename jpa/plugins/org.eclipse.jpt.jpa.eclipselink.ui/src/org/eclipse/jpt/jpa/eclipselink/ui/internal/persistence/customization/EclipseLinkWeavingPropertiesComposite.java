/*******************************************************************************
 * Copyright (c) 2010, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCustomization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkWeaving;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.BooleanStringTransformer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 *  WeavingPropertiesComposite
 */
public class EclipseLinkWeavingPropertiesComposite
	extends Pane<EclipseLinkCustomization>
{
	public EclipseLinkWeavingPropertiesComposite(Pane<? extends EclipseLinkCustomization> subjectHolder,
	                                       Composite container) {

		super(subjectHolder, container);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return this.addSubPane(parent, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Weaving
		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_LABEL);
		this.addWeavingCombo(container);

		// Weaving Lazy
		TriStateCheckBox weavingLazyCheckBox = this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_LAZY_LABEL,
			this.buildWeavingLazyModel(),
			this.buildWeavingLazyStringModel(),
			EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION
		);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		weavingLazyCheckBox.getCheckBox().setLayoutData(gridData);

		// Weaving Fetch Groups
		TriStateCheckBox weavingFetchGroupsCheckBox = this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_FETCH_GROUPS_LABEL,
			this.buildWeavingFetchGroupsModel(),
			this.buildWeavingFetchGroupsStringModel(),
			EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		weavingFetchGroupsCheckBox.getCheckBox().setLayoutData(gridData);

		// Weaving Internal
		TriStateCheckBox weavingInternalCheckBox = this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_INTERNAL_LABEL,
			this.buildWeavingInternalModel(),
			this.buildWeavingInternalStringModel(),
			EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		weavingInternalCheckBox.getCheckBox().setLayoutData(gridData);

		// Weaving Eager
		TriStateCheckBox weavingEagerCheckBox = this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_EAGER_LABEL,
			this.buildWeavingEagerModel(),
			this.buildWeavingEagerStringModel(),
			EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		weavingEagerCheckBox.getCheckBox().setLayoutData(gridData);

		// Weaving Change Tracking
		TriStateCheckBox weavingChangeTrackingCheckBox = this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_CHANGE_TRACKING_LABEL,
			this.buildWeavingChangeTrackingModel(),
			this.buildWeavingChangeTrackingStringModel(),
			EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		weavingChangeTrackingCheckBox.getCheckBox().setLayoutData(gridData);
	}

	// ********* weaving **********

	private EnumFormComboViewer<EclipseLinkCustomization, EclipseLinkWeaving> addWeavingCombo(Composite container) {
		return new EnumFormComboViewer<EclipseLinkCustomization, EclipseLinkWeaving>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkCustomization.WEAVING_PROPERTY);
			}

			@Override
			protected EclipseLinkWeaving[] getChoices() {
				return EclipseLinkWeaving.values();
			}

			@Override
			protected EclipseLinkWeaving getDefaultValue() {
				return getSubject().getDefaultWeaving();
			}

			@Override
			protected String displayString(EclipseLinkWeaving value) {
				switch (value) {
					case true_ :
						return JptJpaEclipseLinkUiMessages.WEAVING_COMPOSITE_TRUE_;
					case false_ :
						return JptJpaEclipseLinkUiMessages.WEAVING_COMPOSITE_FALSE_;
					case static_ :
						return JptJpaEclipseLinkUiMessages.WEAVING_COMPOSITE_STATIC_;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected EclipseLinkWeaving getValue() {
				return getSubject().getWeaving();
			}

			@Override
			protected void setValue(EclipseLinkWeaving value) {
				getSubject().setWeaving(value);
			}

			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.PERSISTENCE_CUSTOMIZATION;
			}
		};
	}


	// ********* weaving lazy **********
	
	private ModifiablePropertyValueModel<Boolean> buildWeavingLazyModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCustomization, Boolean>(getSubjectHolder(), EclipseLinkCustomization.WEAVING_LAZY_PROPERTY) {
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

	private PropertyValueModel<String> buildWeavingLazyStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultWeavingLazyModel(), WEAVING_LAZY_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> WEAVING_LAZY_TRANSFORMER = new BooleanStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_LAZY_LABEL_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_LAZY_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultWeavingLazyModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCustomization, Boolean>(
			getSubjectHolder(),
			EclipseLinkCustomization.WEAVING_LAZY_PROPERTY)
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


	// ********* weaving fetch groups **********

	private ModifiablePropertyValueModel<Boolean> buildWeavingFetchGroupsModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCustomization, Boolean>(getSubjectHolder(), EclipseLinkCustomization.WEAVING_FETCH_GROUPS_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getWeavingFetchGroups();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setWeavingFetchGroups(value);
			}
		};
	}

	private PropertyValueModel<String> buildWeavingFetchGroupsStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultWeavingFetchGroupsModel(), WEAVING_FETCH_GROUPS_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> WEAVING_FETCH_GROUPS_TRANSFORMER = new BooleanStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_FETCH_GROUPS_LABEL_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_FETCH_GROUPS_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultWeavingFetchGroupsModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCustomization, Boolean>(
			getSubjectHolder(),
			EclipseLinkCustomization.WEAVING_FETCH_GROUPS_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getWeavingFetchGroups() != null) {
					return null;
				}
				return this.subject.getDefaultWeavingFetchGroups();
			}
		};
	}


	// ********* weaving internal **********
	
	private ModifiablePropertyValueModel<Boolean> buildWeavingInternalModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCustomization, Boolean>(getSubjectHolder(), EclipseLinkCustomization.WEAVING_INTERNAL_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getWeavingInternal();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setWeavingInternal(value);
			}
		};
	}

	private PropertyValueModel<String> buildWeavingInternalStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultWeavingInternalModel(), WEAVING_INTERNAL_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> WEAVING_INTERNAL_TRANSFORMER = new BooleanStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_INTERNAL_LABEL_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_INTERNAL_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultWeavingInternalModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCustomization, Boolean>(
			getSubjectHolder(),
			EclipseLinkCustomization.WEAVING_INTERNAL_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getWeavingInternal() != null) {
					return null;
				}
				return this.subject.getDefaultWeavingInternal();
			}
		};
	}


	// ********* weaving eager **********
	
	private ModifiablePropertyValueModel<Boolean> buildWeavingEagerModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCustomization, Boolean>(getSubjectHolder(), EclipseLinkCustomization.WEAVING_EAGER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getWeavingEager();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setWeavingEager(value);
			}
		};
	}

	private PropertyValueModel<String> buildWeavingEagerStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultWeavingEagerModel(), WEAVING_EAGER_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> WEAVING_EAGER_TRANSFORMER = new BooleanStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_EAGER_LABEL_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_EAGER_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultWeavingEagerModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCustomization, Boolean>(
			getSubjectHolder(),
			EclipseLinkCustomization.WEAVING_EAGER_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getWeavingEager() != null) {
					return null;
				}
				return this.subject.getDefaultWeavingEager();
			}
		};
	}


	// ********* weaving change tracking **********
	
	private ModifiablePropertyValueModel<Boolean> buildWeavingChangeTrackingModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCustomization, Boolean>(getSubjectHolder(), EclipseLinkCustomization.WEAVING_CHANGE_TRACKING_PROPERTY) {
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

	private PropertyValueModel<String> buildWeavingChangeTrackingStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultWeavingChangeTrackingModel(), WEAVING_CHANGE_TRACKING_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> WEAVING_CHANGE_TRACKING_TRANSFORMER = new BooleanStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_CHANGE_TRACKING_LABEL_DEFAULT,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CUSTOMIZATION_TAB_WEAVING_CHANGE_TRACKING_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultWeavingChangeTrackingModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCustomization, Boolean>(
			getSubjectHolder(),
			EclipseLinkCustomization.WEAVING_CHANGE_TRACKING_PROPERTY)
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
