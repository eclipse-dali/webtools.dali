/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.CacheableReference2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java.EclipseLinkJavaEntityComposite;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.TriStateCheckBoxLabelModelAdapter;
import org.eclipse.jpt.jpa.ui.internal.TriStateCheckBoxLabelModelStringTransformer;
import org.eclipse.jpt.jpa.ui.jpa2.details.JptJpaUiDetailsMessages2_0;
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
 * @see EclipseLinkCaching
 * @see EclipseLinkJavaEntityComposite - The parent container
 * @see EclipseLinkCacheTypeComboViewer
 * @see EclipseLinkCacheSizeCombo
 *
 * @version 3.0
 * @since 3.0
 */
public abstract class EclipseLinkCachingComposite2_0<T extends EclipseLinkCaching>
	extends EclipseLinkCachingComposite<T>
{
	
	protected EclipseLinkCachingComposite2_0(Pane<?> parentPane,
        PropertyValueModel<T> subjectHolder,
        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}
	
	@SuppressWarnings("unused")
	@Override
	protected void initializeLayout(Composite container) {
		PropertyValueModel<Cacheable2_0> cacheableModel = buildCacheableModel();
		
		//Shared Check box, uncheck this and the rest of the panel is disabled
		TriStateCheckBox sharedCheckBox = addTriStateCheckBoxWithDefault(
			container,
			JptJpaUiDetailsMessages2_0.ENTITY_CACHEABLE_LABEL,
			buildSpecifiedCacheableModel(cacheableModel),
			buildCacheableStringModel(cacheableModel),
			JpaHelpContextIds.ENTITY_CACHEABLE
		);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		sharedCheckBox.getCheckBox().setLayoutData(gridData);

		final PropertyValueModel<Boolean> cacheableEnableModel = buildCacheableEnabler(cacheableModel);

		Label cacheTypeLabel = this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CACHE_TYPE_COMPOSITE_LABEL, cacheableEnableModel);
		gridData = new GridData();
		gridData.horizontalIndent = 16;
		cacheTypeLabel.setLayoutData(gridData);
		new EclipseLinkCacheTypeComboViewer(this, container, cacheableEnableModel);

		Label cacheSizeLabel = this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CACHE_SIZE_COMPOSITE_SIZE, cacheableEnableModel);
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
		advancedSection.setText(JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CACHING_COMPOSITE_ADVANCED);


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
	
	protected PropertyValueModel<Cacheable2_0> buildCacheableModel() {
		return new PropertyAspectAdapterXXXX<EclipseLinkCaching, Cacheable2_0>(getSubjectHolder()) {
			@Override
			protected Cacheable2_0 buildValue_() {
				return ((CacheableReference2_0) this.subject).getCacheable();
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildCacheableEnabler(PropertyValueModel<Cacheable2_0> cacheableModel) {
		return new PropertyAspectAdapterXXXX<Cacheable2_0, Boolean>(
				cacheableModel,
				Cacheable2_0.SPECIFIED_CACHEABLE_PROPERTY, 
				Cacheable2_0.DEFAULT_CACHEABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.isCacheable());
			}
		};
	}	
	
	private ModifiablePropertyValueModel<Boolean> buildSpecifiedCacheableModel(PropertyValueModel<Cacheable2_0> cacheableModel) {
		return new PropertyAspectAdapterXXXX<Cacheable2_0, Boolean>(cacheableModel, Cacheable2_0.SPECIFIED_CACHEABLE_PROPERTY) {
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

	private PropertyValueModel<String> buildCacheableStringModel(PropertyValueModel<Cacheable2_0> cacheableModel) {
		return PropertyValueModelTools.transform_(this.buildDefaultCacheableModel(cacheableModel), CACHEABLE_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> CACHEABLE_TRANSFORMER = new TriStateCheckBoxLabelModelStringTransformer(
			JptJpaUiDetailsMessages2_0.ENTITY_CACHEABLE_WITH_DEFAULT_LABEL,
			JptJpaUiDetailsMessages2_0.ENTITY_CACHEABLE_LABEL
		);

	private PropertyValueModel<Boolean> buildDefaultCacheableModel(PropertyValueModel<Cacheable2_0> cacheableModel) {
		return TriStateCheckBoxLabelModelAdapter.adaptSubjectModelAspects_(
				cacheableModel,
				Cacheable2_0.SPECIFIED_CACHEABLE_PROPERTY,
				c -> c.getSpecifiedCacheable(),
				Cacheable2_0.DEFAULT_CACHEABLE_PROPERTY,
				c -> c.getDefaultCacheable()
			);
	}
}
