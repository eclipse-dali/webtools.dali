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

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.model.value.NullCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbPackage;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;

public class OxmFileContentProvider
		extends AbstractItemTreeContentProvider<OxmFile, JaxbContextNode> {
	
	public OxmFileContentProvider(OxmFile item, Manager manager) {
		super(item, manager);
	}
	
	
	public ELJaxbPackage getParent() {
		return (ELJaxbPackage) this.item.getContextRoot().getPackage(this.item.getPackageName());
	}
	
	@Override
	protected CollectionValueModel<JaxbContextNode> buildChildrenModel() {
		return new NullCollectionValueModel();
	}
}
