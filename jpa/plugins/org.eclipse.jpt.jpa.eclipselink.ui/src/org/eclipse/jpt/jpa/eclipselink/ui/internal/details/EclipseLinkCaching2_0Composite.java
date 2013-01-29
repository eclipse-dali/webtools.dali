/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java.JavaEclipseLinkEntityComposite;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.JptUiDetailsMessages2_0;
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
 * @version 3.0
 * @since 3.0
 */
public abstract class EclipseLinkCaching2_0Composite<T extends EclipseLinkCaching> extends EclipseLinkCachingComposite<T>
{
	
	protected EclipseLinkCaching2_0Composite(Pane<?> parentPane,
        PropertyValueModel<T> subjectHolder,
        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		PropertyValueModel<Cacheable2_0> cacheableHolder = buildCacheableHolder();
		
		//Shared Check box, uncheck this and the rest of the panel is disabled
		TriStateCheckBox sharedCheckBox = addTriStateCheckBoxWithDefault(
			container,
			JptUiDetailsMessages2_0.Entity_cacheableLabel,
			buildSpecifiedCacheableHolder(cacheableHolder),
			buildCacheableStringHolder(cacheableHolder),
			JpaHelpContextIds.ENTITY_CACHEABLE
		);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		sharedCheckBox.getCheckBox().setLayoutData(gridData);

		final PropertyValueModel<Boolean> cacheableEnableModel = buildCacheableEnabler(cacheableHolder);

		Label cacheTypeLabel = this.addLabel(container, EclipseLinkUiDetailsMessages.EclipseLinkCacheTypeComposite_label, cacheableEnableModel);
		gridData = new GridData();
		gridData.horizontalIndent = 16;
		cacheTypeLabel.setLayoutData(gridData);
		new EclipseLinkCacheTypeComboViewer(this, container, cacheableEnableModel);

		Label cacheSizeLabel = this.addLabel(container, EclipseLinkUiDetailsMessages.EclipseLinkCacheSizeComposite_size, cacheableEnableModel);
		gridData = new GridData();
		gridData.horizontalIndent = 16;
		cacheSizeLabel.setLayoutData(gridData);
		new EclipseLinkCacheSizeCombo(this, container, cacheableEnableModel);
		
		// Advanced sub-pane
		final Section advancedSection = this.getWidgetFactory().createSection(container, 
			ExpandableComposite.TWISTIE |
			ExpandableComposite.CLIENT_INDENT);
		gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalIndent = 16;
		gridData.horizontalSpan = 2;
		advancedSection.setLayoutData(gridData);
		advancedSection.setText(EclipseLinkUiDetailsMessages.EclipseLinkCachingComposite_advanced);


		advancedSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && advancedSection.getClient() == null) {
					Composite advancedClient = initializeAdvancedPane(advancedSection, cacheableEnableModel);
					advancedSection.setClient(advancedClient);
				}
			}
		});
		
		initializeExistenceCheckingComposite(container);
	}
	
	protected PropertyValueModel<Cacheable2_0> buildCacheableHolder() {
		return new PropertyAspectAdapter<EclipseLinkCaching, Cacheable2_0>(getSubjectHolder()) {
			@Override
			protected Cacheable2_0 buildValue_() {
				return ((CacheableHolder2_0) this.subject).getCacheable();
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildCacheableEnabler(PropertyValueModel<Cacheable2_0> cacheableHolder) {
		return new PropertyAspectAdapter<Cacheable2_0, Boolean>(
				cacheableHolder,
				Cacheable2_0.SPECIFIED_CACHEABLE_PROPERTY, 
				Cacheable2_0.DEFAULT_CACHEABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isCacheable());
			}
		};
	}	
	
	private ModifiablePropertyValueModel<Boolean> buildSpecifiedCacheableHolder(PropertyValueModel<Cacheable2_0> cacheableHolder) {
		return new PropertyAspectAdapter<Cacheable2_0, Boolean>(cacheableHolder, Cacheable2_0.SPECIFIED_CACHEABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedCacheable();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedCacheable(value);
			}
		};
	}

	private PropertyValueModel<String> buildCacheableStringHolder(PropertyValueModel<Cacheable2_0> cacheableHolder) {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultCacheableHolder(cacheableHolder)) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptUiDetailsMessages2_0.Entity_cacheableWithDefaultLabel, defaultStringValue);
				}
				return JptUiDetailsMessages2_0.Entity_cacheableLabel;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultCacheableHolder(PropertyValueModel<Cacheable2_0> cacheableHolder) {
		return new PropertyAspectAdapter<Cacheable2_0, Boolean>(
			cacheableHolder,
			Cacheable2_0.SPECIFIED_CACHEABLE_PROPERTY,
			Cacheable2_0.DEFAULT_CACHEABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedCacheable() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultCacheable());
			}
		};
	}
}