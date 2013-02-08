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
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Caching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.FlushClearCache;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.JptJpaEclipseLinkUiMessages;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class EclipseLinkPersistenceUnitCachingEditorPage<T extends Caching>
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
		CacheDefaultsComposite<T> defaultsComposite = new CacheDefaultsComposite<T>(this, client);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		defaultsComposite.getControl().setLayoutData(gridData);

		// EntitiesList
		EntityListComposite<T> entitiesComposite = new EntityListComposite<T>(this, client);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		entitiesComposite.getControl().setLayoutData(gridData);

		// Flush Clear Cache
		this.addLabel(client, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CACHING_TAB_FLUSH_CLEAR_CACHE_LABEL);
		this.addFlushClearCacheCombo(client);
	}

	protected EnumFormComboViewer<Caching, FlushClearCache> addFlushClearCacheCombo(Composite container) {
		return new EnumFormComboViewer<Caching, FlushClearCache>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Caching.FLUSH_CLEAR_CACHE_PROPERTY);
			}

			@Override
			protected FlushClearCache[] getChoices() {
				return FlushClearCache.values();
			}

			@Override
			protected boolean sortChoices() {
				return false;
			}

			@Override
			protected FlushClearCache getDefaultValue() {
				return this.getSubject().getDefaultFlushClearCache();
			}

			@Override
			protected String displayString(FlushClearCache value) {
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
			protected FlushClearCache getValue() {
				return this.getSubject().getFlushClearCache();
			}

			@Override
			protected void setValue(FlushClearCache value) {
				this.getSubject().setFlushClearCache(value);
			}

			@Override
			protected String getHelpId() {
				return EclipseLinkHelpContextIds.PERSISTENCE_CACHING;
			}
		};
	}
}
