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
import org.eclipse.xsd.XSDConcreteComponent;


public class XsdComponent<A extends XSDConcreteComponent>
		extends AdapterImpl {
	
	protected final A xsdComponent;
	
	
	protected XsdComponent(A xsdComponent) {
		super();
		this.xsdComponent = xsdComponent;
	}
	
	
	public A getXSDComponent() {
		return this.xsdComponent;
	}
	
	@Override
	public boolean isAdapterForType(Object type) {
		return type == XsdUtil.adapterFactory;
	}
}
