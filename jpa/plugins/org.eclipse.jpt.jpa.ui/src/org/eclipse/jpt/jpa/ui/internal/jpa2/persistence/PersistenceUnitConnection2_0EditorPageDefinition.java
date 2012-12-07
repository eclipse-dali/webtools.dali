/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.widgets.Composite;

public class PersistenceUnitConnection2_0EditorPageDefinition 
	extends PersistenceUnitEditorPageDefinition
{
	// singleton
	private static final JpaEditorPageDefinition INSTANCE = 
			new PersistenceUnitConnection2_0EditorPageDefinition();

	/**
	 * Return the singleton.
	 */
	public static JpaEditorPageDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private PersistenceUnitConnection2_0EditorPageDefinition() {
		super();
	}

	public ImageDescriptor getTitleImageDescriptor() {
		return null;
	}

	public String getTitleText() {
		return JptUiPersistence2_0Messages.GenericPersistenceUnit2_0ConnectionTab_title;
	}

	public String getHelpID() {
		return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;	// TODO - Review for JPA 2.0
	}

	@Override
	protected void buildEditorPageContent(Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager, PropertyValueModel<PersistenceUnit> persistenceUnitModel) {
		new PersistenceUnitConnection2_0EditorPage(buildConnectionModel(persistenceUnitModel), parent, widgetFactory, resourceManager);
	}

	public static PropertyValueModel<JpaConnection2_0> buildConnectionModel(PropertyValueModel<PersistenceUnit> persistenceUnitModel) {
		return new TransformationPropertyValueModel<PersistenceUnit, JpaConnection2_0>(persistenceUnitModel, CONNECTION_TRANSFORMER);
	}

	public static final Transformer<PersistenceUnit, JpaConnection2_0> CONNECTION_TRANSFORMER = new ConnectionTransformer();

	public static class ConnectionTransformer
		extends AbstractTransformer<PersistenceUnit, JpaConnection2_0>
	{
		@Override
		protected JpaConnection2_0 transform_(PersistenceUnit persistenceUnit) {
			return (JpaConnection2_0) ((PersistenceUnit2_0) persistenceUnit).getConnection();
		}
	}
}
