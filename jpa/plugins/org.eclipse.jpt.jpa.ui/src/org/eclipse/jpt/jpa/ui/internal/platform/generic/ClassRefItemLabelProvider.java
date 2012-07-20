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
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.graphics.Image;

public class ClassRefItemLabelProvider
	extends AbstractItemExtendedLabelProvider<ClassRef>
{
	protected final Image image;

	public ClassRefItemLabelProvider(ClassRef classRef, ItemLabelProvider.Manager manager) {
		super(classRef, manager);
		this.image = this.buildImage();
	}

	@Override
	public Image getImage() {
		return this.image;
	}

	protected Image buildImage() {
		return JptJpaUiPlugin.instance().getImage(JptUiIcons.CLASS_REF, this.item.isVirtual());
	}

	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new TextModel(this.item);
	}

	protected static class TextModel
		extends PropertyAspectAdapter<ClassRef, String>
	{
		public TextModel(ClassRef subject) {
			super(ClassRef.CLASS_NAME_PROPERTY, subject);
		}
		@Override
		protected String buildValue_() {
			return this.subject.getClassName();
		}
	}

	@Override
	protected PropertyValueModel<String> buildDescriptionModel() {
		return PersistenceUnitItemLabelProvider.buildQuotedComponentDescriptionModel(
					this.item,
					this.textModel
				);
	}
}
