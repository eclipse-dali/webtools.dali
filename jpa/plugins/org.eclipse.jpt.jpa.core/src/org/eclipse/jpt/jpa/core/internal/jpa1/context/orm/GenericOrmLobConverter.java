/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
	extends AbstractOrmConverter<Converter.ParentAdapter<OrmAttributeMapping>>
	implements OrmLobConverter
{
	public GenericOrmLobConverter(OrmConverter.ParentAdapter parentAdapter) {
		super(parentAdapter);
	}

	protected XmlConvertibleMapping getXmlConvertibleMapping() {
		return (XmlConvertibleMapping) this.getXmlAttributeMapping();
	}


	// ********** misc **********

	public Class<LobConverter> getConverterType() {
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
