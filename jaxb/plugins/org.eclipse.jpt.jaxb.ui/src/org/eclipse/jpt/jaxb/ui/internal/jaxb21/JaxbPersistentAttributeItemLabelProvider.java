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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.internal.jface.AbstractItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.ui.internal.JaxbMappingImageHelper;


public class JaxbPersistentAttributeItemLabelProvider
	extends AbstractItemExtendedLabelProvider<JaxbPersistentAttribute>
{
	protected final String text;
	protected final String description;
	
	public JaxbPersistentAttributeItemLabelProvider(JaxbPersistentAttribute attribute, ItemExtendedLabelProvider.Manager manager) {
		super(attribute, manager);
		this.text = this.buildText();
		this.description = this.buildDescription();
	}
	
	@Override
	protected PropertyValueModel<ImageDescriptor> buildImageDescriptorModel() {
		return new PropertyAspectAdapter<JaxbPersistentAttribute, ImageDescriptor>(IMAGE_ASPECT_NAMES, this.item) {
			@Override
			protected ImageDescriptor buildValue_() {
				return JaxbPersistentAttributeItemLabelProvider.this.buildImageDescriptor(this.subject.getMappingKey());
			}
		};
	}

	protected ImageDescriptor buildImageDescriptor(String mappingKey) {
		return JaxbMappingImageHelper.imageDescriptorForAttributeMapping(mappingKey);
	}

	protected static final String[] IMAGE_ASPECT_NAMES = new String[] {
		JaxbPersistentAttribute.DEFAULT_MAPPING_KEY_PROPERTY,
		JaxbPersistentAttribute.MAPPING_PROPERTY
	};

	@Override
	public String getText() {
		return this.text;
	}

	protected String buildText() {
		StringBuffer sb = new StringBuffer();
		if (this.item.isInherited()) {
			sb.append(this.item.getDeclaringJavaResourceType().getName());
			sb.append('.');
		}
		sb.append(this.item.getName());
		return sb.toString();
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}
	
	protected String buildDescription() {
		return this.item.getName();
	}
}
