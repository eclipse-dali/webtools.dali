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
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.orm.OrmStructureNode;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLink1_1JpaUiFactory;
import org.eclipse.jpt.eclipselink.ui.internal.structure.EclipseLink1_1OrmResourceModelStructureProvider;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;

public class EclipseLink1_1JpaPlatformUi
	extends EclipseLinkJpaPlatformUi  //TODO just extend for now, but we need to change this to match the JpaPlatform
{
	public EclipseLink1_1JpaPlatformUi() {
		super();
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
	
	@Override
	//EclipseLink has to be able to build UI for both the orm.xml and the eclipselink-orm.xml so we can't
	//just override the ormDetailsProviders and replace them with EclipseLink, we have to instead determine
	//which details providers we need based on the selected structurenode.  Need to find a better way to do this
	protected synchronized JpaDetailsProvider[] getDetailsProviders(JpaStructureNode structureNode) {
		// TODO - overhaul this class hierarchy!
		//it's getting better, but still an instanceof here - KFB
		if (structureNode instanceof OrmStructureNode) {
			if (((OrmStructureNode) structureNode).getContentType().equals(JptEclipseLinkCorePlugin.ECLIPSELINK1_1_ORM_XML_CONTENT_TYPE)) {
				return getEclipseLinkDetailsProviders();
			}
		}
		return super.getDetailsProviders(structureNode);
	}

}
