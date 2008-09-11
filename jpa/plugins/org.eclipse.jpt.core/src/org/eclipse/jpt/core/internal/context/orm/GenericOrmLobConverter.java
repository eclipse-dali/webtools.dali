/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.LobConverter;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.utility.TextRange;

public class GenericOrmLobConverter extends AbstractOrmJpaContextNode
	implements LobConverter, OrmConverter
{
	private XmlConvertibleMapping resourceConvertableMapping;
	
	public GenericOrmLobConverter(OrmAttributeMapping parent, XmlBasic resourceBasic) {
		super(parent);
		this.initialize(resourceBasic);
	}
	
	@Override
	public OrmAttributeMapping getParent() {
		return (OrmAttributeMapping) super.getParent();
	}
	
	public String getType() {
		return Converter.LOB_CONVERTER;
	}
	
	public void initialize(XmlConvertibleMapping resourceConvertableMapping) {
		this.resourceConvertableMapping = resourceConvertableMapping;
	}
	
	public void update(XmlConvertibleMapping resourceConvertableMapping) {
		this.resourceConvertableMapping = resourceConvertableMapping;		
	}
	
	public TextRange getValidationTextRange() {
		return this.resourceConvertableMapping.getLobTextRange();
	}
	
	public void addToResourceModel() {
		this.resourceConvertableMapping.setLob(true);
	}
	
	public void removeFromResourceModel() {
		this.resourceConvertableMapping.setLob(false);
	}
}
