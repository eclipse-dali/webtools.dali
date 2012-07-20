/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.JptUIPlugin;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.graphics.Image;

public abstract class AbstractMappingUiDefinition<M, T>
	implements MappingUiDefinition<M, T>
{
	protected AbstractMappingUiDefinition() {
		super();
	}

	/**
	 * Subclasses can use the Dali-provided icons by overriding the method
	 * {@link #getImageKey()} to return a key to one of the icons in the
	 * <code>org.eclipse.jpt.jpa.ui/icons</code> folder.
	 * @see JptUIPlugin#getImage(String)
	 */
	public final Image getImage() {
		return JptJpaUiPlugin.instance().getImage(this.getImageKey());
	}

	/**
	 * Subclasses can use the Dali-provided icons by overriding the method
	 * {@link #getImageKey()} to return a key to one of the icons in the
	 * <code>org.eclipse.jpt.jpa.ui/icons</code> folder.
	 * @see JptUIPlugin#getGhostImage(String)
	 */
	public Image getGhostImage() {
		return JptJpaUiPlugin.instance().getGhostImage(this.getImageKey());
	}

	/**
	 * Return the image key for a Dali-supplied icon; i.e. an icon in the
	 * <code>org.eclipse.jpt.jpa.ui/icons</code> folder.
	 * Override {@link #getImage()} and {@link #getGhostImage()} to use
	 * non-Dali-supplied icons.
	 * <p>
	 * By default return the key for the generic JPA content image.
	 * 
	 * @see JptUIPlugin#getImage(String)
	 */
	protected String getImageKey() {
		return JptUiIcons.JPA_CONTENT;
	}

	public boolean isEnabledFor(M mappableObject) {
		return true;
	}
}
