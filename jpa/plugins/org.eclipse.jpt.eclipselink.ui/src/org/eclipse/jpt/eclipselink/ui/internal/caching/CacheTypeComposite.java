/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.caching;

import java.util.Collection;

import org.eclipse.jpt.eclipselink.core.internal.context.caching.CacheType;
import org.eclipse.jpt.eclipselink.core.internal.context.caching.Caching;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * CacheTypeComposite
 */
public class CacheTypeComposite extends AbstractPane<EntityCacheProperties>
{
	/**
	 * Creates a new <code>CacheTypeComposite</code>.
	 *
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public CacheTypeComposite(AbstractPane<EntityCacheProperties> parentComposite,
	                          Composite parent) {

		super(parentComposite, parent);
	}

	private EnumFormComboViewer<EntityCacheProperties, CacheType> buildCacheTypeCombo(Composite container) {
		return new EnumFormComboViewer<EntityCacheProperties, CacheType>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Caching.CACHE_TYPE_PROPERTY);
			}

			@Override
			protected CacheType[] choices() {
				return CacheType.values();
			}
			
			@Override
			protected boolean sortChoices() {
				return false;
			}

			@Override
			protected CacheType defaultValue() {
				return this.subject().getDefaultCacheType();
			}

			@Override
			protected String displayString(CacheType value) {
				return buildDisplayString(EclipseLinkUiMessages.class, CacheTypeComposite.this, value);
			}

			@Override
			protected CacheType getValue() {
				return this.subject().getCacheType();
			}

			@Override
			protected void setValue(CacheType value) {
				this.subject().setCacheType(value);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.buildLabeledComposite(
			container,
			EclipseLinkUiMessages.PersistenceXmlCachingTab_cacheTypeLabel,
			this.buildCacheTypeCombo(container),
			null		// TODO IJpaHelpContextIds.
		);
	}
}
