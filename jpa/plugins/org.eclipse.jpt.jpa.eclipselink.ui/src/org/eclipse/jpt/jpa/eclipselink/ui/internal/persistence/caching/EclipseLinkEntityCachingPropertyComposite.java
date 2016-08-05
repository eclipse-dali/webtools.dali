/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.caching;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.swt.listeners.SWTListenerTools;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCacheType;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCachingEntity;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.TriStateCheckBoxLabelModelStringTransformer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkEntityCachingPropertyComposite
	extends Pane<EclipseLinkCachingEntity>
{
	/**
	 * Creates a new <code>EntityCachingPropertyComposite</code>.
	 *
	 * @param parentComposite The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public EclipseLinkEntityCachingPropertyComposite(Pane<? extends EclipseLinkCaching> parentComposite,
	                                      PropertyValueModel<EclipseLinkCachingEntity> subjectHolder,
	                                      PropertyValueModel<Boolean> enabledModel,
	                                      Composite parent) {

		super(parentComposite, subjectHolder, enabledModel, parent);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return addSubPane(parent, 2, 0, 0, 0, 0);
	}

	@Override
	@SuppressWarnings("unused")
	protected void initializeLayout(Composite container) {
		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CACHING_TAB_CACHE_TYPE_LABEL);
		new CacheTypeComboViewer(container);

		// Cache Size
		this.addLabel(container, JptJpaEclipseLinkUiMessages.CACHE_SIZE_COMPOSITE_CACHE_SIZE);
		this.addCacheSizeCombo(container);

		TriStateCheckBox sharedCacheCheckBox = this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CACHING_TAB_SHARED_CACHE_LABEL,
			this.buildSharedCacheModel(),
			this.buildSharedCacheStringModel(),
			EclipseLinkHelpContextIds.PERSISTENCE_CACHING
		);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		sharedCacheCheckBox.getCheckBox().setLayoutData(gridData);
	}

	class CacheTypeComboViewer extends EnumFormComboViewer<EclipseLinkCachingEntity, EclipseLinkCacheType> {

		CacheTypeComboViewer(Composite parent) {
			super(EclipseLinkEntityCachingPropertyComposite.this, parent);
		}

		@Override
		protected void addPropertyNames(Collection<String> propertyNames) {
			super.addPropertyNames(propertyNames);
			propertyNames.add(EclipseLinkCachingEntity.CACHE_TYPE_PROPERTY);
		}

		private PropertyValueModel<EclipseLinkCaching> buildCachingModel() {
			return PropertyValueModelTools.transform(this.getSubjectHolder(), EclipseLinkCachingEntity.PARENT_TRANSFORMER);
		}

		private PropertyValueModel<EclipseLinkCacheType> buildDefaultCacheTypeModel() {
			return new PropertyAspectAdapterXXXX<EclipseLinkCaching, EclipseLinkCacheType>(buildCachingModel(), EclipseLinkCaching.CACHE_TYPE_DEFAULT_PROPERTY) {
				@Override
				protected EclipseLinkCacheType buildValue_() {
					EclipseLinkCacheType cacheType = this.subject.getCacheTypeDefault();
					if (cacheType == null) {
						cacheType = this.subject.getDefaultCacheTypeDefault();
					}
					return cacheType;
				}
			};
		}

		private PropertyChangeListener buildDefaultCachingTypePropertyChangeListener() {
			return SWTListenerTools.wrap(buildDefaultCachingTypePropertyChangeListener_());
		}

		private PropertyChangeListener buildDefaultCachingTypePropertyChangeListener_() {
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent e) {
					if ((e.getNewValue() != null) && !getControl().isDisposed()) {
						CacheTypeComboViewer.this.doPopulate();
					}
				}
			};
		}

		@Override
		protected EclipseLinkCacheType[] getChoices() {
			return EclipseLinkCacheType.values();
		}

		@Override
		protected EclipseLinkCacheType getDefaultValue() {
			return getSubjectParent().getDefaultEntityCacheType();
		}

		@Override
		protected String displayString(EclipseLinkCacheType value) {
			switch (value) {
				case full :
					return JptJpaEclipseLinkUiMessages.CACHE_TYPE_COMPOSITE_FULL;
				case weak :
					return JptJpaEclipseLinkUiMessages.CACHE_TYPE_COMPOSITE_WEAK;
				case soft :
					return JptJpaEclipseLinkUiMessages.CACHE_TYPE_COMPOSITE_SOFT;
				case soft_weak :
					return JptJpaEclipseLinkUiMessages.CACHE_TYPE_COMPOSITE_SOFT_WEAK;
				case hard_weak :
					return JptJpaEclipseLinkUiMessages.CACHE_TYPE_COMPOSITE_HARD_WEAK;
				case none  :
					return JptJpaEclipseLinkUiMessages.CACHE_TYPE_COMPOSITE_NONE;
				default :
					throw new IllegalStateException();
			}
		}

		@Override
		protected void doPopulate() {
			// This is required to allow the class loader to let the listener
			// written above to access this method
			super.doPopulate();
		}

		@Override
		protected EclipseLinkCacheType getValue() {
			return getSubjectParent().getEntityCacheType(getSubjectName());
		}

		@Override
		protected void initialize() {
			super.initialize();

			PropertyValueModel<EclipseLinkCacheType> defaultCacheTypeModel =
				buildDefaultCacheTypeModel();

			defaultCacheTypeModel.addPropertyChangeListener(
				PropertyValueModel.VALUE,
				buildDefaultCachingTypePropertyChangeListener()
			);
		}

		@Override
		protected void setValue(EclipseLinkCacheType value) {
			getSubjectParent().setEntityCacheType(getSubjectName(), value);
		}

		@Override
		protected boolean sortChoices() {
			return false;
		}

		@Override
		protected String getHelpId() {
			return EclipseLinkHelpContextIds.PERSISTENCE_CACHING;
		}
	}

	String getSubjectName() {
		return this.getSubjectHolder().getValue().getName();
	}

	EclipseLinkCaching getSubjectParent() {
		return this.getSubjectHolder().getValue().getParent();
	}

	@SuppressWarnings("unused")
	private void addCacheSizeCombo(Composite container) {
		new CacheSizeCombo(this, container);
	}

	class CacheSizeCombo
		extends IntegerCombo<EclipseLinkCachingEntity>
	{
		CacheSizeCombo(Pane<? extends EclipseLinkCachingEntity> parentPane, Composite parent) {
			super(parentPane, parent);
		}

		@Override
		protected String getHelpId() {
			return EclipseLinkHelpContextIds.PERSISTENCE_CACHING;
		}

		@Override
		protected PropertyValueModel<Integer> buildDefaultModel() {
			return new PropertyAspectAdapterXXXX<EclipseLinkCaching, Integer>(buildCachingModel(), EclipseLinkCaching.CACHE_SIZE_DEFAULT_PROPERTY) {
				@Override
				protected Integer buildValue_() {
					Integer size = this.subject.getCacheSizeDefault();
					if (size == null) {
						size = this.subject.getDefaultCacheSizeDefault();
					}
					return size;
				}
			};
		}

		@Override
		protected ModifiablePropertyValueModel<Integer> buildSelectedItemModel() {
			return new PropertyAspectAdapterXXXX<EclipseLinkCachingEntity, Integer>(getSubjectHolder(), EclipseLinkCachingEntity.CACHE_SIZE_PROPERTY) {
				@Override
				protected Integer buildValue_() {
					return getSubjectParent().getEntityCacheSize(getSubjectName());
				}

				@Override
				protected void setValue_(Integer value) {
					getSubjectParent().setEntityCacheSize(getSubjectName(), value);
				}
			};
		}
	}

	PropertyValueModel<EclipseLinkCaching> buildCachingModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), EclipseLinkCachingEntity.PARENT_TRANSFORMER);
	}

	private ModifiablePropertyValueModel<Boolean> buildSharedCacheModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCachingEntity, Boolean>(
					getSubjectHolder(), EclipseLinkCachingEntity.SHARED_CACHE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return getSubjectParent().getEntitySharedCache(getSubjectName());
			}

			@Override
			protected void setValue_(Boolean value) {
				getSubjectParent().setEntitySharedCache(getSubjectName(), value);
			}
		};
	}

	private PropertyValueModel<String> buildSharedCacheStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultAndNonDefaultSharedCacheModel(), SHARED_CACHE_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> SHARED_CACHE_TRANSFORMER = new TriStateCheckBoxLabelModelStringTransformer(
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CACHING_TAB_DEFAULT_SHARED_CACHE_LABEL,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CACHING_TAB_SHARED_CACHE_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultAndNonDefaultSharedCacheModel() {
		// If the size of ListValueModel equals 1, that means the shared
		// Cache properties is not set (partially selected), which means we
		// want to see the default value appended to the text
		return ListValueModelTools.singleElementPropertyValueModel(this.buildDefaultAndNonDefaultSharedCacheListModel());
	}

	private ListValueModel<Boolean> buildDefaultAndNonDefaultSharedCacheListModel() {
		ArrayList<ListValueModel<Boolean>> holders = new ArrayList<>(2);
		holders.add(buildSharedCacheListModel());
		holders.add(buildDefaultSharedCacheListModel());
		return CompositeListValueModel.forModels(holders);
	}

	private ListValueModel<Boolean> buildSharedCacheListModel() {
		return new PropertyListValueModelAdapter<>(
			buildSharedCacheModel()
		);
	}

	private ListValueModel<Boolean> buildDefaultSharedCacheListModel() {
		return new PropertyListValueModelAdapter<>(
			buildDefaultSharedCacheModel()
		);
	}

	private PropertyValueModel<Boolean> buildDefaultSharedCacheModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCaching, Boolean>(this.buildCachingModel(), EclipseLinkCaching.SHARED_CACHE_DEFAULT_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				Boolean b = this.subject.getSharedCacheDefault();
				if (b == null) {
					b = this.subject.getDefaultSharedCacheDefault();
				}
				return b;
			}
		};
	}
}
