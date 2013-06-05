/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context;

import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Orderable;
import org.eclipse.jpt.jpa.db.Table;

/**
 * Multi-valued (1:m, m:m) relationship and element collection mappings support
 * ordering.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.3
 */
public interface Orderable2_0
		extends Orderable {
	
	// ***** order column *****
	
	/**
	 * String associated with changes to the "orderColumnOrdering" property
	 */
	String ORDER_COLUMN_ORDERING_PROPERTY = "orderColumnOrdering"; //$NON-NLS-1$
	
	/**
	 * If true, will have orderColumn metadata that takes precedence over other metadata
	 */
	boolean isOrderColumnOrdering();
	
	/**
	 * Will set orderColumnOrdering to true 
	 * (will remove all other metadata, and will set orderColumn to null)
	 */
	void setOrderColumnOrdering();
	
	/**
	 * Return the orderColumn object.
	 * This will never be null.
	 */
	SpecifiedOrderColumn2_0 getOrderColumn();
	
	
	String getDefaultTableName();
	
	
	// ***** parent adapter *****
	
	/**
	 * interface allowing ordering in multiple places
	 * (i.e. multi-value relationship and element collection mappings)
	 */
	interface ParentAdapter<M extends AttributeMapping> {
		
		M getOrderableParent();
		
		/**
		 * Return the name of the column's table.
		 */
		String getTableName();
		
		Table resolveDbTable(String tableName);
		
		/**
		 * This can be used for JPA 1.0 implementations.
		 */
		class Null<M extends AttributeMapping>
				implements ParentAdapter<M> {
			
			protected final M parent;
			
			public Null(M parent) {
				super();
				this.parent = parent;
			}
			
			public M getOrderableParent() {
				return this.parent;
			}
			
			public String getTableName() {
				return null;
			}
			
			public Table resolveDbTable(String tableName) {
				return null;
			}
		}
	}
}
