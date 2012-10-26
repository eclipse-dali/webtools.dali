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
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
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

	public String getPageText() {
		return JptUiPersistence2_0Messages.GenericPersistenceUnit2_0ConnectionTab_title;
	}

	public ImageDescriptor getPageImageDescriptor() {
		return null;
	}

	public String getHelpID() {
		return JpaHelpContextIds.PERSISTENCE_XML_CONNECTION;	// TODO - Review for JPA 2.0
	}

	@Override
	public void buildEditorPageContent(Composite parent, WidgetFactory widgetFactory, PropertyValueModel<JpaStructureNode> jpaRootStructureNodeModel) {
		new PersistenceUnitConnection2_0EditorPage(this.buildJpaConnection2_0Model(jpaRootStructureNodeModel), parent, widgetFactory);
	}

	protected PropertyValueModel<JpaConnection2_0> buildJpaConnection2_0Model(PropertyValueModel<JpaStructureNode> jpaRootStructureNodeModel) {
		return new TransformationPropertyValueModel<PersistenceUnit, JpaConnection2_0>(this.buildPersistenceUnitModel(jpaRootStructureNodeModel)) {
			@Override
			protected JpaConnection2_0 transform_(PersistenceUnit value) {
				return (JpaConnection2_0) ((PersistenceUnit2_0) value).getConnection();
			}
		};
	}
}
