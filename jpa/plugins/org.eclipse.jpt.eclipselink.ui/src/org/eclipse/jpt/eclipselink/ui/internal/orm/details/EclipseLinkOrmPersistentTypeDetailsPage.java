/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import java.util.Iterator;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.eclipselink.ui.internal.platform.EclipseLinkJpaPlatformUi;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmPersistentTypeDetailsPage;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmPersistentTypeDetailsPage
	extends OrmPersistentTypeDetailsPage
{
	/**
	 * Creates a new <code>EclipseLinkOrmPersistentTypeDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkOrmPersistentTypeDetailsPage(
			Composite parent,
			WidgetFactory widgetFactory) {
		super(parent, widgetFactory);
	}
	
	
	@Override
	protected EclipseLinkJpaPlatformUi getJpaPlatformUi() {
		return (EclipseLinkJpaPlatformUi) super.getJpaPlatformUi();
	}
	
	@Override
	public Iterator<TypeMappingUiProvider<? extends TypeMapping>> typeMappingUiProviders() {
		return getJpaPlatformUi().eclipseLinkOrmTypeMappingUiProviders();
	}
}
