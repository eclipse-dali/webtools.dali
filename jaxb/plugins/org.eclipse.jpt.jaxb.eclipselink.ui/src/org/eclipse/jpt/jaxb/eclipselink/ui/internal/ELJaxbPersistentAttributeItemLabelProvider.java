/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.ui.internal;

import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.ui.internal.jaxb21.JaxbPersistentAttributeItemLabelProvider;
import org.eclipse.swt.graphics.Image;


public class ELJaxbPersistentAttributeItemLabelProvider
	extends JaxbPersistentAttributeItemLabelProvider
{
	public ELJaxbPersistentAttributeItemLabelProvider(JaxbPersistentAttribute attribute, ItemLabelProvider.Manager manager) {
		super(attribute, manager);
	}
	
	
	@Override
	protected Image buildImage(String mappingKey) {
		return ELJaxbMappingImageHelper.imageForAttributeMapping(mappingKey);
	}
}
