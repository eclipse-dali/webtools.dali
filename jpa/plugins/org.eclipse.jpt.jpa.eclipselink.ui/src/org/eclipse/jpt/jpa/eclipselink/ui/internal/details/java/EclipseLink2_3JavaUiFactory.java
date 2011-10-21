/*******************************************************************************
* Copyright (c) 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.jpa2.details.java.JavaUiFactory2_0;
import org.eclipse.swt.widgets.Composite;

/**
 *  EclipseLink2_0JpaUiFactory
 */
public class EclipseLink2_3JavaUiFactory
	extends EclipseLink2_0JavaUiFactory
	implements JavaUiFactory2_0
{
	public EclipseLink2_3JavaUiFactory() {
		super();
	}


	// **************** java type mapping composites ***************************

	@Override
	public JpaComposite createJavaEntityComposite(
			PropertyValueModel<JavaEntity> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		return new JavaEclipseLinkEntity2_3Composite(subjectHolder, parent, widgetFactory);
	}

	@Override
	public JpaComposite createJavaMappedSuperclassComposite(
			PropertyValueModel<JavaMappedSuperclass> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		return new JavaEclipseLinkMappedSuperclass2_3Composite(subjectHolder, parent, widgetFactory);
	}

}
