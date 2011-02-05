/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.details.AbstractMappingUiDefinition;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class UnsupportedOrmMappingUiDefinition
	extends AbstractMappingUiDefinition
	implements OrmAttributeMappingUiDefinition
{
	// singleton
	private static final UnsupportedOrmMappingUiDefinition INSTANCE = 
			new UnsupportedOrmMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private UnsupportedOrmMappingUiDefinition() {
		super();
	}
	
	
	public Image getImage() {
		return JpaMappingImageHelper.imageForAttributeMapping(getKey());
	}
	
	public String getLabel() {
		return JptUiDetailsOrmMessages.UnsupportedOrmMappingUiProvider_label;
	}
	
	public String getLinkLabel() {
		return JptUiDetailsOrmMessages.UnsupportedOrmMappingUiProvider_linkLabel;
	}
	
	public String getKey() {
		return null;
	}	
	
	public JpaComposite buildAttributeMappingComposite(
			OrmXmlUiFactory factory,
			PropertyValueModel subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return new NullComposite(subjectHolder, parent, widgetFactory);
	}
	
	public static class NullComposite extends Pane<JavaAttributeMapping>
		implements JpaComposite
	{
		NullComposite(
				PropertyValueModel<JavaAttributeMapping> subjectHolder,
		        Composite parent,
		        WidgetFactory widgetFactory) {
			
			super(subjectHolder, parent, widgetFactory);
		}
		
		
		@Override
		protected void initializeLayout(Composite container) {}
	}
}
