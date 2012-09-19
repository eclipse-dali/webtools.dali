/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal;

import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider.Manager;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbPackage;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.ui.internal.jaxb21.GenericJaxb_2_1_NavigatorTreeItemContentProviderFactory;

public class ELJaxbNavigatorTreeItemContentProviderFactory
		extends GenericJaxb_2_1_NavigatorTreeItemContentProviderFactory {
	
	private static ELJaxbNavigatorTreeItemContentProviderFactory INSTANCE 
			= new ELJaxbNavigatorTreeItemContentProviderFactory();
	
	
	public static ELJaxbNavigatorTreeItemContentProviderFactory instance() {
		return INSTANCE;
	}
	
	
	private ELJaxbNavigatorTreeItemContentProviderFactory() {
		super();
	}
	
	
	@Override
	public ItemTreeContentProvider buildProvider(Object item, Manager manager) {
		if (item instanceof OxmFile) {
			return buildOxmFileContentProvider((OxmFile) item, manager);
		}
		return super.buildProvider(item, manager);
	}
	
	@Override
	protected ItemTreeContentProvider buildJaxbPackageProvider(JaxbPackage item, Manager manager) {
		return new ELJaxbPackageContentProvider((ELJaxbPackage) item, manager);
	}
	
	protected ItemTreeContentProvider buildOxmFileContentProvider(OxmFile item, Manager manager) {
		return new OxmFileContentProvider(item, manager);
	}
}
