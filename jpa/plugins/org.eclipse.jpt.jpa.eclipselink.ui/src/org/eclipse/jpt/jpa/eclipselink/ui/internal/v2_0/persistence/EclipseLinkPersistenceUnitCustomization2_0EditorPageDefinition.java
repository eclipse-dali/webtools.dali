/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Customization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization.EclipseLinkPersistenceUnitCustomizationEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.PersistenceUnitEditorPageDefinition;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkPersistenceUnitCustomization2_0EditorPageDefinition
	extends PersistenceUnitEditorPageDefinition
{
	// singleton
	private static final JpaEditorPageDefinition INSTANCE = 
			new EclipseLinkPersistenceUnitCustomization2_0EditorPageDefinition();

	/**
	 * Return the singleton.
	 */
	public static JpaEditorPageDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private EclipseLinkPersistenceUnitCustomization2_0EditorPageDefinition() {
		super();
	}

	@Override
	public void buildEditorPageContent(Composite parent, WidgetFactory widgetFactory, PropertyValueModel<JpaStructureNode> jpaRootStructureNodeModel) {
		new EclipseLinkPersistenceUnitCustomization2_0EditorPage(
				this.buildCustomizationModel(jpaRootStructureNodeModel), 
				parent, 
				widgetFactory);
	}

	private PropertyValueModel<Customization> buildCustomizationModel(PropertyValueModel<JpaStructureNode> jpaRootStructureNodeModel) {
		return new TransformationPropertyValueModel<PersistenceUnit, Customization>(this.buildPersistenceUnitModel(jpaRootStructureNodeModel)) {
			@Override
			protected Customization transform_(PersistenceUnit value) {
				return ((EclipseLinkPersistenceUnit) value).getCustomization();
			}
		};
	}

	public String getPageText() {
		return EclipseLinkPersistenceUnitCustomizationEditorPageDefinition.instance().getPageText();
	}

	public String getHelpID() {
		return EclipseLinkPersistenceUnitCustomizationEditorPageDefinition.instance().getHelpID();
	}

	public ImageDescriptor getPageImageDescriptor() {
		return EclipseLinkPersistenceUnitCustomizationEditorPageDefinition.instance().getPageImageDescriptor();
	}

}
