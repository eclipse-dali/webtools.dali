/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;

/**
 * <code>orm.xml</code> converter
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.1
 */
public interface OrmConverter
	extends Converter
{
	OrmAttributeMapping getParent();

	/**
	 * We need this "hack" because we can't build the resource converter before
	 * we build the context converter. There is no resource "converter", just a
	 * simple setting on the mapping. (Maybe "converter" is not the best way to
	 * model what is going on here....)
	 */
	void initialize();


	// ********** parent adapter **********

	/**
	 * Interface allowing converters to be used in multiple places
	 * (e.g. basic mappings, collection mappings, etc).
	 */
	public interface ParentAdapter
		extends Converter.ParentAdapter<OrmAttributeMapping>
	{
		// specify generic argument
	}


	// ********** adapter **********

	/**
	 * This interface allows a convertible mapping to interact with various
	 * <code>orm.xml</code> converters via the same protocol.
	 */
	public interface Adapter {
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
		OrmConverter buildConverter(OrmAttributeMapping parent, OrmXmlContextModelFactory factory);

		/**
		 * Return whether the specified XML mapping has the
		 * adapter's value set. If the adapter is active and the context
		 * mapping's converter does not match the adapter, the mapping will
		 * build a new converter (via the adapter).
		 * 
		 * @see #buildNewConverter(OrmAttributeMapping, OrmXmlContextModelFactory)
		 */
		boolean isActive(XmlAttributeMapping xmlMapping);

		/**
		 * Build a converter for specified mapping.
		 * This is used when the context model is synchronized with the
		 * resource model (and the resource model has changed) or when a client
		 * changes a mapping's converter.
		 * The appropriate setting in the XML mapping will be configured once
		 * the context converter has been added to the context model (see {@link
		 * OrmConverter#initialize()}.
		 * 
		 * @see #isActive(XmlAttributeMapping)
		 */
		OrmConverter buildNewConverter(OrmAttributeMapping parent, OrmXmlContextModelFactory factory);

		/**
		 * Clear the adapter's XML value from the specified XML mapping.
		 */
		void clearXmlValue(XmlAttributeMapping xmlMapping);
	}
}
