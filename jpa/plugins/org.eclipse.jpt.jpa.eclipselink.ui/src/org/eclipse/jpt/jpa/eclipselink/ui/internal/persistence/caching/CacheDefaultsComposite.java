/*******************************************************************************
* Copyright (c) 2009, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.caching;

import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.CacheType;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Caching;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 *  CacheDefaultsComposite
 */
public class CacheDefaultsComposite<T extends Caching> extends Pane<T>
{
	public CacheDefaultsComposite(Pane<T> subjectHolder,
	                                       Composite container) {

		super(subjectHolder, container);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return this.addTitledGroup(
			parent,
			EclipseLinkUiMessages.CacheDefaultsComposite_groupTitle,
			2,
			null
		);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		// Default Cache Type
		addLabel(parent, EclipseLinkUiMessages.PersistenceXmlCachingTab_defaultCacheTypeLabel);
		buildDefaultCacheTypeCombo(parent);

		// Default Cache Size
		addLabel(parent, EclipseLinkUiMessages.DefaultCacheSizeComposite_defaultCacheSize);
		addDefaultCacheSizeCombo(parent);

		// Default Shared Cache
		TriStateCheckBox sharedCacheCheckBox = this.addTriStateCheckBoxWithDefault(
			parent,
			EclipseLinkUiMessages.PersistenceXmlCachingTab_sharedCacheDefaultLabel,
			this.buildDefaultSharedCacheHolder(),
			this.buildDefaultSharedCacheStringHolder(),
			EclipseLinkHelpContextIds.PERSISTENCE_CACHING_DEFAULT_SHARED
		);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		sharedCacheCheckBox.getCheckBox().setLayoutData(gridData);
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

	protected void addDefaultCacheSizeCombo(Composite container) {
		new IntegerCombo<Caching>(this, container) {	
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
	
	private ModifiablePropertyValueModel<Boolean> buildDefaultSharedCacheHolder() {
		return new PropertyAspectAdapter<Caching, Boolean>(getSubjectHolder(), Caching.SHARED_CACHE_DEFAULT_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSharedCacheDefault();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSharedCacheDefault(value);
			}
		};
	}

	private PropertyValueModel<String> buildDefaultSharedCacheStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultDefaultSharedCacheHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(EclipseLinkUiMessages.PersistenceXmlCachingTab_defaultSharedCacheDefaultLabel, defaultStringValue);
				}
				return EclipseLinkUiMessages.PersistenceXmlCachingTab_sharedCacheDefaultLabel;
			}
		};
	}
	private PropertyValueModel<Boolean> buildDefaultDefaultSharedCacheHolder() {
		return new PropertyAspectAdapter<Caching, Boolean>(
			getSubjectHolder(),
			Caching.SHARED_CACHE_DEFAULT_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSharedCacheDefault() != null) {
					return null;
				}
				return this.subject.getDefaultSharedCacheDefault();
			}
		};
	}
}
