/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.platform;

import java.util.List;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLink1_1JpaUiFactory;
import org.eclipse.jpt.eclipselink.ui.internal.structure.EclipseLink1_1OrmResourceModelStructureProvider;
import org.eclipse.jpt.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;

public class EclipseLink1_1JpaPlatformUi
	extends EclipseLinkJpaPlatformUi
{
	public EclipseLink1_1JpaPlatformUi(JpaPlatformUiProvider... platformUiProviders) {
		super(platformUiProviders);
	}
	
	@Override
	protected EclipseLink1_1JpaUiFactory buildJpaUiFactory() {
		return new EclipseLink1_1JpaUiFactory();
	}
	
	@Override
	protected void addJpaStructureProvidersTo(List<JpaStructureProvider> providers) {
		super.addJpaStructureProvidersTo(providers);
		providers.add(EclipseLink1_1OrmResourceModelStructureProvider.instance());
	}
}
