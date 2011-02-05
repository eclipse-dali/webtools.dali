/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.orm.OrmTypeMappingUiDefinition;
import org.eclipse.jpt.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.ui.internal.details.AbstractEmbeddableUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class OrmEmbeddableUiDefinition 
	extends AbstractEmbeddableUiDefinition<PersistentType, OrmEmbeddable>
	implements OrmTypeMappingUiDefinition<OrmEmbeddable>
{
	// singleton
	private static final OrmEmbeddableUiDefinition INSTANCE = new OrmEmbeddableUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmTypeMappingUiDefinition<OrmEmbeddable> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private OrmEmbeddableUiDefinition() {
		super();
	}
	
	
	public JpaComposite buildTypeMappingComposite(
			OrmXmlUiFactory factory,
			PropertyValueModel<OrmEmbeddable> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return factory.createOrmEmbeddableComposite(subjectHolder, parent, widgetFactory);
	}
}
