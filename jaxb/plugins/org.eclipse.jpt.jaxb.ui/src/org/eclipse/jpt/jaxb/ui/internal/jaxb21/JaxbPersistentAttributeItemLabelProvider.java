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

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.ui.internal.JaxbMappingImageHelper;
import org.eclipse.swt.graphics.Image;


public class JaxbPersistentAttributeItemLabelProvider
	extends AbstractItemExtendedLabelProvider<JaxbPersistentAttribute>
{
	protected final String text;
	protected final String description;
	
	public JaxbPersistentAttributeItemLabelProvider(JaxbPersistentAttribute attribute, ItemLabelProvider.Manager manager) {
		super(attribute, manager);
		this.text = this.buildText();
		this.description = this.buildDescription();
	}
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new PropertyAspectAdapter<JaxbPersistentAttribute, Image>(IMAGE_ASPECT_NAMES, this.item) {
			@Override
			protected Image buildValue_() {
				return JaxbPersistentAttributeItemLabelProvider.this.buildImage(this.subject.getMappingKey());
			}
		};
	}

	protected Image buildImage(String mappingKey) {
		return JaxbMappingImageHelper.imageForAttributeMapping(mappingKey);
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
