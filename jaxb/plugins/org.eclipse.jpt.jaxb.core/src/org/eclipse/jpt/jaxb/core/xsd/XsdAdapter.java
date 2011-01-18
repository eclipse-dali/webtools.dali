/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.xsd;

import org.eclipse.emf.common.notify.impl.AdapterImpl;


public class XsdAdapter
		extends AdapterImpl {
	
	protected XsdAdapter() {
		super();
	}
	
	
	@Override
	public boolean isAdapterForType(Object type) {
		return type == XsdUtil.adapterFactory;
	}
}
