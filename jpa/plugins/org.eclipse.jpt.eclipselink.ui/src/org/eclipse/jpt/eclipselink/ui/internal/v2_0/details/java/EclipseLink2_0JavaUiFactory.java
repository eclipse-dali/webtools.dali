/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.java;

import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.details.java.EclipseLink1_1JavaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  EclipseLink2_0JpaUiFactory
 */
public class EclipseLink2_0JavaUiFactory extends EclipseLink1_1JavaUiFactory
{
	public EclipseLink2_0JavaUiFactory() {
		super();
	}

	
	// **************** java type mapping composites ***************************
	
	@Override
	public JpaComposite createJavaEntityComposite(
			PropertyValueModel<JavaEntity> subjectHolder,
			Composite parent, WidgetFactory widgetFactory) {
		return new JavaEclipseLinkEntity2_0Composite(subjectHolder, parent, widgetFactory);
	}
	
	@Override
	public JpaComposite createJavaIdMappingComposite(PropertyValueModel<JavaIdMapping> subjectHolder, Composite parent, WidgetFactory widgetFactory) {
		return new JavaEclipseLinkIdMapping2_0Composite(subjectHolder, parent, widgetFactory);
	}
}
