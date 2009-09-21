/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0;

import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.persistence.EclipseLinkPersistenceUnit2_0;
import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.options.Options2_0;
import org.eclipse.jpt.eclipselink.ui.internal.persistence.options.PersistenceXmlOptionsTab;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.EclipseLink1_1JpaUiFactory;
import org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.java.JavaEclipseLinkEntity2_0Composite;
import org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.java.JavaEclipseLinkIdMapping2_0Composite;
import org.eclipse.jpt.eclipselink.ui.internal.v2_0.persistence.options.PersistenceXmlOptions2_0Tab;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  EclipseLink2_0JpaUiFactory
 */
public class EclipseLink2_0JpaUiFactory extends EclipseLink1_1JpaUiFactory  //TODO just extend for now, but we need to change this to match the JpaPlatform
{
	public EclipseLink2_0JpaUiFactory() {
		super();
	}

	@Override
	protected PersistenceXmlOptionsTab<Options2_0> buildOptionsTab(
				PropertyValueModel<PersistenceUnit> subjectHolder,
				Composite parent,
				WidgetFactory widgetFactory) {
		PropertyValueModel<Options2_0> optionsHolder = this.buildOptionsHolder(subjectHolder);

		return new PersistenceXmlOptions2_0Tab(optionsHolder, parent, widgetFactory);
	}

	private PropertyValueModel<Options2_0> buildOptionsHolder(
				PropertyValueModel<PersistenceUnit> subjectHolder) {
		return new TransformationPropertyValueModel<PersistenceUnit, Options2_0>(subjectHolder) {
			@Override
			protected Options2_0 transform_(PersistenceUnit value) {

				return ((EclipseLinkPersistenceUnit2_0)value).getOptions();
			}
		};
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
