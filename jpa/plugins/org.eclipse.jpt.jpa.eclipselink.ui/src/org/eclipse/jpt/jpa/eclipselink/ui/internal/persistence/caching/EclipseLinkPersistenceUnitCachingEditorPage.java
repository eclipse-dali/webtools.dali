/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.caching;

import java.util.Collection;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkFlushClearCache;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class EclipseLinkPersistenceUnitCachingEditorPage<T extends EclipseLinkCaching>
	extends Pane<T>
{
	public EclipseLinkPersistenceUnitCachingEditorPage(
			PropertyValueModel<T> subjectModel,
			Composite parentComposite,
            WidgetFactory widgetFactory,
            ResourceManager resourceManager) {
		super(subjectModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CACHING_TAB_SECTION_TITLE);
		section.setDescription(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CACHING_TAB_SECTION_DESCRIPTION);

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

		// Defaults
		EclipseLinkCacheDefaultsComposite<T> defaultsComposite = new EclipseLinkCacheDefaultsComposite<T>(this, client);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		defaultsComposite.getControl().setLayoutData(gridData);

		// EntitiesList
		EclipseLinkEntityListComposite<T> entitiesComposite = new EclipseLinkEntityListComposite<T>(this, client);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		entitiesComposite.getControl().setLayoutData(gridData);

		// Flush Clear Cache
		this.addLabel(client, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CACHING_TAB_FLUSH_CLEAR_CACHE_LABEL);
		this.addFlushClearCacheCombo(client);
	}

	protected EnumFormComboViewer<EclipseLinkCaching, EclipseLinkFlushClearCache> addFlushClearCacheCombo(Composite container) {
		return new EnumFormComboViewer<EclipseLinkCaching, EclipseLinkFlushClearCache>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(EclipseLinkCaching.FLUSH_CLEAR_CACHE_PROPERTY);
			}

			@Override
			protected EclipseLinkFlushClearCache[] getChoices() {
				return EclipseLinkFlushClearCache.values();
			}

			@Override
			protected boolean sortChoices() {
				return false;
			}

			@Override
			protected EclipseLinkFlushClearCache getDefaultValue() {
				return this.getSubject().getDefaultFlushClearCache();
			}

			@Override
			protected String displayString(EclipseLinkFlushClearCache value) {
				switch (value) {
					case drop :
						return JptJpaEclipseLinkUiMessages.FLUSH_CLEAR_CACHE_COMPOSITE_DROP;
					case drop_invalidate :
						return JptJpaEclipseLinkUiMessages.FLUSH_CLEAR_CACHE_COMPOSITE_DROP_INVALIDATE;
					case merge :
						return JptJpaEclipseLinkUiMessages.FLUSH_CLEAR_CACHE_COMPOSITE_MERGE;
					default :
						throw new IllegalStateException();
				}
			}

			@Override
			protected EclipseLinkFlushClearCache getValue() {
				return this.getSubject().getFlushClearCache();
			}

			@Override
			protected void setValue(EclipseLinkFlushClearCache value) {
				this.getSubject().setFlushClearCache(value);
			}

			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.PERSISTENCE_CACHING;
			}
		};
	}
}
