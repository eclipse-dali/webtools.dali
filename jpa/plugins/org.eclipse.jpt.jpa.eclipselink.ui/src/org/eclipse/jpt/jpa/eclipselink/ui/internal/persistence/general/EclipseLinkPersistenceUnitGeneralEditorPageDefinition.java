/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.general;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.PersistenceUnitEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceUnitGeneralEditorPageDefinition;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkPersistenceUnitGeneralEditorPageDefinition
	extends PersistenceUnitEditorPageDefinition
{
	// singleton
	private static final JpaEditorPageDefinition INSTANCE = 
		new EclipseLinkPersistenceUnitGeneralEditorPageDefinition();

	/**
	 * Return the singleton.
	 */
	public static JpaEditorPageDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private EclipseLinkPersistenceUnitGeneralEditorPageDefinition() {
		super();
	}

	@Override
	public void buildEditorPageContent(Composite parent, WidgetFactory widgetFactory, PropertyValueModel<JpaStructureNode> jpaRootStructureNodeModel) {
		new EclipseLinkPersistenceUnitGeneralEditorPage(this.buildPersistenceUnitModel(jpaRootStructureNodeModel), parent, widgetFactory);
	}

	public String getPageText() {
		return PersistenceUnitGeneralEditorPageDefinition.instance().getPageText();
	}

	public ImageDescriptor getPageImageDescriptor() {
		return PersistenceUnitGeneralEditorPageDefinition.instance().getPageImageDescriptor();
	}

	public String getHelpID() {
		return PersistenceUnitGeneralEditorPageDefinition.instance().getHelpID();
	}
}