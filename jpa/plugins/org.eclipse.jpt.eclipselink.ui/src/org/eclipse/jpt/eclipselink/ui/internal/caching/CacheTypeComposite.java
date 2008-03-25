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
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.swt.widgets.Composite;

/**
 * CacheTypeComposite
 */
public class CacheTypeComposite extends AbstractFormPane<Caching>
{
	private EntityListComposite entitiesComposite;

	/**
	 * Creates a new <code>CacheTypeComposite</code>.
	 * 
	 * @param parentController
	 *            The parent container of this one
	 * @param parent
	 *            The parent container
	 */
	public CacheTypeComposite(
									AbstractFormPane<? extends Caching> parentComposite, 
									Composite parent, 
									EntityListComposite entitiesComposite) {

		super(parentComposite, parent);
		this.entitiesComposite = entitiesComposite;
	}

	private EnumFormComboViewer<Caching, CacheType> buildCacheTypeCombo(Composite container) {
		return new EnumFormComboViewer<Caching, CacheType>(this, container) {
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
			protected CacheType defaultValue() {
				return this.subject().getDefaultCacheType();
			}

			@Override
			protected String displayString(CacheType value) {
				return buildDisplayString(EclipseLinkUiMessages.class, CacheTypeComposite.this, value);
			}

			@Override
			protected CacheType getValue() {
				String entityName = CacheTypeComposite.this.getSelection();
				if (!StringTools.stringIsEmpty(entityName)) {
					return this.subject().getCacheType(entityName);
				}
				return null;
			}

			@Override
			protected void setValue(CacheType value) {
				String entityName = CacheTypeComposite.this.getSelection();
				if (!StringTools.stringIsEmpty(entityName)) {
					this.subject().setCacheType(value, entityName);
				}
			}
		};
	}

	protected String getSelection() {
		return (String) this.entitiesComposite.listPane().getSelectionModel().selectedValue();
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
