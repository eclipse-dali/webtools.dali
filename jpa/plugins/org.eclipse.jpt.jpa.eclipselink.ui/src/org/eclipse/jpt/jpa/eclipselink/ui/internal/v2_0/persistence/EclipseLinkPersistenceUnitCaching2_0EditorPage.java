/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence;

import java.util.Collection;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.options.SharedCacheMode;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.CacheType;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Caching;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.caching.EclipseLinkPersistenceUnitCachingEditorPage;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.JptUiPersistence2_0Messages;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class EclipseLinkPersistenceUnitCaching2_0EditorPage
	extends EclipseLinkPersistenceUnitCachingEditorPage<Caching>
{
	public EclipseLinkPersistenceUnitCaching2_0EditorPage(
			PropertyValueModel<Caching> subjectModel,
			Composite parentComposite,
            WidgetFactory widgetFactory,
            ResourceManager resourceManager) {
		super(subjectModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(EclipseLinkUiMessages.PersistenceXmlCachingTab_sectionTitle);
		section.setDescription(EclipseLinkUiMessages.PersistenceXmlCachingTab_sectionDescription);

		Composite client = this.getWidgetFactory().createComposite(section);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 0;
		layout.marginLeft   = 0;
		layout.marginBottom = 0;
		layout.marginRight  = 0;
		client.setLayout(layout);
		client.setLayoutData(new GridData(GridData.FILL_BOTH));
		section.setClient(client);

		PropertyValueModel<PersistenceUnit2_0> persistenceUnitModel = this.buildPersistenceUnit2_0Model();

		// SharedCacheMode
		this.addLabel(client, JptUiPersistence2_0Messages.SharedCacheModeComposite_sharedCacheModeLabel);		
		this.addSharedCacheModeCombo(client, persistenceUnitModel);

		// Defaults
		// Default Cache Type
		Label cacheTypeLabel = this.addLabel(client, EclipseLinkUiMessages.PersistenceXmlCachingTab_defaultCacheTypeLabel);
		Combo cacheTypeCombo = this.buildDefaultCacheTypeCombo(client).getControl();

		// Default Cache Size
		Label cacheSizeLabel = this.addLabel(client, EclipseLinkUiMessages.DefaultCacheSizeComposite_defaultCacheSize);
		Combo cacheSizeCombo = this.addDefaultCacheSizeCombo(client).getControl();


		// Flush Clear Cache
		Label flushClearCacheLabel = this.addLabel(client, EclipseLinkUiMessages.PersistenceXmlCachingTab_FlushClearCacheLabel);
		Combo flushClearCacheCombo = this.addFlushClearCacheCombo(client).getControl();

		this.controlEnabledState(
			this.buildSharedCacheModeEnabledModel(persistenceUnitModel),
			cacheTypeLabel,
			cacheTypeCombo,
			cacheSizeLabel,
			cacheSizeCombo,
			flushClearCacheLabel,
			flushClearCacheCombo);
	}

	private PropertyValueModel<PersistenceUnit2_0> buildPersistenceUnit2_0Model() {
		return new PropertyAspectAdapter<Caching, PersistenceUnit2_0>(this.getSubjectHolder()) {
			@Override
			protected PersistenceUnit2_0 buildValue_() {
				return (PersistenceUnit2_0) this.subject.getPersistenceUnit();
			}
		};
	}


	//********* shared cache mode ***********

	private EnumFormComboViewer<PersistenceUnit2_0, SharedCacheMode> addSharedCacheModeCombo(Composite parent, PropertyValueModel<? extends PersistenceUnit2_0> subjectModel) {
		return new EnumFormComboViewer<PersistenceUnit2_0, SharedCacheMode>(this, subjectModel, parent) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(PersistenceUnit2_0.SPECIFIED_SHARED_CACHE_MODE_PROPERTY);
			}

			@Override
			protected SharedCacheMode[] getChoices() {
				return SharedCacheMode.values();
			}

			@Override
			protected boolean sortChoices() {
				return false;
			}

			@Override
			protected SharedCacheMode getDefaultValue() {
				return this.getSubject().getDefaultSharedCacheMode();
			}

			@Override
			protected String displayString(SharedCacheMode value) {
				switch (value) {
					case ALL :
						return JptUiPersistence2_0Messages.SharedCacheModeComposite_all;
					case DISABLE_SELECTIVE :
						return JptUiPersistence2_0Messages.SharedCacheModeComposite_disable_selective;
					case ENABLE_SELECTIVE :
						return JptUiPersistence2_0Messages.SharedCacheModeComposite_enable_selective;
					case NONE :
						return JptUiPersistence2_0Messages.SharedCacheModeComposite_none;
					case UNSPECIFIED :
						return JptUiPersistence2_0Messages.SharedCacheModeComposite_unspecified;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected SharedCacheMode getValue() {
				return this.getSubject().getSpecifiedSharedCacheMode();
			}

			@Override
			protected void setValue(SharedCacheMode value) {
				this.getSubject().setSpecifiedSharedCacheMode(value);
			}
		};
	}

	private PropertyValueModel<Boolean> buildSharedCacheModeEnabledModel(PropertyValueModel<PersistenceUnit2_0> persistenceUnitModel) {
		return new TransformationPropertyValueModel<SharedCacheMode, Boolean>(this.buildSharedCacheModeModel(persistenceUnitModel), SHARED_CACHE_MODE_ENABLED_TRANSFORMER);
	}

	private PropertyValueModel<SharedCacheMode> buildSharedCacheModeModel(PropertyValueModel<PersistenceUnit2_0> persistenceUnitModel) {
		return new PropertyAspectAdapter<PersistenceUnit2_0, SharedCacheMode>(
								persistenceUnitModel, 
								PersistenceUnit2_0.SPECIFIED_SHARED_CACHE_MODE_PROPERTY, 
								PersistenceUnit2_0.DEFAULT_SHARED_CACHE_MODE_PROPERTY) {
			@Override
			protected SharedCacheMode buildValue_() {
				return this.subject.getSharedCacheMode();
			}
		};
	}

	private static final Transformer<SharedCacheMode, Boolean> SHARED_CACHE_MODE_ENABLED_TRANSFORMER = new SharedCacheModeEnabledTransformer();
	/* CU private */ static class SharedCacheModeEnabledTransformer
		extends TransformerAdapter<SharedCacheMode, Boolean>
	{
		@Override
		public Boolean transform(SharedCacheMode mode) {
			return Boolean.valueOf(mode != SharedCacheMode.NONE);
		}
	}

	protected EnumFormComboViewer<Caching, CacheType> buildDefaultCacheTypeCombo(Composite container) {
		return new EnumFormComboViewer<Caching, CacheType>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Caching.CACHE_TYPE_DEFAULT_PROPERTY);
			}

			@Override
			protected CacheType[] getChoices() {
				return CacheType.values();
			}

			@Override
			protected boolean sortChoices() {
				return false;
			}

			@Override
			protected CacheType getDefaultValue() {
				return getSubject().getDefaultCacheTypeDefault();
			}

			@Override
			protected String displayString(CacheType value) {
				switch (value) {
					case full :
						return EclipseLinkUiMessages.CacheTypeComposite_full;
					case weak :
						return EclipseLinkUiMessages.CacheTypeComposite_weak;
					case soft :
						return EclipseLinkUiMessages.CacheTypeComposite_soft;
					case soft_weak :
						return EclipseLinkUiMessages.CacheTypeComposite_soft_weak;
					case hard_weak :
						return EclipseLinkUiMessages.CacheTypeComposite_hard_weak;
					case none  :
						return EclipseLinkUiMessages.CacheTypeComposite_none;
					default :
						throw new IllegalStateException();
				}

			}

			@Override
			protected CacheType getValue() {
				return getSubject().getCacheTypeDefault();
			}

			@Override
			protected void setValue(CacheType value) {
				getSubject().setCacheTypeDefault(value);
			}

			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.PERSISTENCE_CACHING_DEFAULT_TYPE;
			}
		};
	}

	protected IntegerCombo<Caching> addDefaultCacheSizeCombo(Composite container) {
		return new IntegerCombo<Caching>(this, container) {	
			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.PERSISTENCE_CACHING_DEFAULT_SIZE;
			}

			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Caching, Integer>(getSubjectHolder()) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getDefaultCacheSizeDefault();
					}
				};
			}

			@Override
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<Caching, Integer>(getSubjectHolder(), Caching.CACHE_SIZE_DEFAULT_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getCacheSizeDefault();
					}

					@Override
					protected void setValue_(Integer value) {
						this.subject.setCacheSizeDefault(value);
					}
				};
			}
		};
	}
}
