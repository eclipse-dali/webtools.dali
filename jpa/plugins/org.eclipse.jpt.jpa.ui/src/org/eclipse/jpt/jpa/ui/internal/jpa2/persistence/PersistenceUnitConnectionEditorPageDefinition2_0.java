/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.persistence;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.connection.Connection2_0;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.jpa2.persistence.JptJpaUiPersistenceMessages2_0;
import org.eclipse.swt.widgets.Composite;

public class PersistenceUnitConnectionEditorPageDefinition2_0
	extends PersistenceUnitEditorPageDefinition2_0
{
	// singleton
	private static final JpaEditorPageDefinition INSTANCE = 
			new PersistenceUnitConnectionEditorPageDefinition2_0();

	/**
	 * Return the singleton.
	 */
	public static JpaEditorPageDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private PersistenceUnitConnectionEditorPageDefinition2_0() {
		super();
	}

	public ImageDescriptor getTitleImageDescriptor() {
		return null;
	}

	public String getTitleText() {
		return JptJpaUiPersistenceMessages2_0.PERSISTENCE_UNIT_CONNECTION_TAB_TITLE;
	}

	public String getHelpID() {
		return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;	// TODO - Review for JPA 2.0
	}

	@Override
	protected void buildEditorPageContent(Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager, PropertyValueModel<PersistenceUnit> persistenceUnitModel) {
		new PersistenceUnitConnection2_0EditorPage(buildConnectionModel(persistenceUnitModel), parent, widgetFactory, resourceManager);
	}

	public static PropertyValueModel<Connection2_0> buildConnectionModel(PropertyValueModel<PersistenceUnit> persistenceUnitModel) {
		return new TransformationPropertyValueModel<PersistenceUnit, Connection2_0>(persistenceUnitModel, CONNECTION_TRANSFORMER);
	}

	public static final Transformer<PersistenceUnit, Connection2_0> CONNECTION_TRANSFORMER = new ConnectionTransformer();

	public static class ConnectionTransformer
		extends AbstractTransformer<PersistenceUnit, Connection2_0>
	{
		@Override
		protected Connection2_0 transform_(PersistenceUnit persistenceUnit) {
			return ((PersistenceUnit2_0) persistenceUnit).getConnection();
		}
	}
}
