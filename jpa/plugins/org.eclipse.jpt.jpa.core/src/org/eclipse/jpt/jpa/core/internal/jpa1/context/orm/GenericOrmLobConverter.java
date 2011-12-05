/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.LobConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmLobConverter;
import org.eclipse.jpt.jpa.core.resource.orm.XmlConvertibleMapping;

public class GenericOrmLobConverter
	extends AbstractOrmConverter
	implements OrmLobConverter
{
	public GenericOrmLobConverter(OrmAttributeMapping parent, OrmConverter.Owner owner) {
		super(parent, owner);
	}

	protected XmlConvertibleMapping getXmlConvertibleMapping() {
		return (XmlConvertibleMapping) this.getXmlAttributeMapping();
	}


	// ********** misc **********

	public Class<? extends Converter> getType() {
		return LobConverter.class;
	}

	public void initialize() {
		this.getXmlConvertibleMapping().setLob(true);
	}


	// ********** validation **********

	@Override
	protected TextRange getXmlValidationTextRange() {
		return this.getXmlConvertibleMapping().getLobTextRange();
	}
}
