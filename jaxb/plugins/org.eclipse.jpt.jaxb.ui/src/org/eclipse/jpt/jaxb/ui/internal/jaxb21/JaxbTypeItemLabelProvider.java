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
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;


public abstract class JaxbTypeItemLabelProvider<I extends JavaType>
		extends AbstractItemExtendedLabelProvider<I> {
	
	protected final String text;
	protected final String description;
	
	protected JaxbTypeItemLabelProvider(I jaxbType, ItemExtendedLabelProvider.Manager manager) {
		super(jaxbType, manager);
		this.text = this.buildText();
		this.description = this.buildDescription();
	}

	@Override
	public String getText() {
		return this.text;
	}

	protected String buildText() {
		return this.item.getTypeName().getTypeQualifiedName();
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}
	
	protected String buildDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.item.getTypeName().getFullyQualifiedName());
		sb.append(" - ");  //$NON-NLS-1$
		sb.append(this.item.getResource().getFullPath().makeRelative());
		return sb.toString();
	}
}
