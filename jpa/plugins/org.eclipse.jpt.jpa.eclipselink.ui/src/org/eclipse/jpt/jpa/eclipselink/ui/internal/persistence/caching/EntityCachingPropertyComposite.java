/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.CacheType;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Caching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.CachingEntity;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @version 2.0
 * @since 2.0
 */
public class EntityCachingPropertyComposite extends Pane<CachingEntity> {

	/**
	 * Creates a new <code>EntityCachingPropertyComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public EntityCachingPropertyComposite(Pane<? extends Caching> parentComposite,
	                                      PropertyValueModel<CachingEntity> subjectHolder,
	                                      PropertyValueModel<Boolean> enabledModel,
	                                      Composite parent) {

		super(parentComposite, subjectHolder, enabledModel, parent);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return addSubPane(parent, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlCachingTab_cacheTypeLabel);
		new CacheTypeComboViewer(container);

		// Cache Size
		this.addLabel(container, EclipseLinkUiMessages.CacheSizeComposite_cacheSize);
		this.addCacheSizeCombo(container);

		TriStateCheckBox sharedCacheCheckBox = this.addTriStateCheckBoxWithDefault(
			container,
			EclipseLinkUiMessages.PersistenceXmlCachingTab_sharedCacheLabel,
			this.buildSharedCacheHolder(),
			this.buildSharedCacheStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_CACHING
		);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		sharedCacheCheckBox.getCheckBox().setLayoutData(gridData);
	}

	private class CacheTypeComboViewer extends EnumFormComboViewer<CachingEntity, CacheType> {

		private CacheTypeComboViewer(Composite parent) {
			super(EntityCachingPropertyComposite.this, parent);
		}

		@Override
		protected void addPropertyNames(Collection<String> propertyNames) {
			super.addPropertyNames(propertyNames);
			propertyNames.add(CachingEntity.CACHE_TYPE_PROPERTY);
		}

		private PropertyValueModel<Caching> buildCachingHolder() {
			return new TransformationPropertyValueModel<CachingEntity, Caching>(getSubjectHolder()) {
				@Override
				protected Caching transform_(CachingEntity value) {
					return value.getParent();
				}
			};
		}

		private PropertyValueModel<CacheType> buildDefaultCacheTypeHolder() {
			return new PropertyAspectAdapter<Caching, CacheType>(buildCachingHolder(), Caching.CACHE_TYPE_DEFAULT_PROPERTY) {
				@Override
				protected CacheType buildValue_() {
					CacheType cacheType = subject.getCacheTypeDefault();
					if (cacheType == null) {
						cacheType = subject.getDefaultCacheTypeDefault();
					}
					return cacheType;
				}
			};
		}

		private PropertyChangeListener buildDefaultCachingTypePropertyChangeListener() {
			return new SWTPropertyChangeListenerWrapper(
				buildDefaultCachingTypePropertyChangeListener_()
			);
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
		protected CacheType[] getChoices() {
			return CacheType.values();
		}

		@Override
		protected CacheType getDefaultValue() {
			return getSubjectParent().getDefaultCacheType();
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
		protected void doPopulate() {
			// This is required to allow the class loader to let the listener
			// written above to access this method
			super.doPopulate();
		}

		@Override
		protected CacheType getValue() {
			return getSubjectParent().getCacheTypeOf(getSubjectName());
		}

		@Override
		protected void initialize() {
			super.initialize();

			PropertyValueModel<CacheType> defaultCacheTypeHolder =
				buildDefaultCacheTypeHolder();

			defaultCacheTypeHolder.addPropertyChangeListener(
				PropertyValueModel.VALUE,
				buildDefaultCachingTypePropertyChangeListener()
			);
		}

		@Override
		protected void setValue(CacheType value) {
			getSubjectParent().setCacheTypeOf(getSubjectName(), value);
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

	private String getSubjectName() {
		return this.getSubjectHolder().getValue().getName();
	}

	private Caching getSubjectParent() {
		return this.getSubjectHolder().getValue().getParent();
	}

	private void addCacheSizeCombo(Composite container) {
		new IntegerCombo<CachingEntity>(this, container) {	
			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.PERSISTENCE_CACHING;
			}
			@Override
			protected PropertyValueModel<Integer> buildDefaultHolder() {
				return new PropertyAspectAdapter<Caching, Integer>(buildCachingHolder(), Caching.CACHE_SIZE_DEFAULT_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						Integer value = this.subject.getCacheSizeDefault();
						if (value == null) {
							value = this.subject.getDefaultCacheSizeDefault();
						}
						return value;
					}
				};
			}

			@Override
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<CachingEntity, Integer>(getSubjectHolder(), CachingEntity.CACHE_SIZE_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return getSubjectParent().getCacheSizeOf(getSubjectName());
					}

					@Override
					protected void setValue_(Integer value) {
						getSubjectParent().setCacheSizeOf(getSubjectName(), value);
					}
				};
			}
		};
	}

	private PropertyValueModel<Caching> buildCachingHolder() {
		return new TransformationPropertyValueModel<CachingEntity, Caching>(this.getSubjectHolder()) {
			@Override
			protected Caching transform_(CachingEntity value) {
				return value.getParent();
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildSharedCacheHolder() {
		return new PropertyAspectAdapter<CachingEntity, Boolean>(
					getSubjectHolder(), CachingEntity.SHARED_CACHE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return getSubjectParent().getSharedCacheOf(getSubjectName());
			}

			@Override
			protected void setValue_(Boolean value) {
				getSubjectParent().setSharedCacheOf(getSubjectName(), value);
			}
		};
	}

	private PropertyValueModel<String> buildSharedCacheStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultAndNonDefaultSharedCacheHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlCachingTab_defaultSharedCacheLabel, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlCachingTab_sharedCacheLabel;
			}
		};
	}

	private PropertyValueModel<Boolean> buildDefaultAndNonDefaultSharedCacheHolder() {
		return new ListPropertyValueModelAdapter<Boolean>(
			buildDefaultAndNonDefaultSharedCacheListHolder()
		) {
			@Override
			protected Boolean buildValue() {
				// If the number of ListValueModel equals 1, that means the shared
				// Cache properties is not set (partially selected), which means we
				// want to see the default value appended to the text
				if (this.listModel.size() == 1) {
					return (Boolean) this.listModel.listIterator().next();
				}
				return null;
			}
		};
	}

	private ListValueModel<Boolean> buildDefaultAndNonDefaultSharedCacheListHolder() {
		ArrayList<ListValueModel<Boolean>> holders = new ArrayList<ListValueModel<Boolean>>(2);
		holders.add(buildSharedCacheListHolder());
		holders.add(buildDefaultSharedCacheListHolder());
		return CompositeListValueModel.forModels(holders);
	}

	private ListValueModel<Boolean> buildSharedCacheListHolder() {
		return new PropertyListValueModelAdapter<Boolean>(
			buildSharedCacheHolder()
		);
	}

	private ListValueModel<Boolean> buildDefaultSharedCacheListHolder() {
		return new PropertyListValueModelAdapter<Boolean>(
			buildDefaultSharedCacheHolder()
		);
	}

	private PropertyValueModel<Boolean> buildDefaultSharedCacheHolder() {
		return new PropertyAspectAdapter<Caching, Boolean>(buildCachingHolder(), Caching.SHARED_CACHE_DEFAULT_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				Boolean value = this.subject.getSharedCacheDefault();
				if (value == null) {
					value = this.subject.getDefaultSharedCacheDefault();
				}
				return value;
			}
		};
	}
}
