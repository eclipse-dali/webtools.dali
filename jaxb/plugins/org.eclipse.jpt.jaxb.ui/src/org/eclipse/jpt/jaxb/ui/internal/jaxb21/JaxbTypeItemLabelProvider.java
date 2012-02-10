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
import org.eclipse.jpt.jaxb.core.context.JaxbType;


public abstract class JaxbTypeItemLabelProvider<I extends JaxbType>
	extends AbstractItemExtendedLabelProvider<I>
{
	protected final String text;
	protected final String description;
	
	protected JaxbTypeItemLabelProvider(I jaxbType, ItemLabelProvider.Manager manager) {
		super(jaxbType, manager);
		this.text = this.buildText();
		this.description = this.buildDescription();
	}

	@Override
	public String getText() {
		return this.text;
	}

	protected String buildText() {
		return this.item.getTypeQualifiedName();
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}
	
	protected String buildDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.item.getFullyQualifiedName());
		sb.append(" - ");  //$NON-NLS-1$
		sb.append(this.item.getResource().getFullPath().makeRelative());
		return sb.toString();
	}
}
