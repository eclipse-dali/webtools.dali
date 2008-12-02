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
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.eclipselink.ui.internal.platform.EclipseLinkJpaPlatformUi;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.mappings.details.OrmPersistentAttributeMapAsComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmPersistentAttributeMapAsComposite
	extends OrmPersistentAttributeMapAsComposite
{
	/**
	 * Creates a new <code>EclipseLinkOrmPersistentAttributeMapAsComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public EclipseLinkOrmPersistentAttributeMapAsComposite(
			Pane<? extends OrmPersistentAttribute> parentPane, Composite parent) {
		super(parentPane, parent);
	}
	
	
	@Override
	protected Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> attributeMappingUiProviders() {
		return ((EclipseLinkJpaPlatformUi) getJpaPlatformUi()).eclipseLinkOrmAttributeMappingUiProviders();
	}
	
	@Override
	protected Iterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> defaultAttributeMappingUiProviders() {
		return ((EclipseLinkJpaPlatformUi) getJpaPlatformUi()).defaultEclipseLinkOrmAttributeMappingUiProviders();
	}
}
