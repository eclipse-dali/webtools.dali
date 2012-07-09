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
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Caching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.FlushClearCache;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.editors.JpaPageComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 *  PersistenceXmlCachingTab
 */
public class PersistenceXmlCachingTab<T extends Caching>
								extends Pane<T>
								implements JpaPageComposite
{
	public PersistenceXmlCachingTab(
			PropertyValueModel<T> subjectHolder,
			Composite parent,
            WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	// ********** JpaPageComposite implementation **********

	public String getHelpID() {
		return EclipseLinkHelpContextIds.PERSISTENCE_CACHING;
	}

	public ImageDescriptor getPageImageDescriptor() {
		return null;
	}
	public String getPageText() {
		return EclipseLinkUiMessages.PersistenceXmlCachingTab_title;
	}

	
	@Override
	protected void initializeLayout(Composite container) {
		container = this.addSection(
			container,
			EclipseLinkUiMessages.PersistenceXmlCachingTab_sectionTitle,
			EclipseLinkUiMessages.PersistenceXmlCachingTab_sectionDescription
		);
		container.setLayout(new GridLayout(2, false));

		// Defaults
		CacheDefaultsComposite<T> defaultsComposite = new CacheDefaultsComposite<T>(this, container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		defaultsComposite.getControl().setLayoutData(gridData);

		// EntitiesList
		EntityListComposite<T> entitiesComposite = new EntityListComposite<T>(this, container);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		entitiesComposite.getControl().setLayoutData(gridData);

		// Flush Clear Cache
		this.addLabel(container, EclipseLinkUiMessages.PersistenceXmlCachingTab_FlushClearCacheLabel);
		this.addFlushClearCacheCombo(container);
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
						return EclipseLinkUiMessages.FlushClearCacheComposite_drop;
					case drop_invalidate :
						return EclipseLinkUiMessages.FlushClearCacheComposite_drop_invalidate;
					case merge :
						return EclipseLinkUiMessages.FlushClearCacheComposite_merge;
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
