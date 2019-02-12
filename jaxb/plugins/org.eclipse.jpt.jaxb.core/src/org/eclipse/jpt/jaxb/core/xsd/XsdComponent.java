/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
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
