/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.navigator;

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;
import org.eclipse.jpt.jpa.ui.JpaRootContextNodeModel;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.graphics.Image;

public class RootContextNodeModelItemLabelProvider
	extends AbstractItemExtendedLabelProvider<JpaRootContextNodeModel>
{
	public RootContextNodeModelItemLabelProvider(JpaRootContextNodeModel rootContextNodeModel, ItemLabelProvider.Manager manager) {
		super(rootContextNodeModel, manager);
	}
	
	@Override
	protected PropertyValueModel<Image> buildImageModel() {
		return new TransformationPropertyValueModel<JpaRootContextNode, Image>(this.item, IMAGE_TRANSFORMER);
	}

	protected static final Transformer<JpaRootContextNode, Image> IMAGE_TRANSFORMER = new ImageTransformer();

	protected static class ImageTransformer
		extends TransformerAdapter<JpaRootContextNode, Image>
	{
		@Override
		public Image transform(JpaRootContextNode root) {
			return JptJpaUiPlugin.instance().getImage(JptUiIcons.JPA_CONTENT, (root == null));
		}
	}

	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new TransformationPropertyValueModel<JpaRootContextNode, String>(this.item, TEXT_TRANSFORMER);
	}

	protected static final Transformer<JpaRootContextNode, String> TEXT_TRANSFORMER = new TextTransformer();

	protected static class TextTransformer
		extends TransformerAdapter<JpaRootContextNode, String>
	{
		@Override
		public String transform(JpaRootContextNode root) {
			String text = JptUiMessages.JpaContent_label;
			return (root != null) ? text : "[" + text + "]"; //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		return new TransformationPropertyValueModel<JpaRootContextNode, String>(this.item, DESCRIPTION_TRANSFORMER);
	}

	protected static final Transformer<JpaRootContextNode, String> DESCRIPTION_TRANSFORMER = new DescriptionTransformer();

	protected static class DescriptionTransformer
		extends TransformerAdapter<JpaRootContextNode, String>
	{
		@Override
		public String transform(JpaRootContextNode root) {
			if (root == null) {
				return null;
			}

			StringBuilder sb = new StringBuilder();
			sb.append(JptUiMessages.JpaContent_label);
			sb.append(" - ");  //$NON-NLS-1$
			sb.append(root.getResource().getFullPath().makeRelative());
			return sb.toString();
		}
	}
}
