/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal.navigator;

import org.eclipse.jpt.common.utility.internal.NotNullFilter;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.FilteringCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbPackage;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.ui.internal.jaxb21.JaxbPackageItemContentProvider;

public class ELJaxbPackageContentProvider
		extends JaxbPackageItemContentProvider {
	
	public ELJaxbPackageContentProvider(ELJaxbPackage jaxbPackage, Manager manager) {
		super(jaxbPackage, manager);
	}
	
	
	@Override
	protected CollectionValueModel<JaxbContextNode> buildChildrenModel() {
		return new CompositeCollectionValueModel(
				buildOxmFileChildrenModel(),
				super.buildChildrenModel());
	}
	
	protected CollectionValueModel<JaxbContextNode> buildOxmFileChildrenModel() {
		return new FilteringCollectionValueModel(
				new PropertyCollectionValueModelAdapter(
						new PropertyAspectAdapter<ELJaxbPackage, OxmFile>(
								ELJaxbPackage.OXM_FILE_PROPERTY, 
								(ELJaxbPackage) ELJaxbPackageContentProvider.this.item) {
							@Override
							protected OxmFile buildValue_() {
								return this.subject.getOxmFile();
							}
						}),
				NotNullFilter.instance());
	}
}
