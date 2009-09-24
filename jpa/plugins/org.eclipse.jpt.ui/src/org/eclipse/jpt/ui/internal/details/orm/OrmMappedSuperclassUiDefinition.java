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

import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.orm.OrmTypeMappingUiDefinition;
import org.eclipse.jpt.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.ui.internal.details.AbstractMappedSuperclassUiDefinition;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class OrmMappedSuperclassUiDefinition 
	extends AbstractMappedSuperclassUiDefinition<OrmMappedSuperclass>
	implements OrmTypeMappingUiDefinition<OrmMappedSuperclass>
{
	// singleton
	private static final OrmMappedSuperclassUiDefinition INSTANCE = 
			new OrmMappedSuperclassUiDefinition();
	
	/**
	 * Return the singleton.
	 */
	public static OrmTypeMappingUiDefinition<OrmMappedSuperclass> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private OrmMappedSuperclassUiDefinition() {
		super();
	}
	
	public JpaComposite buildTypeMappingComposite(
			OrmXmlUiFactory factory,
			PropertyValueModel<OrmMappedSuperclass> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return factory.createOrmMappedSuperclassComposite(subjectHolder, parent, widgetFactory);
	}
}
