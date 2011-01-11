/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.jaxb21;

import org.eclipse.jpt.jaxb.core.context.JaxbPersistentField;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;


public class JaxbPersistentFieldItemLabelProvider
		 extends JaxbPersistentAttributeItemLabelProvider {
	
	public JaxbPersistentFieldItemLabelProvider(
		JaxbPersistentField jaxbField, DelegatingContentAndLabelProvider labelProvider) {
		
		super(jaxbField, labelProvider);
	}
	
	
	@Override
	public JaxbPersistentField model() {
		return (JaxbPersistentField) super.model();
	}

}
