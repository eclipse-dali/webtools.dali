/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.LobConverter;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmLobConverter;
import org.eclipse.text.edits.ReplaceEdit;

public class GenericOrmLobConverter
	extends AbstractOrmConverter
	implements OrmLobConverter
{
	public GenericOrmLobConverter(OrmAttributeMapping parent) {
		super(parent);
	}


	// ********** misc **********

	public Class<? extends Converter> getType() {
		return LobConverter.class;
	}

	public void initialize() {
		this.getXmlConvertibleMapping().setLob(true);
	}


	// ********** validation **********

	public TextRange getValidationTextRange() {
		return this.getXmlConvertibleMapping().getLobTextRange();
	}


	// ********** refactoring **********

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return EmptyIterable.instance();
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return EmptyIterable.instance();
	}
}
