/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.swt.widgets.Composite;

public class EclipseLink1_2JavaUiFactory
	extends EclipseLinkJavaUiFactory
{
	// ********** type mappings **********

	@Override
	public JpaComposite createJavaMappedSuperclassComposite(
			PropertyValueModel<JavaMappedSuperclass> mappedSuperclassModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkMappedSuperclass1_2Composite(mappedSuperclassModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createJavaEntityComposite(
			PropertyValueModel<JavaEntity> entityModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkEntity1_2Composite(entityModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	public JpaComposite createJavaEmbeddableComposite(
			PropertyValueModel<JavaEmbeddable> embeddableModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		return new JavaEclipseLinkEmbeddable1_2Composite(embeddableModel, parentComposite, widgetFactory, resourceManager);
	}
}
