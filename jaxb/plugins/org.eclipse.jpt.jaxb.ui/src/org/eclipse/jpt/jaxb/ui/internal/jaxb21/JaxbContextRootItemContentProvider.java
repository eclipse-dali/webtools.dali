/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.jaxb21;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.ui.internal.jface.AbstractItemTreeContentProvider;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;


public class JaxbContextRootItemContentProvider
		extends AbstractItemTreeContentProvider<JaxbContextRoot, JaxbPackage> {
	
	public JaxbContextRootItemContentProvider(JaxbContextRoot rootContext, Manager manager) {
		super(rootContext, manager);
	}
	
	
	public IProject getParent() {
		return item.getJaxbProject().getProject();
	}
	
	@Override
	protected CollectionValueModel<JaxbPackage> buildChildrenModel() {
		return new CollectionAspectAdapter<JaxbContextRoot, JaxbPackage>(
				JaxbContextRoot.PACKAGES_COLLECTION,
				this.item) {
			
			@Override
			protected Iterable<JaxbPackage> getIterable() {
				return this.subject.getPackages();
			}
		};
	}
}
