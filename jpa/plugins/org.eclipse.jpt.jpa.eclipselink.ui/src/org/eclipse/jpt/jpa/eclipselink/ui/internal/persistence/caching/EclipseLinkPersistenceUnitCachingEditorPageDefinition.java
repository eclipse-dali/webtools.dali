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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Caching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.PersistenceUnitEditorPageDefinition;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkPersistenceUnitCachingEditorPageDefinition
	extends PersistenceUnitEditorPageDefinition
{
	// singleton
	private static final JpaEditorPageDefinition INSTANCE = 
			new EclipseLinkPersistenceUnitCachingEditorPageDefinition();

	/**
	 * Return the singleton.
	 */
	public static JpaEditorPageDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private EclipseLinkPersistenceUnitCachingEditorPageDefinition() {
		super();
	}

	public ImageDescriptor getTitleImageDescriptor() {
		return null;
	}

	public String getTitleText() {
		return EclipseLinkUiMessages.PersistenceXmlCachingTab_title;
	}

	public String getHelpID() {
		return EclipseLinkHelpContextIds.PERSISTENCE_CACHING;
	}

	@Override
	protected void buildEditorPageContent(Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager, PropertyValueModel<PersistenceUnit> persistenceUnitModel) {
		new EclipseLinkPersistenceUnitCachingEditorPage<Caching>(buildCachingModel(persistenceUnitModel), parentComposite, widgetFactory, resourceManager);
	}

	public static PropertyValueModel<Caching> buildCachingModel(PropertyValueModel<PersistenceUnit> persistenceUnitModel) {
		return new TransformationPropertyValueModel<PersistenceUnit, Caching>(persistenceUnitModel, CACHING_TRANSFORMER);
	}

	public static final Transformer<PersistenceUnit, Caching> CACHING_TRANSFORMER = new CachingTransformer();

	public static class CachingTransformer
		extends AbstractTransformer<PersistenceUnit, Caching>
	{
		@Override
		protected Caching transform_(PersistenceUnit persistenceUnit) {
			return ((EclipseLinkPersistenceUnit) persistenceUnit).getCaching();
		}
	}
}
