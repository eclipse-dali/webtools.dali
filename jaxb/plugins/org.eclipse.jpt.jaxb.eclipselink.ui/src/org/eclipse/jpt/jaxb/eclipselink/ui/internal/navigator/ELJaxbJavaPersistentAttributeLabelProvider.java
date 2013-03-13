/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal.navigator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jaxb.eclipselink.ui.internal.ELJaxbMappingImageHelper;
import org.eclipse.jpt.jaxb.ui.internal.jaxb21.JavaPersistentAttributeItemLabelProvider;


public class ELJaxbJavaPersistentAttributeLabelProvider
		extends JavaPersistentAttributeItemLabelProvider {
	
	public ELJaxbJavaPersistentAttributeLabelProvider(JavaPersistentAttribute attribute, ItemExtendedLabelProvider.Manager manager) {
		super(attribute, manager);
	}
	
	
	@Override
	protected ImageDescriptor buildImageDescriptor(String mappingKey) {
		return ELJaxbMappingImageHelper.imageDescriptorForAttributeMapping(mappingKey);
	}
}
