/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import java.util.Collection;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCacheCoordinationType;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkExistenceType;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java.JavaEclipseLinkEntityComposite;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 * This pane shows the caching options.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | x Shared                                                                  |
 * |    CacheTypeComposite                                                     |
 * |    CacheSizeComposite                                                     |
 * |    > Advanced   	                                                       |
 * |    	ExpiryComposite                                                    |
 * |    	AlwaysRefreshComposite                                             |
 * |   		RefreshOnlyIfNewerComposite                                        |
 * |    	DisableHitsComposite                                               |
 * |    	CacheCoordinationComposite                                         |
 * | ExistenceTypeComposite                                                    |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Entity
 * @see EclipseLinkCaching
 * @see JavaEclipseLinkEntityComposite - The parent container
 * @see EclipseLinkCacheTypeComboViewer
 * @see EclipseLinkCacheSizeCombo
 * @see EclipseLinkAlwaysRefreshComposite
 * @see EclipseLinkRefreshOnlyIfNewerComposite
 * @see EclipseLinkDisableHitsComposite
 *
 * @version 2.1
 * @since 2.1
 */
public abstract class EclipseLinkCachingComposite<T extends EclipseLinkCaching> extends Pane<T>
{

	protected EclipseLinkCachingComposite(Pane<?> parentPane,
        PropertyValueModel<T> subjectHolder,
        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected Composite addComposite(Composite container) {
		return this.addSubPane(container, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		//Shared Check box, uncheck this and the rest of the panel is disabled
		TriStateCheckBox sharedCheckBox = addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CACHING_COMPOSITE_SHARED_LABEL,
			buildSpecifiedSharedHolder(),
			buildSharedStringHolder(),
			EclipseLinkHelpContextIds.CACHING_SHARED
		);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		sharedCheckBox.getCheckBox().setLayoutData(gridData);

		final PropertyValueModel<Boolean> sharedCacheEnableModel = buildSharedCacheEnabler();
		
		Label cacheTypeLabel = this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CACHE_TYPE_COMPOSITE_LABEL, sharedCacheEnableModel);
		gridData = new GridData();
		gridData.horizontalIndent = 16;
		cacheTypeLabel.setLayoutData(gridData);
		new EclipseLinkCacheTypeComboViewer(this, container, sharedCacheEnableModel);

		Label cacheSizeLabel = this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CACHE_SIZE_COMPOSITE_SIZE, sharedCacheEnableModel);
		gridData = new GridData();
		gridData.horizontalIndent = 16;
		cacheSizeLabel.setLayoutData(gridData);
		new EclipseLinkCacheSizeCombo(this, container, sharedCacheEnableModel);

		
		// Advanced sub-pane
		final Section advancedSection = this.getWidgetFactory().createSection(container,
				ExpandableComposite.TWISTIE |
				ExpandableComposite.CLIENT_INDENT);
		advancedSection.setText(JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CACHING_COMPOSITE_ADVANCED);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalIndent = 16;
		gridData.horizontalSpan = 2;
		advancedSection.setLayoutData(gridData);

		advancedSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && advancedSection.getClient() == null) {
					advancedSection.setClient(initializeAdvancedPane(advancedSection, sharedCacheEnableModel));
				}
			}
		});
		
		
		initializeExistenceCheckingComposite(container);
	}
	
	protected Composite initializeAdvancedPane(Composite container, PropertyValueModel<Boolean> sharedCacheEnableModel) {
		container = addSubPane(container, 2, 0, 0, 0, 0);

		EclipseLinkExpiryComposite expiryComposite = new EclipseLinkExpiryComposite(this, container, sharedCacheEnableModel);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		expiryComposite.getControl().setLayoutData(gridData);

		TriStateCheckBox alwaysRefreshCheckBox = addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_ALWAYS_REFRESH_COMPOSITE_ALWAYS_REFRESH_LABEL,
			buildAlwaysRefreshHolder(),
			buildAlwaysRefreshStringHolder(),
			sharedCacheEnableModel,
			EclipseLinkHelpContextIds.CACHING_ALWAYS_REFRESH
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		alwaysRefreshCheckBox.getCheckBox().setLayoutData(gridData);

		TriStateCheckBox refreshOnlyIfNewerCheckBox = addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_REFRESH_ONLY_IF_NEWER_COMPOSITE_REFRESH_ONLY_IF_NEWER_LABEL,
			buildRefreshOnlyIfNewerHolder(),
			buildRefreshOnlyIfNewerStringHolder(),
			sharedCacheEnableModel,
			EclipseLinkHelpContextIds.CACHING_REFRESH_ONLY_IF_NEWER
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		refreshOnlyIfNewerCheckBox.getCheckBox().setLayoutData(gridData);

		TriStateCheckBox disableHitsCheckBox = addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_DISABLE_HITS_COMPOSITE_DISABLE_HITS_LABEL,
			buildDisableHitsHolder(),
			buildDisableHitsStringHolder(),
			sharedCacheEnableModel,
			EclipseLinkHelpContextIds.CACHING_DISABLE_HITS
		);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		disableHitsCheckBox.getCheckBox().setLayoutData(gridData);

		this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CACHE_COORDINATION_TYPE_COMPOSITE_LABEL, sharedCacheEnableModel);
		this.addCacheCoordinationTypeCombo(container, sharedCacheEnableModel);
		return container;
	}
	
	protected abstract void initializeExistenceCheckingComposite(Composite container);
	
	private PropertyValueModel<Boolean> buildSharedCacheEnabler() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(
				getSubjectHolder(), 
				EclipseLinkCaching.SPECIFIED_SHARED_PROPERTY, 
				EclipseLinkCaching.DEFAULT_SHARED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isShared());
			}
		};
	}	
	
	private ModifiablePropertyValueModel<Boolean> buildSpecifiedSharedHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(getSubjectHolder(), EclipseLinkCaching.SPECIFIED_SHARED_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedShared();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedShared(value);
			}
		};
	}

	private PropertyValueModel<String> buildSharedStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultSharedHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CACHING_COMPOSITE_SHARED_LABEL_DEFAULT, defaultStringValue);
				}
				return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CACHING_COMPOSITE_SHARED_LABEL;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultSharedHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(
			getSubjectHolder(),
			EclipseLinkCaching.SPECIFIED_SHARED_PROPERTY,
			EclipseLinkCaching.DEFAULT_SHARED_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedShared() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultShared());
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildAlwaysRefreshHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(getSubjectHolder(), EclipseLinkCaching.SPECIFIED_ALWAYS_REFRESH_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedAlwaysRefresh();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedAlwaysRefresh(value);
			}
		};
	}

	private PropertyValueModel<String> buildAlwaysRefreshStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultAlwaysRefreshHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_ALWAYS_REFRESH_COMPOSITE_ALWAYS_REFRESH_DEFAULT, defaultStringValue);
				}
				return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_ALWAYS_REFRESH_COMPOSITE_ALWAYS_REFRESH_LABEL;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultAlwaysRefreshHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(
			getSubjectHolder(),
			EclipseLinkCaching.SPECIFIED_ALWAYS_REFRESH_PROPERTY,
			EclipseLinkCaching.DEFAULT_ALWAYS_REFRESH_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedAlwaysRefresh() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultAlwaysRefresh());
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildRefreshOnlyIfNewerHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(getSubjectHolder(), EclipseLinkCaching.SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedRefreshOnlyIfNewer();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedRefreshOnlyIfNewer(value);
			}
		};
	}

	private PropertyValueModel<String> buildRefreshOnlyIfNewerStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultRefreshOnlyIfNewerHolder()) {

			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_REFRESH_ONLY_IF_NEWER_COMPOSITE_REFRESH_ONLY_IF_NEWER_DEFAULT, defaultStringValue);
				}
				return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_REFRESH_ONLY_IF_NEWER_COMPOSITE_REFRESH_ONLY_IF_NEWER_LABEL;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultRefreshOnlyIfNewerHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Boolean>(
			getSubjectHolder(),
			EclipseLinkCaching.SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY,
			EclipseLinkCaching.DEFAULT_REFRESH_ONLY_IF_NEWER_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedRefreshOnlyIfNewer() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultRefreshOnlyIfNewer());
			}
		};
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
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_DISABLE_HITS_COMPOSITE_DISABLE_HITS_DEFAULT, defaultStringValue);
				}
				return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_DISABLE_HITS_COMPOSITE_DISABLE_HITS_LABEL;
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

	private EnumFormComboViewer<EclipseLinkCaching, EclipseLinkCacheCoordinationType> addCacheCoordinationTypeCombo(Composite container, PropertyValueModel<Boolean> sharedCacheEnableModel) {

		return new EnumFormComboViewer<EclipseLinkCaching, EclipseLinkCacheCoordinationType>(this, container, sharedCacheEnableModel) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkCaching.DEFAULT_COORDINATION_TYPE_PROPERTY);
				propertyNames.add(EclipseLinkCaching.SPECIFIED_COORDINATION_TYPE_PROPERTY);
			}

			@Override
			protected EclipseLinkCacheCoordinationType[] getChoices() {
				return EclipseLinkCacheCoordinationType.values();
			}

			@Override
			protected EclipseLinkCacheCoordinationType getDefaultValue() {
				return getSubject().getDefaultCoordinationType();
			}

			@Override
			protected String displayString(EclipseLinkCacheCoordinationType value) {
				switch (value) {
					case INVALIDATE_CHANGED_OBJECTS :
						return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CACHE_COORDINATION_TYPE_COMPOSITE_INVALIDATE_CHANGED_OBJECTS;
					case SEND_NEW_OBJECTS_WITH_CHANGES :
						return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CACHE_COORDINATION_TYPE_COMPOSITE_SEND_NEW_OBJECTS_WITH_CHANGES;
					case SEND_OBJECT_CHANGES :
						return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CACHE_COORDINATION_TYPE_COMPOSITE_SEND_OBJECT_CHANGES;
					case NONE :
						return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CACHE_COORDINATION_TYPE_COMPOSITE_NONE;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected EclipseLinkCacheCoordinationType getValue() {
				return getSubject().getSpecifiedCoordinationType();
			}

			@Override
			protected void setValue(EclipseLinkCacheCoordinationType value) {
				getSubject().setSpecifiedCoordinationType(value);
			}
			
			@Override
			protected boolean sortChoices() {
				return false;
			}

			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.CACHING_CACHE_COORDINATION_TYPE;
			}
		};
	}

	protected EnumFormComboViewer<EclipseLinkCaching, EclipseLinkExistenceType> addExistenceCheckingTypeCombo(Composite container) {

		return new EnumFormComboViewer<EclipseLinkCaching, EclipseLinkExistenceType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkCaching.DEFAULT_EXISTENCE_TYPE_PROPERTY);
				propertyNames.add(EclipseLinkCaching.SPECIFIED_EXISTENCE_TYPE_PROPERTY);
			}

			@Override
			protected EclipseLinkExistenceType[] getChoices() {
				return EclipseLinkExistenceType.values();
			}

			@Override
			protected EclipseLinkExistenceType getDefaultValue() {
				return getSubject().getDefaultExistenceType();
			}

			@Override
			protected String displayString(EclipseLinkExistenceType value) {
				switch (value) {
					case CHECK_CACHE :
						return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_EXISTENCE_CHECKING_COMPOSITE_CHECK_CACHE;
					case CHECK_DATABASE :
						return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_EXISTENCE_CHECKING_COMPOSITE_CHECK_DATABASE;
					case ASSUME_EXISTENCE :
						return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_EXISTENCE_CHECKING_COMPOSITE_ASSUME_EXISTENCE;
					case ASSUME_NON_EXISTENCE :
						return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_EXISTENCE_CHECKING_COMPOSITE_ASSUME_NON_EXISTENCE;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected EclipseLinkExistenceType getValue() {
				return getSubject().getSpecifiedExistenceType();
			}

			@Override
			protected void setValue(EclipseLinkExistenceType value) {
				getSubject().setSpecifiedExistenceType(value);
			}
			
			@Override
			protected boolean sortChoices() {
				return false;
			}
		};
	}

}