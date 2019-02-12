/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkCaching;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkHelpContextIds;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.PersistenceUnitEditorPageDefinition2_0;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkPersistenceUnitCachingEditorPageDefinition
	extends PersistenceUnitEditorPageDefinition2_0
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
		return JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CACHING_TAB_TITLE;
	}

	public String getHelpID() {
		return EclipseLinkHelpContextIds.PERSISTENCE_CACHING;
	}

	@Override
	protected void buildEditorPageContent(Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager, PropertyValueModel<PersistenceUnit> persistenceUnitModel) {
		new EclipseLinkPersistenceUnitCachingEditorPage<EclipseLinkCaching>(buildCachingModel(persistenceUnitModel), parentComposite, widgetFactory, resourceManager);
	}

	public static PropertyValueModel<EclipseLinkCaching> buildCachingModel(PropertyValueModel<PersistenceUnit> persistenceUnitModel) {
		return new TransformationPropertyValueModel<PersistenceUnit, EclipseLinkCaching>(persistenceUnitModel, CACHING_TRANSFORMER);
	}

	public static final Transformer<PersistenceUnit, EclipseLinkCaching> CACHING_TRANSFORMER = new CachingTransformer();

	public static class CachingTransformer
		extends AbstractTransformer<PersistenceUnit, EclipseLinkCaching>
	{
		@Override
		protected EclipseLinkCaching transform_(PersistenceUnit persistenceUnit) {
			return ((EclipseLinkPersistenceUnit) persistenceUnit).getCaching();
		}
	}
}
