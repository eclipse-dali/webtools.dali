/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.ui.internal;

import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkJpaUiFactory;
import org.eclipse.jpt.eclipselink1_1.ui.internal.java.details.EclipseLink1_1JavaEmbeddableComposite;
import org.eclipse.jpt.eclipselink1_1.ui.internal.java.details.EclipseLink1_1JavaEntityComposite;
import org.eclipse.jpt.eclipselink1_1.ui.internal.java.details.EclipseLink1_1JavaMappedSuperclassComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class EclipseLink1_1JpaUiFactory extends EclipseLinkJpaUiFactory //TODO just extend for now, but we need to change this to match the JpaPlatform
{
	public EclipseLink1_1JpaUiFactory() {
		super();
	}
	
	
	
	// **************** java type mapping composites ***************************
	
	@Override
	public JpaComposite createJavaMappedSuperclassComposite(
			PropertyValueModel<JavaMappedSuperclass> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		return new EclipseLink1_1JavaMappedSuperclassComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaEntityComposite(
			PropertyValueModel<JavaEntity> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		return new EclipseLink1_1JavaEntityComposite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaEmbeddableComposite(
			PropertyValueModel<JavaEmbeddable> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		return new EclipseLink1_1JavaEmbeddableComposite(subjectHolder, parent, widgetFactory);
	}
}