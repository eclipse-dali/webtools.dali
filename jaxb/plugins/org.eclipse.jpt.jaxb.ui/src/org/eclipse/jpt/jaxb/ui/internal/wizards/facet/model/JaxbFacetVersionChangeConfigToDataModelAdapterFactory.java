/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.facet.model;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jpt.jaxb.core.internal.facet.JaxbFacetVersionChangeConfig;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;


public class JaxbFacetVersionChangeConfigToDataModelAdapterFactory
		implements IAdapterFactory {
	
	private static final Class<?>[] ADAPTER_LIST = new Class[] { IDataModel.class };

	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}
	
	public Object getAdapter(Object adaptableObj, Class adapterType) {
		if (adapterType == IDataModel.class) {
			JaxbFacetVersionChangeDataModelProvider provider 
					= new JaxbFacetVersionChangeDataModelProvider((JaxbFacetVersionChangeConfig) adaptableObj);
			return DataModelFactory.createDataModel( provider );
		}
		
		return null;
	}
}
