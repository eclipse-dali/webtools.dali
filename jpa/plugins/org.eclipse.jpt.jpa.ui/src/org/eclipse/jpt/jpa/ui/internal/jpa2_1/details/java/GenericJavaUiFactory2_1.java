/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2_1.details.java;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaEntity2_0;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.java.GenericJavaUiFactory2_0;
import org.eclipse.jpt.jpa.ui.jpa2_1.details.JpaUiFactory2_1;
import org.eclipse.swt.widgets.Composite;

public class GenericJavaUiFactory2_1
	extends GenericJavaUiFactory2_0
	implements JpaUiFactory2_1
{
	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createMappedSuperclassComposite(
			PropertyValueModel<? extends MappedSuperclass> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaMappedSuperclassComposite2_1((PropertyValueModel<? extends JavaMappedSuperclass>) mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JpaComposite createEntityComposite(
			PropertyValueModel<? extends Entity> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEntityComposite2_1((PropertyValueModel<? extends JavaEntity2_0>) entityModel, parentComposite, widgetFactory, resourceManager);
	}
}
