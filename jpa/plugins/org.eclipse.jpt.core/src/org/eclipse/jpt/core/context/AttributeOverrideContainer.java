/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface AttributeOverrideContainer
	extends JpaContextNode
{

	// **************** attribute overrides **************************************

	/**
	 * Return a list iterator of the attribute overrides whether specified or default.
	 * This will not be null.
	 */
	<T extends AttributeOverride> ListIterator<T> attributeOverrides();

	/**
	 * Return the number of attribute overrides, both specified and default.
	 */
	int attributeOverridesSize();

	/**
	 * Return a list iterator of the specified attribute overrides.
	 * This will not be null.  No add/remove for specified attribute overrides, the
	 * virtual attribute overrides will be populated from superclasses, then use
	 * {@link AttributeOverride#setVirtual(boolean)} to add/remove the attribute
	 * override from the source
	 */
	<T extends AttributeOverride> ListIterator<T> specifiedAttributeOverrides();

	/**
	 * Return the number of specified attribute overrides.
	 */
	int specifiedAttributeOverridesSize();

	/**
	 * Return a list iterator of the virtual attribute overrides, those not specified.
	 * This will not be null.
	 */
	<T extends AttributeOverride> ListIterator<T> virtualAttributeOverrides();

	/**
	 * Return the number of default attribute overrides.
	 */
	int virtualAttributeOverridesSize();

	/**
	 * Move the specified attribute override from the source index to the target index.
	 */
	void moveSpecifiedAttributeOverride(int targetIndex, int sourceIndex);
		String SPECIFIED_ATTRIBUTE_OVERRIDES_LIST = "specifiedAttributeOverrides"; //$NON-NLS-1$
		String VIRTUAL_ATTRIBUTE_OVERRIDES_LIST = "virtualAttributeOverrides"; //$NON-NLS-1$

	/**
	 * Return the attribute override, whether specified or default,
	 * with the given name.
	 */
	AttributeOverride getAttributeOverrideNamed(String name);
	
	interface Owner
	{
		/**
		 * Return the type mapping of the owning persistent type.
		 */
		TypeMapping getTypeMapping();

		/**
		 * Return the overridable persistent type, not the owning persistent type.
		 * This will be the persistent type of the mapped superclass or embeddable.
		 */
		TypeMapping getOverridableTypeMapping();

		Column resolveOverriddenColumn(String attributeOverrideName);

		/**
		 * Return the name of the table which the column belongs to by default
		 */
		String getDefaultTableName();

		/**
		 * return whether the given table cannot be explicitly specified
		 * in the column's 'table' element
		 */
		boolean tableNameIsInvalid(String tableName);

		/**
		 * Return the database table for the specified table name
		 */
		org.eclipse.jpt.db.Table getDbTable(String tableName);

		/**
		 * Return a list of table names that are valid for the overrides column, or join columns
		 */
		Iterator<String> candidateTableNames();		
		
		/**
		 * Return a validation message for the column's table not being valid in the context.
		 * Use the given text range in the message
		 */
		IMessage buildColumnTableNotValidMessage(AttributeOverride override, BaseColumn column, TextRange textRange);
		
		/**
		 * Return a validation message for the column's name not resolving on the 
		 * table either specified or default. Use the given text range in the message
		 */
		IMessage buildColumnUnresolvedNameMessage(AttributeOverride override, NamedColumn column, TextRange textRange);
	}

}
