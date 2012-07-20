/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.context.persistence.JarFileRef;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.graphics.Image;

public class JarFileRefItemLabelProvider
	extends AbstractItemExtendedLabelProvider<JarFileRef>
{
	protected final Image image;

	public JarFileRefItemLabelProvider(JarFileRef jarFileRef, ItemLabelProvider.Manager manager) {
		super(jarFileRef, manager);
		this.image = this.buildImage();
	}

	@Override
	public Image getImage() {
		return this.image;
	}

	protected Image buildImage() {
		return JptJpaUiPlugin.instance().getImage(JptUiIcons.JAR_FILE_REF);
	}

	@Override
	protected PropertyValueModel<String> buildTextModel() {
		return new TextModel(this.item);
	}

	protected static class TextModel
		extends PropertyAspectAdapter<JarFileRef, String>
	{
		public TextModel(JarFileRef subject) {
			super(JarFileRef.FILE_NAME_PROPERTY, subject);
		}
		@Override
		protected String buildValue_() {
			return this.subject.getFileName();
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
