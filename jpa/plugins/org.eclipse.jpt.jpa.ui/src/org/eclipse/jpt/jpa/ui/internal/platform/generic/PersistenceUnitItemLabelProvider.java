/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.platform.generic;

import org.eclipse.jpt.common.ui.internal.jface.AbstractItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.model.value.CompositePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.swt.graphics.Image;

public class PersistenceUnitItemLabelProvider
	extends AbstractItemExtendedLabelProvider<PersistenceUnit>
{
	protected final Image image;

	public PersistenceUnitItemLabelProvider(PersistenceUnit persistenceUnit, ItemLabelProvider.Manager manager) {
		super(persistenceUnit, manager);
		this.image = this.buildImage();
	}

	@Override
	public Image getImage() {
		return this.image;
	}

	protected Image buildImage() {
		return JptJpaUiPlugin.getImage(JptUiIcons.PERSISTENCE_UNIT);
	}
	
	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new TextModel(this.item);
	}

	protected static class TextModel
		extends PropertyAspectAdapter<PersistenceUnit, String>
	{
		public TextModel(PersistenceUnit subject) {
			super(PersistenceUnit.NAME_PROPERTY, subject);
		}
		@Override
		protected String buildValue_() {
			return this.subject.getName();
		}
	}
	
	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		return new DescriptionModel(this.item);
	}

	protected static class DescriptionModel
		extends TextModel
	{
		public DescriptionModel(PersistenceUnit subject) {
			super(subject);
		}
		@Override
		protected String buildValue_() {
			StringBuilder sb = new StringBuilder();
			sb.append(super.buildValue_());
			sb.append(" - ");  //$NON-NLS-1$
			sb.append(this.subject.getResource().getFullPath().makeRelative());
			return sb.toString();
		}
	}
	

	// ********** component description model **********

	@SuppressWarnings("unchecked")
	public static PropertyValueModel<String> buildQuotedComponentDescriptionModel(JpaContextNode node, PropertyValueModel<String> nodeTextModel) {
		return buildComponentDescriptionModel(node, true, nodeTextModel);
	}
		
	public static PropertyValueModel<String> buildNonQuotedComponentDescriptionModel(JpaContextNode node, PropertyValueModel<String>... nodeTextModels) {
		return buildComponentDescriptionModel(node, false, nodeTextModels);
	}
		
	protected static PropertyValueModel<String> buildComponentDescriptionModel(JpaContextNode node, boolean quote, PropertyValueModel<String>... nodeTextModel) {
		return new ComponentDescriptionModel(
					nodeTextModel,
					new TextModel(node.getPersistenceUnit()),
					node.getResource().getFullPath().makeRelative().toString(),
					quote
				);
	}
		
	public static class ComponentDescriptionModel
		extends CompositePropertyValueModel<String, Object>
	{
		protected final PropertyValueModel<String>[] nodeTextModels;
		protected final PropertyValueModel<String> persistenceUnitNameModel;
		protected final String path;
		protected final boolean quote;

		@SuppressWarnings("unchecked")
		ComponentDescriptionModel(
				PropertyValueModel<String> nodeTextModel,
				PropertyValueModel<String> persistenceUnitNameModel,
				String path,
				boolean quote
		) {
			this(
				new PropertyValueModel[] {nodeTextModel},
				persistenceUnitNameModel,
				path,
				quote
			);
		}

		ComponentDescriptionModel(
				PropertyValueModel<String>[] nodeTextModels,
				PropertyValueModel<String> persistenceUnitNameModel,
				String path,
				boolean quote
		) {
			super(ArrayTools.add(nodeTextModels, persistenceUnitNameModel));
			if (nodeTextModels.length < 1) {
				throw new IllegalArgumentException();
			}
			this.nodeTextModels = nodeTextModels;
			this.persistenceUnitNameModel = persistenceUnitNameModel;
			this.path = path;
			this.quote = quote;
		}

		@Override
		protected String buildValue() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.persistenceUnitNameModel.getValue());
			sb.append('/');
			if (this.quote) {
				sb.append('\"');
			}
			sb.append(this.nodeTextModels[0].getValue());
			for (int i = 1; i < this.nodeTextModels.length; i++) {
				sb.append('/');
				sb.append(this.nodeTextModels[i].getValue());
			}
			if (this.quote) {
				sb.append('\"');
			}
			sb.append(" - "); //$NON-NLS-1$
			sb.append(this.path);
			return sb.toString();
		}
	}
}
