/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.text.edits.ReplaceEdit;

/**
 * <code>orm.xml</code> converter
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.1
 */
public interface OrmConverter
	extends Converter, XmlContextNode
{
	OrmAttributeMapping getParent();

	/**
	 * We need this "hack" because we can't build the resource converter before
	 * we build the context converter. There is no resource "converter", just a
	 * simple setting on the mapping. (Maybe "converter" is not the best way to
	 * model what is going on here....)
	 */
	void initialize();


	// ********** refactoring **********

	/**
	 * Create ReplaceEdits for renaming any references to the originalType to the newName.
	 * The originalType has not yet been renamed, the newName is the new short name.
	 */
	Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName);

	/**
	 * Create ReplaceEdits for moving any references to the originalType to the newPackage.
	 * The originalType has not yet been moved.
	 */
	Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage);

	/**
	 * Create ReplaceEdits for renaming any references to the originalPackage to the newName.
	 * The originalPackage has not yet been renamed.
	 */
	Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName);


	// ********** adapter **********

	/**
	 * This interface allows a convertible mapping to interact with various
	 * <code>orm.xml</code> converters via the same protocol.
	 */
	public interface Adapter
	{
		/**
		 * Return the type of converter handled by the adapter.
		 */
		Class<? extends Converter> getConverterType();

		/**
		 * Build a converter for specified mapping
		 * if the mapping's XML has the adapter's value set.
		 * Return <code>null</code> otherwise.
		 * This is used to build a converter during construction of the
		 * converter's mapping.
		 */
		OrmConverter buildConverter(OrmAttributeMapping parent, OrmXmlContextNodeFactory factory);

		/**
		 * Return whether the specified XML mapping has the
		 * adapter's value set. If the adapter is active and the context
		 * mapping's converter does not match the adapter, the mapping will
		 * build a new converter (via the adapter).
		 * 
		 * @see #buildNewConverter(OrmAttributeMapping, OrmXmlContextNodeFactory)
		 */
		boolean isActive(XmlConvertibleMapping xmlMapping);

		/**
		 * Build a converter for specified mapping.
		 * This is used when the context model is synchronized with the
		 * resource model (and the resource model has changed) or when a client
		 * changes a mapping's converter.
		 * The appropriate setting in the XML mapping will be configured once
		 * the context converter has been added to the context model (see {@link
		 * OrmConverter#initialize()}.
		 * 
		 * @see #isActive(XmlConvertibleMapping)
		 */
		OrmConverter buildNewConverter(OrmAttributeMapping parent, OrmXmlContextNodeFactory factory);

		/**
		 * Clear the adapter's XML value from the specified XML mapping.
		 */
		void clearXmlValue(XmlConvertibleMapping xmlMapping);
	}
}
